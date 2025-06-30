package Zhenghuo.card.purple;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.OldCoin;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

public class JustLucky extends AbstractCard {
    public static final String ID = "Xingyunyiji";
    private static final CardStrings cardStrings;

    public JustLucky() {
        super("JustLucky", cardStrings.NAME, "purple/attack/just_lucky", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 3;
        this.baseBlock = 2;
        this.baseMagicNumber = 300;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new FlickCoinEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.3F));
        if(AbstractDungeon.player.hasRelic(OldCoin.ID)||AbstractDungeon.player.gold>=this.magicNumber)
        {
            this.damage=300;
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AttackEffect.NONE));
            if(AbstractDungeon.player.hasRelic(OldCoin.ID)){
                AbstractDungeon.player.loseRelic(OldCoin.ID);
            }
            else {
                AbstractDungeon.player.loseGold(magicNumber);

            }
        }else{

        }
    }
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            if (AbstractDungeon.player.hasRelic(OldCoin.ID)||AbstractDungeon.player.gold>=magicNumber) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
       this.upgradeBaseCost(0);
       this.upgradeMagicNumber(-100);
        }

    }

    public AbstractCard makeCopy() {
        return new JustLucky();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Xingyunyiji");
    }
}
