//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Zhenghuo.card.green;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Storm;
import com.megacrit.cardcrawl.cards.green.StormOfSteel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Nemesis;
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin;
import com.megacrit.cardcrawl.powers.WeakPower;

public class LegSweep extends AbstractCard {
    public static final String ID = "Leg Sweep";
    private static final CardStrings cardStrings;

    public LegSweep() {
        super("Leg Sweep", cardStrings.NAME, "green/skill/leg_sweep", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseBlock = 11;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if(!(m instanceof Nemesis)) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.block, DamageInfo.DamageType.NORMAL), false));
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.block, DamageInfo.DamageType.NORMAL), false));
        }
        if(m instanceof Lagavulin){
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.block, DamageInfo.DamageType.NORMAL), false));
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.block, DamageInfo.DamageType.NORMAL), false));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeBlock(3);

        }

    }

    public AbstractCard makeCopy() {
        return new LegSweep();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Leg Sweep");
    }
}
