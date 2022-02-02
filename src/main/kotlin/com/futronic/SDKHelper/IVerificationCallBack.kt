package com.futronic.SDKHelper

interface IVerificationCallBack : ICallBack {
    /**
     * The "Verification operation complete" event.
     *
     * @param bSuccess `true` if the operation succeeds, otherwise is `false`
     * @param nResult the Futronic SDK return code.
     * @param bVerificationSuccess if the operation succeeds (bSuccess is `true`),
     * this parameters shows the verification operation result. `true`
     * if the captured from the attached scanner template is matched, otherwise is `false`.
     */
    fun OnVerificationComplete(
        bSuccess: Boolean,
        nResult: Int,
        bVerificationSuccess: Boolean
    )
}
