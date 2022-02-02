package com.futronic.SDKHelper

class FutronicEnrollment : FutronicSdkBase(), Runnable {
    /**
     * This function starts the enrollment operation.
     *
     * The enrollment operation starts in its own thread. To interact with the
     * enrollment operation caller must implement the `IEnrollmentCallBack`
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
     * <td>The "Fake Finger Detected"  event. This event raises only if
     * `FakeDetection` and `FFDControl` properties are
     * `true`.</td>
    </tr> *
     * <tr>
     * <td>OnEnrollmentComplete</td>
     * <td>This event is signaled when the enrollment operation is completed.
     * If the operation is completed successfully, you may get a template.</td>
    </tr> *
    </table> *
     * If the enrollment operation is completed successfully, you may get a
     * template. The next call of the enrollment operation removes the last
     * created template.
     *
     * @param callBack reference to call back interface (can not be NULL)
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     *
     * @exception NullPointerException a null reference parameter callBack is
     * passed to the function.
     */
    @Throws(IllegalStateException::class, NullPointerException::class)
    fun Enrollment(callBack: IEnrollmentCallBack?) {
        CheckDispose()
        check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
        if (callBack == null) throw NullPointerException("A null reference parameter callBack is passed to the function.")
        m_State = EnrollmentState.process_in_progress
        m_CallBack = callBack
        m_bCancel = false

        // run new thread
        m_WorkedThread = Thread(this, "Enrollment operation")
        m_WorkedThread!!.start()
    }
    /**
     * get the MIOT mode setting
     *
     * @exception IllegalStateException the object disposed.
     */
    /**
     * Enable or disable the MIOT mode
     *
     * Set to `true`, if you want to enable the MIOT mode.
     *
     * @param bMIOTControl new value
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class)
    var mIOTControlOff: Boolean
        get() {
            CheckDispose()
            return m_bMIOTControlOff
        }
        set(bMIOTControlOff) {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
            m_bMIOTControlOff = bMIOTControlOff
        }
    /**
     * get max number of models in one template.
     *
     * @exception IllegalStateException the object disposed.
     */
    /**
     * Set max number of models in one template.
     *
     * This value must be between 3 and 10.
     *
     * @param MaxModels new value
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation.
     * @exception IllegalStateException the object disposed.
     * @exception IllegalArgumentException a method has been passed an
     * inappropriate argument MaxModels.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class, IllegalArgumentException::class)
    var maxModels: Int
        get() {
            CheckDispose()
            return m_MaxModels
        }
        set(MaxModels) {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
            require(!(MaxModels < 1 || MaxModels > 10)) { "The value of argument 'MaxModels' is outside the allowable range of values." }
            m_MaxModels = MaxModels
        }

    /**
     * Returns the template of the last enrollment operation.
     *
     * Returns a copy of template. If the last enrollment operation is
     * unsuccessful, the return code is null.
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation. The enrollment operation is started.
     * @exception IllegalStateException the object disposed.
     */
    @get:Throws(IllegalStateException::class)
    val template: ByteArray?
        get() {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation. The enrollment operation is started" }
            return if (m_Template == null) null else m_Template!!.clone()
        }

    /**
     * Return the quality of the template.
     *
     * Return value may be one of the following: 1 (the lowest quality) to  10
     * (best quality). If the enrollment operation is unsuccessful or was not
     * started, the return value is 0.
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation. The enrollment operation is started.
     * @exception IllegalStateException the object disposed.
     */
    val quality: Int
        get() {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation. The enrollment operation is started" }
            return m_Quality
        }

    /**
     * The main thread of the enrollment operation.
     */
    override fun run() {
        var nResult = RETCODE_INTERNAL_ERROR
        try {
            synchronized(m_SyncRoot) {
                m_Template = null
                m_Quality = 0
                nResult = FutronicEnroll()
            }
        } finally {
            m_State = EnrollmentState.ready_to_process
            (m_CallBack as IEnrollmentCallBack).OnEnrollmentComplete(nResult == RETCODE_OK, nResult)
        }
    }

    /**
     * The MIOT mode setting.
     * You cannot modify this variable directly. Use the `getMIOTControl`
     * and `setMIOTControl` methods.
     * The default value is `false`.
     */
    private var m_bMIOTControlOff = true

    /**
     * The template of the last enrollment operation.
     * You cannot modify this variable directly. Use the `getTemplate` method.
     */
    private var m_Template: ByteArray? = null

    /**
     * Estimation of a template quality in terms of recognition:
     * 1 corresponds to the worst quality, 10 denotes the best.
     */
    private var m_Quality = 0

    /**
     * Max number of models in one template. This value must be between 3 and 10.
     */
    private var m_MaxModels: Int

    companion object {
        protected var MinModelsValue = 1
        protected var MaxModelsValue = 10
        protected var DefaultModelsValue = 5
    }

    /**
     * The FutronicEnrollment class constructor.
     * Initialize a new instance of the FutronicEnrollment class.
     *
     * @exception FutronicException Error occurs during SDK initialization.
     * To get error code, see method `getErrorCode` of FutronicException
     * class.
     */
    init {
        m_MaxModels = DefaultModelsValue
    }
}