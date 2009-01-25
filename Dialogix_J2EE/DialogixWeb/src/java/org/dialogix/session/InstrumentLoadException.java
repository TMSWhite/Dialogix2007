/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;
import java.util.logging.Level;

/**
 *
 * @author Coevtmw
 */
public class InstrumentLoadException extends Exception {
    private Object object=null;
    private Level level=Level.SEVERE;

    /**
     * Creates a new instance of <code>InstrumentLoadException</code> without detail message.
     */
    public InstrumentLoadException() {
    }


    /**
     * Constructs an instance of <code>InstrumentLoadException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InstrumentLoadException(String msg) {
        super(msg);
    }
    
    public InstrumentLoadException(String msg, Level level, Object object) {
        super(msg);
        this.object = object;
        this.level = level;
    }

    public Object getObject() {
        return object;
    }
    
    public Level getLevel() {
        return level;
    }
    
    
}
