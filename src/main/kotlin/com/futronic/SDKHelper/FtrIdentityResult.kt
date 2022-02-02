package com.futronic.SDKHelper

class FtrIdentifyResult {
    /**
     * If the identification process succeeds, field contains an index of the
     * matched record (the first element has an index 0) or -1, if
     * no matching source templates are detected.
     */
    var m_Index: Int

    /** Creates a new instance of FtrIdentifyResult  */
    init {
        m_Index = -1
    }
}
