package Zhenghuo.card.green;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import Zhenghuo.otherplayer.AbstractOtherPlayer;
import Zhenghuo.otherplayer.OtherPlayerHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SurroundedPower;

public class Shuangchongcunzai extends AbstractCard {
    public static final String ID = "Shuangchongcunzai";
    private static final CardStrings cardStrings;

    public Shuangchongcunzai() {
        super("Shuangchongcunzai", cardStrings.NAME, "green/skill/doppelganger", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
                new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractMonster m=new AbstractOtherPlayer(AbstractDungeon.player.getClass().getSimpleName());
AbstractMonster am=AbstractDungeon.getCurrRoom().monsters.monsters.get(AbstractDungeon.getCurrRoom().monsters.monsters.size()-1);
                        m.drawX=am.drawX+am.hb_w/2+m.hb_w/2;
                        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                            addToBot(new ApplyPowerAction(monster,p,new SurroundedPower(monster)));
                        }

                       // addToBot(new ApplyPowerAction(m,p,new BackAttackPower(m)));
                        OtherPlayerHelper.addMinion(AbstractDungeon.player,m);
                        m.flipHorizontal=true;
                        isDone=true;
                    }
                }
        );
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new Shuangchongcunzai();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Shuangchongcunzai");
    }
}
