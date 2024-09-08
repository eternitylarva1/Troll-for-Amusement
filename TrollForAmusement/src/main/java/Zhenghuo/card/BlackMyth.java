package Zhenghuo.card;


import Zhenghuo.helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class BlackMyth extends CustomCard {

    public static final String ID = ModHelper.makePath("BlackMyth");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "ZhenghuoResources/images/BlackMyth.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CardColor.RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public BlackMyth() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust=true;
        this.magicNumber = this.baseMagicNumber = 100;

    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeBaseCost(1); // 将该卡牌的伤害提高3点。
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AbstractGameAction() {
            private void upgradeAllCardsInGroup(CardGroup cardGroup) {
                Iterator var2 = cardGroup.group.iterator();

                while(var2.hasNext()) {
                    AbstractCard c = (AbstractCard)var2.next();
                    AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                    this.addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {

                            cardGroup.removeCard(c);

                            ArrayList<AbstractCard> tempPool = new ArrayList<>();
                            CardLibrary.addBlueCards(tempPool);
                            AbstractCard a=tempPool.get(AbstractDungeon.cardRandomRng.random(0,tempPool.size()-1));

                            switch (cardGroup.type)
                            {
                                case HAND:
                                    this.addToTop(new MakeTempCardInHandAction(a));
                                    break;
                                case DISCARD_PILE:
                                    cardGroup.addToTop(a);
                                    break;
                                case EXHAUST_PILE:
                                    cardGroup.addToTop(a);
                                    break;
                                case DRAW_PILE:
                                    cardGroup.addToTop(a);
                                    break;
                            }
                            if (a.canUpgrade()) {
                                if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                                    a.superFlash();
                                }

                                a.upgrade();
                                a.applyPowers();
                            }
                            isDone=true;
                        }
                    });

                }

            }
            @Override
            public void update() {

                    AbstractPlayer p = AbstractDungeon.player;
                    this.upgradeAllCardsInGroup(p.hand);
                    this.upgradeAllCardsInGroup(p.drawPile);
                    this.upgradeAllCardsInGroup(p.discardPile);
                    this.upgradeAllCardsInGroup(p.exhaustPile);
                    this.isDone = true;

            }
        });
    }

    }




