package Zhenghuo.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MyScreen extends CustomScreen
{
    public static class Enum
    {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen MY_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen()
    {
        return Enum.MY_SCREEN;
    }

    // Note that this can be private and take any parameters you want.
    // When you call openCustomScreen it finds the first method named "open"
    // and calls it with whatever arguments were passed to it.

    private void open(String foo, AbstractCard bar)
    {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        // Call reopen in this example because the basics of
        // setting the current screen are the same across both
        reopen();
    }

    @Override
    public void reopen()
    {
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    @Override
    public void close() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void openingSettings()
    {
        // Required if you want to reopen your screen when the settings screen closes
        AbstractDungeon.previousScreen = curScreen();
    }

    // ...
}