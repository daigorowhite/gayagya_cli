package util;

import com.google.common.io.Files;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 設定系のクラス
 * 設定っぽかったら全部ここにいれちまえ（てきとー
 */
public class Setting {

    private Map<String,Object> settings;

    /**
     * ちゃんと動くかテストするためにメインを残しておく
     * うまく、yamlよめねーなーって人は、イロイロ変えながら、mainを実行してみてね
     * @param args
     */
    public static void main (String[] args){
        new Setting();
    }

    public Setting(){
        Yaml yaml = new Yaml();
        InputStream asStream = this.makeInputStream("/app.yaml");
        settings = (Map<String,Object>)yaml.load(asStream);
    }

    public <T> T getSetting(String key){
        return getSetting(key, null);
    }

    public <T> T getSetting(String key, T defaultObj){
        Object tmp = settings.get(key);
        return (tmp == null ? defaultObj: (T) tmp);
    }

    private InputStream makeInputStream(String filePath){
        return this.getClass().getResourceAsStream(filePath);
    }

}
