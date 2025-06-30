package cn.candy.powers;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DamagePlayerAction extends AbstractGameAction {
    private DamageInfo info;

    public DamagePlayerAction(DamageInfo info, AttackEffect effect) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {
        this.target = AbstractDungeon.player;
        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
        }

        this.isDone = true;
    }
}
