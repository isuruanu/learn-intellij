/*    */ package com.genymobile.genymotion.ide.idea;
/*    */ 
/*    */ import com.genymobile.genymotion.lib.IdeConsole;
/*    */ import com.intellij.notification.Notification;
/*    */ import com.intellij.notification.NotificationDisplayType;
/*    */ import com.intellij.notification.NotificationType;
/*    */ import com.intellij.notification.Notifications.Bus;
/*    */ import com.intellij.notification.NotificationsConfiguration;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class IdeaConsole extends IdeConsole
/*    */ {
/*    */   final ResourceBundle labels;
/*    */ 
/*    */   public IdeaConsole(ResourceBundle labels)
/*    */   {
/* 19 */     this.labels = labels;
/*    */ 
/* 23 */     NotificationsConfiguration.getNotificationsConfiguration().register("Genymotion", NotificationDisplayType.NONE);
/*    */   }
/*    */ 
/*    */   public void print(String message)
/*    */   {
/* 28 */     Notifications.Bus.notify(new Notification("Genymotion", this.labels.getString("genymotion"), message, NotificationType.INFORMATION));
/*    */   }
/*    */ 
/*    */   public void println(String message)
/*    */   {
/* 38 */     print(message);
/*    */   }
/*    */ 
/*    */   public void println()
/*    */   {
/* 43 */     print("");
/*    */   }
/*    */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.ide.idea.IdeaConsole
 * JD-Core Version:    0.6.2
 */