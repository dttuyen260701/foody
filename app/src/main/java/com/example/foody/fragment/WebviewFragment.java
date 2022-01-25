package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.foody.R;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.utils.Constant_Values;

public class WebviewFragment extends Fragment {
    private ImageView img_Back_Webview_Frag;
    private WebView webView_Frag;
    private Listener_for_BackFragment listener_back;
    private TextView txt_Tittle_Webview_Fragment;
    //true la ToU, False la forgetPass
    private boolean for_ToU_or_Forget;

    public WebviewFragment(Listener_for_BackFragment listener_back, boolean for_ToU_or_Forget) {
        this.listener_back = listener_back;
        this.for_ToU_or_Forget = for_ToU_or_Forget;
    }

    //destroy web view
    @Override
    public void onPause() {
        super.onPause();

        webView_Frag.clearHistory();
        // NOTE: clears RAM cache, if you pass true, it will also clear the disk cache.
        // Probably not a great idea to pass true if you have other WebViews still alive.
        webView_Frag.clearCache(true);

        // Loading a blank page is optional, but will ensure that the WebView isn't doing anything when you destroy it.
        webView_Frag.loadUrl("about:blank");

        webView_Frag.onPause();
        webView_Frag.removeAllViews();
        webView_Frag.destroyDrawingCache();
        // NOTE: This pauses JavaScript execution for ALL WebViews,
        // do not use if you have other WebViews still alive.
        // If you create another WebView after calling this,
        // make sure to call mWebView.resumeTimers().
        webView_Frag.pauseTimers();
        // NOTE: This can occasionally cause a segfault below API 17 (4.2)
        webView_Frag.destroy();

        // Null out the reference so that you don't end up re-using it.
        webView_Frag = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        img_Back_Webview_Frag = (ImageView) view.findViewById(R.id.img_Back_Webview_Frag);
        webView_Frag = (WebView) view.findViewById(R.id.webView_Frag);
        txt_Tittle_Webview_Fragment = (TextView) view.findViewById(R.id.txt_Tittle_Webview_Fragment);

        img_Back_Webview_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_back.orderBill_Or_BackFragment();
            }
        });

        if(for_ToU_or_Forget){
            txt_Tittle_Webview_Fragment.setText("Team of Use");
        } else {
            txt_Tittle_Webview_Fragment.setText("Forget Pass");
        }

        String url = (for_ToU_or_Forget) ? Constant_Values.URL_TERM_OF_SERVICE
                : Constant_Values.URL_FORGET_PASS;
        webView_Frag.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView_Frag.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView_Frag.loadUrl(url);
    }
}
