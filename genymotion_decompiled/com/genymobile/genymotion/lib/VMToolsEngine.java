/*     */ package com.genymobile.genymotion.lib;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class VMToolsEngine
/*     */ {
/*     */   public final String genymotionFolder;
/*     */   private String vboxPath;
/*  47 */   private static boolean isInit = false;
/*     */   private IdeConsole console;
/*     */ 
/*     */   public VMToolsEngine(String genymotionFolder, IdeConsole ideConsole)
/*     */   {
/*  53 */     this.genymotionFolder = (genymotionFolder + File.separator);
/*  54 */     this.vboxPath = "VBoxManage";
/*  55 */     this.console = ideConsole;
/*  56 */     this.console.println("Genymotion directory: " + genymotionFolder);
/*     */   }
/*     */ 
/*     */   public boolean initialize() {
/*  60 */     if (isInit) {
/*  61 */       return true;
/*     */     }
/*     */ 
/*  64 */     Process pr = null;
/*     */     try {
/*  66 */       pr = new ProcessBuilder(new String[0]).command(new String[] { this.vboxPath, "--version" }).start();
/*  67 */       this.console.println("Launch process: " + this.vboxPath + " --version");
/*     */     } catch (IOException e) {
/*  69 */       if (Utils.isNotWin())
/*     */       {
/*  71 */         return false;
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  76 */         Process pr2 = new ProcessBuilder(new String[0]).command(new String[] { this.genymotionFolder + "/reg" }).start();
/*  77 */         this.console.println("Launch process: " + this.genymotionFolder + "/reg.exe");
/*  78 */         pr2.waitFor();
/*     */ 
/*  80 */         if (pr2.exitValue() == 0) {
/*  81 */           BufferedReader reader = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
/*     */           String line;
/*  84 */           if ((line = reader.readLine()) != null) {
/*  85 */             this.vboxPath = (line.trim() + File.separator + "VBoxManage.exe");
/*  86 */             pr = new ProcessBuilder(new String[0]).command(new String[] { this.vboxPath, "--version" }).start();
/*  87 */             this.console.println("Launch process: " + this.vboxPath + " --version");
/*     */           }
/*     */         }
/*     */       } catch (Exception e2) {
/*  91 */         this.console.println("Exception (" + this.vboxPath + "): " + e2.toString());
/*  92 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  98 */       pr.waitFor();
/*     */     } catch (InterruptedException e) {
/* 100 */       e.printStackTrace();
/* 101 */       return false;
/*     */     }
/*     */ 
/* 104 */     if (pr.exitValue() == 0) {
/* 105 */       isInit = true;
/* 106 */       return true;
/*     */     }
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isInit()
/*     */   {
/* 113 */     return isInit;
/*     */   }
/*     */ 
/*     */   public boolean shutdown() {
/* 117 */     return true;
/*     */   }
/*     */ 
/* 121 */   public VMToolsMachine[] getMachines() { ArrayList list = new ArrayList();
/* 122 */     VMToolsMachine[] type = new VMToolsMachine[0];
/*     */     Process pr;
/*     */     try {
/* 126 */       this.console.println("Launch process: " + this.vboxPath + " list vms");
/* 127 */       pr = new ProcessBuilder(new String[0]).command(new String[] { this.vboxPath, "list", "vms" }).start();
/*     */     }
/*     */     catch (Exception e) {
/* 130 */       this.console.println("[ERROR] Process: " + this.vboxPath + " list vms");
/* 131 */       e.printStackTrace();
/* 132 */       return null;
/*     */     }
/*     */ 
/* 135 */     this.console.println("[SUCCESS] Process: " + this.vboxPath + " list vms" + "\n");
/* 136 */     BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
/*     */     try
/*     */     {
/* 140 */       Pattern expr = Pattern.compile("^\"(.+)\" \\{(.+)\\}$");
/* 141 */       this.console.println("Listing virtual machines and found Genymotion virtual device...\n");
/*     */       String line;
/* 143 */       while ((line = reader.readLine()) != null) {
/* 144 */         Matcher m = expr.matcher(line);
/*     */ 
/* 146 */         if (m.find()) {
/* 147 */           this.console.println("Virtual machine found: " + m.group(1) + " [" + m.group(2) + "]");
/* 148 */           if (VMToolsMachine.isCompatible(this.vboxPath, m.group(2), this.console)) {
/* 149 */             this.console.println("Trying to add device to main list: " + m.group(1));
/* 150 */             list.add(new VMToolsMachine(this.vboxPath, m.group(1), m.group(2), this.console));
/* 151 */             this.console.println("Device added to main list: " + m.group(1) + "\n");
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (IOException e) {
/* 156 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 159 */     return (VMToolsMachine[])list.toArray(type);
/*     */   }
/*     */ 
/*     */   public boolean startMachine(String name)
/*     */   {
/* 164 */     return startMachine(name, this.genymotionFolder);
/*     */   }
/*     */ 
/*     */   public boolean startMachine(String name, String path)
/*     */   {
/*     */     try {
/* 170 */       Process pr = new ProcessBuilder(new String[0]).command(new String[] { path + "/player", "--vm-name", name }).directory(new File(path)).start();
/*     */ 
/* 178 */       StreamGobbler outputGobbler = new StreamGobbler(pr.getInputStream());
/*     */ 
/* 180 */       StreamGobbler errorGobbler = new StreamGobbler(pr.getErrorStream());
/* 181 */       outputGobbler.start();
/* 182 */       errorGobbler.start();
/*     */     }
/*     */     catch (Exception e) {
/* 185 */       return false;
/*     */     }
/*     */ 
/* 188 */     return true;
/*     */   }
/*     */ 
/*     */   public String getGenymotionPath()
/*     */   {
/* 193 */     return this.genymotionFolder;
/*     */   }
/*     */ 
/*     */   public static boolean isValidGenymotionPath(String path)
/*     */   {
/* 206 */     if (Utils.isNullOrEmpty(path)) {
/* 207 */       return false;
/*     */     }
/*     */ 
/* 211 */     if (Utils.isMac()) {
/* 212 */       path = path + File.separator + "Contents" + File.separator + "MacOS";
/*     */     }
/*     */ 
/* 215 */     File folder = new File(path);
/* 216 */     if ((folder.exists()) && (folder.isDirectory())) {
/* 217 */       String playerBinaryName = Utils.isWin() ? "player.exe" : "player";
/* 218 */       File player = new File(folder, playerBinaryName);
/* 219 */       if (player.exists()) {
/* 220 */         return true;
/*     */       }
/*     */     }
/* 223 */     return false;
/*     */   }
/*     */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.lib.VMToolsEngine
 * JD-Core Version:    0.6.2
 */