package Zhenghuo.actions;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import Zhenghuo.card.CharacterCard;
import Zhenghuo.card.Characterfusion;
import Zhenghuo.card.TongpeiCard;
import Zhenghuo.modcore.CustomTags;
import Zhenghuo.modcore.ExampleMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Zhenghuo.actions.GatherCharacterAction.result;

public class FusionAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean endTurn;
    public static int numDiscarded;
    private static final float DURATION;
    private int num=0;
    public static   AbstractCard cardResult;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList();
    public static boolean isFusion=false;
    public FusionAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this(target, source, amount, isRandom, false);
    }

    public FusionAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean endTurn) {
        this.p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.actionType = ActionType.SPECIAL;
        this.endTurn = endTurn;
        this.duration = DURATION;
    }


    public void update() {
        AbstractCard c;
        Iterator var1;

        if (this.duration == DURATION) {

            var1 = this.p.hand.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (!(c.hasTag(CustomTags.WordCard))) {
                    this.cannotUpgrade.add(c);
                }
            }

            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            this.p.hand.group.removeAll(this.cannotUpgrade);
            isFusion=true;
        if (!this.isRandom) {
                if (this.amount < 0) {
                    AbstractDungeon.handCardSelectScreen.open("选择需要融合的牌", 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();
                    this.tickDuration();
                    return;
                }
                numDiscarded = this.amount;
                if (this.p.hand.size() > this.amount) {
                    AbstractDungeon.handCardSelectScreen.open("选择需要融合的牌", this.amount, true,true);
                }
                else{
                    AbstractDungeon.handCardSelectScreen.open("选择需要融合的牌", this.p.hand.size(), true,true);
                }
                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }
        }
/*
if(cardResult!=null){
    ExampleMod.renderable.add(spriteBatch -> {
        cardResult.render(spriteBatch);
    });
}*/
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                isFusion=false;


                if(cardResult!=null){
                    float PADDING = 25.0F * Settings.scale;
//AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardResult));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(cardResult, (float)Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, (float)Settings.HEIGHT / 2.0F));
                   }
                else
                {
                    System.out.println("未检测到符合项");
                    Iterator var5 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        AbstractDungeon.player.hand.moveToHand(c);
                    }
                }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.returnCards();
        }

        this.tickDuration();
    }
    private void returnCards() {
        Iterator var1 = this.cannotUpgrade.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}

