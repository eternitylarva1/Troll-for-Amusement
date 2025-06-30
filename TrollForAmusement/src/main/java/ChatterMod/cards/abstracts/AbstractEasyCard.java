/*     */ package ChatterMod.cards.abstracts;
/*     */ 
/*     */ import ChatterMod.MainModfile;
/*     */ import ChatterMod.util.CardArtRoller;
/*     */ import ChatterMod.util.Wiz;
/*     */ import basemod.BaseMod;
/*     */ import basemod.abstracts.CustomCard;
/*     */ import basemod.helpers.TooltipInfo;
/*     */ import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Color;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*     */ import com.megacrit.cardcrawl.actions.common.DamageAction;
/*     */ import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
/*     */ import com.megacrit.cardcrawl.actions.common.GainBlockAction;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.cards.DamageInfo;
/*     */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*     */ import com.megacrit.cardcrawl.helpers.CardLibrary;
/*     */ import com.megacrit.cardcrawl.helpers.TipHelper;
/*     */ import com.megacrit.cardcrawl.localization.CardStrings;
/*     */ import com.megacrit.cardcrawl.localization.Keyword;
/*     */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractEasyCard
/*     */   extends CustomCard
/*     */ {
/*  38 */   protected static final Color RED = new Color(-16776961);
/*  39 */   protected static final Color SCARLET = new Color(-14679809);
/*  40 */   protected static final Color VERMILION = new Color(-12582657);
/*  41 */   protected static final Color PERSIMMON = new Color(-10485505);
/*  42 */   protected static final Color ORANGE = new Color(-8388353);
/*  43 */   protected static final Color ORANGE_PEEL = new Color(-6291201);
/*  44 */   protected static final Color AMBER = new Color(-4194049);
/*  45 */   protected static final Color GOLDEN_YELLOW = new Color(-2096897);
/*  46 */   protected static final Color YELLOW = new Color(-65281);
/*  47 */   protected static final Color LEMON = new Color(-520158977);
/*  48 */   protected static final Color LIME = new Color(-1057029889);
/*  49 */   protected static final Color SPRING_BUG = new Color(-1593900801);
/*  50 */   protected static final Color CHARTREUSE = new Color(-2130771713);
/*  51 */   protected static final Color BRIGHT_GREEN = new Color(1627324671);
/*  52 */   protected static final Color HARLEQUIN = new Color(1090453759);
/*  53 */   protected static final Color NEON_GREEN = new Color(553582847);
/*  54 */   protected static final Color GREEN = new Color(16711935);
/*  55 */   protected static final Color JADE = new Color(16720127);
/*  56 */   protected static final Color ERIN = new Color(16728319);
/*  57 */   protected static final Color EMERALD = new Color(16736511);
/*  58 */   protected static final Color SPRING_GREEN = new Color(16744703);
/*  59 */   protected static final Color MINT = new Color(16752895);
/*  60 */   protected static final Color AQUAMARINE = new Color(16761087);
/*  61 */   protected static final Color TURQUOISE = new Color(16769279);
/*  62 */   protected static final Color CYAN = new Color(16777215);
/*  63 */   protected static final Color SKY_BLUE = new Color(14745599);
/*  64 */   protected static final Color CAPRI = new Color(12648447);
/*  65 */   protected static final Color CORNFLOWER = new Color(10551295);
/*  66 */   protected static final Color AZURE = new Color(8454143);
/*  67 */   protected static final Color COBALT = new Color(6356991);
/*  68 */   protected static final Color CERULEAN = new Color(4259839);
/*  69 */   protected static final Color SAPPHIRE = new Color(2162687);
/*  70 */   protected static final Color BLUE = new Color(65535);
/*  71 */   protected static final Color IRIS = new Color(536936447);
/*  72 */   protected static final Color INDIGO = new Color(1073807359);
/*  73 */   protected static final Color VERONICA = new Color(1610678271);
/*  74 */   protected static final Color VIOLET = new Color(-2147418113);
/*  75 */   protected static final Color AMETHYST = new Color(-1610547201);
/*  76 */   protected static final Color PURPLE = new Color(-1073676289);
/*  77 */   protected static final Color PHLOX = new Color(-536805377);
/*  78 */   protected static final Color MAGENTA = new Color(-16711681);
/*  79 */   protected static final Color FUCHSIA = new Color(-16719617);
/*  80 */   protected static final Color CERISE = new Color(-16727809);
/*  81 */   protected static final Color DEEP_PINK = new Color(-16736001);
/*  82 */   protected static final Color ROSE = new Color(-16744193);
/*  83 */   protected static final Color RASPBERRY = new Color(-16752385);
/*  84 */   protected static final Color CRIMSON = new Color(-16760577);
/*  85 */   protected static final Color AMARANTH = new Color(-16768769);
/*     */   
/*  87 */   protected static final Color PLATINUM = new Color(-437984513);
/*  88 */   protected static final Color GOLD = new Color(-2686721);
/*  89 */   protected static final Color SILVER = new Color(-1061109505);
/*  90 */   protected static final Color BRONZE = new Color(-847301889);
/*     */   
/*  92 */   protected static final Color WHITE = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/*  93 */   protected static final Color LIGHT_GRAY = new Color(0.75F, 0.75F, 0.75F, 1.0F);
/*  94 */   protected static final Color GRAY = new Color(0.5F, 0.5F, 0.5F, 1.0F);
/*  95 */   protected static final Color DARK_GRAY = new Color(0.25F, 0.25F, 0.25F, 1.0F);
/*  96 */   protected static final Color BLACK = new Color(0.0F, 0.0F, 0.0F, 1.0F);
/*     */   
/*  98 */   protected static final Color TRANSPARENT = new Color(0.0F, 0.0F, 0.0F, 0.0F);
/*     */   
/*     */   private List<TooltipInfo> addedTips;
/*     */   
/*     */   protected final CardStrings cardStrings;
/* 103 */   public int secondMagic = -1;
/* 104 */   public int baseSecondMagic = -1;
/*     */   
/*     */   public boolean upgradedSecondMagic;
/*     */   public boolean isSecondMagicModified;
/* 108 */   public int secondDamage = -1;
/* 109 */   public int baseSecondDamage = -1;
/*     */   
/*     */   public boolean upgradedSecondDamage;
/*     */   public boolean isSecondDamageModified;
/* 113 */   public int secondBlock = -1;
/* 114 */   public int baseSecondBlock = -1;
/*     */   
/*     */   public boolean upgradedSecondBlock;
/*     */   public boolean isSecondBlockModified;
/* 118 */   public int info = -1;
/* 119 */   public int baseInfo = -1;
/*     */   
/*     */   public boolean upgradedInfo;
/*     */   public boolean isInfoModified;
/* 123 */   private float rotationTimer = 0.0F;
/*     */   private int previewIndex;
/* 125 */   protected ArrayList<AbstractCard> cyclePreviewCards = new ArrayList();
/*     */   
/*     */   protected boolean needsArtRefresh = false;
/*     */   protected boolean manualD2 = false;
/*     */   
/*     */   public AbstractEasyCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
/* 131 */     super(cardID, "", getCardTextureString(cardID.replace("ChatterMod:", ""), type), cost, "", type, color, rarity, target);
/*     */     
/* 133 */     this.cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
/* 134 */     this.rawDescription = this.cardStrings.DESCRIPTION;
/* 135 */     this.name = this.originalName = this.cardStrings.NAME;
/* 136 */     initializeTitle();
/* 137 */     initializeDescription();
/*     */     
/* 139 */     if (this.textureImg.contains("ui/missing.png")) {
/* 140 */       if (CardLibrary.getAllCards() != null && !CardLibrary.getAllCards().isEmpty()) {
/* 141 */         CardArtRoller.computeCard(this);
/* 142 */         if (this.cardsToPreview instanceof AbstractEasyCard) {
/* 143 */           CardArtRoller.computeCard((AbstractEasyCard)this.cardsToPreview);
/*     */         }
/*     */       } else {
/* 146 */         this.needsArtRefresh = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Texture getPortraitImage() {
/* 154 */     if (super.getPortraitImage() == null) {
/* 155 */       return CardArtRoller.getPortraitTexture(this);
/*     */     }
/* 157 */     return super.getPortraitImage();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCardTextureString(String cardName, CardType cardType) {
/*     */     String textureString;
/* 163 */     switch (cardType) {
/*     */       case ATTACK:
/*     */       case POWER:
/*     */       case SKILL:
/* 167 */         textureString = MainModfile.makeImagePath("cards/" + cardName + ".png");
/*     */         break;
/*     */       default:
/* 170 */         textureString = MainModfile.makeImagePath("ui/missing.png");
/*     */         break;
/*     */     } 
/*     */     
/* 174 */     FileHandle h = Gdx.files.internal(textureString);
/* 175 */     if (!h.exists()) {
/* 176 */       textureString = MainModfile.makeImagePath("ui/missing.png");
/*     */     }
/* 178 */     return textureString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyPowers() {
/* 183 */     if (this.baseSecondDamage > -1 && !this.manualD2)
/* 184 */     { this.secondDamage = this.baseSecondDamage;
/*     */       
/* 186 */       int tmp = this.baseDamage;
/* 187 */       this.baseDamage = this.baseSecondDamage;
/*     */       
/* 189 */       super.applyPowers();
/*     */       
/* 191 */       this.secondDamage = this.damage;
/* 192 */       this.baseDamage = tmp;
/*     */       
/* 194 */       super.applyPowers();
/*     */       
/* 196 */       this.isSecondDamageModified = (this.secondDamage != this.baseSecondDamage); }
/* 197 */     else { super.applyPowers(); }
/*     */   
/*     */   }
/*     */   
/*     */   protected void applyPowersToBlock() {
/* 202 */     if (this.baseSecondBlock > -1) {
/* 203 */       this.secondBlock = this.baseSecondBlock;
/*     */       
/* 205 */       int tmp = this.baseBlock;
/* 206 */       this.baseBlock = this.baseSecondBlock;
/*     */       
/* 208 */       super.applyPowersToBlock();
/*     */       
/* 210 */       this.secondBlock = this.block;
/* 211 */       this.baseBlock = tmp;
/*     */       
/* 213 */       super.applyPowersToBlock();
/*     */       
/* 215 */       this.isSecondBlockModified = (this.secondBlock != this.baseSecondBlock);
/*     */     } else {
/* 217 */       super.applyPowersToBlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void calculateCardDamage(AbstractMonster mo) {
/* 223 */     if (this.baseSecondDamage > -1 && !this.manualD2)
/* 224 */     { this.secondDamage = this.baseSecondDamage;
/*     */       
/* 226 */       int tmp = this.baseDamage;
/* 227 */       this.baseDamage = this.baseSecondDamage;
/*     */       
/* 229 */       super.calculateCardDamage(mo);
/*     */       
/* 231 */       this.secondDamage = this.damage;
/* 232 */       this.baseDamage = tmp;
/*     */       
/* 234 */       super.calculateCardDamage(mo);
/*     */       
/* 236 */       this.isSecondDamageModified = (this.secondDamage != this.baseSecondDamage); }
/* 237 */     else { super.calculateCardDamage(mo); }
/*     */   
/*     */   }
/*     */   public void resetAttributes() {
/* 241 */     super.resetAttributes();
/* 242 */     this.secondMagic = this.baseSecondMagic;
/* 243 */     this.isSecondMagicModified = false;
/* 244 */     this.secondDamage = this.baseSecondDamage;
/* 245 */     this.isSecondDamageModified = false;
/* 246 */     this.secondBlock = this.baseSecondBlock;
/* 247 */     this.isSecondBlockModified = false;
/* 248 */     this.info = this.baseInfo;
/* 249 */     this.isInfoModified = false;
/*     */   }
/*     */   
/*     */   public void displayUpgrades() {
/* 253 */     super.displayUpgrades();
/* 254 */     if (this.upgradedSecondMagic) {
/* 255 */       this.secondMagic = this.baseSecondMagic;
/* 256 */       this.isSecondMagicModified = true;
/*     */     } 
/* 258 */     if (this.upgradedSecondDamage) {
/* 259 */       this.secondDamage = this.baseSecondDamage;
/* 260 */       this.isSecondDamageModified = true;
/*     */     } 
/* 262 */     if (this.upgradedSecondBlock) {
/* 263 */       this.secondBlock = this.baseSecondBlock;
/* 264 */       this.isSecondBlockModified = true;
/*     */     } 
/* 266 */     if (this.upgradedInfo) {
/* 267 */       this.info = this.baseInfo;
/* 268 */       this.isInfoModified = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void upgradeSecondMagic(int amount) {
/* 273 */     this.baseSecondMagic += amount;
/* 274 */     this.secondMagic = this.baseSecondMagic;
/* 275 */     this.upgradedSecondMagic = true;
/*     */   }
/*     */   
/*     */   protected void upgradeSecondDamage(int amount) {
/* 279 */     this.baseSecondDamage += amount;
/* 280 */     this.secondDamage = this.baseSecondDamage;
/* 281 */     this.upgradedSecondDamage = true;
/*     */   }
/*     */   
/*     */   protected void upgradeSecondBlock(int amount) {
/* 285 */     this.baseSecondBlock += amount;
/* 286 */     this.secondBlock = this.baseSecondBlock;
/* 287 */     this.upgradedSecondBlock = true;
/*     */   }
/*     */   
/*     */   protected void upgradeInfo(int amount) {
/* 291 */     this.baseInfo += amount;
/* 292 */     this.info = this.baseInfo;
/* 293 */     this.upgradedInfo = true;
/*     */   }
/*     */   
/*     */   protected void uDesc() {
/* 297 */     this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
/* 298 */     initializeDescription();
/*     */   }
/*     */   
/*     */   protected void upgradeCardToPreview() {
/* 302 */     for (AbstractCard q : this.cyclePreviewCards) {
/* 303 */       q.upgrade();
/*     */     }
/*     */   }
/*     */   
/*     */   public void upgrade() {
/* 308 */     if (!this.upgraded) {
/* 309 */       upgradeName();
/* 310 */       upp();
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void upp();
/*     */   
/*     */   public void update() {
/* 317 */     super.update();
/* 318 */     if (this.needsArtRefresh) {
/* 319 */       CardArtRoller.computeCard(this);
/* 320 */       if (this.cardsToPreview instanceof AbstractEasyCard) {
/* 321 */         CardArtRoller.computeCard((AbstractEasyCard)this.cardsToPreview);
/*     */       }
/* 323 */       for (AbstractCard c : this.cyclePreviewCards) {
/* 324 */         if (c instanceof AbstractEasyCard) {
/* 325 */           CardArtRoller.computeCard((AbstractEasyCard)c);
/*     */         }
/*     */       } 
/* 328 */       for (AbstractCard c : (ArrayList<AbstractCard>)MultiCardPreview.multiCardPreview.get(this)) {
/* 329 */         if (c instanceof AbstractEasyCard) {
/* 330 */           CardArtRoller.computeCard((AbstractEasyCard)c);
/*     */         }
/*     */       } 
/*     */     } 
/* 334 */     if (!this.cyclePreviewCards.isEmpty() && 
/* 335 */       this.hb.hovered) {
/* 336 */       if (this.rotationTimer <= 0.0F) {
/* 337 */         this.rotationTimer = getRotationTimeNeeded();
/* 338 */         this.cardsToPreview = (AbstractCard)this.cyclePreviewCards.get(this.previewIndex);
/* 339 */         if (this.previewIndex == this.cyclePreviewCards.size() - 1) {
/* 340 */           this.previewIndex = 0;
/*     */         } else {
/* 342 */           this.previewIndex++;
/*     */         } 
/*     */       } else {
/* 345 */         this.rotationTimer -= Gdx.graphics.getDeltaTime();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 352 */   protected float getRotationTimeNeeded() { return 2.5F; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 357 */   protected void dmg(AbstractCreature t, AbstractGameAction.AttackEffect fx) { dmg(t, fx, false); }
/*     */ 
/*     */ 
/*     */   
/* 361 */   protected void dmg(AbstractCreature t, AbstractGameAction.AttackEffect fx, boolean fast) { Wiz.atb(new DamageAction(t, new DamageInfo(AbstractDungeon.player, this.damage, this.damageTypeForTurn), fx, fast)); }
/*     */ 
/*     */ 
/*     */   
/* 365 */   protected void dmgTop(AbstractCreature t, AbstractGameAction.AttackEffect fx) { dmgTop(t, fx, false); }
/*     */ 
/*     */ 
/*     */   
/* 369 */   protected void dmgTop(AbstractCreature t, AbstractGameAction.AttackEffect fx, boolean fast) { Wiz.att(new DamageAction(t, new DamageInfo(AbstractDungeon.player, this.damage, this.damageTypeForTurn), fx, fast)); }
/*     */ 
/*     */ 
/*     */   
/* 373 */   protected void allDmg(AbstractGameAction.AttackEffect fx) { allDmg(fx, false); }
/*     */ 
/*     */ 
/*     */   
/* 377 */   protected void allDmg(AbstractGameAction.AttackEffect fx, boolean fast) { Wiz.atb(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, fx, fast)); }
/*     */ 
/*     */ 
/*     */   
/* 381 */   protected void blck() { Wiz.atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block)); }
/*     */ 
/*     */ 
/*     */   
/* 385 */   public String cardArtCopy() { return null; }
/*     */ 
/*     */ 
/*     */   
/* 389 */   public CardArtRoller.ReskinInfo reskinInfo(String ID) { return new CardArtRoller.ReskinInfo(ID, 0.5F, 0.5F, 0.5F, 0.5F, false); }
/*     */ 
/*     */ 
/*     */   
/* 393 */   public String itemArt() { return ""; }
/*     */ 
/*     */ 
/*     */   
/* 397 */   public String rollerKey() { return this.cardID; }
/*     */ 
/*     */ 
/*     */   
/* 401 */   public float itemScale() { return 1.0F; }
/*     */ 
/*     */ 
/*     */   
/* 405 */   public static Color mix(Color c1, Color c2) { return c1.cpy().lerp(c2, 0.5F); }
/*     */ 
/*     */ 
/*     */   
/* 409 */   public static Color lighten(Color c) { return c.cpy().lerp(Color.WHITE, 0.25F); }
/*     */ 
/*     */ 
/*     */   
/* 413 */   public static Color darken(Color c) { return c.cpy().lerp(Color.BLACK, 0.25F); }
/*     */ 
/*     */ 
/*     */   
/* 417 */   public static Color pastel(Color c) { return colorFromHSL(getHue(c), getSat(c), 0.8F, c.a); }
/*     */ 
/*     */   
/*     */   private static float getHue(Color c) {
/* 421 */     float max = c.r;
/* 422 */     float min = c.r;
/* 423 */     if (c.g > max) {
/* 424 */       max = c.g;
/*     */     }
/* 426 */     if (c.b > max) {
/* 427 */       max = c.b;
/*     */     }
/* 429 */     if (c.g < min) {
/* 430 */       min = c.g;
/*     */     }
/* 432 */     if (c.b < min) {
/* 433 */       min = c.b;
/*     */     }
/* 435 */     float delta = max - min;
/* 436 */     if (delta == 0.0F) {
/* 437 */       return 0.0F;
/*     */     }
/* 439 */     if (c.g >= c.b) {
/* 440 */       return (float)Math.toDegrees(Math.acos((c.r - c.g / 2.0F - c.b / 2.0F) / Math.sqrt((c.r * c.r + c.g * c.g + c.b * c.b - c.r * c.g - c.r * c.b - c.g * c.b))));
/*     */     }
/* 442 */     return 360.0F - (float)Math.toDegrees(Math.acos((c.r - c.g / 2.0F - c.b / 2.0F) / Math.sqrt((c.r * c.r + c.g * c.g + c.b * c.b - c.r * c.g - c.r * c.b - c.g * c.b))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static float getSat(Color c) {
/* 447 */     float max = c.r;
/* 448 */     float min = c.r;
/* 449 */     if (c.g > max) {
/* 450 */       max = c.g;
/*     */     }
/* 452 */     if (c.b > max) {
/* 453 */       max = c.b;
/*     */     }
/* 455 */     if (c.g < min) {
/* 456 */       min = c.g;
/*     */     }
/* 458 */     if (c.b < min) {
/* 459 */       min = c.b;
/*     */     }
/* 461 */     float delta = max - min;
/* 462 */     if (delta == 0.0F) {
/* 463 */       return 0.0F;
/*     */     }
/* 465 */     float lightness = (max + min) / 2.0F;
/* 466 */     return delta / (1.0F - Math.abs(2.0F * lightness - 1.0F));
/*     */   }
/*     */   
/*     */   public static Color colorFromHSL(float hue, float sat, float light, float alpha) {
/* 470 */     float d = sat * (1.0F - Math.abs(2.0F * light - 1.0F));
/* 471 */     float x = d * (1.0F - Math.abs(hue / 60.0F % 2.0F - 1.0F));
/* 472 */     float m = light - d / 2.0F;
/* 473 */     if (0.0F <= hue && hue < 60.0F)
/* 474 */       return new Color(d + m, x + m, m, alpha); 
/* 475 */     if (60.0F <= hue && hue < 120.0F)
/* 476 */       return new Color(x + m, d + m, m, alpha); 
/* 477 */     if (120.0F <= hue && hue < 180.0F)
/* 478 */       return new Color(m, d + m, x + m, alpha); 
/* 479 */     if (180.0F <= hue && hue < 240.0F)
/* 480 */       return new Color(m, x + m, d + m, alpha); 
/* 481 */     if (240.0F <= hue && hue < 300.0F) {
/* 482 */       return new Color(x + m, m, d + m, alpha);
/*     */     }
/* 484 */     return new Color(d + m, m, x + m, alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addCustomKeyword(String key) {
/* 489 */     if (this.addedTips == null) {
/* 490 */       this.addedTips = new ArrayList();
/*     */     }
/* 492 */     this.addedTips.add(new TooltipInfo(BaseMod.getKeywordTitle(key), BaseMod.getKeywordDescription(key)));
/*     */   }
/*     */   
/*     */   protected void addVanillaKeyword(Keyword key) {
/* 496 */     if (this.addedTips == null) {
/* 497 */       this.addedTips = new ArrayList();
/*     */     }
/* 499 */     this.addedTips.add(new TooltipInfo(TipHelper.capitalize(key.NAMES[0]), key.DESCRIPTION));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TooltipInfo> getCustomTooltips() {
/* 504 */     if (this.addedTips != null) {
/* 505 */       return this.addedTips;
/*     */     }
/* 507 */     return super.getCustomTooltips();
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMod\cards\abstracts\AbstractEasyCard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */