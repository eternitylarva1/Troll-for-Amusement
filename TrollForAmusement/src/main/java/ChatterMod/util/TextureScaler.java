/*    */ package ChatterMod.util;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.OrthographicCamera;
/*    */ import com.badlogic.gdx.graphics.Texture;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/*    */ import com.badlogic.gdx.graphics.glutils.FrameBuffer;
/*    */ 
/*    */ public class TextureScaler
/*    */ {
/* 11 */   public static Texture rescale(Texture t, float scale) { return rescale(t, (int)(t.getWidth() * scale), (int)(t.getHeight() * scale)); }
/*    */ 
/*    */   
/*    */   public static Texture rescale(Texture t, int w, int h) {
/* 15 */     float w2 = w / 2.0F;
/* 16 */     float h2 = h / 2.0F;
/* 17 */     FrameBuffer fb = ImageHelper.createBuffer(w, h);
/* 18 */     SpriteBatch sb = new SpriteBatch();
/* 19 */     sb.setProjectionMatrix((new OrthographicCamera(w, h)).combined);
/* 20 */     ImageHelper.beginBuffer(fb);
/* 21 */     sb.begin();
/* 22 */     sb.draw(t, -w2, -h2, -w2, -h2, w, h, 1.0F, 1.0F, 0.0F, 0, 0, t.getWidth(), t.getHeight(), false, true);
/* 23 */     sb.end();
/* 24 */     fb.end();
/* 25 */     return ImageHelper.getBufferTexture(fb).getTexture();
/*    */   }
/*    */ 
/*    */   
/* 29 */   public static Texture rescale(TextureAtlas.AtlasRegion r, float scale) { return rescale(r, (int)(r.packedWidth * scale), (int)(r.packedHeight * scale)); }
/*    */ 
/*    */   
/*    */   public static Texture rescale(TextureAtlas.AtlasRegion r, int w, int h) {
/* 33 */     float w2 = w / 2.0F;
/* 34 */     float h2 = h / 2.0F;
/* 35 */     FrameBuffer fb = ImageHelper.createBuffer(w, h);
/* 36 */     SpriteBatch sb = new SpriteBatch();
/* 37 */     sb.setProjectionMatrix((new OrthographicCamera(w, h)).combined);
/* 38 */     ImageHelper.beginBuffer(fb);
/* 39 */     r.flip(false, true);
/* 40 */     sb.begin();
/* 41 */     sb.draw(r, -w2, -h2, -w2, -h2, w, h, 1.0F, 1.0F, 0.0F);
/* 42 */     sb.end();
/* 43 */     fb.end();
/* 44 */     r.flip(false, true);
/* 45 */     return ImageHelper.getBufferTexture(fb).getTexture();
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\TextureScaler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */