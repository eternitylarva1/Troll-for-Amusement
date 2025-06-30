package Zhenghuo.card.purple;

import Zhenghuo.utils.Invoker;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.Slot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.WheelKick;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BustedCrown;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class huihuanti extends AbstractCard {
    public static final String ID = "WheelKick";
    private static final CardStrings cardStrings;

    public huihuanti() {
        super("WheelKick", cardStrings.NAME, "purple/attack/wheel_kick", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust = false;
        this.baseMagicNumber=this.magicNumber=1;
        this.baseDamage = 15;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                Skeleton skeleton = Invoker.getField(m,"skeleton"); // 这里需要你根据游戏实际情况实现获取方法
// 获取指定插槽
                Slot slot = skeleton.findSlot("crown");
                if (slot != null) {
                    // 获取插槽当前的颜色对象
                    Color currentColor = slot.getColor();
                    // 增加透明度（范围从 0 到 1）

                    // 使用 set 方法设置新的颜色和透明度
                    currentColor.set(currentColor.r, currentColor.g, currentColor.b,0f);
                    AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new BustedCrown()));
                }

                isDone=true;
            }
        });

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }

    }

    public AbstractCard makeCopy() {
        return new huihuanti();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WheelKick");
    }
}
