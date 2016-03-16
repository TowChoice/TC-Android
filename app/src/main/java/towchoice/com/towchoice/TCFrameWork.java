package towchoice.com.towchoice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import android.webkit.WebView;
/**
 * Created by atan on 3/15/16.
 */
public class TCFrameWork {
    private static Map<String, String> splitQuery(String url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.split("\\?")[1];

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }

        return query_pairs;
    }

    public void executeUrl(String url, WebView webview, MainActivity activity) {
        if( !url.startsWith("towchoice:") ) {
            System.out.format("unsupported url scheme <%s>\n", url);
            return;
        }
        Map<String, String> params = null;
        try {
            params = splitQuery(url);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if( params.get("api").equals("confirm") ) {
            String [] l_buttons = params.get("btns").split(",");
            System.out.format("confirm(%s, %s, %s, %s,%s)\n", params.get("message"),
                    params.get("callback"), params.get("title"), l_buttons[0], l_buttons[1]);
        }
        else if( params.get("api").equals("alert") ) {
            System.out.format("alert(%s, %s, %s, %s)\n", params.get("message"),
                    params.get("callback"), params.get("title"), params.get("btn"));
        }
        else if( params.get("api").equals("ready") ) {
            activity.ready();
        }
        else {
            System.out.format("unsupported API = %s\n", params.get("api"));
        }
    }
}
