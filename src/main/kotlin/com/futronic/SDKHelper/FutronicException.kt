package com.futronic.SDKHelper


class FutronicException : Exception {
    /**
     * Creates a new instance of `FutronicException` with the
     * specified error code.
     *
     * @param nErrorCode Error code
     */
    constructor(nErrorCode: Int) {
        errorCode = nErrorCode
    }

    /**
     * Constructs an instance of `FutronicException` with the
     * specified error code and with the specified detail message.
     *
     * @param nErrorCode Error code
     * @param msg the detail message.
     */
    constructor(nErrorCode: Int, msg: String?) : super(msg) {
        errorCode = nErrorCode
    }

    /**
     * Gets a error code.
     */
    var errorCode: Int
        private set
}
