package Zhenghuo.actions;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import Zhenghuo.card.TongpeiCard;
import Zhenghuo.modcore.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Zhenghuo.actions.FusionAction.cardResult;
import static Zhenghuo.actions.GatherCharacterAction.result;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.gridSelectScreen;

public class BetteFusionCharacterAction extends AbstractGameAction {
    public static final String[] TEXT= new String[]{"",""};
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    public BetteFusionCharacterAction(int numberOfCards, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public BetteFusionCharacterAction(int numberOfCards) {
        this(numberOfCards, false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.numberOfCards > 0) {
                AbstractCard c;
                Iterator var6;
                if (this.player.drawPile.size() <= this.numberOfCards && !this.optional) {
                    /*
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();
                    var6 = this.player.drawPile.group.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        cardsToMove.add(c);
                    }

                    var6 = cardsToMove.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        if (this.player.hand.size() == 10) {
                            this.player.drawPile.moveToDiscardPile(c);
                            this.player.createHandIsFullDialog();
                        } else {
                            this.player.drawPile.moveToHand(c, this.player.drawPile);
                        }
                    }
*/
                    this.isDone = true;
                } else {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    var6 = AbstractDungeon.player.drawPile.group.iterator();
                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        if(c.hasTag(CustomTags.WordCard)) {
                            temp.addToTop(c);
                            c.isGlowing=false;
                        }
                    }
                    Iterator<AbstractCard> var7 = AbstractDungeon.player.hand.group.iterator();
                    while(var7.hasNext()) {
                        c = (AbstractCard)var7.next();
                        if(c.hasTag(CustomTags.WordCard)) {
                            temp.addToTop(c);
                            c.isGlowing=false;
                        }
                        else {
                            temp.addToBottom(c);
                            c.isGlowing=false;
                        }

                    }
                    Iterator<AbstractCard> var8 = AbstractDungeon.player.discardPile.group.iterator();
                    while(var8.hasNext()) {
                        c = (AbstractCard)var8.next();
                        if(c.hasTag(CustomTags.WordCard)) {
                            temp.addToTop(c);
                            c.isGlowing=false;
                        }
                    }
                    FusionAction.isFusion=true;
                    if (this.optional) {
                        gridSelectScreen.open(temp, this.numberOfCards, true, "请组合需要的卡牌(手牌+所有文字牌)");
                    } else {
                        gridSelectScreen.open(temp, this.numberOfCards, "请组合需要的卡牌，(手牌+所有文字牌", false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!gridSelectScreen.selectedCards.isEmpty()) {
                AbstractCard c;
                Iterator var4 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                ArrayList<Character> ChList = new ArrayList<>();
                int upgradenum=0;
                while(var4.hasNext()) {
                    c = (AbstractCard)var4.next();


                    if(!(c instanceof TongpeiCard)){

                        System.out.println("正在尝试组合"+c.name);
                        for (char ch : c.name.toCharArray()) {
                            if(!(ch == "+".charAt(0)||Character.isDigit(ch))) {
                                ChList.add(ch);
                                System.out.println("已将"+ch+"加入检索序列");
                            }
                            else if(Character.isDigit(ch)){
                                int number = ch - '0'-1;
                                System.out.println("检测到数字"+number+"转换成升级");

                                upgradenum+=number;

                            }else{
                                upgradenum++;
                            }


                        }
                    }
                    else{
                        System.out.println("检测到名字为通配符，改为*");
                        ChList.add("*".charAt(0));
                    }
                }

                //List<AbstractCard> result =result(ChList);
                if(cardResult!=null){
                    float PADDING = 25.0F * Settings.scale;
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(cardResult, (float)Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, (float)Settings.HEIGHT / 2.0F));
                }

                else
                {
                    System.out.println("未检测到符合项");
                    Iterator var5 = gridSelectScreen.selectedCards.iterator();
                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        if(AbstractDungeon.player.hand.group.contains(c)) {
                            AbstractDungeon.player.hand.moveToHand(c);
                        }
                    }
                }

                gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {

    }
}
