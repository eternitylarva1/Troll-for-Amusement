package Zhenghuo.ui;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;


public class compoundoption extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public compoundoption(boolean active) {
        this.label = TEXT[0];
        this.usable = active;
        this.description = TEXT[1];
        this.img = ImageMaster.CAMPFIRE_TOKE_BUTTON;
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new CampfireTokeEffect());
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Toke Option");
        TEXT = uiStrings.TEXT;
    }
}
