/*    */ package ChatterMod.util.matcher;
/*    */ 
/*    */ import com.evacipated.cardcrawl.modthespire.lib.Matcher;
/*    */ import com.evacipated.cardcrawl.modthespire.patcher.Expectation;
/*    */ import java.util.HashMap;
/*    */ import javassist.expr.Expr;
/*    */ 
/*    */ public class CompoundMatcher
/*    */   extends Matcher {
/* 10 */   HashMap<Matcher, Boolean> matchers = new HashMap();
/*    */   
/*    */   boolean pedantic;
/*    */   
/* 14 */   public CompoundMatcher(Matcher... matchers) { this(true, matchers); }
/*    */ 
/*    */   
/*    */   public CompoundMatcher(boolean pedantic, Matcher... matchers) {
/* 18 */     super(Expectation.CATCH_CLAUSE);
/* 19 */     this.pedantic = pedantic;
/* 20 */     for (Matcher m : matchers) {
/* 21 */       this.matchers.put(m, Boolean.valueOf(false));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean compoundMatch(Expectation expectation, Expr expr) {
/* 26 */     if (this.pedantic) {
/* 27 */       return this.matchers.keySet().stream().allMatch(m -> (m.getExpectation() == expectation && m.match(expr)));
/*    */     }
/* 29 */     for (Matcher m : this.matchers.keySet()) {
/* 30 */       if (m.getExpectation() == expectation && m.match(expr)) {
/* 31 */         this.matchers.put(m, Boolean.valueOf(true));
/*    */       }
/*    */     } 
/* 34 */     return this.matchers.keySet().stream().allMatch(m -> ((Boolean)this.matchers.get(m)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/* 38 */   public void reset() { this.matchers.replaceAll((m, v) -> Boolean.valueOf(false)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public boolean match(Expr expr) { return false; }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\matcher\CompoundMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */