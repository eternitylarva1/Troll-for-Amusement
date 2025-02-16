package Zhenghuo.card;


import Zhenghuo.helpers.ModHelper;
import Zhenghuo.modcore.CustomTags;
import Zhenghuo.utils.TextImageGenerator;
import basemod.AutoAdd;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;

import static Zhenghuo.player.Mycharacter.PlayerColorEnum.CharacterBlack;
@AutoAdd.Ignore
public class CardArgument extends CustomCard {

    public static final String ID = ModHelper.makePath("CardArgument");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "ZhenghuoResources/images/Character.png";
    private static final int COST = 0;
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CharacterBlack;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    public   AbstractCardModifier cardModifier ;

    public CardArgument(String NAME, String DESCRIPTION, AbstractCardModifier cardModifier) {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION+" NL zhenghuo:词条", TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 100;
        this.cardModifier=cardModifier;
        Texture customTexture = TextImageGenerator.getTextImage("词条");;
        this.tags.add(CustomTags.WordCard);
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
        this.portrait = customRegion;

    }
    public CardArgument()
    {
        this("",DESCRIPTION,null);
    }
    public CardArgument(AbstractCardModifier cardModifier )
    {
        this("",DESCRIPTION,cardModifier);
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

    }




