/*    */ package ChatterMod.util.matcher;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.Matcher;
/*    */ import com.evacipated.cardcrawl.modthespire.patcher.Expectation;
/*    */ import javassist.expr.*;
/*    */
/*    */
/*    */

/*    */
/*    */ public class BasicMatchChecker extends ExprEditor {
/*    */   private final Matcher matcher;
/*    */   
/* 12 */   public BasicMatchChecker(Matcher matcher) { this.matcher = matcher; }
/*    */   private boolean didMatch;
/*    */   
/*    */   protected void doMatch(Expectation expectation, Expr expr) {
/* 16 */     if (this.matcher instanceof CompoundMatcher && ((CompoundMatcher)this.matcher).compoundMatch(expectation, expr)) {
/* 17 */       this.didMatch = true;
/* 18 */     } else if (this.matcher.getExpectation() == expectation && this.matcher.match(expr)) {
/* 19 */       this.didMatch = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 24 */   public boolean didMatch() { return this.didMatch; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public void edit(Cast expr) { doMatch(Expectation.TYPE_CAST, expr); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void edit(ConstructorCall expr) { doMatch(Expectation.CONSTRUCTOR_CALL, expr); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public void edit(FieldAccess expr) { doMatch(Expectation.FIELD_ACCESS, expr); }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public void edit(Handler expr) { doMatch(Expectation.CATCH_CLAUSE, expr); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public void edit(Instanceof expr) { doMatch(Expectation.INSTANCEOF, expr); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void edit(MethodCall expr) { doMatch(Expectation.METHOD_CALL, expr); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public void edit(NewArray expr) { doMatch(Expectation.ARRAY_CREATION, expr); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public void edit(NewExpr expr) { doMatch(Expectation.NEW_EXPRESSION, expr); }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\matcher\BasicMatchChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */