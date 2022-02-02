package com.futronic.SDKHelper

import java.awt.image.BufferedImage


abstract class FutronicSdkBase {
    companion object {
        /**
         * Base value for the error codes.
         */
        private const val FTR_RETCODE_ERROR_BASE = 1

        /**
         * Base value for the device error codes.
         */
        private const val FTR_RETCODE_DEVICE_BASE = 200

        // Futronic API return code
        const val RETCODE_OK = 0
        const val RETCODE_NO_MEMORY = FTR_RETCODE_ERROR_BASE + 1
        const val RETCODE_INVALID_ARG = FTR_RETCODE_ERROR_BASE + 2
        const val RETCODE_ALREADY_IN_USE = FTR_RETCODE_ERROR_BASE + 3
        const val RETCODE_INVALID_PURPOSE = FTR_RETCODE_ERROR_BASE + 4
        const val RETCODE_INTERNAL_ERROR = FTR_RETCODE_ERROR_BASE + 5
        const val RETCODE_UNABLE_TO_CAPTURE = FTR_RETCODE_ERROR_BASE + 6
        const val RETCODE_CANCELED_BY_USER = FTR_RETCODE_ERROR_BASE + 7
        const val RETCODE_NO_MORE_RETRIES = FTR_RETCODE_ERROR_BASE + 8
        const val RETCODE_INCONSISTENT_SAMPLING = FTR_RETCODE_ERROR_BASE + 10
        const val RETCODE_TRIAL_EXPIRED = FTR_RETCODE_ERROR_BASE + 11
        const val RETCODE_FRAME_SOURCE_NOT_SET = FTR_RETCODE_DEVICE_BASE + 1
        const val RETCODE_DEVICE_NOT_CONNECTED = FTR_RETCODE_DEVICE_BASE + 2
        const val RETCODE_DEVICE_FAILURE = FTR_RETCODE_DEVICE_BASE + 3
        const val RETCODE_EMPTY_FRAME = FTR_RETCODE_DEVICE_BASE + 4
        const val RETCODE_FAKE_SOURCE = FTR_RETCODE_DEVICE_BASE + 5
        const val RETCODE_INCOMPATIBLE_HARDWARE = FTR_RETCODE_DEVICE_BASE + 6
        const val RETCODE_INCOMPATIBLE_FIRMWARE = FTR_RETCODE_DEVICE_BASE + 7
        const val RETCODE_FRAME_SOURCE_CHANGED = FTR_RETCODE_DEVICE_BASE + 8
        // Signal values.
        /**
         * Invitation for touching the fingerprint scanner surface.
         */
        const val FTR_SIGNAL_TOUCH_SENSOR = 1

        /**
         * Proposal to take off a finger from the scanner surface.
         */
        const val FTR_SIGNAL_TAKE_OFF = 2
        const val FTR_SIGNAL_FAKE_SOURCE = 3
        // State bit mask values.
        /**
         * The pBitmap parameter provided
         */
        const val FTR_STATE_FRAME_PROVIDED = 0x01

        /**
         * The pBitmap parameter provided
         */
        const val FTR_STATE_SIGNAL_PROVIDED = 0x02
        // Response values.
        /**
         * The calling function must return control as quickly as possible. The
         * caller returns the RETCODE_CANCELED_BY_USER value.
         */
        const val FTR_CANCEL = 1

        /**
         * The calling function can continue execution.
         */
        const val FTR_CONTINUE = 2
        // Available frame sources.
        /**
         * No device attached
         */
        const val FSD_UNDEFINED = 0

        /**
         * Futronic USB Fingerprint Scanner Device.
         */
        const val FSD_FUTRONIC_USB = 1

        /**
         * Contains predefined FAR values. This array must have the same size as FarnValues
         * without farn_custom (currently only 6 elements).
         */
        var rgFARN = intArrayOf(
            1,  // 738151462: 0,343728560
            95,  //  20854379: 0,009711077
            166,  //    103930: 0,000048396
            245,  //       256: 0,000000119209
            345,  //         8: 0,000000003725
            405 //         1: 0,000000000466
        )

        // Values used for the version definition.
        const val FTR_VERSION_PREVIOUS = 1
        const val FTR_VERSION_COMPATIBLE = 2
        const val FTR_VERSION_CURRENT = 3

        /**
         * Number of the FTRAPI library references.
         */
        private var m_RefCount = 0

        /**
         * This object prevents more than one thread from using nRefCount simultaneously.
         * It also synchronize the FTRAPI library initialization/deinitialization.
         */
        private val m_InitLock = Any()

        /**
         * This object synchronizes the FTRAPI.dll usage from any Java-wrapper class.
         */
        var m_SyncRoot = Any()

        /**
         * Gets an error description by a Futronic SDK error code.
         *
         * @param nRetCode Futronic SDK error code.
         *
         * @return Error description.
         */
        fun SdkRetCode2Message(nRetCode: Int): String {
            val szMessage: String
            when (nRetCode) {
                RETCODE_OK -> szMessage = "The function is completed successfully."
                RETCODE_NO_MEMORY -> szMessage = "There is not enough memory to continue the execution of a program."
                RETCODE_INVALID_ARG -> szMessage =  "Some parameters were not specified or had invalid values."
                RETCODE_ALREADY_IN_USE -> szMessage =  "The current operation has already initialized the API."
                RETCODE_INVALID_PURPOSE -> szMessage =  "Base template is not correspond purpose."
                RETCODE_INTERNAL_ERROR -> szMessage =  "Internal SDK or Win32 API system error."
                RETCODE_UNABLE_TO_CAPTURE -> szMessage =  "Unable to capture."
                RETCODE_CANCELED_BY_USER -> szMessage =  "User canceled operation."
                RETCODE_NO_MORE_RETRIES -> szMessage =  "Number of retries is overflow."
                RETCODE_INCONSISTENT_SAMPLING -> szMessage =  "Source sampling is inconsistent."
                RETCODE_FRAME_SOURCE_NOT_SET -> szMessage =  "Frame source not set."
                RETCODE_DEVICE_NOT_CONNECTED -> szMessage =  "The frame source device is not connected."
                RETCODE_DEVICE_FAILURE -> szMessage =  "Device failure."
                RETCODE_EMPTY_FRAME -> szMessage =  "Empty frame."
                RETCODE_FAKE_SOURCE -> szMessage =  "Fake source."
                RETCODE_INCOMPATIBLE_HARDWARE -> szMessage =  "Incompatible hardware."
                RETCODE_INCOMPATIBLE_FIRMWARE -> szMessage =  "Incompatible firmware."
                RETCODE_TRIAL_EXPIRED -> szMessage =
                     "Trial limitation - only 1000 templates may be verified/identified."
                RETCODE_FRAME_SOURCE_CHANGED -> szMessage =  "Frame source has been changed."
                else -> szMessage = String.format("Unknown error code %d.", nRetCode)
            }
            return szMessage
        }

        init {
            System.loadLibrary("ftrJSDK")
        }
    }

    /**
     * This function should be called to abort current process (enrollment,
     * identification etc.).
     */
    fun OnCalcel() {
        m_bCancel = true
    }
    /**
     * get the "Fake Detection" value
     *
     * @exception IllegalStateException the object disposed.
     */
    /**
     * set the "Fake Detection" value
     *
     * Set to `true`, if you want to activate Live Finger Detection
     * (LFD) feature during the capture process. The capture time is increasing,
     * when you activate the LFD feature.
     *
     * @param bFakeDetection new value
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class)
    var fakeDetection: Boolean
        get() {
            CheckDispose()
            return m_bFakeDetection
        }
        set(bFakeDetection) {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
            m_bFakeDetection = bFakeDetection
        }
    /**
     * get the "Fake Detection Event Handler" property value
     *
     * @exception IllegalStateException the object disposed.
     */
    /**
     * set the "Fake Detection Event Handler" property value
     *
     * Set to `true`, if you want to receive the "Fake Detect" event.
     * You should also set the `m_bFakeDetection` property to receive
     * this event.
     *
     * @param bFFDControl new value
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class)
    var fFDControl: Boolean
        get() {
            CheckDispose()
            return m_bFFDControl
        }
        set(bFFDControl) {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
            m_bFFDControl = bFFDControl
        }
    /**
     * get the "False Accepting Ratio" property level
     *
     * @exception IllegalStateException the object disposed.
     */
    /**
     * set the "False Accepting Ratio" property level
     *
     * You cannot use the `farn_custom value` to set this property.
     * The `farn_custom` value shows that a custom value is assigned.
     *
     * @param FarnLevel new level
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     * @exception IllegalArgumentException the argument FarnLevel has invalid
     * value.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class, IllegalArgumentException::class)
    var fARnLevel: FarnValues
        get() {
            CheckDispose()
            return m_FarnLevel
        }
        set(FarnLevel) {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
            require(FarnLevel.ordinal <= rgFARN.size) { "The argument FarnLevel has invalid value" }
            m_FarnLevel = FarnLevel
            m_FARN = rgFARN[m_FarnLevel.ordinal]
        }
    /**
     * get the "False Accepting Ratio" property value
     *
     * @exception IllegalStateException the object disposed.
     */
    /**
     * set the "False Accepting Ratio" property level
     *
     * You can set any valid False Accepting Ratio (FAR). The value must be
     * between 1 and 1000. The larger value implies the "softer" result. If you
     * set one from FarnValues values, FARnLevel sets to the appropriate level.
     *
     * @param Value new value
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     * @exception IllegalArgumentException the argument Value has invalid
     * value.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class, IllegalArgumentException::class)
    var fARN: Int
        get() {
            CheckDispose()
            return m_FARN
        }
        set(Value) {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
            require(!(Value < 1 || Value > 1000)) { "The argument Value has invalid value" }
            m_FarnLevel = FarnValues.farn_custom
            for (i in rgFARN.indices) {
                if (rgFARN[i] == Value) {
                    m_FarnLevel = FarnValues.values()[i]
                    break
                }
            }
            m_FARN = Value
        }

    /**
     * Gets a value that indicates whether a library is trial version.
     *
     * @return `true<\ccode> if this is a trial version otherwise <c>false<c>
     * @exception  IllegalStateException The class instance is disposed. Any
     * calls are prohibited.
    </c></c>` */
    fun IsTrial(): Boolean {
        CheckDispose()
        return FutronicIsTrial()
    }

