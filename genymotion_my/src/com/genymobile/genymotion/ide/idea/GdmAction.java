/*    */ package com.genymobile.genymotion.ide.idea;
/*    */ 
/*    */ import com.genymobile.genymotion.lib.IdeConsole;
/*    */ import com.genymobile.genymotion.lib.Utils;
/*    */ import com.genymobile.genymotion.lib.VMToolsEngine;
/*    */ import com.google.common.base.Strings;
/*    */ import com.intellij.ide.util.PropertiesComponent;
/*    */ import com.intellij.notification.Notification;
/*    */ import com.intellij.notification.NotificationDisplayType;
/*    */ import com.intellij.notification.NotificationType;
/*    */ import com.intellij.notification.Notifications;
import com.intellij.notification.Notifications.Bus;
/*    */ import com.intellij.openapi.actionSystem.AnAction;
/*    */ import com.intellij.openapi.actionSystem.AnActionEvent;
/*    */ import com.intellij.openapi.actionSystem.PlatformDataKeys;
/*    */ import com.intellij.openapi.options.ShowSettingsUtil;
/*    */ import com.intellij.openapi.project.Project;
/*    */ import java.io.File;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public class GdmAction extends AnAction
/*    */ {
/*    */   private VMToolsEngine engine;
/*    */   private IdeConsole console;
/* 30 */   private ResourceBundle labels = ResourceBundle.getBundle("resources.labels");
/*    */ 
/*    */   public void actionPerformed(AnActionEvent e)
/*    */   {
/* 35 */     if (this.console == null) {
/* 36 */       this.console = new IdeaConsole(this.labels);
/*    */     }
/*    */ 
/* 40 */     if (!loadLibrary()) {
/* 41 */       return;
/*    */     }
/*    */ 
/* 44 */     Project project = (Project)e.getData(PlatformDataKeys.PROJECT);
/* 45 */     GdmDialog dlg = new GdmDialog(this.engine, this.console, project);
/* 46 */     dlg.show();
/*    */   }
/*    */ 
/*    */   private boolean loadLibrary()
/*    */   {
/* 57 */     if (VMToolsEngine.isInit()) {
/* 58 */       return true;
/*    */     }
/*    */ 
/* 61 */     this.console.println("Loading Genymotion library");
/*    */ 
/* 63 */     String genymotionPath = PropertiesComponent.getInstance().getValue("KEY_GENYMOTION_PATH", "");
/*    */ 
/* 65 */     if ((Strings.isNullOrEmpty(genymotionPath)) || (!VMToolsEngine.isValidGenymotionPath(genymotionPath))) {
/* 66 */       Notifications.Bus.register("Genymotion settings", NotificationDisplayType.STICKY_BALLOON);
/*    */ 
/* 69 */       Notifications.Bus.notify(new Notification("Genymotion settings", this.labels.getString("genymotion_warning"), this.labels.getString("must_specify_path"), NotificationType.WARNING));
/*    */ 
/* 75 */       ShowSettingsUtil.getInstance().showSettingsDialog(null, this.labels.getString("genymotion"));
/* 76 */       return false;
/*    */     }
/*    */ 
/* 80 */     if ((!Strings.isNullOrEmpty(genymotionPath)) && (Utils.isMac())) {
/* 81 */       genymotionPath = genymotionPath + File.separator + "Contents" + File.separator + "MacOS";
/*    */     }
/*    */ 
/* 85 */     this.engine = new VMToolsEngine(genymotionPath, this.console);
/*    */ 
/* 88 */     this.console.println("Trying to initialize engine");
/*    */ 
/* 90 */     if (!this.engine.initialize()) {
/* 91 */       this.console.println("Initialize Engine: failed");
/* 92 */       return false;
/*    */     }
/* 94 */     this.console.println("Initialize Engine: success");
/* 95 */     return true;
/*    */   }
/*    */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.ide.idea.GdmAction
 * JD-Core Version:    0.6.2
 */