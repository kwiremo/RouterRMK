package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.support.LabException;

/**
 * Created by Rene Moise on 1/22/2017.
 * The datagramHeaderField interface serves as a trmplate for the datagram header fields. All the
 * fields of a datagram will implement this interface.
 */

public interface DatagramHeaderField {

    /**
    This returns a String appropriately displaying the contents of the field. This is frequently
    used in the construction of transmission strings. This string should be an appropriate length of
    characters for this type field. For example, if this is a 2 byte type field, it should always be
    4 characters long.
     */
    public String toString();

    /**
    This returns a String of the contents in hex.
    If the field is an Integer, return a hex value for the Integer in the appropriate length for
    the field.  For example, a two byte field with the Integer value 65 would return the hex string
    “003F”. If the field is an ASCII string then all ASCII characters should be converted to two
    byte ASCII hex value. For example, the ASCII String “Abcd” would be returned by this method
    as “41626364”.
     */
    public String toHexString();

    /**
    This returns a string that is formatted to display the content and the meaning of the field.
    This will be used by the Sniffer UI.  For example, if the field is an LL2P TYPE FIELD that
    contains 0x8001, which is the type value for an LL3P packet, then the returned string should be
    something clear like this:
     “LL2P Type Field. Value = 0x8001, payload is an LL3P Packet”
    */
    public String explainSelf();


    /**
    This returns an ASCII string where each hex byte is converted to its corresponding ASCII
    characters.
     */
    public String toAsciiString();
}
