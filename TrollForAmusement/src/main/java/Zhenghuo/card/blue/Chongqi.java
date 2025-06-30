package Zhenghuo.card.blue;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.cards.AbstractCard;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
        import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Zhenghuo.relics.StrongCharacter.initizeGame;

public class Chongqi extends AbstractCard {
    public static final String ID = "Chongqi";
    private static final CardStrings cardStrings;

    public Chongqi() {
        super(ID, cardStrings.NAME, "blue/skill/reboot", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        initizeGame();
    }

    public AbstractCard makeCopy() {
        return new Chongqi();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);

        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Chongqi");
    }
}
