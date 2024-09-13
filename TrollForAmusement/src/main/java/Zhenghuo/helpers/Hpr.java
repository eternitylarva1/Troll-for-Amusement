package Zhenghuo.helpers;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hpr {
    private static final Logger logger = LogManager.getLogger(Hpr.class);

    public Hpr() {
    }

    public static boolean HasRelicOrUpgradedVersion(String id) {
        return AbstractDungeon.player.hasRelic(id) || AbstractDungeon.player.hasRelic(id + "Upgraded");
    }

    public static int GetRelicAmount(String id) {
        int amt = 0;
        if (AbstractDungeon.player != null) {
            amt = (int)Player().relics.stream().filter((r) -> {
                return id.equals(r.relicId);
            }).count();
        }

        return amt;
    }

    public static boolean outOfScreen(float x, float y) {
        return x < 0.0F || x > (float)Settings.WIDTH || y < 0.0F && y > (float)Settings.HEIGHT;
    }

    public static boolean isAttackCostZero(AbstractCard card) {
        return card.type == CardType.ATTACK && (card.costForTurn == 0 || card.freeToPlayOnce && card.cost != -1);
    }

    public static boolean isCostZero(AbstractCard card) {
        return card.costForTurn == 0 || card.freeToPlayOnce && card.cost != -1;
    }

    public static AbstractMonster getRandomMonsterSafe() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        return m != null && !m.isDeadOrEscaped() && !m.isDead ? m : null;
    }

    public static boolean isInBattle() {
        return CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT;
    }

    public static AbstractPlayer Player() {
        return AbstractDungeon.player;
    }

    public static ArrayList<AbstractMonster> monsters() {
        return AbstractDungeon.getMonsters() == null ? new ArrayList() : AbstractDungeon.getMonsters().monsters;
    }

    public static boolean isAlive(AbstractCreature c) {
        return c != null && !c.isDeadOrEscaped() && !c.isDead;
    }

    public static int aliveMonstersAmount() {
        int i = 0;
        Iterator var2 = monsters().iterator();

        while(var2.hasNext()) {
            AbstractMonster m = (AbstractMonster)var2.next();
            if (isAlive(m)) {
                ++i;
            }
        }

        return i;
    }

    public static void addToNext(AbstractGameAction action) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            int index = Math.min(AbstractDungeon.actionManager.actions.size(), 1);
            AbstractDungeon.actionManager.actions.add(index, action);
        }

    }

    public static void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public static void addToBotAbstract(final VoidSupplier func) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                func.run();
                this.isDone = true;
            }
        });
    }

    public static void addToTopAbstract(final VoidSupplier func) {
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            public void update() {
                func.run();
                this.isDone = true;
            }
        });
    }

    public static void addEffect(AbstractGameEffect effect) {
        AbstractDungeon.effectList.add(effect);
    }

    public static void GainRelic(AbstractRelic r) {
        AbstractDungeon.player.relics.add(r);
        r.onEquip();
        AbstractDungeon.player.reorganizeRelics();
    }

    public static void info(String s) {
        logger.info(s);
    }

    public static AbstractCard makeStatEquivalentCopy(AbstractCard c) {
        AbstractCard card = c.makeStatEquivalentCopy();
        card.retain = c.retain;
        card.selfRetain = c.selfRetain;
        card.purgeOnUse = c.purgeOnUse;
        card.isEthereal = c.isEthereal;
        card.exhaust = c.exhaust;
        card.glowColor = c.glowColor;
        card.rawDescription = c.rawDescription;
        card.cardsToPreview = c.cardsToPreview;
        card.initializeDescription();
        return card;
    }

    public static void foreachCardNotExhausted(Function<AbstractCard, Boolean> func) {
        Iterator var2 = AbstractDungeon.player.drawPile.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if ((Boolean)func.apply(c)) {
                return;
            }
        }

        var2 = AbstractDungeon.player.hand.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if ((Boolean)func.apply(c)) {
                return;
            }
        }

        var2 = AbstractDungeon.player.discardPile.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if ((Boolean)func.apply(c)) {
                return;
            }
        }

    }

    public static void foreachCardNotExhaustedNotHand(Function<AbstractCard, Boolean> func) {
        Iterator var2 = AbstractDungeon.player.drawPile.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if ((Boolean)func.apply(c)) {
                return;
            }
        }

        var2 = AbstractDungeon.player.discardPile.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if ((Boolean)func.apply(c)) {
                return;
            }
        }

    }

    public static void foreachPowerHeroAndMonstersHave(Function<AbstractPower, Boolean> func) {
        Iterator var2 = AbstractDungeon.player.powers.iterator();

        while(var2.hasNext()) {
            AbstractPower p = (AbstractPower)var2.next();
            if ((Boolean)func.apply(p)) {
                return;
            }
        }

        var2 = monsters().iterator();

        while(true) {
            AbstractMonster m;
            do {
                if (!var2.hasNext()) {
                    return;
                }

                m = (AbstractMonster)var2.next();
            } while(!isAlive(m));

            Iterator var4 = m.powers.iterator();

            while(var4.hasNext()) {
                AbstractPower p = (AbstractPower)var4.next();
                if ((Boolean)func.apply(p)) {
                    return;
                }
            }
        }
    }

    public static void foreachAliveMonster(Function<AbstractMonster, Boolean> func) {
        Iterator var2 = monsters().iterator();

        AbstractMonster m;
        do {
            if (!var2.hasNext()) {
                return;
            }

            m = (AbstractMonster)var2.next();
        } while(!isAlive(m) || !(Boolean)func.apply(m));

    }

    public static void tempLoseStrength(AbstractCreature mo, AbstractCreature p, int amt) {
        addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -amt), -amt, true, AttackEffect.NONE));
        if (!mo.hasPower("Artifact")) {
            addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, amt), amt, true, AttackEffect.NONE));
        }

    }

    public static void tempGainDex(AbstractCreature target, AbstractCreature source, int amt) {
        addToBot(new ApplyPowerAction(target, source, new DexterityPower(source, amt)));
        addToBot(new ApplyPowerAction(target, source, new LoseDexterityPower(source, amt)));
    }

    public static void gainEnergy(int amount) {
        if (amount != 0) {
            if (amount == -1) {
                amount = EnergyPanel.getCurrentEnergy();
            }

            AbstractPlayer p = AbstractDungeon.player;
            p.gainEnergy(amount);
            AbstractDungeon.actionManager.updateEnergyGain(amount);
            Iterator var3 = p.hand.group.iterator();

            while(var3.hasNext()) {
                AbstractCard card = (AbstractCard)var3.next();
                card.triggerOnGainEnergy(amount, true);
            }

        }
    }

    public interface VoidSupplier extends Runnable {
    }
}
