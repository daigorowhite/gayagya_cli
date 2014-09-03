package GayaGaya;

import java.awt.*;

/**
 * 発言の表示に関するDto
 */
public class WordDto {
    //発言された文言
    public String mongon = "";
    //発言者
    public String commentator;

    //    現在のポジション
    public int x = 0;
    public int y = 0;

    //     情報
    public Color color;
    public Font font;
}
