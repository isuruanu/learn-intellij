/*     */ package com.genymobile.genymotion.lib;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class VMToolsMachine
/*     */ {
/*  21 */   public static String ANDROID_VERSION_GP = "android_version";
/*  22 */   public static String GENYMOTION_VERSION_GP = "genymotion_version";
/*     */   public String name;
/*     */   public String androidVersion;
/*     */   public String genymotionVersion;
/*     */   public Status status;
/*  30 */   private final HashMap<String, String> props = new HashMap();
/*     */   private final String vboxPath;
/*     */ 
/*     */   public VMToolsMachine(String vboxPath, String name, String uuid, IdeConsole console)
/*     */   {
/*  37 */     this.vboxPath = vboxPath;
/*     */ 
/*  39 */     console.println("Launch process: " + vboxPath + " guestproperty enumerate " + uuid);
/*     */     Process pr;
/*     */     try
/*     */     {
/*  41 */       pr = new ProcessBuilder(new String[] { vboxPath, "guestproperty", "enumerate", uuid }).start();
/*     */     }
/*     */     catch (Exception e1) {
/*  44 */       console.println("[ERROR] Process: " + vboxPath + " guestproperty enumerate " + uuid);
/*  45 */       e1.printStackTrace();
/*  46 */       return;
/*     */     }
/*     */ 
/*  49 */     BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
/*     */     try
/*     */     {
/*  52 */       Pattern expr = Pattern.compile("Name: (\\S+), value: (\\S*), timestamp:");
/*     */       String line;
/*  55 */       while ((line = reader.readLine()) != null) {
/*  56 */         Matcher m = expr.matcher(line);
/*     */ 
/*  58 */         if (m.find())
/*  59 */           this.props.put(m.group(1), m.group(2));
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/*  63 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  66 */     this.name = name;
/*  67 */     this.androidVersion = ((String)this.props.get(ANDROID_VERSION_GP));
/*  68 */     if (Utils.isNullOrEmpty(this.androidVersion)) {
/*  69 */       this.androidVersion = "4.1.1";
/*     */     }
/*  71 */     this.genymotionVersion = ((String)this.props.get(GENYMOTION_VERSION_GP));
/*  72 */     if (Utils.isNullOrEmpty(this.genymotionVersion))
/*  73 */       this.genymotionVersion = "1.0.0";
/*     */   }
/*     */ 
/*     */   public Status getStatus()
/*     */   {
/*  79 */     this.status = Status.Undefined;
/*  80 */     Process pr = null;
/*     */     try
/*     */     {
/*  83 */       pr = new ProcessBuilder(new String[] { this.vboxPath, "showvminfo", this.name }).start();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  87 */       e.printStackTrace();
/*  88 */       return this.status;
/*     */     }
/*     */ 
/*  91 */     BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
/*     */     try
/*     */     {
/*  94 */       Pattern expr = Pattern.compile("state:\\s+(.*) \\(since ");
/*     */       String line;
/*  97 */       while ((line = reader.readLine()) != null) {
/*  98 */         Matcher m = expr.matcher(line.toLowerCase());
/*     */ 
/* 100 */         if (m.find()) {
/* 101 */           String strstatus = m.group(1);
/*     */ 
/* 103 */           if (strstatus.contains("powered off")) {
/* 104 */             this.status = Status.Off; break;
/* 105 */           }if (strstatus.contains("running")) {
/* 106 */             this.status = Status.On; break;
/* 107 */           }if (strstatus.contains("paused")) {
/* 108 */             this.status = Status.Paused; break;
/* 109 */           }if (strstatus.contains("aborted")) {
/* 110 */             this.status = Status.Aborted; break;
/*     */           }
/* 112 */           this.status = Status.Process;
/*     */ 
/* 115 */           break;
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 119 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 122 */     return this.status;
/*     */   }
/*     */ 
/*     */   public static boolean isCompatible(String vboxPath, String uuid, IdeConsole console) {
/* 127 */     console.println("Checking virtual machine...");
/*     */     Process pr;
/*     */     try {
/* 130 */       console.println("Launch process: " + vboxPath + " guestproperty enumerate " + uuid);
/* 131 */       pr = new ProcessBuilder(new String[] { vboxPath, "guestproperty", "enumerate", uuid }).start();
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 135 */       console.println("[ERROR] Process: " + vboxPath + " guestproperty enumerate " + uuid);
/* 136 */       e1.printStackTrace();
/* 137 */       return false;
/*     */     }
/*     */ 
/* 140 */     BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
/*     */     try
/*     */     {
/*     */       String line;
/* 144 */       while ((line = reader.readLine()) != null)
/* 145 */         if (line.contains("vbox_graph_mode")) {
/* 146 */           console.println("[SUCCESS] " + uuid + ": Genymotion virtual device found");
/* 147 */           return true;
/*     */         }
/*     */     }
/*     */     catch (IOException e) {
/* 151 */       e.printStackTrace();
/*     */     }
/* 153 */     console.println("[ERROR] " + uuid + ": not a Genymotion virtual device");
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   public String getIPAddress() {
/* 158 */     if (this.props.containsKey("androvm_ip_management")) {
/* 159 */       return (String)this.props.get("androvm_ip_management");
/*     */     }
/* 161 */     return "";
/*     */   }
/*     */ 
/*     */   public static enum Status
/*     */   {
/*  14 */     Undefined, 
/*  15 */     On, 
/*  16 */     Off, 
/*  17 */     Paused, 
/*  18 */     Process, 
/*  19 */     Aborted;
/*     */   }
/*     */ }

/* Location:           /foss/learn-intellij/action_system/offline_resources/genymotion-idea-plugin-20140326.jar
 * Qualified Name:     com.genymobile.genymotion.lib.VMToolsMachine
 * JD-Core Version:    0.6.2
 */