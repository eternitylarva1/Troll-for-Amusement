package ChouXiangRelic.patchs;

import ChouXiangRelic.powers.HalfDamageThronsPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;


@SpirePatch(clz = AbstractMonster.class, method = "damage")
    public class DamagePatch {

        @SpireInsertPatch(
                rloc = 8,
                localvars = {"damageAmount"}
        )
        public static SpireReturn Insert(AbstractMonster monster, DamageInfo __info, int damageAmount) {
            if(damageAmount>0){
                if(__info.type== DamageInfo.DamageType.NORMAL&& __info.owner instanceof AbstractPlayer){
                    if(AbstractDungeon.player.hasPower(HalfDamageThronsPower.POWER_ID)){
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,new ThornsPower(AbstractDungeon.player,damageAmount)));
                        return SpireReturn.Return();
                    }
                }
            }
            return SpireReturn.Continue();
        }

}


