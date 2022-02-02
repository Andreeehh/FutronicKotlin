package com.futronic.SDKHelper

interface IIdentificationCallBack : ICallBack {
    /**
     * The "Get base template operation complete" event.
     *
     * @param bSuccess `true` if the operation succeeds, otherwise is `false`.
     * @param nResult The Futronic SDK return code.
     */
    fun OnGetBaseTemplateComplete(bSuccess: Boolean, nResult: Int)
}
