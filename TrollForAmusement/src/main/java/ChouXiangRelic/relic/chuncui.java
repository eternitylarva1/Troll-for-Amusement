package ChouXiangRelic.relic;

import ChouXiangRelic.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.blights.TimeMaze.DESC;

/**
 * 这个遗物会在开始的时候赋予房间所有怪物3点血量
 *
 * @author : Administrator
 * @date : 2020-08-06 16:27
 **/
@SuppressWarnings("unused")
public class chuncui extends CustomRelic {
    /**
     * 遗物ID 随便写 但是需要和json文件名称一致
     * 比如我这里最终是 CANDY_MOD_Money 就需要最后json文件内有 CANDY_MOD_Money 的遗物信息
     */
    public static final String ID = ModHelper.makePath(chuncui.class.getSimpleName());
    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(chuncui.class);

    /**
     * 构造函数
     */
    public chuncui() {
        //图片使用内置的 使用破碎王冠 的图标
        //使用内置图标就不需要导入了 想自定义可以抄其他的mod或者看教程

        super(ID, new Texture(Gdx.files.internal("images/relics/chuncui.png")), RelicTier.BOSS, LandingSound.CLINK);
        this.counter=0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 0;
        //战斗开始时获得99力量，99敏捷，44集中。每回合最多打出1张牌。
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,50)));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,50)));
    }
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (this.counter < 1 && card.type != AbstractCard.CardType.CURSE) {
            ++this.counter;
            if (this.counter >= 15) {
                this.flash();
            }
        }

    }
    @Override
    public boolean canPlay(AbstractCard card) {
        if (this.counter >= 1 && card.type != AbstractCard.CardType.CURSE) {
            card.cantUseMessage = DESC[2] + 1 + DESC[1];
            return false;
        } else {
            return true;
        }

    }

    public void atTurnStart() {
        this.counter = 0;
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
