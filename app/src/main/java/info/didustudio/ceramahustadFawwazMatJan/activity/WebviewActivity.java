package info.didustudio.ceramahustadFawwazMatJan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;

public class WebviewActivity extends AppCompatActivity {

    private ImageView tombolKembali, tombolTigaTitik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView webView = (WebView) findViewById(R.id.webview);
        String alamatHtml = getIntent().getStringExtra(Constant.kodeWebview);
        if(alamatHtml.equals(Constant.kodeWebviewAsmaulHusna)){
            webView.loadUrl("file:///android_asset/html/asmaul.html");
            ((TextView) findViewById(R.id.textJudul)).setText("Asmaul Husna");
        }else{
            webView.loadUrl("file:///android_asset/html/nabirasul.html");
            ((TextView) findViewById(R.id.textJudul)).setText("Nabi Dan Rasul");
        }

        tombolKembali = (ImageView) findViewById(R.id.tombolKembali);
        tombolKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tombolTigaTitik = (ImageView) findViewById(R.id.tombolTigaTitik);
        tombolTigaTitik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilkanMenu(v);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void tampilkanMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_app, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        startActivity(new Intent(WebviewActivity.this, MenuActivity.class));
                        finish();
                        return true;
                    case R.id.menu_asmaul_husna:
                        Intent intent = new Intent(WebviewActivity.this, WebviewActivity.class);
                        intent.putExtra(Constant.kodeWebview, Constant.kodeWebviewAsmaulHusna);
                        startActivity(intent);
                        return true;
                    case R.id.menu_nabi:
                        Intent intents = new Intent(WebviewActivity.this, WebviewActivity.class);
                        intents.putExtra(Constant.kodeWebview, Constant.kodeWebviewNabiRasul);
                        startActivity(intents);
                        return true;
                    case R.id.menu_tasbih:
                        startActivity(new Intent(WebviewActivity.this, TasbihActivity.class));
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();
    }
}
