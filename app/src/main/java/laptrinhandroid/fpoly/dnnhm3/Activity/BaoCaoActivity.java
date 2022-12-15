package laptrinhandroid.fpoly.dnnhm3.Activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao.BaocaoAdapterViewager;
import laptrinhandroid.fpoly.dnnhm3.R;

public class BaoCaoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao);

if(Build.VERSION.SDK_INT>=26){
    anhXa();
    setToolbar();
    BaocaoAdapterViewager adapterViewager = new BaocaoAdapterViewager(this);
    viewPager.setAdapter(adapterViewager);

    new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
        @Override
        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            switch (position){
                case 0: tab.setText("Thu chi"); break;
                case 1: tab.setText("Lãi lỗ"); break;
                case 2: tab.setText("Cửa hàng"); break;
                case 3: tab.setText("Kho hàng"); break;
            }
        }
    }).attach();
}else{
    findViewById(R.id.mLinearLayout).setVisibility(View.GONE);
}


    }

    private void anhXa(){
        toolbar = findViewById(R.id.baoCao_toolbar);
        viewPager = findViewById(R.id.baocao_viewpager);
        tabLayout = findViewById(R.id.baoCao_tablayout);
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //tắt title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //nut back
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}