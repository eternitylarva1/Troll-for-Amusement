package Zhenghuo.events;

import Zhenghuo.card.CharacterCard;
import Zhenghuo.card.TongpeiCard;
import Zhenghuo.modcore.CustomTags;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Zhenghuo.actions.BetteGatheRelicAction.resultRelic;
import static Zhenghuo.actions.GatherCharacterAction.result;
import static Zhenghuo.utils.CardArguments.RewardPatch.Relics;
import static com.megacrit.cardcrawl.helpers.CardLibrary.getAllCards;

public class MyFirstEvent extends AbstractImageEvent {
    public static final String ID = "CharacterGather";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    //This text should be set up through loading an event localization json file
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private final Texture pressKey1;
    private  AbstractCard selectedCard=null;
    private AbstractCard hoverCard;
    private AbstractRelic selectedRelic;


    private final AbstractCard[][] cardsMatrix = new AbstractCard[8][4];

    // 定义每行和每列的卡片数量

    private final Hitbox button;
    // 卡片之间的间距
    private float timer = 18.0F;
    private final CardGroup characterCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final CardGroup RewardCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final CardGroup PlayCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final CardGroup LeftCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final ArrayList<AbstractRelic> RewardRelics=new ArrayList<>();
    private Screen screen;

    public MyFirstEvent() {
        super(NAME, DESCRIPTIONS[0], "images/events/matchAndKeep.jpg");
        this.screen=Screen.INTRO;
        //This is where you would create your dialog options
        this.imageEventText.setDialogOption(OPTIONS[0]); //This adds the option to a list of options
        button = new Hitbox(120, 120);
        this.pressKey1 = new Texture("ZhenghuoResources/images/ui/okay.png");
for(int i=0;i<7;i++)
{
    RewardCards.addToTop(getAllCards().get(AbstractDungeon.cardRandomRng.random(getAllCards().size()-1)));
}
for(int i=0;i<2;i++){
    RewardRelics.add(Relics.get(AbstractDungeon.cardRandomRng.random(Relics.size()-1)));
}
        for(AbstractRelic c:RewardRelics)
        {
            for(char m:c.name.toCharArray())
            {
                PlayCards.group.add(new CharacterCardEvent(Character.toString(m)));
            };
            button.move(convertX(-0.5f), convertY(2.5f));

        }
for(AbstractCard c:RewardCards.group)
{
    for(char m:c.name.toCharArray())
    {
        PlayCards.group.add(new CharacterCardEvent(Character.toString(m)));
    };
    button.move(convertX(-0.5f), convertY(2.5f));

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
    private void updateGameLogic() {
if(button.clicked)
{System.out.println("检测到按下按钮");
    complete();
}
    }
    private void complete() {
        screen = Screen.GET_CARD;
        GenericEventDialog.show();
        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);

        this.imageEventText.setDialogOption(OPTIONS[1]);

        if(!LeftCards.group.isEmpty()) {
            AbstractCard c;
            Iterator var4 = LeftCards.group.iterator();
            ArrayList<Character> ChList = new ArrayList<>();
            int upgradenum = 0;
            while (var4.hasNext()) {
                c = (AbstractCard) var4.next();


                if (!(c instanceof TongpeiCard)) {

                    System.out.println("正在尝试组合" + c.name);
                    for (char ch : c.name.toCharArray()) {
                        if (!(ch == "+".charAt(0) || Character.isDigit(ch))) {
                            ChList.add(ch);
                            System.out.println("已将" + ch + "加入检索序列");
                        } else if (Character.isDigit(ch)) {
                            int number = ch - '0' - 1;
                            System.out.println("检测到数字" + number + "转换成升级");

                            upgradenum += number;

                        } else {
                            upgradenum++;
                        }


                    }
                } else {
                    System.out.println("检测到名字为通配符，改为*");
                    ChList.add("*".charAt(0));
                }
            }


            List<AbstractCard> result = result(ChList);
            List<AbstractRelic> resultRelic=resultRelic(ChList);
            if(!result.isEmpty()) {
                this.selectedCard = result.get(AbstractDungeon.cardRandomRng.random(0, result.size() - 1)).makeSameInstanceOf();
                if(!(selectedCard.hasTag(CustomTags.WordCard))) {
                    selectedCard.name = selectedCard.originalName;
                }
                if(upgradenum>0){
                    for(int i=0;i<upgradenum;i++)
                    {
                        selectedCard.upgraded=false;
                        selectedCard.upgrade();
                    }
                }
                System.out.println("已经将"+this.selectedCard.name+"加入奖励");
            }
            else if(!resultRelic.isEmpty())
            {
                this.selectedRelic=resultRelic.get(AbstractDungeon.cardRandomRng.random(0, resultRelic.size() - 1)).makeCopy();
                System.out.println("已经将"+this.selectedRelic.name+"加入奖励");
            }
            else{
                StringBuilder sb = new StringBuilder();
                for (char str : ChList) {

                    sb.append(str);
                }
                this.selectedCard=new CharacterCard(sb.toString());
            }
        }
        if(this.selectedCard!=null)
        {
            this.imageEventText.setDialogOption(String.format(OPTIONS[3],this.selectedCard.name), this.selectedCard);

        }
        else
        if(this.selectedRelic!=null)
        {
            this.imageEventText.setDialogOption(String.format(OPTIONS[3],this.selectedRelic.name), this.selectedRelic);
        }else{
            this.selectedCard=new CharacterCard("空");
            this.imageEventText.setDialogOption(String.format(OPTIONS[3],this.selectedCard.name), this.selectedCard);
        }
     characterCards.clear();

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
            } else if(LeftCards.contains(card)){

                card.targetDrawScale = .4f;
            }
            else{
                card.targetDrawScale = .5f;
            }
        }
        if(this.screen==Screen.PLAY){
            timer-= Gdx.graphics.getDeltaTime();
            if(timer<=0){
                complete();
            }
            updateHitbox(button);
            updateGameLogic();
            if (InputHelper.justReleasedClickLeft) {
                button.clicked = false;}

        }
        if(InputHelper.isMouseDown&&hoverCard!=null&&InputHelper.justClickedLeft)
        {
            if(!LeftCards.group.contains(hoverCard))
            {
                if(LeftCards.size()<5) {

                    hoverCard.target_x = convertX(0);
                    hoverCard.target_y = convertY(0.8f*LeftCards.size());
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
                for(AbstractCard c:LeftCards.group)
                {
                    c.target_x=convertX(0);
                    c.target_y=convertY(0.8f*LeftCards.group.indexOf(c));
                }
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
        if (rng.randomBoolean(0.3f)) {
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
     FontHelper.renderFont(sb,FontHelper.menuBannerFont,"剩余时间"+(int)timer,convertX(6),convertY(-0.5f), Color.WHITE);
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
            case GET_CARD:
                if (buttonPressed == 1 ) {
                    if(selectedCard!=null) {
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.selectedCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }
                    else if(selectedRelic!=null)
                    {
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F,this.selectedRelic);
                    }
                    else {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    }
                } else {
                    this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                }
                this.imageEventText.removeDialogOption(1);
                this.imageEventText.removeDialogOption(0);
                this.imageEventText.setDialogOption(OPTIONS[1]);
                this.screen = Screen.COMPLETE;
                return;
            case COMPLETE:
                this.openMap();
                return;
        }

    }
}