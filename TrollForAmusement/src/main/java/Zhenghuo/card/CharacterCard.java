package Zhenghuo.card;


import Zhenghuo.helpers.ModHelper;
import Zhenghuo.utils.TextImageGenerator;
import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Zhenghuo.player.Mycharacter.PlayerColorEnum.CharacterBlack;
import static Zhenghuo.utils.CardArguments.RewardPatch.Words;
@AutoAdd.Ignore
public class CharacterCard extends CustomCard implements CustomSavable<String>, SpawnModificationCard {

    public static final String ID = ModHelper.makePath("CharacterCard");
    public ArrayList<AbstractCard> sutureCards;
    private static final String IMG_PATH = "ZhenghuoResources/images/Character.png";
    private static final int COST = 1;
    public static final String DESCRIPTION =Settings.language == Settings.GameLanguage.ZHS?"zhenghuo:文字牌 NL 消耗 ":"A normal card NL Exhaust";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CharacterBlack;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private Texture Text;
    private boolean haschanged=false;
    public boolean isAugrment=false;
public ArrayList<AbstractCard> CardPool=new ArrayList<>();

    public CharacterCard(String NAME,String DESCRIPTION) {

        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 0;
        this.baseBlock = this.block = 0;
        this.baseMagicNumber = this.magicNumber = 0;
        this.exhaust=true;
        this.Text=TextImageGenerator.getTextImage(NAME);
        Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);
        CardPool.add(new Strike_Red());
        CardPool.add(new Defend_Red());
// Step 3: 设置卡牌的portrait属
        this.portrait = customRegion;
        rawDescription=DESCRIPTION;
        rawDescription=this.getthisDescription()+" NL "+rawDescription;
        this.initializeDescription();


    }

    public CharacterCard() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        this(katu);
    }
    public CharacterCard(String NAME)
    {
        this(NAME, DESCRIPTION);
    }

public static String katu="";


    public AbstractCard makeCopy()
    {
        return new CharacterCard(this.name,this.rawDescription);
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
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.initializeDescription();
        }
    }

    public void InitizethisCard()
    {

    }

public String getthisDescription()
{
    String tem="";
    for(char a:this.name.toCharArray())
    {
        for (AbstractCard abstractCard : this.CardPool) {
            if(CardCrawlGame.languagePack.getCardStrings(abstractCard.cardID).NAME.contains(String.valueOf(a)))
            {

                if(abstractCard.baseDamage>0) {
                    this.damage= this.baseDamage = abstractCard.baseDamage;
                    this.target=CardTarget.ENEMY;
                }
                if(abstractCard.baseBlock>0)
                {
                    this.damage= this.baseBlock=abstractCard.baseBlock;
                    this.target=CardTarget.SELF;
                }
                if(this.upgraded)
                {
                    this.baseDamage+=3;
                    this.baseBlock+=3;
                }
                tem+=CardCrawlGame.languagePack.getCardStrings(abstractCard.cardID).DESCRIPTION;
            }
        }





    }
        return tem;
}


@Override
    public void applyPowers()
{   rawDescription=DESCRIPTION;
    rawDescription=this.getthisDescription()+" NL "+rawDescription;
    this.initializeDescription();
    super.applyPowers();


}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public String onSave() {
        return this.name;
    }

    @Override
    public void onLoad(String s) {
        this.name=s;
        this.Text=TextImageGenerator.getTextImage(s);
        Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);
        this.portrait = customRegion;
        this.rawDescription=DESCRIPTION;
        this.initializeDescription();
    }
    @Override
    public AbstractCard replaceWith(ArrayList<AbstractCard> currentRewardCards) {
        this.name=Words.get(AbstractDungeon.cardRandomRng.random(Words.size()-1));
        this.Text=TextImageGenerator.getTextImage( this.name);
        Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
        this.portrait = customRegion;
        this.rawDescription=DESCRIPTION;
        this.initializeDescription();
        return this;
    }

}




