package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterSanPham.ViewSanPhamAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.SanPham.FragmentSanPham;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.addsp;

public class SanPhamActivity extends AppCompatActivity implements addsp {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    Toolbar toolbar;
    FragmentSanPham sanPham;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        tabLayout = findViewById(R.id.tablayout_sanpham);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolBar_sanpham);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Sản Phẩm");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        sanPham = new FragmentSanPham();
        ViewSanPhamAdapter viewSanPhamAdapter = new ViewSanPhamAdapter(this, sanPham);
        viewPager.setAdapter(viewSanPhamAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (TabLayoutMediator.TabConfigurationStrategy) (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Sản phẩm");


                    break;
                case 1:
                    tab.setText("Danh mục");
                    break;
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        final android.widget.SearchView searchView = (android.widget.SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Tìm kiếm sản phẩm");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sanPham.InforSearch(s);
                return true;
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    menu.clear();
                }else{
                    getMenuInflater().inflate(R.menu.menu, menu);
                    MenuItem menuItem = menu.findItem(R.id.app_bar_search);
                    final android.widget.SearchView searchView = (android.widget.SearchView) MenuItemCompat.getActionView(menuItem);
                    searchView.setQueryHint("Tìm kiếm sản phẩm");
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            sanPham.InforSearch(s);
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean addSP(SanPham sanPham) {
        if (new DAOSanPham().addSanPham(sanPham)) {
            return true;
        }
        return false;
    }


}