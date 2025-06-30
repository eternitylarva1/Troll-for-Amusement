package Zhenghuo.card.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

public class Houkongfan extends AbstractCard {
    public static final String ID = "Houkongfan";
    private static final CardStrings cardStrings;

    public Houkongfan() {
        super("Backflip", cardStrings.NAME, "green/skill/backflip", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
addToBot(new AbstractGameAction() {
    @Override
    public void update() {
        AbstractDungeon.player.movePosition(AbstractDungeon.player.drawX-AbstractDungeon.player.hb_w,AbstractDungeon.player.drawY);
        if(AbstractDungeon.player.drawX+AbstractDungeon.player.hb_w/2<0||AbstractDungeon.player.drawX+AbstractDungeon.player.hb_w/2> Settings.WIDTH){
            AbstractCreature target = AbstractDungeon.player;
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AbstractDungeon.getCurrRoom().smoked = true;
                this.addToBot(new VFXAction(new SmokeBombEffect(target.hb.cX, target.hb.cY)));
                AbstractDungeon.player.hideHealthBar();
                AbstractDungeon.player.isEscaping = true;
                AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
                AbstractDungeon.overlayMenu.endTurnButton.disable();
                AbstractDungeon.player.escapeTimer = 2.5F;
            }
        }
        isDone=true;
    }
});
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }

    }

    public AbstractCard makeCopy() {
        return new Houkongfan();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Houkongfan");
    }
}
