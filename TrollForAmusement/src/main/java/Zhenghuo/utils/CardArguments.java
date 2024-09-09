package Zhenghuo.utils;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.daily.mods.Chimera;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

import java.util.ArrayList;
import java.util.Map;

import static Zhenghuo.actions.ChangePlayerAction.ChangePlayer;
import static Zhenghuo.modcore.ExampleMod.NowPlayer;

public class CardArguments {
    private static AbstractCard fallback=(AbstractCard)new Madness();

    public static boolean Chimeraopened()
    {
        if(Loader.isModLoadedOrSideloaded("CardAugments"))
        {
            return true;
        }
        return false;
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.helpers.CardLibrary",
            method = "getAllCards"
    )
    public static class RewardPatch {
        public static ArrayList<AbstractCard> ModifiedCards=new ArrayList<AbstractCard>();
        public static CardGroup CardAugrments= new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        public static ArrayList<AbstractRelic> Relics= new ArrayList<>();
        @SpireInsertPatch(
                loc = 1119,
                localvars= {"c","retVal"}
        )
        public static SpireReturn Insert(Map.Entry<String, AbstractCard> c, ArrayList<AbstractCard> retVal) {
            if(Chimeraopened()){
                ///如果奇美拉打开


            }
            return SpireReturn.Continue();
        }

    }


}
