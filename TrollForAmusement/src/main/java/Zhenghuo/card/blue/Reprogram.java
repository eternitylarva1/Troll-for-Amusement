package Zhenghuo.card.blue;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import Zhenghuo.actions.ChangePlayerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Reprogram extends AbstractCard {
    public static final String ID = "Chongbiancheng";
    private static final CardStrings cardStrings;
    public String player="";

    public Reprogram() {
        super(ID, cardStrings.NAME, "blue/skill/reprogram", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    public Reprogram(String player) {
        super("Reprogram", cardStrings.NAME, "blue/skill/reprogram", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        switch (player){
            case"Ironclad":
                //addToBot(new ChangePlayerAction("Ironclad"));
                this.rawDescription="变成铁甲战士";
                break;
            case"TheSilent":
                //addToBot(new ChangePlayerAction("TheSilent"));
                this.rawDescription="变成静默猎手";
                break;
                case"Watcher":
                //addToBot(new ChangePlayerAction("Watcher"));
                this.rawDescription="变成观者";
                break;
        }
        initializeDescription();
        this.player=player;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new Reprogram("Ironclad"));
        stanceChoices.add(new Reprogram("TheSilent"));
        if(this.upgraded){
            stanceChoices.add(new Reprogram("Watcher"));
        }
        this.addToBot(new ChooseOneAction(stanceChoices));
    }


    public AbstractCard makeCopy() {
        return new Reprogram();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.upgradeBaseCost(1);
        }

    }
    public void onChoseThisOption() {
addToBot(new ChangePlayerAction(this.player));

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
