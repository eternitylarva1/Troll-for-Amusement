package cn.candy.relic;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class ShelledParasite1 extends AbstractMonster {
    public static final String ID = "Shelled Parasite";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 68;
    private static final int HP_MAX = 72;
    private static final int A_2_HP_MIN = 70;
    private static final int A_2_HP_MAX = 75;
    private static final float HB_X_F = 20.0F;
    private static final float HB_Y_F = -6.0F;
    private static final float HB_W = 350.0F;
    private static final float HB_H = 260.0F;
    private static final int PLATED_ARMOR_AMT = 14;
    private static final int FELL_DMG = 18;
    private static final int DOUBLE_STRIKE_DMG = 6;
    private static final int SUCK_DMG = 10;
    private static final int A_2_FELL_DMG = 21;
    private static final int A_2_DOUBLE_STRIKE_DMG = 7;
    private static final int A_2_SUCK_DMG = 12;
    private int fellDmg;
    private int doubleStrikeDmg;
    private int suckDmg;
    private static final int DOUBLE_STRIKE_COUNT = 2;
    private static final int FELL_FRAIL_AMT = 2;
    private static final byte FELL = 1;
    private static final byte DOUBLE_STRIKE = 2;
    private static final byte LIFE_SUCK = 3;
    private static final byte STUNNED = 4;
    private boolean firstMove;
    public static final String ARMOR_BREAK = "ARMOR_BREAK";

    public ShelledParasite1(float x,float y,float w,float h,int health) {
        super(NAME, "Shelled Parasite", 72, x, y, w, h, (String)null, 0, 0);
        this.firstMove = true;
        this.loadAnimation("images/monsters/theCity/shellMonster/skeleton.atlas", "images/monsters/theCity/shellMonster/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.2F);
        e.setTimeScale(0.8F);
        this.dialogX = -50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(health);
        } else {
            this.setHp(health);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.doubleStrikeDmg = 7;
            this.fellDmg = 21;
            this.suckDmg = 12;
        } else {
            this.doubleStrikeDmg = 6;
            this.fellDmg = 18;
            this.suckDmg = 10;
        }

        this.damage.add(new DamageInfo(this, this.doubleStrikeDmg));
        this.damage.add(new DamageInfo(this, this.fellDmg));
        this.damage.add(new DamageInfo(this, this.suckDmg));
    }



    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 14)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 14));
    }

    public void takeTurn() {
        label18:
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                break;
            case 2:
                int i = 0;

                while(true) {
                    if (i >= 2) {
                        break label18;
                    }

                    AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                    ++i;
                }
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-25.0F, 25.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-25.0F, 25.0F) * Settings.scale, Color.GOLD.cpy()), 0.0F));
                AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AttackEffect.NONE));
                break;
            case 4:
                this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(1)).base);
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextType.STUNNED));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "ARMOR_BREAK":
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                this.setMove((byte)4, Intent.STUN);
                this.createIntent();
        }

    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            if (AbstractDungeon.ascensionLevel >= 17) {
                this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(1)).base);
            } else {
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 2, true);
                } else {
                    this.setMove((byte)3, Intent.ATTACK_BUFF, ((DamageInfo)this.damage.get(2)).base);
                }

            }
        } else {
            if (num < 20) {
                if (!this.lastMove((byte)1)) {
                    this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(1)).base);
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(20, 99));
                }
            } else if (num < 60) {
                if (!this.lastTwoMoves((byte)2)) {
                    this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 2, true);
                } else {
                    this.setMove((byte)3, Intent.ATTACK_BUFF, ((DamageInfo)this.damage.get(2)).base);
                }
            } else if (!this.lastTwoMoves((byte)3)) {
                this.setMove((byte)3, Intent.ATTACK_BUFF, ((DamageInfo)this.damage.get(2)).base);
            } else {
                this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 2, true);
            }

        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Shelled Parasite");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
