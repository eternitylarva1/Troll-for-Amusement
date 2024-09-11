package Zhenghuo.events;

import Zhenghuo.card.CharacterCardEvent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.random.Random;

import static com.megacrit.cardcrawl.helpers.CardLibrary.getAllCards;

public class MyFirstEvent extends AbstractImageEvent {
    public static final String ID = "CharacterGather";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    //This text should be set up through loading an event localization json file
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private final Texture pressKey1;

    private AbstractCard hoverCard;


    private final AbstractCard[][] cardsMatrix = new AbstractCard[8][4];

    // 定义每行和每列的卡片数量

    private final Hitbox button;
    // 卡片之间的间距

    private final CardGroup characterCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final CardGroup RewardCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final CardGroup PlayCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final CardGroup LeftCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private Screen screen;

    public MyFirstEvent() {
        super(NAME, DESCRIPTIONS[0], "images/events/matchAndKeep.jpg");
        this.screen=Screen.INTRO;
        //This is where you would create your dialog options
        this.imageEventText.setDialogOption(OPTIONS[0]); //This adds the option to a list of options
        button = new Hitbox(120, 120);
        this.pressKey1 = new Texture("ZhenghuoResources/images/ui/okay.png");
for(int i=0;i<9;i++)
{
    RewardCards.addToTop(getAllCards().get(AbstractDungeon.cardRandomRng.random(getAllCards().size()-1)));
}
for(AbstractCard c:RewardCards.group)
{
    for(char m:c.name.toCharArray())
    {
        PlayCards.group.add(new CharacterCardEvent(Character.toString(m)));
    };
    button.move(convertX(0), convertY(2.5f));


}


    }
    private float convertX(float x) {
        return x * 235.0F * Settings.scale-2*235.0F * Settings.scale + 640.0F * Settings.scale;
    }

    private float convertY(float y) {
        return y * -235.0F * Settings.scale + 850.0F * Settings.scale;
    }
    private void moveCard(AbstractCard card, int x, int y) {
        cardsMatrix[x][y] = card;
        card.target_x = convertX(x);
        card.target_y = convertY(y);
    }

    private void initializeCards() {
        System.out.println("正在初始化");
        Random rng = AbstractDungeon.cardRandomRng;
        int cardCount =28;
        for (int i = 0; i < cardCount; i++) {
            putNewCard();
        }
    }
    private void updateHitbox(Hitbox hitbox) {
        hitbox.update();
        if (InputHelper.justClickedLeft && hitbox.hovered) {
            hitbox.clickStarted = true;
        }
    }
    public void update()
    {
        super.update();
        this.characterCards.update();

        hoverCard = null;
        for (AbstractCard card : this.characterCards.group) {
            card.hb.update();
            if (card.hb.hovered) {
                card.targetDrawScale = .7f;
                hoverCard = card;
            } else {

                card.targetDrawScale = .5f;
            }
        }
        if(this.screen==Screen.PLAY){
            updateHitbox(button);
            if (InputHelper.justReleasedClickLeft) {
                button.clicked = false;}
        }
        if(InputHelper.isMouseDown&&hoverCard!=null&&InputHelper.justClickedLeft)
        {
            if(!LeftCards.group.contains(hoverCard))
            {
                if(LeftCards.size()<4) {
                    moveCard(hoverCard, 0, LeftCards.size());
                    LeftCards.addToTop(hoverCard);
                    hoverCard.beginGlowing();
                    for (int i = 1; i < cardsMatrix.length; i++) {
                        for (int j = 0; j < cardsMatrix[i].length; j++) {
                            if (cardsMatrix[i][j] == hoverCard) {
                                cardsMatrix[i][j] = null;
                                return;
                            }
                        }
                    }
                }
            }
            else{
                LeftCards.removeCard(hoverCard);
                hoverCard.stopGlowing();
                for (int i=1;i<8;i++) {
                   for(int j=0;j<3;j++)
                   {
                       if(cardsMatrix[i][j]==null)
                       {
                           moveCard(hoverCard,i,j);
                           return;
                       }
                   }
                }

            }
        }

    }
    private void putNewCard() {
        if(!PlayCards.group.isEmpty()){
        System.out.println("将新的卡将入游戏");
        Random rng = AbstractDungeon.cardRandomRng;

        AbstractCard card = PlayCards.group.get(rng.random(PlayCards.size()-1));
        PlayCards.group.remove(card);
        if (rng.randomBoolean()) {
            card.upgrade();
        }

        while (true) {
            int x = rng.random(1,7);
            int y = rng.random(0,3);
            if (cardsMatrix[x][y] != null) {
                continue;
            }

            characterCards.addToTop(card);
            card.targetDrawScale = 0.5F;
            card.drawScale = 0.001f;
            moveCard(card, x, y);
            card.current_x = card.target_x;
            card.current_y = card.target_y;
            return;
        }
        }
    }

