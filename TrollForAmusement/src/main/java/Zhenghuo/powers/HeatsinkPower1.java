package Zhenghuo.powers;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.WrathStance;

public class HeatsinkPower1 extends AbstractPower {
    public static final String POWER_ID = "Heatsink";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public HeatsinkPower1(AbstractCreature owner, int drawAmt) {
        this.name = NAME;
        this.ID = "Heatsink";
        this.owner = owner;
        this.amount = drawAmt;
        this.updateDescription();
        this.loadRegion("heatsink");
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.POWER) {
            this.flash();


        }

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        super.onAfterCardPlayed(usedCard);
        if (usedCard instanceof Burn) {
            this.flash();
            this.addToTop(new DrawCardAction(this.owner, this.amount));

            this.addToBot(new ChangeStanceAction(new WrathStance()));

        }
    }

    public void updateDescription() {

            this.description = "每当你打出灼伤时，进入愤怒并抽" + this.amount +"张牌";


    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Heatsink");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
