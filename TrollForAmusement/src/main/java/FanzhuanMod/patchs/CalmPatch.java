package FanzhuanMod.patchs;

import FanzhuanMod.hook.MyModConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.stances.CalmStance;

@SpirePatch(clz = CalmStance.class, method = SpirePatch.CONSTRUCTOR)
public class CalmPatch {

    @SpirePostfixPatch

    public static SpireReturn Insertfix(CalmStance stance) {
        if(MyModConfig.EnableCalm) {
            stance.ID = "Wrath";
        }

        return SpireReturn.Continue();
    }

}
