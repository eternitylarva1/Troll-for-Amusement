/*    */ package ChatterMod.util.matcher;
/*    */ 
/*    */ import com.evacipated.cardcrawl.modthespire.Loader;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.Matcher;
/*    */ import javassist.CtClass;
/*    */ import javassist.NotFoundException;
/*    */ import javassist.expr.Expr;
/*    */ import javassist.expr.FieldAccess;
/*    */ 
/*    */ public class FuzzyFieldAccessMatcher extends Matcher.FieldAccessMatcher {
/*    */   String classToCheck;
/*    */   String fieldName;
/*    */   
/*    */   public FuzzyFieldAccessMatcher(Class<?> clazz, String fieldName) {
/* 15 */     super(clazz, fieldName);
/* 16 */     this.classToCheck = clazz.getName();
/* 17 */     this.fieldName = fieldName;
/*    */   }
/*    */   
/*    */   public FuzzyFieldAccessMatcher(String className, String fieldName) {
/* 21 */     super(className, fieldName);
/* 22 */     this.classToCheck = className;
/* 23 */     this.fieldName = fieldName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean match(Expr toMatch) {
/* 28 */     FieldAccess expr = (FieldAccess)toMatch;
/*    */     try {
/* 30 */       CtClass ctClassToCheck = Loader.getClassPool().getCtClass(this.classToCheck);
/* 31 */       return (expr.getFieldName().equals(this.fieldName) && expr.getEnclosingClass().subclassOf(ctClassToCheck));
/* 32 */     } catch (NotFoundException notFoundException) {
/*    */       
/* 34 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\matcher\FuzzyFieldAccessMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */