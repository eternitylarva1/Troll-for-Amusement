package Zhenghuo.cardModifier;

import Zhenghuo.card.CharacterCard;
import Zhenghuo.powers.HuaPower;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import javax.smartcardio.Card;

public class HuaModifier extends AbstractCardModifier {
    public static String ID = "Zhenghuo:Hua";
    private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    public HuaModifier()
    {

    }
    // 修改描述
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(STRINGS.TEXT[0], rawDescription);
    }
    public void onInitialApplication(AbstractCard card) {
        card.type = AbstractCard.CardType.POWER;
        card.target= AbstractCard.CardTarget.SELF;

    }


    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {

        AbstractCreature target1= AbstractDungeon.player;
String name=card.name;
AbstractCard ac=new CharacterCard(card.name.replace("化",""));

        this.addToBot(new ApplyPowerAction(target1,target1,new HuaPower(target1,1,ac.name,ac)));
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new HuaModifier();
    }
    @Override
    public String modifyName(String name, AbstractCard card) {
        return String.format(STRINGS.TEXT[1], name);
    }
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
