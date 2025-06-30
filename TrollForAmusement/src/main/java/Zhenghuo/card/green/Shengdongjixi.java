package Zhenghuo.card.green;

import Zhenghuo.powers.ShengdongjixiPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Distraction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Shengdongjixi extends AbstractCard {
    public static final String ID = "Distraction";
    private static final CardStrings cardStrings;

    public Shengdongjixi() {
        super("Distraction", cardStrings.NAME, "green/skill/distraction", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseDamage = 14;
        this.magicNumber = -1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
this.addToBot(new ApplyPowerAction(p,p,new ShengdongjixiPower(p,this.magicNumber),this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new Shengdongjixi();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Distraction");
    }
}
