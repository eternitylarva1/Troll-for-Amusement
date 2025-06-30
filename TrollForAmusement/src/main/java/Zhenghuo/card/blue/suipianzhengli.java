package Zhenghuo.card.blue;

import Zhenghuo.utils.GetFreeSpace;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class suipianzhengli extends AbstractCard {
    public static final String ID = "Defragment";
    private static final CardStrings cardStrings;

    public suipianzhengli() {
        super("Defragment", cardStrings.NAME, "blue/power/defragment", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.damage=this.baseDamage= GetFreeSpace.GetFreeSpaces();
   this.upgradeBaseCost(GetFreeSpace.GetFullSpaces()/200+1);


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
this.addToBot(new DamageAction(p, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }

    public AbstractCard makeCopy() {
        return new suipianzhengli();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defragment");
    }

}

