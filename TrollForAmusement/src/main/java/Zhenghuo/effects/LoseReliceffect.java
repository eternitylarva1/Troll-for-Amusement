package Zhenghuo.effects;



import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class LoseReliceffect
        extends AbstractGameEffect
{
    private AbstractRelic relic;

    public LoseReliceffect(AbstractRelic relic) { this.relic = relic; }



    public void update() {
        if(!AbstractDungeon.player.getStartingRelics().contains(relic.relicId)){
            AbstractDungeon.player.loseRelic(relic.relicId);
        }
        this.isDone = true;
    }

    public void render(SpriteBatch spriteBatch) {}

    public void dispose() {}
}
