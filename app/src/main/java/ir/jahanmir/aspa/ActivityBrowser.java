package ir.jahanmir.aspa;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmir.aspa.classes.DialogClass;
import ir.jahanmir.aspa.classes.Logger;

public class ActivityBrowser extends AppCompatActivity {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    DialogClass dlg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);
        dlg = new DialogClass();

        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        String url = intent.getStringExtra("PAY_URL");
        String bankName = intent.getStringExtra("BankName");
        TextView bankTitle = (TextView) findViewById(R.id.txtPageTitle);
        bankTitle.setText(String.format(getString(R.string.bank_portal), bankName));
        Logger.d("FragmentBrowser : url is " + url);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setWebViewClient(new ActivityBrowser.MyWebViewClient());



//        Intent intentweb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        intentweb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intentweb.setPackage("com.android.chrome");
//        try {
//            context.startActivity(intentweb);
//            Intent intent0 = new Intent(this, ActivityShowCurrentService.class);
//            startActivity(intent0);
//            finish();
//
//            AsyncTask<String,Boolean,Boolean> asyncTask = new AsyncTask<String, Boolean, Boolean>() {
//                @Override
//                protected Boolean doInBackground(String... strings) {
//                    return null;
//                }
//
//            };
////            new Handler()
//        } catch (ActivityNotFoundException ex) {
//            // Chrome browser presumably not installed so allow user to choose instead
//            intentweb.setPackage(null);
//            context.startActivity(intentweb);
//        }


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }




    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(this, ActivityShowCurrentService.class);
        startActivity(intent);
        finish();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //dlg.showDialogLoadingEnableCancelable(G.currentActivity);
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBrowser.this);
            builder.setMessage("لطفا برنامه خود را به روز رسانی کنید");
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
