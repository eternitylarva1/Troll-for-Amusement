package Zhenghuo.card.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Unload;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

public class Qiankunyizhi extends AbstractCard {
    public static final String ID = "Unload";
    private static final CardStrings cardStrings;

    public Qiankunyizhi() {
        super("Unload", cardStrings.NAME, "green/attack/unload", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 14;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
addToBot(new AbstractGameAction() {
    @Override
    public void update() {
   
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
        return new Qiankunyizhi();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Unload");
    }
}
