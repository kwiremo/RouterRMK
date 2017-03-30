package com.renemoise.routerrmk.network.datagram_fields;

/**
 * Created by Rene Moise on 3/30/2017.
 */

public class LL3PChecksum extends CRC {
    public LL3PChecksum(String crcValue) {
        super(crcValue);
    }

    @Override
    public String explainSelf() {
        return String.format("LL3P Checksum: " + toAsciiString());
    }
}
