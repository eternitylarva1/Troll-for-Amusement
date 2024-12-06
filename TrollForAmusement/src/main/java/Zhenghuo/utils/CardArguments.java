package Zhenghuo.utils;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import Zhenghuo.card.CardArgument;
import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PeacePipe;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import static CardAugments.CardAugmentsMod.cardAugmentsConfig;
import static Zhenghuo.utils.CardArguments.RewardPatch.CardAugrments;

public class CardArguments implements PostInitializeSubscriber {
    public CardArguments(){
        BaseMod.subscribe(this);
    }
    private static AbstractCard fallback=(AbstractCard)new Madness();

    public static boolean Chimeraopened()
    {
        if(Loader.isModLoadedOrSideloaded("CardAugments"))
        {
            return true;
        }
        return false;
    }
    private static void initializeModifiedCards(){
        if(Chimeraopened()){
            for (String id : CardAugmentsMod.modMap.keySet()) {
                if(Loader.isModLoadedOrSideloaded("CardAugments"))
                ///遍历所有词条
                {
                    AbstractAugment a = (AbstractAugment) CardAugmentsMod.modMap.get(id);
                    //给卡牌加入词条
                    AbstractCard copy = new CardArgument(a);
                    //CardModifierManager.addModifier(copy,a);
                    copy.name = a.modifyName(copy.originalName, copy);
                    copy.rawDescription = a.modifyDescription(copy.rawDescription, copy);
                    copy.targetDrawScale = 0.75F;
                    ///加入卡池
                    CardAugrments.group.add(copy);
                }

                cardAugmentsConfig.setBool("modifyInCombat", false);



                Collections.sort(CardAugrments.group, new Comparator<AbstractCard>() {
                    @Override
                    public int compare(AbstractCard o1, AbstractCard o2) {
                        return Collator.getInstance().compare(o1.name, o2.name);
                    }
                });
            }

        }
    }

    @Override
    public void receivePostInitialize() {
        initializeModifiedCards();
    }


    public static class RewardPatch {
        public static ArrayList<AbstractCard> ModifiedCards=new ArrayList<AbstractCard>();
        public static CardGroup CardAugrments= new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        public static ArrayList<AbstractRelic> Relics= new ArrayList<>();
        public static ArrayList<String> Words= new ArrayList<>();
        public static ArrayList<String> Descriptions= new ArrayList<>();


    }


}
