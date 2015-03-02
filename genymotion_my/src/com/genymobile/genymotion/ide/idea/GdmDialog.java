/*     */ package com.genymobile.genymotion.ide.idea;
/*     */ 
/*     */ import com.genymobile.genymotion.lib.IdeConsole;
/*     */ import com.genymobile.genymotion.lib.VMToolsEngine;
/*     */ import com.genymobile.genymotion.lib.VMToolsMachine;
/*     */ import com.genymobile.genymotion.lib.VMToolsMachine.Status;
/*     */ import com.intellij.openapi.application.Application;
/*     */ import com.intellij.openapi.application.ApplicationManager;
/*     */ import com.intellij.openapi.project.Project;
/*     */ import com.intellij.openapi.ui.DialogWrapper;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public class GdmDialog extends DialogWrapper
/*     */ {
/*     */   private JPanel gdmPanel;
/*     */   private JButton startButton;
/*     */   private JTable devicesTable;
/*     */   private JButton refreshButton;
/*     */   private JButton newButton;
/*     */   private JLabel status;
/*     */   private final ResourceBundle labels;
/*     */   private final String[] titles;
/*     */   final IdeConsole console;
/*     */   final VMToolsEngine engine;
/*     */   VMToolsMachine[] devices;
/*     */ 
/*     */   public GdmDialog(@NotNull VMToolsEngine p_engine, @NotNull IdeConsole p_console, @NotNull Project p_project)
/*     */   {
/*  53 */     super(p_project, true);
/*     */

/*  39 */     this.labels = ResourceBundle.getBundle("resources.labels");
/*     */ 
/*  41 */     this.titles = new String[] { this.labels.getString("name"), this.labels.getString("aosp_version"), this.labels.getString("genymotion_version"), this.labels.getString("ip_address"), this.labels.getString("state") };
/*     */ 
/*  50 */     this.devices = null;
/*     */ 
/*  54 */     this.console = p_console;
/*  55 */     this.engine = p_engine;
/*  56 */     this.devices = this.engine.getMachines();
/*     */ 
/*  58 */     setTitle(this.labels.getString("genymotion_device_manager"));
/*  59 */     setResizable(true);
/*     */ 
/*  61 */     this.startButton.setEnabled(false);
/*     */ 
/*  63 */     this.status.setText(" ");
/*     */ 
/*  65 */     this.devicesTable.setShowVerticalLines(true);
/*  66 */     ListSelectionModel selectionModel = this.devicesTable.getSelectionModel();
/*  67 */     selectionModel.setSelectionMode(0);
/*  68 */     selectionModel.addListSelectionListener(new ListSelectionListener()
/*     */     {
/*     */       public void valueChanged(ListSelectionEvent e) {
/*  71 */         GdmDialog.this.updateStartButton();
/*     */       }
/*     */     });
/*  74 */     updateVirtualDevicesList();
/*     */ 
/*  76 */     this.newButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*  79 */         EventQueue.invokeLater(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try {
/*  84 */               new ProcessBuilder(new String[0]).command(new String[] { GdmDialog.this.engine.getGenymotionPath() + "/genymotion", "--open-wizard" }).start();
/*     */             }
/*     */             catch (IOException ioe) {
/*  87 */               GdmDialog.this.console.println("Run Genymotion wizard: failed: " + ioe.toString());
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */     });
/*  94 */     this.startButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*  97 */         int row = GdmDialog.this.devicesTable.getSelectedRow();
/*  98 */         if (row != -1) {
/*  99 */           final String deviceName = GdmDialog.this.devices[row].name;
/* 100 */           GdmDialog.this.console.println("Starting device " + deviceName);
/* 101 */           GdmDialog.this.status.setText("Starting device " + deviceName + ". Please wait...");
/*     */ 
/* 103 */           EventQueue.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 106 */               GdmDialog.this.engine.startMachine(deviceName);
/*     */ 
/* 108 */               ApplicationManager.getApplication().invokeLater(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 111 */
/*     */                 }
/*     */               });
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */     });
/* 120 */     this.refreshButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 123 */         GdmDialog.this.status.setText("Refreshing list of devices. Please wait...");
/* 124 */         GdmDialog.this.console.println("Refreshing list...");
/*     */ 
/* 126 */         EventQueue.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 129 */             GdmDialog.this.devices = GdmDialog.this.engine.getMachines();
/*     */ 
/* 131 */             ApplicationManager.getApplication().invokeLater(new Runnable()
/*     */             {
/*     */               public void run() {
/* 134 */                 GdmDialog.this.updateVirtualDevicesList();
/* 135 */                 GdmDialog.this.updateStartButton();
/* 136 */                 GdmDialog.this.status.setText(" ");
/* 137 */                 GdmDialog.this.console.println("Refresh: success");
/*     */               }
/*     */             });
/*     */           }
/*     */         });
/*     */       }
/*     */     });
/* 145 */     init();
/*     */   }
/*     */ 
/*     */   @NotNull
/*     */   protected Action[] createActions()
/*     */   {
/* 152 */     Action[] tmp4_1 = new Action[0]; if (tmp4_1 == null) throw new IllegalStateException("@NotNull method com/genymobile/genymotion/ide/idea/GdmDialog.createActions must not return null"); return tmp4_1;
/*     */   }
/*     */ 
/*     */   @Nullable
/*     */   protected JComponent createCenterPanel()
/*     */   {
/* 158 */     return this.gdmPanel;
/*     */   }
/*     */ 
/*     */   private void updateVirtualDevicesList()
/*     */   {
/* 166 */     if (this.devices == null) {
/* 167 */       this.console.println("[Error] Genymotion plugin has been unable to retrieve list of virtual devicesTable");
/* 168 */       return;
/*     */     }
/* 170 */     TableModel model = new GdmTableModel(this.devices, this.titles);
/* 171 */     this.devicesTable.setModel(model);
/*     */   }
/*     */ 
/*     */   private void updateStartButton()
/*     */   {
/* 179 */     int idDevice = this.devicesTable.getSelectedRow();
/*     */ 
/* 181 */     if (idDevice < 0) {
/* 182 */       this.startButton.setEnabled(false);
/*     */     } else {
/* 184 */       boolean isRunning = this.devices[idDevice].status == VMToolsMachine.Status.On;
/* 185 */       this.startButton.setEnabled(!isRunning);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class GdmTableModel extends AbstractTableModel
/*     */   {
/*     */     VMToolsMachine[] devices;
/*     */     String[] columnNames;
/*     */ 
/*     */     public GdmTableModel(VMToolsMachine[] devices, String[] columnNames) {
/* 196 */       this.devices = devices;
/* 197 */       this.columnNames = columnNames;
/*     */     }
/*     */ 
/*     */     public String getColumnName(int column)
/*     */     {
/* 202 */       return this.columnNames[column];
/*     */     }
/*     */ 
/*     */     public int getColumnCount()
/*     */     {
/* 207 */       return GdmDialog.this.titles.length;
/*     */     }
/*     */ 
/*     */     public int getRowCount()
/*     */     {
/* 212 */       return this.devices.length;
/*     */     }
/*     */ 
/*     */     public Object getValueAt(int rowIndex, int columnIndex)
/*     */     {
/* 217 */       switch (columnIndex) {
/*     */       case 0:
/* 219 */         return this.devices[rowIndex].name;
/*     */       case 1:
/* 221 */         return this.devices[rowIndex].androidVersion;
/*     */       case 2:
/* 223 */         return this.devices[rowIndex].genymotionVersion;
/*     */       case 3:
/* 225 */         return this.devices[rowIndex].getIPAddress();
/*     */       case 4:
/* 227 */         return this.devices[rowIndex].getStatus();
/*     */       }
/* 229 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.ide.idea.GdmDialog
 * JD-Core Version:    0.6.2
 */