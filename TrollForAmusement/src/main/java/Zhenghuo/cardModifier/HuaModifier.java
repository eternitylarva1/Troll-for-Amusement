package Zhenghuo.cardModifier;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class HuaModifier extends AbstractCardModifier {
    public static String ID = "Zhenghuo:Hua";
    private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString(ID);

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
        //todo 这里要写一个能力，回合开始时打出卡牌的复制
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new HuaModifier();
    }
    @Override
    public String modifyName(String name, AbstractCard card) {
        return String.format(STRINGS.TEXT[1], name);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
