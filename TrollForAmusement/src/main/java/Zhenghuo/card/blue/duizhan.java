package Zhenghuo.card.blue;

import ChatterMod.cards.Chatter;
import Zhenghuo.modcore.ExampleMod;
import Zhenghuo.utils.ScreenDarkener;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;

public class duizhan extends AbstractCard {
    public static final String ID = "Stack";
    private static final CardStrings cardStrings;

    public duizhan() {
        super("Stack", cardStrings.NAME, "blue/skill/stack", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
        this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();


    }

    public AbstractCard makeCopy() {
        return new duizhan();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Stack");
    }
}
