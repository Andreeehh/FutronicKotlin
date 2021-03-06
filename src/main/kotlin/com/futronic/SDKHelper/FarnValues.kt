package com.futronic.SDKHelper


enum class FarnValues {
    farn_low, farn_below_normal, farn_normal, farn_above_normal, farn_high, farn_max,

    /**
     * This value cannot be used as FARnLevel parameter.
     * The farn_custom shows that a custom value is assigned for FAR.
     */
    farn_custom
}

