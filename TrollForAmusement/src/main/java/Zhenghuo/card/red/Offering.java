//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Zhenghuo.card.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.cards.purple.EmptyFist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.Bonfire;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;


import java.util.ArrayList;

public class Offering extends AbstractCard {
    public static final String ID = "Offering";
    private static final CardStrings cardStrings;

    public Offering() {
        super("Offering", cardStrings.NAME, "red/skill/offering", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new OfferingEffect(), 0.1F));
        } else {
            this.addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        }

        this.addToBot(new LoseHPAction(p, p, 6));
        this.addToBot(new GainEnergyAction(2));
        this.addToBot(new DrawCardAction(p, this.magicNumber));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone=true;
                AbstractDungeon.player.increaseMaxHp(10, false);
                AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                AbstractCard offeredCard=findFirstMatchingUuid(AbstractDungeon.player.masterDeck.group);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(offeredCard, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(offeredCard);
            }
        });

    }
    public <T extends AbstractCard> T findFirstMatchingUuid(ArrayList<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        for (T item : list) {
            if (item.uuid.equals(this.uuid)) {
                return item;
            }
        }
        return null;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new Offering();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Offering");
    }
}
