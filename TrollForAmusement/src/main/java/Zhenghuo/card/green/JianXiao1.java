package Zhenghuo.card.green;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import ChatterMod.MainModfile;
import ChatterMod.actions.RecordAndPlaybackAction;
import Zhenghuo.modcore.ExampleMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JianXiao1 extends AbstractCard {
    public static final String ID = "PiercingWail";
    private static final CardStrings cardStrings;

    public JianXiao1() {
        super(ID, cardStrings.NAME,"green/skill/piercing_wail", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.exhaust = true;
        this.baseMagicNumber = 6;
       this.damage=this.baseDamage=6;
        this.magicNumber = this.baseMagicNumber= (int) (Settings.MASTER_VOLUME*100);
        System.out.println(Settings.MASTER_VOLUME);

    }
public void update() {

        super.update();



    if( this.magicNumber !=Settings.MASTER_VOLUME*100){

        this.magicNumber = this.baseMagicNumber= (int) (Settings.MASTER_VOLUME*100);
        this.initializeDescription();
    }

    }
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new RecordAndPlaybackAction(f -> {
            MainModfile.logger.info("Chatter Volume: " + f);
            ExampleMod.Tips="目前音量为"+f;
            for (int i = 0; i < (AbstractDungeon.getMonsters()).monsters.size(); i++) {
                this.multiDamage[i] = (int)(this.multiDamage[i] * Math.min(1.0F, Math.max(0.0F, f.floatValue() - 40.0F) / 45.0F));
            }
            addToTop(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    ExampleMod.StartRecord=false;
                    isDone=true;
                }
            });
        }));
/*
        AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX,AbstractDungeon.player.dialogY,"我的力量无人能及!",true));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveType.CHAOTIC), 0.3F));
        } else {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveType.CHAOTIC), 1.5F));
        }

        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        AbstractMonster mo;
        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
            this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }

        var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
            if (!mo.hasPower("Artifact")) {
                this.addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }*/
}

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }

    public AbstractCard makeCopy() {
        return new JianXiao1();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("PiercingWail");
    }
}
