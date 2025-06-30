package ChouXiangRelic.cardmodifier;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import ChouXiangRelic.helpers.ModHelper;
import ChouXiangRelic.patchs.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThronsModifier extends AbstractCardModifier {
    public static String ID = ModHelper.makePath(ThronsModifier.class.getSimpleName());
    private static final UIStrings uiStrings;


    public ThronsModifier() {
    }
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {

        ///this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,new ThornsPower(AbstractDungeon.player,card.damage)));
        card.use(AbstractDungeon.player,  (target instanceof AbstractMonster) ? (AbstractMonster)target : null);


    }
    public String modifyDescription(String rawDescription, AbstractCard card) {
        //rawDescription中的格挡改为金属化
        return rawDescription.replace(uiStrings.TEXT[0], uiStrings.TEXT[1]).replace(uiStrings.TEXT[2], uiStrings.TEXT[3]);
    }

    public boolean shouldApply(AbstractCard card) {
        return !(CardModifierManager.hasModifier(card, ID))&&card.baseDamage>0;
    }

    public void onInitialApplication(AbstractCard card) {
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, Boolean.valueOf(true));

    }

    public void onRemove(AbstractCard card) {
        card.rawDescription= card.rawDescription.replace(uiStrings.TEXT[1], uiStrings.TEXT[0]);
    }

    public AbstractCardModifier makeCopy() {
        return new ThronsModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    }
}

