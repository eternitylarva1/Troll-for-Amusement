/*    */ package ChatterMod.cards;
/*    */ 
/*    */ import ChatterMod.MainModfile;
/*    */
/*    */
/*    */
import ChatterMod.actions.RecordAndPlaybackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*    */ 
/*    */ public class Chatter
/*    */   extends AbstractCard
/*    */ {
/* 16 */   public static final String ID = MainModfile.makeID(Chatter.class.getSimpleName());
/*    */   
/*    */   public Chatter() {
    super("Chrysalis", "真正的尖啸", "green/skill/piercing_wail", 2, "?", CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE);

    /* 19 */   /* 20 */     this.baseDamage = this.damage = 12;
/* 21 */     this.isMultiDamage = true;
/*    */   }

    @Override
    public void upgrade() {

    }

    /*    */
/*    */   
/*    */   public void use(AbstractPlayer p, AbstractMonster m) {
/* 26 */     addToBot(new RecordAndPlaybackAction(f -> {
/* 27 */             MainModfile.logger.info("Chatter Volume: " + f);
/* 28 */             for (int i = 0; i < (AbstractDungeon.getMonsters()).monsters.size(); i++) {
/* 29 */               this.multiDamage[i] = (int)(this.multiDamage[i] * Math.min(1.0F, Math.max(0.0F, f.floatValue() - 40.0F) / 45.0F));
/*    */             }
/* 31 */             addToTop(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
/*    */           }));
/*    */   }

    @Override
    public AbstractCard makeCopy() {
        return null;
    }

    /*    */
/*    */ 
/*    */   
/* 37 */   public void upp() { upgradeDamage(4); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public String cardArtCopy() { return "PiercingWail"; }
/*    */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMod\cards\Chatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */