package com.resulkacar.sigaraybrakyorum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class kullaniciverileri_page extends AppCompatActivity {
    private LottieAnimationView animationView;
    Button devamedelim_button;
    EditText gunlukicilensigara_ED,sigarapaketadeti_ED,kacseneictiniz_ED,sigarapaketfiyati_ED;
    SharedPreferences sharedPreferences; // Sigara verilerini Telefon Hafızasına Kaydetmek İçin




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kullaniciverileri_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            animationView = findViewById(R.id.lottieAnimation);
            devamedelim_button = findViewById(R.id.devamedelim_button);
            gunlukicilensigara_ED = findViewById(R.id.KVgunlukicilensigara_Edittext);
            sigarapaketadeti_ED = findViewById(R.id.KVpakettekisigarasayisi_Edittext);
            kacseneictiniz_ED = findViewById(R.id.KVsigaraicilenyil_Edittext);
            sigarapaketfiyati_ED = findViewById(R.id.KVsigarapaketiFiyat_Edittext);
            sharedPreferences = this.getSharedPreferences("com.resulkacar.sigaraybrakyorum", Context.MODE_PRIVATE); //Kullanıcı Verilerini Kaydetmek İçin


            // Animasyonu başlat
            animationView.playAnimation();

            //Kullanıcı Tarih Ve Zamanı Girmesi İçin Diğer Sayfaya Aktarılıyor.
            devamedelim_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   kullaniciVerileriKaydet();
                }
            });

            return insets;
        });
    }



    //Kullanıcının Verileri SharedPrefances İle Kaydediliyor.
    private  void kullaniciVerileriKaydet()
    {
        if(gunlukicilensigara_ED.getText().toString().equals("") ||  sigarapaketadeti_ED.getText().toString().equals("") || kacseneictiniz_ED.getText().toString().equals("") || sigarapaketfiyati_ED.getText().toString().equals(""))
        {
            new AlertDialog.Builder(kullaniciverileri_page.this)  // Eğer bu kod bir Activity içerisinde ise "this", değilse uygun context kullanılmalı.
                    .setTitle("Bilgi")
                    .setMessage("Lütfen Tüm Alanları Doldurunuz.")
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // "Tamam" butonuna tıklandığında yapılacak işlemler buraya eklenebilir.
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)  // İkon ayarlaması isteğe bağlıdır.
                    .show();
        }
        else
        {
            int gunlukicilensigara = Integer.parseInt(gunlukicilensigara_ED.getText().toString());
            int sigarapaketiadeti = Integer.parseInt(sigarapaketadeti_ED.getText().toString());
            int kacseneictiniz = Integer.parseInt(kacseneictiniz_ED.getText().toString());
            int sigarapaketifiyati = Integer.parseInt(sigarapaketfiyati_ED.getText().toString());

            gunlukicilensigara = Integer.parseInt(gunlukicilensigara_ED.getText().toString());
            sharedPreferences.edit().putInt("gunlukicilen", gunlukicilensigara).apply();

            sigarapaketiadeti = Integer.parseInt((sigarapaketadeti_ED.getText().toString()));
            sharedPreferences.edit().putInt("paketfiyat", sigarapaketiadeti).apply();

            kacseneictiniz = Integer.parseInt(kacseneictiniz_ED.getText().toString());
            sharedPreferences.edit().putInt("sigarayil", kacseneictiniz).apply();

            sigarapaketifiyati = Integer.parseInt(sigarapaketfiyati_ED.getText().toString());
            sharedPreferences.edit().putInt("paketsigara", sigarapaketifiyati).apply();
            Intent intent = new Intent(kullaniciverileri_page.this,kullanicibilgileri_son.class);
            startActivity(intent);
        }
    }



}