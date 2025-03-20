package com.resulkacar.sigaraybrakyorum;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class sigaraBirak extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    InterstitialAd interstitialAd1;//Reklam İçin

    double progressStatus = 0.0 // Progressbarın Yüzdesi
            , toplamzaman // Geçen Zaman
            , giden, yuzdePerSecond;
    double totalSeconds = 60 * 60; // Toplam saniye sayısı (1 gün, 24 saat)
    public double[] currentSecond = {toplamzaman}; // Kullanıcının şu an kaçıncı saniyede olduğunu buraya girin
    int hours, minutes, seconds, newPercentage = 100;
    int gecen_yil, gecen_ay, gecen_gun, gun, ay, yil, buzamanakadar_sigara, buzamanakadar_para;
    int gecenSaniye, gecenDakika, gecenSaat, gecenGun, gecenAy, gecenYil, seekbaralpha = 100;

    boolean seekbardurum = true;
    public float icilmeyen_sigara_hesap, icilmeyen_sigara = 0, tasarruf_edilen = 0, tasarruf_edilen_hesap, kazanilan_hayat_hesap, kazanilan_hayat = 0, buzamanakadar_hayat, kaybedilen_hayat_hesap;
    TextView gun_txt, buzamanakadar_sigara_txt, progress_txt, buzamanakadar_zaman_txt, buzamanakadar_para_txt, gecenYil_txt, gecenAy_txt, gecenGun_txt;
    TextView icilmeyen_sigara_txt, tasarruf_edilen_txt, kazanilan_hayat_txt, gdakika, gsaat, gsaniye;
    String durum = "1 SAAT";
    RewardedAd mrewardedAd;
    AdView banner;
    SeekBar seekBar;
    ProgressBar progressBar1;
    Button listbutton;
    SharedPreferences sharedPreferences;
    Handler handler = new Handler();
    BottomNavigationView bottomNavigationView;
    ConstraintLayout frameLayout;
    public static String kazanilanhayatF = "", tasarrufedilenF = "", icilmeyensigaraF = "";


    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigara_birak);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
        }


        sharedPreferences = this.getSharedPreferences("com.resulkacar.sigaraybrakyorum", Context.MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        gsaat = findViewById(R.id.gsaat);
        gdakika = findViewById(R.id.gdakika);
        gsaniye = findViewById(R.id.gsaniye);
        progressBar1 = findViewById(R.id.pgbar);
        progress_txt = findViewById(R.id.progress_txt);
        buzamanakadar_para_txt = findViewById(R.id.buzamanakadar_para);
        buzamanakadar_sigara_txt = findViewById(R.id.buzamanakadar_sigara);
        buzamanakadar_zaman_txt = findViewById(R.id.buzamanakadar_zaman);
        tasarruf_edilen_txt = findViewById(R.id.tasarruf_edilen_txt);
        icilmeyen_sigara_txt = findViewById(R.id.icilmeyen_sigara_txt);
        kazanilan_hayat_txt = findViewById(R.id.kazanilan_hayat_txt);
        gun_txt = findViewById(R.id.gun_txt);
        gecenYil_txt = findViewById(R.id.geecenYil_txt);
        gecenAy_txt = findViewById(R.id.gecenAy_txt);
        gecenGun_txt = findViewById(R.id.gecenGun_txt);
        listbutton = findViewById(R.id.button2);
        seekBar = findViewById(R.id.seekBar);
        frameLayout = findViewById(R.id.arkaplanf);
        banner = findViewById(R.id.adView);


        //Reklam Başlatma
        MobileAds.initialize(sigaraBirak.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });


        bannerReklam();
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);


// Kullanıcı Sigara Verileri
        int shared_gunlukicilen = sharedPreferences.getInt("gunlukicilen", 0);
        int shared_paketfiyat = sharedPreferences.getInt("paketfiyat", 0);
        int shared_sigarayil = sharedPreferences.getInt("sigarayil", 0);
        int shared_paketsigara = sharedPreferences.getInt("paketsigara", 0);

