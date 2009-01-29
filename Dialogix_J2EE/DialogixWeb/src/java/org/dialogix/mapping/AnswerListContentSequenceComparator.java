/*
 * AnswerListContentSequenceComparator.java
 * 
 * Created on Nov 6, 2007, 11:07:50 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.mapping;
import org.dialogix.entities.AnswerListContent;
import java.util.Comparator;

/**
 *
 * @author coevtmw
 */
public class AnswerListContentSequenceComparator implements Comparator<AnswerListContent>, java.io.Serializable {

    public int compare(AnswerListContent o1, AnswerListContent o2) {
        int int_o1 = o1.getAnswerOrder();
        int int_o2 = o2.getAnswerOrder();
        if (int_o1 < int_o2)
            return -1;
        else if (int_o1 > int_o2)
            return 1;
        else
            return 0;
    }
}
