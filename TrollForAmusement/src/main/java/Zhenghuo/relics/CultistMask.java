package Zhenghuo.relics;

import ChatterMod.MainModfile;
import ChatterMod.actions.RecordAndPlaybackAction;
import Zhenghuo.effects.LoseReliceffect;
import Zhenghuo.helpers.ModHelper;
import Zhenghuo.modcore.ExampleMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

// 继承CustomRelic
public class CultistMask extends CustomRelic{
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("CultistMask");
    // 图片路径
    private static final String IMG_PATH = "ZhenghuoResources/images/relics/cultistMask.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;


    public CultistMask() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {

        return new CultistMask();

    }



    public void atBattleStart() {
        this.flash();
        addToBot(new RecordAndPlaybackAction(f -> {
            MainModfile.logger.info("Chatter Volume: " + f);
            ExampleMod.Tips="目前音量为"+f;
            int i=   (int) Math.max(0.0F, Math.max(0.0F, f.floatValue() - 20.0F) / 20.0F)  ;
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new RitualPower(AbstractDungeon.player,i,true),i));
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    ExampleMod.StartRecord=false;
                    isDone=true;
                }
            });
        }));
        this.addToBot(new TalkAction(true, this.DESCRIPTIONS[1], 1.0F, 2.0F));

    }

}