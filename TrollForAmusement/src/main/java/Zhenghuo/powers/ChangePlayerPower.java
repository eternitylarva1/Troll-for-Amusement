package Zhenghuo.powers;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import Zhenghuo.actions.ChangePlayerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ChangePlayerPower extends AbstractPower {
    public static final String POWER_ID = "EndTurnDeath";
    private static final PowerStrings powerStrings;
    public final String descriptions= Settings.language == Settings.GameLanguage.ZHS?"回合后，你将永久变为故障机器人":"Turns Later,you will become Defect";
private String player;
    public ChangePlayerPower(AbstractCreature owner,int Turn,String player) {
        this.name =Settings.language == Settings.GameLanguage.ZHS? "变为机器人":"Become Defect";
        this.ID = "EndTurnDeath";
        this.owner = owner;
        this.player=player;
        this.amount = Turn;
        this.updateDescription();
        this.loadRegion("end_turn_death");
    }

    public void updateDescription() {

        ///this.description = powerStrings.DESCRIPTIONS[0];
        this.description =this.amount+descriptions;
    }

    public void atStartOfTurnPostDraw()  {

        if(this.amount==1) {
            this.flash();
            this.addToBot(new ChangePlayerAction(this.player));
        this.addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    else{
            this.amount--;

        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EndTurnDeath");
    }
}
