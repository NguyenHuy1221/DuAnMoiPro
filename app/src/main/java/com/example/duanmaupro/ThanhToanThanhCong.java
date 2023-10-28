package com.example.duanmaupro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanmaupro.Fragment.Fragment_Trang_Chu;

public class ThanhToanThanhCong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_thanh_cong);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ThanhToanThanhCong.this, Navigation_Drawer.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}