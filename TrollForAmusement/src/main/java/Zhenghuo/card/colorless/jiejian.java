package Zhenghuo.card.colorless;

import Zhenghuo.utils.Invoker;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.Slot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Chrysalis;
import com.megacrit.cardcrawl.cards.colorless.Metamorphosis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BustedCrown;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class jiejian extends AbstractCard {
    public static final String ID = "Chrysalis";
    private static final CardStrings cardStrings;
    private static final Metamorphosis metamorphosis=new Metamorphosis();

    public jiejian() {
        super("Chrysalis", cardStrings.NAME, "colorless/skill/chrysalis", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.cardsToPreview=metamorphosis;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }

    }

    public AbstractCard makeCopy() {
        return new jiejian();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Chrysalis");
    }
}
