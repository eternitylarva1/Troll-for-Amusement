package Zhenghuo.card.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Metamorphosis;
import com.megacrit.cardcrawl.cards.purple.Halt;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.core.CardCrawlGame.stopClock;

public class Tingdun extends AbstractCard {
    public static final String ID = "Halt";
    private static final CardStrings cardStrings;


    public Tingdun() {
        super("Halt", cardStrings.NAME, "purple/skill/halt", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 3;
        this.magicNumber = this.baseMagicNumber = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                stopClock=!stopClock;
                isDone=true;
            }
        });


    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);

        }

    }

    public AbstractCard makeCopy() {
        return new Tingdun();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Halt");
    }
}
