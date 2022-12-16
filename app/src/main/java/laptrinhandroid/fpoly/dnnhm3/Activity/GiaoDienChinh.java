package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.Adapter_GDC.AdapterPagerSlideImg;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOBangLuong;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOChamCong;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.DAO.ThongBaoAdminDAO;
import laptrinhandroid.fpoly.dnnhm3.DAO.ThongBaoNVDAO;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.BangLuong;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChamCong;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ThongBaoAdmin;
import laptrinhandroid.fpoly.dnnhm3.XuLiNgay.FormatDay;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.notification.FcmNotificationsSender;
import me.relex.circleindicator.CircleIndicator3;

public class GiaoDienChinh extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    Toolbar toolbar;
    AdapterPagerSlideImg adapterPager;
    ViewPager2 viewPager2;
    CircleIndicator3 indicator3;
    ActionBar actionbar;
    Handler handler = new Handler(Looper.myLooper());
    Intent intent;
    int i = 0;
    public static DAOChamCong daoChamCong = new DAOChamCong();
    public static DAOBangLuong bangLuong = new DAOBangLuong();
    public static DAONhanVien nhanVien1 = new DAONhanVien();
    public static ThongBaoNVDAO thongBaoNVDAO = new ThongBaoNVDAO();
    public static ThongBaoAdminDAO thongBaoAdminDAO = new ThongBaoAdminDAO();
    DrawerLayout drawerLayout;
    FloatingActionButton floatAction;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            i = viewPager2.getCurrentItem();
            if (i == 2) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(i + 1);
            }
        }
    };
    Button btnXacNhan, btnBangXepHang;
    NhanVien nv;
    TextView txtTg, txtMessage, txtWarning, txtSoGioDaLam, txtThuHangHienTai, txtSoTienThuongHienTai, soNoti;
    LinearLayout nhanVien, donHang, baoCao, sanPham, qlKho, cuaHang;
    LinearLayout a, c;
    ImageView imageView;
    BottomNavigationView bottomNavigationView;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_chinh);
        initview();
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.person) {
                Intent intent1 = new Intent(GiaoDienChinh.this, BaoCaoActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GiaoDienChinh.this, nhanVien, "a");
                startActivity(intent1, options.toBundle());
            }
            return true;
        });
        View view = navigationView.getHeaderView(0);
        TextView view1 = view.findViewById(R.id.a);
        TextView view2 = view.findViewById(R.id.b);
        TextView view3 = view.findViewById(R.id.c);
        ImageView view4 = view.findViewById(R.id.d);
        Button button = findViewById(R.id.btnBangXepHang);

        try {
            nv = nhanVien1.getListNhanVien(intent.getIntExtra("NV", 0));
            nhanVien.setOnClickListener(v -> {
                if (nv != null) {
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.putExtra("NV", nv.getMaNv());
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                    startActivity(intent1, options.toBundle());
                } else {
                    Intent intent1 = new Intent(this, QuanLiNhanVien.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                    startActivity(intent1, options.toBundle());
                }
            });
            button.setOnClickListener(view5 -> {
                Intent intent1 = new Intent(this, XepLoai.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                startActivity(intent1, options.toBundle());
            });
            navigationView.setNavigationItemSelectedListener(this);
            if (nv != null) {
                view4.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nv.getAnh()));
                view1.setText(nv.getHoTen() + "");
                view2.setText(nv.getSoDT() + "");
                view3.setText(nv.getEmail() + "");
                soNoti.setText(thongBaoNVDAO.getCountThongBaoNhanVien(nv.getMaNv()) + "");
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(GiaoDienChinh.this, ThongBao.class);
                        intent.putExtra("nv", nv.getMaNv());
                        startActivity(intent);
                    }
                });

                List<ChamCong> chamCongs = daoChamCong.getListChamCong(1, FormatDay.calendarDay().getYear() + "-" + FormatDay.calendarDay().getMonth());
                int i = getTop();
                if (chamCongs != null) {
                    long soH = 0;
                    for (ChamCong chamCong : chamCongs) {
                        if (chamCong.getGioKetThuc() != null && chamCong.getXacNhanChamCong() == 1) {
                            soH += chamCong.getGioKetThuc().getTime() - chamCong.getGioBatDau().getTime();
                        }
                    }
                    float h = (float) soH / (1000 * 60 * 60);
                    txtSoGioDaLam.setText(String.format("%.2g", h) + " giờ");
                    txtThuHangHienTai.setText("Top " + i);
                    txtSoTienThuongHienTai.setText(bangLuong.getBangLuong(nv.getMaNv()).getThuong() + "  VND");
                }
            } else {
                view1.setText("Admin" + "");
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                a.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                soNoti.setText(thongBaoAdminDAO.getCountThongBaoAdmin() + "");
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(GiaoDienChinh.this, ThongBao.class);
                        startActivity(intent);
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("sssssc", "onCreate: " + e.getMessage() + nv);
        }
        setAdaperViewPager();
        //Chạy chữ
        //runLetters();
        donHang.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MainActivityhoadon.class);
            intent1.putExtra("ch", 1);
            if (nv != null) {
                intent1.putExtra("maNV", nv.getMaNv());
            } else {
                intent1.putExtra("maNV", 28);
            }
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
            startActivity(intent1, options.toBundle());
        });
        qlKho.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, QuanLyKho.class);
            if (nv != null) {
                intent1.putExtra("maNV", nv.getMaNv());
            } else {
                intent1.putExtra("maNV", 28);
            }
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
            startActivity(intent1, options.toBundle());
        });
        baoCao.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, BaoCaoActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
            startActivity(intent1, options.toBundle());
        });
        sanPham.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, SanPhamActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
            startActivity(intent1, options.toBundle());
        });
        cuaHang.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, hoadon11111.class);
            if (nv != null) {
                intent1.putExtra("maNV", nv.getMaNv());
            } else {
                intent1.putExtra("maNV", 28);
            }
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
            startActivity(intent1, options.toBundle());
        });
        floatAction.setOnClickListener(viewe -> {
            if (nv == null) {
                Toast.makeText(this, "Chấm công chỉ dành cho nhân viên", Toast.LENGTH_SHORT).show();
            } else {
                Dialog dialog = createDialog();
                btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
                txtTg = dialog.findViewById(R.id.txtTg);
                txtMessage = dialog.findViewById(R.id.txtMessage);
                txtWarning = dialog.findViewById(R.id.txtWarning);
                long currentTime = FormatDay.getCurrentDateUtil().getTime();
                long gioBatDau = FormatDay.getBatDauLam();
                long gioKetThuc = FormatDay.getKetThucHLam();
                long conLai = gioBatDau - currentTime;
                CountDownTimer demNguocGioBatDauLam;
                CountDownTimer demNguocGioKetThucLam = demNguocGioKetThucLam(txtTg, txtMessage, gioKetThuc - currentTime);
                try {
                    ChamCong chamCong = daoChamCong.getChamCong(nv.getMaNv());
                    //Đếm thời gian bắt đầu vào làm
                    if (currentTime >= (gioBatDau - (30 * 60 * 1000)) && currentTime <= gioBatDau) {
                        txtMessage.setText("Bắt đầu vào làm sau");
                        demNguocGioBatDauLam = demNguocGioBatDauLam(txtTg, txtMessage, conLai).start();
                        demNguocGioBatDauLam.start();
                        if (chamCong != null) {
                            btnXacNhan.setText("Chấm công kết thúc ");
                            khiChamCongVaoLam(chamCong.getGioBatDau());
                            checkXacNhanCong(chamCong);
                        } else {
                            btnXacNhan.setText("Bạn chưa chấm công vào làm");
                        }
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date(System.currentTimeMillis()));
                        //Khi bắt đầu giờ làm
                        if ((calendar.get(Calendar.HOUR_OF_DAY) >= 7 && calendar.get(Calendar.MINUTE) >= 30) || calendar.get(Calendar.HOUR_OF_DAY) >= 8) {
                            //Khi chấm công vào làm thành công
                            if (currentTime <= (gioKetThuc + (15 * 60 * 1000))) {
                                demNguocGioKetThucLam.start();
                                txtMessage.setText("Hết giờ làm sau ");
                                if (chamCong != null) {
                                    btnXacNhan.setText("Chấm công kết thúc ");
                                    khiChamCongVaoLam(chamCong.getGioBatDau());
                                    checkXacNhanCong(chamCong);
                                } else {
                                    btnXacNhan.setText("Chấm công vào làm");
                                }
                            } else {
                                if (chamCong != null) {
                                    checkXacNhanCong(chamCong);
                                } else {
                                    hideView();
                                    txtMessage.setText("Không xác nhận công do chưa chấm công kết thúc");
                                }
                            }
                        } else {
                            hideView();
                            txtMessage.setText("Chưa đến giờ làm");
                        }
                    }
                } catch (SQLException e) {
                    Log.d("ccccccc", "onCreate: " + e.getMessage());
                    hideView();
                    txtMessage.setText("Vui lòng kiểm tra lại mạng");
                }

                dialog.show();
                btnXacNhan.setOnClickListener(view11 -> {
                    ChamCong chamCong = null;
                    try {
                        chamCong = daoChamCong.getChamCong(nv.getMaNv());
                        Toast.makeText(this, chamCong + "", Toast.LENGTH_SHORT).show();
                        if (chamCong != null) {
                            chamCong.setGioKetThuc(new Time(System.currentTimeMillis()));
                            if (daoChamCong.updateChamCong(chamCong)) {
                                hideView();
                                txtMessage.setText("Đang chờ xác nhận");
                                String tokenAdmin = nhanVien1.getTokenAdmin();
                                new FcmNotificationsSender(tokenAdmin, "Nhân viên " + nv.getHoTen() + " yêu cầu xác nhận công", "Nhấn vào đây để đi đến xác nhận", GiaoDienChinh.this).SendNotifications();
                                BangLuong bangLuong1 = bangLuong.getBangLuong(nv.getMaNv());
                                switch (getTop()) {
                                    case 1:
                                        bangLuong1.setThuong(bangLuong1.getThuong() + 50000);
                                        break;
                                    case 2:
                                        bangLuong1.setThuong(bangLuong1.getThuong() + 30000);

                                        break;
                                    case 3:
                                        bangLuong1.setThuong(bangLuong1.getThuong() + 20000);

                                        break;
                                }
                                bangLuong.updateBangLuong(bangLuong1);
                                new ThongBaoAdminDAO().addThongBaoAdmin(new ThongBaoAdmin(nv.getMaNv(), chamCong.getNgay().toString(), false));
                            }
                        } else {
                            daoChamCong.addChamCong(new ChamCong(nv.getMaNv(), new Time(System.currentTimeMillis()), null, new java.sql.Date(System.currentTimeMillis()), 0));
                            bangLuong.addBangLuong(new BangLuong(nv.getMaNv(), 5000, 0, 0, FormatDay.calendarDay().getYear() + "-" + (FormatDay.calendarDay().getMonth())));
                            khiChamCongVaoLam(new Time(System.currentTimeMillis()));
                            btnXacNhan.setText("Chấm công kết thúc");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.d("sssss1", "onCreate: " + e.toString());
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.Search);
        try {
            if (nv != null) {
                soNoti.setText(thongBaoNVDAO.getCountThongBaoNhanVien(nv.getMaNv()) + "");
            } else {
                soNoti.setText(thongBaoAdminDAO.getCountThongBaoAdmin() + "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private int getTop() throws SQLException {
        HashMap<Integer, Integer> hashMap = GiaoDienChinh.bangLuong.getTongSoGioLamNhanVien();
        int i = 1;
        for (Map.Entry<Integer, Integer> hashMap1 : hashMap.entrySet()) {
            Log.d("ssdaa", "getTop: " + hashMap1.getKey());
            if (hashMap1.getKey() == nv.getMaNv()) {
                return i;
            }
            i++;
        }
        return 0;
    }

    private void initview() {
        nhanVien = findViewById(R.id.nhanVien);
        donHang = findViewById(R.id.donHang);
        baoCao = findViewById(R.id.baoCao);
        sanPham = findViewById(R.id.sanPham);
        qlKho = findViewById(R.id.btnstartKho);
        cuaHang = findViewById(R.id.cuaHang);
        indicator3 = findViewById(R.id.circleIndicator3);
        toolbar = findViewById(R.id.toolBar);
        viewPager2 = findViewById(R.id.viewPager2);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        floatAction = findViewById(R.id.floatAction);
        txtSoGioDaLam = findViewById(R.id.txtSoGioDaLam);
        txtThuHangHienTai = findViewById(R.id.txtThuHangHienTai);
        txtSoTienThuongHienTai = findViewById(R.id.txtSoTienThuongHienTai);
        btnBangXepHang = findViewById(R.id.btnBangXepHang);
        a = findViewById(R.id.a);
        c = findViewById(R.id.c);
        imageView = findViewById(R.id.thongBao);
        soNoti = findViewById(R.id.soNoti);

        setToolBar();
        intent = getIntent();
        adapterPager = new AdapterPagerSlideImg(this);
        navigationView.setItemIconTintList(null);

    }

    private void checkXacNhanCong(ChamCong chamCong) {
        if (chamCong.getGioKetThuc() != null) {
            hideView();
            Log.d("ssssssss", "checkXacNhanCong: 0k3" + chamCong);

            if (chamCong.getXacNhanChamCong() == 0) {
                txtMessage.setText("Đang chờ xác nhận");

            } else if (chamCong.getXacNhanChamCong() == 1) {

                txtMessage.setText("Đã xác nhận thành công");
            } else if (chamCong.getXacNhanChamCong() == 2) {

                txtMessage.setText("Không xác nhận công");
            }
        }
    }

    private void hideView() {
        txtWarning.setVisibility(View.GONE);
        txtTg.setVisibility(View.GONE);
        btnXacNhan.setVisibility(View.GONE);
    }


    @SuppressLint({"SetTextI18n", "ResourceType", "UseCompatTextViewDrawableApis"})
    private void khiChamCongVaoLam(Time gioBatDau) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        txtWarning.setText("Bạn đã chấm công vào lúc " + simpleDateFormat.format(gioBatDau));
        txtWarning.setTextColor(Color.parseColor("#269A0A"));
        txtWarning.setBackgroundColor(Color.parseColor("#81B7F3B9"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            txtWarning.setCompoundDrawableTintList(getColorStateList(R.color.teal_200));
        }
    }


    private CountDownTimer demNguocGioKetThucLam(TextView txtTg, TextView txtMessage, long conLai) {


        return new CountDownTimer(conLai, 60000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                long differenceInMilliSeconds = Math.abs(l);
                long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
                long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;

                txtTg.setText(differenceInHours + ":" + differenceInMinutes);
            }

            @Override
            public void onFinish() {

            }
        };
    }

    private CountDownTimer demNguocGioBatDauLam(TextView txtTg, TextView txtMessage, long conLai) {

        return new CountDownTimer(conLai, 60000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                txtMessage.setText("Bắt đầu làm sau");
                txtTg.setText(TimeUnit.MILLISECONDS.toMinutes(l) + " phút");
            }

            @Override
            public void onFinish() {


            }
        };
    }


    private Dialog createDialog() {
        Dialog dialog = new Dialog(GiaoDienChinh.this);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialog_cham_cong);
        return dialog;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        return true;
    }


    private void setAdaperViewPager() {
        viewPager2.setOffscreenPageLimit(1);//Dat so luong trang giu lai o hai ben
        //Hiển thị view ẩn
        viewPager2.setClipToPadding(false);//
        viewPager2.setClipChildren(false);//
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setAdapter(adapterPager);
//      Lắng nghe Thay đổi
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int i) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3500);
            }

        });
        indicator3.setViewPager(viewPager2);
    }

    @SuppressLint("RestrictedApi")
    public void setToolBar() {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_home_24);//set icon menu
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.donHang:
                Intent i1ntent1 = new Intent(this, MainActivityhoadon.class);
                i1ntent1.putExtra("ch", 1);
                if (nv != null) {
                    i1ntent1.putExtra("maNV", nv.getMaNv());
                } else {
                    i1ntent1.putExtra("maNV", 28);
                }
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                startActivity(i1ntent1, options.toBundle());
                break;
            case R.id.sanPham:
                Intent intent11 = new Intent(this, SanPhamActivity.class);
                ActivityOptions options11 = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                startActivity(intent11, options11.toBundle());
                break;
            case R.id.qlKho:
                Intent intent12 = new Intent(this, QuanLyKho.class);
                if (nv != null) {
                    intent12.putExtra("maNV", nv.getMaNv());
                } else {
                    intent12.putExtra("maNV", 28);
                }
                ActivityOptions options2 = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                startActivity(intent12, options2.toBundle());
                break;
            case R.id.thongKe:
                Intent intent13 = new Intent(this, BaoCaoActivity.class);
                ActivityOptions options3 = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                startActivity(intent13, options3.toBundle());
                break;
            case R.id.qlNhanVien:
                if (nv != null) {
                    Intent intent14 = new Intent(this, MainActivity.class);
                    intent14.putExtra("NV", nv.getMaNv());
                    ActivityOptions options4 = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                    startActivity(intent14, options4.toBundle());
                } else {
                    Intent intent15 = new Intent(this, QuanLiNhanVien.class);

                    ActivityOptions options5 = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                    startActivity(intent15, options5.toBundle());
                }
                break;
            case R.id.doiMK:
                startActivity(new Intent(GiaoDienChinh.this, DoiMatKhauActivity.class));
                break;
            case R.id.logout:
                Intent intent15 = new Intent(this, login.class);
                ActivityOptions options5 = ActivityOptions.makeSceneTransitionAnimation(this, nhanVien, "a");
                startActivity(intent15, options5.toBundle());
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }

}