package Zhenghuo.modcore;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import CardAugments.cards.DummyCard;
import CardAugments.util.FormatHelper;
import Zhenghuo.card.*;
import Zhenghuo.events.JoinCharacterEvent;
import Zhenghuo.otherplayer.OtherPlayerHelper;
import Zhenghuo.relics.Dictionary;
import Zhenghuo.relics.GatherMachine;
import Zhenghuo.relics.SplitMachine;
import Zhenghuo.relics.StrongCharacter;
import Zhenghuo.screens.CharacterScreen;
import Zhenghuo.screens.MyScreen;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.abstracts.CustomScreen;
import basemod.devcommands.relic.RelicPool;
import basemod.helpers.CardModifierManager;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import basemod.patches.whatmod.WhatMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.compendium.RelicViewScreen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import static Zhenghuo.actions.ChangePlayerAction.ChangePlayer;
import static Zhenghuo.utils.CardArguments.Chimeraopened;
import static Zhenghuo.utils.CardArguments.RewardPatch.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;
import java.util.ArrayList;

@SpireInitializer
public class ExampleMod implements PostInitializeSubscriber,PostDungeonInitializeSubscriber,OnStartBattleSubscriber, PostBattleSubscriber,CustomSavable<String>,EditCardsSubscriber, EditStringsSubscriber , EditRelicsSubscriber { // 实现接口
public static String NowPlayer=null;

    public ExampleMod() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件
        BaseMod.addSaveField("Zhenghuo", this);

    }
    public  static CharacterScreen dictionary;
    public static void initialize() {

        new ExampleMod();
    }

    // 当basemod开始注册mod卡牌时，便会调用这个函数
public static CharacterScreen getdictionary()
{
    return dictionary;
}
    @Override
    public void receiveEditCards() {
        // TODO 这里写添加你卡牌的代码
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Soul_P());
        BaseMod.addCard(new Soul_G());
        BaseMod.addCard(new CharacterCard("文字牌"));
        BaseMod.addCard(new Characterdesign());
        BaseMod.addCard(new Charactergather());
        BaseMod.addCard(new TongpeiCard());
        BaseMod.addCard(new Golden_Form());
        BaseMod.addCard(new BlackMyth());
        BaseMod.addCard(new Suijiyingbian());
        BaseMod.addCard(new RelicName());
        BaseMod.addCard(new MonsterName());
        BaseMod.addCard(new SuiXin());
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "ENG"; // 如果没有相应语言的版本，默认加载英语
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "ZhenghuoResources/localization/" + lang + "/cards.json"); // 加载相应语言的卡牌本地化内容。

        BaseMod.loadCustomStringsFile(RelicStrings.class, "ZhenghuoResources/localization/" + lang + "/relics.json");// 如果是中文，加载的就是"ExampleResources/localization/ZHS/cards.json"
            }



    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new StrongCharacter(), RelicType.SHARED); // RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物

        BaseMod.addRelic(new GatherMachine(), RelicType.SHARED);
        BaseMod.addRelic(new SplitMachine(), RelicType.SHARED);
        BaseMod.addRelic(new Dictionary(),RelicType.SHARED);
    }

    @Override
    public String onSave() {
        System.out.println("正在保存");/*
        ChangePlayer(NowPlayer);
        for (RewardItem reward : getCurrRoom().rewards) {
            System.out.println("替换中");
            if (reward.type == RewardItem.RewardType.CARD) {
                System.out.println("已执行替换");
                reward.cards = AbstractDungeon.getRewardCards();


            }
        }*/
            return NowPlayer;

    }
    @Override
    public void onLoad(String s) {
        if(hasLoaded){
            InitizeModifiedCards();
        }
        NowPlayer=s;
        System.out.println("成功加载");
        ChangePlayer(NowPlayer);


    }

