package laptrinhandroid.fpoly.dnnhm3.Activity;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdpaterNhanVien.AdapterPagerNhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.BangLuong;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChamCong;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.XuLiNgay.FormatDay;


public class MainActivity extends AppCompatActivity {


    List<CalendarDay> list0;
    List<CalendarDay> list1;
    List<CalendarDay> list2;
    TabLayout layout;
    ViewPager2 viewPager2;
    MaterialCalendarView calendarView;
    int nhanVien;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);
        setToolBar();
        Intent intent = getIntent();
        nhanVien = intent.getIntExtra("NV", 0);
         viewPager2.setAdapter(new AdapterPagerNhanVien(this, nhanVien));
        new TabLayoutMediator(layout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Bảng công");
                } else {
                    tab.setText("Bảng lương");
                }
            }
        }).attach();

    }

    @SuppressLint("RestrictedApi")
    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);//set icon menu
    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            // imageView.setImageBitmap(ConvertImg.convertBaseStringToBitmap(ConvertImg.convertBitmapToBaseString((Bitmap) result.getData().getExtras().get("data"))));

        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_nhan_vien, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.ungLuong) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.getWindow().setGravity(Gravity.TOP);
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(R.layout.ung_luong);
            TextView hanMuc = dialog.findViewById(R.id.hanMuc);
            TextView soTien = dialog.findViewById(R.id.soTien);
            TextView soTienDaUng = dialog.findViewById(R.id.soTienDaUng);
            MaterialButton ung = dialog.findViewById(R.id.ung);
            SeekBar snackbar = dialog.findViewById(R.id.seekBar);
            float soH = 0f;
            try {
                Calendar calendar = Calendar.getInstance();
                float hChuNhat = 0;

                CalendarDay calendar1 = FormatDay.calendarDay();
                int tháng = calendar1.getMonth();
                String thang;
                if (tháng < 10) {
                    thang = "0" + tháng;
                } else {
                    thang = String.valueOf(tháng);
                }
                List<BangLuong> bangLuong = (List<BangLuong>) GiaoDienChinh.bangLuong.getListBangLuong(nhanVien,calendar1.getYear() + "-" + thang);
                int[] tien = new int[1];
                tien[0]=0;
                double hanMucc=0;
                if(bangLuong.size()>0){
                   for (ChamCong chamCong : GiaoDienChinh.daoChamCong.getListChamCong(nhanVien, calendar1.getYear() + "-" + thang)) {
                       if (chamCong.getXacNhanChamCong() == 1) {
                           soH += chamCong.getGioKetThuc().getTime() - chamCong.getGioBatDau().getTime();
                           calendar.setTime(chamCong.getNgay());
                           if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                               hChuNhat += (float) (chamCong.getGioKetThuc().getTime() - chamCong.getGioBatDau().getTime()) / (1000 * 60 * 60);
                           }
                       }
                   }

                   float h = (float) soH / (1000 * 60 * 60);
                   float v = bangLuong.get(bangLuong.size()-1).getLuongCB();
                   float congMotNgay = v / 26;
                   float congMotGio = congMotNgay / 8;
                   float tongLuong = ((h - hChuNhat) + (hChuNhat * 2)) * congMotGio;
                     hanMucc = ((tongLuong - bangLuong.get(bangLuong.size()-1).getUngLuong() + bangLuong.get(bangLuong.size()-1).getThuong()) * 70) / 100;
                   hanMuc.setText((int) hanMucc + " ₫");
                   snackbar.setProgress(100);
                   soTienDaUng.setText("Đã ứng :" + bangLuong.get(bangLuong.size()-1).getUngLuong() + " đ");
                    double finalHanMucc = hanMucc;
                    snackbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                       @Override
                       public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                           soTien.setText(((int) finalHanMucc * i) / 100 + " đ");
                           tien[0] = ((int) finalHanMucc * i) / 100;
                       }

                       @Override
                       public void onStartTrackingTouch(SeekBar seekBar) {

                       }

                       @Override
                       public void onStopTrackingTouch(SeekBar seekBar) {

                       }
                   });
               }
                double finalHanMucc1 = hanMucc;
                ung.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Không đủ điều kiện để ứng lương", Toast.LENGTH_SHORT).show();

                        if (finalHanMucc1 < 200000) {
                            Toast.makeText(MainActivity.this, "Không đủ điều kiện để ứng lương", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                BangLuong bangLuong1 = GiaoDienChinh.bangLuong.getBangLuong(nhanVien);
                                bangLuong1.setUngLuong(tien[0] + bangLuong1.getUngLuong());
                                GiaoDienChinh.bangLuong.updateBangLuong(bangLuong1 );

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("sddddđ", "onOptionsItemSelected: "+e.getMessage());            }
            dialog.show();
        } else {

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
