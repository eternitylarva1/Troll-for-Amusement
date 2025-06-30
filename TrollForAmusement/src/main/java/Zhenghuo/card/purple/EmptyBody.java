//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Zhenghuo.card.purple;

import Zhenghuo.utils.Invoker;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.Slot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.purple.EmptyMind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

import java.util.ArrayList;

import static Zhenghuo.utils.SpineRegionExtractor.getTextureFromSlot;
import static Zhenghuo.utils.TextureCache.cacheTexture;

public class EmptyBody extends AbstractCard {
    public static final String ID = "EmptyBody";
    private static final CardStrings cardStrings;

    public EmptyBody() {
        super("EmptyBody", cardStrings.NAME, "purple/skill/empty_body", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 7;
        this.tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                Bone bone;
                Slot slot ;
                TextureAtlas atlas;
                ArrayList<Slot> weaponSlots = new ArrayList<>();
                AbstractPlayer monster= AbstractDungeon.player;

                weaponSlots.clear();
                atlas= Invoker.getField(monster,"atlas");
                Skeleton skeleton = Invoker.getField(monster,"skeleton"); // 这里需要你根据游戏实际情况实现获取方法
// 获取指定插槽


                String[] slotsToHide = {"shirt","pants","Leg","Spine","pau"};
                for (String slotName : slotsToHide) {
                    slot = skeleton.findSlot(slotName);
                    bone = skeleton.findBone(slotName);

                    if (slot != null) {
                        weaponSlots.add(slot);
                    }
                    else if(bone!=null){
                        for (Slot slot1 : skeleton.getSlots()) {
                            if (slot1.getBone()==bone) {
                                weaponSlots.add(slot1);
                            }
                        }
                    }


                }
                for (Slot slot2 : skeleton.getSlots()) {
                    if (slot2 != null && slot2.getData().getName().contains("weapon")|| slot2.getData().getName().contains("dagger")|| slot2.getData().getName().contains("shield")|| slot2.getData().getName().contains("spear")) {
                        if(!weaponSlots.contains(slot2)) {
                            weaponSlots.add(slot2);
                        }
                    }

                }
                for (Slot slots : weaponSlots) {
                    Color currentColor = slots.getColor();

                    Texture originalTexture = getTextureFromSlot(slots,atlas);
                    String textureKey =slots.getData().getName()+"_"+slots.getData().getIndex();
                    cacheTexture(textureKey, originalTexture);
                    currentColor.set(currentColor.r, currentColor.g, currentColor.b, 0f);
                    /*AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new Customweapon(getCachedTexture(textureKey))));
                     */}




                isDone=true;
            }
        });
    }

    public AbstractCard makeCopy() {
        return new EmptyBody();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("EmptyBody");
    }
}
