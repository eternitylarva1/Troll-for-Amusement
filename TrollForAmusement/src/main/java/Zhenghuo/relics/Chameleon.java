package Zhenghuo.relics;



import Zhenghuo.helpers.ModHelper;
import Zhenghuo.utils.GOTUtils;
import Zhenghuo.utils.TextureUtils;
import basemod.abstracts.CustomRelic;
import basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame.ApplyScreenPostProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class Chameleon extends CustomRelic {
    public static final String ID = ModHelper.makePath("Chameleon");
    // 图片路径
    private static final String IMG_PATH = "ZhenghuoResources/images/relics/cultistMask.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;


    public Chameleon() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        GOTUtils.ModifyEnergyMaster(1);
    }

    public void onUnequip() {
        GOTUtils.ModifyEnergyMaster(-1);
    }

    public static class DisguiseHandCard {
        private static FrameBuffer bgBuffer = null;

        public static ShaderProgram chameleon_shader = null;

        public static boolean Disguising() {
            return false;
            //(GOTUtils.RoomAvailable() && AbstractDungeon.player.hasRelic(Chameleon.ID));
        }





        public static class SetupBgBuffer {
            @SpireInsertPatch(locator = Locator.class)
            public static void BeforeSpriteBatchBegin(CardCrawlGame _inst, SpriteBatch ___sb) {
                if (!DisguiseHandCard.Disguising()) {
                    if (DisguiseHandCard.bgBuffer != null)
                        DisguiseHandCard.bgBuffer.dispose();
                    return;
                }

                if (DisguiseHandCard.bgBuffer == null)
                    DisguiseHandCard.bgBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
                DisguiseHandCard.bgBuffer.begin();
                ___sb.setBlendFunction(770, 771);
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                Gdx.gl.glClear(17664);
            }

            private static class Locator extends SpireInsertLocator {
                public int[] Locate(CtBehavior ctBehavior) throws Exception {
                    Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "begin");
                    return LineFinder.findInOrder(ctBehavior, (Matcher)matcher);
                }
            }
        }

        @SpirePatch(clz = OverlayMenu.class, method = "render")
        public static class ProcessBgBuffer {
            @SpireInstrumentPatch
            public static ExprEditor SkipNaturalHandRendering() {
                return new ExprEditor() {
                    public void edit(MethodCall m) throws CannotCompileException {
                        if ("renderHand".equals(m.getMethodName()) || "renderTip".equals(m.getMethodName()))
                            m.replace("{if(!" + DisguiseHandCard.class.getName() + ".Disguising() ) {$_=$proceed($$);} }");
                    }
                };
            }

            @SpireInsertPatch(locator = PreLocator.class)
            public static void BeforeRenderHand(OverlayMenu _inst, SpriteBatch sb, AbstractPlayer ___player) {
                if (DisguiseHandCard.Disguising()) {
                    ___player.hand.stopGlowing();
                    FrameBuffer handBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
                    sb.end();
                    handBuffer.begin();
                    Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                    Gdx.gl.glClear(17664);
                    sb.begin();
                    ___player.renderHand(sb);
                    ___player.hand.renderTip(sb);
                    sb.end();
                    handBuffer.end();
                    sb.begin();
                    Texture bg = ApplyScreenPostProcessor.getFrameBufferTexture();
                    Texture hand = (Texture)handBuffer.getColorBufferTexture();
                    hand.bind(1);
                    bg.bind(0);
                    if (DisguiseHandCard.chameleon_shader == null)
                        DisguiseHandCard.chameleon_shader = TextureUtils.GetShader("ZhenghuoResources/shaders/chameleon/chameleonhand.fs");
                    ShaderProgram oldShader = sb.getShader();
                    sb.setShader(DisguiseHandCard.chameleon_shader);
                    DisguiseHandCard.chameleon_shader.setUniform2fv("u_resolution", new float[] { Gdx.graphics.getWidth(), Gdx.graphics.getHeight() }, 0, 2);
                    DisguiseHandCard.chameleon_shader.setUniformi("u_buffer", 1);
                    sb.draw(bg, 0.0F, 0.0F, bg.getWidth(), bg.getHeight(), 0, 0, bg.getWidth(), bg.getHeight(), false, true);
                    sb.setShader(oldShader);
                    handBuffer.dispose();
                }
            }

            private static class PreLocator extends SpireInsertLocator {
                public int[] Locate(CtBehavior ctBehavior) throws Exception {
                    Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "renderHand");
                    return LineFinder.findInOrder(ctBehavior, (Matcher)matcher);
                }
            }
        }
    }
}
