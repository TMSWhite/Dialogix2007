package org.dianexus.triceps;

/** Encoder to ensure that an Input can be expressed without tabs and newlines - simply strip them out and replace with single space
 **/
class InputEncoder implements VersionIF {

    public String encode(String s) {    //  CONCURRENCY RISK?: NO
        StringBuffer sb = new StringBuffer();
        if (s == null) {
            return "";
        }
        char[] chars = s.toCharArray();
        char c;

        for (int i = 0; i < chars.length; ++i) {
            c = chars[i];
            if (Character.isWhitespace(c)) {
                sb.append(' ');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