public static boolean hasLoaded=false;
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if(NowPlayer==null)
        {
            NowPlayer=player.getClass().getSimpleName();
        }
        ChangePlayer(NowPlayer);

        /*
    String[] a= {"Defect","Ironclad","Watcher","TheSilent"};
        ArrayList<String> list = new ArrayList<>(Arrays.asList(a));
    if(list.contains(NowPlayer)){
       list.remove(NowPlayer);
    }
    int b=0;
        for (String s : list) {
            b++;
            AbstractOtherPlayer am=new AbstractOtherPlayer(s);
            am.drawX=player.drawX-100*b;
            OtherPlayerHelper.addMinion(player, am);
        }
*/

    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        OtherPlayerHelper.clearMinions(player);
    }
    public static HashMap<String, AbstractAugment> modMap=new HashMap<>();
    @Override
    public void receivePostDungeonInitialize() {
        if(!Settings.isEndless){
            NowPlayer = null;

        }
    }

    public static void InitizeModifiedCards()
    {
System.out.println("正在预加载");
        ModifiedCards.clear();
        ModifiedCards.addAll(CardLibrary.getAllCards());
        CardAugrments.clear();
        Relics.clear();
        RelicLibrary.starterList = RelicLibrary.sortByStatus(RelicLibrary.starterList, false);
        RelicLibrary.commonList = RelicLibrary.sortByStatus(RelicLibrary.commonList, false);
        RelicLibrary.uncommonList = RelicLibrary.sortByStatus(RelicLibrary.uncommonList, false);
        RelicLibrary.rareList = RelicLibrary.sortByStatus(RelicLibrary.rareList, false);
        RelicLibrary.bossList = RelicLibrary.sortByStatus(RelicLibrary.bossList, false);
        RelicLibrary.specialList = RelicLibrary.sortByStatus(RelicLibrary.specialList, false);
        RelicLibrary.shopList = RelicLibrary.sortByStatus(RelicLibrary.shopList, false);
       Relics= Arrays.asList(RelicLibrary.starterList, RelicLibrary.commonList,RelicLibrary.uncommonList,RelicLibrary.rareList,RelicLibrary.bossList,RelicLibrary.specialList,RelicLibrary.shopList)
               .stream()
               .flatMap(ArrayList::stream)
               .collect(Collectors.toCollection(ArrayList::new));
       System.out.println(Relics.toString());

        if(Chimeraopened())
        {
            for(AbstractCard c:CardLibrary.getAllCards()) {

                for (String id : CardAugmentsMod.modMap.keySet()) {
                    ///遍历所有词条
                    AbstractAugment a = (AbstractAugment) CardAugmentsMod.modMap.get(id);

                    if (
                            a.canApplyTo(c)) {
                        //给卡牌加入词条

                        AbstractCard copy = c.makeCopy();
                        CardModifierManager.addModifier(copy, a.makeCopy());
                        copy.targetDrawScale = 0.75F;
                        copy.name=a.modifyName(copy.name,copy);
                        ///加入卡池
                        ModifiedCards.add(copy);
                    }
                /*

                if (!c.getClass().isAnnotationPresent(NoCompendium.class) &&
                        this.selectedAugment.canApplyTo(c)) {
                    AbstractCard copy = c.makeCopy();
                    CardModifierManager.addModifier(copy, this.selectedAugment.makeCopy());
                    if (this.upgradePreview && copy.canUpgrade()) {
                        copy.upgrade();
                        copy.displayUpgrades();
                    }
                    copy.targetDrawScale = 0.75F;
                    this.validCards.addToBottom(copy);
                }
*/
                }
            }

            for (String id : CardAugmentsMod.modMap.keySet()) {
                ///遍历所有词条
                AbstractAugment a = (AbstractAugment) CardAugmentsMod.modMap.get(id);
                AbstractCard c=new CharacterCard("",CharacterCard.DESCRIPTION);
                //给卡牌加入词条
                AbstractCard copy = new CharacterCard(a.modifyName("",c),a.getAugmentDescription().replaceAll("#[yb]", ""));

                copy.targetDrawScale = 0.75F;
                ///加入卡池
                ModifiedCards.add(copy);
                CardAugrments.group.add(copy);
            }

        }

    }
    @Override
    public void receivePostInitialize() {
        BaseMod.addCustomScreen(new CharacterScreen() {
        });/*
        BaseMod.addCustomScreen(new MyScreen() {
        });*/
        dictionary=new CharacterScreen();
        InitizeModifiedCards();

    }
}
