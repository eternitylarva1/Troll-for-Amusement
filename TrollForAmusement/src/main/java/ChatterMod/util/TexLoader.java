/*    */ package ChatterMod.util;
/*    */ 
/*    */ import ChatterMod.MainModfile;
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.graphics.Texture;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/*    */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TexLoader
/*    */ {
/* 15 */   private static HashMap<String, Texture> textures = new HashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Texture getTexture(String textureString) {
/* 23 */     if (textures.get(textureString) == null) {
/*    */       try {
/* 25 */         loadTexture(textureString, true);
/* 26 */       } catch (GdxRuntimeException e) {
/* 27 */         return getTexture(MainModfile.makeImagePath("ui/missing.png"));
/*    */       } 
/*    */     }
/* 30 */     return (Texture)textures.get(textureString);
/*    */   }
/*    */ 
/*    */   
/* 34 */   private static void loadTexture(String textureString) throws GdxRuntimeException { loadTexture(textureString, false); }
/*    */ 
/*    */   
/*    */   private static void loadTexture(String textureString, boolean linearFilter) throws GdxRuntimeException {
/* 38 */     Texture texture = new Texture(textureString);
/* 39 */     if (linearFilter) {
/* 40 */       texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
/*    */     } else {
/* 42 */       texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
/*    */     } 
/* 44 */     textures.put(textureString, texture);
/*    */   }
/*    */ 
/*    */   
/* 48 */   public static boolean testTexture(String filePath) { return Gdx.files.internal(filePath).exists(); }
/*    */ 
/*    */   
/*    */   public static TextureAtlas.AtlasRegion getTextureAsAtlasRegion(String textureString) {
/* 52 */     Texture texture = getTexture(textureString);
/* 53 */     return ImageHelper.asAtlasRegion(texture);
/*    */   }
/*    */   
/*    */   @SpirePatch(clz = Texture.class, method = "dispose")
/*    */   public static class DisposeListener
/*    */   {
/*    */     @SpirePrefixPatch
/*    */     public static void DisposeListenerPatch(Texture __instance) {
/* 61 */       textures.entrySet().removeIf(entry -> {
/* 62 */             if (((Texture)entry.getValue()).equals(__instance)) System.out.println("TextureLoader | Removing Texture: " + (String)entry.getKey()); 
/* 63 */             return ((Texture)entry.getValue()).equals(__instance);
/*    */           });
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\TexLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */