package cn.candy.powers;



//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import cn.candy.relic.randommonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SplitPower1 extends AbstractPower {
    public static final String POWER_ID = "Compulsive";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    private int count=1;
    public static final String[] DESCRIPTIONS;

    public SplitPower1(AbstractCreature owner) {
        this.name = NAME;

        this.ID = "Compulsive";
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("split");
        this.priority = 50;
        this.amount=0;

    }

    public void updateDescription() {
        this.description = "每当受到10次攻击伤害，随机变成一种怪物,继承所有的非debuff";
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        this.amount++;

        if( this.amount>=10)
        {
            this.amount=0;
            if (info.owner != null && info.type != DamageType.HP_LOSS && info.type != DamageType.THORNS && damageAmount > 0 && damageAmount < this.owner.currentHealth) {
                this.flash();
                this.addToBot(new RollMoveAction((AbstractMonster) this.owner));
                AbstractMonster SP;
                int randomNumber = AbstractDungeon.monsterRng.random(68) + 1;
                if(count>=69){
                    randomNumber=69;
                }
                SP = randommonster.randommonster(randomNumber);

                AbstractMonster m = (AbstractMonster) this.owner;
                SP.drawX = m.drawX;
                SP.drawY = m.drawY;//蛇花的位置
                SP.maxHealth = m.maxHealth;
                SP.addBlock(m.currentBlock);


                SP.currentHealth = m.currentHealth;


                AbstractPower po;
                for (AbstractPower power : m.powers) {
                    // 对每个元素进行处理
                    power.owner = SP;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(SP, SP, power));

                }
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SP, false));

                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(SP));
                SP.rollMove();
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(SP));
                SP.nextMove = 1;
                SP.createIntent();
                SP.usePreBattleAction();
                AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(m, false));

            }
        }
        return damageAmount;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Split");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
