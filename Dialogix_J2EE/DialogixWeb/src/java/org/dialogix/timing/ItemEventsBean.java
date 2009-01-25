/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.timing;

import java.io.Serializable;
import org.dialogix.entities.PageUsageEvent;

/**
 *
 * @author Coevtmw
 */
public class ItemEventsBean implements Serializable {
    private int focusTime = -1;
    private int firstPostFocusTime = -1;
    private int blurTime = -1;
    private int vacillation = 0;
    private int lastResponseLatency = -1;
    private int lastResponseDuration = -1;
    private int numVisits = 0;
    private int totalResponseLatency = -1;
    private int totalResponseDuration = -1;
    private int priorBlurTime = -1;
    private String varName = null;

    public ItemEventsBean(String varName) {
        this.varName = varName;
    }
    
    public void processPageUsageEvent(PageUsageEvent pageUsageEvent, int priorBlurTime, int itemEventCount) {
        this.priorBlurTime = priorBlurTime;
        
        String guiActionType = pageUsageEvent.getGuiActionType();
        if (itemEventCount == 1) {
            ++numVisits;
            focusTime = -1;
            firstPostFocusTime = -1;
            blurTime = -1;
        }
        if ("focus".equals(guiActionType)) {
            this.focusTime = pageUsageEvent.getDuration();            
        }
        else if ("change".equals(guiActionType)) {
            // FIXME - which require change vs. blur?
            // change:  text, password, textarea
            // blur: 
            // both: select-one, radio, checkbox, 
            // FIXME - password, text, and textarea can all change without first having a focus event
            blurTime = pageUsageEvent.getDuration();
            if (focusTime == -1) {
                focusTime = priorBlurTime;
            }
            this.lastResponseDuration = (blurTime - focusTime);
            this.totalResponseDuration += (blurTime - focusTime);
            ++vacillation;
        }
        else {
            if (firstPostFocusTime == -1) {
                firstPostFocusTime = pageUsageEvent.getDuration();
                if (focusTime == -1) {
                    focusTime = priorBlurTime;
                }
                lastResponseLatency = (firstPostFocusTime - focusTime);
                totalResponseLatency += (firstPostFocusTime - focusTime);
            }
        }
    }

    public int getTotalResponseDuration() {
        return totalResponseDuration;
    }

    public int getTotalResponseLatency() {
        return totalResponseLatency;
    }
    
}
