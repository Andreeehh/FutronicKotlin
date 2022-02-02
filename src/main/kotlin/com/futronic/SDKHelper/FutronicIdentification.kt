package com.futronic.SDKHelper


class FutronicIdentification
/**
 * The FutronicIdentification class constructor.
 * Initialize a new instance of the FutronicIdentification class.
 *
 * @exception FutronicException error occurs during SDK initialization. To
 * get error code, see property ErrorCode of the FutronicException class.
 */
    : FutronicSdkBase(), Runnable {
    /**
     * This function starts the "get base template" operation for the identification purpose.
     *
     * The "get base template" operation starts in its own thread. To interact with
     * the enrollment operation caller must implement the `IIdentificationCallBack`
     * interface and should specify it. The interface methods denote following:
     * <table>
     * <thead>
     * <tr>
     * <td>Method</td>
     * <td>Description</td>
    </tr> *
    </thead> *
     * <tr>
     * <td>OnPutOn</td>
     * <td>Invitation for touching the fingerprint scanner surface.</td>
    </tr> *
     * <tr>
     * <td>OnTakeOff</td>
     * <td>Proposal to take off a finger from the scanner surface.</td>
    </tr> *
     * <tr>
     * <td>UpdateScreenImage</td>
     * <td>The "Show the current fingerprint image" event.</td>
    </tr> *
     * <tr>
     * <td>OnFakeSource</td>
     * <td>The "Fake Finger Detected" event. This event raises only if
     * `FakeDetection` and `FFDControl` properties are
     * `true`.</td>
    </tr> *
     * <tr>
     * <td>OnGetBaseTemplateComplete</td>
     * <td>This event is signaled when the enrollment operation for the
     * identification purpose is completed and base template is ready. If
     * the operation is completed successfully, you may start the
     * identification operation.</td>
    </tr> *
    </table> *
     * If the enrollment operation for the identification purpose is completed
     * successfully, you may start any identification function.
     * The next call of the enrollment operation will empty the last received results.
     *
     * @param callBack reference to call back interface (can not be NULL)
     *
     * @exception IllegalStateException the class instance is disposed. Any
     * calls are prohibited.
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation. The identification operation or the
     * enrollment operation for the identification purpose is already started.
     *
     * @exception NullPointerException a null reference parameter callBack is
     * passed to the function.
     */
    @Throws(IllegalStateException::class, NullPointerException::class)
    fun GetBaseTemplate(callBack: IIdentificationCallBack?) {
        CheckDispose()
        check(!(m_State != EnrollmentState.ready_to_process && m_State != EnrollmentState.ready_to_continue)) {
            "The object is not in an appropriate state for the requested operation." +
                    "The identification operation or the enrollment operation for the identification purpose is already started."
        }
        if (callBack == null) throw NullPointerException("A null reference parameter callBack is passed to the function.")
        m_CallBack = callBack
        m_bCancel = false

        // run new thread
        m_WorkedThread = Thread(this, "Get base template operation")
        m_WorkedThread!!.start()
    }

    /**
     * The function compares the base template against a set of source templates.
     * The identification operation is stopped, when the first matched template
     * is detected.
     *
     * @param rgTemplates the set of source templates (can not be NULL).
     * @param Result If the function succeeds, field `m_Index` contains an
     * index of the matched record (the first element has an index 0) or -1, if
     * no matching source templates are detected. Parameter can not be NULL.
     *
     * @return the Futronic SDK return code.
     *
     * @exception IllegalStateException the class instance is disposed. Any
     * calls are prohibited.
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation. The identification operation or the
     * enrollment operation for the identification purpose is already started.
     *
     * @exception NullPointerException a null reference parameter rgTemplates
     * or Result are passed to the function.
     */
    @Throws(IllegalStateException::class, NullPointerException::class)
    fun Identification(rgTemplates: Array<FtrIdentifyRecord?>?, Result: FtrIdentifyResult?): Int {
        var nResult = RETCODE_INTERNAL_ERROR
        CheckDispose()
        check(m_State == EnrollmentState.ready_to_continue) {
            "The object is not in an appropriate state for the requested operation." +
                    "The enrollment operation for the identification purpose is not completed."
        }
        if (Result == null) throw NullPointerException("A null reference parameter Result is passed to the function.")
        if (rgTemplates == null) throw NullPointerException("A null reference parameter rgTemplates is passed to the function.")
        Result.m_Index = -1
        if (rgTemplates.size == 0) return RETCODE_OK
        m_State = EnrollmentState.continue_in_progress
        try {
            synchronized(m_SyncRoot) { nResult = IdentifyProcess(rgTemplates, Result) }
        } finally {
            m_State = EnrollmentState.ready_to_continue
            m_bCancel = false
        }
        return nResult
    }
    /**
     * Get the base template.
     * Returns the base template. If enrollment operation for the identification
     * purpose is not completed, the return value is null.
     *
     * @exception IllegalStateException the class instance is disposed. Any
     * calls are prohibited.
     * @exception IllegalStateException the object is not in an appropriate state
     * for the requested operation. The identification operation or the enrollment
     * operation for the identification purpose is already started.
     */
    /**
     * Set the base template.
     *
     * @exception IllegalStateException the class instance is disposed. Any
     * calls are prohibited.
     *
     * @exception NullPointerException a null reference parameter BaseTemplate is
     * passed to the function.
     *
     * @exception IllegalStateException the object is not in an appropriate state
     * for the requested operation. The identification operation or the enrollment
     * operation for the identification purpose is already started.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class, NullPointerException::class)
    var baseTemplate: ByteArray?
        get() {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_continue) {
                "The object is not in an appropriate state for the requested operation." +
                        "The identification operation or the enrollment operation for the identification purpose is already started."
            }
            return if (m_BaseTemplate == null) null else m_BaseTemplate!!.clone()
        }
        set(BaseTemplate) {
            CheckDispose()
            check(!(m_State != EnrollmentState.ready_to_process && m_State != EnrollmentState.ready_to_continue)) {
                "The object is not in an appropriate state for the requested operation" +
                        "The identification operation or the enrollment operation for the identification purpose is already started."
            }
            if (BaseTemplate == null) throw NullPointerException("A null reference parameter BaseTemplate is passed to the function.")
            m_BaseTemplate = BaseTemplate.clone()
            m_State = EnrollmentState.ready_to_continue
        }

    /**
     * The main thread of the enrollment operation for the identification purpose.
     *
     * Function prepares all necessary parameters for the enrollment
     * operation and calls the function from unmanaged code. This native
     * function sets all parameters for SDK and starts the enrollment operation.
     */
    override fun run() {
        var nResult = RETCODE_INTERNAL_ERROR
        try {
            synchronized(m_SyncRoot) {
                m_BaseTemplate = null
                nResult = GetBaseTemplateProcess()
            }
        } finally {
            if (m_BaseTemplate != null) {
                m_State = EnrollmentState.ready_to_continue
            } else {
                m_State = EnrollmentState.ready_to_process
            }
            (m_CallBack as IIdentificationCallBack).OnGetBaseTemplateComplete(nResult == RETCODE_OK, nResult)
            m_bCancel = false
        }
    }

    /**
     * The base template.
     */
    private var m_BaseTemplate: ByteArray? = null
}
