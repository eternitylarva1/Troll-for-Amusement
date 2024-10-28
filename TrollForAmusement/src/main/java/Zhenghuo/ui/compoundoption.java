package Zhenghuo.ui;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;


public class compoundoption extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public compoundoption(boolean active) {
        this.label = TEXT[0];
        this.usable = active;
        this.description = TEXT[1];
        this.img = new Texture("ZhenghuoResources/images/ui/palslee1p.png");
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new compoundeffect());

        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Zhenghuo:compoundoption");
        TEXT = uiStrings.TEXT;
    }
}
