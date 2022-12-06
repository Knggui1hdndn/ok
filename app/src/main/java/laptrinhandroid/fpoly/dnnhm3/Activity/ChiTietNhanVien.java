package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.textview.MaterialTextView;

import java.sql.SQLException;

import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.BottomSheetSelectImg;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.setImg;

public class ChiTietNhanVien extends AppCompatActivity implements setImg {
    private PhotoView avatar, imgCCCD, imgTATS;
    private TextView txtSelectImg, txtSelectCCCD, txtSelectTATS, txtChiTiet;
    private MaterialTextView hoTen, diaChi, soDT, email, gioiTinh, ngaySinh, ngayBD;
    private Toolbar toolbar;
    Intent intent;
    NhanVien nhanVien;
    int i = 0;
    SpannableString s;
    MenuItem menuItem;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nhan_vien);
        avatar = findViewById(R.id.avatar);
        imgCCCD = findViewById(R.id.imgCCCD);
        imgTATS = findViewById(R.id.imgTATS);
        txtSelectImg = findViewById(R.id.txtSelectImg);
        txtSelectCCCD = findViewById(R.id.txtSelectCCCD);
        txtSelectTATS = findViewById(R.id.txtSelectTATS);
        txtChiTiet = findViewById(R.id.txtChiTiet);
        hoTen = findViewById(R.id.hoTen);
        diaChi = findViewById(R.id.diaChi);
        soDT = findViewById(R.id.soDT);
        email = findViewById(R.id.email);
        gioiTinh = findViewById(R.id.gioiTinh);
        ngaySinh = findViewById(R.id.ngaySinh);
        toolbar = findViewById(R.id.toolBar);
        ngayBD = findViewById(R.id.ngayBD);
        intent = getIntent();
        try {
            nhanVien = GiaoDienChinh.nhanVien1.getListNhanVien(intent.getIntExtra("maNV", 0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setToolBar();
        imgCCCD.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnhPhoToCC()));
        imgTATS.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnhXNKcoTATS()));
        avatar.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnh()));
        setView(nhanVien);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    NhanVien nhanVien1 = (NhanVien) intent.getSerializableExtra("NV");
                    nhanVien.setHoTen(nhanVien1.getHoTen());
                    nhanVien.setDiaChi(nhanVien1.getDiaChi());
                    nhanVien.setEmail(nhanVien1.getEmail());
                    nhanVien.setNgayBD(nhanVien1.getNgayBD());
                    nhanVien.setGioiTinh(nhanVien1.getGioiTinh());
                    nhanVien.setNgaySinh(nhanVien1.getNgaySinh());
                    nhanVien.setPasswords(nhanVien1.getPasswords());
                    nhanVien.setSoDT(nhanVien1.getSoDT());
                     setView(nhanVien1);
                    s = new SpannableString("Lưu");
                    s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                    menuItem.setTitle(s);
                    menuItem.setEnabled(true);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("set");
        registerReceiver(broadcastReceiver, intentFilter);
        txtSelectImg.setOnClickListener(view -> {
            show();
            i = 1;
        });
        txtSelectCCCD.setOnClickListener(view -> {
            show();
            i = 2;
        });
        txtSelectTATS.setOnClickListener(view -> {
            show();
            i = 3;
        });
        txtChiTiet.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, ShowChiTiet.class);
            NhanVien nhanVien1 = new NhanVien();
            nhanVien1.setHoTen(nhanVien.getHoTen());
            nhanVien1.setDiaChi(nhanVien.getDiaChi());
            nhanVien1.setEmail(nhanVien.getEmail());
            nhanVien1.setNgayBD(nhanVien.getNgayBD());
            nhanVien1.setGioiTinh(nhanVien.getGioiTinh());
            nhanVien1.setNgaySinh(nhanVien.getNgaySinh());
            nhanVien1.setPasswords(nhanVien.getPasswords());
            nhanVien1.setSoDT(nhanVien.getSoDT());
            intent1.putExtra("NV", nhanVien1);
            startActivity(intent1);
        });


    }



    private void setView(NhanVien nhanVien) {

        hoTen.setText("Tên " + nhanVien.getHoTen());
        diaChi.setText("Sống tại " + nhanVien.getDiaChi());
        soDT.setText("Số điện thoại " + nhanVien.getSoDT());
        gioiTinh.setText("Giới tính " + nhanVien.getGioiTinh());
        ngaySinh.setText("Ngày sinh " + nhanVien.getNgaySinh());
        ngayBD.setText("Ngày bắt đầu làm " + nhanVien.getNgayBD());
        email.setText("Địa chỉ email " + nhanVien.getEmail());
    }

    private void show() {
        BottomSheetSelectImg bottomSheetSelectImg = new BottomSheetSelectImg(ChiTietNhanVien.this );
        bottomSheetSelectImg.show(getSupportFragmentManager(), "sss");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent gallery = new Intent();
            gallery.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(gallery, 1);
        } else {
            Toast.makeText(this, "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            s = new SpannableString("Lưu");
            s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
            menuItem.setTitle(s);
            menuItem.setEnabled(true);
            if (requestCode == 0) {
                switch (i) {
                    case 1:
                        avatar.setImageURI(data.getData());
                        nhanVien.setAnh(ConvertImg.convertBitmapToBaseString(setImg(avatar)));
                        break;
                    case 2:
                        imgCCCD.setImageURI(data.getData());
                        nhanVien.setAnhPhoToCC(ConvertImg.convertBitmapToBaseString(setImg(imgCCCD)));
                        break;

                    case 3:
                        imgTATS.setImageURI(data.getData());
                        nhanVien.setAnhXNKcoTATS(ConvertImg.convertBitmapToBaseString(setImg(imgTATS)));
                        break;
                }
            } else if (requestCode == 1) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                switch (i) {
                    case 1:
                        avatar.setImageBitmap(bitmap);
                        nhanVien.setAnh(ConvertImg.convertBitmapToBaseString(bitmap));
                        break;
                    case 2:
                        imgCCCD.setImageBitmap(bitmap);
                        nhanVien.setAnhPhoToCC(ConvertImg.convertBitmapToBaseString(bitmap));
                        break;
                    case 3:
                        imgTATS.setImageBitmap(bitmap);
                        nhanVien.setAnhXNKcoTATS(ConvertImg.convertBitmapToBaseString(bitmap));
                        break;
                }
            }
        }
    }

    private Bitmap setImg(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        return drawable.getBitmap();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu1, menu);
        s = new SpannableString("Lưu");
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        menu.getItem(0).setTitle(s);
        menu.getItem(0).setEnabled(false);
        menuItem = menu.getItem(0);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Xác nhận lưu nhân viên");
            builder.setTitle("Thông báo").setIcon(R.drawable.bell).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("sssssssss", "onClick: "+nhanVien);
                    try {
                        if (GiaoDienChinh.nhanVien1.updateNhanVien(nhanVien)) {
                            Toast.makeText(ChiTietNhanVien.this, "Update thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChiTietNhanVien.this, "Update thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();

                        Log.d("ssssssssss", "onClick: "+e.getMessage());
                    }
                }
            }).show();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @SuppressLint("RestrictedApi")
    public void setToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(nhanVien.getHoTen());
        actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);//set icon menu
    }


    @Override
    public void check(int id) {
        if (id == R.id.btnLibrary) {
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(gallery, 0);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent gallery = new Intent();
                    gallery.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(gallery, 1);
                } else {
                    ActivityCompat.requestPermissions(ChiTietNhanVien.this,
                            new String[]{Manifest.permission.CAMERA}, 2);
                }
            }
        }
    }
}