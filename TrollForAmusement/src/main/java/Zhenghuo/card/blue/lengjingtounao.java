package Zhenghuo.card.blue;

import Zhenghuo.powers.HeatsinkPower1;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Coolheaded;
import com.megacrit.cardcrawl.cards.colorless.Metamorphosis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;

public class lengjingtounao extends AbstractCard {
    public static final String ID = "Coolheaded";
    private static final CardStrings cardStrings;
    private static final Metamorphosis metamorphosis=new Metamorphosis();

    public lengjingtounao() {
        super("Coolheaded", cardStrings.NAME, "blue/skill/coolheaded", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
        this.addToBot(new DrawCardAction(p, this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new lengjingtounao();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Coolheaded");
    }
}
