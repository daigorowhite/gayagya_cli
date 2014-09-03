package YajiYaji;

import java.awt.*;
import java.util.ArrayList;

/**
 * 野次に使われる文字のフォントなどの倉庫
 * ここに追記していけば、ランダムで選ばれる
 */
public class YajiYajiUtil {

    static final java.util.List<Color> COLOR_LIST = new ArrayList<Color>(){{
        add(Color.BLACK);
//        add(Color.RED);
//        add(Color.BLUE);
//        add(Color.GREEN);
    }};

    static final java.util.List<String> FONT_LIST = new ArrayList<String>(){{
        add("MS UI Gothic");
//        add("Arial");
//        add("Helvetica");
    }};

    static final java.util.List<Integer> FONT_STYLE_LIST = new ArrayList<Integer>(){{
        add(Font.PLAIN);
//        add(Font.BOLD);
    }};

    static java.util.List<Integer> FONT_SIZE_LIST = new ArrayList<Integer>(){{
        add(20);
    }};
}
