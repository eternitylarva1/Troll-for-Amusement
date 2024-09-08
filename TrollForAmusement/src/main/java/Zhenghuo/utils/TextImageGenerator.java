package Zhenghuo.utils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextImageGenerator {
    private static BufferedImage createTextImage(String text, int width, int height) {
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取Graphics2D对象
        Graphics2D g2d = image.createGraphics();

        // 设置背景色和填充
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 设置文本颜色、字体
        g2d.setColor(Color.BLACK);

        g2d.setFont(new Font("Serif", Font.BOLD, 240/(text.length()+1)));

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


        private static Texture convertToTexture(BufferedImage image) {
            int width = image.getWidth();
            int height = image.getHeight();
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    int r = (pixel >> 16) & 0xff;
                    int g = (pixel >> 8) & 0xff;
                    int b = pixel & 0xff;
                    int a = (pixel >> 24) & 0xff;
                    pixmap.drawPixel(x, height - y - 1, (a << 24) | (b << 16) | (g << 8) | r);
                }
            }

            Texture texture = new Texture(pixmap);
            pixmap.dispose();
            return texture;
        }
    public static Texture getTextImage(String Name)
    {
        return convertToTexture(createTextImage(Name,250,190));
    }
}