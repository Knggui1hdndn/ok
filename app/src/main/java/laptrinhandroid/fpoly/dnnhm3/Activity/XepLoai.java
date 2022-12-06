package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdpaterNhanVien.AdapterListNhanVien1;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;

public class XepLoai extends AppCompatActivity {
    TextView name, name1, name2, sdt, sdt1, sdt2;
    ImageView img, img1, img2;
    RecyclerView rcy;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xep_loai);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        name = findViewById(R.id.name);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        sdt = findViewById(R.id.sdt);
        sdt1 = findViewById(R.id.sdt1);
        rcy = findViewById(R.id.rcy);
        sdt2 = findViewById(R.id.sdt2);
        img = findViewById(R.id.img);
        setToolBar();
        rcy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        try {
            HashMap<Integer, Integer> hashMap = GiaoDienChinh.bangLuong.getTongSoGioLamNhanVien();
            int i = 0;
            for (Map.Entry<Integer, Integer> hashMap1 : hashMap.entrySet()) {
                NhanVien nhanVien = GiaoDienChinh.nhanVien1.getListNhanVien(hashMap1.getKey());
                 switch (i) {
                    case 0:
                        name.setText(nhanVien.getHoTen());
                        sdt.setText(hashMap1.getValue() +"giờ làm | +50.000 đ");
                        img.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnh()));
                        break;
                    case 1:
                        name1.setText(nhanVien.getHoTen());
                        sdt1.setText(hashMap1.getValue()+"giờ làm | +50.000 đ");
                        img1.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnh()));
                        break;
                    case 2:
                        name2.setText(nhanVien.getHoTen());
                        sdt2.setText(hashMap1.getValue()+"giờ làm | +50.000 đ");
                        img2.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnh()));
                        break;
                }
                i++;
                if (i == 3) {
                    break;
                }
            }
            AdapterListNhanVien1 adapterListNhanVien1 = new AdapterListNhanVien1();
            adapterListNhanVien1.setData(hashMap);
            rcy.setAdapter(adapterListNhanVien1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


     public void setToolBar() {
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
         actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);//set icon menu
    }

}