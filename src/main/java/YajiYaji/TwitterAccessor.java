package YajiYaji;

import com.google.common.collect.Lists;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import util.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ツイッターに接続する用のクラス
 * ここで受信したワードを表示する
 */
public class TwitterAccessor implements Runnable {

    // 認証キーを設定
    static ConfigurationBuilder builder = new ConfigurationBuilder();
    static Setting setting= new Setting();

    static {
        builder.setOAuthConsumerKey(setting.<String>getSetting("OAuthConsumerKey"));
        builder.setOAuthConsumerSecret(setting.<String>getSetting("OAuthConsumerSecret"));
        builder.setOAuthAccessToken(setting.<String>getSetting("OAuthAccessToken"));
        builder.setOAuthAccessTokenSecret(setting.<String>getSetting("OAuthAccessTokenSecret"));
    }

    @Override
    public void run() {
        // Configurationを作る
        Configuration conf = builder.build();
        // TwitterStreamのインスタンス作成
        TwitterStream twitterStream = new TwitterStreamFactory(conf).getInstance();
        // Listenerを登録
        twitterStream.addListener(new Listener());
        //フィルターするキーを書く
        List<String> YajiList = setting.<List<String>>getSetting("YajiWords",Arrays.asList("#yajiyaji"));
        String[] tracks = YajiList.toArray(new String[0]);
        //TODO ロガーも後回し
        System.out.println("野次リスト = "+YajiList);
        FilterQuery filter = new FilterQuery();
        filter.track(tracks);

        //実行(ﾟдﾟ)！
        twitterStream.filter(filter);
    }


    /**
     * Tweetを出力するだけのListener
     */
    static class Listener extends StatusAdapter {
        // Tweetを受け取るたびにこのメソッドが呼び出される
        public void onStatus(Status status) {
                System.out.println(status.getUser().getScreenName() + " > " + status.getText());
                YajiYajiApplet.words.add(YajiYajiApplet.makeWordDto(status.getUser().getScreenName(), status.getText()));
        }
    }
}
