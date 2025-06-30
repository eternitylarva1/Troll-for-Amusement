package cn.candy.relic;

import cn.candy.powers.AfterImagePower1;
import cn.candy.powers.BiasPower1;
import cn.candy.powers.SplitPower1;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.powers.watcher.StudyPower;

public class random_power {

    public static AbstractPower random_power(int key, AbstractMonster owner) {

    switch (key) {
        case 0:
            return new TimeWarpPower(owner);
        case 1:
            return new MetallicizePower(owner,1);
        case 2:
            return new StrengthPower(owner,1);
        case 3:
            return new RegenerateMonsterPower(owner, 1);
        case 4:
            return new MalleablePower(owner, 1);
        case 5:
            return new DemonFormPower(owner,1);
        case 6:
            return new AfterImagePower1(owner, 1);
        case 7:
            if (AbstractDungeon.miscRng.randomBoolean()) {
                return new SharpHidePower(owner, 1);
            }

            return new DemonFormPower(owner,1);
        case 8:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new CuriosityPower(owner, 1);
            } else {
                if (AbstractDungeon.aiRng.random(5) <= 1) {
                return new AngerPower(owner, 1);}
                else
                {return new TimeWarpPower(owner);}
            }
        case 9:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new BarricadePower(owner);
            } else {
                return new HexPower(owner, 1);
            }
        case 10:
            if(AbstractDungeon.aiRng.random(15) == 0){
                return new AngryPower(owner,1);
            }else {
                return new FlightPower(owner, 1);
            }
        case 11:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new BiasPower1(owner, 1);
            } else {
                return new SplitPower1(owner);
            }
        case 12:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new BeatOfDeathPower(owner, 1);
            } else {
                return new DemonFormPower(owner,1);
            }

        case 13:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new ThornsPower(owner, 1);
            } else {
                return new CombustPower(owner,1,7);
            }
        case 14:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new DoubleDamagePower(owner, 1,true);
            } else {
                return new DuplicationPower(owner,1);
            }
        case 15:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new HelloPower(owner, 1);
            } else {
                return new CreativeAIPower(owner,1);
            }
        case 16:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new PenNibPower(owner, 1);
            } else {
                return new FlameBarrierPower(owner,1);
            }
        case 17:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new MasterRealityPower(owner);
            } else {
                return new DarkEmbracePower(owner,1);
            }
        case 18:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new FeelNoPainPower(owner,1);
            } else {
                return new FreeAttackPower(owner,1);
            }
        case 19:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new CorruptionPower(owner);
            } else {
                return new PainfulStabsPower(owner);
            }
        case 20:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new StudyPower(owner,1);
            } else {
                return new MayhemPower(owner,1);
            }
        case 21:
            if (AbstractDungeon.aiRng.random(1) == 0) {
                return new StudyPower(owner,1);
            } else {
                return new EndTurnDeathPower(owner);
            }
        default:
            return new FlightPower(owner, 1);
    }
}
}
