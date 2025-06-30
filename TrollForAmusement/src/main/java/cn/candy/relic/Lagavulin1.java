package cn.candy.relic;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
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
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lagavulin1 extends AbstractMonster {
    private static final Logger logger = LogManager.getLogger(Lagavulin.class.getName());
    public static final String ID = "Lagavulin";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 109;
    private static final int HP_MAX = 111;
    private static final int A_2_HP_MIN = 112;
    private static final int A_2_HP_MAX = 115;
    private static final byte DEBUFF = 1;
    private static final byte STRONG_ATK = 3;
    private static final byte OPEN = 4;
    private static final byte IDLE = 5;
    private static final byte OPEN_NATURAL = 6;
    static final String DEBUFF_NAME;
    private static final int STRONG_ATK_DMG = 18;
    private static final int DEBUFF_AMT = -1;
    private static final int A_18_DEBUFF_AMT = -2;
    private static final int A_2_STRONG_ATK_DMG = 20;
    private int attackDmg;
    private int debuff;
    private static final int ARMOR_AMT = 8;
    private boolean isOut = false;
    private boolean asleep;
    private boolean isOutTriggered = false;
    private int idleCount = 0;
    private int debuffTurnCount = 0;

    public Lagavulin1(boolean setAsleep,int health) {
        super(NAME, "Lagavulin", health, 0.0F, -25.0F, 320.0F, 220.0F, (String)null, 0.0F, 20.0F);
        this.type = EnemyType.ELITE;
        this.dialogX = -100.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(health);
        } else {
            this.setHp(health);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackDmg = 20;
        } else {
            this.attackDmg = 18;
        }

        if (AbstractDungeon.ascensionLevel >= 18) {
            this.debuff = -2;
        } else {
            this.debuff = -1;
        }

        this.damage.add(new DamageInfo(this, this.attackDmg));
        this.asleep = setAsleep;
        this.loadAnimation("images/monsters/theBottom/lagavulin/skeleton.atlas", "images/monsters/theBottom/lagavulin/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = null;
        if (!this.asleep) {
            this.isOut = true;
            this.isOutTriggered = true;
            e = this.state.setAnimation(0, "Idle_2", true);
            this.updateHitbox(0.0F, -25.0F, 320.0F, 370.0F);
        } else {
            e = this.state.setAnimation(0, "Idle_1", true);
        }

        this.stateData.setMix("Attack", "Idle_2", 0.25F);
        this.stateData.setMix("Hit", "Idle_2", 0.25F);
        this.stateData.setMix("Idle_1", "Idle_2", 0.5F);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {
        if (this.asleep) {
            CardCrawlGame.music.precacheTempBgm("ELITE");
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 8));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 8), 8));
        } else {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            CardCrawlGame.music.playTempBgmInstantly("ELITE");
            this.setMove(DEBUFF_NAME, (byte)1, Intent.STRONG_DEBUFF);
        }

    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                this.debuffTurnCount = 0;
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "DEBUFF"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, this.debuff), this.debuff));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, this.debuff), this.debuff));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            case 2:
            default:
                break;
            case 3:
                ++this.debuffTurnCount;
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextType.STUNNED));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 5:
                ++this.idleCount;
                if (this.idleCount >= 3) {
                    logger.info("idle happened");
                    this.isOutTriggered = true;
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base));
                } else {
                    this.setMove((byte)5, Intent.SLEEP);
                }

                switch (this.idleCount) {
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.5F, 2.0F));
                        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                        return;
                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 0.5F, 2.0F));
                        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                        return;
                    default:
                        return;
                }
            case 6:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                this.createIntent();
                this.isOutTriggered = true;
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        }

    }

    public void changeState(String stateName) {
        if (stateName.equals("ATTACK")) {
            this.state.setAnimation(0, "Attack", false);
            this.state.addAnimation(0, "Idle_2", true, 0.0F);
        } else if (stateName.equals("DEBUFF")) {
            this.state.setAnimation(0, "Debuff", false);
            this.state.addAnimation(0, "Idle_2", true, 0.0F);
        } else if (stateName.equals("OPEN") && !this.isDying) {
            this.isOut = true;
            this.updateHitbox(0.0F, -25.0F, 320.0F, 360.0F);
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[3], 0.5F, 2.0F));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this, this, "Metallicize", 8));
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            CardCrawlGame.music.playPrecachedTempBgm();
            this.state.setAnimation(0, "Coming_out", false);
            this.state.addAnimation(0, "Idle_2", true, 0.0F);
        }

    }

    public void damage(DamageInfo info) {
        int previousHealth = this.currentHealth;
        super.damage(info);
        if (this.currentHealth != previousHealth && !this.isOutTriggered) {
            this.setMove((byte)4, Intent.STUN);
            this.createIntent();
            this.isOutTriggered = true;
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
        } else if (this.isOutTriggered && info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle_2", true, 0.0F);
        }

    }

    protected void getMove(int num) {
        if (this.isOut) {
            if (this.debuffTurnCount < 2) {
                if (this.lastTwoMoves((byte)3)) {
                    this.setMove(DEBUFF_NAME, (byte)1, Intent.STRONG_DEBUFF);
                } else {
                    this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                }
            } else {
                this.setMove(DEBUFF_NAME, (byte)1, Intent.STRONG_DEBUFF);
            }
        } else {
            this.setMove((byte)5, Intent.SLEEP);
        }

    }

    public void die() {
        super.die();
        AbstractDungeon.scene.fadeInAmbiance();
        CardCrawlGame.music.fadeOutTempBGM();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Lagavulin");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        DEBUFF_NAME = MOVES[0];
    }
}

