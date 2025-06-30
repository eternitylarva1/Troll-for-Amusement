package ChouXiangRelic.relic;

import ChouXiangRelic.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 这个遗物会在开始的时候赋予房间所有怪物3点血量
 *
 * @author : Administrator
 * @date : 2020-08-06 16:27
 **/
@SuppressWarnings("unused")
public class dangao extends CustomRelic {
    /**
     * 遗物ID 随便写 但是需要和json文件名称一致
     * 比如我这里最终是 CANDY_MOD_Money 就需要最后json文件内有 CANDY_MOD_Money 的遗物信息
     */
    public static final String ID = ModHelper.makePath(dangao.class.getSimpleName());
    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(dangao.class);

    /**
     * 构造函数
     */
    public dangao() {
        //图片使用内置的 使用破碎王冠 的图标
        //使用内置图标就不需要导入了 想自定义可以抄其他的mod或者看教程

        super(ID, new Texture(Gdx.files.internal("images/relics/dangao.png")), RelicTier.BOSS, LandingSound.CLINK);
    }
    int floor;
    @Override
    public void obtain()
    {
        super.obtain();
        //拾起时，获得所有除了自己以外的boss遗物
        AbstractDungeon.player.increaseMaxHp(150, true);
        AbstractDungeon.player.currentHealth+=150;
        AbstractDungeon.player.healthBarUpdatedEvent();
        AbstractDungeon.effectsQueue.add(new HealEffect(AbstractDungeon.player.hb.cX - AbstractDungeon.player.animX, AbstractDungeon.player.hb.cY, 150));

    }
    public int onPlayerHeal(int healAmount) {
        this.flash();
      return 0;
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
