package Zhenghuo.utils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
public class CardTextureGenerator {


    private static BufferedImage textureToBufferedImage(Texture texture) {
        texture.getTextureData().prepare(); // 让纹理数据准备好
        Pixmap pixmap = texture.getTextureData().consumePixmap(); // 获取 Pixmap

        int width = pixmap.getWidth();
        int height = pixmap.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        // 将 Pixmap 数据复制到 BufferedImage
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixmap.getPixel(x, y);
                int alpha = (pixel & 0x000000ff);
                int blue = (pixel >> 8) & 0x000000ff;
                int green = (pixel >> 16) & 0x000000ff;
                int red = (pixel >> 24) & 0x000000ff;

                int index = (y * width + x) * 4;
                pixels[index] = (byte) red;
                pixels[index + 1] = (byte) green;
                pixels[index + 2] = (byte) blue;
                pixels[index + 3] = (byte) alpha;
            }
        }

        pixmap.dispose(); // 清理 Pixmap 内存
        return image;
    }

    public static BufferedImage createTextImage(String text, int width, int height, AbstractCard.CardType cardType) {
        Texture baseTexture;
        BufferedImage baseImage;

        switch (cardType) {
            case ATTACK:
                baseTexture = new Texture("ZhenghuoResources/images/baseattack.png");
                break;
            case SKILL:
                baseTexture = new Texture("ZhenghuoResources/images/baseskill.png");
                break;
            case POWER:
                baseTexture = new Texture("ZhenghuoResources/images/basePower.png");
                break;
            default:
                baseTexture = new Texture("ZhenghuoResources/images/baseskill.png");
        }

        // 转换 Texture 为 BufferedImage
        baseImage = textureToBufferedImage(baseTexture);

        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // 设置背景色和填充
        g2d.setColor(Color.WHITE);
        //g2d.fillRect(0, 0, width, height);

        // 绘制 baseTexture
        g2d.drawImage(baseImage, 0, 0, width, height, null);

        // 设置文本颜色、字体
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.BOLD, 240 / (text.length() + 1)));

        // 获取FontMetrics来计算文本的宽度和高度
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        // 计算文本位置，使其居中
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fm.getAscent();

        // 绘制文本
        g2d.drawString(text, x, y);

        // 释放Graphics2D对象
        g2d.dispose();

        // 返回图像对象
        return image;
    }

}
