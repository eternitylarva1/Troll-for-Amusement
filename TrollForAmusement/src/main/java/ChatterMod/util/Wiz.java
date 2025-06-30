/*     */ package ChatterMod.util;
/*     */ 
/*     */ import ChatterMod.actions.TimedVFXAction;
/*     */ import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*     */ import com.megacrit.cardcrawl.actions.animations.VFXAction;
/*     */ import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
/*     */ import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
/*     */ import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.characters.AbstractPlayer;
/*     */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*     */ import com.megacrit.cardcrawl.helpers.CardLibrary;
/*     */ import com.megacrit.cardcrawl.helpers.Hitbox;
/*     */ import com.megacrit.cardcrawl.helpers.input.InputHelper;
/*     */ import com.megacrit.cardcrawl.monsters.AbstractMonster;
/*     */ import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
/*     */ import com.megacrit.cardcrawl.random.Random;
/*     */ import com.megacrit.cardcrawl.rooms.AbstractRoom;
/*     */ import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Wiz
/*     */ {
/*  34 */   public static AbstractPlayer adp() { return AbstractDungeon.player; }
/*     */ 
/*     */   
/*     */   public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
/*  38 */     for (AbstractCard c : cardsList) {
/*  39 */       consumer.accept(c);
/*     */     }
/*     */   }
/*     */   
/*     */   public static ArrayList<AbstractCard> getAllCardsInCardGroups(boolean includeHand, boolean includeExhaust, boolean includePlant) {
/*  44 */     ArrayList<AbstractCard> masterCardsList = new ArrayList<AbstractCard>();
/*  45 */     masterCardsList.addAll(AbstractDungeon.player.drawPile.group);
/*  46 */     masterCardsList.addAll(AbstractDungeon.player.discardPile.group);
/*  47 */     if (includeHand) {
/*  48 */       masterCardsList.addAll(AbstractDungeon.player.hand.group);
/*     */     }
/*  50 */     if (includeExhaust) {
/*  51 */       masterCardsList.addAll(AbstractDungeon.player.exhaustPile.group);
/*     */     }
/*  53 */     return masterCardsList;
/*     */   }
/*     */   
/*     */   public static void forAllMonstersLiving(Consumer<AbstractMonster> consumer) {
/*  57 */     for (AbstractMonster m : getEnemies()) {
/*  58 */       consumer.accept(m);
/*     */     }
/*     */   }
/*     */
/*     */   public static ArrayList<AbstractMonster> getEnemies() {
/*  63 */     ArrayList<AbstractMonster> monsters = new ArrayList((AbstractDungeon.getMonsters()).monsters);
/*  64 */     monsters.removeIf(m -> (m.isDead || m.isDying));
/*  65 */     return monsters;
/*     */   }
/*     */ 
/*     */   
/*  69 */   public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred) { return getCardsMatchingPredicate(pred, false); }
/*     */ 
/*     */   
/*     */   public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred, boolean allcards) {
/*  73 */     if (allcards) {
/*  74 */       ArrayList<AbstractCard> cardsList = new ArrayList<AbstractCard>();
/*  75 */       for (AbstractCard c : CardLibrary.getAllCards()) {
/*  76 */         if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy()); 
/*     */       } 
/*  78 */       return cardsList;
/*     */     } 
/*  80 */     ArrayList<AbstractCard> cardsList = new ArrayList<AbstractCard>();
/*  81 */     for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
/*  82 */       if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy()); 
/*     */     } 
/*  84 */     for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
/*  85 */       if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy()); 
/*     */     } 
/*  87 */     for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
/*  88 */       if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy()); 
/*     */     } 
/*  90 */     return cardsList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred, boolean allCards) { return (AbstractCard)getRandomItem(getCardsMatchingPredicate(pred, allCards)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred) { return returnTrulyRandomPrediCardInCombat(pred, false); }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static <T> T getRandomItem(ArrayList<T> list, Random rng) { return (T)(list.isEmpty() ? null : list.get(rng.random(list.size() - 1))); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static <T> T getRandomItem(ArrayList<T> list) { return (T)getRandomItem(list, AbstractDungeon.cardRandomRng); }
/*     */ 
/*     */ 
/*     */   
/* 112 */   private static boolean actuallyHovered(Hitbox hb) { return (InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y && InputHelper.mY < hb.y + hb.height); }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static boolean isInCombat() { return (CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT); }
/*     */ 
/*     */ 
/*     */   
/* 120 */   public static boolean isCombatCard(AbstractCard card) { return (!(adp()).masterDeck.contains(card) && !CardCrawlGame.cardPopup.isOpen); }
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static void atb(AbstractGameAction action) { AbstractDungeon.actionManager.addToBottom(action); }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public static void att(AbstractGameAction action) { AbstractDungeon.actionManager.addToTop(action); }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static void vfx(AbstractGameEffect gameEffect) { atb(new VFXAction(gameEffect)); }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public static void vfx(AbstractGameEffect gameEffect, float duration) { atb(new VFXAction(gameEffect, duration)); }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public static void tfx(AbstractGameEffect gameEffect) { atb(new TimedVFXAction(gameEffect)); }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static void makeInHand(AbstractCard c, int i) { atb(new MakeTempCardInHandAction(c, i)); }
/*     */ 
/*     */ 
/*     */   
/* 148 */   public static void makeInHand(AbstractCard c) { makeInHand(c, 1); }
/*     */ 
/*     */ 
/*     */   
/* 152 */   public static void shuffleIn(AbstractCard c, int i) { atb(new MakeTempCardInDrawPileAction(c, i, true, true)); }
/*     */ 
/*     */ 
/*     */   
/* 156 */   public static void shuffleIn(AbstractCard c) { shuffleIn(c, 1); }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public static void topDeck(AbstractCard c, int i) { AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true)); }
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static void topDeck(AbstractCard c) { topDeck(c, 1); }
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static void applyToEnemy(AbstractMonster m, AbstractPower po) { atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount)); }
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static void applyToEnemyTop(AbstractMonster m, AbstractPower po) { att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount)); }
/*     */ 
/*     */ 
/*     */   
/* 176 */   public static void applyToSelf(AbstractPower po) { atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount)); }
/*     */ 
/*     */ 
/*     */   
/* 180 */   public static void applyToSelfTop(AbstractPower po) { att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount)); }
/*     */ 
/*     */ 
/*     */   
/* 184 */   public static boolean isUnaware(AbstractMonster m) { return (m != null && !m.isDeadOrEscaped() && m.getIntentDmg() < 5); }
/*     */ 
/*     */   
/*     */   public static boolean anyMonsterUnaware() {
/* 188 */     for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
/* 189 */       if (isUnaware(m)) {
/* 190 */         return true;
/*     */       }
/*     */     } 
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 197 */   public static boolean isAttacking(AbstractMonster m) { return (m != null && !m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0); }
/*     */ 
/*     */   
/*     */   public static boolean anyMonsterAttacking() {
/* 201 */     for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
/* 202 */       if (isAttacking(m)) {
/* 203 */         return true;
/*     */       }
/*     */     } 
/* 206 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean anyMonsterNotAttacking() {
/* 210 */     for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
/* 211 */       if (!isAttacking(m)) {
/* 212 */         return true;
/*     */       }
/*     */     } 
/* 215 */     return false;
/*     */   }
/*     */   
/*     */   public static List<AbstractCard> cardsPlayedThisCombat() {
/* 219 */     if (isInCombat()) {
/* 220 */       return AbstractDungeon.actionManager.cardsPlayedThisCombat;
/*     */     }
/* 222 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public static List<AbstractCard> cardsPlayedThisTurn() {
/* 226 */     if (isInCombat()) {
/* 227 */       return AbstractDungeon.actionManager.cardsPlayedThisTurn;
/*     */     }
/* 229 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public static AbstractCard lastCardPlayed() {
/* 233 */     if (isInCombat() && !cardsPlayedThisCombat().isEmpty()) {
/* 234 */       return (AbstractCard)cardsPlayedThisCombat().get(cardsPlayedThisCombat().size() - 1);
/*     */     }
/* 236 */     return null;
/*     */   }
/*     */   
/*     */   public static AbstractCard lastCardPlayedThisTurn() {
/* 240 */     if (isInCombat() && !cardsPlayedThisTurn().isEmpty()) {
/* 241 */       return (AbstractCard)cardsPlayedThisTurn().get(cardsPlayedThisTurn().size() - 1);
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */   
/*     */   public static AbstractCard secondLastCardPlayed() {
/* 247 */     if (isInCombat() && cardsPlayedThisCombat().size() >= 2) {
/* 248 */       return (AbstractCard)cardsPlayedThisCombat().get(cardsPlayedThisCombat().size() - 2);
/*     */     }
/* 250 */     return null;
/*     */   }
/*     */   
/*     */   public static void forAdjacentCards(AbstractCard thisCard, Consumer<AbstractCard> consumer) {
/* 254 */     int lastIndex = (adp()).hand.group.indexOf(thisCard);
/* 255 */     if (lastIndex != -1) {
/* 256 */       if (lastIndex > 0) {
/* 257 */         consumer.accept((adp()).hand.group.get(lastIndex - 1));
/*     */       }
/* 259 */       if (lastIndex < (adp()).hand.group.size() - 1) {
/* 260 */         consumer.accept((adp()).hand.group.get(lastIndex + 1));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ArrayList<AbstractCard> getAdjacentCards(AbstractCard thisCard) {
/* 266 */     ArrayList<AbstractCard> ret = new ArrayList<AbstractCard>();
/* 267 */     forAdjacentCards(thisCard, ret::add);
/* 268 */     return ret;
/*     */   }
/*     */   
/*     */   public static void forAdjacentMonsters(AbstractCreature entity, Consumer<AbstractMonster> consumer) {
/* 272 */     ArrayList<Hitbox> hitboxes = new ArrayList<Hitbox>();
/* 273 */     HashMap<Hitbox, AbstractMonster> hitMap = new HashMap<Hitbox, AbstractMonster>();
/* 274 */     hitboxes.add(entity.hb);
/* 275 */     for (AbstractMonster m : getEnemies()) {
/* 276 */       hitMap.put(m.hb, m);
/* 277 */       if (!hitboxes.contains(m.hb)) {
/* 278 */         hitboxes.add(m.hb);
/*     */       }
/*     */     } 
/* 281 */     hitboxes.sort((h1, h2) -> Float.compare(h1.cX, h2.cX));
/* 282 */     int index = hitboxes.indexOf(entity.hb);
/* 283 */     if (index > 0) {
/* 284 */       consumer.accept(hitMap.get(hitboxes.get(index - 1)));
/*     */     }
/* 286 */     if (index < hitboxes.size() - 1) {
/* 287 */       consumer.accept(hitMap.get(hitboxes.get(index + 1)));
/*     */     }
/*     */   }
/*     */   
/*     */   public static ArrayList<AbstractMonster> getAdjacentMonsters(AbstractCreature entity) {
/* 292 */     ArrayList<AbstractMonster> ret = new ArrayList<AbstractMonster>();
/* 293 */     forAdjacentMonsters(entity, ret::add);
/* 294 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMo\\util\Wiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */