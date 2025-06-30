package cn.candy.powers;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import cn.candy.relic.randommonster;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class new_split_power extends AbstractPower {
    public static final String POWER_ID = "Split";
    private static final PowerStrings powerStrings;
    public static final String NAME;

    public static final String[] DESCRIPTIONS;

    public new_split_power(AbstractCreature owner) {
        this.name = NAME;

        this.ID = "Split";
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("split");
        this.priority = 50;
        this.amount=0;

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];
    }





    public void atStartOfTurn() {
        int randomNumber=0;
        AbstractMonster SP=null;
        AbstractMonster SQ=null;
        randomNumber = AbstractDungeon.monsterRng.random(25)+1;
        SP = randommonster.randommonster(AbstractDungeon.monsterRng.random(25)+1);
        SQ = randommonster.randommonster(AbstractDungeon.monsterRng.random(25)+1);
        SP.currentHealth= owner.currentHealth;
        SP.maxHealth=SP.currentHealth;
        SQ.currentHealth= owner.currentHealth;
        SQ.maxHealth= SQ.currentHealth;
        SP.drawX+=250;
        SQ.drawX-=150;
        AbstractPower po;



        System.out.println("正在判定中");
        if(this.owner.currentHealth<=this.owner.maxHealth/2){
            AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());

            System.out.println("成功判定，满足分裂条件");
        this.owner.drawX+=50.0F;
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SP, false));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SQ, false));
        AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this.owner, 1.0F, 0.1F));
        AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this.owner));
        AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster) this.owner, false));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
        AbstractDungeon.actionManager.addToBottom(new CanLoseAction());

}

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Split");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}