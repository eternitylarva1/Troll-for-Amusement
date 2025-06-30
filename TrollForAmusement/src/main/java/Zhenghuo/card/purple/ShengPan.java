package Zhenghuo.card.purple;

import Zhenghuo.actions.ChangePlayerAction;
import Zhenghuo.utils.Invoker;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Metamorphosis;
import com.megacrit.cardcrawl.cards.purple.Judgement;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;

import javax.smartcardio.Card;

import static Zhenghuo.modcore.ExampleMod.NowPlayer;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.topPanel;

public class ShengPan extends AbstractCard {
    public static final String ID = "Judgement";
    private static final CardStrings cardStrings;
    private static final Metamorphosis metamorphosis=new Metamorphosis();

    public ShengPan() {
        super("Judgement", cardStrings.NAME, "purple/skill/judgment", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseMagicNumber = 30;
        if(!(AbstractDungeon.player ==null)) {
            this.rawDescription= cardStrings.DESCRIPTION;
            this.rawDescription += " NL 你的" +  Invoker.getField(topPanel, "title") + "的最高连胜场数是" + ChangePlayerAction.getPlayerClass().getCharStat().bestWinStreak;
        this.initializeDescription();
        }    }

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
        return new ShengPan();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Judgement");
    }
}
