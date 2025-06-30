package cn.candy.relic;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;

public class TimeEater1 extends AbstractMonster {
    public static final String ID = "TimeEater";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int HP = 456;
    public static final int A_2_HP = 480;
    private static final byte REVERBERATE = 2;
    private static final byte RIPPLE = 3;
    private static final byte HEAD_SLAM = 4;
    private static final byte HASTE = 5;
    private static final int REVERB_DMG = 7;
    private static final int REVERB_AMT = 3;
    private static final int A_2_REVERB_DMG = 8;
    private static final int RIPPLE_BLOCK = 20;
    private static final int HEAD_SLAM_DMG = 26;
    private static final int A_2_HEAD_SLAM_DMG = 32;
    private int reverbDmg;
    private int headSlamDmg;
    private static final int HEAD_SLAM_STICKY = 1;
    private static final int RIPPLE_DEBUFF_TURNS = 1;
    private boolean usedHaste = false;
    private boolean firstTurn = true;

    public TimeEater1() {
        super(NAME, "TimeEater", 456, -10.0F, -30.0F, 476.0F, 410.0F, (String)null, -50.0F, 30.0F);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(480);
        } else {
            this.setHp(456);
        }

        this.loadAnimation("images/monsters/theForest/timeEater/skeleton.atlas", "images/monsters/theForest/timeEater/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        this.type = EnemyType.BOSS;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.reverbDmg = 8;
            this.headSlamDmg = 32;
        } else {
            this.reverbDmg = 7;
            this.headSlamDmg = 26;
        }

        this.damage.add(new DamageInfo(this, this.reverbDmg, DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.headSlamDmg, DamageType.NORMAL));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        UnlockTracker.markBossAsSeen("WIZARD");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TimeWarpPower(this)));
    }

    public void takeTurn() {
        if (this.firstTurn) {
            if (AbstractDungeon.player.chosenClass == PlayerClass.WATCHER) {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 0.5F, 2.0F));
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 0.5F, 2.0F));
            }

            this.firstTurn = false;
        }

        label32:
        switch (this.nextMove) {
            case 2:
                int i = 0;

                while(true) {
                    if (i >= 3) {
                        break label32;
                    }

                    AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveType.CHAOTIC), 0.75F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.FIRE));
                    ++i;
                }
            case 3:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 20));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                if (AbstractDungeon.ascensionLevel >= 19) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                }
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.POISON));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, 1)));
                if (AbstractDungeon.ascensionLevel >= 19) {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
                }
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[1], 0.5F, 2.0F));
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth / 2 - this.currentHealth));
                if (AbstractDungeon.ascensionLevel >= 19) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.headSlamDmg));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
            default:
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
        if (this.currentHealth < this.maxHealth / 2 && !this.usedHaste) {
            this.usedHaste = true;
            this.setMove((byte)5, Intent.BUFF);
        } else if (num < 45) {
            if (!this.lastTwoMoves((byte)2)) {
                this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 3, true);
            } else {
                this.getMove(AbstractDungeon.aiRng.random(50, 99));
            }
        } else if (num < 80) {
            if (!this.lastMove((byte)4)) {
                this.setMove((byte)4, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(1)).base);
            } else if (AbstractDungeon.aiRng.randomBoolean(0.66F)) {
                this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 3, true);
            } else {
                this.setMove((byte)3, Intent.DEFEND_DEBUFF);
            }
        } else if (!this.lastMove((byte)3)) {
            this.setMove((byte)3, Intent.DEFEND_DEBUFF);
        } else {
            this.getMove(AbstractDungeon.aiRng.random(74));
        }
    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            super.die();
            this.onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("WIZARD");
            UnlockTracker.unlockAchievement("TIME_EATER");
            this.onFinalBossVictoryLogic();
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TimeEater");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

