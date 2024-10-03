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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

import static Zhenghuo.player.Mycharacter.PlayerColorEnum.CharacterBlack;
import static Zhenghuo.utils.CardArguments.RewardPatch.Words;
@AutoAdd.Ignore
public class CharacterCard extends CustomCard implements CustomSavable<String>, SpawnModificationCard {

    public static final String ID = ModHelper.makePath("CharacterCard");
    public ArrayList<AbstractCard> sutureCards=new ArrayList<>();
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
public static ArrayList<AbstractCard> CardPool=new ArrayList<>();

    public CharacterCard(String NAME,String DESCRIPTION) {

        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 0;
        this.baseBlock = this.block = 0;
        this.baseMagicNumber = this.magicNumber = 0;
        this.exhaust=true;
applyPowers();

// Step 3: 设置卡牌的portrait属

        InitizethisCard();
        initializeSutureCard();


        this.rawDescription = this.getDescription();
        this.initializeDescription();


    }
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();

        if (this.sutureCards.size() > 0) {
            Iterator var2 = this.sutureCards.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                c.triggerWhenDrawn();
                c.initializeDescription();
            }
        }

    }

    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        if (this.sutureCards.size() > 0) {
            Iterator var2 = this.sutureCards.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                c.triggerOnEndOfPlayerTurn();
                c.initializeDescription();
            }
        }

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
    public void upgrade() {
        if (!this.upgraded && this.sutureCards.size() > 0) {
            this.upgradeName();
            Iterator var2 = this.sutureCards.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                c.upgrade();
            }

            this.rawDescription = this.getDescription();
            this.initializeDescription();
        }

    }
    private void initializeSutureCard() {
        Iterator var2 = this.sutureCards.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type == CardType.POWER) {
                this.exhaust = true;
            }

            if (c.exhaust) {
                this.exhaust = true;
            }

            if (c.isEthereal) {
                this.isEthereal = true;
            }

            if (c.isInnate) {
                this.isInnate = true;
            }

            if (c.returnToHand) {
                this.returnToHand = true;
            }

            if (c.retain) {
                this.retain = true;
            }

            if (c.selfRetain) {
                this.selfRetain = true;
            }

            if (c.shuffleBackIntoDrawPile) {
                this.shuffleBackIntoDrawPile = true;
            }


        }

        var2 = this.sutureCards.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.target == CardTarget.SELF_AND_ENEMY) {
                this.target = CardTarget.SELF_AND_ENEMY;
                break;
            }

            if (c.target == CardTarget.ENEMY) {
                this.target = CardTarget.ENEMY;
                break;
            }
        }

        ///ReflectionHacks.setPrivate(this, AbstractCard.class, "renderColor", new Color(0.8F, 0.0F, 0.0F, 1.0F));
    }
    private void getTopCardStat() {
        if(!sutureCards.isEmpty()) {
            AbstractCard topCard = sutureCards.get(0);
            //this.portrait = topCard.portrait;
            this.rarity = topCard.rarity;
            this.type = topCard.type;
            //this.color = topCard.color;
            this.Text=TextImageGenerator.getTextImage(this.name,type);
            Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
            TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());
            int attack=0;
            int skill=0;
            customRegion.flip(false, true);
            this.portrait = customRegion;
            AbstractCard card;
            for (Iterator<AbstractCard> var1 = this.sutureCards.iterator(); var1.hasNext(); this.baseBlock += Integer.max(0, card.baseBlock)) {
                card = (AbstractCard) var1.next();
                this.cost += Integer.max(0, card.cost-1);
                this.baseDamage += Integer.max(0, card.baseDamage);
                if(card.baseDamage>0){
                    attack++;
                }
                if(card.baseBlock>0){
                    skill++;
                }
                if(card.baseMagicNumber>0){
                    this.baseMagicNumber=card.baseMagicNumber;
                }
                System.out.println("已经将卡牌增加"+card.baseDamage+"来源"+card.name);
            }

                this.baseDamage = attack>0?this.baseDamage/attack:this.baseDamage;
                System.out.println("基础伤害"+this.damage);
                this.baseBlock = skill>0? this.baseBlock/skill:this.baseBlock;
                System.out.println("基础格挡"+this.block);
                this.magicNumber=this.baseMagicNumber;
                this.damage=this.baseDamage;
                this.block=this.baseBlock;

            this.costForTurn = this.cost;
            initializeDescription();
        }
        else{
            this.Text=TextImageGenerator.getTextImage(this.name,type);
            Texture customTexture = Text;
// Step 2: 将Texture转换为TextureAtlas.AtlasRegion
            TextureAtlas.AtlasRegion customRegion = new TextureAtlas.AtlasRegion(customTexture, 0, 0, customTexture.getWidth(), customTexture.getHeight());

            customRegion.flip(false, true);
            this.portrait = customRegion;
        }
    }
    public void InitizethisCard()
    {
        sutureCards.clear();
        initializeSutureCard();
        for(char a:this.name.toCharArray())
        {
            for (AbstractCard abstractCard : CardPool) {
                if(CardCrawlGame.languagePack.getCardStrings(abstractCard.cardID).NAME.contains(String.valueOf(a)))
                {

                    sutureCards.add(abstractCard);
                }}}
        getTopCardStat();

    }
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (this.sutureCards.size() > 0) {
            Iterator var4 = this.sutureCards.iterator();

            while(var4.hasNext()) {
                AbstractCard card = (AbstractCard)var4.next();
                card.onPlayCard(c, m);
                card.initializeDescription();
            }
        }

    }


    private String getDescription() {
        Iterator var2 = this.sutureCards.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            c.initializeDescription();
        }

        String des = "";
        int max = this.sutureCards.size();

        for(int i = 0; i < max; ++i) {
            des = des+ ((AbstractCard)this.sutureCards.get(i)).rawDescription + " ";
        }
        return des+"NL 消耗 ";
    }




    public void applyPowers() {
        super.applyPowers();
        if (this.sutureCards.size() > 0) {
            Iterator var2 = this.sutureCards.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                c.applyPowers();
                c.initializeDescription();
            }
        }
        this.rawDescription=getDescription();
        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p == null) {
            p = AbstractDungeon.player;
        }

        if (m == null) {
            m = AbstractDungeon.getRandomMonster();
        }

        if (this.sutureCards.size() > 0 && m != null && !m.isDeadOrEscaped() && !m.isDead) {
            Iterator var4 = this.sutureCards.iterator();

            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                c.damage=this.damage;
                c.block=this.block;
                c.use(p, m);
            }
        }

    }


    @Override
    public String onSave() {

        return this.name;
    }

    @Override
    public void onLoad(String s) {
        this.name=s;
        InitizethisCard();
        initializeSutureCard();
        this.rawDescription = this.getDescription();
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
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (this.sutureCards.size() > 0) {
            Iterator var2 = this.sutureCards.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                c.triggerOnGlowCheck();
                if (c.glowColor != AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy()) {
                    this.glowColor = c.glowColor;
                    break;
                }
            }
        }

    }


}




