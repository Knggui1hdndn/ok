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
import android.util.Log;
import android.view.MenuItem;

import java.sql.SQLException;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterNotification.AdapterThongBaoFromAdmin;
import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterNotification.AdapterThongBaoFromNhanVien;
import laptrinhandroid.fpoly.dnnhm3.DAO.ThongBaoAdminDAO;
import laptrinhandroid.fpoly.dnnhm3.DAO.ThongBaoNVDAO;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ThongBaoAdmin;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ThongBaoNV;
import laptrinhandroid.fpoly.dnnhm3.R;

public class ThongBao extends AppCompatActivity {
    RecyclerView recyclerView;
    public static ThongBaoNVDAO thongBaoNVDAO = new ThongBaoNVDAO();
    public static ThongBaoAdminDAO thongBaoAdminDAO = new ThongBaoAdminDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        recyclerView = findViewById(R.id.rcy);
        Intent intent = getIntent();
        setToolBar();
        int mNV = intent.getIntExtra("nv", 0);
        try {
            if (mNV != 0) {
                List<ThongBaoNV> thongBaoNVS = thongBaoNVDAO.getListNotiNhanVien(mNV);
                AdapterThongBaoFromNhanVien adapterThongBao = new AdapterThongBaoFromNhanVien(GiaoDienChinh.nhanVien1.getListNhanVien(mNV));
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(itemDecoration);    if (thongBaoNVS != null) {
                    adapterThongBao.setData(thongBaoNVS);
                }
                recyclerView.setAdapter(adapterThongBao);
            } else {
                List<ThongBaoAdmin> listThongBaoAdmin = thongBaoAdminDAO.getListThongBaoAdmin();
                AdapterThongBaoFromAdmin adapterThongBaoFromAdmin = new AdapterThongBaoFromAdmin(this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(itemDecoration); if (listThongBaoAdmin != null) {
                    adapterThongBaoFromAdmin.setData(listThongBaoAdmin);
                }
                recyclerView.setAdapter(adapterThongBaoFromAdmin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("xxxxxxxxxx", "onCreate: " + e.getMessage());
        }

    } @Override
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
}