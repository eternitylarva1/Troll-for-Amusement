package Zhenghuo.card.green;

import Zhenghuo.actions.DiscoveryAction1;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class jisuanxiazhu extends AbstractCard {
    public static final String ID = "Calculated Gamble";
    private static final CardStrings cardStrings;

    public jisuanxiazhu() {
        super("Calculated Gamble", cardStrings.NAME, "green/skill/calculated_gamble", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = false;
        this.baseMagicNumber=this.magicNumber=1;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
AbstractDungeon.actionManager.addToBottom(new DiscoveryAction1(this.upgraded,this.magicNumber));

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
           this.baseMagicNumber=this.magicNumber=2;
     this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
     initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new jisuanxiazhu();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Calculated Gamble");
    }
}
