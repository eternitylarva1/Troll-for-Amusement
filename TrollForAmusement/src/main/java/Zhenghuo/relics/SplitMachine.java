package Zhenghuo.relics;

import Zhenghuo.actions.GatherCharacterAction;
import Zhenghuo.actions.SplitCharacterAction;
import Zhenghuo.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

// 继承CustomRelic
public class SplitMachine extends CustomRelic implements ClickableRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("SplitMachine");
    // 图片路径
    private static final String IMG_PATH = "ZhenghuoResources/images/relics/SplitMachine.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public SplitMachine() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new SplitMachine();
    }

    @Override
    public void onRightClick() {
        addToBot(new SplitCharacterAction(AbstractDungeon.player, AbstractDungeon.player, -1, false,false));
    }
}