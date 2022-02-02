package com.futronic.SDKHelper


class FutronicVerification(Template: ByteArray?) : FutronicSdkBase(),
    Runnable {
    /**
     * This function starts the verification operation.
     *
     * The verification operation starts in its own thread. To interact with
     * the verification operation caller must implement the `IVerificationCallBack`
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
     * <td>OnVerificationComplete</td>
     * <td>This event is signaled when the verification operation is completed.</td>
    </tr> *
    </table> *
     *
     * @param callBack reference to call back interface (can not be NULL)
     *
     * @exception IllegalStateException the class instance is disposed. Any
     * calls are prohibited.
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     *
     * @exception NullPointerException a null reference parameter callBack is
     * passed to the function.
     */
    @Throws(IllegalStateException::class, NullPointerException::class)
    fun Verification(callBack: IVerificationCallBack?) {
        CheckDispose()
        check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
        if (callBack == null) throw NullPointerException("A null reference parameter callBack is passed to the function.")
        m_State = EnrollmentState.process_in_progress
        m_CallBack = callBack
        m_bCancel = false

        // run new thread
        m_WorkedThread = Thread(this, "Verification operation")
        m_WorkedThread!!.start()
    }

    /**
     * The last verification result.
     *
     * @exception IllegalStateException the class instance is disposed. Any
     * calls are prohibited.
     * @exception IllegalStateException the object is not in an appropriate state
     * for the requested operation. The verification operation is not finished.
     */
    @get:Throws(IllegalStateException::class)
    val result: Boolean
        get() {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) {
                "The object is not in an appropriate state for the requested operation." +
                        "The verification operation is not finished."
            }
            return m_bResult
        }

    /**
     * The FARN value returned during the last verification operation.
     *
     * @exception IllegalStateException the class instance is disposed. Any
     * calls are prohibited.
     * @exception IllegalStateException the object is not in an appropriate state
     * for the requested operation. The verification operation is not finished.
     */
    @get:Throws(IllegalStateException::class)
    val fARNValue: Int
        get() {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) {
                "The object is not in an appropriate state for the requested operation." +
                        "The verification operation is not finished."
            }
            return m_FARNValue
        }

    /**
     * The main thread of the verification operation.
     * Function prepares all necessary parameters for the verification
     * operation and calls the function from unmanaged code. This native
     * function sets all parameters for SDK and starts the verification
     * operation.
     */
    override fun run() {
        var nResult = RETCODE_INTERNAL_ERROR
        try {
            synchronized(m_SyncRoot) {
                m_bResult = false
                nResult = VerificationProcess()
            }
        } finally {
            m_State = EnrollmentState.ready_to_process
            (m_CallBack as IVerificationCallBack).OnVerificationComplete(nResult == RETCODE_OK, nResult, m_bResult)
        }
    }

    /**
     * This is a copy of the source template.
     */
    private val m_Template: ByteArray

    /**
     * The last verification result.
     * You cannot access to this variable directly. Use the Result property.
     */
    private var m_bResult: Boolean

    /**
     * The FARN value returned during the last verification operation.
     * You cannot access to this variable directly. Use the FARNValue property.
     */
    private val m_FARNValue: Int

    /**
     * The FutronicVerification class constructor. Initialize a new instance of
     * the FutronicVerification class.
     *
     * @param Template a source template for verification.
     *
     * @exception FutronicException error occurs during SDK initialization. To
     * get error code, see property ErrorCode of the FutronicException class.
     * @exception NullPointerException a null reference parameter Template is
     * passed to the constructor.
     */
    init {
        if (Template == null) throw NullPointerException("A null reference parameter Template is passed to the constructor.")
        m_Template = Template.clone()
        m_FARNValue = 1
        m_bResult = false
    }
}
