package org.dialogix.util;

import java.text.DecimalFormat;
import java.util.logging.*;

/** Encoder to massage XML Attribute Strings to prevent premature termination of Attribute Nodees
 **/
public class XMLAttrEncoder implements java.io.Serializable {
    /** Encode XML Attribute values.  Replace any character that might prematurely terminate an XML attribute with an XML entity
     * FIXME - needs to be modified to supporte Unicode
     **/
    public String encode(String s) {   // CONCURRENCY RISK?:  Shouldn't be
        StringBuffer sb = new StringBuffer();
        char[] chars = s.toCharArray();
        char c;

        for (int i = 0; i < chars.length; ++i) {
            c = chars[i];
            if (Character.isISOControl(c) || c == '\'' || c == '<' || c == '>' || c == '"' || c == '&') {
//			sb.append(attEntityFormat(c));
                sb.append(c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /** Format XML Entities using &#000; syntax.  FIXME  This does not extend gracefully to Unicode
     **/
    private String attEntityFormat(char c) {   //  CONCURRENCY RISK?:  Should be OK
        try {
            DecimalFormat ATT_ENTITY_FORMAT = new DecimalFormat("'&#'000';'");
            return ATT_ENTITY_FORMAT.format((long) (c & 0x00ff));	// must strip high byte for HTML
        } catch (Exception t) {
            Logger.getLogger("org.dialogix.util.XMLAttrEncoder").log(Level.SEVERE, "", t);
            return "";
        }
    }
}
