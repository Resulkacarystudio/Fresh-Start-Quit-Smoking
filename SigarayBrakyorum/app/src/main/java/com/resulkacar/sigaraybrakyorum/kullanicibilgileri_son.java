package com.resulkacar.sigaraybrakyorum;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class kullanicibilgileri_son extends AppCompatActivity {
    private LottieAnimationView zaman_anim;
    Button sigarayibirakiyorum_button;
    private TextInputEditText dateInput, timeInput;
    private TextView selectedDateTime;
    private String selectedDate = "", selectedTime = "";
    SharedPreferences sharedPreferences; // Sigara verilerini Telefon Hafızasına Kaydetmek İçin






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kullanicibilgileri_son);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            zaman_anim = findViewById(R.id.zaman_anim);
            sigarayibirakiyorum_button = findViewById(R.id.sigarayiBirakiyorum_Button);
            dateInput = findViewById(R.id.dateInput);
            timeInput = findViewById(R.id.timeInput);
            sharedPreferences = this.getSharedPreferences("com.resulkacar.sigaraybrakyorum", Context.MODE_PRIVATE); //Kullanıcı Verilerini Kaydetmek İçin



            //animasyon oynatılır
            zaman_anim.playAnimation();

            //Tarih ve Zaman olaylarına tıklatılınca
            dateInput.setOnClickListener(view -> showDatePicker());
            timeInput.setOnClickListener(view2 -> showTimePicker());



            //Kullanıcı tüm verileri girikten sonra anasayfaya geçer.
            sigarayibirakiyorum_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkDateSelected() || checkTimeSelected()) {
                        return; // Eğer eksik bilgi varsa burada işlemi durdur.
                    }


                    sharedPreferences.edit().putBoolean("calisti", true).apply();

                    Intent intent = new Intent(kullanicibilgileri_son.this, sigaraBirak.class);
                    startActivity(intent);
                    finish();

                }
            });


            return insets;
        });
    }

    //Kullanıcı tarihi seçer ve sharedprefences ile kaydedilir
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            if (year1 > year || (year1 == year && month1 > month) || (year1 == year && month1 == month && dayOfMonth > day)) {
                // Gelecek tarih seçildiyse uyarı ver
                showAlert("Hata", "Gelecek bir tarih seçemezsiniz.");
            } else {
                // Tarih seçilmişse, kullanıcıya göster ve kaydet
                selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                dateInput.setText(selectedDate);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("secilen_yil", year1);
                editor.putInt("secilen_ay", month1 + 1);
                editor.putInt("secilen_gun", dayOfMonth);
                editor.apply();
            }
        }, year, month, day);

        datePicker.show();
    }

    // Eğer tarih hiç seçilmediyse (boşsa) uyarı veren fonksiyon
    private boolean checkDateSelected() {
        String selectedDateText = dateInput.getText().toString().trim();

        if (selectedDateText.isEmpty()) {
            showAlert("Hata", "Lütfen bir tarih seçin.");
            return true;
        }

        return false;
    }



    //Kullanıcı zamanı seçer ve sharedprefences ile kaydedilir
    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            // Seçilen tarih ve saat
            Calendar selectedTimeCal = Calendar.getInstance();
            selectedTimeCal.set(Calendar.YEAR, sharedPreferences.getInt("secilen_yil", calendar.get(Calendar.YEAR)));
            selectedTimeCal.set(Calendar.MONTH, sharedPreferences.getInt("secilen_ay", calendar.get(Calendar.MONTH)) - 1);
            selectedTimeCal.set(Calendar.DAY_OF_MONTH, sharedPreferences.getInt("secilen_gun", calendar.get(Calendar.DAY_OF_MONTH)));
            selectedTimeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedTimeCal.set(Calendar.MINUTE, minute1);

            // Şu anki tarih ve saat
            Calendar currentTime = Calendar.getInstance();

            // İleri tarih kontrolü
            if (selectedTimeCal.after(currentTime)) {
                showAlert("Hata", "Gelecek bir saat seçemezsiniz.");
            } else {
                // Tarih ve saat uygun, kaydet
                selectedTime = hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1);
                timeInput.setText(selectedTime);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("secilen_saat", hourOfDay);
                editor.putInt("secilen_dakika", minute1);
                editor.putInt("secilen_saniye", 0);
                editor.apply();
            }
        }, hour, minute, true);

        timePicker.show();
    }


    // Eğer saat hiç seçilmediyse (boşsa) uyarı veren fonksiyon
    private boolean checkTimeSelected() {
        String selectedTimeText = timeInput.getText().toString().trim();

        if (selectedTimeText.isEmpty()) {
            showAlert("Hata", "Lütfen bir saat seçin.");
            return true;
        }

        return false;
    }


    // **Uyarı mesajı göstermek için fonksiyon**
    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Tamam", (dialog, which) -> dialog.dismiss())
                .show();
    }


}