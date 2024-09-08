package Zhenghuo.relics;

import Zhenghuo.actions.BetteGatherCharacterAction;
import Zhenghuo.helpers.ModHelper;
import Zhenghuo.screens.CharacterScreen;
import Zhenghuo.screens.MyScreen;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static Zhenghuo.modcore.ExampleMod.dictionary;


// 继承CustomRelic
public class Dictionary extends CustomRelic implements ClickableRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("Dictionary");
    // 图片路径
    private static final String IMG_PATH = "ZhenghuoResources/images/relics/GatherMachine.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public Dictionary() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Dictionary();
    }

    @Override
    public void onRightClick() {
       /// addToBot(new GatherCharacterAction(AbstractDungeon.player, AbstractDungeon.player, -1, false,false));
        ///BaseMod.openCustomScreen(MyScreen.Enum.MY_SCREEN, "foobar", new Shiv());
        //BaseMod.openCustomScreen(CharacterScreen.Enum.MY_SCREEN);
        dictionary.open();
    }
}