// Kullanıcı Zaman Verileri
        int shared_secilenyil = sharedPreferences.getInt("secilen_yil", 0);
        int shared_secilenay = sharedPreferences.getInt("secilen_ay", 0);
        int shared_secilengun = sharedPreferences.getInt("secilen_gun", 0);
        int shared_secilensaniye = sharedPreferences.getInt("secilen_saniye", 0);
        int shared_secilendakika = sharedPreferences.getInt("secilen_dakika", 0);
        int shared_secilensaat = sharedPreferences.getInt("secilen_saat", 0);

// Şu anki zamanı alıyoruz
        LocalDateTime suAn = LocalDateTime.now();

// Kullanıcının sigarayı bıraktığı zamanı alıyoruz
        LocalDateTime customDateTime = LocalDateTime.of(
                shared_secilenyil, shared_secilenay, shared_secilengun,
                shared_secilensaat, shared_secilendakika, shared_secilensaniye
        );

// Toplam saniye farkını hesaplıyoruz
        long toplamSaniyeFarki = ChronoUnit.SECONDS.between(customDateTime, suAn);

// 1 yılı 365 gün, 1 ayı 30 gün olarak ele alıyoruz
        int toplamGunFarki = (int) (toplamSaniyeFarki / 86400); // Toplam geçen gün
        int gecenYil = toplamGunFarki / 365; // 1 yıl = 365 gün
        int kalanGunYildanSonra = toplamGunFarki % 365; // Yıldan arta kalan günler
        int gecenAy = kalanGunYildanSonra / 30; // 1 ay = 30 gün
        int gecenGun = kalanGunYildanSonra % 30; // Aydan arta kalan günler

// Saat, dakika ve saniye farkını hesaplıyoruz
        int gecenSaat = (int) ((toplamSaniyeFarki % 86400) / 3600);
        int gecenDakika = (int) ((toplamSaniyeFarki % 3600) / 60);
        int gecenSaniye = (int) (toplamSaniyeFarki % 60);

// Toplam geçen zamanı saniye cinsinden saklıyoruz
        double toplamzaman = (double) toplamSaniyeFarki;
        currentSecond[0] = toplamzaman;

// `hours1`, `minutes1`, `seconds1` dizileri
        final int[] hours1 = {gecenSaat};
        final int[] minutes1 = {gecenDakika};
        final int[] seconds1 = {gecenSaniye};

// Zamanı yıl, ay, gün, saat, dakika ve saniye formatında gösteriyoruz
        @SuppressLint("DefaultLocale") String zamanDetay = String.format(
                "%d yıl, %d ay, %d gün, %d saat, %d dakika, %d saniye",
                gecenYil, gecenAy, gecenGun, gecenSaat, gecenDakika, gecenSaniye
        );

// `progress_txt` güncellemesi
        progress_txt.setText(String.format(
                "Geçen Süre:\n%d yıl, %d ay, %d gün\n%d saat, %d dakika, %d saniye",
                gecenYil, gecenAy, gecenGun, gecenSaat, gecenDakika, gecenSaniye
        ));


