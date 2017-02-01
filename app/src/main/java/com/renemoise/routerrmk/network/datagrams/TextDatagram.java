package com.renemoise.routerrmk.network.datagrams;

/**
 * Created by Rene Moise on 1/31/2017.
 */

public class TextDatagram implements Datagram {

    private String payload;

    public TextDatagram(String payload) {
        this.payload = payload;
    }

    @Override
    public String toHexString() {
        StringBuilder hexValue = new StringBuilder();

        for(int i = 0; i < payload.length(); i++)
        {
            int asciiValue = (int)payload.charAt(i);
            hexValue.append(Integer.toHexString(asciiValue));
        }
        return hexValue.toString();
    }

    @Override
    public String toProtocolExplanationString() {
        return String.format("TEXT datagram. Value: {0}", payload);
    }

    @Override
    public String toSummaryString() {
        return String.format("Text Payload");
    }
}
