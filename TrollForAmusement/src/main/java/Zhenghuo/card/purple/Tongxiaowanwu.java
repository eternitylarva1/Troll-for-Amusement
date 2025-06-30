package Zhenghuo.card.purple;

import Zhenghuo.actions.DiscoveryAction2;
import Zhenghuo.utils.Invoker;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.Slot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Omniscience;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BustedCrown;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class Tongxiaowanwu extends AbstractCard {
    public static final String ID = "Omniscience";
    private static final CardStrings cardStrings;

    public Tongxiaowanwu() {
        super("Omniscience", cardStrings.NAME, "purple/skill/omniscience", 4, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    this.addToBot(new DiscoveryAction2());

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new Tongxiaowanwu();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Omniscience");
    }
}
