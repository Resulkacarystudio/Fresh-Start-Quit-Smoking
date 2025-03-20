package com.resulkacar.sigaraybrakyorum;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class kazanim extends Fragment {

    ListView listView;
    Animation animation;
    String[] title;
    double gecici;

    TextView t1,birakmatarihi_txt,icinlemeyensigara_txt,tasarrufedilen_txt,kazanilanhayat_txt;
    CardView c1,c2,c3,c6,c12,c24,c48,c72,c1h,c2h,c1a,c3a,c6a,c1y,c5y;
    LinearLayout l1,l6,l3,l12,l24,l48,l72,l1h,l2h,l1a,l3a,l6a,l1y,l5y;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kazanim, container, false);
        sharedPreferences = getContext().getSharedPreferences("com.resulkacar.sigaraybrakyorum", Context.MODE_PRIVATE);

        icinlemeyensigara_txt = view.findViewById(R.id.icinmeyen_sigara_kazanim);
        tasarrufedilen_txt = view.findViewById(R.id.tasarruf_edilen_kazanim);
        kazanilanhayat_txt = view.findViewById(R.id.kazanilan_hayat_kazanim);


        t1 = view.findViewById(R.id.birsaatT);
        c1 = view.findViewById(R.id.birsaatC);//cardview
        l1 = view.findViewById(R.id.birsaatL);//linearlayout

        c3 = view.findViewById(R.id.ucsaatC);
        l3 = view.findViewById(R.id.ucsaatL);

        c6 = view.findViewById(R.id.altisaatC);
        l6 = view.findViewById(R.id.altisaatL);

        c12 = view.findViewById(R.id.onikisaatC);
        l12 = view.findViewById(R.id.onikisaatL);

        c24 = view.findViewById(R.id.birgunC);
        l24 = view.findViewById(R.id.birgunL);

        c48 = view.findViewById(R.id.ikigunC);
        l48 = view.findViewById(R.id.ikigunL);

        c72 = view.findViewById(R.id.ucgunC);
        l72 = view.findViewById(R.id.ucgunL);

        c1h = view.findViewById(R.id.birhaftaC);
        l1h = view.findViewById(R.id.birhaftaL);

        c2h = view.findViewById(R.id.ikihaftaC);
        l2h = view.findViewById(R.id.ikihaftaL);

        c1a = view.findViewById(R.id.birayC);
        l1a = view.findViewById(R.id.birayL);

        c3a = view.findViewById(R.id.ucayC);
        l3a = view.findViewById(R.id.ucayL);

        c6a = view.findViewById(R.id.altiayC);
        l6a = view.findViewById(R.id.altiayL);

        c1y = view.findViewById(R.id.biryilC);
        l1y = view.findViewById(R.id.biryilL);

        c5y = view.findViewById(R.id.besyilC);
        l5y = view.findViewById(R.id.besyilL);

        birakmatarihi_txt = view.findViewById(R.id.birakmatarihi);


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


        if(gecici > 3600)
        {
           // c1.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l1.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 3600*2)
        {
          //  c3.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l3.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 3600*6)
        {
            //c6.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l6.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 3600*12)
        {
           // c12.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l12.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 3600*24)
        {
           // c24.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l24.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 3600*48)
        {
          //  c48.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l48.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 3600*72)
        {
            //c72.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l72.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 86400*7)
        {
           // c1h.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l1h.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 86400*14)
        {
            //c2h.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l2h.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 86400*30)
        {
           // c1a.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l1a.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 86400*30*3)
        {
          //  c3a.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l3a.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 86400*30*6)
        {
           // c6a.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l6a.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 86400*30*12)
        {
          //  c1y.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l1y.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        if(gecici > 86400*30*12*5)
        {
            //c5y.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk
            l5y.setBackgroundColor(Color.rgb(0, 150, 136)); // Kapalı Yeşil renk

        }

        return view;
    }

    public void setDeger(double deger) {
        // Degeri kullanmak için buraya istediğiniz işlemleri ekleyin
        t1.setText(String.valueOf(deger));
    }
}
