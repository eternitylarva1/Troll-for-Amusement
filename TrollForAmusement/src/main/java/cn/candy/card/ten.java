package cn.candy.card;


import cn.candy.powers.new_split_power;
import cn.candy.relic.CorruptHeart1;
import cn.candy.relic.randommonster;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;
import java.util.Random;

public class ten extends AbstractCard {
    public static final String ID = "Shockwave";
    private static final CardStrings cardStrings;

    public ten() {
        super("Shockwave", "十面骰", "green/skill/calculated_gamble", 0, "随机替换你的所有怪物", CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.exhaust = true;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var3.hasNext()) {
            m = (AbstractMonster) var3.next();
            if (m.isDying == false) {
                Random random = new Random();
                int randomNumber;



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
                } else {

                    randomNumber = AbstractDungeon.monsterRng.random(5) +63;
                    SP = randommonster.randommonster(randomNumber);
                    if(m.id== "CorruptHeart")
                    {

                        SP=new CorruptHeart1();
                        SP.addPower(new new_split_power(SP));

                    }
                }
                SP.drawX = m.drawX;
                SP.drawY = m.drawY;//蛇花的位置
                SP.maxHealth = m.maxHealth;
                SP.currentHealth = m.currentHealth;
                AbstractPower po;
                for (AbstractPower power : m.powers) {
                    // 对每个元素进行处理
                    power.owner = SP;
                    if (power.ID!="Split") {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(SP, SP, power));

                    }
                    else{
                        SP.addPower(new new_split_power(SP));

                    }


                }
                //AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SP,false));
                if(SP.id!="GremlinLeader") {
                    SP.usePreBattleAction();

                }
                else{

                }
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(SP));
                SP.rollMove();

                SP.nextMove = 1;
                SP.createIntent();

                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SP, false));

                AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
                AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(m, 1.0F, 0.1F));
                AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(m, false));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());

            }

    }}

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

        }

    }

    public AbstractCard makeCopy() {
        return new ten();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Shockwave");
    }
}
