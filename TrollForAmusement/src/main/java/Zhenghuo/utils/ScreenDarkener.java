package Zhenghuo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ScreenDarkener {
    private Texture blackTexture;
    private TextureRegion blackRegion;
    // 半透明黑色幕布的透明度，取值范围 0 - 1，值越大越暗
    private float alpha = 0.3f;

    public ScreenDarkener() {
        // 创建一个 1x1 的黑色 Pixmap
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();

        // 使用 Pixmap 创建黑色纹理
        blackTexture = new Texture(pixmap);
        pixmap.dispose(); // 释放 Pixmap 资源

        blackRegion = new TextureRegion(blackTexture);
    }

    public void render(SpriteBatch spriteBatch) {
        // 启用混合功能
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        boolean isdrawing=false;
        if (spriteBatch.isDrawing()) {
            spriteBatch.end();
            isdrawing=true;
        }
        // 开始绘制批次
        spriteBatch.begin();
        // 设置半透明颜色
        spriteBatch.setColor(0, 0, 0, alpha);
        // 绘制覆盖整个屏幕的黑色矩形
        spriteBatch.draw(blackRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // 结束绘制批次
        spriteBatch.end();

        // 禁用混合功能
        Gdx.gl.glDisable(GL20.GL_BLEND);
        if(isdrawing){   spriteBatch.begin();}
    }

    public void increaseDarkness() {
        // 每次增加 0.1 的透明度，但不超过 1
        alpha = Math.min(1f, alpha + 0.1f);
    }
    public void dispose() {
        blackTexture.dispose();
    }
}