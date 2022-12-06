package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

 import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.HoadonAdapter1;
import laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao.HoadonDateAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOBaoCao;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.R;

public class BaoCaoDonhangActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    TextView tvMassege;

    BottomSheetDialog sheetDialogLich;
    List<HoaDonBan> listHoaDonBan = new ArrayList<>();
    DAOBaoCao daoBaoCao;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    boolean isDonhang;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_donhang);

        toolbar = findViewById(R.id.baoCaoDonHang_toolbar);
        recyclerView = findViewById(R.id.baocaoDonHang_rcv);
        tvMassege = findViewById(R.id.baocaoDonhang_tv_messege);

        Bundle bundle = getIntent().getExtras();
        listHoaDonBan = (List<HoaDonBan>) bundle.getSerializable("data");
        isDonhang = bundle.getBoolean("is");
        daoBaoCao  = new DAOBaoCao();
        setToolbar();

        if (isDonhang) tvMassege.setText("Không có đơn hàng");
        else tvMassege.setText("Không có đơn hủy");

        if (listHoaDonBan != null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            HoadonAdapter1 hoadonAdapter  = new HoadonAdapter1(this, listHoaDonBan);
//            HoadonDateAdapter hoadonAdapter = new HoadonDateAdapter(this, listHoaDonBan);
            recyclerView.setAdapter(hoadonAdapter);
        }

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
    protected void onResume() {
        super.onResume();
        if (listHoaDonBan != null) tvMassege.setVisibility(View.GONE);
        else tvMassege.setVisibility(View.VISIBLE);


    }
}