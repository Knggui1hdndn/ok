package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdpaterNhanVien.AdapterListChamCong;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOChamCong;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChamCong;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ThongBaoNV;
import laptrinhandroid.fpoly.dnnhm3.Fragment.NhanVien.FragmentLuong;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.notification.FcmNotificationsSender;
import laptrinhandroid.fpoly.dnnhm3.sendData;

public class ListChamCongBangLuong extends AppCompatActivity implements sendData {

    RecyclerView recyclerView;
    Intent intent;
    FrameLayout frameLayout;
    public List<ChamCong> chamCongs1 = new ArrayList<>();
    int maNV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cham_cong);
        recyclerView = findViewById(R.id.rcy);
        frameLayout = findViewById(R.id.frameLayout);
        intent = getIntent();
        DAOChamCong chamCong = new DAOChamCong();
        Intent intent = getIntent();
        maNV = intent.getIntExtra("maNV", 0);
        String ngay = intent.getStringExtra("ngay");

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        if (ngay != null) {
            List<ChamCong> chamCongs = null;
            try {
                setToolBar("Danh sách chấm công");
                chamCongs = chamCong.getListChamCong(maNV);
                if (chamCongs != null) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                    AdapterListChamCong adapterListChamCong = new AdapterListChamCong();
                    DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(itemDecoration);
                    adapterListChamCong.setData(chamCongs);
                    recyclerView.setAdapter(adapterListChamCong);
                    int i;
                    for (i = 0; i < chamCongs.size(); i++) {
                        if (chamCongs.get(i).getNgay().toString().equals(ngay)) {
                            recyclerView.scrollToPosition(i);
                            break;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            if (intent.getIntExtra("CC", 0) == 1 && intent.getIntExtra("maNV", 0) != 0) {
                List<ChamCong> chamCongs = null;
                setToolBar("Danh sách chấm công");
                try {
                    chamCongs = chamCong.getListChamCong(maNV);
                    if (chamCongs != null) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                        AdapterListChamCong adapterListChamCong = new AdapterListChamCong();
                        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                        recyclerView.addItemDecoration(itemDecoration);
                        adapterListChamCong.setData(chamCongs);
                        recyclerView.setAdapter(adapterListChamCong);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (intent.getIntExtra("BL", 0) == 2 && intent.getIntExtra("maNV", 0) != 0) {
                setToolBar("Bảng lương nhân viên");
                FragmentLuong fragmentLuong = new FragmentLuong();
                Bundle bundle = new Bundle();
                bundle.putInt("nv", maNV);
                bundle.putString("ad", "ad");
                fragmentLuong.setArguments(bundle);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, fragmentLuong);
                ft.commit();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       if(getIntent().getIntExtra("BL",0)==0){
           getMenuInflater().inflate(R.menu.menu1, menu);
           menu.getItem(0).setTitle("Lưu");
       }
        return true;
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
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo");
            builder.setCancelable(false);
            builder.setMessage("Dữ liệu của nhân viên sẽ bị thay đổi ?");
            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DAOChamCong chamCong = GiaoDienChinh.daoChamCong;
                    int i1 = 0;
                    for (ChamCong cong : chamCongs1) {
                        try {
                            if (chamCong.updateChamCong(cong)) {
                                ThongBaoNV thongBaoNV = new ThongBaoNV();
                                thongBaoNV.setMaNV(maNV);
                                thongBaoNV.setDoc(false);
                                thongBaoNV.setNgay(new Date(System.currentTimeMillis()).toString());
                                new FcmNotificationsSender(GiaoDienChinh.nhanVien1.getListNhanVien(maNV).getToken(), "Cập nhật của admin", "Nhấn vào đây để đi đến ứng dụng", ListChamCongBangLuong.this).SendNotifications();
                                thongBaoNV.setTrangThai(String.valueOf(cong.getXacNhanChamCong()));
                                if (cong.getXacNhanChamCong() == 0) {
                                    thongBaoNV.setMessage("Admin chưa xác nhận ngày công của bạn(" + cong.getNgay() + ")");
                                } else if (cong.getXacNhanChamCong() == 1) {
                                    thongBaoNV.setMessage("Admin đã xác nhận ngày công của bạn(" + cong.getNgay() + ")");
                                } else {
                                    thongBaoNV.setMessage("Admin hủy ngày công của bạn(" + cong.getNgay() + ")");
                                }
                                GiaoDienChinh.thongBaoNVDAO.addThongBaoNhanVien(thongBaoNV);

                                i1++;
                                builder.setMessage("Cập nhật " + i1 + "/" + chamCongs1.size());

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (i1 == chamCongs1.size()) {
                        Toast.makeText(ListChamCongBangLuong.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    public void setToolBar(String s) {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(s);
        actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);//set icon menu
    }

    @Override
    public void sendData(ChamCong chamCong) {
        chamCongs1.remove(chamCong);
        chamCongs1.add(chamCong);
    }
}