package Zhenghuo.card;


import Zhenghuo.actions.RandomDescription;
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
import static Zhenghuo.utils.CardArguments.RewardPatch.Descriptions;

public class GainDescriptions extends CustomCard implements CustomSavable<String> , SpawnModificationCard {

    public static final String ID = ModHelper.makePath("GainDescriptions");
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

    private String RandomWord="";

    public GainDescriptions(String NAME) {

        //  为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, String.format(NAMES,NAME), IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.RandomWord=NAME;
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
        this.rawDescription=DESCRIPTION;
        this.rawDescription=String.format(this.rawDescription,NAME);
        this.initializeDescription();
        tags.add(CardTags.HEALING);
    }

    public GainDescriptions() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        this("斩杀");
    }




    public AbstractCard makeCopy()
    {
        return new GainDescriptions(RandomWord);
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
            this.name= String.format(NAMES, RandomWord);
            this.Text=TextImageGenerator.getTextImage(RandomWord);
            Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
            TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
            customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
            this.portrait = customRegion;
            this.rawDescription=DESCRIPTION;
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.rawDescription=String.format(this.rawDescription,RandomWord);
            this.initializeDescription();
        }
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RandomDescription(false,this.RandomWord));
    }

    @Override
    public String onSave() {

        return this.RandomWord;
    }

    @Override
    public void onLoad(String s) {
        this.RandomWord=s;
        this.name= String.format(NAMES, RandomWord);
        this.Text=TextImageGenerator.getTextImage(RandomWord);
        Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
        this.portrait = customRegion;
        this.rawDescription=DESCRIPTION;
        this.rawDescription=String.format(this.rawDescription,RandomWord);
        this.initializeDescription();
    }


   
    public AbstractCard replaceWith(ArrayList<AbstractCard> currentRewardCards) {

            this.RandomWord = Descriptions.get(AbstractDungeon.cardRandomRng.random(Descriptions.size() - 1));
            this.name = String.format(NAMES, RandomWord);
            this.Text = TextImageGenerator.getTextImage(RandomWord);
            Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
            TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
            customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
            this.portrait = customRegion;
            this.rawDescription = DESCRIPTION;
        this.rawDescription=String.format(this.rawDescription,RandomWord);
            this.initializeDescription();
        System.out.println("已经将随机牌"+this.RandomWord+"替换");

        return new GainDescriptions(this.RandomWord);
    }

    }