    public void render(SpriteBatch sb) {
        super.render(sb);


        if (screen == Screen.PLAY) {
            ///System.out.println("检测到进入play环节");
            this.characterCards.render(sb);
            if (hoverCard != null) {
                hoverCard.render(sb);
            }
            renderArrow(sb, button, 0, false);
            button.render(sb);




        }
/*

        }*/
    }
    private void renderArrow(SpriteBatch sb, Hitbox hitbox, float rotation, boolean flipX) {
        Texture popupArrow = ImageMaster.OPTION_YES;
        sb.setColor(1, 1, 1, 1);
        sb.draw(popupArrow,
                hitbox.cX - popupArrow.getWidth() / 2f,
                hitbox.cY - popupArrow.getHeight() / 2f,
                popupArrow.getWidth() / 2f,
                popupArrow.getHeight() / 2f,
                popupArrow.getWidth(),
                popupArrow.getHeight(),
                Settings.scale * 0.8f,
                Settings.scale * 0.8f,
                rotation,
                0,
                0,
                popupArrow.getWidth(),
                popupArrow.getHeight(),
                flipX,
                false);
     FontHelper.renderFont(sb,FontHelper.topPanelAmountFont,"确认",hitbox.cX - popupArrow.getWidth() / 2f,hitbox.cY - popupArrow.getHeight() / 2f+50, Color.WHITE);

        if (hitbox.hovered) {
            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            sb.setColor(1, 1, 1, 0.5f);
            sb.draw(popupArrow,
                    hitbox.cX - popupArrow.getWidth() / 2f,
                    hitbox.cY - popupArrow.getHeight() / 2f,
                    popupArrow.getWidth() / 2f,
                    popupArrow.getHeight() / 2f,
                    popupArrow.getWidth(),
                    popupArrow.getHeight(),
                    Settings.scale * 0.8f,
                    Settings.scale * 0.8f,
                    rotation,
                    0,
                    0,
                    popupArrow.getWidth(),
                    popupArrow.getHeight(),
                    flipX,
                    false);
            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
    }
    private enum Screen {
        INTRO,
        RULE_EXPLANATION,
        PLAY,
        GET_CARD,
        COMPLETE,
    }
    @Override
    protected void buttonEffect(int buttonPressed) {
        //It is best to look at examples of what to do here in the basegame event classes, but essentially when you click on a dialog option the index of the pressed button is passed here as buttonPressed.
        switch(this.screen) {
            case INTRO:
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                this.imageEventText.setDialogOption(OPTIONS[1]);
                this.screen = Screen.RULE_EXPLANATION;
                return;
            case RULE_EXPLANATION:
                this.imageEventText.removeDialogOption(1);
                this.imageEventText.removeDialogOption(0);
                if (buttonPressed == 1) {
                    this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                    this.imageEventText.setDialogOption(OPTIONS[1]);
                    this.screen = Screen.COMPLETE;
                } else {
                    GenericEventDialog.hide();
                    this.screen = Screen.PLAY;
                    initializeCards();
                }

                return;
        }

    }
}