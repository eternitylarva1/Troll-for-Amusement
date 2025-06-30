package Zhenghuo.card.red;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.AllForOne;
import com.megacrit.cardcrawl.cards.colorless.Metamorphosis;
import com.megacrit.cardcrawl.cards.red.Bloodletting;
import com.megacrit.cardcrawl.cards.red.Offering;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fangxue extends AbstractCard {
    public static final String ID = "Bloodletting";
    private static final CardStrings cardStrings;
    private static final Metamorphosis metamorphosis=new Metamorphosis();

    public Fangxue() {
        super("Bloodletting", cardStrings.NAME, "red/skill/bloodletting", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;

    }
    public void update() {
        super.update();
        this.stopGlowing();
        this.transparency = 0.0F;

    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p, p, 1));
        if(this.upgraded){
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.player.hand.addToHand(Fangxue.this);
                    isDone = true;
                }
            });
        }

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new Fangxue();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bloodletting");
    }
}
