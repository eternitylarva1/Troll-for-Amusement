package ChouXiangRelic.relic;

import ChouXiangRelic.helpers.ModHelper;
import ChouXiangRelic.utils.Invoker;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * 这个遗物会在开始的时候赋予房间所有怪物3点血量
 *
 * @author : Administrator
 * @date : 2020-08-06 16:27
 **/
@SuppressWarnings("unused")
public class tanlan extends CustomRelic  {
    /**
     * 遗物ID 随便写 但是需要和json文件名称一致
     * 比如我这里最终是 CANDY_MOD_Money 就需要最后json文件内有 CANDY_MOD_Money 的遗物信息
     */
    public static final String ID = ModHelper.makePath(tanlan.class.getSimpleName());
    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(tanlan.class);

    /**
     * 构造函数
     */
    public tanlan() {
        //图片使用内置的 使用破碎王冠 的图标
        //使用内置图标就不需要导入了 想自定义可以抄其他的mod或者看教程

        super(ID, new Texture(Gdx.files.internal("images/relics/tanlan.png")), RelicTier.BOSS, LandingSound.CLINK);
    }

    public void obtain()
    {
        super.obtain();
        //拾起时，获得所有除了自己以外的boss遗物
        AbstractDungeon.player.gainGold(9999);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        this.grayscale = false;
        this.usedUp = false;
        this.description = this.DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    public void onSpendGold() {
        if (!this.usedUp) {
            flash();
           this.usedUp();
           AbstractDungeon.shopScreen.coloredCards.clear();
           AbstractDungeon.shopScreen.colorlessCards.clear();
            ArrayList<StoreRelic> relics = Invoker.getField(AbstractDungeon.shopScreen, "images/relics");
// 调用clear方法清空集合
            for(  StoreRelic relic : relics){
             relic.isPurchased=true;
            }

            ArrayList<StorePotion> potions = Invoker.getField(AbstractDungeon.shopScreen, "potions");
            for(  StorePotion potion : potions){
                potion.isPurchased=true;
            }

            AbstractDungeon.shopScreen.purgeAvailable = false;
// 调用clear方法清空集合

        }
    }
    /**
     * 重写遗物的描述内容 可以不用管
     *
     * @return 字符串内容
     */
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


}