    /**
     * Gets a value that specify identification limit value.
     *
     * @return identification limit value. If property contains Integer.MAX_VALUE
     * that is "no limits"
     *
     * @exception  IllegalStateException The class instance is disposed. Any
     * calls are prohibited.
     */
    val identificationsLeft: Int
        get() {
            CheckDispose()
            return FutronicIdentificationsLeft()
        }
    /**
     * get the "Version compatible" property value
     *
     * @exception IllegalStateException the object disposed.
     */
    /**
     * set the "Version compatible" property
     *
     * @param Value new value
     *
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     * @exception IllegalArgumentException the argument Value has unknown
     * value.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class, IllegalArgumentException::class)
    var version: VersionCompatible
        get() {
            CheckDispose()
            return m_Version
        }
        set(Value) {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
            m_Version = Value
            if (Value == VersionCompatible.ftr_version_compatible) {
                m_InternalVersion = FTR_VERSION_COMPATIBLE
                return
            }
            if (Value == VersionCompatible.ftr_version_current) {
                m_InternalVersion = FTR_VERSION_CURRENT
                return
            }
            if (Value == VersionCompatible.ftr_version_previous) {
                m_InternalVersion = FTR_VERSION_PREVIOUS
                return
            }
            throw IllegalArgumentException("The argument Value has unknown value")
        }
    /**
     * get the "Fast Mode" property value
     *
     * @exception IllegalStateException the object disposed.
     */
    /**
     * set the "Fast Mode" property value
     *
     * Set to `true`, if you want to use fast mode.
     *
     * @param bFastMode new value
     * @exception IllegalStateException the object is not in an appropriate
     * state for the requested operation or the object disposed.
     */
    @get:Throws(IllegalStateException::class)
    @set:Throws(IllegalStateException::class)
    var fastMode: Boolean
        get() {
            CheckDispose()
            return m_bFastMode
        }
        set(bFastMode) {
            CheckDispose()
            check(m_State == EnrollmentState.ready_to_process) { "The object is not in an appropriate state for the requested operation" }
            m_bFastMode = bFastMode
        }

