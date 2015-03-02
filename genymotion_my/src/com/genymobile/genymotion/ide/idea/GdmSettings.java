/*     */ package com.genymobile.genymotion.ide.idea;
/*     */ 
/*     */ import com.genymobile.genymotion.lib.Utils;
/*     */ import com.genymobile.genymotion.lib.VMToolsEngine;
/*     */ import com.google.common.base.Strings;
/*     */ import com.intellij.ide.util.PropertiesComponent;
/*     */ import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
/*     */ import com.intellij.openapi.options.Configurable;
/*     */ import com.intellij.openapi.options.ConfigurationException;
/*     */ import com.intellij.openapi.ui.TextFieldWithBrowseButton;
/*     */ import com.intellij.openapi.ui.popup.Balloon;
/*     */ import com.intellij.openapi.ui.popup.Balloon.Position;
/*     */ import com.intellij.openapi.ui.popup.BalloonBuilder;
/*     */ import com.intellij.openapi.ui.popup.JBPopupFactory;
/*     */ import com.intellij.ui.awt.RelativePoint;
/*     */ import java.awt.Color;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import org.jetbrains.annotations.Nls;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public class GdmSettings
/*     */   implements Configurable
/*     */ {
/*     */   public static final String KEY_GENYMOTION_PATH = "KEY_GENYMOTION_PATH";
/*     */   private JPanel myPanel;
/*     */   private TextFieldWithBrowseButton pathTextFieldWithButton;
/*     */   private JLabel labelPath;
/*     */   private ResourceBundle labels;
/*     */   private String lastValue;
/*     */ 
/*     */   public GdmSettings()
/*     */   {
/*  46 */      this.labels = ResourceBundle.getBundle("resources.labels");
/*     */   }
/*     */ 
/*     */   @Nls
/*     */   public String getDisplayName()
/*     */   {
/*  52 */     return this.labels.getString("genymotion");
/*     */   }
/*     */ 
/*     */   public boolean isModified()
/*     */   {
/*  57 */     String stored = PropertiesComponent.getInstance().getValue("KEY_GENYMOTION_PATH", "");
/*  58 */     if ((Strings.isNullOrEmpty(this.pathTextFieldWithButton.getText())) && (Strings.isNullOrEmpty(stored))) {
/*  59 */       return false;
/*     */     }
/*  61 */     return !this.pathTextFieldWithButton.getText().equals(stored);
/*     */   }
/*     */ 
/*     */   @Nullable
/*     */   public JComponent createComponent()
/*     */   {
/*  69 */     String platform = System.getProperty("os.name").toLowerCase();
/*     */     String selectPath;
/*     */
/*  70 */     if (Utils.isMac())
/*  71 */       selectPath = this.labels.getString("genymotion_select_path_mac");
/*     */     else {
/*  73 */       selectPath = this.labels.getString("genymotion_select_path");
/*     */     }
/*  75 */     this.labelPath.setText(selectPath);
/*     */ 
/*  77 */     this.pathTextFieldWithButton.addBrowseFolderListener(this.labels.getString("genymotion"), selectPath, null, FileChooserDescriptorFactory.createSingleFolderDescriptor());
/*     */ 
/*  83 */     this.lastValue = PropertiesComponent.getInstance().getValue("KEY_GENYMOTION_PATH", "");
/*  84 */     this.pathTextFieldWithButton.setText(this.lastValue);
/*     */ 
/*  86 */     return this.myPanel;
/*     */   }
/*     */ 
/*     */   public void apply() throws ConfigurationException
/*     */   {
/*  91 */     PropertiesComponent props = PropertiesComponent.getInstance();
/*     */ 
/*  93 */     String _path = this.pathTextFieldWithButton.getText();
/*     */ 
/*  96 */     if (VMToolsEngine.isValidGenymotionPath(_path))
/*     */     {
/*  98 */       if (!_path.equals(props.getValue("KEY_GENYMOTION_PATH")))
/*  99 */         props.setValue("KEY_GENYMOTION_PATH", _path);
/*     */     }
/*     */     else {
/* 102 */       BalloonBuilder builder = JBPopupFactory.getInstance().createHtmlTextBalloonBuilder("<b>" + this.labels.getString("genymotion_error") + "</b><br/>" + this.labels.getString("invalid_path"), null, Color.RED, null);
/*     */ 
/* 104 */       Balloon balloon = builder.createBalloon();
/* 105 */       balloon.show(RelativePoint.getCenterOf(this.pathTextFieldWithButton.getTextField()), Balloon.Position.below);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Nullable
/*     */   public String getHelpTopic()
/*     */   {
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   public void disposeUIResources()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 123 */     this.pathTextFieldWithButton.setText(this.lastValue);
/*     */   }
/*     */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.ide.idea.GdmSettings
 * JD-Core Version:    0.6.2
 */