package ChouXiangRelic.relic;

import ChouXiangRelic.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

/**
 * 这个遗物会在开始的时候赋予房间所有怪物3点血量
 *
 * @author : Administrator
 * @date : 2020-08-06 16:27
 **/
@SuppressWarnings("unused")
public class weixianbaoxiang extends CustomRelic  {
    /**
     * 遗物ID 随便写 但是需要和json文件名称一致
     * 比如我这里最终是 CANDY_MOD_Money 就需要最后json文件内有 CANDY_MOD_Money 的遗物信息
     */
    public static final String ID = ModHelper.makePath(weixianbaoxiang.class.getSimpleName());
    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(weixianbaoxiang.class);

    /**
     * 构造函数
     */
    public weixianbaoxiang() {
        //图片使用内置的 使用破碎王冠 的图标
        //使用内置图标就不需要导入了 想自定义可以抄其他的mod或者看教程

        super(ID, new Texture(Gdx.files.internal("images/relics/weixianbaoxiang.png")), RelicTier.BOSS, LandingSound.CLINK);
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
if(player!=null&& player.hasRelic(this.relicId)) {
    for (ArrayList<MapRoomNode> mapRoomNodes : AbstractDungeon.map) {
        for (MapRoomNode mapRoomNode : mapRoomNodes) {
            if (mapRoomNode.getRoom() instanceof MonsterRoom &&!(mapRoomNode.getRoom() instanceof MonsterRoomElite)&&!(mapRoomNode.getRoom() instanceof MonsterRoomBoss) ) {
                TreasureRoom treasureRoom = new TreasureRoom();
                if (AbstractDungeon.currMapNode.room == mapRoomNode.room) {
                    System.out.println("当前房间");
                    AbstractDungeon.currMapNode.room = treasureRoom;
                    treasureRoom.onPlayerEntry();

                }
                mapRoomNode.setRoom(treasureRoom);

            }
            if (mapRoomNode.getRoom() instanceof EventRoom ) {
                MonsterRoomElite eliteRoom = new MonsterRoomElite();
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

    }


}
