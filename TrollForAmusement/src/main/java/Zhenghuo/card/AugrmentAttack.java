package Zhenghuo.card;


import Zhenghuo.helpers.ModHelper;
import Zhenghuo.utils.TextImageGenerator;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CardAugments.CardAugmentsMod.applyWeightedCardMod;
import static CardAugments.CardAugmentsMod.rollRarity;
import static Zhenghuo.player.Mycharacter.PlayerColorEnum.CharacterBlack;
import static Zhenghuo.utils.CardArguments.Chimeraopened;


public class AugrmentAttack extends CustomCard {

    public static final String ID = ModHelper.makePath("AugrmentAttack");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "ZhenghuoResources/images/Character.png";
    private static final int COST = 2;
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CharacterBlack;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public AugrmentAttack() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
      this.damage=this.baseDamage=15;

        Texture customTexture = TextImageGenerator.getTextImage(NAME,this.type);;

// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
        TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
        customRegion.flip(false, true);
// Step 3: 设置卡牌的portrait属
        this.portrait = customRegion;

    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();


        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if(Chimeraopened()){
            applyWeightedCardMod(this, rollRarity(this.rarity), 0);

        }
    }

    }




