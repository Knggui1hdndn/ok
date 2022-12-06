package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Date;
import java.text.SimpleDateFormat;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;

public class ShowChiTiet extends AppCompatActivity {
    private Toolbar toolbar;
    TextInputLayout hoTen, diaChi, soDT, email, gioiTinh, ngaySinh, ngayBD, matKhau;
    AutoCompleteTextView autoCompleteTextView;
    NhanVien nhanVien;
    int i = 0;
    ArrayAdapter<String> arrayAdapter;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    MenuItem menuItem;
    SpannableString s;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_chi_tiet);
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
        Intent intent = getIntent();
        nhanVien = (NhanVien) intent.getSerializableExtra("NV");
        hoTen.getEditText().setText(nhanVien.getHoTen());
        matKhau.getEditText().setText(nhanVien.getPasswords());
        diaChi.getEditText().setText(nhanVien.getDiaChi());
        soDT.getEditText().setText(nhanVien.getSoDT());
        gioiTinh.getEditText().setText(nhanVien.getGioiTinh());
        ngaySinh.getEditText().setText(nhanVien.getNgaySinh().toString());
        ngayBD.getEditText().setText(nhanVien.getNgayBD().toString());
        email.getEditText().setText(nhanVien.getEmail());
        checkText(hoTen);
        checkText(diaChi);
        checkText(soDT);
        checkText(email);
        checkText(gioiTinh);
        checkText(ngaySinh);
        checkText(ngayBD);
        checkText(matKhau);
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
    }
    private void setDate(TextInputLayout n) {
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(ShowChiTiet.this);
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
                     s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    menuItem.setEnabled(false);
                    menuItem.setTitle(s);
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
                    if(b[0]){
                        s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                        menuItem.setEnabled(true);
                        menuItem.setTitle(s);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        menuItem = menu.getItem(0);
        menuItem.setEnabled(false);
        s = new SpannableString("Lưu");
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        menuItem.setEnabled(false);
        menuItem.setTitle(s);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (checkText(hoTen) && checkText(soDT) && checkText(diaChi) && checkText(email) &&
                    checkText(gioiTinh) && checkText(ngaySinh) && checkText(ngayBD) && checkText(matKhau)) {
                Intent intent1 = new Intent();
                intent1.setAction("set");
                intent1.putExtra("NV",nhanVien);
                sendBroadcast(intent1);
                finish();

            }
        } else {
            finish();
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
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);//set icon tren toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);//set icon menu
    }
}