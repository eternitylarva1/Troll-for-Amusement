/*     */ package ChatterMod.util;
/*     */ 
/*     */ import ChatterMod.MainModfile;
/*     */ import ChatterMod.cards.abstracts.AbstractEasyCard;
/*     */ import basemod.patches.whatmod.WhatMod;
/*     */ import com.badlogic.gdx.graphics.Color;
/*     */ import com.badlogic.gdx.graphics.OrthographicCamera;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.badlogic.gdx.graphics.glutils.FrameBuffer;
/*     */ import com.badlogic.gdx.graphics.glutils.ShaderProgram;
/*     */ import com.badlogic.gdx.math.MathUtils;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.helpers.CardLibrary;
/*     */ import com.megacrit.cardcrawl.random.Random;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CardArtRoller
/*     */ {
/*  34 */   private static final Texture attackMask = TexLoader.getTexture(MainModfile.makeImagePath("masks/AttackMask.png"));
/*  35 */   private static final Texture skillMask = TexLoader.getTexture(MainModfile.makeImagePath("masks/SkillMask.png"));
/*  36 */   private static final Texture powerMask = TexLoader.getTexture(MainModfile.makeImagePath("masks/PowerMask.png"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String partialHueRodrigues = "vec3 applyHue(vec3 rgb, float hue)\n{\n    vec3 k = vec3(0.57735);\n    float c = cos(hue);\n    //Rodrigues' rotation formula\n    return rgb * c + cross(k, rgb) * sin(hue) + k * dot(k, rgb) * (1.0 - c);\n}\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String vertexShaderHSLC = "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nvarying float v_lightFix;\n\nvoid main()\n{\n   v_color = a_color;\n   v_texCoords = a_texCoord0;\n   v_color.a = pow(v_color.a * (255.0/254.0) + 0.5, 1.709);\n   v_lightFix = 1.0 + pow(v_color.a, 1.41421356);\n   gl_Position =  u_projTrans * a_position;\n}\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String fragmentShaderHSLC = "#ifdef GL_ES\n#define LOWP lowp\nprecision mediump float;\n#else\n#define LOWP \n#endif\nvarying vec2 v_texCoords;\nvarying float v_lightFix;\nvarying LOWP vec4 v_color;\nuniform sampler2D u_texture;\nvec3 applyHue(vec3 rgb, float hue)\n{\n    vec3 k = vec3(0.57735);\n    float c = cos(hue);\n    //Rodrigues' rotation formula\n    return rgb * c + cross(k, rgb) * sin(hue) + k * dot(k, rgb) * (1.0 - c);\n}\nvoid main()\n{\n    float hue = 6.2831853 * (v_color.x - 0.5);\n    float saturation = v_color.y * 2.0;\n    float brightness = v_color.z - 0.5;\n    vec4 tgt = texture2D( u_texture, v_texCoords );\n    tgt.rgb = applyHue(tgt.rgb, hue);\n    tgt.rgb = vec3(\n     (0.5 * pow(dot(tgt.rgb, vec3(0.375, 0.5, 0.125)), v_color.w) * v_lightFix + brightness),\n     ((tgt.r - tgt.b) * saturation),\n     ((tgt.g - tgt.b) * saturation));\n    gl_FragColor = clamp(vec4(\n     dot(tgt.rgb, vec3(1.0, 0.625, -0.5)),\n     dot(tgt.rgb, vec3(1.0, -0.375, 0.5)),\n     dot(tgt.rgb, vec3(1.0, -0.375, -0.5)),\n     tgt.a), 0.0, 1.0);\n}";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String vertexBicolorShader = "#version 330\n\nuniform mat4 u_projTrans;\n\nin vec4 a_position;\nin vec2 a_texCoord0;\nin vec4 a_color;\n\nout vec4 v_color;\nout vec2 v_texCoord;\n\nvoid main() {\n    gl_Position = u_projTrans * a_position;\n    v_color = a_color;\n    v_texCoord = a_texCoord0;\n}";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String fragmentBicolorShader = "const float SQRT = 1.73205;\n\nvarying vec2 v_texCoord;\n\nuniform float lRed;\nuniform float lGreen;\nuniform float lBlue;\nuniform float rRed;\nuniform float rGreen;\nuniform float rBlue;\nuniform float anchorAR;\nuniform float anchorAG;\nuniform float anchorAB;\nuniform float anchorBR;\nuniform float anchorBG;\nuniform float anchorBB;\n\nuniform sampler2D u_texture;\nuniform vec2 u_screenSize;\n\nvoid main() {\n\tvec4 color = texture2D(u_texture, v_texCoord);\n\n    vec3 T = vec3(color.r,color.g,color.b);\n    vec3 aA = vec3(anchorAR,anchorAG,anchorAB);\n    vec3 aB = vec3(anchorBR,anchorBG,anchorBB);\n\n    float lT = length(T);\n\n    float distA = 0.2126*abs(aA.r - T.r) + 0.7152*abs(aA.g - T.g) + 0.0722*abs(aA.b - T.b);\n    float distB = 0.2126*abs(aB.r - T.r) + 0.7152*abs(aB.g - T.g) + 0.0722*abs(aB.b - T.b);\n\n    float vT = distA/(distB+distA);\n\n    float newR = lRed + (rRed - lRed)*vT;\n    float newG = lGreen + (rGreen - lGreen)*vT;\n    float newB = lBlue + (rBlue - lBlue)*vT;\n\n    vec3 newColor = vec3(newR,newG,newB)*lT;\n\n    gl_FragColor = vec4(newColor,color.a);\n}";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   private static HashMap<String, TextureAtlas.AtlasRegion> doneCards = new HashMap();
/* 154 */   public static HashMap<String, ReskinInfo> infos = new HashMap();
/* 155 */   private static ShaderProgram shade = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nvarying float v_lightFix;\n\nvoid main()\n{\n   v_color = a_color;\n   v_texCoords = a_texCoord0;\n   v_color.a = pow(v_color.a * (255.0/254.0) + 0.5, 1.709);\n   v_lightFix = 1.0 + pow(v_color.a, 1.41421356);\n   gl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n#define LOWP lowp\nprecision mediump float;\n#else\n#define LOWP \n#endif\nvarying vec2 v_texCoords;\nvarying float v_lightFix;\nvarying LOWP vec4 v_color;\nuniform sampler2D u_texture;\nvec3 applyHue(vec3 rgb, float hue)\n{\n    vec3 k = vec3(0.57735);\n    float c = cos(hue);\n    //Rodrigues' rotation formula\n    return rgb * c + cross(k, rgb) * sin(hue) + k * dot(k, rgb) * (1.0 - c);\n}\nvoid main()\n{\n    float hue = 6.2831853 * (v_color.x - 0.5);\n    float saturation = v_color.y * 2.0;\n    float brightness = v_color.z - 0.5;\n    vec4 tgt = texture2D( u_texture, v_texCoords );\n    tgt.rgb = applyHue(tgt.rgb, hue);\n    tgt.rgb = vec3(\n     (0.5 * pow(dot(tgt.rgb, vec3(0.375, 0.5, 0.125)), v_color.w) * v_lightFix + brightness),\n     ((tgt.r - tgt.b) * saturation),\n     ((tgt.g - tgt.b) * saturation));\n    gl_FragColor = clamp(vec4(\n     dot(tgt.rgb, vec3(1.0, 0.625, -0.5)),\n     dot(tgt.rgb, vec3(1.0, -0.375, 0.5)),\n     dot(tgt.rgb, vec3(1.0, -0.375, -0.5)),\n     tgt.a), 0.0, 1.0);\n}");
/* 156 */   private static ShaderProgram bicolorShader = new ShaderProgram("#version 330\n\nuniform mat4 u_projTrans;\n\nin vec4 a_position;\nin vec2 a_texCoord0;\nin vec4 a_color;\n\nout vec4 v_color;\nout vec2 v_texCoord;\n\nvoid main() {\n    gl_Position = u_projTrans * a_position;\n    v_color = a_color;\n    v_texCoord = a_texCoord0;\n}", "const float SQRT = 1.73205;\n\nvarying vec2 v_texCoord;\n\nuniform float lRed;\nuniform float lGreen;\nuniform float lBlue;\nuniform float rRed;\nuniform float rGreen;\nuniform float rBlue;\nuniform float anchorAR;\nuniform float anchorAG;\nuniform float anchorAB;\nuniform float anchorBR;\nuniform float anchorBG;\nuniform float anchorBB;\n\nuniform sampler2D u_texture;\nuniform vec2 u_screenSize;\n\nvoid main() {\n\tvec4 color = texture2D(u_texture, v_texCoord);\n\n    vec3 T = vec3(color.r,color.g,color.b);\n    vec3 aA = vec3(anchorAR,anchorAG,anchorAB);\n    vec3 aB = vec3(anchorBR,anchorBG,anchorBB);\n\n    float lT = length(T);\n\n    float distA = 0.2126*abs(aA.r - T.r) + 0.7152*abs(aA.g - T.g) + 0.0722*abs(aA.b - T.b);\n    float distB = 0.2126*abs(aB.r - T.r) + 0.7152*abs(aB.g - T.g) + 0.0722*abs(aB.b - T.b);\n\n    float vT = distA/(distB+distA);\n\n    float newR = lRed + (rRed - lRed)*vT;\n    float newG = lGreen + (rGreen - lGreen)*vT;\n    float newB = lBlue + (rBlue - lBlue)*vT;\n\n    vec3 newColor = vec3(newR,newG,newB)*lT;\n\n    gl_FragColor = vec4(newColor,color.a);\n}");
/* 157 */   private static String[] strikes = { "Strike_R", "Strike_B", "Strike_G", "Strike_P" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   private static String[] defends = { "Defend_R", "Defend_B", "Defend_G", "Defend_P" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   private static ArrayList<String> possAttacks = new ArrayList();
/* 170 */   private static ArrayList<String> openAttacks = new ArrayList();
/* 171 */   private static ArrayList<String> doneAttacks = new ArrayList();
/* 172 */   private static ArrayList<String> possSkills = new ArrayList();
/* 173 */   private static ArrayList<String> openSkills = new ArrayList();
/* 174 */   private static ArrayList<String> doneSkills = new ArrayList();
/* 175 */   private static ArrayList<String> possPowers = new ArrayList();
/* 176 */   private static ArrayList<String> openPowers = new ArrayList();
/* 177 */   private static ArrayList<String> donePowers = new ArrayList();
/* 178 */   private static CardLibrary.LibraryType[] basicColors = { CardLibrary.LibraryType.RED, CardLibrary.LibraryType.GREEN, CardLibrary.LibraryType.BLUE, CardLibrary.LibraryType.PURPLE, CardLibrary.LibraryType.COLORLESS, CardLibrary.LibraryType.CURSE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void computeCard(AbstractEasyCard c) {
/* 188 */     c.portrait = (TextureAtlas.AtlasRegion)doneCards.computeIfAbsent(c.rollerKey(), key -> {
/* 189 */           ReskinInfo r = (ReskinInfo)infos.computeIfAbsent(key,k -> new ReskinInfo(c.cardID,0,0,0,0,false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 271 */           Color HSLC = new Color(r.H, r.S, r.L, r.C);
/* 272 */           Texture itemTex = null;
/* 273 */           if (!c.itemArt().isEmpty()) {
/* 274 */             itemTex = TexLoader.getTexture(MainModfile.makeImagePath("items/" + c.itemArt() + ".png"));
/* 275 */             if (c.itemScale() != 1.0F) {
/* 276 */               itemTex = TextureScaler.rescale(itemTex, c.itemScale());
/*     */             }
/*     */           } 
/* 279 */           AbstractCard artCard = CardLibrary.getCard(r.origCardID);
/* 280 */           TextureAtlas.AtlasRegion t = artCard.portrait;
/* 281 */           t.flip(r.flipX, true);
/* 282 */           FrameBuffer fb = ImageHelper.createBuffer(250, 190);
/* 283 */           OrthographicCamera og = new OrthographicCamera(250.0F, 190.0F);
/* 284 */           if (needsMask(c, artCard)) {
/* 285 */             if (artCard.type == AbstractCard.CardType.ATTACK) {
/* 286 */               if (c.type == AbstractCard.CardType.POWER) {
/*     */                 
/* 288 */                 og.zoom = 0.976F;
/* 289 */                 og.translate(-3.0F, 0.0F);
/*     */               } else {
/*     */                 
/* 292 */                 og.zoom = 0.9F;
/* 293 */                 og.translate(0.0F, -10.0F);
/*     */               } 
/* 295 */             } else if (artCard.type == AbstractCard.CardType.POWER) {
/* 296 */               if (c.type == AbstractCard.CardType.ATTACK) {
/*     */                 
/* 298 */                 og.zoom = 0.9F;
/* 299 */                 og.translate(0.0F, -10.0F);
/*     */               } else {
/*     */                 
/* 302 */                 og.zoom = 0.825F;
/* 303 */                 og.translate(-1.0F, -18.0F);
/*     */               }
/*     */             
/* 306 */             } else if (c.type == AbstractCard.CardType.POWER) {
/*     */               
/* 308 */               og.zoom = 0.976F;
/* 309 */               og.translate(-3.0F, 0.0F);
/*     */             } 
/*     */ 
/*     */             
/* 313 */             og.update();
/*     */           } 
/* 315 */           SpriteBatch sb = new SpriteBatch();
/* 316 */           sb.setProjectionMatrix(og.combined);
/* 317 */           ImageHelper.beginBuffer(fb);
/* 318 */           sb.begin();
/* 319 */           if (!r.isBicolor) {
/* 320 */             sb.setShader(shade);
/* 321 */             sb.setColor(HSLC);
/*     */           } else {
/* 323 */             sb.setShader(bicolorShader);
/* 324 */             sb.setColor(Color.WHITE);
/* 325 */             setBicolorShaderValues(r);
/*     */           } 
/* 327 */           sb.draw(t, -125.0F, -95.0F);
/* 328 */           sb.setShader(SpriteBatch.createDefaultShader());
/* 329 */           if (itemTex != null) {
/* 330 */             sb.draw(itemTex, -itemTex.getWidth() / 2.0F, -itemTex.getHeight() / 2.0F, -itemTex.getWidth() / 2.0F, -itemTex.getHeight() / 2.0F, itemTex.getWidth(), itemTex.getHeight(), 1.0F, 1.0F, 0.0F, 0, 0, itemTex.getWidth(), itemTex.getHeight(), false, true);
/*     */           }
/* 332 */           if (needsMask(c, artCard) || !c.itemArt().isEmpty()) {
/* 333 */             sb.setBlendFunction(774, 0);
/* 334 */             sb.setColor(Color.WHITE);
/* 335 */             Texture mask = getMask(c);
/* 336 */             sb.setProjectionMatrix((new OrthographicCamera(250.0F, 190.0F)).combined);
/* 337 */             sb.draw(mask, -125.0F, -95.0F, -125.0F, -95.0F, 250.0F, 190.0F, 1.0F, 1.0F, 0.0F, 0, 0, mask.getWidth(), mask.getHeight(), false, true);
/*     */           } 
/* 339 */           sb.end();
/* 340 */           fb.end();
/* 341 */           t.flip(r.flipX, true);
/* 342 */           TextureRegion a = ImageHelper.getBufferTexture(fb);
/* 343 */           return new TextureAtlas.AtlasRegion(a.getTexture(), 0, 0, 250, 190);
/*     */         });
/*     */   }
/*     */   
/*     */   public static Texture getPortraitTexture(AbstractEasyCard c) {
/* 348 */     if (!infos.containsKey(c.rollerKey())) {
/* 349 */       computeCard(c);
/*     */     }
/* 351 */     ReskinInfo r = (ReskinInfo)infos.get(c.rollerKey());
/* 352 */     Color HSLC = new Color(r.H, r.S, r.L, r.C);
/* 353 */     Texture itemTex = null;
/* 354 */     if (!c.itemArt().isEmpty()) {
/* 355 */       itemTex = TexLoader.getTexture(MainModfile.makeImagePath("items/" + c.itemArt() + ".png"));
/* 356 */       if (c.itemScale() != 1.0F) {
/* 357 */         itemTex = TextureScaler.rescale(itemTex, c.itemScale());
/*     */       }
/*     */     } 
/* 360 */     AbstractCard artCard = CardLibrary.getCard(r.origCardID);
/* 361 */     TextureAtlas.AtlasRegion t = new TextureAtlas.AtlasRegion(TexLoader.getTexture("images/1024Portraits/" + artCard.assetUrl + ".png"), 0, 0, 500, 380);
/* 362 */     t.flip(r.flipX, true);
/* 363 */     FrameBuffer fb = ImageHelper.createBuffer(500, 380);
/* 364 */     OrthographicCamera og = new OrthographicCamera(500.0F, 380.0F);
/* 365 */     if (needsMask(c, artCard)) {
/* 366 */       if (artCard.type == AbstractCard.CardType.ATTACK) {
/* 367 */         if (c.type == AbstractCard.CardType.POWER) {
/*     */           
/* 369 */           og.zoom = 0.976F;
/* 370 */           og.translate(-6.0F, 0.0F);
/*     */         } else {
/*     */           
/* 373 */           og.zoom = 0.9F;
/* 374 */           og.translate(0.0F, -20.0F);
/*     */         } 
/* 376 */       } else if (artCard.type == AbstractCard.CardType.POWER) {
/* 377 */         if (c.type == AbstractCard.CardType.ATTACK) {
/*     */           
/* 379 */           og.zoom = 0.9F;
/* 380 */           og.translate(0.0F, -20.0F);
/*     */         } else {
/*     */           
/* 383 */           og.zoom = 0.825F;
/* 384 */           og.translate(-2.0F, -36.0F);
/*     */         }
/*     */       
/* 387 */       } else if (c.type == AbstractCard.CardType.POWER) {
/*     */         
/* 389 */         og.zoom = 0.976F;
/* 390 */         og.translate(-6.0F, 0.0F);
/*     */       } 
/*     */ 
/*     */       
/* 394 */       og.update();
/*     */     } 
/* 396 */     SpriteBatch sb = new SpriteBatch();
/* 397 */     sb.setProjectionMatrix(og.combined);
/* 398 */     ImageHelper.beginBuffer(fb);
/* 399 */     sb.begin();
/* 400 */     if (!r.isBicolor) {
/* 401 */       sb.setShader(shade);
/* 402 */       sb.setColor(HSLC);
/*     */     } else {
/* 404 */       sb.setShader(bicolorShader);
/* 405 */       sb.setColor(Color.WHITE);
/* 406 */       setBicolorShaderValues(r);
/*     */     } 
/* 408 */     sb.draw(t, -250.0F, -190.0F);
/* 409 */     sb.setShader(SpriteBatch.createDefaultShader());
/* 410 */     if (itemTex != null) {
/* 411 */       sb.draw(itemTex, -itemTex.getWidth(), -itemTex.getHeight(), -itemTex.getWidth(), -itemTex.getHeight(), (itemTex.getWidth() * 2), (itemTex.getHeight() * 2), 1.0F, 1.0F, 0.0F, 0, 0, itemTex.getWidth(), itemTex.getHeight(), false, true);
/*     */     }
/* 413 */     if (needsMask(c, artCard) || !c.itemArt().isEmpty()) {
/* 414 */       sb.setBlendFunction(774, 0);
/* 415 */       sb.setColor(Color.WHITE);
/* 416 */       Texture mask = getMask(c);
/* 417 */       sb.setProjectionMatrix((new OrthographicCamera(500.0F, 380.0F)).combined);
/* 418 */       sb.draw(mask, -250.0F, -190.0F, -250.0F, -190.0F, 500.0F, 380.0F, 1.0F, 1.0F, 0.0F, 0, 0, mask.getWidth(), mask.getHeight(), false, true);
/*     */     } 
/* 420 */     sb.end();
/* 421 */     fb.end();
/* 422 */     t.flip(r.flipX, true);
/* 423 */     TextureRegion a = ImageHelper.getBufferTexture(fb);
/* 424 */     return a.getTexture();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setBicolorShaderValues(ReskinInfo info) {
/* 430 */     bicolorShader.setUniformf("lRed", info.target1.r);
/* 431 */     bicolorShader.setUniformf("lGreen", info.target1.g);
/* 432 */     bicolorShader.setUniformf("lBlue", info.target1.b);
/* 433 */     bicolorShader.setUniformf("rRed", info.target2.r);
/* 434 */     bicolorShader.setUniformf("rGreen", info.target2.g);
/* 435 */     bicolorShader.setUniformf("rBlue", info.target2.b);
/* 436 */     bicolorShader.setUniformf("anchorAR", info.anchor1.r);
/* 437 */     bicolorShader.setUniformf("anchorAG", info.anchor1.g);
/* 438 */     bicolorShader.setUniformf("anchorAB", info.anchor1.b);
/* 439 */     bicolorShader.setUniformf("anchorBR", info.anchor2.r);
/* 440 */     bicolorShader.setUniformf("anchorBG", info.anchor2.g);
/* 441 */     bicolorShader.setUniformf("anchorBB", info.anchor2.b);
/*     */   }
/*     */   
/*     */   public static Texture getMask(AbstractCard card) {
/* 445 */     switch (card.type) {
/*     */       case SKILL:
/*     */       case STATUS:
/*     */       case CURSE:
/* 449 */         return skillMask;
/*     */       case ATTACK:
/* 451 */         return attackMask;
/*     */       case POWER:
/* 453 */         return powerMask;
/*     */     } 
/* 455 */     return skillMask;
/*     */   }
/*     */   public static int getMaskIndex(AbstractCard card) {
/* 458 */     switch (card.type) {
/*     */       case SKILL:
/*     */       case STATUS:
/*     */       case CURSE:
/* 462 */         return 2;
/*     */       case ATTACK:
/* 464 */         return 1;
/*     */       case POWER:
/* 466 */         return 0;
/*     */     } 
/* 468 */     return 0;
/*     */   }
/*     */ 
/*     */   
/* 472 */   public static boolean needsMask(AbstractCard card, AbstractCard desiredArt) { return (getMaskIndex(card) != getMaskIndex(desiredArt)); }
/*     */   public static class ReskinInfo { public String origCardID;
/*     */     public boolean isBicolor;
/*     */     
/*     */     public ReskinInfo(String ID, float H, float S, float L, float C, boolean flipX) {
/* 477 */       this.isBicolor = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 490 */       this.origCardID = ID;
/* 491 */       this.H = H;
/* 492 */       this.S = S;
/* 493 */       this.L = L;
/* 494 */       this.C = C;
/* 495 */       this.flipX = flipX;
/*     */     }
/*     */     public float H; public float S; public float L; public float C; public boolean flipX; public Color anchor1;
/*     */     public Color anchor2;
/*     */     public Color target1;
/*     */     public Color target2;
/*     */     
/*     */     public ReskinInfo(String ID, Color anchor1, Color anchor2, Color target1, Color target2, boolean flipX) {
/*     */       this.isBicolor = false;
/* 504 */       this.origCardID = ID;
/* 505 */       this.anchor1 = anchor1;
/* 506 */       this.anchor2 = anchor2;
/* 507 */       this.target1 = target1;
/* 508 */       this.target2 = target2;
/* 509 */       this.flipX = flipX;
/* 510 */       this.isBicolor = true;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\CardArtRoller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */