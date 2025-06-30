package FanzhuanMod.patchs;

import FanzhuanMod.hook.MyModConfig;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.*;

@SpirePatch(clz = AbstractPlayer.class, method = "channelOrb")

public class ChangeOrbPatch {

    @SpireInsertPatch(
            rloc=0
    )

    public static void Insertfix(AbstractPlayer player, @ByRef AbstractOrb[] orbToSet) {
      switch (orbToSet[0].ID){
          case Lightning.ORB_ID:
              if(MyModConfig.EnableLighting)
              orbToSet[0]=new Frost();
              break;
          case Frost.ORB_ID:
              if(MyModConfig.EnableLighting)
              orbToSet[0]=new Lightning();

                  break;
          case Dark.ORB_ID:
              if(MyModConfig.EnableDark)
              orbToSet[0]=new Plasma();
              break;
          case Plasma.ORB_ID:
              if(MyModConfig.EnableDark)
              orbToSet[0]=new Dark();

              break;
      }
    }
}
