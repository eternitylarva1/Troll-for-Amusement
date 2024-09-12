package Zhenghuo.card;


import Zhenghuo.helpers.ModHelper;
import Zhenghuo.utils.TextImageGenerator;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Zhenghuo.player.Mycharacter.PlayerColorEnum.CharacterBlack;

public class RandomCardWithWord extends CustomCard implements CustomSavable<String> {

    public static final String ID = ModHelper.makePath("RandomCardWithWord");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "ZhenghuoResources/images/Character.png";
    private static final int COST = 1;
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CharacterBlack;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private Texture Text;
    private boolean haschanged=false;
    public boolean isAugrment=false;
    static String[] RandomWordPool = new String[]{"打", "击", "神"};
    private String RandomWord="";

    public RandomCardWithWord(String NAME) {

        //  为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, "随机"+NAME+"牌", IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
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
        this.initializeDescription();
    }

    public RandomCardWithWord() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        this("?");
    }

    public static String getRandomWord(){
        return RandomWordPool[AbstractDungeon.cardRandomRng.random(RandomWordPool.length-1)];
    }


    public AbstractCard makeCopy()
    {
        return new RandomCardWithWord(this.name);
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
            this.upgradeMagicNumber(-25); // 将该卡牌的伤害提高3点。
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
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
        RandomWord=s;
        this.Text=TextImageGenerator.getTextImage(s+"?");
        this.name="随机"+RandomWord+"牌";
        Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);

// Step 3: 设置卡牌的portrait属
        this.portrait = customRegion;
        this.rawDescription=DESCRIPTION;
        this.initializeDescription();
    }


}




