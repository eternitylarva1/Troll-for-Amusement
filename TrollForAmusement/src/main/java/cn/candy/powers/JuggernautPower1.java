package cn.candy.powers;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class JuggernautPower1 extends AbstractPower {
    public static final String POWER_ID = "Juggernaut";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public JuggernautPower1(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = "Juggernaut";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("juggernaut");
    }

    public void onGainedBlock(float blockAmount) {
        if (blockAmount > 0.0F) {
            this.flash();
            this.addToBot(new DamagePlayerAction(new DamageInfo(this.owner, this.amount, DamageType.THORNS), AttackEffect.SLASH_HORIZONTAL));
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Juggernaut");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

