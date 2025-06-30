package Zhenghuo.card.red;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;

import java.util.Iterator;

public class ThunderClap extends AbstractCard {
    public static final String ID = "Thunderclap";
    private static final CardStrings cardStrings;

    public ThunderClap() {
        super("Thunderclap", cardStrings.NAME, "red/attack/thunder_clap", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.isMultiDamage = true;
        this.baseDamage = 4;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("THUNDERCLAP", 0.05F));
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        AbstractMonster mo;
        while(var3.hasNext()) {
            mo = (AbstractMonster)var3.next();
            if (!mo.isDeadOrEscaped()) {
if(this.upgraded){
    this.addToBot(new ChannelAction(new Lightning()));
}
                this.addToBot(new ChannelAction(new Lightning()));
            }
        }


    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }

    public AbstractCard makeCopy() {
        return new ThunderClap();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Thunderclap");
    }

}

