package cn.candy.relic;




import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class relic_link extends RewardItem {

    public relic_link(AbstractRelic relic)
    {
        super(relic);
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            return false;
        }

        if (this.relicLink != null) {
            this.relicLink.isDone = true;
            this.relicLink.ignoreReward = true;
        }


        return true;
    }

    @Override
    public void render(SpriteBatch sb) {
        if(this.isDone)
            return;
        super.render(sb);
        sb.setColor(Color.WHITE);
        //两个遗物的链子
        sb.draw(ImageMaster.RELIC_LINKED, this.hb.cX - 64.0F, this.y - 64.0F + 52.0F * Settings.scale, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
    }

    @Override
    public void update() {
        if(this.isDone)
            return;
        super.update();
    }
}
