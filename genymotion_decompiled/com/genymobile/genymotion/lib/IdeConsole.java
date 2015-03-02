/*    */ package com.genymobile.genymotion.lib;
/*    */ 
/*    */ public abstract class IdeConsole
/*    */ {
/*    */   public abstract void print(String paramString);
/*    */ 
/*    */   public void println()
/*    */   {
/* 17 */     print("\n");
/*    */   }
/*    */ 
/*    */   public void println(String message)
/*    */   {
/* 27 */     print(message + "\n");
/*    */   }
/*    */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.lib.IdeConsole
 * JD-Core Version:    0.6.2
 */