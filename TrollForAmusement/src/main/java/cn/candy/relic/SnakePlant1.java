package cn.candy.relic;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.MalleablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class SnakePlant1 extends AbstractMonster {
    public static final String ID = "SnakePlant";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 75;
    private static final int HP_MAX = 79;
    private static final int A_2_HP_MIN = 78;
    private static final int A_2_HP_MAX = 82;
    private static final byte CHOMPY_CHOMPS = 1;
    private static final byte SPORES = 2;
    private static final int CHOMPY_AMT = 3;
    private static final int CHOMPY_DMG = 7;
    private static final int A_2_CHOMPY_DMG = 8;
    private int rainBlowsDmg;

    public SnakePlant1(float x,float y,float w,float h,int health) {
        super(NAME, "SnakePlant", 79, x, y, w, h, (String)null, 0, 0);
        this.loadAnimation("images/monsters/theCity/snakePlant/skeleton.atlas", "images/monsters/theCity/snakePlant/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(health);
        } else {
            this.setHp(health);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.rainBlowsDmg = 8;
        } else {
            this.rainBlowsDmg = 7;
        }

        this.damage.add(new DamageInfo(this, this.rainBlowsDmg));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MalleablePower(this)));
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

    public void takeTurn() {
        AbstractCreature p = AbstractDungeon.player;
        label16:
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                int numBlows = 3;
                int i = 0;

                while(true) {
                    if (i >= numBlows) {
                        break label16;
                    }

                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.CHARTREUSE.cpy()), 0.2F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(p, (DamageInfo)this.damage.get(0), AttackEffect.NONE, true));
                    ++i;
                }
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (AbstractDungeon.ascensionLevel >= 17) {
            if (num < 65) {
                if (this.lastTwoMoves((byte)1)) {
                    this.setMove(MOVES[0], (byte)2, Intent.STRONG_DEBUFF);
                } else {
                    this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 3, true);
                }
            } else if (!this.lastMove((byte)2) && !this.lastMoveBefore((byte)2)) {
                this.setMove(MOVES[0], (byte)2, Intent.STRONG_DEBUFF);
            } else {
                this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 3, true);
            }
        } else if (num < 65) {
            if (this.lastTwoMoves((byte)1)) {
                this.setMove(MOVES[0], (byte)2, Intent.STRONG_DEBUFF);
            } else {
                this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 3, true);
            }
        } else if (this.lastMove((byte)2)) {
            this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 3, true);
        } else {
            this.setMove(MOVES[0], (byte)2, Intent.STRONG_DEBUFF);
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SnakePlant");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

