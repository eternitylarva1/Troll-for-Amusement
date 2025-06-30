package cn.candy.relic;

import basemod.abstracts.CustomRelic;
import cn.candy.card.ten;
import cn.candy.config.BetterClickableRelic;
import cn.candy.config.RelicConfig;
import cn.candy.powers.new_split_power;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AttackPotion;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.potions.SmokeBomb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.PotionBelt;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import com.megacrit.cardcrawl.rewards.RewardItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Random;

/**
 * 这个遗物会在开始的时候赋予房间所有怪物3点血量
 *
 * @author : Administrator
 * @date : 2020-08-06 16:27
 **/
@SuppressWarnings("unused")
public class Money extends CustomRelic implements BetterClickableRelic<Money> {
    private int count=1;
    public static int count2=0;
    public static final String ID = RelicConfig.RELIC_PRE_NAME + "Money";
    /**
     * 日志对象
     */
    private static final Logger log = LogManager.getLogger(Money.class);
    private boolean cardsReceived;

    /**
     * 构造函数
     */
    public Money() {
        //图片使用内置的 使用破碎王冠 的图标
        //使用内置图标就不需要导入了 想自定义可以抄其他的mod或者看教程
        super(ID, "crown.png", RelicTier.SPECIAL, LandingSound.CLINK);
        this.setDuration(800).addRightClickActions( () -> {
            if(this.counter>=3) {
                this.counter-=3;
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new ten(), 1, true));
            }


        });
    }
    
    /**
     * 在战斗开始时触发
     */
    @Override

    public void atBattleStart() {
        super.atBattleStart();
            roll_monster();
            this.counter+=1;

            }




    public void roll_monster() {
        AbstractMonster m;
        Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();//获取所有怪物

        while (var2.hasNext()) {
            m = (AbstractMonster) var2.next();
            Random random = new Random();
            int randomNumber;

            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));

            int s = AbstractDungeon.floorNum;
            AbstractMonster SP = null;

            if(s<=17) {
                randomNumber = AbstractDungeon.monsterRng.random(25)+1;

                System.out.println(randomNumber);
                SP = randommonster.randommonster(randomNumber);
            } else if (s<=34) {
                randomNumber =AbstractDungeon.monsterRng.random(22)+26;
                System.out.println(randomNumber);
                SP = randommonster.randommonster(randomNumber);
            }else if (s<=52) {
                randomNumber = AbstractDungeon.monsterRng.random(17)+49;
                System.out.println(randomNumber);
                SP = randommonster.randommonster(randomNumber);
            }else{

                randomNumber = AbstractDungeon.monsterRng.random(5)+63;
                System.out.println(randomNumber);
                SP = randommonster.randommonster(randomNumber);
                if(m.id== "CorruptHeart")
                {
                    randomNumber = AbstractDungeon.monsterRng.random(62)+3;
                    SP = new CorruptHeart1();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(SP, SP, new ReactivePower1(SP)));

                }
            }

            SP.drawX = m.drawX;
            SP.drawY = m.drawY;//蛇花的位置
            SP.maxHealth = m.maxHealth;
            SP.addBlock(m.currentBlock);


            SP.currentHealth = m.currentHealth;


            AbstractPower po;
            for (AbstractPower power : m.powers) {
                // 对每个元素进行处理
                power.owner=SP;
                if (power.ID!="Split") {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(SP, SP, power));

                }
                else{
                    SP.addPower(new new_split_power(SP));

                }

            }
            if (m.maxHealth!=400) {
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SP, false));
            }

            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(SP));
            SP.rollMove();

            SP.nextMove=1;
            SP.createIntent();
            if (SP.id!="CorruptHeart") {
                SP.usePreBattleAction();
            }

            //AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SP, false));
            if(AbstractDungeon.player.hasRelic("NeowsBlessing"))
            {
                if(AbstractDungeon.player.getRelic("NeowsBlessing").counter>0)
                {
                    SP.currentHealth=1;
                }

            }
            if(AbstractDungeon.player.hasRelic("PreservedInsect"))
            {
                if (AbstractDungeon.getCurrRoom().eliteTrigger) {
                    this.flash();


                    if (SP.currentHealth > (int)((float)SP.maxHealth * (1.0F - 0.25F))) {
                        SP.currentHealth = (int)((float)SP.maxHealth * (1.0F - 0.25F));
                        SP.healthBarUpdatedEvent();

                    }


                }

            } if(AbstractDungeon.player.hasRelic("Red Mask"))
            {
                this.addToBot(new ApplyPowerAction(SP, AbstractDungeon.player, new WeakPower(SP, 1, false), 1, true));

            }
            if(AbstractDungeon.player.hasRelic("Bag of Marbles"))
            {
                this.addToBot(new ApplyPowerAction(SP, AbstractDungeon.player, new VulnerablePower(SP, 1, false), 1, true));

            }
            if (m.maxHealth!=400) { AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());

            AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(m));
           AbstractDungeon.actionManager.addToBottom(new SuicideAction(m, false));


            AbstractDungeon.actionManager.addToBottom(new CanLoseAction());}

        }}
        //定义一个金钱变量为100
        @Override
    public void onVictory() {
            if (this.count2 > 0) {
                RewardItem rewardItem1 = new RewardItem(new PrayerWheel());
                RewardItem rewardItem2 = new RewardItem(new PotionBelt());
                    RewardItem rewardItem3 = new RewardItem(new MoltenEgg2());

                AbstractDungeon.getCurrRoom().rewards.add(rewardItem1);
                AbstractDungeon.getCurrRoom().rewards.add(rewardItem2);
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new FairyPotion()));
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new SmokeBomb()));
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new AttackPotion()));
                AbstractDungeon.getCurrRoom().rewards.add(rewardItem3);
                this.count2 = 0;
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
            }
            if (this.counter <= 4) {
                this.counter++;


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
