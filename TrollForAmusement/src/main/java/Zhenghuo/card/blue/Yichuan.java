package Zhenghuo.card.blue;



//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.stats.RunData;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class Yichuan extends AbstractCard {
    public static final String ID = "Yichuan";
    private static final CardStrings cardStrings;

    public Yichuan() {
        super("Yichuan", cardStrings.NAME, "blue/skill/genetic_algorithm", 3, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        this.misc = 1;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = this.misc;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
addToBot(new AbstractGameAction() {
    @Override
    public void update() {
        AbstractDungeon.player.hand.group.clear();
        AbstractDungeon.player.drawPile.group.clear();
        AbstractDungeon.player.discardPile.group.clear();
        ArrayList<RunData> rdlist = getmyVictories(0);
        if (!rdlist.isEmpty()) {
            setmyLoadout((RunData)rdlist.get(0));}
        isDone=true;
    }
});
    }



    public void upgrade() {
        if (!this.upgraded) {
           this.upgradeBaseCost(0);
            this.upgradeName();
        }

    }

    public AbstractCard makeCopy() {
        return new Yichuan();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Yichuan");
    }


    private static ArrayList<RunData> getmyVictories(int character) {
        new ArrayList();
        RunHistoryScreen rhs = new RunHistoryScreen();
        rhs.refreshData();
        if (character > 0) {
            ((DropdownMenu) ReflectionHacks.getPrivate(rhs, RunHistoryScreen.class, "characterFilter")).setSelectedIndex(character);
        }

        ((DropdownMenu)ReflectionHacks.getPrivate(rhs, RunHistoryScreen.class, "winLossFilter")).setSelectedIndex(1);

        try {
            Method resetRunsDropdown = RunHistoryScreen.class.getDeclaredMethod("resetRunsDropdown");
            resetRunsDropdown.setAccessible(true);
            resetRunsDropdown.invoke(rhs);
        } catch (Exception var4) {
        }

        return (ArrayList)ReflectionHacks.getPrivate(rhs, RunHistoryScreen.class, "filteredRuns");
    }

    private static void setmyLoadout(RunData rd) {
        Iterator var2;

        String card;/*
        while(var2.hasNext()) {
            card = (String)var2.next();
            AbstractRelic ar = RelicLibrary.getRelic(card);
            ar.instantObtain();
        }
*/
        AbstractDungeon.player.drawPile.group.clear();

        AbstractCard ac;
        for(var2 = rd.master_deck.iterator(); var2.hasNext(); AbstractDungeon.player.drawPile.group.add(ac)) {
            card = (String)var2.next();
            if (card.matches(".*\\+\\d+")) {
                int index = card.lastIndexOf("+");
                ac = CardLibrary.getCopy(card.substring(0, index), index, 0);
            } else {
                ac = CardLibrary.getCopy(card, 0, 0);
            }
        }

    }

}
