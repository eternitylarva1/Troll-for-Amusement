package Zhenghuo.card.blue;

import Zhenghuo.modcore.ExampleMod;
import Zhenghuo.utils.ScreenDarkener;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Darkness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class qihei extends AbstractCard {
    public static final String ID = "Darkness";
    private static final CardStrings cardStrings;

    public qihei() {
        super("Darkness", cardStrings.NAME, "blue/skill/darkness", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ChannelAction(new Dark()));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
    @Override
    public void update() {
        if(  ExampleMod.screendarkeners.isEmpty()) {
            ScreenDarkener screenDarkener = new ScreenDarkener();
            ExampleMod.screendarkeners.add(screenDarkener);
        }else {ExampleMod.screendarkeners.get(0).increaseDarkness();}
     isDone=true;
};

    });
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
        return new qihei();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Darkness");
    }
}
