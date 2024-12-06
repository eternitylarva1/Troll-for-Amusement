package Zhenghuo.cardModifier;

import Zhenghuo.card.CharacterCard;
import Zhenghuo.powers.HuaPower;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShuangModifier extends AbstractCardModifier {
    public static String ID = "Zhenghuo:Shuang";
    private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    public ShuangModifier()
    {

    }
    // 修改描述
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return String.format(STRINGS.TEXT[0], rawDescription);
    }
    public void onInitialApplication(AbstractCard card) {


    }


    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        card.use(AbstractDungeon.player, (AbstractMonster)target);
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ShuangModifier();
    }
    @Override
    public String modifyName(String name, AbstractCard card) {
        return name;
    }
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
