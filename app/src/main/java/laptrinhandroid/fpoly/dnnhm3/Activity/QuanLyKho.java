package laptrinhandroid.fpoly.dnnhm3.Activity;


import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.ViewPagerAdapter;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Kho.TabListBill_QLKho_Fragment;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Kho.TabListProduct_QLKho_Fragment;
import laptrinhandroid.fpoly.dnnhm3.Interface.InforSearch;
import laptrinhandroid.fpoly.dnnhm3.R;

public class QuanLyKho extends AppCompatActivity {
    TabListBill_QLKho_Fragment qlKho_fragment = new TabListBill_QLKho_Fragment();
    TabLayout tabLayout_kho;
    ViewPager2 viewPager_kho;
    ViewPagerAdapter adapter;
    Toolbar toolbar_kho;
    TabListBill_QLKho_Fragment qlKho_fragment1 = new TabListBill_QLKho_Fragment();
    TabListProduct_QLKho_Fragment tabListProductQlKhoFragment = new TabListProduct_QLKho_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_kho);
        tabLayout_kho = findViewById(R.id.tablayout);
        viewPager_kho = findViewById(R.id.viewpager);

        toolbar_kho = findViewById(R.id.toolBar_kho);
        toolbar_kho.setTitleTextColor(Color.WHITE);
        toolbar_kho.setTitle("Kho hàng");
        setSupportActionBar(toolbar_kho);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        adapter = new ViewPagerAdapter(QuanLyKho.this, tabListProductQlKhoFragment, qlKho_fragment1, getIntent().getIntExtra("maNV", 0));
        viewPager_kho.setAdapter(adapter);

        new TabLayoutMediator(tabLayout_kho, viewPager_kho, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Tồn kho");
                        break;
                    case 1:
                        tab.setText("Đơn Nhập");
                        break;
                }
            }
        }).attach();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.item_kho, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_searchkho).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Tìm kiếm sản phẩm");
        tabLayout_kho.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    searchView.setQueryHint("Tìm kiếm sản phẩm");

                } else {
                    searchView.setQueryHint("Tìm kiếm hóa đơn");

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (tabLayout_kho.getSelectedTabPosition() == 0) {
                    tabListProductQlKhoFragment.InforSearch(s);
                } else {
                    qlKho_fragment1.InforSearch(s);
                }
                return true;
            }
        });
        return true;

    }

}

