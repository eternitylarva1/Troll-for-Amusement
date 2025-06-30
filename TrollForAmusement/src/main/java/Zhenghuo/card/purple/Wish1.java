package Zhenghuo.card.purple;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Iterator;

public class Wish1 extends AbstractCard {
    public static final String ID = "Wish";
    private static final CardStrings cardStrings;

    public Wish1() {
        super("Wish", cardStrings.NAME, "purple/skill/wish", 3, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.NONE);
        this.baseDamage = 3;
        this.baseMagicNumber = 25;
        this.magicNumber = 25;
        this.baseBlock = 6;
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new BecomeAlmighty());
        stanceChoices.add(new FameAndFortune());
        stanceChoices.add(new yongshengbusi());
        if (this.upgraded) {
            Iterator var4 = stanceChoices.iterator();

            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));
    }

    public void applyPowers() {
    }

    public void calculateCardDamage(AbstractMonster mo) {
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(5);
            this.upgradeBlock(2);
        }

    }

    public AbstractCard makeCopy() {
        return new Wish1();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Wish");
    }
}

