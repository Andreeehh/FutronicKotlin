package com.futronic.SDKHelper


class FtrIdentifyRecord
/** Creates a new instance of FtrIdentifyRecord  */
{
    /**
     * The current record unique ID.
     * This record should be set from the main program.
     * The maximum unique ID length is 16 bytes.
     */
    var m_KeyValue: ByteArray? = null

    /**
     * The current template.
     */
    var m_Template: ByteArray? = null
    var clientId = 0
}
