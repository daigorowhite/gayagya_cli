package GayaGaya;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by daigoro on 2014/08/03.
 */
public class GayaGayaUtil {

    static final int MAX_WORDS=10;


    static final java.util.List<Color> COLOR_LIST = new ArrayList<Color>(){{
        add(Color.BLACK);
        add(Color.RED);
        add(Color.BLUE);
        add(Color.GREEN);
    }};

    static final java.util.List<String> FONT_LIST = new ArrayList<String>(){{
        add("MS UI Gothic");
        add("Helvetica");
    }};

    static final java.util.List<Integer> FONT_STYLE_LIST = new ArrayList<Integer>(){{
        add(Font.PLAIN);
        add(Font.BOLD);
    }};

    static java.util.List<Integer> FONT_SIZE_LIST = new ArrayList<Integer>(){{
        add(48);
        add(60);
        add(72);
    }};
}
