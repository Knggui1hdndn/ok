package laptrinhandroid.fpoly.dnnhm3.Activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon.Adapterchitiet;
import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon.Spinerkhachhang;

import laptrinhandroid.fpoly.dnnhm3.Adapter.Spinernhanvien;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAO.DAOHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOhoadon;
import laptrinhandroid.fpoly.dnnhm3.DAO.Daochitiethoadon;
import laptrinhandroid.fpoly.dnnhm3.DAO.Daokhachhang;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonban;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.KhachHang;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class Chitiethoadon extends AppCompatActivity {
    Context context;
    Toolbar toolbar;
    Adapterchitiet adapterchitiet;
    RecyclerView recyclerView;
    //list
    List<HoaDonBan> hoaDonBanList;
    List<KhachHang> listkh;
    List<NhanVien> listnv;
    List<SanPham> listsp;

    //dao
    DAOhoadon daOhoadon;
    Daokhachhang daokhachhang;
    DAONhanVien daoNhanVien;
    DAOSanPham daoSanPham;
    Daochitiethoadon daochitiethoadon;
    //spiner
    Spinernhanvien spinernhanvien;
    Spinerkhachhang spinerkhachhang;
    Spinner spinner1, spinner2;
    long l = 0;
    long f = 0;
    TextView date;
    int makh, manv;

    List<SanPham> sanPhams;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    boolean check = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiethoadon);
        toolbar = findViewById(R.id.toolbarCTHD);
        TextView tongsanpham = findViewById(R.id.CTHD_tongsanpham);
        Button Thanhtoan = findViewById(R.id.CTHD_thanhtoan);
        TextView tontien = findViewById(R.id.CTHD_tongtien);
        TextView khachhang = findViewById(R.id.CTHD_TaoKhachhang);
        date = findViewById(R.id.CTHD_DATE);
        date.setText(format.format(new Date()));
        spinner1 = findViewById(R.id.spinernhanvienhd);
        spinner2 = findViewById(R.id.CTHD_khachhang);
        toolbar.setTitle("Xác nhận đơn hàng ");

        recyclerView = findViewById(R.id.Recychodon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        listnv = new ArrayList<NhanVien>();
        daoNhanVien = new DAONhanVien();
        try {
            listnv = daoNhanVien.getListNhanVien();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        spinernhanvien = new Spinernhanvien(getApplicationContext(), listnv);
        if (getIntent().getIntExtra("maNV", 0) == 28) {
            listnv.clear();
            NhanVien nhanVien = new NhanVien();
            nhanVien.setHoTen("admin");
            nhanVien.setMaNv(28);
            listnv.add(nhanVien);
        }
        spinner1.setAdapter(spinernhanvien);
        spinner1.setSelection(listnv.size() - 1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                manv = listnv.get(i).getMaNv();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //spiner khachhang
        listkh = new ArrayList<KhachHang>();
        daokhachhang = new Daokhachhang();
        try {
            listkh.clear();
            listkh.addAll(daokhachhang.getlistkhachang());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        spinerkhachhang = new Spinerkhachhang(getApplicationContext(), listkh);
        spinner2.setAdapter(spinerkhachhang);
        spinner2.setSelection(listkh.size() - 1);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                makh = listkh.get(i).getMaKhach();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sanPhams = (List<SanPham>) getIntent().getSerializableExtra("key");
        adapterchitiet = new Adapterchitiet(getApplicationContext(), sanPhams);
        recyclerView.setAdapter(adapterchitiet);
        for (SanPham sanPham : sanPhams) {
            l += sanPham.getSoLuong() * sanPham.getGiaBan();
            f += sanPham.getSoLuong();
        }
        tongsanpham.setText(f + "");
        String tongtien = l + "";
        tontien.setText(tongtien + " đ");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_24);
        actionBar.setTitle("Xác nhận đơn hàng");
        khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertkhachhang();
            }
        });

        Thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (addHoaDon() == 1) {
                        Dialog dialog = new Dialog(Chitiethoadon.this);
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.item_progress_sussec);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.cancel();
                                finish();
                            }
                        }, 2000);
                    } else {
                        Dialog dialog = new Dialog(Chitiethoadon.this);
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.item_progress_can);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.cancel();
                                finish();
                            }
                        }, 2000);


                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        });

    }

    private int addHoaDon() throws SQLException {
        HoaDonBan hoaDonBan = new HoaDonBan();
        DAOSanPham daoSanPham = new DAOSanPham();
        hoaDonBan.setMaKH(makh);
        hoaDonBan.setMaNV(manv);
        hoaDonBan.setTongTien(l);
        hoaDonBan.setNgayBan(new java.sql.Date(System.currentTimeMillis()));
        int i = 0;
        if (new DAOhoadon().Insert(hoaDonBan)) {
            List<HoaDonBan> maHoaDon = new DAOhoadon().getListHoadonban();
            int maHD = maHoaDon.get(maHoaDon.size() - 1).getMaHDBan();
            Daochitiethoadon daoChiTietHoaDonNhap = new Daochitiethoadon();
            for (SanPham sanPham : sanPhams) {
                if (daoChiTietHoaDonNhap.Insertchitiethoadon(new ChiTietHoaDonban(maHD, sanPham.getMaSP(),
                        sanPham.getAnh(), sanPham.getTenSP(), sanPham.getSoLuong(), sanPham.getGiaBan(),
                        sanPham.getGiaBan() * sanPham.getSoLuong()))) {
                    sanPham.setSoLuong(daoSanPham.getIdSP(String.valueOf(sanPham.getMaSP())).getSoLuong() - sanPham.getSoLuong());
                    sanPham.setSoLuongDaBan(daoSanPham.getIdSP(String.valueOf(sanPham.getMaSP())).getSoLuongDaBan() + sanPham.getSoLuong());
                    if (daoSanPham.updateSanPham(sanPham)) {
                        i = 1;
                    }
                }
            }
        }
        return i;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);

    }


    @SuppressLint("MissingInflatedId")
    public void insertkhachhang() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogkhachhang, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        EditText txtname, txtdiachi, txtSdt;
        ImageView txtimg;
        Button btnthem, btngiaosau;


        txtname = view.findViewById(R.id.DIGLOG_name);
        txtdiachi = view.findViewById(R.id.DIGLOG_diachi);
        txtSdt = view.findViewById(R.id.DIGLOG_SDT);
        txtimg = view.findViewById(R.id.DIGLOG_Img);
        btnthem = view.findViewById(R.id.DIALOG_Save);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                if (txtname.getText().toString().isEmpty()) {
                    i++;
                    txtname.setError("Họ tên không được để trống");
                 }  if (txtdiachi.getText().toString().isEmpty()) {
                    txtdiachi.setError("Địa chỉ không được để trống");
                    i++;

                 }   if (txtSdt.getText().toString().isEmpty()) {
                    txtSdt.setError("Sđt không được để trống");
i++;
                 }
                if (i == 0) {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setHoTen(txtname.getText().toString());
                    khachHang.setDiaChi(txtdiachi.getText().toString());
                    khachHang.setSoDT(txtSdt.getText().toString());
                    try {
                        if (daokhachhang.addKhachhang(khachHang)) {
                            Toast.makeText(Chitiethoadon.this, "thanh cong", Toast.LENGTH_SHORT).show();

                            spinerkhachhang.add(khachHang);
                            spinerkhachhang.notifyDataSetChanged();
                            dialog.cancel();
                        } else {
                            Toast.makeText(Chitiethoadon.this, "khong thanh cong" +
                                    "", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
//
    }
}
