/*    */ package com.genymobile.genymotion.lib;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ class StreamGobbler extends Thread
/*    */ {
/*    */   InputStream is;
/*    */   String type;
/*    */ 
/*    */   StreamGobbler(InputStream inputStream)
/*    */   {
/* 22 */     this.is = inputStream;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try {
/* 29 */       InputStreamReader isr = new InputStreamReader(this.is);
/* 30 */       BufferedReader br = new BufferedReader(isr);
/* 31 */       while (br.readLine() != null);
/*    */     }
/*    */     catch (IOException ioe) {
/* 35 */       ioe.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.lib.StreamGobbler
 * JD-Core Version:    0.6.2
 */