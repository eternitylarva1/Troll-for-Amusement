package Zhenghuo.patchs;

import Zhenghuo.relics.Chameleon;
import Zhenghuo.utils.TextureUtils;
import basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame.ApplyScreenPostProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class MonsterDisguisePatch {

    // 检测是否需要伪装
    public static boolean shouldDisguise() {
        // 这里可以根据具体条件判断是否需要伪装
        return
         AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Tieba:Chameleon");
    }

    @SpirePatch(clz = MonsterGroup.class, method = "render")
    public static class MonsterRenderPatch {

        private static FrameBuffer bgBuffer = null;
        private static FrameBuffer monsterBuffer = null;
        private static ShaderProgram disguiseShader = null;

        @SpireInstrumentPatch
        public static ExprEditor skipMonsterRendering() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if ("render".equals(m.getMethodName())) {
                        // 跳过正常的怪物渲染
                        m.replace("{if(!" + MonsterDisguisePatch.class.getName() + ".shouldDisguise()) {$_=$proceed($$);} }");
                    }
                }
            };
        }



        @SpireInsertPatch(rloc=4)
        public static void beforeRenderMonster(MonsterGroup _instance, SpriteBatch sb) {
            if (shouldDisguise()) {
                // 创建怪物缓冲区
                if (monsterBuffer == null) {
                    monsterBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
                }

                // 开始渲染到怪物缓冲区
                sb.end();
                monsterBuffer.begin();
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                Gdx.gl.glClear(17664);
                sb.begin();

                // 渲染怪物到缓冲区
                for (AbstractMonster m : _instance.monsters) {

                    m.render(sb);
                    if (_instance.hoveredMonster != null && !_instance.hoveredMonster.isDead && !_instance.hoveredMonster.escaped && AbstractDungeon.player.hoverEnemyWaitTimer < 0.0F && (!AbstractDungeon.isScreenUp || PeekButton.isPeeking)) {
                        _instance.hoveredMonster.renderTip(sb);
                    }
                }

                // 结束怪物缓冲区渲染
                sb.end();
                monsterBuffer.end();
                sb.begin();

                // 获取背景纹理
                Texture bg = ApplyScreenPostProcessor.getFrameBufferTexture();;

                // 获取怪物纹理
                Texture monsterTex = monsterBuffer.getColorBufferTexture();
                monsterTex.bind(1);
                bg.bind(0);

                // 初始化着色器
                if (disguiseShader== null) {
                    disguiseShader = TextureUtils.GetShader("ZhenghuoResources/shaders/chameleon/glasseffect.fs");
                }
                ShaderProgram oldShader = sb.getShader();
                sb.setShader(disguiseShader);
                disguiseShader.setUniformi("u_texture", 0); // 设置纹理采样器

                // 绘制怪物纹理以应用效果
                sb.draw(monsterTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

                /*

                disguiseShader.setUniform2fv("u_resolution", new float[]{Gdx.graphics.getWidth(), Gdx.graphics.getHeight()}, 0, 2);
                disguiseShader.setUniformi("u_buffer", 1);
                sb.draw(bg, 0, 0, bg.getWidth(), bg.getHeight(), 0, 0, bg.getWidth(), bg.getHeight(), false, true);
               */

                sb.setShader(oldShader);
            }
            else{
                if ( monsterBuffer != null) {
                    monsterBuffer.dispose();
                }
             }
        }
@SpireInsertPatch(rloc=4)
public static void Graypatch(MonsterGroup _instance, SpriteBatch sb) {
    if (shouldDisguise()) {
        // 设置混合模式
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // 渲染怪物到背景上
        for (AbstractMonster m : _instance.monsters) {
            m.render(sb);
            if (_instance.hoveredMonster != null && !_instance.hoveredMonster.isDead && !_instance.hoveredMonster.escaped && AbstractDungeon.player.hoverEnemyWaitTimer < 0.0F && (!AbstractDungeon.isScreenUp || PeekButton.isPeeking)) {
                _instance.hoveredMonster.renderTip(sb);
            }
        }

        // 恢复默认混合模式
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }
}

        private static class PreLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "render");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
