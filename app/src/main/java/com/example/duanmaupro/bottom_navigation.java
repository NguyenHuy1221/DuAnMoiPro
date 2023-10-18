package com.example.duanmaupro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanmaupro.Fragment.Fragment_Trang_Chu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottom_navigation extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        // ánh xạ
        bottomNavigationView = findViewById(R.id.navigation_bottom);

        //sét fragment mặc định khi chạy lên
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout,new Fragment_Trang_Chu())
                .commit();

//        // sự kiện khi ấn vào ic_trên bottonNavigation
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment = null;
//                int items = item.getItemId();
//
//                if(items == R.id.action_home){
//                    fragment = new Fragment_Trang_Chu();
//                } else if (items == R.id.action_search) {
//                    fragment = new F();
//                } else if (items == R.id.action_heart) {
//                    fragment = new Fragment_Theo_Doi();
//                } else if (items == R.id.action_people) {
//                    fragment = new Fragment_Toi();
//                }
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frameLayout,fragment)
//                        .commit();
//
//                return true;
//            }
//        });


    }
}