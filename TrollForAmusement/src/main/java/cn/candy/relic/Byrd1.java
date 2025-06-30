package cn.candy.relic;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.SetAnimationAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Byrd1 extends AbstractMonster {
    public static final String ID = "Byrd";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String THREE_BYRDS = "3_Byrds";
    public static final String FOUR_BYRDS = "4_Byrds";
    public static final String IMAGE = "images/monsters/theCity/byrdFlying.png";
    private static final int HP_MIN = 25;
    private static final int HP_MAX = 31;
    private static final int A_2_HP_MIN = 26;
    private static final int A_2_HP_MAX = 33;
    private static final float HB_X_F = 0.0F;
    private static final float HB_X_G = 10.0F;
    private static final float HB_Y_F = 50.0F;
    private static final float HB_Y_G = -50.0F;
    private static final float HB_W = 240.0F;
    private static final float HB_H = 180.0F;
    private static final int PECK_DMG = 1;
    private static final int PECK_COUNT = 5;
    private static final int SWOOP_DMG = 12;
    private static final int A_2_PECK_COUNT = 6;
    private static final int A_2_SWOOP_DMG = 14;
    private int peckDmg;
    private int peckCount;
    private int swoopDmg;
    private int flightAmt;
    private static final int HEADBUTT_DMG = 3;
    private static final int CAW_STR = 1;
    private static final byte PECK = 1;
    private static final byte GO_AIRBORNE = 2;
    private static final byte SWOOP = 3;
    private static final byte STUNNED = 4;
    private static final byte HEADBUTT = 5;
    private static final byte CAW = 6;
    private boolean firstMove = true;
    private boolean isFlying = true;
    public static final String FLY_STATE = "FLYING";
    public static final String GROUND_STATE = "GROUNDED";

    public Byrd1(float x, float y) {
        super(NAME, "Byrd", 31, 0.0F, 50.0F, 240.0F, 180.0F, (String)null, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(26, 33);
        } else {
            this.setHp(25, 31);
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.flightAmt = 4;
        } else {
            this.flightAmt = 3;
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.peckDmg = 1;
            this.peckCount = 6;
            this.swoopDmg = 14;
        } else {
            this.peckDmg = 1;
            this.peckCount = 5;
            this.swoopDmg = 12;
        }

        this.damage.add(new DamageInfo(this, this.peckDmg));
        this.damage.add(new DamageInfo(this, this.swoopDmg));
        this.damage.add(new DamageInfo(this, 3));
        this.loadAnimation("images/monsters/theCity/byrd/flying.atlas", "images/monsters/theCity/byrd/flying.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle_flap", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {


        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));


        this.createIntent();

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, this.flightAmt)));

    }


    public void takeTurn() {
        label20:
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                int i = 0;

                while(true) {
                    if (i >= this.peckCount) {
                        break label20;
                    }

                    this.playRandomBirdSFx();
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_LIGHT, true));
                    ++i;
                }
            case 2:
                this.isFlying = true;
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "FLYING"));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, this.flightAmt)));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.SLASH_HEAVY));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new SetAnimationAction(this, "head_lift"));
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextType.STUNNED));
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AttackEffect.BLUNT_HEAVY));
                this.setMove((byte)2, Intent.UNKNOWN);
                return;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("BYRD_DEATH"));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.2F, 1.2F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playRandomBirdSFx() {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_BYRD_ATTACK_" + MathUtils.random(0, 5)));
    }

    public void changeState(String stateName) {
        AnimationState.TrackEntry e;
        switch (stateName) {
            case "FLYING":
                this.loadAnimation("images/monsters/theCity/byrd/flying.atlas", "images/monsters/theCity/byrd/flying.json", 1.0F);
                e = this.state.setAnimation(0, "idle_flap", true);
                e.setTime(e.getEndTime() * MathUtils.random());
                this.updateHitbox(0.0F, 50.0F, 240.0F, 180.0F);
                break;
            case "GROUNDED":
                this.setMove((byte)4, Intent.STUN);
                this.createIntent();
                this.isFlying = false;
                this.loadAnimation("images/monsters/theCity/byrd/grounded.atlas", "images/monsters/theCity/byrd/grounded.json", 1.0F);
                e = this.state.setAnimation(0, "idle", true);
                e.setTime(e.getEndTime() * MathUtils.random());
                this.updateHitbox(10.0F, -50.0F, 240.0F, 180.0F);
        }

    }

    protected void getMove(int num) {






            if (this.isFlying) {
                if (num < 50) {
                    if (this.lastTwoMoves((byte)1)) {
                        if (AbstractDungeon.aiRng.randomBoolean(0.4F)) {
                            this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                        } else {
                            this.setMove((byte)6, Intent.BUFF);
                        }
                    } else {
                        this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, this.peckCount, true);
                    }
                } else if (num < 70) {
                    if (this.lastMove((byte)3)) {
                        if (AbstractDungeon.aiRng.randomBoolean(0.375F)) {
                            this.setMove((byte)6, Intent.BUFF);
                        } else {
                            this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, this.peckCount, true);
                        }
                    } else {
                        this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                    }
                } else if (this.lastMove((byte)6)) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.2857F)) {
                        this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                    } else {
                        this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, this.peckCount, true);
                    }
                } else {
                    this.setMove((byte)6, Intent.BUFF);
                }
            } else {
                this.setMove((byte)5, Intent.ATTACK, ((DamageInfo)this.damage.get(2)).base);
            }

        }


    public void die() {
        super.die();
        CardCrawlGame.sound.play("BYRD_DEATH");
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Byrd");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
