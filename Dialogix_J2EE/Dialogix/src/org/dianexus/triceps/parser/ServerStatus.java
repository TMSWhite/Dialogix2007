/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dianexus.triceps.parser;

import java.lang.Math;

/**
  Get server status information
*/
public class ServerStatus implements java.io.Serializable  {
  Runtime rt = Runtime.getRuntime();
  
  public void ServerStatus() { }
  
  /**
    @return the bytes of free memory
  */
  public long getFreeMemory() {
    return rt.freeMemory();
  }
  
  /**
    @return the bytes of Maximum available memory requestable
  */
  public long getMaxMemory() {
    return rt.maxMemory();
  }
  
  /**
    @return the total currently available memory
  */
  public long getTotalMemory() {
    return rt.totalMemory();
  }

  /**
    Manually run the garbage collection
  **/  
  public void garbageCollect() {
    rt.gc();
  } 
  
  /**
    @return memory used, in megabytes
  */
  public String getMemoryUsed() {
    long used = (getTotalMemory() - getFreeMemory());
    double kb = Math.floor(used / 1000);
    double mb = kb / 1000;
    return (Double.toString(mb) + "MB");
  }
}
