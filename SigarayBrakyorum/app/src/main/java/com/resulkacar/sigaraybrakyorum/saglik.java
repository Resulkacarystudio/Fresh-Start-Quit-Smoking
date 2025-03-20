package com.resulkacar.sigaraybrakyorum;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

public class saglik extends Fragment {

    Button button;
    double zaman;
    LinearLayout l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11;
    TextView icinlemeyensigara_txt,tasarrufedilen_txt,kazanilanhayat_txt,birakmatarihi;
    SharedPreferences sharedPreferences;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saglik, container, false);

        sharedPreferences = getContext().getSharedPreferences("com.resulkacar.sigaraybrakyorum", Context.MODE_PRIVATE);


        l1 =  view.findViewById(R.id.l1);
        l2= view.findViewById(R.id.l2);
        l3 =  view.findViewById(R.id.l3);
        l4 =  view.findViewById(R.id.l4);
        l5 =  view.findViewById(R.id.l5);
        l6 =  view.findViewById(R.id.l6);
        l7 =  view.findViewById(R.id.l7);
        l8 =  view.findViewById(R.id.l8);
        l9 =  view.findViewById(R.id.l9);
        l10 =  view.findViewById(R.id.l10);
        l11 =  view.findViewById(R.id.l11);

        icinlemeyensigara_txt = view.findViewById(R.id.icinmeyen_sigara_saglik);
        tasarrufedilen_txt = view.findViewById(R.id.tasarruf_edilen_saglik);
        kazanilanhayat_txt = view.findViewById(R.id.kazanilan_hayat_saglik);

        sigaraBirak sigaraBirak = new sigaraBirak();

        icinlemeyensigara_txt.setText("İçilmeyen Sigara:"+sigaraBirak.icilmeyensigaraF+" Adet");
        tasarrufedilen_txt.setText("Tasarruf Edilen"+ sigaraBirak.tasarrufedilenF +"₺");
        kazanilanhayat_txt.setText("Kazanılan Hayat:"+ sigaraBirak.kazanilanhayatF+" Gün");

        // Kullanıcı Zaman Verileri
        int shared_secilenyil = sharedPreferences.getInt("secilen_yil", 0);
        int shared_secilenay = sharedPreferences.getInt("secilen_ay", 0);
        int shared_secilengun = sharedPreferences.getInt("secilen_gun", 0);

// Ayın adını almak için ay isimleri dizisi
        String[] ayIsimleri = {"Oca", "Şub", "Mar", "Nis", "May", "Haz", "Tem", "Ağu", "Eyl", "Eki", "Kas", "Ara"};
        String tarihMetni = shared_secilengun + " " + ayIsimleri[shared_secilenay - 1] + ", " + shared_secilenyil;

        TextView birakmatarihi = view.findViewById(R.id.birakmatarihi);
        birakmatarihi.setText(tarihMetni);




        l1.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk






        if(zaman > 60*20)
        {
            l2.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk






        }

        if(zaman > 3600*8)
        {
            l3.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk

        }

        if(zaman > 86400)
        {
            l4.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk

        }

        if(zaman > 86400*2)
        {
            l5.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk
        }

        if(zaman > 86400*3)
        {
            l6.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk
        }

        if(zaman > 86400*14)
        {
            l7.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk
        }

        if(zaman > 86400*30*3)
        {
            l8.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk
        }

        if(zaman > 86400*30*12)
        {
            l9.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk
        }

        if(zaman > 86400*30*12*10)
        {
            l10.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk
        }

        if(zaman > 86400*30*12*15)
        {
            l10.setBackgroundColor(Color.rgb(73, 17, 30)); // Kapalı Yeşil renk
        }



        return view;

    }



}
