/*     */ package ChatterMod;
/*     */ 
/*     */ import ChatterMod.util.TexLoader;
/*     */ import basemod.AutoAdd;
/*     */ import basemod.BaseMod;
/*     */ import basemod.ModPanel;
/*     */ import basemod.interfaces.EditCardsSubscriber;
/*     */ import basemod.interfaces.EditStringsSubscriber;
/*     */ import basemod.interfaces.PostInitializeSubscriber;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*     */ import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ import com.megacrit.cardcrawl.core.Settings;
/*     */ import com.megacrit.cardcrawl.localization.UIStrings;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 

/*     */ public class MainModfile
/*     */   implements EditCardsSubscriber, EditStringsSubscriber, PostInitializeSubscriber
/*     */ {
/*     */   public static final String modID = "ChatterMod";
/*  28 */   public static final Logger logger = LogManager.getLogger(MainModfile.class.getName());
/*     */   public static final String BADGE_IMAGE = "ChatterModResources/images/Badge.png";
/*     */   
/*  31 */   public static String makeID(String idText) { return "ChatterMod:" + idText; }
/*     */ 
/*     */   
/*     */   public static UIStrings uiStrings;
/*     */   
/*     */   public static String[] TEXT;
/*     */   
/*     */   public static String[] EXTRA_TEXT;
/*     */   
/*     */   private static final String AUTHOR = "Mistress Autumn";
/*     */   
/*  42 */   public MainModfile() { BaseMod.subscribe(this); }
/*     */ 
/*     */ 
/*     */   
/*  46 */   public static String makePath(String resourcePath) { return "ChatterModResources/" + resourcePath; }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static String makeImagePath(String resourcePath) { return "ChatterModResources/images/" + resourcePath; }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static String makeRelicPath(String resourcePath) { return "ChatterModResources/images/relics/" + resourcePath; }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static String makePowerPath(String resourcePath) { return "ChatterModResources/images/powers/" + resourcePath; }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static String makeCardPath(String resourcePath) { return "ChatterModResources/images/cards/" + resourcePath; }
/*     */ 
/*     */ 
/*     */   
/*  6
/*     */   
/*  71 */   public void receiveEditCards() { (new AutoAdd("ChatterMod"))
/*  72 */       .packageFilter("ChatterMod.cards")
/*  73 */       .setDefaultSeen(true)
/*  74 */       .cards(); }
/*     */ 
/*     */   
/*     */   private void loadLangStrings(String language) {
/*  78 */     String path = "ChatterModResources/localization/" + language + "/";
/*     */     
/*  80 */     tryLoadStringsFile(com.megacrit.cardcrawl.localization.CardStrings.class, path + "Cardstrings.json");
/*  81 */     tryLoadStringsFile(UIStrings.class, path + "UIstrings.json");
/*     */   }
/*     */   
/*     */   private void tryLoadStringsFile(Class<?> stringType, String filepath) {
/*     */     try {
/*  86 */       BaseMod.loadCustomStringsFile(stringType, filepath);
/*  87 */     } catch (GdxRuntimeException e) {
/*     */       
/*  89 */       if (!e.getMessage().startsWith("File not found:")) {
/*  90 */         throw e;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveEditStrings() {
/*  97 */     String language = Settings.language.name().toLowerCase();
/*  98 */     loadLangStrings("eng");
/*  99 */     if (!language.equals("eng")) {
/* 100 */       loadLangStrings(language);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void receivePostInitialize() {
/* 106 */     uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ModConfigs"));
/* 107 */     EXTRA_TEXT = uiStrings.EXTRA_TEXT;
/* 108 */     TEXT = uiStrings.TEXT;
/*     */     
/* 110 */     ModPanel settingsPanel = new ModPanel();
/*     */ 
/*     */     
/* 113 */     Texture badgeTexture = TexLoader.getTexture("ChatterModResources/images/Badge.png");
/* 114 */     BaseMod.registerModBadge(badgeTexture, EXTRA_TEXT[0], "Mistress Autumn", EXTRA_TEXT[1], settingsPanel);
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMod\MainModfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */