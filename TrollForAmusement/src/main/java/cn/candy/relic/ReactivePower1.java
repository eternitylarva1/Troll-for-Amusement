package cn.candy.relic;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ReactivePower1 extends AbstractPower {
    public static final String POWER_ID = "Compulsive";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    private int count=1;
    public static final String[] DESCRIPTIONS;

    public ReactivePower1(AbstractCreature owner) {
        this.name = NAME;

        this.ID = "Compulsive";
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("reactive");
        this.priority = 50;
        this.amount=0;

    }

    public void updateDescription() {
        this.description = "每当受到4次攻击伤害，随机获得一层非debuff";
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && damageAmount < this.owner.currentHealth)
        {
        this.amount++;

        if( this.amount>=4)
        {
            this.amount=0;

                this.flash();
                this.addToBot(new ApplyPowerAction(owner,owner, random_power.random_power(AbstractDungeon.monsterRng.random(23), (AbstractMonster) owner),1));


            }
        }
        return damageAmount;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Compulsive");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
