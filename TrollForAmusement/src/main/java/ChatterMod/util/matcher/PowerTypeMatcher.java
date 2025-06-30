/*    */ package ChatterMod.util.matcher;
/*    */ 
/*    */ import com.evacipated.cardcrawl.modthespire.Loader;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.Matcher;
/*    */ import com.megacrit.cardcrawl.powers.AbstractPower;
/*    */ import javassist.CtClass;
/*    */ import javassist.CtConstructor;
/*    */ import javassist.NotFoundException;
/*    */ import javassist.expr.Expr;
/*    */ import javassist.expr.ExprEditor;
/*    */ import javassist.expr.FieldAccess;
/*    */ import javassist.expr.NewExpr;
/*    */ 
/*    */ public class PowerTypeMatcher extends Matcher.NewExprMatcher {
/*    */   public PowerTypeMatcher(AbstractPower.PowerType type) {
/* 16 */     super(AbstractPower.class);
/* 17 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean match(Expr toMatch) {
/* 22 */     NewExpr expr = (NewExpr)toMatch;
/*    */     try {
/* 24 */       CtClass ctExprClass = Loader.getClassPool().get(expr.getClassName());
/* 25 */       CtClass ctPowerClass = Loader.getClassPool().getCtClass(AbstractPower.class.getName());
/* 26 */       ctExprClass.defrost();
/* 27 */       if (ctExprClass != ctPowerClass && ctExprClass.subclassOf(ctPowerClass)) {
/* 28 */         if (this.type == AbstractPower.PowerType.BUFF) {
/* 29 */           final boolean[] ret = { true };
/* 30 */           for (CtConstructor ctcon : ctExprClass.getConstructors()) {
/* 31 */             ctcon.instrument(new ExprEditor()
/*    */                 {
/*    */                   public void edit(FieldAccess f) {
/* 34 */                     if (f.getClassName().equals(AbstractPower.PowerType.class.getName()) && !f.getFieldName().equals(PowerTypeMatcher.this.type.name())) {
/* 35 */                       ret[0] = false;
/*    */                     }
/*    */                   }
/*    */                 });
/*    */           } 
/* 40 */           return ret[0];
/*    */         } 
/* 42 */         final boolean[] ret = { false };
/* 43 */         for (CtConstructor ctcon : ctExprClass.getConstructors()) {
/* 44 */           ctcon.instrument(new ExprEditor()
/*    */               {
/*    */                 public void edit(FieldAccess f) {
/* 47 */                   if (f.getClassName().equals(AbstractPower.PowerType.class.getName()) && f.getFieldName().equals(PowerTypeMatcher.this.type.name())) {
/* 48 */                     ret[0] = true;
/*    */                   }
/*    */                 }
/*    */               });
/*    */         } 
/* 53 */         return ret[0];
/*    */       }
/*    */     
/* 56 */     } catch (NotFoundException|javassist.CannotCompileException notFoundException) {}
/* 57 */     return false;
/*    */   }
/*    */   
/*    */   AbstractPower.PowerType type;
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\matcher\PowerTypeMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */