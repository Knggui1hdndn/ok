package laptrinhandroid.fpoly.dnnhm3.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.SanPhamThanhToanAdapter;
import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.Spinner_nhaCungCap;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOhoadon;
import laptrinhandroid.fpoly.dnnhm3.DAO.DaoNhaCungCap;
import laptrinhandroid.fpoly.dnnhm3.DAO.Daochitiethoadon;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonban;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhaCungCap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class ChiTietHoaDonNhapActivity extends AppCompatActivity {
    private HoaDonNhapKho mHoaDonNhap;
    private RecyclerView recyclerView;
    private SanPham msanPham;
    ArrayList<SanPham> arrSP = new ArrayList<>();
    ArrayList<HoaDonNhapKho> arrHDN = new ArrayList<>();
    ArrayList<ChiTietHoaDonNhap> arrChiTietHD = new ArrayList<>();
    private Button btn_datHang;
    private Spinner spinner_ncc;
    private TextView tv_tongsoluong, tv_tongtienhang, tv_tongchi, tv_ngay, tv_addNCC;
    SanPhamThanhToanAdapter adapter;
    DAOSanPham daoSanPham = new DAOSanPham();
    DaoNhaCungCap daoNhaCungCap;
    List<NhaCungCap> listNcc = new ArrayList<>();
    DAOHoaDonNhap daoHoaDonNhap;
    DAOChiTietHoaDonNhap daoChiTietHoaDonNhap = new DAOChiTietHoaDonNhap();
    Spinner_nhaCungCap spinnerNhaCungCap;
    int tongsoluong = 0, tongtienhang = 0, maNCC;
    boolean check = false;
int maNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don_nhap);
        initView();
        maNV=getIntent().getIntExtra("maNV",0);
        arrSP = (ArrayList<SanPham>) getIntent().getSerializableExtra("key");
        setDataBill();
        tv_addNCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertNhaCungCap();
            }
        });
        btn_datHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (addHoaDonNhap() > 0) {
                        Toast.makeText(ChiTietHoaDonNhapActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ChiTietHoaDonNhapActivity.this, "Đặt hàng không thành công", Toast.LENGTH_SHORT).show();

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.d("ffffffghhh", "onClick: "+e.getMessage());
                }
            }
        });
    }

    private int addHoaDonNhap() throws SQLException {
        DAOSanPham daoSanPham = new DAOSanPham();
        int i = 0;
        if (new DAOHoaDonNhap().addHoaDon(new HoaDonNhapKho(maNV, maNCC, new java.sql.Date(System.currentTimeMillis()), tongtienhang))) {
             List<HoaDonNhapKho> maHoaDon = new DAOHoaDonNhap().getListHoaDonNhap();
            int maHD = maHoaDon.get(maHoaDon.size() - 1).getMaHDNhap();
            DAOChiTietHoaDonNhap daoChiTietHoaDonNhap = new DAOChiTietHoaDonNhap();
            for (SanPham sanPham : arrSP) {
                if (daoChiTietHoaDonNhap.Insertchitiethoadonnhap(new ChiTietHoaDonNhap(maHD, sanPham.getMaSP(),
                        sanPham.getAnh(), sanPham.getTenSP(), sanPham.getSoLuong(), sanPham.getGiaNhap(),
                        sanPham.getGiaNhap() * sanPham.getSoLuong()))) {
                    sanPham.setSoLuong(daoSanPham.getIdSP(String.valueOf(sanPham.getMaSP())).getSoLuong() + sanPham.getSoLuong());
                    if (daoSanPham.updateSanPham(sanPham)) {
                        i = 1;
                    }
                }
            }
        }
        return i;
    }

    private void checkInsertHoaDon(boolean checkDon) {
        if (checkDon == true) {
            Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }


    private void initView() {
        btn_datHang = findViewById(R.id.btn_datHang);
        recyclerView = findViewById(R.id.recyclerview_lsProduct_ChiTietHoaDonNhap);
        tv_ngay = findViewById(R.id.tv_ngay_ChiTietHoaDonNhap);
        tv_tongsoluong = findViewById(R.id.tv_chitietSoLuong_ChiTietHoaDonNhap);
        tv_tongtienhang = findViewById(R.id.tv_tongtienNH_ChiTietHoaDonNhap);
        tv_tongchi = findViewById(R.id.tv_tongchiNH_ChiTietHoaDonNhap);
        spinner_ncc = findViewById(R.id.spn_NCC);
        tv_addNCC = findViewById(R.id.tv_add_NCC);
    }

    public void insertNhaCungCap() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_ncc, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        EditText txtname, txtdiachi, txtSdt;
        ImageView txtimg;
        Button btnthem, btngiaosau;


        txtname = view.findViewById(R.id.DIGLOG_name_ncc);
        txtdiachi = view.findViewById(R.id.DIGLOG_diachi_ncc);
        txtSdt = view.findViewById(R.id.DIGLOG_SDT_ncc);
        txtimg = view.findViewById(R.id.DIGLOG_Img_ncc);
        btnthem = view.findViewById(R.id.DIALOG_Save_ncc);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhaCungCap nhaCungCapd = new NhaCungCap();
                nhaCungCapd.setHoTen(txtname.getText().toString());
                nhaCungCapd.setDiaChi(txtdiachi.getText().toString());
                nhaCungCapd.setSoDT(txtSdt.getText().toString());
                try {
                    if (daoNhaCungCap.addNhaCungCap(nhaCungCapd)) {
                        Log.e("sssss", "onClick: ");
                        Toast.makeText(ChiTietHoaDonNhapActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        listNcc.clear();
                        daoNhaCungCap = new DaoNhaCungCap();
                        listNcc.addAll(daoNhaCungCap.getAllNhaCung());
                    } else {
                        Toast.makeText(ChiTietHoaDonNhapActivity.this, "khong thanh cong" +
                                "", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });
//
    }

    private void setDataBill() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SanPhamThanhToanAdapter(this, arrSP);
        recyclerView.setAdapter(adapter);

        for (SanPham sanpham : arrSP) {
            tongsoluong += sanpham.getSoLuong();
            tongtienhang += (sanpham.getGiaNhap() * sanpham.getSoLuong());
        }

        //spiner nha cung
        //lay du lieu nha cung
        daoNhaCungCap = new DaoNhaCungCap();
        try {
            listNcc = daoNhaCungCap.getAllNhaCung();
            if (listNcc != null) {
                spinnerNhaCungCap = new Spinner_nhaCungCap(getApplicationContext(), listNcc);
                spinner_ncc.setAdapter(spinnerNhaCungCap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        spinner_ncc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maNCC = listNcc.get(i).getMaNCC();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //so lieu
        tv_ngay.setText(simpleDateFormat.format(new Date()));
        tv_tongsoluong.setText(tongsoluong + "");
        tv_tongtienhang.setText(tongtienhang + " đ");
        tv_tongchi.setText(tongtienhang + " đ");

    }
}