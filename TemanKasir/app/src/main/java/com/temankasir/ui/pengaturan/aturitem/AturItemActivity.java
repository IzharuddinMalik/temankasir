package com.temankasir.ui.pengaturan.aturitem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.BubbleToggleView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.temankasir.R;
import com.temankasir.ui.home.MainActivity;
import com.temankasir.ui.home.fragment.FragHistori;
import com.temankasir.ui.home.fragment.FragPengaturan;
import com.temankasir.ui.home.fragment.FragTransaksi;
import com.temankasir.ui.pengaturan.aturitem.fragment.FragItem;
import com.temankasir.ui.pengaturan.aturitem.fragment.FragKategoriItem;

public class AturItemActivity extends AppCompatActivity {

    Fragment selectedFragment = null;
    String fragment ="";
    BubbleToggleView view1;
    BubbleNavigationLinearView bubbleNavigation;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_item);

        bubbleNavigation = findViewById(R.id.bubbleNavigationItem);
        view1 = findViewById(R.id.item);
        ivBack = findViewById(R.id.ivAturItemBackToPengaturan);

        ivBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragment", "pengaturan");
            startActivity(intent);
        });

        fragment = getIntent().getStringExtra("fragment");

        if(fragment!=null && fragment.equals("kategoriitem")){
            bubbleNavigation.setCurrentActiveItem(1);
            recent_fragment(new FragKategoriItem());
        }else{
            recent_fragment(new FragItem());
        }

        bubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position){
                    case 0:
                        selectedFragment = new FragItem();
                        break;
                    case 1:
                        selectedFragment = new FragKategoriItem();
                        break;
                }
                recent_fragment(selectedFragment);
            }
        });
    }

    public void recent_fragment(Fragment selectedFragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_item,
                selectedFragment).commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment", "pengaturan");
        startActivity(intent);
    }
}