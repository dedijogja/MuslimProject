package info.didustudio.ceramahustadFawwazMatJan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.KotakRelative;

public class TasbihActivity extends AppCompatActivity {

    KotakRelative tombolPenghitung;
    ImageView tombolReset, tombolVibrate, tombolTigaTitik;
    TextView penampilHitung;
    EditText maksimalHitung;
    private boolean statusVibrate = true;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbih);


        tombolPenghitung = (KotakRelative) findViewById(R.id.tombolHitungTasbih);
        tombolReset = (ImageView) findViewById(R.id.tombolReset);
        tombolVibrate = (ImageView) findViewById(R.id.tombolVibrate);
        maksimalHitung = (EditText) findViewById(R.id.maksimalHitung);
        penampilHitung = (TextView) findViewById(R.id.textPenampilJumlahTasbih);

        ImageView tombolKembali = (ImageView) findViewById(R.id.tombolKembali);
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


        setKlikListener();
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
                        startActivity(new Intent(TasbihActivity.this, MenuActivity.class));
                        finish();
                        return true;
                    case R.id.menu_asmaul_husna:
                        Intent intent = new Intent(TasbihActivity.this, WebviewActivity.class);
                        intent.putExtra(Constant.kodeWebview, Constant.kodeWebviewAsmaulHusna);
                        startActivity(intent);
                        return true;
                    case R.id.menu_nabi:
                        Intent intents = new Intent(TasbihActivity.this, WebviewActivity.class);
                        intents.putExtra(Constant.kodeWebview, Constant.kodeWebviewNabiRasul);
                        startActivity(intents);
                        return true;
                    case R.id.menu_tasbih:
                        startActivity(new Intent(TasbihActivity.this, TasbihActivity.class));
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();
    }


    private void setKlikListener(){
        tombolPenghitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maksimalHitung.getText().toString().equals("")){
                    maksimalHitung.setText("0");
                }
                int angkaSaatIni = Integer.parseInt(penampilHitung.getText().toString());
                int angkaMaksimal = Integer.parseInt(maksimalHitung.getText().toString());
                if(angkaSaatIni>=angkaMaksimal){
                    lakukanVibrate();
                    penampilHitung.setText(String.valueOf(angkaMaksimal));
                }else{
                    angkaSaatIni++;
                    penampilHitung.setText(String.valueOf(angkaSaatIni));
                }
            }
        });

        tombolReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penampilHitung.setText("0");
            }
        });

        tombolVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statusVibrate){
                    statusVibrate = false;
                    tombolVibrate.setImageResource(R.drawable.vibrateoff);
                }else{
                    statusVibrate = true;
                    tombolVibrate.setImageResource(R.drawable.vibrateon);
                }
            }
        });
    }

    private void lakukanVibrate(){
        if(statusVibrate){
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            v.vibrate(200);
        }
    }
}