// Gerekirse toplam geçen süreyi başka yerlerde kullanmak için
        int gecenSure = (int) toplamzaman;


        //Seekbar Olayları
        seekBar.setEnabled(false);
        seekBar.getThumb().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        seekBar.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        seekbardurum = true;
        seekBar.getThumb().setVisible(true, false);
        // Seekbar ilerlemesi için oluşturulan durum
        int asama = 0;
        // Seekbar Aşamaları
        int[] duraklar = {3600 * 24, 3600 * 24 * 7, 3600 * 24 * 30, 3600 * 24 * 30 * 12, 3600 * 24 * 30 * 12 * 5};
        // Geçen süreye en yakın durak noktasını bulun
        for (int i = 0; i < duraklar.length; i++) {

            if (gecenSure < 3600) {
                asama = 0;
                break;
            } else if (gecenSure >= duraklar[i]) {
                asama++; // Bir sonraki aşamaya geç
            } else {
                break; // Duraklar arasında bir konumda olduğunda döngüden çık
            }
        }
        //SeekBar'ın pozisyonunu güncelle
        seekBar.setProgress(asama);
        odulluReklamYuke();

        //Bottom sekmesi sayfalar arası geçiş
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                kazanim kazanim = new kazanim();
                saglik saglik = new saglik();
                if (item.getItemId() == R.id.basari) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();


                    videoReklam();

                    kazanim.gecici = currentSecond[0];
                    transaction.replace(R.id.arkaplanf, kazanim).commit();


                    return true;
                } else if (item.getItemId() == R.id.saglik) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    videoReklam();

                    saglik.zaman = currentSecond[0];
                    transaction.replace(R.id.arkaplanf, saglik

                    ).commit();


                } else if (item.getItemId() == R.id.durum) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    sigaraBirak sigaraBirak = new sigaraBirak();
                    Intent intent = new Intent(getApplicationContext(), sigaraBirak.class);

                    startActivity(intent);

                    return true;

                }
                return true;
            }
        });


        //Kullanıcının bu zamana kadarki ictiği ve içmediği sigara verileri
        Calendar bugun = Calendar.getInstance();
        yil = bugun.get(Calendar.YEAR);
        ay = bugun.get((Calendar.MONTH));
        gun = bugun.get(Calendar.DAY_OF_MONTH);

        //Bırakmadan önce İçilen veriler
        buzamanakadar_sigara = shared_gunlukicilen * 365 * shared_sigarayil;
        buzamanakadar_para = (buzamanakadar_sigara / shared_paketsigara) * shared_paketfiyat;


        //İçilmeyen Sigara verileri
        icilmeyen_sigara_hesap = (float) shared_gunlukicilen / 86400;
        tasarruf_edilen_hesap = (((float) (shared_paketfiyat) / (float) (shared_paketsigara)) * (float) (shared_gunlukicilen)) / (float) 86400;
        kazanilan_hayat_hesap = ((float) (shared_gunlukicilen) * (float) (660)) / (float) (86400);
        kaybedilen_hayat_hesap = (shared_gunlukicilen * 720.0f) * shared_sigarayil * 365.0f; // Saniye cinsinden kaybedilen hayat

        //Sigarayı bıraktıktan sonra kazanılan hayat zamanı
        kazanilan_hayat = (float) (currentSecond[0] * kazanilan_hayat_hesap) / 86400;
        kazanilanhayatF = String.format("%.1f", kazanilan_hayat);
        kazanilan_hayat_txt.setText("Kazanılan Hayat\n" + kazanilanhayatF + " Gün");

        //Sigarayı bıraktıktan sonra tasarruf edilen para
        tasarruf_edilen = (float) (toplamzaman * tasarruf_edilen_hesap);
        tasarrufedilenF = String.format("%.1f", tasarruf_edilen);
        tasarruf_edilen_txt.setText("Tasarruf Edilen\n" + tasarrufedilenF + "₺");

        //Sigarayı bıraktıktan sonra içilmeyen sigara
        icilmeyen_sigara = (float) (toplamzaman * icilmeyen_sigara_hesap);
        icilmeyensigaraF = String.format("%.1f", icilmeyen_sigara);
        icilmeyen_sigara_txt.setText("İçilmeyen Sigara\n" + icilmeyensigaraF + " Adet");

        //Sigarayı bıraktıktan sonraki verileri ekrana yazdırıyoruz
        gecenGun_txt.setText("GÜN\n" + String.valueOf(gecenGun));
        gecenAy_txt.setText("AY\n" + String.valueOf(gecenAy));
        gecenYil_txt.setText("YIL\n" + String.valueOf(gecenYil));

        int gecti = (int) (gecenGun + (gecenAy) * 30 + (gecenYil) * 365);
        gun_txt.setText("Geçen Gün:" + gecti);


        //Sigara içerken kaybedilen hayat zamanı
        int kaybedilen_saniye_fazlalik = (int) (kaybedilen_hayat_hesap % 60);
        int kaybedilen_dakika_tam = (int) (kaybedilen_hayat_hesap / 60) % 60;
        int kaybedilen_saat_tam = (int) (kaybedilen_hayat_hesap / 3600) % 24;
        int kaybedilen_gun_tam = (int) (kaybedilen_hayat_hesap / 86400) % 31;
        int kaybedilen_ay_tam = (int) (kaybedilen_hayat_hesap / 2592000) % 12;
        int kaybedilen_yil = (int) (kaybedilen_hayat_hesap / 31104000);

        buzamanakadar_hayat = (float) (shared_gunlukicilen * 720 * shared_sigarayil * 365);
        gecen_yil = yil - shared_secilenyil;
        gecen_ay = ay - shared_secilenay;
        gecen_gun = gun - shared_secilengun;


        if (gecen_ay < 0) {
            gecen_ay = gecen_ay * -1;
        }
        if (gecen_gun < 0) {
            gecen_gun = gecen_gun * -1;
        }

        //Sigara içerken kaybedilen verileri yazdırıyoruz
        buzamanakadar_para_txt.setText("Harcanan Para\n" + buzamanakadar_para + "₺");
        buzamanakadar_sigara_txt.setText("İçilen Sigara\n" + buzamanakadar_sigara + " Adet");
        buzamanakadar_zaman_txt.setText("Kaybedilen Hayat\n" + kaybedilen_saniye_fazlalik + " Sn" + kaybedilen_dakika_tam + " DK" + kaybedilen_saat_tam + " Saat\n" + kaybedilen_gun_tam + " Gün" + kaybedilen_ay_tam + " Ay" + kaybedilen_yil + " Yıl");


        // Saniye başına düşen yüzde miktarını hesaplayın
        yuzdePerSecond = 100.0 / totalSeconds;
        // Progress bar'ın başlangıç değerini ve maksimum değerini ayarlayın
        progressBar1.setMax(100);

        //Progressbar ilerlemesi
        if (currentSecond[0] > totalSeconds) {
            ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                    .setDuration(1000)
                    .start();
            progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
            if (currentSecond[0] > 3600 * 24 * 30 * 12 * 10) {
                progress_txt.setText(String.format("%.1f%%\n\n10 YIL TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 3600 * 24 * 30 * 12 * 20;
                durum = "20 YIL";
            } else if (currentSecond[0] > 3600 * 24 * 30 * 12 * 5) {
                progress_txt.setText(String.format("%.1f%%\n\n5 YIL TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 3600 * 24 * 30 * 12 * 10;
                durum = "10 YIL";
            } else if (currentSecond[0] > 3600 * 24 * 30 * 12) {
                progress_txt.setText(String.format("%.1f%%\n\n1 YIL TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 3600 * 24 * 30 * 12 * 5;
                durum = "5 YIL";
            } else if (currentSecond[0] > 3600 * 24 * 30 * 6) {
                progress_txt.setText(String.format("%.1f%%\n\n6 AY TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 86400 * 30 * 12;
                durum = "1 YIL";
            } else if (currentSecond[0] > 3600 * 24 * 30) {
                progress_txt.setText(String.format("%.1f%%\n\n1 AY TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 86400 * 30 * 6;
                durum = "6 AY";
            } else if (currentSecond[0] > 3600 * 24 * 7) {
                progress_txt.setText(String.format("%.1f%%\n\n1 HAFTA TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 3600 * 24 * 30;
                durum = "1 AY";
            } else if (currentSecond[0] > 3600 * 24 * 2) {
                progress_txt.setText(String.format("%.1f%%\n\n2 GÜN TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 86400 * 7;
                durum = "1 HAFTA";
            } else if (currentSecond[0] > 3600 * 24) {
                progress_txt.setText(String.format("%.1f%%\n\n2 GÜN TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 86400 * 2;
                durum = "2 GÜN";
            } else if (currentSecond[0] > 3600) {
                progress_txt.setText(String.format("%.1f%%\n\n1 SAAT TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
                totalSeconds = 3600 * 24;
                durum = "1 GÜN";
            } else if (currentSecond[0] < 3600) {
                totalSeconds = 3600;
                durum = "1 SAAT";
            }
            progressStatus = 0.0;
            yuzdePerSecond = 100.0 / totalSeconds;
            newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
            progressBar1.setProgress(newPercentage);
            progress_txt.setText(String.format("%.1f", currentSecond[0] / totalSeconds * 100) + "%\n" + "\n"
                    + String.format("%02d:%02d:%02d", hours, minutes, seconds));
            int previousPercentage = progressBar1.getProgress();
            ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                    .setDuration(1000)
                    .start();
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (currentSecond[0] <= totalSeconds) {
                    // Geçen zamanı saat, dakika, saniye olarak hesaplayın
                    hours = (int) (currentSecond[0] / (60 * 60));
                    minutes = (int) (currentSecond[0] % (60 * 60)) / 60;
                    seconds = (int) (currentSecond[0] % 60);

                    hours1[0] = (int) ((currentSecond[0] / 3600) % 24);
                    minutes1[0] = (int) ((currentSecond[0] % 3600) / 60);
                    seconds1[0] = (int) (currentSecond[0] % 60);

                    // Yeni yüzde değerini hesaplayın
                    newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                    // Progress bar'ı ve metin görüntüleyicisini güncelle, yüzde değeri değiştiğinde
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            int previousPercentage = progressBar1.getProgress();

                            ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                                    .setDuration(1000)
                                    .start();
                            progress_txt.setTextSize(15);
                            progressBar1.setProgress(newPercentage);
                            progress_txt.setText(String.format("%.1f", currentSecond[0] / totalSeconds * 100) + "%\n" + "\n" + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                            gsaat.setText("SAAT\n" + hours1[0]);
                            gdakika.setText("DAKİKA\n" + minutes1[0]);
                            gsaniye.setText("SANİYE\n" + seconds1[0]);
                            progress_txt.setText(String.format("%.1f", currentSecond[0] / totalSeconds * 100) + "%\n" + "\n" + "Seçili:" + durum);
                            getText(giden);
                            if (seconds1[0] % 2 == 0) {
                                seekbaralpha = 400;
                                seekBar.getThumb().setAlpha(seekbaralpha);
                            } else {
                                seekbaralpha = 20;
                                seekBar.getThumb().setAlpha(seekbaralpha);
                            }
                            currentSecond[0]++;
                        }
                    });
                } else if (currentSecond[0] > totalSeconds) {
                    hours = (int) (currentSecond[0] / (60 * 60));
                    minutes = (int) (currentSecond[0] % (60 * 60)) / 60;
                    seconds = (int) (currentSecond[0] % 60);

                    hours1[0] = (int) ((currentSecond[0] / 3600) % 24);
                    minutes1[0] = (int) ((currentSecond[0] % 3600) / 60);
                    seconds1[0] = (int) (currentSecond[0] % 60);

                    currentSecond[0]++;
                    gsaat.setText("SAAT\n" + hours1[0]);
                    gdakika.setText("DAKİKA\n" + minutes1[0]);
                    gsaniye.setText("SANİYE\n" + seconds1[0]);
                    if (seconds1[0] % 2 == 0) {
                        seekbaralpha = 400;
                        seekBar.getThumb().setAlpha(seekbaralpha);
                    } else {
                        seekbaralpha = 20;
                        seekBar.getThumb().setAlpha(seekbaralpha);
                    }
                }
            }
        };
        new Timer().scheduleAtFixedRate(timerTask, 0, 1000); // Her 1 saniye aralıklarla çalıştır


    }


    //Menü Ayarları
    public void showpopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.liste);
        popupMenu.show();
    }

    //Menü Liste Seçimi
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.bir_gun) {
            Toast.makeText(this, "1 GÜN", Toast.LENGTH_SHORT).show();
            totalSeconds = 3600 * 24;
            if (currentSecond[0] > totalSeconds) {
                ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                        .setDuration(1000)
                        .start();
                progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
                progress_txt.setText(String.format("%.1f%%\n\n1 GÜN TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
            } else {
                toplamzaman = gecenSaniye + (gecenDakika) * 60 + (gecenSaat) * 3600 + (gecenGun) * 86400 + (gecenAy) * 2629743 + (gecenYil * 31556926);
                progressStatus = 0.0;
                durum = "1GÜN";
                yuzdePerSecond = 100.0 / totalSeconds;
                newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                progressBar1.setProgress(newPercentage);
                progress_txt.setText(String.format("%.1f", currentSecond[0] / totalSeconds * 100) + "%\n" + "\n" + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                int previousPercentage = progressBar1.getProgress();
                ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                        .setDuration(1000)
                        .start();

            }
            return true;
        }
        if (item.getItemId() == R.id.bir_saat) {
            Toast.makeText(this, "1 SAAT", Toast.LENGTH_SHORT).show();
            totalSeconds = 3600;
            if (currentSecond[0] > totalSeconds) {
                ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                        .setDuration(1000)
                        .start();
                progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
                progress_txt.setText(String.format("%.1f%%\n\n1 SAAT TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
            } else {
                durum = "1 SAAT";
                toplamzaman = gecenSaniye + (gecenDakika) * 60 + (gecenSaat) * 3600 + (gecenGun) * 86400 + (gecenAy) * 2629743 + (gecenYil * 31556926);
                progressStatus = 0.0;
                totalSeconds = 86400 / 24;
                yuzdePerSecond = 100.0 / totalSeconds;
                newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                progress_txt.setText(String.format("%.1f", currentSecond[0] / totalSeconds * 100) + "%\n" + "\n" + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                int previousPercentage = progressBar1.getProgress();
                ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                        .setDuration(1000)
                        .start();
            }
            return true;
        } else if (item.getItemId() == R.id.iki_gun) {
            Toast.makeText(this, "2 GÜN", Toast.LENGTH_SHORT).show();
            totalSeconds = 86400 * 2;
            if (currentSecond[0] > totalSeconds) {
                ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                        .setDuration(1000)
                        .start();
                progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
                progress_txt.setText(String.format("%.1f%%\n\n2 GÜN TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
            } else {
                durum = "2 GÜN";
                toplamzaman = gecenSaniye + (gecenDakika) * 60 + (gecenSaat) * 3600 + (gecenGun) * 86400 + (gecenAy) * 2629743 + (gecenYil * 31556926);
                progressStatus = 0.0;
                totalSeconds = 86400 * 2;
                yuzdePerSecond = 100.0 / totalSeconds;
                newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                int previousPercentage = progressBar1.getProgress();
                ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                        .setDuration(1000)
                        .start();
            }

            return true;
        } else if (item.getItemId() == R.id.bir_hafta) {
            Toast.makeText(this, "1 HAFTA", Toast.LENGTH_SHORT).show();
            totalSeconds = 86400 * 7;
            if (currentSecond[0] > totalSeconds) {
                ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                        .setDuration(1000)
                        .start();
                progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
                progress_txt.setText(String.format("%.1f%%\n\n1 HAFTA TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
            } else {
                toplamzaman = gecenSaniye + (gecenDakika) * 60 + (gecenSaat) * 3600 + (gecenGun) * 86400 + (gecenAy) * 2629743 + (gecenYil * 31556926);
                progressStatus = 0.0;
                totalSeconds = 86400 * 7;
                yuzdePerSecond = 100.0 / totalSeconds;
                durum = "1 HAFTA";
                newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                int previousPercentage = progressBar1.getProgress();
                ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                        .setDuration(1000)
                        .start();
            }
            return true;
        } else if (item.getItemId() == R.id.bir_ay) {

            Toast.makeText(this, "1 AY", Toast.LENGTH_SHORT).show();
            totalSeconds = 86400 * 30;
            if (currentSecond[0] > totalSeconds) {
                ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                        .setDuration(1000)
                        .start();
                progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
                progress_txt.setText(String.format("%.1f%%\n\n1 AY TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
            } else {
                toplamzaman = gecenSaniye + (gecenDakika) * 60 + (gecenSaat) * 3600 + (gecenGun) * 86400 + (gecenAy) * 2629743 + (gecenYil * 31556926);
                progressStatus = 0.0;
                durum = "1 AY";
                totalSeconds = 86400 * 30;
                yuzdePerSecond = 100.0 / totalSeconds;
                newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                int previousPercentage = progressBar1.getProgress();
                ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                        .setDuration(1000)
                        .start();
            }
            return true;
        } else if (item.getItemId() == R.id.alti_ay) {

            Toast.makeText(this, "6 AY", Toast.LENGTH_SHORT).show();
            totalSeconds = 86400 * 30 * 6;
            if (currentSecond[0] > totalSeconds) {
                ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                        .setDuration(1000)
                        .start();
                progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
                progress_txt.setText(String.format("%.1f%%\n\n6 AY TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
            } else {
                toplamzaman = gecenSaniye + (gecenDakika) * 60 + (gecenSaat) * 3600 + (gecenGun) * 86400 + (gecenAy) * 2629743 + (gecenYil * 31556926);
                progressStatus = 0.0;
                durum = "6 AY";
                totalSeconds = 86400 * 30 * 6;
                yuzdePerSecond = 100.0 / totalSeconds;
                newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                int previousPercentage = progressBar1.getProgress();
                ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                        .setDuration(1000)
                        .start();
            }
            return true;
        } else if (item.getItemId() == R.id.bir_yil) {
            Toast.makeText(this, "1 YIL", Toast.LENGTH_SHORT).show();
            totalSeconds = 86400 * 30 * 12;
            if (currentSecond[0] > totalSeconds) {
                ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                        .setDuration(1000)
                        .start();
                progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
                progress_txt.setText(String.format("%.1f%%\n\n1 YIL TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
            } else {
                toplamzaman = gecenSaniye + (gecenDakika) * 60 + (gecenSaat) * 3600 + (gecenGun) * 86400 + (gecenAy) * 2629743 + (gecenYil * 31556926);
                progressStatus = 0.0;
                durum = "1 YIL";
                totalSeconds = 86400 * 30 * 12;
                yuzdePerSecond = 100.0 / totalSeconds;
                newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                int previousPercentage = progressBar1.getProgress();
                ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                        .setDuration(1000)
                        .start();
            }
            return true;
        } else if (item.getItemId() == R.id.bes_yil) {
            Toast.makeText(this, "5 YIL", Toast.LENGTH_SHORT).show();
            totalSeconds = 86400 * 365 * 5;
            if (currentSecond[0] > totalSeconds) {
                ObjectAnimator.ofInt(progressBar1, "progress", 0, 100)
                        .setDuration(1000)
                        .start();
                progress_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
                progress_txt.setText(String.format("%.1f%%\n\n5 YIL TAMAMLANDI", 100.0));
                progress_txt.setTextColor(getColor(R.color.black));
            } else {
                progressStatus = 0.0;
                toplamzaman = gecenSaniye + (gecenDakika) * 60 + (gecenSaat) * 3600 + (gecenGun) * 86400 + (gecenAy) * 2629743 + (gecenYil * 31556926);
                durum = "5 YIL";
                totalSeconds = 86400 * 30 * 12 * 5;
                yuzdePerSecond = 100.0 / totalSeconds;
                newPercentage = (int) (currentSecond[0] * yuzdePerSecond);
                int previousPercentage = progressBar1.getProgress();
                ObjectAnimator.ofInt(progressBar1, "progress", previousPercentage, newPercentage)
                        .setDuration(1000)
                        .start();
            }
            return true;
        } else if (item.getItemId() == R.id.yenidenbasla) {

            // AlertDialog ile uyarı göster
            new AlertDialog.Builder(sigaraBirak.this)
                    .setTitle("Yeniden Başlat")
                    .setMessage("Gerçekten yeniden başlatmak istiyor musunuz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Sayfayı yeniden başlat
                            Intent intent = new Intent(sigaraBirak.this, kullaniciverileri_page.class);
                            startActivity(intent);
                            finish(); // Önceki aktiviteyi kapat
                        }
                    })
                    .setNegativeButton("Hayır", null) // "Hayır" seçilirse bir şey yapma
                    .show();
            return true;

        } else {
            return false;
        }


    }



    public double getText(double v) {
        v=currentSecond[0];
        return  v;
    }

    public void bannerReklam()
    {
        AdRequest adRequest1 = new AdRequest.Builder().build();
        InterstitialAd.load(sigaraBirak.this, "ca-app-pub-1527387264089153/6145613048", adRequest1, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                interstitialAd1 = interstitialAd;
            }
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                interstitialAd1 = null;
               // Toast.makeText(sigaraBirak.this,"Reklam Gösterilmedi",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void odulluReklamYuke()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(sigaraBirak.this, "ca-app-pub-1527387264089153/8593497199", adRequest, new RewardedAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                mrewardedAd = rewardedAd;
                mrewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        odulluReklamYuke();
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mrewardedAd=null;
              //  Toast.makeText(sigaraBirak.this,"Reklam Gösterilmedi",Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void videoReklam() {
        Random random = new Random();
        int chance = random.nextInt(100); // 0 ile 99 arasında rastgele sayı üret
        System.out.println("Reklam İhtimali" + chance);
        if (chance < 30) { // %30 ihtimalle reklam göster
            if (mrewardedAd != null) {
                mrewardedAd.show(sigaraBirak.this, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                    }
                });
            }
        }
    }



}
