package com.futronic.SDKHelper

interface IEnrollmentCallBack : ICallBack {
    /**
     * The "Enrollment operation complete" event.
     *
     * @param bSuccess `true` if the operation succeeds, otherwise is
     * `false`.
     * @param nResult The Futronic SDK return code (see FTRAPI.h).
     */
    fun OnEnrollmentComplete(bSuccess: Boolean, nResult: Int)
}
