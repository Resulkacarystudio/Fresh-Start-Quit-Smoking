package com.resulkacar.sigaraybrakyorum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class sigarayibirakma_ilkadim extends AppCompatActivity {
    Button hadibaslayalim_button;
    SharedPreferences sharedPreferences; // Sigara verilerini Telefon Hafızasına Kaydetmek İçin


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sigarayibirakma_ilkadim);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            hadibaslayalim_button = findViewById(R.id.hadibaslayalim_button);
            sharedPreferences = this.getSharedPreferences("com.resulkacar.sigaraybrakyorum", Context.MODE_PRIVATE); //Kullanıcı Verilerini Kaydetmek İçin



            //Kullanıcılanın Verileri Girmesi İçin Diğer Sayfaya Geçiyor.
            hadibaslayalim_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(sigarayibirakma_ilkadim.this,kullaniciverileri_page.class);
                    startActivity(intent);
                }
            });

            //Eğer Kullanıcı Daha Önceden Verileri Giriş Yaptıysa Direkt Olarak Anasayfaya Geçiyor
            boolean durum=sharedPreferences.getBoolean("calisti",false);
            if(durum== true)
            {
                Intent intent = new Intent(sigarayibirakma_ilkadim.this, sigaraBirak.class);
                startActivity(intent);
                finish();
            }



            return insets;
        });
    }
}