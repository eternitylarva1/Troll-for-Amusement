package Zhenghuo.card;


import Zhenghuo.helpers.ModHelper;
import Zhenghuo.utils.TextImageGenerator;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.OnStartBattleSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static Zhenghuo.player.Mycharacter.PlayerColorEnum.CharacterBlack;
import static Zhenghuo.utils.CardArguments.RewardPatch.ModifiedCards;
import static Zhenghuo.utils.CardArguments.RewardPatch.Words;

public class RandomNumberCard extends CustomCard implements CustomSavable<Integer> , OnStartBattleSubscriber , SpawnModificationCard {

    public static final String ID = ModHelper.makePath("RandomNumberCard");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "ZhenghuoResources/images/Character.png";
    private static final int COST = 1;
    public static String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String NAMES = CARD_STRINGS.NAME;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CharacterBlack;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private Texture Text;
    private boolean haschanged=false;
    public boolean isAugrment=false;

    private int RandomNumber =2;

    public RandomNumberCard(int NAME) {

        //  为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, String.format(NAMES,NAME ) , IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        BaseMod.subscribe(this);
        this.RandomNumber =NAME;
        this.baseDamage = this.damage = 1;
        this.baseBlock = this.block = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.exhaust=true;
        this.Text=TextImageGenerator.getTextImage(NAME+"?");
        Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);
        /*

        List<Character> charList = this.name.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        List<AbstractCard> CardList=result(charList);
        if(!CardList.isEmpty())
        {
            this.rawDescription=CardList.get(0).rawDescription;
            this.initializeDescription();
        }*/
// Step 3: 设置卡牌的portrait属
        this.portrait = customRegion;
        this.rawDescription=DESCRIPTION+RandomNumber+"字的牌";
        this.initializeDescription();
        tags.add(CardTags.HEALING);
    }

    public RandomNumberCard() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        this(2);
    }




    public AbstractCard makeCopy()
    {

        return new RandomNumberCard(RandomNumber);
    }
    public void update()
{
    super.update();
}
    public void onChoseThisOption() {
        AbstractDungeon.player.hand.moveToHand(this);
    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.name= String.format(NAMES, RandomNumber);
            this.Text=TextImageGenerator.getTextImage(String.valueOf(RandomNumber));
            Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
            TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
            customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
            this.portrait = customRegion;
            this.rawDescription=DESCRIPTION;
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.rawDescription = DESCRIPTION+RandomNumber+"字的牌";
            this.initializeDescription();
        }
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public Integer onSave() {

        return this.RandomNumber;
    }

    @Override
    public void onLoad(Integer s) {
        this.RandomNumber =s;
        this.name= String.format(NAMES, RandomNumber);
        this.Text=TextImageGenerator.getTextImage(String.valueOf(RandomNumber
        ));
        Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
        this.portrait = customRegion;
        this.rawDescription=DESCRIPTION+RandomNumber+"字的牌";
        this.initializeDescription();
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        for (AbstractCard abstractCard : AbstractDungeon.player.drawPile.group) {
            if(abstractCard ==this)
            {
                System.out.println("检测到随机牌"+this.RandomNumber);
                int targetChar =this.RandomNumber;
                ArrayList<AbstractCard> newList =ModifiedCards.stream()
                        .filter(element -> element.name.length()==targetChar)
                        .collect(Collectors.toCollection(ArrayList::new));
                if(!newList.isEmpty()) {
                    AbstractDungeon.player.drawPile.group.set(AbstractDungeon.player.drawPile.group.indexOf(abstractCard), newList.get(AbstractDungeon.cardRandomRng.random(newList.size() - 1)).makeSameInstanceOf());
                }
            }
        }

    }
    public AbstractCard replaceWith(ArrayList<AbstractCard> currentRewardCards) {

            this.RandomNumber = AbstractDungeon.cardRandomRng.random(1,5);
            this.name = String.format(NAMES, RandomNumber);
            this.Text = TextImageGenerator.getTextImage(String.valueOf(RandomNumber));
            Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
            TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
            customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
            this.portrait = customRegion;
            this.rawDescription = DESCRIPTION+RandomNumber+"字的牌";
            this.initializeDescription();
            System.out.println("已经将随机牌"+this.RandomNumber +"替换");

        return new RandomNumberCard(this.RandomNumber);
    }

    }





