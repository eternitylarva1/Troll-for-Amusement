package FanzhuanMod.patchs;

import FanzhuanMod.hook.MyModConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;

public class GetStancePatch {
    @SpirePatch(clz = AbstractStance.class, method = "getStanceFromName")
    public static class CalmPatch {

        @SpirePrefixPatch

        public static SpireReturn<AbstractStance>  Insertfix(String name) {
            if(MyModConfig.EnableCalm){
                if (name.equals("Calm")) {
                    return SpireReturn.Return(new WrathStance()) ;
                } else if (name.equals("Wrath")) {
                    return SpireReturn.Return(new CalmStance());
                }
            }
         return SpireReturn.Continue();

        }



    }


}
