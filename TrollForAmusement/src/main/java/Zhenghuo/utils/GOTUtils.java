package Zhenghuo.utils;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public interface GOTUtils {
    static boolean RoomChecker(AbstractRoom room, AbstractRoom.RoomPhase phase) {
        return (RoomAvailable() && room.phase == phase);
    }

    static boolean RoomChecker(AbstractRoom.RoomPhase phase) {
        return (RoomAvailable() && RoomChecker(AbstractDungeon.getCurrRoom(), phase));
    }

    static boolean RoomAvailable() {
        return (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null);
    }

    static boolean InCombat() {
        return (RoomAvailable() && RoomChecker(AbstractRoom.RoomPhase.COMBAT));
    }

    static String GameLang() {
        switch (Settings.language) {
            case ZHS:
                return "zhs";
            case ZHT:
                return "zht";
        }
        return "eng";
    }

    static String MakeID(String internalID) {
        return "gatheringthoughts:" + internalID;
    }

    static String RmPrefix(String ID) {
        return ID.replace("gatheringthoughts:", "");
    }

    static int Ascension() {
        return AbstractDungeon.ascensionLevel;
    }

    static void AddToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    static void AddToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    static void ModifyEnergyMaster(int amount) {
        AbstractPlayer p = AbstractDungeon.player;
        p.energy.energyMaster += amount;
        if (p.energy.energy != p.energy.energyMaster)
            p.energy.energy = p.energy.energyMaster;
    }

    static Texture RelicImage(String relicID) {
        String internalID = RmPrefix(relicID);
        String path = "GatheringThoughtsAssets/images/relics/%s.png";
        String imgPath = String.format(path, new Object[] { internalID });
        if (!Gdx.files.internal(imgPath).exists())
            imgPath = String.format(path, new Object[] { "RunicEye" });
        return ImageMaster.loadImage(imgPath);
    }

    static Texture RelicOutline(String relicID) {
        String internalID = RmPrefix(relicID);
        String path = "GatheringThoughtsAssets/images/relics/outline/%s.png";
        String imgPath = String.format(path, new Object[] { internalID });
        if (!Gdx.files.internal(imgPath).exists())
            imgPath = String.format(path, new Object[] { "RunicEye" });
        return ImageMaster.loadImage(imgPath);
    }
}
