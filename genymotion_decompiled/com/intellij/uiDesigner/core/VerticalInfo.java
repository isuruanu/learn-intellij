/*    */ package com.intellij.uiDesigner.core;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ 
/*    */ final class VerticalInfo extends DimensionInfo
/*    */ {
/*    */   public VerticalInfo(LayoutState layoutState, int gap)
/*    */   {
/* 20 */     super(layoutState, gap);
/*    */   }
/*    */ 
/*    */   protected int getOriginalCell(GridConstraints constraints) {
/* 24 */     return constraints.getRow();
/*    */   }
/*    */ 
/*    */   protected int getOriginalSpan(GridConstraints constraints) {
/* 28 */     return constraints.getRowSpan();
/*    */   }
/*    */ 
/*    */   int getSizePolicy(int componentIndex) {
/* 32 */     return this.myLayoutState.getConstraints(componentIndex).getVSizePolicy();
/*    */   }
/*    */ 
/*    */   int getChildLayoutCellCount(GridLayoutManager childLayout) {
/* 36 */     return childLayout.getRowCount();
/*    */   }
/*    */ 
/*    */   public int getMinimumWidth(int componentIndex) {
/* 40 */     return getMinimumSize(componentIndex).height;
/*    */   }
/*    */ 
/*    */   public DimensionInfo getDimensionInfo(GridLayoutManager grid) {
/* 44 */     return grid.myVerticalInfo;
/*    */   }
/*    */ 
/*    */   public int getCellCount() {
/* 48 */     return this.myLayoutState.getRowCount();
/*    */   }
/*    */ 
/*    */   public int getPreferredWidth(int componentIndex) {
/* 52 */     return getPreferredSize(componentIndex).height;
/*    */   }
/*    */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.intellij.uiDesigner.core.VerticalInfo
 * JD-Core Version:    0.6.2
 */