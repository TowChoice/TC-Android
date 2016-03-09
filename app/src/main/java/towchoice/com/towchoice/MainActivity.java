package towchoice.com.towchoice;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.view.Window;
import android.view.View;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView m_webview;
    private ImageView m_splashscreen;
    private ProgressDialog m_spinner;
    private TowChoiceWebViewClient m_webviewclient;

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
        m_webview.getSettings().setGeolocationEnabled(true);
        m_webview.getSettings().setDomStorageEnabled(true);
        m_webview.getSettings().setDatabaseEnabled(true);
        m_webview.getSettings().setJavaScriptEnabled(true);
        m_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        m_webviewclient = new TowChoiceWebViewClient();
        m_webview.setWebViewClient(m_webviewclient);
        m_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        m_webview.clearCache(true);
        m_webview.loadUrl("http://live.towchoice.com/provider/");
    }

    public class TowChoiceWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            System.out.format("Finished Loading <%s>\n", url);
            m_splashscreen.setVisibility(View.GONE);
            m_spinner.hide();
        }
    }
}
