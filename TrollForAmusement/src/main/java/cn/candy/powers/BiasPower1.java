package cn.candy.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class BiasPower1 extends AbstractPower {
        public static final String POWER_ID = "Bias";
        private static final PowerStrings powerStrings;
        public static final String NAME;
        public static final String[] DESCRIPTIONS;

        public BiasPower1(AbstractCreature owner, int setAmount) {
            this.name = NAME;
            this.ID = "Bias";
            this.owner = owner;
            this.amount = setAmount;
            this.updateDescription();
            this.loadRegion("bias");
            this.type = PowerType.DEBUFF;
        }

        public void atStartOfTurn() {
            this.flash();
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new FocusPower(AbstractDungeon.player, -this.amount), -this.amount));
        }

        public void stackPower(int stackAmount) {
            this.fontScale = 8.0F;
            this.amount += stackAmount;
        }

        public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }

        static {
            powerStrings = CardCrawlGame.languagePack.getPowerStrings("Bias");
            NAME = powerStrings.NAME;
            DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        }
    }


