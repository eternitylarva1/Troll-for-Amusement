package Zhenghuo.relics;

import Zhenghuo.actions.ChangePlayerAction;
import Zhenghuo.effects.LoseReliceffect;
import Zhenghuo.helpers.ModHelper;
import Zhenghuo.modcore.ExampleMod;
import Zhenghuo.utils.Invoker;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.events.shrines.Nloth;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EmptyRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

// 继承CustomRelic
public class StrongCharacter extends CustomRelic implements ClickableRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("StrongCharacter");
    // 图片路径
    private static final String IMG_PATH = "ZhenghuoResources/images/relics/bell.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public StrongCharacter() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {

        return new StrongCharacter();

    }

    @Override
    public void onRightClick() {
        if(!Settings.isEndless){
            ExampleMod.NowPlayer = null;
        }
        AbstractDungeon.floorNum = 1;
        AbstractDungeon.actNum=0;
        int i=ascensionLevel;
        ascensionLevel=1;
        AbstractDungeon.generateSeeds();
        CardCrawlGame.dungeon=new Exordium(AbstractDungeon.player,new ArrayList<String>());
        ascensionLevel=i;
        CardCrawlGame.music.fadeOutBGM();
        CardCrawlGame.music.fadeOutTempBGM();
        AbstractDungeon.fadeOut();
        AbstractDungeon.topLevelEffects.clear();
        AbstractDungeon.actionManager.actions.clear();
        AbstractDungeon.effectList.clear();
        AbstractDungeon.effectsQueue.clear();
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.dungeonMapScreen.open(true);
       AbstractDungeon.player.masterDeck.group.clear();
        for (String s : AbstractDungeon.player.getStartingDeck()) {
            AbstractDungeon.player.masterDeck.addToTop(CardLibrary.getCard(AbstractDungeon.player.chosenClass, s).makeCopy());
        }
        if (ascensionLevel >= 10) {
            player.masterDeck.addToTop(new AscendersBane());
        }

        Iterator<AbstractRelic> iterator = player.relics.iterator();
        while (iterator.hasNext()) {
            AbstractRelic relic = iterator.next();
            {

                effectList.add(new LoseReliceffect(relic));

            }
        }
        AbstractDungeon.floorNum = 0;
      for(AbstractPotion p: player.potions)
      {
          p=new PotionSlot(1);
      }
        Settings.hasRubyKey=false;
        Settings.hasSapphireKey=false;
        Settings.hasEmeraldKey=false;

    }
    public void onEnterRoom(AbstractRoom room)
    {
    }
}