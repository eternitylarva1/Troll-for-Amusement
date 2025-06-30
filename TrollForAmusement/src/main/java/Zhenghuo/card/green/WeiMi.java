package Zhenghuo.card.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.monsters.city.SphericGuardian;
import com.megacrit.cardcrawl.monsters.exordium.Sentry;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.Iterator;

public class WeiMi extends AbstractCard {
    public static final String ID = "WeiMi";
    private static final CardStrings cardStrings;

    public WeiMi() {
        super("WeiMi", cardStrings.NAME, "green/skill/malaise", 3, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(isMachine(m)) {
                    addToBot(new InstantKillAction(m));
                }else{
                    AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX,p.dialogY,"目标不是机械",true));
                }
                    this.isDone=true;
            }
        });
    }

    private boolean isMachine(AbstractMonster m){
        return(m instanceof Sentry||m instanceof TheGuardian||m instanceof SphericGuardian||m instanceof BronzeAutomaton||m instanceof BronzeOrb||m instanceof Spiker||m instanceof Exploder||m instanceof Repulsor||m instanceof OrbWalker||m instanceof Donu||m instanceof Deca);
    }
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (isMachine(m)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.upgradeBaseCost(2);
        }

    }

    public AbstractCard makeCopy() {
        return new WeiMi();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WeiMi");
    }
}
