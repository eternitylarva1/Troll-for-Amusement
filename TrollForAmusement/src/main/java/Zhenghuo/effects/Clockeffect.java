package Zhenghuo.effects;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.blue.Defragment;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class Clockeffect
        extends AbstractGameEffect
{


    public Clockeffect(float duration) { this.duration=duration ;}



    public void update() {

        if (this.duration <= 0.0F) {
            if(AbstractDungeon.screen==AbstractDungeon.CurrentScreen.CARD_REWARD) {
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player,AbstractDungeon.player,99,true));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
                System.out.println("wrongAnswer");
            }
            System.out.println("close");

            this.isDone = true;}
        else {
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        }




    public void render(SpriteBatch spriteBatch) {}

    public void dispose() {}
}
