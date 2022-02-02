package com.futronic.SDKHelper

import java.awt.image.BufferedImage


interface ICallBack {
    /**
     * The "Put your finger on the scanner" event.
     *
     * @param Progress the current progress data structure.
     */
    fun OnPutOn(Progress: FTR_PROGRESS?)

    /**
     * The "Take off your finger from the scanner" event.
     *
     * @param Progress the current progress data structure.
     */
    fun OnTakeOff(Progress: FTR_PROGRESS?)

    /**
     * The "Show the current fingerprint image" event.
     *
     * @param Bitmap the instance of Bitmap class with fingerprint image.
     */
    fun UpdateScreenImage(Bitmap: BufferedImage?)

    /**
     * The "Fake finger detected" event.
     *
     * @param Progress the fingerprint image.
     *
     * @return `true` if the current indetntification operation
     * should be aborted, otherwise is `false`
     */
    fun OnFakeSource(Progress: FTR_PROGRESS?): Boolean
}
