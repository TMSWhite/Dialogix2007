/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.export;

import java.util.Comparator;
import org.dialogix.entities.InstrumentContent;

/**
 *
 * @author Coevtmw
 */
public class InstrumentContentsComparator implements Comparator<InstrumentContent>, java.io.Serializable {
    
    public int compare(InstrumentContent o1, InstrumentContent o2) {
        
        int is1 = o1.getItemSequence();
        int is2 = o2.getItemSequence();
        if (is1 < is2) {
            return -1;
        }
        else if (is1 > is2) {
            return 1;
        }
        else {
            return 0;
        }
    }

}
