package ChouXiangRelic.relic;

import ChouXiangRelic.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * 这个遗物会在开始的时候赋予房间所有怪物3点血量
 *
 * @author : Administrator
 * @date : 2020-08-06 16:27
 **/
@SuppressWarnings("unused")
public class kuangwang extends CustomRelic  {
    /**
     * 遗物ID 随便写 但是需要和json文件名称一致
     * 比如我这里最终是 CANDY_MOD_Money 就需要最后json文件内有 CANDY_MOD_Money 的遗物信息
     */
    public static final String ID = ModHelper.makePath(kuangwang.class.getSimpleName());
    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(kuangwang.class);

    /**
     * 构造函数
     */
    public kuangwang() {
        //图片使用内置的 使用破碎王冠 的图标
        //使用内置图标就不需要导入了 想自定义可以抄其他的mod或者看教程

        super(ID, new Texture(Gdx.files.internal("images/relics/kuangwang.png")), RelicTier.BOSS, LandingSound.CLINK);
    }
    int floor;


    
    /**
     * 重写遗物的描述内容 可以不用管
     *
     * @return 字符串内容
     */
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void update() {
        super.update();
if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasRelic(this.relicId)) {
    for (ArrayList<MapRoomNode> mapRoomNodes : AbstractDungeon.map) {
        for (MapRoomNode mapRoomNode : mapRoomNodes) {
            if (mapRoomNode.getRoom() instanceof RestRoom) {
                MonsterRoom eliteRoom = new MonsterRoomElite();
                if (AbstractDungeon.currMapNode.room == mapRoomNode.room) {
                    System.out.println("当前房间");
                    AbstractDungeon.currMapNode.room = eliteRoom;
                    eliteRoom.phase = MonsterRoomElite.RoomPhase.COMBAT;
                    eliteRoom.onPlayerEntry();
                }
                mapRoomNode.setRoom(eliteRoom);
                mapRoomNode.hasEmeraldKey = true;
            }
        }
    }
}
    }
    public void onEquip() {
        if(!  Settings.hasRubyKey) {
            AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.RED));
        }
    }

    @Override
    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {

            ArrayList<AbstractCard> upgradableCards = new ArrayList();
            Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                if (c.canUpgrade() ) {
                    upgradableCards.add(c);
                }
            }
            Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
            if (!upgradableCards.isEmpty()) {

                    ((AbstractCard)upgradableCards.get(0)).upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                    AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

            }
            AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth*0.2f));

        }
    }
}
