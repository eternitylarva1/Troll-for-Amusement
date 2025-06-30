package FanzhuanMod.cards;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;

public class Darkness extends AbstractCard {
    public static final String ID = "Darkness";
    private static final CardStrings cardStrings;

    public Darkness() {
        super("Darkness", cardStrings.NAME, "blue/skill/darkness", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ChannelAction(new Dark()));
        if (this.upgraded) {
            this.addToBot(new DarkImpulseAction());
        }

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new Darkness();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Darkness");
    }
}
