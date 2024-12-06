package Zhenghuo.actions;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import Zhenghuo.card.CharacterCard;
import Zhenghuo.card.TongpeiCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static Zhenghuo.actions.GatherCharacterAction.result;

import static Zhenghuo.utils.CardArguments.RewardPatch.ModifiedCards;
import static Zhenghuo.utils.CardArguments.RewardPatch.Relics;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.gridSelectScreen;

public class BetteGatheRelicAction extends AbstractGameAction {
    public static final String[] TEXT= new String[]{"",""};
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    public BetteGatheRelicAction(int numberOfCards, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public BetteGatheRelicAction(int numberOfCards) {
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
                    CardGroup temp = new CardGroup(CardGroupType.UNSPECIFIED);
                    var6 = AbstractDungeon.player.drawPile.group.iterator();
                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        if(c instanceof CharacterCard||c instanceof TongpeiCard) {
                            temp.addToTop(c);
                            c.isGlowing=false;
                        }
                    }
                    Iterator<AbstractCard> var7 = AbstractDungeon.player.hand.group.iterator();
                    while(var7.hasNext()) {
                        c = (AbstractCard)var7.next();
                        if(c instanceof CharacterCard||c instanceof TongpeiCard) {
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
                        if(c instanceof CharacterCard ||c instanceof TongpeiCard) {
                            temp.addToTop(c);
                            c.isGlowing=false;
                        }
                    }

                    if (this.optional) {
                        gridSelectScreen.open(temp, this.numberOfCards, true, "请组合需要的卡牌");
                    } else {
                        gridSelectScreen.open(temp, this.numberOfCards, "请组合需要的卡牌", false);
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

                List<AbstractRelic> result =resultRelic(ChList);
                if(!result.isEmpty()){
                    System.out.println("检测到符合项目，开始获取结果");
                    var4 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                    while(var4.hasNext()) {
                        c = (AbstractCard)var4.next();
                       AbstractDungeon.player.hand.removeCard(c);
                       AbstractDungeon.player.discardPile.removeCard(c);
                       AbstractDungeon.player.drawPile.removeCard(c);
                    }


                    AbstractRelic cm=result.get(AbstractDungeon.cardRandomRng.random(0,result.size()-1)).makeCopy();
                    if(!(AbstractDungeon.player.hasRelic(cm.relicId))){
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2),cm);
                    }else
                    {
                     System.out.println("重复了");
                     AbstractDungeon.effectsQueue.add(new ThoughtBubble(AbstractDungeon.player.animX,AbstractDungeon.player.animY,"不能合成重复的遗物",true));

                    }




                    System.out.println("已经合成"+cm.name);

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
    public static List<AbstractRelic> resultRelic (List<Character> charList) {
            return Relics.stream()
                    .filter(obj -> {
                        String name = obj.name;

                        // 将 name 转换为字符集
                        List<Character> nameCharList = name.chars()
                                .mapToObj(c -> (char) c)
                                .collect(Collectors.toList());

                        // 复制一份字符集以供匹配
                        List<Character> charListCopy = charList.stream()
                                .collect(Collectors.toList());

                        // 检查字符串中的字符是否能在字符集中找到对应项或被通配符替代
                        for (char c : nameCharList) {
                            if (charListCopy.contains(c)) {
                                charListCopy.remove((Character) c);
                            } else if (charListCopy.contains('*')) {
                                charListCopy.remove((Character) '*');
                            } else {
                                return false; // 无法匹配字符时，过滤掉这个字符串
                            }
                        }

                        // 最终，charListCopy 应该只剩下未使用的字符
                        return charListCopy.isEmpty();
                    })
                    .collect(Collectors.toList());
    }


    static {

    }
}