    /**
     * Clean all allocated resources
     *
     * Decrements the reference count for the library.
     * If the reference count on the library falls to 0, the SDK library
     * is uninitialized.
     */
    fun Dispose() {
        if (m_bDispose) return
        if (m_WorkedThread != null && m_WorkedThread!!.isAlive) {
            m_bCancel = true
            m_WorkedThread = try {
                m_WorkedThread!!.join(3000)
                if (m_WorkedThread!!.isAlive) m_WorkedThread!!.interrupt()
                null
            } catch (e: InterruptedException) {
                m_WorkedThread!!.interrupt()
                null
            }
        }
        synchronized(m_InitLock) {
            m_RefCount--
            if (m_RefCount == 0) FutronicTerminate()
        }
        m_bDispose = true
    }

    /**
     * State callback function. It's called from native code.
     *
     * @param Progress data capture progress information.
     * @param StateMask a bit mask indicating what arguments are provided.
     * @param Signal this signal should be used to interact with a user.
     * @param BitmapWidth contain a width of the bitmap to be displayed.
     * @param BitmapHeight contain a height of the bitmap to be displayed.
     * @param pBitmap contain a bitmap data.
     *
     * @return user response value
     */
    protected fun cbControl(
        Progress: FTR_PROGRESS?, StateMask: Int, Signal: Int,
        BitmapWidth: Int, BitmapHeight: Int,
        pBitmap: ByteArray
    ): Int {
        var nRetCode = FTR_CONTINUE
        if (StateMask and FTR_STATE_SIGNAL_PROVIDED != 0) {
            when (Signal) {
                FTR_SIGNAL_TOUCH_SENSOR -> m_CallBack!!.OnPutOn(Progress)
                FTR_SIGNAL_TAKE_OFF -> m_CallBack!!.OnTakeOff(Progress)
                FTR_SIGNAL_FAKE_SOURCE -> if (m_CallBack!!.OnFakeSource(Progress)) nRetCode = FTR_CANCEL
                else -> assert(false)
            }
        }
        if (StateMask and FTR_STATE_FRAME_PROVIDED != 0) {
            val hImage = BufferedImage(
                BitmapWidth,
                BitmapHeight,
                BufferedImage.TYPE_BYTE_GRAY
            )
            val db1 = hImage.raster.dataBuffer
            for (i in 0 until db1.size) {
                db1.setElem(i, pBitmap[i].toInt())
            }
            m_CallBack!!.UpdateScreenImage(hImage)
        }
        if (m_bCancel) {
            nRetCode = FTR_CANCEL
            m_bCancel = false
        }
        return nRetCode
    }

    protected fun finalize() {
        if (!m_bDispose) Dispose()
    }

    /**
     * If the class is disposed, this function raises an exception.
     *
     * This function must be called before any operation in all functions.
     *
     * @exception  IllegalStateException The class instance is disposed. Any
     * calls are prohibited.
     */
    @Throws(IllegalStateException::class)
    protected fun CheckDispose() {
        check(!m_bDispose) { "The object disposed" }
    }

    /**
     * `true` if the object disposed explicitly by calling
     * `Dispose` method, otherwise `false`.
     * The default value is `false`.
     */
    protected var m_bDispose: Boolean

    /**
     * `true` if the library should activate Live Finger Detection
     * (LFD) feature. You cannot modify this variable directly. Use the
     * `getFakeDetection` and `setFakeDetection` methods.
     * The default value is `false`.
     */
    protected var m_bFakeDetection: Boolean

    /**
     * `true` if the library should raise the "Fake Detection Event
     * Handler". You cannot modify this variable directly. Use the
     * `getFFDControl` and `setFFDControl` methods.
     * The default value is `true`.
     */
    protected var m_bFFDControl: Boolean

    /**
     * `true` if the library should abort current process. You cann't
     * modify this variable directly. Use the `OnCancel` method.
     * The default value is `true`.
     */
    protected var m_bCancel: Boolean

