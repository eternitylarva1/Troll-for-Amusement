package Zhenghuo.modcore;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import Zhenghuo.card.*;

import Zhenghuo.events.MyFirstEvent;
import Zhenghuo.otherplayer.OtherPlayerHelper;
import Zhenghuo.player.Mycharacter;
import Zhenghuo.relics.Dictionary;
import Zhenghuo.relics.GatherMachine;
import Zhenghuo.relics.SplitMachine;
import Zhenghuo.relics.StrongCharacter;
import Zhenghuo.screens.CharacterScreen;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

import static Zhenghuo.actions.ChangePlayerAction.ChangePlayer;
import static Zhenghuo.player.Mycharacter.PlayerColorEnum.CharacterBlack;
import static Zhenghuo.player.Mycharacter.PlayerColorEnum.Cangjie;
import static Zhenghuo.utils.CardArguments.Chimeraopened;
import static Zhenghuo.utils.CardArguments.RewardPatch.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

@SpireInitializer
public class ExampleMod implements PostCampfireSubscriber,PostCreateStartingDeckSubscriber,EditCharactersSubscriber,PostInitializeSubscriber,PostDungeonInitializeSubscriber,OnStartBattleSubscriber, PostBattleSubscriber,CustomSavable<String>,EditCardsSubscriber, EditStringsSubscriber , EditRelicsSubscriber { // 实现接口
public static String NowPlayer=null;
    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "ZhenghuoModResources/img/char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "ZhenghuoModResources/img/char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "ZhenghuoModResources/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "ZhenghuoModResources/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "ZhenghuoModResources/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "ZhenghuoModResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "ZhenghuoModResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "ZhenghuoModResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "ZhenghuoModResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "ZhenghuoModResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "ZhenghuoModResources/img/char/cost_orb.png";
    public static final Color MY_COLOR = new Color(1.0F / 255.0F, 1.0F / 255.0F, 3.0F / 255.0F, 0.85F);
    public ExampleMod() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件
        BaseMod.addSaveField("Zhenghuo", this);

    }
    public  static CharacterScreen dictionary;
    public static void initialize() {

        new ExampleMod();
        BaseMod.addColor(CharacterBlack, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB);

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
        BaseMod.addCard(new GatherRelic());
        BaseMod.addCard(new RandomCardWithWord());
        BaseMod.addCard(new DescriptionStrike());
/*
      String[]WordPool = new String[]{"打", "击", "神","+","之","重","暴"};
       for(String word :WordPool){
           BaseMod.addCard(new CharacterCard(word));
       }*/
int i=0;/*
        for (AbstractCard.CardType ct : AbstractCard.CardType.values()) {
          for(AbstractCard.CardRarity cr:AbstractCard.CardRarity.values())
          {
i++;
              CharacterCard c= new CharacterCard(Integer.toString(i));
              c.type=ct;
              c.rarity=cr;
              BaseMod.addCard(c);
          }

        }
*/
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
        BaseMod.loadCustomStringsFile(EventStrings.class, "ZhenghuoResources/localization/" + lang + "/events.json");// 如果是中文，加载的就是"ExampleResources/localization/ZHS/cards.json"
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "ZhenghuoResources/localization/" + lang + "/characters.json");
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
    public void receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(new Mycharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, Cangjie);
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
        Map<String, Integer> wordCountMap = new HashMap<>();
        for (AbstractCard nameObject : ModifiedCards) {
            String[] words = nameObject.name.split("");
            for (String word : words) {
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = wordCountMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());

        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            if (count == 15) {
                break;
            }
            Words.add(entry.getKey());
            System.out.println( entry.getKey()+ " 出现次数：" + entry.getValue());
            count++;
        }

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
       System.out.println(Relics);

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
            Collections.sort(CardAugrments.group, new Comparator<AbstractCard>() {
                @Override
                public int compare(AbstractCard o1, AbstractCard o2) {
                    return Collator.getInstance().compare(o1.name, o2.name);
                }
            });
        }

    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addEvent(MyFirstEvent.ID, MyFirstEvent.class);
        BaseMod.addCustomScreen(new CharacterScreen() {
        });/*
        BaseMod.addCustomScreen(new MyScreen() {
        });*/
        dictionary=new CharacterScreen();
        InitizeModifiedCards();

    }

    @Override
    public void receivePostCreateStartingDeck(AbstractPlayer.PlayerClass playerClass, CardGroup cardGroup) {
        if(playerClass== Cangjie){
            cardGroup.clear();
            for (int x = 0; x < 3; x++) {
                cardGroup.group.add(new CharacterCard("打"));
                cardGroup.group.add(new CharacterCard("击"));
            }
            for (int x = 0; x < 3; x++) {
                cardGroup.group.add(new CharacterCard("防"));
                cardGroup.group.add(new CharacterCard("御"));
            }

        }
    }

    @Override
    public boolean receivePostCampfire() {
        //代码加在这里
        return false;
    }
}
