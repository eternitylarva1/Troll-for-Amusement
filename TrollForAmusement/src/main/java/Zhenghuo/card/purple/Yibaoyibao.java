package Zhenghuo.card.purple;

import Zhenghuo.actions.ChangePlayerAction;
import Zhenghuo.utils.Invoker;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Metamorphosis;
import com.megacrit.cardcrawl.cards.tempCards.ThroughViolence;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Zhenghuo.modcore.ExampleMod.NowPlayer;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.topPanel;

public class Yibaoyibao extends AbstractCard {
    public static final String ID = "ThroughViolence";
    private static final CardStrings cardStrings;
    private static final Metamorphosis metamorphosis=new Metamorphosis();

    public Yibaoyibao() {
        super("ThroughViolence", cardStrings.NAME, "colorless/attack/through_violence", 0, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseMagicNumber = 30;
        if(!(AbstractDungeon.player ==null)) {
            this.rawDescription= cardStrings.DESCRIPTION;
            this.rawDescription += " NL 你的" +  Invoker.getField(topPanel, "title") + "的最高连胜场数是" + ChangePlayerAction.getPlayerClass().getCharStat().bestWinStreak;
        this.initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    int i= ChangePlayerAction.getPlayerClass().getCharStat().bestWinStreak;
    this.addToBot(new JudgementAction(m,i));
    }
    public void applyPowers() {
        super.applyPowers();
        if(!(AbstractDungeon.player ==null)) {
            this.rawDescription= cardStrings.DESCRIPTION;
            AbstractPlayer player1=ChangePlayerAction.getPlayerClass();
            System.out.println(NowPlayer);
            System.out.println(player1.toString());
            this.rawDescription += " NL 你的" + Invoker.getField(topPanel, "title")+ "的最高连胜场数是" +player1.getCharStat().bestWinStreak;
            this.initializeDescription();


        }
    }
    public void upgrade() {
        if (!this.upgraded) {

            this.upgradeName();
            this.upgradeBaseCost(0);
        }

    }

    public AbstractCard makeCopy() {
        return new Yibaoyibao();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ThroughViolence");
    }
}
