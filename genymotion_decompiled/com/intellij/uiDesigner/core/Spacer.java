/*    */ package com.intellij.uiDesigner.core;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.JComponent;
/*    */ 
/*    */ public class Spacer extends JComponent
/*    */ {
/*    */   public Dimension getMinimumSize()
/*    */   {
/* 23 */     return new Dimension(0, 0);
/*    */   }
/*    */ 
/*    */   public final Dimension getPreferredSize() {
/* 27 */     return getMinimumSize();
/*    */   }
/*    */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.intellij.uiDesigner.core.Spacer
 * JD-Core Version:    0.6.2
 */