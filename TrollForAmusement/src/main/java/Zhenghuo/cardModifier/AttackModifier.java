package Zhenghuo.cardModifier;


import CardAugments.patches.InterruptUseCardFieldPatches;
import CardAugments.util.PortraitHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class AttackModifier extends AbstractCardModifier {
    public static String ID = "Zhenghuo:attack";
    private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    // 修改描述
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(STRINGS.TEXT[0], rawDescription);
    }




    @Override
    public AbstractCardModifier makeCopy() {
        return new AttackModifier();
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
