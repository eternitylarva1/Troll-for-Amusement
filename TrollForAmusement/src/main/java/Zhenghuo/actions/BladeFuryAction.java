//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Zhenghuo.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Caltrops;
import com.megacrit.cardcrawl.cards.red.Clothesline;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.cards.red.Metallicize;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BladeFuryAction extends AbstractGameAction {
    private boolean upgrade;

    public BladeFuryAction(boolean upgraded) {
        this.upgrade = upgraded;
    }

    public void update() {
        int theSize = AbstractDungeon.player.hand.size();
        if (this.upgrade) {
            AbstractCard s = (new Shiv()).makeCopy();
            s.upgrade();
            this.addToTop(new MakeTempCardInHandAction(s, theSize));
        } else {
            for(int i = 0; i < theSize; i++) {
                if (AbstractDungeon.cardRandomRng.randomBoolean(0.25f)) {
                    this.addToTop(new MakeTempCardInHandAction(new Metallicize()));
                } else if (AbstractDungeon.cardRandomRng.randomBoolean(0.33f)) {
                    this.addToTop(new MakeTempCardInHandAction(new IronWave()));
                } else if (AbstractDungeon.cardRandomRng.randomBoolean(0.5f)) {
                    this.addToTop(new MakeTempCardInHandAction(new Clothesline()));
                } else {

                    this.addToTop(new MakeTempCardInHandAction(new Caltrops()));
                }
            }

        }

        this.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));
        this.isDone = true;
    }
}
