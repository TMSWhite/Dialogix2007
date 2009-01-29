package org.dianexus.triceps;

/*import java.lang.*;*/
/*import java.util.*;*/

class Value implements VersionIF {

    private Node node = null;
    private Datum datum = null;
    private int reserved = -1;
    private Schedule schedule = null;

    private Value() {
    }

    Value(Node n,
          Datum d,
          String time) {
        node = n;
        if (time != null && time.trim().length() > 0) {	// don't set to default if no time specified
            n.setTimeStamp(time);
        }
        datum = new Datum(d, n.getLocalName());
    }

    Value(String s,
          Datum d) {
        // no associated node - so a temporary variable
        datum = new Datum(d, s);
    }

    Value(String s,
          Datum d,
          int reserved,
          Schedule schedule) {
        this.reserved = reserved;
        this.schedule = schedule;
        datum = new Datum(d, s);
    }

    Node getNode() {
        return node;
    }

    void setDatum(Datum d,
                  String time) {
        if (node != null) // do set default time, even if no time string specified
        {
            node.setTimeStamp(time);
        }

        if (reserved >= 0 && schedule != null) {
            if (schedule.setReserved(reserved, d.stringVal(), false, true)) {
            // to restrict ability to change certain reserved words from within a schedule
//				datum = new Datum(d,datum.getName());
            } else {
            // a reserved word which should not be changed
            }
        } else {
            datum = new Datum(d, datum.getName());
        }
    }

    Datum getDatum() {
        if (reserved >= 0) {
            return new Datum(schedule.getTriceps(), schedule.getReserved(reserved), Datum.STRING);
        } else {
            return datum;
        }
    }

    boolean isReserved() {
        return (reserved >= 0);
    }

    int getReservedNum() {
        return reserved;
    }
}
