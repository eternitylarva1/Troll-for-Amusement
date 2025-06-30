package Zhenghuo.patchs;

import Zhenghuo.powers.HeatsinkPower1;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.LiveForeverPower;

public class BurnPatch {
    @SpirePatch(clz = AbstractCard.class, method = "update")
    public static class BurnPatch1
    {
        @SpirePostfixPatch
        public static SpireReturn<Boolean> Insert(AbstractCard __instance) {
            if(!(AbstractDungeon.player==null)&&__instance instanceof Burn&&AbstractDungeon.player.hasPower(HeatsinkPower1.POWER_ID)){
                __instance.costForTurn=0;

            }
            return SpireReturn.Continue();
        }
    }
}
