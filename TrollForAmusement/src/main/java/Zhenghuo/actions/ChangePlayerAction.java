package Zhenghuo.actions;

import Zhenghuo.utils.Invoker;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Iterator;

import static Zhenghuo.modcore.ExampleMod.NowPlayer;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class ChangePlayerAction extends AbstractGameAction {
    public String Player;
    public ChangePlayerAction(String Player)
    {
        this.Player=Player;

    }
    @Override
    public void update() {
        NowPlayer=this.Player;
        ChangePlayer(this.Player);
        this.isDone=true;
        System.out.println("执行角色变成"+NowPlayer);
    }

    public static void ChangePlayerPool(String Player)
    {
        if(Player!=null){
            commonCardPool.clear();
            uncommonCardPool.clear();
            rareCardPool.clear();
            srcCommonCardPool.clear();
            srcUncommonCardPool.clear();
            srcRareCardPool.clear();
            ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
            switch (Player) {
                case "Defect":
                    CardLibrary.addBlueCards(tmpPool);
                    break;
                case "Ironclad":
                    CardLibrary.addRedCards(tmpPool);
                    break;
                case "TheSilent":
                    CardLibrary.addGreenCards(tmpPool);
                    break;
                case "Watcher":
                    CardLibrary.addPurpleCards(tmpPool);
                    break;
            }
            Iterator var4 = tmpPool.iterator();
            System.out.println("已将角色改为"+Player);
            AbstractCard c;
            while(var4.hasNext()) {
                c = (AbstractCard) var4.next();
                switch (c.rarity) {
                    case COMMON:
                        commonCardPool.addToTop(c);
                        srcCommonCardPool.addToTop(c.makeCopy());
                        break;
                    case UNCOMMON:
                        uncommonCardPool.addToTop(c);
                        srcUncommonCardPool.addToTop(c.makeCopy());
                        break;
                    case RARE:
                        rareCardPool.addToTop(c);
                        srcRareCardPool.addToTop(c.makeCopy());
                        break;
                    case CURSE:
                        curseCardPool.addToTop(c);
                        break;

                    default:
                        System.out.println("Unspecified rarity: " + c.rarity.name() + " when creating pools! AbstractDungeon: Line 827");

                }}
        }
    }
    public static void ChangePlayerSkin(String Player)
    {  if (player == null) {
        System.err.println("Error: player is null");
        return;
    }
        if (overlayMenu == null || overlayMenu.energyPanel == null) {
            return;
        }

        switch (Player) {

            case"Ironclad":
                Invoker.invoke(player, "loadAnimation","images/characters/ironclad/idle/skeleton.atlas", "images/characters/ironclad/idle/skeleton.json", 1.0F);
                player.state.setAnimation(0, "Idle", true);
                // player.chosenClass = AbstractPlayer.PlayerClass.IRONCLAD;
                Invoker.setField(overlayMenu.energyPanel,"gainEnergyImg",ImageMaster.RED_ORB_FLASH_VFX);

                break;
            case"TheSilent":
                Invoker.invoke(player, "loadAnimation", "images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0F);
                player.state.setAnimation(0, "Idle", true);
                // player.chosenClass = AbstractPlayer.PlayerClass.THE_SILENT;
                Invoker.setField(overlayMenu.energyPanel,"gainEnergyImg",ImageMaster.GREEN_ORB_FLASH_VFX);
                break;
            case "Defect":
                Invoker.invoke(player, "loadAnimation", "images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);
                player.state.setAnimation(0, "Idle", true);
                //  player.chosenClass = AbstractPlayer.PlayerClass.DEFECT;
                Invoker.setField(overlayMenu.energyPanel,"gainEnergyImg",ImageMaster.BLUE_ORB_FLASH_VFX);

                break;
            case"Watcher":
                Invoker.invoke(player, "loadAnimation", "images/characters/watcher/idle/skeleton.atlas", "images/characters/watcher/idle/skeleton.json", 1.0F);
                player.state.setAnimation(0, "Idle", true);
                //  player.chosenClass = AbstractPlayer.PlayerClass.WATCHER;
                Invoker.setField(overlayMenu.energyPanel,"gainEnergyImg",ImageMaster.PURPLE_ORB_FLASH_VFX);

                break;
        }
        ///public static void ChangePlayerSkin(String Player)
        //    { switch (Player) {
        //        case"Ironclad":
        //            Invoker.invoke(player, "loadAnimation","images/characters/ironclad/idle/skeleton.atlas", "images/characters/ironclad/idle/skeleton.json", 1.0F);
        //            player.state.setAnimation(0, "Idle", true);
        //    break;
        //        case"TheSilent":
        //            break;
        //        case "Defect":
        //            break;
        //        case"Watcher":
        //            break;
        //    }

    }
    public static void ChangePlayer(String Player)
    {
        if(Player!=null) {
            ChangePlayerSkin(Player);
            switch (Player) {
                case"Ironclad":
                    player.shoulderImg = ImageMaster.loadImage("images/characters/ironclad/shoulder2.png");
                    player.shoulder2Img = ImageMaster.loadImage("images/characters/ironclad/shoulder.png");
                    player.corpseImg = ImageMaster.loadImage("images/characters/ironclad/corpse.png");
                    Invoker.setField(topPanel, "title", Ironclad.NAMES[0]);
                    Invoker.setField(player, "energyOrb", new EnergyOrbRed());
                    ChangePlayerPool(Player);

                    break;
                case"TheSilent":
                    player.shoulderImg = ImageMaster.loadImage("images/characters/theSilent/shoulder2.png");
                    player.shoulder2Img = ImageMaster.loadImage("images/characters/theSilent/shoulder.png");
                    player.corpseImg = ImageMaster.loadImage("images/characters/theSilent/corpse.png");
                    Invoker.setField(topPanel, "title", TheSilent.NAMES[0]);
                    Invoker.setField(player, "energyOrb", new EnergyOrbGreen());
                    ChangePlayerPool(Player);
                    break;
                case "Defect":
                    player.shoulderImg = ImageMaster.loadImage("images/characters/defect/shoulder2.png");
                    player.shoulder2Img = ImageMaster.loadImage("images/characters/defect/shoulder.png");
                    player.corpseImg = ImageMaster.loadImage("images/characters/defect/corpse.png");

                    Invoker.setField(topPanel, "title", Defect.NAMES[0]);
                    Invoker.setField(player, "energyOrb", new EnergyOrbBlue());
                    ChangePlayerPool(Player);
                    break;
                case"Watcher":

                    player.shoulderImg = ImageMaster.loadImage("images/characters/watcher/shoulder2.png");
                    player.shoulder2Img = ImageMaster.loadImage("images/characters/watcher/shoulder.png");
                    player.corpseImg = ImageMaster.loadImage("images/characters/watcher/corpse.png");
                    Invoker.setField(topPanel, "title", Watcher.NAMES[0]);
                    Invoker.setField(player, "energyOrb", new EnergyOrbPurple());
                    ChangePlayerPool(Player);
                    break;
            }
        }


    };

    public static AbstractPlayer getPlayerClass() {
        if(NowPlayer==null){
            return player;
        }
        switch (NowPlayer)
        {
            case"Ironclad":
                return CardCrawlGame.characterManager.getCharacter(AbstractPlayer.PlayerClass.IRONCLAD);
            case"TheSilent":
                return CardCrawlGame.characterManager.getCharacter(AbstractPlayer.PlayerClass.THE_SILENT);
            case "Defect":
                return CardCrawlGame.characterManager.getCharacter(AbstractPlayer.PlayerClass.DEFECT);
            case"Watcher":
                return CardCrawlGame.characterManager.getCharacter(AbstractPlayer.PlayerClass.WATCHER);
            default:
                return player;
        }

    }

}


