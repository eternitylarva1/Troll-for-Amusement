package Zhenghuo.patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

import static Zhenghuo.actions.ChangePlayerAction.ChangePlayer;
import static Zhenghuo.modcore.ExampleMod.NowPlayer;


public class changeRewardPatch {

//    private static final Logger logger = LogManager.getLogger(BronzeOrbPatch.class.getName());

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.screens.CombatRewardScreen",
            method = "setupItemReward"
    )
    public static class RewardPatch {
        @SpireInsertPatch(
                loc = 75
        )
        public static SpireReturn Insert(CombatRewardScreen __instance) {
            ChangePlayer(NowPlayer);
            return SpireReturn.Continue();
        }
    }
}



