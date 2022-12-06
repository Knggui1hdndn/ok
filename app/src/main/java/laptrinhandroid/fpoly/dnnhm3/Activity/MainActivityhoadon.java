package laptrinhandroid.fpoly.dnnhm3.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon.viewadapter;
import laptrinhandroid.fpoly.dnnhm3.Fragment.haodon1;
import laptrinhandroid.fpoly.dnnhm3.R;

public class MainActivityhoadon extends AppCompatActivity {
    ViewPager2 viewPager;
    TabLayout tabLayout;
    viewadapter viewadapter;
    Toolbar toolbar;
     EditText editText;
    haodon1 haodon1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityhoadon);
        viewPager = findViewById(R.id.viewpager2);
        tabLayout = findViewById(R.id.tablayout);
        toolbar = findViewById(R.id.toobar1);
        if(getIntent().getIntExtra("ch",0)==1){
            toolbar.setTitle("Cửa Hàng");
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_24);
        actionBar.setTitle("Đơn hàng");
        editText = findViewById(R.id.edttimkiem);
          haodon1=new haodon1();
         Bundle bundle=new Bundle();
         bundle.putInt("maNV",getIntent().getIntExtra("maNV",0));
         haodon1.setArguments(bundle);
        viewadapter = new viewadapter(this,haodon1);
        viewPager.setAdapter(viewadapter);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("don hang");
                        break;
                }
            }
        }).attach();
        tabLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item, menu);
        MenuItem menuItem = menu.findItem(R.id.menuseach);
        final android.widget.SearchView searchView = (android.widget.SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                haodon1.InforSearch(s);
                return true;
            }
        });
        return true;
    }


}