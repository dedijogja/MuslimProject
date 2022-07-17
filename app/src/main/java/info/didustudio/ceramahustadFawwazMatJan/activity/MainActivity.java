package info.didustudio.ceramahustadFawwazMatJan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.adapter.TabAdapter;
import info.didustudio.ceramahustadFawwazMatJan.fragment.FragmentFavorit;
import info.didustudio.ceramahustadFawwazMatJan.fragment.FragmentListKonten;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentListKonten(), "CERAMAH");
        adapter.addFragment(new FragmentFavorit(), "FAVORIT");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ImageView tombolKembali, tigaTitik;
        tombolKembali = (ImageView) findViewById(R.id.tombolKembali);
        tigaTitik = (ImageView) findViewById(R.id.tombolTigaTitik);

        tombolKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tigaTitik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilkanMenu(v);
            }
        });
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
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        finish();
                        return true;
                    case R.id.menu_asmaul_husna:
                        Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                        intent.putExtra(Constant.kodeWebview, Constant.kodeWebviewAsmaulHusna);
                        startActivity(intent);
                        return true;
                    case R.id.menu_nabi:
                        Intent intents = new Intent(MainActivity.this, WebviewActivity.class);
                        intents.putExtra(Constant.kodeWebview, Constant.kodeWebviewNabiRasul);
                        startActivity(intents);
                        return true;
                    case R.id.menu_tasbih:
                        startActivity(new Intent(MainActivity.this, TasbihActivity.class));
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
