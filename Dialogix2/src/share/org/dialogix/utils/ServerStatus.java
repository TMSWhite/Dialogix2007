/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.utils;
/** Routines for extracting memory status information
*/
public class ServerStatus {
   Runtime rt = Runtime.getRuntime();
   
   public void ServerStatus() { }
   
   public long getFreeMemory() {
      return rt.freeMemory();
   }
   
   public long getMaxMemory() {
      return rt.maxMemory();
   }
   
   public long getTotalMemory() {
      return rt.totalMemory();
   }
   
   public void garbageCollect() {
      rt.gc();
   } 
}
