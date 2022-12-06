package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Date;
import java.sql.SQLException;

import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.BottomSheetSelectImg;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.setImg;

public class AddNhanVien extends AppCompatActivity implements setImg {
    private PhotoView avatar, imgCCCD, imgTATS;
    private TextView txtSelectImg, txtSelectCCCD, txtSelectTATS;
    private Toolbar toolbar;
    TextInputLayout hoTen, diaChi, soDT, email, gioiTinh, ngaySinh, ngayBD, matKhau;
    AutoCompleteTextView autoCompleteTextView;
    NhanVien nhanVien;
    int i = 0;
    ArrayAdapter<String> arrayAdapter;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_nhan_vien);
        avatar = findViewById(R.id.avatar);
        imgCCCD = findViewById(R.id.imgCCCD);
        imgTATS = findViewById(R.id.imgTATS);
        txtSelectImg = findViewById(R.id.txtSelectImg);
        txtSelectCCCD = findViewById(R.id.txtSelectCCCD);
        txtSelectTATS = findViewById(R.id.txtSelectTATS);

        hoTen = findViewById(R.id.hoTen);
        diaChi = findViewById(R.id.songTai);
        soDT = findViewById(R.id.sdt);
        email = findViewById(R.id.email);
        gioiTinh = findViewById(R.id.gioiTinh);
        ngaySinh = findViewById(R.id.ngaySinh);
        toolbar = findViewById(R.id.toolBar);
        ngayBD = findViewById(R.id.ngayBD);
        matKhau = findViewById(R.id.matKhau);
        autoCompleteTextView = findViewById(R.id.gt);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"Nam", "Nữ"});
        autoCompleteTextView.setAdapter(arrayAdapter);
        setToolBar();
        ngaySinh.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(ngaySinh);
            }
        });
        ngayBD.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(ngayBD);
            }
        });
        nhanVien = new NhanVien();
        txtSelectImg.setOnClickListener(view -> {
            show();
            i = 1;
        });
        txtSelectCCCD.setOnClickListener(view -> {
            show();
            i = 2;
            //a
        });
        txtSelectTATS.setOnClickListener(view -> {
            show();
            i = 3;
        });
        checkText(hoTen);
        checkText(diaChi);
        checkText(soDT);
        checkText(email);
        checkText(gioiTinh);
        checkText(ngaySinh);
        checkText(ngayBD);
        checkText(matKhau);

    }

    private void setDate(TextInputLayout n) {
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(AddNhanVien.this);
        View view = getLayoutInflater().inflate(R.layout.ngay, null);
        dialog1.setView(view);
        DatePicker datePicker = view.findViewById(R.id.birth_day);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener((datePicker1, i, i1, i2) -> {
                String s = i + "-" + (i1+1) + "-" + i2;
                try {
                    if (n.getEditText().getHint().equals("Ngày sinh(yyyy-MM-dd)")) {
                        ngaySinh.getEditText().setText(s);
                        nhanVien.setNgaySinh(Date.valueOf(s));
                    } else {
                        ngayBD.getEditText().setText(s);
                        nhanVien.setNgayBD(Date.valueOf(s));
                    }
                } catch (Exception e) {
                    Log.d("ssssssssss", "onDateChanged: " + e.getMessage());
                }
            });
        }
        dialog1.show();
    }

    public boolean checkText(TextInputLayout inputLayout) {

        boolean[] b = {true};

        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence.toString())) {
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError("Không bỏ trống " + inputLayout.getEditText().getHint());
                    b[0] = false;
                } else {
                    inputLayout.setErrorEnabled(false);
                    b[0] = true;
                    switch (inputLayout.getEditText().getHint().toString()) {
                        case "Họ tên":
                            nhanVien.setHoTen(charSequence.toString());
                            break;
                        case "Sống tại":
                            nhanVien.setDiaChi(charSequence.toString());
                            break;
                        case "Số điện thoại":
                            nhanVien.setSoDT(charSequence.toString());
                            break;
                        case "Giới tính":
                            nhanVien.setGioiTinh(charSequence.toString());
                            break;
                        case "Mật khẩu":
                            nhanVien.setPasswords(charSequence.toString());
                            break;
                        case "Email":
                            if (!Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) {
                                inputLayout.setErrorEnabled(true);
                                inputLayout.setError("Email không đúng định dạng");
                                b[0] = false;
                            } else {
                                nhanVien.setEmail(charSequence.toString());
                            }

                            break;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return b[0];
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.save) {
            if (checkText(hoTen) && checkText(diaChi) && checkText(soDT) && checkText(email) &&
                    checkText(gioiTinh) && checkText(ngaySinh) && checkText(ngayBD) && checkText(matKhau)) {
                try {
                    if (new DAONhanVien().addNhanVien(nhanVien)) {
                        recreate();
                        Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Không thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    Log.d("sssssssssss", "onOptionsItemSelected: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }else {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    private void show() {
        BottomSheetSelectImg bottomSheetSelectImg = new BottomSheetSelectImg(AddNhanVien.this );
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

    @SuppressLint("RestrictedApi")
    public void setToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Thêm nhân viên");
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
                    ActivityCompat.requestPermissions(AddNhanVien.this,
                            new String[]{Manifest.permission.CAMERA}, 2);
                }
            }
        }
    }
}