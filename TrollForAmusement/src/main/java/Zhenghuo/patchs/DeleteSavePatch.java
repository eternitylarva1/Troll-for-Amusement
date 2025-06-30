package Zhenghuo.patchs;



import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.LiveForeverPower;


public class DeleteSavePatch
{
    @SpirePatch(clz = com.megacrit.cardcrawl.helpers.SaveHelper.class, method = "shouldDeleteSave")
    public static class ShouldDeleteSavePatch
    {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Boolean> Insert() {
            if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasPower(LiveForeverPower.POWER_ID)) {
               AbstractDungeon.player.getPower(Zhenghuo.powers.LiveForeverPower.POWER_ID).atEndOfTurn(true);
                return SpireReturn.Return(Boolean.valueOf(false));
            }
        else return SpireReturn.Continue();
        }
    }
}
