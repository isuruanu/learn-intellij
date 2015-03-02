/*    */ package com.genymobile.genymotion.lib;
/*    */ 
/*    */ public class Utils
/*    */ {
/* 12 */   static String platform = System.getProperty("os.name").toLowerCase();
/*    */ 
/*    */   public static boolean isNullOrEmpty(String str) {
/* 15 */     return (str == null) || (str.length() == 0);
/*    */   }
/*    */ 
/*    */   public static boolean isMac() {
/* 19 */     return platform.indexOf("mac") >= 0;
/*    */   }
/*    */ 
/*    */   public static boolean isWin() {
/* 23 */     return platform.indexOf("win") >= 0;
/*    */   }
/*    */ 
/*    */   public static boolean isNotWin() {
/* 27 */     return platform.indexOf("win") == -1;
/*    */   }
/*    */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.lib.Utils
 * JD-Core Version:    0.6.2
 */