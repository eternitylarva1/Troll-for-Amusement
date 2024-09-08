package Zhenghuo.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.MasterDeckSortHeader;
import com.megacrit.cardcrawl.screens.compendium.CardLibSortHeader;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;

import java.util.*;

import static Zhenghuo.screens.CharacterScreen.Enum.MY_SCREEN;
import static Zhenghuo.utils.CardArguments.RewardPatch.CardAugrments;

public class CharacterScreen extends CustomScreen implements ScrollBarListener
{
    public static class Enum
    {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen MY_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen()
    {
        return MY_SCREEN;
    }

    // Note that this can be private and take any parameters you want.
    // When you call openCustomScreen it finds the first method named "open"
    // and calls it with whatever arguments were passed to it.


    @Override
    public void reopen()
    {
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;

    }


    @Override
    public void close() {
        AbstractDungeon.isScreenUp = false;
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.showCombatPanels();
        AbstractDungeon.overlayMenu.hideBlackScreen();

    }

    public void open() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        // Call reopen in this example because the basics of
        // setting the current screen are the same across both
        reopen();
        AbstractDungeon.player.releaseCard();
        CardCrawlGame.sound.play("DECK_OPEN");
        this.currentDiffY = this.scrollLowerBound;
        this.grabStartY = this.scrollLowerBound;
        this.grabbedScreen = false;
        this.hideCards();
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = MY_SCREEN;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.hideCombatPanels();
       AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        this.calculateScrollBounds();
    }



    @Override
    public void openingSettings()
    {
        // Required if you want to reopen your screen when the settings screen closes
        AbstractDungeon.previousScreen = curScreen();

    }

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private static final float SCROLL_BAR_THRESHOLD;
    private static final int CARDS_PER_LINE = 5;
    private boolean grabbedScreen = false;
    private float grabStartY = 0.0F;
    private float currentDiffY = 0.0F;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private static final String HEADER_INFO;
    private AbstractCard hoveredCard;
    private AbstractCard clickStartedCard;
    private int prevDeckSize;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard;
    private CardLibSortHeader sortHeader;
    private int headerIndex;
    private Comparator<AbstractCard> sortOrder;
    private ArrayList<AbstractCard> tmpSortedDeck;
    private float tmpHeaderPosition;
    private int headerScrollLockRemainingFrames;
    private boolean justSorted;

    public CharacterScreen()  {
        this.scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        this.hoveredCard = null;
        this.clickStartedCard = null;
        this.prevDeckSize = 0;
        this.controllerCard = null;
        this.headerIndex = -1;
        this.sortOrder = null;
        this.tmpSortedDeck = null;
        this.tmpHeaderPosition = Float.NEGATIVE_INFINITY;
        this.headerScrollLockRemainingFrames = 0;
        this.justSorted = false;
        drawStartX = (float)Settings.WIDTH;
        drawStartX -= 5.0F * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= 4.0F * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
        padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
        this.scrollBar = new ScrollBar(this);
        this.scrollBar.move(0.0F, -30.0F * Settings.scale);
        this.sortHeader = new CardLibSortHeader(CardAugrments);
    }


