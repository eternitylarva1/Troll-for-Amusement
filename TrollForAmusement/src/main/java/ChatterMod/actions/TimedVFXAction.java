/*    */ package ChatterMod.actions;
/*    */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
/*    */ 
/*    */ public class TimedVFXAction extends AbstractGameAction {
/*    */   private AbstractGameEffect effect;
/*    */   
/*    */   public TimedVFXAction(AbstractGameEffect effect) {
/*  9 */     this.added = false;
/*    */ 
/*    */     
/* 12 */     this.effect = effect;
/*    */   }
/*    */   private boolean added;
/*    */   
/*    */   public void update() {
/* 17 */     if (!this.added) {
/* 18 */       AbstractDungeon.effectList.add(this.effect);
/* 19 */       this.added = true;
/*    */     } 
/* 21 */     this.isDone = this.effect.isDone;
/*    */   }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMod\actions\TimedVFXAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */