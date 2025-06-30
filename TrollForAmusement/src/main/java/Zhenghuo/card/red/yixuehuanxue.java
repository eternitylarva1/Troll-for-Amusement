package Zhenghuo.card.red;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.monsters.city.Chosen;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import java.util.Objects;

public class yixuehuanxue extends AbstractCard {
    public static final String ID = "Blood for Blood";
    private static final CardStrings cardStrings;

    public yixuehuanxue() {
        super("Blood for Blood", cardStrings.NAME, "red/attack/blood_for_blood", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ALL);
        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
      addToBot(new AbstractGameAction() {
          @Override
          public void update() {
              for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                  if(monster.id!=null){
                      if (Objects.equals(monster.id, Cultist.ID) || Objects.equals(monster.id, Chosen.ID)|| Objects.equals(monster.id, Byrd.ID)) {
                          if (!monster.hasPower(SurroundedPower.POWER_ID)) {
                              addToTop(new EscapeAction(monster));
                              addToTop(new TalkAction(monster, "是觉醒者，快跑呀！！"));

                          } else {
                              addToTop(new TalkAction(monster, "我被夹击了无法逃跑"));
                          }
                      }

                  }
              }

              isDone=true;
          }
      });
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new yixuehuanxue();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blood for Blood");
    }
}
