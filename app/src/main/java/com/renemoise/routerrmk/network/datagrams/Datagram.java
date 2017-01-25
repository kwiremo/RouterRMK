package com.renemoise.routerrmk.network.datagrams;

/**
 * Created by Rene Moise on 1/22/2017.
 */

public interface Datagram {

    //This returns a String appropriately displaying the contents of the field.
    // Datagrams should return a string that is suitable for transmission from this method
    String toString();

    //    This returns a String of the contents in hex (if the fields is ASCII data then the
    // Hex characters representing the ASCII data is returned instead).  This is frequently used
    // in the display of data in the Sniffer UI.
    String toHexString();

    //This method should return a String that contains a full explanation of the datagram and all
    // its fields. If this datagram contains other datagrams then this will also include their
    // protocol explanation strings.  It is expected that the string will contain appropriate
    // carriage returns to create information on separate lines.
    String toProtocolExplanationString();

    //This method returns a String that can be displayed in one line. It will be used by the
    // SnifferUI to display the top line.  It should display the top level protocol and information about that protocol.
    String toSummaryString();
}
