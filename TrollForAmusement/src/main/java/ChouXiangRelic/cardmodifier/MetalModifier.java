package ChouXiangRelic.cardmodifier;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import ChouXiangRelic.helpers.ModHelper;
import ChouXiangRelic.patchs.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class MetalModifier extends AbstractCardModifier {
    public static String ID = ModHelper.makePath(MetalModifier.class.getSimpleName());
    private static final UIStrings uiStrings;


    public MetalModifier() {
    }
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,new MetallicizePower(AbstractDungeon.player,card.block)));

        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                card.use(AbstractDungeon.player, (AbstractMonster) target);
                isDone=true;
            }
        });

          }
    public String modifyDescription(String rawDescription, AbstractCard card) {
        //rawDescription中的格挡改为金属化
        return rawDescription+uiStrings.TEXT[0];
    }

    public boolean shouldApply(AbstractCard card) {
        return !(CardModifierManager.hasModifier(card, ID))&&card.baseBlock>0;
    }

    public void onInitialApplication(AbstractCard card) {
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, Boolean.valueOf(true));

    }

    public void onRemove(AbstractCard card) {
        card.rawDescription= card.rawDescription.replace(uiStrings.TEXT[1], uiStrings.TEXT[0]);
    }

    public AbstractCardModifier makeCopy() {
        return new MetalModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    }
}

