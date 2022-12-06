package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdpaterNhanVien.AdapterListNhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.notifiDataChange;

public class QuanLiNhanVien extends AppCompatActivity implements notifiDataChange {
    RecyclerView recyclerView;
    FloatingActionButton action;
    AdapterListNhanVien adapterListChamCong;
    List<NhanVien> chamCongs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_nhan_vien);
        recyclerView = findViewById(R.id.rcy);
        action = findViewById(R.id.add);
        setToolBar();
        action.setOnClickListener(view -> {
            startActivity(new Intent(this, AddNhanVien.class));
        });
        try {
            chamCongs = GiaoDienChinh.nhanVien1.getListNhanVien();
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            adapterListChamCong = new AdapterListNhanVien(this);
            adapterListChamCong.setData(chamCongs);

            recyclerView.setAdapter(adapterListChamCong);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

      runOnUiThread(new Runnable() {
          @Override
          public void run() {
              try {
                  chamCongs = GiaoDienChinh.nhanVien1.getListNhanVien();
                  adapterListChamCong.setData(chamCongs);

              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
      });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);//set icon menu
    }

    @Override
    public void notifiDataChange(int i) {
        chamCongs.remove(i);
        adapterListChamCong.setData(chamCongs);
    }
}