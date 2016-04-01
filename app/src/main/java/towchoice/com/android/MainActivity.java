package towchoice.com.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.view.View;
import android.webkit.WebViewClient;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import towchoice.com.android.R;

public class MainActivity extends AppCompatActivity {
    private WebView m_webview;
    private ImageView m_splashscreen;
    private ProgressDialog m_spinner;
    private TowChoiceWebViewClient m_webviewclient;
    private TCFrameWork m_framework;
    private MainActivity m_activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_webview = (WebView) findViewById(R.id.webview);
        m_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        m_splashscreen = (ImageView) findViewById(R.id.splashScreen);
        m_spinner = new ProgressDialog(this,R.style.NewDialog);
        m_spinner.setMessage("Loading...");
        m_spinner.show();
        m_webview.getSettings().setJavaScriptEnabled(true);
        m_webview.getSettings().setGeolocationEnabled(true);
        m_webview.getSettings().setDomStorageEnabled(true);
        m_webview.getSettings().setDatabaseEnabled(true);
        m_webviewclient = new TowChoiceWebViewClient();
        m_webview.setWebViewClient(m_webviewclient);
        m_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        m_framework = new TCFrameWork();
        m_webview.clearCache(true);
        m_webview.loadUrl("http://live.towchoice.com/provider/");
    }
    public void ready() {
        m_splashscreen.setVisibility(View.GONE);
        m_spinner.hide();
    }
    public class TowChoiceWebViewClient extends WebViewClient {
        public void onPageFinished(WebView view, String url) {
            System.out.format("Finished Loading <%s>\n", url);
            m_splashscreen.setVisibility(View.GONE);
            m_spinner.hide();
        }
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            System.out.format("Loading <%s> failed with error <%s>\n", request, error);
            super.onReceivedError(view, request, error);
        }
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if( url.startsWith("towchoice:") ) {
                m_framework.executeUrl(url, m_webview, m_activity);
                return true;
            }
            try {
                System.out.format("Loading <%s>\n", URLDecoder.decode( url, "UTF-8" ));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }

    }
}