    /**
     * Current False Accepting Ratio value. Contains only one of
     * predefined values.
     * The default value is `FarnValues.farn_normal`.
     */
    protected var m_FarnLevel: FarnValues

    /**
     * The default value is `VersionCompatible.ftr_version_current`.
     */
    protected var m_Version: VersionCompatible

    /**
     * The default value is `VersionCompatible.ftr_version_current`.
     */
    protected var m_InternalVersion: Int

    /**
     * Current False Accepting Ratio value. It may contains any valid
     * value.
     */
    protected var m_FARN: Int

    /**
     * Fast mode property
     * Set this property to  `true` to use fast mode. You cannot modify this variable directly. Use the
     * `getFastMode` and `setFastMode` methods.
     * The default value is `false`.
     */
    protected var m_bFastMode: Boolean

    /**
     * Current frame source.
     */
    protected val m_FrameSource = FSD_FUTRONIC_USB

    /**
     * Reference to the operation thread: capture, enrollment etc.
     */
    protected var m_WorkedThread: Thread?
    protected var m_CallBack: ICallBack? = null

    /**
     * Current state for the class.
     */
    protected var m_State: EnrollmentState
    ///////////////////////////////////////////////////////////////////////////
    // Native API of ftrJSDKHelper library
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Activates the Futronic SDK interface.
     */
    protected external fun FutronicInitialize(): Int

    /**
     * Deactivates the Futronic API.
     */
    protected external fun FutronicTerminate()

    /**
     * Creates the fingerprint template for the desired purpose
     *
     * Function set parameters specific fro enrollment operation and does enrollment.
     */
    protected external fun FutronicEnroll(): Int

    /**
     * Creates the fingerprint template for the desired purpose
     *
     * Function set parameters specific fro enrollment operation and does enrollment.
     */
    protected external fun VerificationProcess(): Int

    /**
     * The native function does of the enrollment operation for the identification purpose.
     *
     * Function set parameters specific fro enrollment operation and does enrollment
     * for the identification purpose.
     */
    protected external fun GetBaseTemplateProcess(): Int

    /**
     * The native function sets parameters for the identification purpose and
     * does identification.
     *
     * @param rgTemplates the set of source templates.
     * @param Result If the function succeeds, field `m_Index` contains an
     * index of the matched record (the first element has an index 0) or -1, if
     * no matching source templates are detected.
     *
     * @return the Futronic SDK return code.
     */
    protected external fun IdentifyProcess(rgTemplates: Array<FtrIdentifyRecord?>?, Result: FtrIdentifyResult?): Int

    /**
     * The native function gets a value that indicates whether a library is trial version.
     *
     * @return <c>true<c> if this is a trial version otherwise <c>false<c>
    </c></c></c></c> */
    protected external fun FutronicIsTrial(): Boolean

    /**
     * The native function gets a value that specify identification limit value.
     *
     * @return identification limit value. If property contains Integer.MAX_VALUE
     * that is "no limits"
     */
    protected external fun FutronicIdentificationsLeft(): Int

    /**
     * Creates a new instance of FutronicSdkBase
     *
     * @exception  FutronicException Error occur during SDK initialization. To
     * get error code, see `getErrorCode` of FutronicException class.
     */
    init {
        synchronized(m_InitLock) {
            if (m_RefCount == 0) {
                val nResult: Int
                nResult = FutronicInitialize()
                if (nResult != RETCODE_OK) {
                    throw FutronicException(
                        nResult,
                        SdkRetCode2Message(nResult)
                    )
                }
            }
            m_RefCount++
        }
        m_bDispose = false
        m_bFakeDetection = false
        m_bFFDControl = true
        m_bCancel = true
        m_FarnLevel = FarnValues.farn_normal
        m_Version = VersionCompatible.ftr_version_current
        m_bFastMode = false
        m_InternalVersion = FTR_VERSION_CURRENT
        m_FARN = rgFARN[m_FarnLevel.ordinal]
        m_State = EnrollmentState.ready_to_process
        m_WorkedThread = null
    }
}
