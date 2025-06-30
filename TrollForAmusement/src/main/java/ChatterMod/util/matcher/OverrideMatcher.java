/*    */ package ChatterMod.util.matcher;
/*    */ 
/*    */ import com.evacipated.cardcrawl.modthespire.Loader;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.Matcher;
/*    */ import javassist.CtClass;
/*    */ import javassist.CtMethod;
/*    */ import javassist.NotFoundException;
/*    */ import javassist.expr.Expr;
/*    */ import javassist.expr.NewExpr;
/*    */ 
/*    */ public class OverrideMatcher extends Matcher.NewExprMatcher {
/*    */   String classToCheck;
/*    */   String methodName;
/*    */   Class<?>[] paramtypez;
/*    */   
/*    */   public OverrideMatcher(Class<?> clazz, String methodName, Class... paramtypez) {
/* 17 */     super(clazz);
/* 18 */     this.classToCheck = clazz.getName();
/* 19 */     this.methodName = methodName;
/* 20 */     this.paramtypez = paramtypez;
/*    */   }
/*    */   
/*    */   public OverrideMatcher(String className, String methodName, Class... paramtypez) {
/* 24 */     super(className);
/* 25 */     this.classToCheck = className;
/* 26 */     this.methodName = methodName;
/* 27 */     this.paramtypez = paramtypez;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean match(Expr toMatch) {
/* 32 */     NewExpr expr = (NewExpr)toMatch;
/*    */     try {
/* 34 */       CtClass ctExprClass = Loader.getClassPool().get(expr.getClassName());
/* 35 */       CtClass ctClass = Loader.getClassPool().getCtClass(this.classToCheck);
/* 36 */       if (ctExprClass != ctClass && ctExprClass.subclassOf(ctClass)) {
/* 37 */         for (CtMethod methodToCheck : ctExprClass.getDeclaredMethods()) {
/* 38 */           if (methodToCheck.getMethodInfo2().getName().equals(this.methodName)) {
/* 39 */             return true;
/*    */           }
/*    */         } 
/*    */       }
/* 43 */     } catch (NotFoundException notFoundException) {}
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\matcher\OverrideMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */