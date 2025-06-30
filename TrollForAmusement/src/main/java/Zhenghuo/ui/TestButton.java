package Zhenghuo.ui;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class TestButton extends TopPanelItem {
    private static final Texture IMG = new Texture("ZhenghuoResources/images/relics/cultistMask.png");
    public static final String ID = "Tieba:TestButton";

    public TestButton() {
        super(IMG, ID);
    }

    @Override
    protected void onClick() {

        // your onclick code
    }
    private void handleRightClick() {
        // 右键点击的具体处理逻辑
        System.out.println("Right Clicked");
    }
    @Override
    public void update() {
        super.update();
        if (this.hitbox.hovered && InputHelper.justClickedRight && this.isClickable()) {
            this.onRightClick();
        }
        if (!Settings.hideTopBar) {
            if (this.hitbox.hovered) {
                TipHelper.renderGenericTip((float) InputHelper.mX - 140.0F * Settings.scale, Settings.HEIGHT - 120.0F * Settings.scale, "左键上升变量，右键下降变量", "当前变量为");
            } else{

            }
        }

    }

    private void onRightClick() {

    }
}