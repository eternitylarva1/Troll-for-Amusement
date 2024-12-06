package Zhenghuo.actions;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import Zhenghuo.card.CardArgument;
import Zhenghuo.card.CharacterCard;
import Zhenghuo.card.TongpeiCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static Zhenghuo.patchs.playerMethodPatch.updatePatch.extractWords;
import static Zhenghuo.patchs.playerMethodPatch.updatePatch.getWords;
import static Zhenghuo.utils.CardArguments.Chimeraopened;
import static Zhenghuo.utils.CardArguments.RewardPatch.ModifiedCards;

public class GatherCharacterAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean endTurn;
    public static int numDiscarded;
    private static final float DURATION;

    public GatherCharacterAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this(target, source, amount, isRandom, false);
    }

    public GatherCharacterAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean endTurn) {
        this.p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.actionType = ActionType.DISCARD;
        this.endTurn = endTurn;
        this.duration = DURATION;
    }

    public void update() {
        AbstractCard c;
        if (this.duration == DURATION) {
            /*
            Iterator var6;
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            var6 = AbstractDungeon.player.drawPile.group.iterator();
            while(var6.hasNext()) {
                c = (AbstractCard)var6.next();
                if(c instanceof CharacterCard) {
                    temp.addToTop(c);
                }
            }
            Iterator<AbstractCard> var7 = AbstractDungeon.player.hand.group.iterator();
            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if(c instanceof CharacterCard) {
                    temp.addToTop(c);
                }
                else {
                    temp.addToBottom(c);
                }

            }
            Iterator<AbstractCard> var8 = AbstractDungeon.player.discardPile.group.iterator();
            while(var8.hasNext()) {
                c = (AbstractCard)var8.next();
                if(c instanceof CharacterCard) {
                    temp.addToTop(c);
                }
            }

            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            if (!this.isRandom) {
                if (this.amount < 0) {
                    /*AbstractDungeon.handCardSelectScreen.open("选择需要组合的牌", 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();


                        AbstractDungeon.gridSelectScreen.open(temp, 99, true, "选择需要组合的牌");

                    this.tickDuration();
                    return;
                }
*/          if (!this.isRandom) {
                if (this.amount < 0) {
                    AbstractDungeon.handCardSelectScreen.open("选择需要组合的牌", 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();
                    this.tickDuration();
                    return;
                }
                numDiscarded = this.amount;
                if (this.p.hand.size() > this.amount) {
                    AbstractDungeon.handCardSelectScreen.open("选择需要组合的牌", this.amount, true,true);
                }
                else{
                    AbstractDungeon.handCardSelectScreen.open("选择需要组合的牌", this.p.hand.size(), true,true);
                }
                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
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

                List<AbstractCard> result =result(ChList);
                if(!result.isEmpty()){
                    System.out.println("检测到符合项目，开始获取结果");
                    while(var4.hasNext()) {
                        c = (AbstractCard)var4.next();
                    AbstractDungeon.player.hand.moveToExhaustPile(c);
                    }


                    AbstractCard cm=result.get(AbstractDungeon.cardRandomRng.random(0,result.size()-1)).makeSameInstanceOf();
                    if(!(cm instanceof CharacterCard)){
                        cm.name = cm.originalName;
                    }else
                    {/*
                        AbstractCard am=new CharacterCard(true);
                        ((CharacterCard) am).isAugrment=true;
                        copyModifiers(cm,am,false,true,true);
                        cm=am;
                        cm.name=onRenderTitle(cm,cm.originalName);
                        System.out.println("已经将牌改为词条");-*/
                    }
                    if(upgradenum>0){
                        for(int i=0;i<upgradenum;i++)
                        {
                            cm.upgrade();
                        }
                    }


                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cm));
                    System.out.println("已经合成"+cm.name);

                }
                else
                {
                    System.out.println("未检测到符合项");
                    Iterator var5 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        AbstractDungeon.player.hand.moveToHand(c);
                    }
                }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }
    public static List<AbstractCard> preresult(List<Character> charList){
        return ModifiedCards.stream()
                .filter(obj -> {
                    String name = obj.name;

                    // 将 name 转换为字符集
                    List<Character> nameCharList = name.chars()
                            .mapToObj(c -> (char) c)
                            .collect(Collectors.toList());

                    // 复制一份字符集以供匹配
                    List<Character> charListCopy = new ArrayList<>(charList);

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
    public static List<AbstractCard> result (List<Character> charList){
        if(Chimeraopened())
        {
            List<Character> newcharlist;
            List<AbstractCard> resulta=preresult(charList);
                    if(resulta.isEmpty())
                    {
                        //todo 检测其中是否包含词条
                        //todo 如果包含词条，就把词条去掉，重新检测是否能组成卡牌
                        //todo 如果能组成卡牌，则组成卡牌，然后把词条加上
                        ArrayList<AbstractCard> cardlist=extractWords(charList);
                        newcharlist=getWords(charList);
                        System.out.println(newcharlist);
                        if(!newcharlist.isEmpty()) {
                        resulta=preresult(newcharlist);
                        }
                        if(!resulta.isEmpty()){
                            List<AbstractCard> resultb = new ArrayList<>();
                            for(AbstractCard c: resulta){
                                AbstractCard c1=c.makeCopy();
                              CardArgument card1= (CardArgument) cardlist.get(AbstractDungeon.cardRandomRng.random(cardlist.size()-1));
                              if(card1.cardModifier!=null) {
                                  CardModifierManager.addModifier(c1, card1.cardModifier);
                                  resultb.add(c1);
                              }
                            }
                            resulta=resultb;
                        }
                    }
            return resulta;
        }

        else{
        return CardLibrary.getAllCards().stream()
                .filter(obj -> {
                    String name = obj.name;

                    // 将 name 转换为字符集
                    List<Character> nameCharList = name.chars()
                            .mapToObj(c -> (char) c)
                            .collect(Collectors.toList());

                    // 复制一份字符集以供匹配
                    List<Character> charListCopy = new ArrayList<>(charList);

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
                .collect(Collectors.toList());}
}

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}