    public void update() {
        this.updateControllerInput();
        if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen && !AbstractDungeon.topPanel.selectPotionMode) {
            if ((float) Gdx.input.getY() > (float)Settings.HEIGHT * 0.7F) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            } else if ((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.3F) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }
        }

        boolean isDraggingScrollBar = false;
        if (this.shouldShowScrollBar()) {
            isDraggingScrollBar = this.scrollBar.update();
        }

        if (!isDraggingScrollBar) {
            this.updateScrolling();
        }

        this.updatePositions();
        this.sortHeader.update();
        this.updateClicking();
        if (Settings.isControllerMode && this.controllerCard != null) {
            Gdx.input.setCursorPosition((int)this.controllerCard.hb.cX, (int)((float)Settings.HEIGHT - this.controllerCard.hb.cY));
        }

    }

    private void updateControllerInput() {
        if (Settings.isControllerMode && !AbstractDungeon.topPanel.selectPotionMode) {
            CardGroup deck = CardAugrments;
            boolean anyHovered = false;
            int index = 0;
            if (this.tmpSortedDeck == null) {
                this.tmpSortedDeck = deck.group;
            }

            for(Iterator var4 = this.tmpSortedDeck.iterator(); var4.hasNext(); ++index) {
                AbstractCard c = (AbstractCard)var4.next();
                if (c.hb.hovered) {
                    anyHovered = true;
                    break;
                }
            }

            anyHovered = anyHovered || this.headerIndex >= 0;
            if (!anyHovered) {
                if (this.tmpSortedDeck.size() > 0) {
                    Gdx.input.setCursorPosition((int)((AbstractCard)this.tmpSortedDeck.get(0)).hb.cX, (int)((AbstractCard)this.tmpSortedDeck.get(0)).hb.cY);
                    this.controllerCard = (AbstractCard)this.tmpSortedDeck.get(0);
                }
            } else if (this.headerIndex >= 0) {
                if (!CInputActionSet.down.isJustPressed() && !CInputActionSet.altDown.isJustPressed()) {
                    if (!CInputActionSet.left.isJustPressed() && !CInputActionSet.altLeft.isJustPressed()) {
                        if ((CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) && this.headerIndex < this.sortHeader.buttons.length - 1) {
                            this.selectSortButton(++this.headerIndex);
                        }
                    } else if (this.headerIndex > 0) {
                        this.selectSortButton(--this.headerIndex);
                    }
                } else {
                    this.sortHeader.selectionIndex = this.headerIndex = -1;
                    this.controllerCard = (AbstractCard)this.tmpSortedDeck.get(0);
                    Gdx.input.setCursorPosition((int)((AbstractCard)this.tmpSortedDeck.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.tmpSortedDeck.get(0)).hb.cY);
                }
            } else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && deck.size() > 5) {
                index -= 5;
                if (index < 0) {
                    this.selectSortButton(this.headerIndex = 0);
                    return;
                }

                Gdx.input.setCursorPosition((int)((AbstractCard)this.tmpSortedDeck.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.tmpSortedDeck.get(index)).hb.cY);
                this.controllerCard = (AbstractCard)this.tmpSortedDeck.get(index);
            } else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && deck.size() > 5) {
                if (index < deck.size() - 5) {
                    index += 5;
                } else {
                    index %= 5;
                }

                Gdx.input.setCursorPosition((int)((AbstractCard)this.tmpSortedDeck.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.tmpSortedDeck.get(index)).hb.cY);
                this.controllerCard = (AbstractCard)this.tmpSortedDeck.get(index);
            } else if (!CInputActionSet.left.isJustPressed() && !CInputActionSet.altLeft.isJustPressed()) {
                if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                    if (index % 5 < 4) {
                        ++index;
                        if (index > deck.size() - 1) {
                            index -= deck.size() % 5;
                        }
                    } else {
                        index -= 4;
                        if (index < 0) {
                            index = 0;
                        }
                    }

                    Gdx.input.setCursorPosition((int)((AbstractCard)this.tmpSortedDeck.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.tmpSortedDeck.get(index)).hb.cY);
                    this.controllerCard = (AbstractCard)this.tmpSortedDeck.get(index);
                }
            } else {
                if (index % 5 > 0) {
                    --index;
                } else {
                    index += 4;
                    if (index > deck.size() - 1) {
                        index = deck.size() - 1;
                    }
                }

                Gdx.input.setCursorPosition((int)((AbstractCard)this.tmpSortedDeck.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)this.tmpSortedDeck.get(index)).hb.cY);
                this.controllerCard = (AbstractCard)this.tmpSortedDeck.get(index);
            }

        }
    }



    private void updatePositions() {
        this.hoveredCard = null;
        int lineNum = 0;
        ArrayList<AbstractCard> cards = CardAugrments.group;
        if (this.sortOrder != null) {
            cards = new ArrayList(cards);
            Collections.sort(cards, this.sortOrder);
            this.tmpSortedDeck = cards;
        } else {
            this.tmpSortedDeck = null;
        }

        AbstractCard c;
        if (this.justSorted && this.headerScrollLockRemainingFrames <= 0) {
            c = this.highestYPosition(cards);
            if (c != null) {
                this.tmpHeaderPosition = c.current_y;
            }
        }

        int lerps;
        for(int i = 0; i < cards.size(); ++i) {
            lerps = i % 5;
            if (lerps == 0 && i != 0) {
                ++lineNum;
            }

            ((AbstractCard)cards.get(i)).target_x = drawStartX + (float)lerps * padX;
            ((AbstractCard)cards.get(i)).target_y = drawStartY + this.currentDiffY - (float)lineNum * padY;
            ((AbstractCard)cards.get(i)).update();
            ((AbstractCard)cards.get(i)).updateHoverLogic();
            if (((AbstractCard)cards.get(i)).hb.hovered) {
                this.hoveredCard = (AbstractCard)cards.get(i);
            }
        }

        c = this.highestYPosition(cards);
        if (this.justSorted && c != null) {
            lerps = 0;
            float lerpY = c.current_y;

            for(float lerpTarget = c.target_y; lerpY != lerpTarget; ++lerps) {
                lerpY = MathHelper.cardLerpSnap(lerpY, lerpTarget);
            }

            this.headerScrollLockRemainingFrames = lerps;
        }

        this.headerScrollLockRemainingFrames -= Settings.FAST_MODE ? 2 : 1;
        if (cards.size() > 0 && this.sortHeader != null && c != null) {
           /// this.sortHeader.updateScrollPosition(this.headerScrollLockRemainingFrames <= 0 ? c.current_y : this.tmpHeaderPosition);
            this.justSorted = false;
        }

    }

    private AbstractCard highestYPosition(List<AbstractCard> cards) {
        if (cards == null) {
            return null;
        } else {
            float highestY = 0.0F;
            AbstractCard retVal = null;
            Iterator var4 = cards.iterator();

            while(var4.hasNext()) {
                AbstractCard card = (AbstractCard)var4.next();
                if (card.current_y > highestY) {
                    highestY = card.current_y;
                    retVal = card;
                }
            }

            return retVal;
        }
    }

    private void updateScrolling() {
        int y = InputHelper.mY;
        if (!this.grabbedScreen) {
            if (InputHelper.scrolledDown) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            } else if (InputHelper.scrolledUp) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }

            if (InputHelper.justClickedLeft) {
                this.grabbedScreen = true;
                this.grabStartY = (float)y - this.currentDiffY;
            }
        } else if (InputHelper.isMouseDown) {
            this.currentDiffY = (float)y - this.grabStartY;
        } else {
            this.grabbedScreen = false;
        }

        if (this.prevDeckSize != CardAugrments.size()) {
            this.calculateScrollBounds();
        }

        this.resetScrolling();
        this.updateBarPosition();
    }

    private void updateClicking() {
        if (this.hoveredCard != null) {
            CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
            if (InputHelper.justClickedLeft) {
                this.clickStartedCard = this.hoveredCard;
            }

            if ((InputHelper.justReleasedClickLeft && this.hoveredCard == this.clickStartedCard || CInputActionSet.select.isJustPressed()) && this.headerIndex < 0) {
                InputHelper.justReleasedClickLeft = false;
                CardCrawlGame.cardPopup.open(this.hoveredCard, CardAugrments);
                this.clickStartedCard = null;
            }
        } else {
            this.clickStartedCard = null;
        }

    }

    private void calculateScrollBounds() {
        if (CardAugrments.size() > 10) {
            int scrollTmp = CardAugrments.size() / 5 - 2;
            if (CardAugrments.size() % 5 != 0) {
                ++scrollTmp;
            }

            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + (float)scrollTmp * padY;
        } else {
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }

        this.prevDeckSize = CardAugrments.size();
    }

    private void resetScrolling() {
        if (this.currentDiffY < this.scrollLowerBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
        } else if (this.currentDiffY > this.scrollUpperBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
        }

    }

    private void hideCards() {
        int lineNum = 0;
        ArrayList<AbstractCard> cards = CardAugrments.group;

        for(int i = 0; i < cards.size(); ++i) {
            int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }

            ((AbstractCard)cards.get(i)).current_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).current_y = drawStartY + this.currentDiffY - (float)lineNum * padY - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale);
            ((AbstractCard)cards.get(i)).targetDrawScale = 0.75F;
            ((AbstractCard)cards.get(i)).drawScale = 0.75F;
            ((AbstractCard)cards.get(i)).setAngle(0.0F, true);
        }

    }

    public void render(SpriteBatch sb) {
        if (this.shouldShowScrollBar()) {
            this.scrollBar.render(sb);
        }

        if (this.hoveredCard == null) {
            CardAugrments.renderMasterDeck(sb);
        } else {
            CardAugrments.renderMasterDeckExceptOneCard(sb, this.hoveredCard);
            this.hoveredCard.renderHoverShadow(sb);
            this.hoveredCard.render(sb);
            AbstractRelic tmp;
            float prevX;
            float prevY;
            if (this.hoveredCard.inBottleFlame) {
                tmp = RelicLibrary.getRelic("Bottled Flame");
                prevX = tmp.currentX;
                prevY = tmp.currentY;
                tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            } else if (this.hoveredCard.inBottleLightning) {
                tmp = RelicLibrary.getRelic("Bottled Lightning");
                prevX = tmp.currentX;
                prevY = tmp.currentY;
                tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            } else if (this.hoveredCard.inBottleTornado) {
                tmp = RelicLibrary.getRelic("Bottled Tornado");
                prevX = tmp.currentX;
                prevY = tmp.currentY;
                tmp.currentX = this.hoveredCard.current_x + 130.0F * Settings.scale;
                tmp.currentY = this.hoveredCard.current_y + 182.0F * Settings.scale;
                tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            }
        }

        CardAugrments.renderTip(sb);
        FontHelper.renderDeckViewTip(sb, HEADER_INFO, 96.0F * Settings.scale, Settings.CREAM_COLOR);
        this.sortHeader.render(sb);
    }

    public void scrolledUsingBar(float newPercent) {
        this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
        this.updateBarPosition();
    }

    private void updateBarPosition() {
        float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
        this.scrollBar.parentScrolledToPercent(percent);
    }

    private boolean shouldShowScrollBar() {
        return this.scrollUpperBound > SCROLL_BAR_THRESHOLD;
    }

    public void setSortOrder(Comparator<AbstractCard> sortOrder) {
        if (this.sortOrder != sortOrder) {
            this.justSorted = true;
        }

        this.sortOrder = sortOrder;
    }

    private void selectSortButton(int index) {
        Hitbox hb = this.sortHeader.buttons[this.headerIndex].hb;
        Gdx.input.setCursorPosition((int)hb.cX, Settings.HEIGHT - (int)hb.cY);
        this.controllerCard = null;
        this.sortHeader.selectionIndex = this.headerIndex;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("MasterDeckViewScreen");
        TEXT = uiStrings.TEXT;
        drawStartY = (float)Settings.HEIGHT * 0.66F;
        SCROLL_BAR_THRESHOLD = 500.0F * Settings.scale;
        HEADER_INFO = TEXT[0];
    }
}