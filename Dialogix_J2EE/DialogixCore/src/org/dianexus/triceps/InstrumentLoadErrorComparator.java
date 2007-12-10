package org.dianexus.triceps;
import org.dialogix.entities.InstrumentLoadError;
import java.util.Comparator;
/**
 *  Sort errors by row then column.  If all in memory, this should be fast.  If working off of persisted data, SQL would be better.
 * @author Coevtmw
 */
public class InstrumentLoadErrorComparator implements Comparator<InstrumentLoadError> {
    
    public int compare(InstrumentLoadError o1, InstrumentLoadError o2) {
        int row1 = o1.getSourceRow();
        int row2 = o2.getSourceRow();
        int col1 = o1.getSourceColumn();
        int col2 = o2.getSourceColumn();
        if (row1 < row2) {
            return -1;
        }
        else if (row1 > row2) {
            return 1;
        }
        else {
            if (col1 < col2) {
                return -1;
            }
            else if (col1 > col2) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}
