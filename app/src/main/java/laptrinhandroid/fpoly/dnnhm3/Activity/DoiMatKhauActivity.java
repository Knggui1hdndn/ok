package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.SQLException;

import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;

public class DoiMatKhauActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputLayout tilmkcu, tilmkmoi1, tilmkmoi2;
    TextView tvName;
    ImageView imgAdd;
    Button button;

    NhanVien nhanVien;

    private boolean checkPassCu,checkCuMoi, checkPassMoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        anhxa();

        SharedPreferences sharedPreferences = getSharedPreferences("thongtin", Context.MODE_PRIVATE);
        String gmail = sharedPreferences.getString("gmail", "");
        String pass = sharedPreferences.getString("pass", "");

        DAONhanVien daoNhanVien = new DAONhanVien();
        try {
            nhanVien = daoNhanVien.checkLogin(gmail, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setToolbar();
        listenData();
        setUpData();


        button.setOnClickListener(v -> {
            try {
                checkData();
                    nhanVien.setPasswords(tilmkmoi1.getEditText().getText().toString().trim());
                    if (daoNhanVien.updateNhanVien(nhanVien)) {
                        Toast.makeText(this, "Them thanh cong", Toast.LENGTH_SHORT).show();

                        clearTIL();
                    }else {
                        Toast.makeText(this, "Thai bai", Toast.LENGTH_SHORT).show();
                    }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void checkData() {
        if (checkEmptyTIL(tilmkcu) | checkEmptyTIL(tilmkmoi1) | checkEmptyTIL(tilmkmoi2)) ;
        else {
            String mkcu = tilmkcu.getEditText().getText().toString();
            String mk1 = tilmkmoi1.getEditText().getText().toString();
            String mk2 = tilmkmoi2.getEditText().getText().toString();

            // kiểm tra pass cũ
            if (!nhanVien.getPasswords().equals(mkcu)) {
                tilmkcu.setError("Không trùng mật khẩu");
                checkPassCu = false;
            } else {
                tilmkcu.setError(null);
                checkPassCu = true;
            }

            //check pass cũ giống pass mới
            if (mkcu.equals(mk1)){
                tilmkmoi1.setHelperText("Không trùng mật khẩu");
                tilmkmoi1.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                tilmkmoi1.setError("Không trùng mật khẩu");
                checkCuMoi = false;
            }else {
                tilmkcu.setError(null);
                checkPassCu = true;
            }

            //check pass mới
            if (mk1.length() < 7) {
                tilmkmoi1.setError("Mật khẩu yếu");
                checkPassMoi = false;
            } else {
                tilmkmoi1.setError(null);
                if (mk1.equals(mk2)) {
                    checkPassMoi = true;
                } else {
                    tilmkmoi2.setError("Không trùng mật khẩu");
                    checkPassCu = false;
                }

            }

        }
    }

    private void anhxa() {
        tilmkcu = findViewById(R.id.doimatkhau_tv_mkcu);
        tilmkmoi1 = findViewById(R.id.doimatkhau_tv_mkmoi1);
        tilmkmoi2 = findViewById(R.id.doimatkhau_tv_mkmoi2);
        tvName = findViewById(R.id.doimatkhau_tv_name);
        imgAdd = findViewById(R.id.doimatkhau_img_add);
        toolbar = findViewById(R.id.baoCaoPhantich_toolbar);
        button = findViewById(R.id.doimatkhau_btn_capnhat);
    }

    private void setToolbar() {
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

    private void setUpData(){
        tvName.setText(nhanVien.getHoTen());
        imgAdd.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnh()));
    }

    private void listenData() {
        tilmkcu.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    tilmkcu.setHelperText("Bắt buộc");
                    tilmkcu.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                } else {
                    tilmkcu.setHelperText(null);
                    tilmkcu.setError(null);

                }
            }
        });

        tilmkmoi1.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int color_GreenAqua = getResources().getColor(R.color.teal_200);
                int color_redWhite = getResources().getColor(R.color.red);

                if (s.toString().length() > 15) {
                    tilmkmoi1.setHelperText("Khỏe");
                    tilmkmoi1.setHelperTextColor(ColorStateList.valueOf(color_GreenAqua));
                } else if (s.toString().length() > 7) {
                    tilmkmoi1.setHelperText("Tốt");
                    tilmkmoi1.setHelperTextColor(ColorStateList.valueOf(color_GreenAqua));
                } else {
                    tilmkmoi1.setHelperText("Yếu");
                    tilmkmoi1.setHelperTextColor(ColorStateList.valueOf(color_redWhite));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    tilmkmoi1.setHelperText("Bắt buộc");
                    tilmkmoi1.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                } else {
                    tilmkmoi1.setError(null);
                }
            }
        });

        tilmkmoi2.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(tilmkmoi1.getEditText().getText().toString().trim())) {
                    tilmkmoi2.setHelperText("Mật khẩu không giống");
                    tilmkmoi2.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                } else {
                    tilmkmoi2.setHelperText(null);
                    tilmkmoi2.setError(null);
                }
            }
        });
    }


    private boolean checkEmptyTIL(TextInputLayout textInputLayout) {
        String s = textInputLayout.getEditText().getText().toString();
        if (s.isEmpty()) {
            textInputLayout.setError("Mandatory");
            return true;
        } else {
            textInputLayout.setError(null);
            return false;
        }
    }

    public void clearTIL() {
        checkPassCu = false;
        checkPassMoi = false;
        tilmkcu.getEditText().getText().clear();
        tilmkmoi1.getEditText().getText().clear();
        tilmkmoi2.getEditText().getText().clear();
        tilmkcu.setError(null);
        tilmkmoi1.setError(null);
        tilmkmoi2.setError(null);
        tilmkcu.setHelperText(null);
        tilmkmoi1.setHelperText(null);
        tilmkmoi2.setHelperText(null);
        button.clearFocus();
    }

}