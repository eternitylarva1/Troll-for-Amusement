package Zhenghuo.relics;

import Zhenghuo.effects.LoseReliceffect;
import Zhenghuo.helpers.ModHelper;
import Zhenghuo.modcore.ExampleMod;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    public static boolean isUPLive(String uid) {
        String apiUrl = "https://api.bilibili.com/x/space/acc/info?mid=" + uid;
        Map<String, String> headers = new HashMap<>();
        headers.put("origin", "https://space.bilibili.com");
        headers.put("referer", "https://space.bilibili.com/");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36 Edg/88.0.705.63");
        headers.put("Host", "api.bilibili.com");

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            reader.close();

            int liveStatusIndex = response.indexOf("\"liveStatus\":");
            if (liveStatusIndex!= -1) {
                int valueStartIndex = liveStatusIndex + "\"liveStatus\":".length();
                int valueEndIndex = response.indexOf(",", valueStartIndex);
                if (valueEndIndex == -1) {
                    valueEndIndex = response.length() - 1;
                }
                String liveStatusValue = response.substring(valueStartIndex, valueEndIndex).trim();
                return "1".equals(liveStatusValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void onRightClick() {
        String uid = "1909499809"; // 替换为实际的UID
        StringBuilder nameBuilder = new StringBuilder();
        boolean isLive = isUPLive(uid);
        tips.clear();
        if(isLive){
            tips.add(new PowerTip("使用方法", "右键本遗物刷新状态"));
            tips.add(new PowerTip("开播状态", "白夕seal" + " 正在直播"));

        }else {
            tips.add(new PowerTip("使用方法", "右键本遗物刷新状态"));
            tips.add(new PowerTip("开播状态", "白夕seal"+ " 未开播或请求过于频繁"));
        }
        initializeTips();
        /*
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
*/
    }
    public void onEnterRoom(AbstractRoom room)
    {
    }
}