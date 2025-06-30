/*    */ package ChatterMod.util.matcher;
/*    */ 
/*    */ import com.evacipated.cardcrawl.modthespire.Loader;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.Matcher;
/*    */ import java.util.Arrays;
/*    */ import javassist.CtClass;
/*    */ import javassist.NotFoundException;
/*    */ import javassist.expr.Expr;
/*    */ import javassist.expr.NewExpr;
/*    */ 
/*    */ public class InterfaceMatcher
/*    */   extends Matcher.NewExprMatcher {
/*    */   String interfaceToCheck;
/*    */   
/*    */   public InterfaceMatcher(Class<?> clazz) {
/* 16 */     super(clazz);
/* 17 */     this.interfaceToCheck = clazz.getName();
/*    */   }
/*    */   
/*    */   public InterfaceMatcher(String className) {
/* 21 */     super(className);
/* 22 */     this.interfaceToCheck = className;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean match(Expr toMatch) {
/* 27 */     NewExpr expr = (NewExpr)toMatch;
/*    */     try {
/* 29 */       CtClass ctExprClass = Loader.getClassPool().get(expr.getClassName());
/* 30 */       CtClass ctClass = Loader.getClassPool().getCtClass(this.interfaceToCheck);
/* 31 */       if (Arrays.asList(ctExprClass.getInterfaces()).contains(ctClass)) {
/* 32 */         return true;
/*    */       }
/* 34 */     } catch (NotFoundException notFoundException) {}
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\matcher\InterfaceMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */