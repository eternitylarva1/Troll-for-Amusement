package Zhenghuo.actions;

import Zhenghuo.utils.Invoker;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;

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
    }

    public static void ChangePlayerPool(String Player)
    {
        if(Player!=null){
        switch (Player){
            case"Defect":
                commonCardPool.clear();
                uncommonCardPool.clear();
                rareCardPool.clear();
                srcCommonCardPool.clear();
                srcUncommonCardPool.clear();
                srcRareCardPool.clear();
                ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
                CardLibrary.addBlueCards(tmpPool);
                Iterator var4 = tmpPool.iterator();
                System.out.println("已将角色改为鸡煲");
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
    }}
    }
    public static void ChangePlayerSkin(String Player)
    { switch (Player) {
        case"Ironclad":
            Invoker.invoke(player, "loadAnimation","images/characters/ironclad/idle/skeleton.atlas", "images/characters/ironclad/idle/skeleton.json", 1.0F);
            player.state.setAnimation(0, "Idle", true);
    break;
        case"TheSilent":
            Invoker.invoke(player, "loadAnimation", "images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0F);
            player.state.setAnimation(0, "Idle", true);
            break;
        case "Defect":
            Invoker.invoke(player, "loadAnimation", "images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);
            player.state.setAnimation(0, "Idle", true);
            break;
        case"Watcher":
            Invoker.invoke(player, "loadAnimation", "images/characters/watcher/idle/skeleton.atlas", "images/characters/watcher/idle/skeleton.json", 1.0F);
            player.state.setAnimation(0, "Idle", true);
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
    {if(Player!=null) {
        ChangePlayerSkin(Player);
        switch (Player) {
            case"Ironclad":

                Invoker.setField(topPanel, "title", Ironclad.NAMES[0]);
                Invoker.setField(player, "energyOrb", new EnergyOrbRed());
                ChangePlayerPool(Player);
                break;
            case"TheSilent":

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

                Invoker.setField(topPanel, "title", Watcher.NAMES[0]);
                Invoker.setField(player, "energyOrb", new EnergyOrbPurple());
                ChangePlayerPool(Player);
                break;
        }
    }

    };

}


