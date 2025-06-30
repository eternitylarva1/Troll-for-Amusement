package Zhenghuo.powers;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class LiveForeverPower extends AbstractPower {
    public static final String POWER_ID = "AngelForm";
    private static final PowerStrings powerStrings;

    public LiveForeverPower(AbstractCreature owner, int armorAmt) {
        this.name = powerStrings.NAME;
        this.ID = "AngelForm";
        this.owner = owner;
        this.amount = armorAmt;
        this.updateDescription();
        this.loadRegion("deva");
    }

    public void updateDescription() {
        this.description = "你死后可以进行sl回到死亡前";
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("AngelForm");
    }
}
