package YajiYaji;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 野次野次アプリの本体。
 * こいつのメインを起動してね
 *
 * このクラスにはたくさんの固定値がいる。
 * そのうち、整理しよう。うん。そうしよう。
 */
public class YajiYajiApplet extends Applet {
    static int displayWidth = 1024;//default
    static int displayHeight = 768;//default
    static int x = 0;
    static int oneTermTimeMs = 100;
    static int oneLineCharSize = 20;
    static int maxMongonSize = 100;
    //TODO このwordsは、スレッドセーフにしなきゃだめ。
    //めんどくさいので後回し。表示と作成が同時に起きるとエラーでるが無視。
    static List<YajiDto> words =
            new ArrayList<YajiDto>();
    static BufferedImage image;

    {
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream("/img/fukidashi.png"));
        } catch (IOException e) {
            //コレ酷い実装だｗ
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        List<YajiDto> removeList =
                new ArrayList<YajiDto>();

        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        for (YajiDto word : words) {
            word.existedTimeMs -= oneTermTimeMs;

            //描写
            g.drawImage(image, word.x, word.y, 450, 200, null);
            g.setFont(word.font);
            g.setColor(word.color);

            //ユーザ名の書き出し
            g.drawString(word.commentator, word.x + 200, word.y + 140);
            int mojiLeng = 0;
            int fontSize = word.font.getSize();

            //文言の書き出し
            while (mojiLeng * oneLineCharSize < word.mongonLength - oneLineCharSize) {
                int offset = mojiLeng * oneLineCharSize;
                g.drawString(word.mongon.substring(offset, offset + oneLineCharSize),
                        word.x + 20, word.y + 20 + offset + fontSize );
                mojiLeng += 1;
            }

            g.drawString(word.mongon.substring(mojiLeng * oneLineCharSize, word.mongonLength),
                    word.x + 20, word.y + 20 + mojiLeng * oneLineCharSize + fontSize );

            if (word.existedTimeMs < 0) {
                removeList.add(word);
            }
        }

        //不要になったオブジェクトを削除する
        for (YajiDto word : removeList) {
            words.remove(word);
        }
    }

    public static void main(String args[]) {
        YajiYajiApplet ga = new YajiYajiApplet();
        YajiYajiFrame m = new YajiYajiFrame("YajiTaji", ga);

        //Frame画面をフルスクリーンにする
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rect = env.getMaximumWindowBounds();
        m.setBounds(rect);
        displayWidth = rect.width;
        displayHeight = rect.height;
        //表示する
        m.setVisible(true);


        Thread tweetStreamThread = new Thread(new TwitterAccessor());
        tweetStreamThread.run();


        x = displayWidth;
        while (true) {
            ga.repaint();//再描画
            try {
                Thread.sleep(oneTermTimeMs);//スレッドの待ち合わせ
            } catch (InterruptedException e) {//例外処理（メニューから参照）
            }
        }
    }

    public static YajiDto makeWordDto(String user, String mongon) {
        Random a = new Random();
        YajiDto w = new YajiDto();
        w.color = YajiYajiUtil.COLOR_LIST.get((int) (
                YajiYajiUtil.COLOR_LIST.size() * a.nextDouble()
        ));
        Font f = new Font(YajiYajiUtil.FONT_LIST.get((int) (
                YajiYajiUtil.FONT_LIST.size() * a.nextDouble()))
                , YajiYajiUtil.FONT_STYLE_LIST.get((int) (
                YajiYajiUtil.FONT_STYLE_LIST.size() * a.nextDouble()))
                , YajiYajiUtil.FONT_SIZE_LIST.get((int) (
                YajiYajiUtil.FONT_SIZE_LIST.size() * a.nextDouble())));
        w.font = f;
        w.x = (int) ((displayWidth - 400) * a.nextDouble());
        w.y = (int) (displayHeight - 220) + (int)(50* a.nextDouble());
        w.existedTimeMs = 5000;
        w.commentator = user;
        //規定文字数以上は消す
        w.mongon = (mongon.length() < maxMongonSize ? mongon: mongon.substring(0,maxMongonSize));
        w.mongonLength = w.mongon.length();
        return w;
    }
}
