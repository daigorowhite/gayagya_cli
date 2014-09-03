package GayaGaya; /**
 * Created by daigoro on 2014/07/27.
 */

import java.applet.Applet;
import java.awt.*;
import java.util.*;
import java.util.List;

import static GayaGaya.GayaGayaUtil.MAX_WORDS;

public class GayagayaApplet extends Applet {
    static int displayWidth = 1024;//default
    static int displayHeight = 768;//default
    static int x = 0;
    static List<WordDto> words =
            new ArrayList<WordDto>();


    public void paint(Graphics g) {
        List<WordDto> removeList =
                new ArrayList<WordDto>();

        for(WordDto word : words){
            //処理
            word.x = word.x - 12;//TODO 固定値移動値

            //描写
            g.setFont(word.font);
            g.setColor(word.color);
            g.drawString(word.commentator +  "< " + word.mongon,word.x,word.y);
            if (word.x < -10000) {
                removeList.add(word);
            }
        }

        //不要になったオブジェクトを削除する
        for(WordDto word : removeList){
            words.remove(word);
        }
    }

    public static void main(String args[]) {
        GayagayaApplet ga = new GayagayaApplet();
        GayagayaFrame m = new GayagayaFrame("GayaGaya", ga);

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
            x -= 10;
            ga.repaint();//再描画
            try {
                Thread.sleep(50);//スレッドの待ち合わせ
            } catch (InterruptedException e) {//例外処理（メニューから参照）
            }
        }
    }

    public static WordDto makeWordDto(String user, String mongon) {
        Random a = new Random();
        WordDto w = new WordDto();
        w.color = GayaGayaUtil.COLOR_LIST.get((int)(
                GayaGayaUtil.COLOR_LIST.size() * a.nextDouble()
        ));
        Font f = new Font(GayaGayaUtil.FONT_LIST.get((int)(
                GayaGayaUtil.FONT_LIST.size() * a.nextDouble()))
                ,GayaGayaUtil.FONT_STYLE_LIST.get((int)(
                GayaGayaUtil.FONT_STYLE_LIST.size() * a.nextDouble()))
                ,GayaGayaUtil.FONT_SIZE_LIST.get((int)(
                GayaGayaUtil.FONT_SIZE_LIST.size() * a.nextDouble())));
        w.font = f;
        w.x = displayWidth;
        w.y = (int) (displayHeight * a.nextDouble());
        w.commentator = user;
        w.mongon = mongon;
        return w;
    }
}
