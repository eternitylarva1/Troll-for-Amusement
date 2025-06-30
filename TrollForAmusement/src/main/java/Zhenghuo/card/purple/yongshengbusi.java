package Zhenghuo.card.purple;

import Zhenghuo.actions.DiscoveryAction1;
import Zhenghuo.powers.LiveForeverPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

public class yongshengbusi extends AbstractCard {
    public static final String ID = "LiveForever";
    private static final CardStrings cardStrings;

    public yongshengbusi() {
        super("LiveForever", cardStrings.NAME, "colorless/power/live_forever", -2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new VFXAction(new BorderFlashEffect(Color.CHARTREUSE, true)));
        this.addToBot(new VFXAction(p, new MiracleEffect(Color.CHARTREUSE, Color.LIME, "BLOCK_GAIN_1"), 1.0F));
        this.addToBot(new ApplyPowerAction(p, p, new LiveForeverPower(p, -1), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }
    public AbstractCard makeCopy() {
        return new yongshengbusi();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("LiveForever");
    }
}
