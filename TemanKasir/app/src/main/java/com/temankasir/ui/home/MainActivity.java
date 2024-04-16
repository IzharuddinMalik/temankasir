package com.temankasir.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.BubbleToggleView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.temankasir.R;
import com.temankasir.ui.home.fragment.FragHistori;
import com.temankasir.ui.home.fragment.FragPengaturan;
import com.temankasir.ui.home.fragment.FragTransaksi;

public class MainActivity extends AppCompatActivity {

    Fragment selectedFragment = null;
    String fragment ="";
    BubbleToggleView view1;
    BubbleNavigationLinearView bubbleNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubbleNavigation = findViewById(R.id.bubbleNavigation);
        view1 = findViewById(R.id.home);

        fragment = getIntent().getStringExtra("fragment");

        if(fragment!=null && fragment.equals("pengaturan")){
            bubbleNavigation.setCurrentActiveItem(2);
            recent_fragment(new FragPengaturan());
        }else if(fragment!=null && fragment.equals("riwayat")){
            bubbleNavigation.setCurrentActiveItem(1);
            recent_fragment(new FragHistori());
        }else{
            recent_fragment(new FragTransaksi());
        }

        bubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position){
                    case 0:
                        selectedFragment = new FragTransaksi();
                        break;
                    case 1:
                        selectedFragment = new FragHistori();
                        break;
                    case 2:
                        selectedFragment = new FragPengaturan();
                        break;
                }
                recent_fragment(selectedFragment);
            }
        });
    }

    public void bublesetitem(int posisi){
        bubbleNavigation.setCurrentActiveItem(posisi);
    }

    public void recent_fragment(Fragment selectedFragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }

    @Override
    public void onBackPressed() {

    }
}