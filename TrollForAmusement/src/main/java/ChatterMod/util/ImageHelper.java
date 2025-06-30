/*    */ package ChatterMod.util;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.graphics.Pixmap;
/*    */ import com.badlogic.gdx.graphics.Texture;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.badlogic.gdx.graphics.glutils.FrameBuffer;
/*    */ import com.megacrit.cardcrawl.core.Settings;
/*    */ import com.megacrit.cardcrawl.helpers.TipHelper;
/*    */ import com.megacrit.cardcrawl.helpers.input.InputHelper;
/*    */ 
/*    */ 
/*    */ public class ImageHelper
/*    */ {
/* 17 */   public static FrameBuffer createBuffer() { return createBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public static FrameBuffer createBuffer(int sizeX, int sizeY) { return new FrameBuffer(Pixmap.Format.RGBA8888, sizeX, sizeY, false, false); }
/*    */ 
/*    */   
/*    */   public static void beginBuffer(FrameBuffer fbo) {
/* 25 */     fbo.begin();
/* 26 */     Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 27 */     Gdx.gl.glClear(16640);
/* 28 */     Gdx.gl.glColorMask(true, true, true, true);
/*    */   }
/*    */   
/*    */   public static TextureRegion getBufferTexture(FrameBuffer fbo) {
/* 32 */     TextureRegion texture = new TextureRegion((Texture)fbo.getColorBufferTexture());
/* 33 */     texture.flip(false, true);
/* 34 */     return texture;
/*    */   }
/*    */ 
/*    */   
/* 38 */   public static TextureAtlas.AtlasRegion asAtlasRegion(Texture tex) { return new TextureAtlas.AtlasRegion(tex, 0, 0, tex.getWidth(), tex.getHeight()); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public static void drawTextureScaled(SpriteBatch sb, Texture tex, float x, float y) { sb.draw(tex, x, y, 0.0F, 0.0F, tex.getWidth() * Settings.scale, tex.getHeight() * Settings.scale, 1.0F, 1.0F, 0.0F, 0, 0, tex.getWidth(), tex.getHeight(), false, false); }
/*    */ 
/*    */   
/*    */   public static void tipBoxAtMousePos(String name, String description) {
/* 46 */     if (InputHelper.mX < 1400.0F * Settings.scale) {
/* 47 */       TipHelper.renderGenericTip(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY - 50.0F * Settings.scale, name, description);
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 52 */       TipHelper.renderGenericTip(InputHelper.mX - 350.0F * Settings.scale, InputHelper.mY - 50.0F * Settings.scale, name, description);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\ImageHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */