package FanzhuanMod.patchs;

import FanzhuanMod.hook.MyModConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.stances.WrathStance;

@SpirePatch(clz = WrathStance.class, method = SpirePatch.CONSTRUCTOR)
public class StancePatch {

    @SpirePostfixPatch

    public static SpireReturn Insertfix(WrathStance stance) {
        if(MyModConfig.EnableCalm)
            stance.ID="Calm";

        return SpireReturn.Continue();
    }

}
