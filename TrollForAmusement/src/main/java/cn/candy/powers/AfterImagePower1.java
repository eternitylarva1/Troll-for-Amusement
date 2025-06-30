package cn.candy.powers;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AfterImagePower1 extends AbstractPower {
    public static final String POWER_ID = "After Image";
    private static final PowerStrings powerStrings;

    public AfterImagePower1(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = "After Image";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("afterImage");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (Settings.FAST_MODE) {
            this.addToBot(new GainBlockAction(owner, owner, this.amount, true));
        } else {
            this.addToBot(new GainBlockAction(owner, owner, this.amount));
        }

        this.flash();
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("After Image");
    }
}
