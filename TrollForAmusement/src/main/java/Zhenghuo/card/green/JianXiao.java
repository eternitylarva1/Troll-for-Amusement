package Zhenghuo.card.green;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
        import com.megacrit.cardcrawl.powers.RitualPower;
        import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;

public class JianXiao extends AbstractCard {
    public static final String ID = "JianXiao";
    private static final CardStrings cardStrings;

    public JianXiao() {
        super(ID, cardStrings.NAME,"green/skill/piercing_wail", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.exhaust = true;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX,AbstractDungeon.player.dialogY,"我的力量无人能及!",true));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveType.CHAOTIC), 0.3F));
        } else {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveType.CHAOTIC), 1.5F));
        }
AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new RitualPower(p,1,true)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }

    public AbstractCard makeCopy() {
        return new JianXiao();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("JianXiao");
    }
}
