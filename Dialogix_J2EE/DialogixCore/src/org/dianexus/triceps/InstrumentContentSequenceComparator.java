/*
 * InstrumentContentSequenceComparator.java
 * 
 * Created on Nov 6, 2007, 10:37:48 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dianexus.triceps;
import org.dialogix.entities.*;
import java.util.*;

/**
 *
 * @author coevtmw
 */
public class InstrumentContentSequenceComparator implements Comparator<InstrumentContent> {

    public int compare(InstrumentContent o1, InstrumentContent o2) {
        int int_o1 = o1.getItemSequence();
        int int_o2 = o2.getItemSequence();
        if (int_o1 < int_o2)
            return -1;
        else if (int_o1 > int_o2)
            return 1;
        else
            return 0;
    }
}
