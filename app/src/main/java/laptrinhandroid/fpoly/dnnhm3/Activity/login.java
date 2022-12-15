package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.sql.SQLException;

import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = findViewById(R.id.btnLogin);
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        inputEmail.setText("haidzkkk.gamil.com");
        inputPassword.setText("thanhhai");

Dialog dialog=new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
         dialog.setContentView(R.layout.item_progress);


        btnLogin.setOnClickListener(view -> {
            dialog.show();
            FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!TextUtils.isEmpty(inputEmail.getText().toString()) && !TextUtils.isEmpty(inputPassword.getText().toString())) {
                        try {
                            NhanVien nhanVien = new DAONhanVien().checkLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
                            Boolean admin = new DAONhanVien().checkLoginAdmin(inputEmail.getText().toString(), inputPassword.getText().toString());
                            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (task.isSuccessful()) {
                                        if (admin) {
                                            try {
                                                if (new DAONhanVien().updateAdmin(inputEmail.getText().toString(), task.getResult())) {
                                                    Intent intent = new Intent(login.this, GiaoDienChinh.class);
                                                    // lưu dữ liệu tý
                                                    SharedPreferences sharedPreferences = getSharedPreferences("thongtin", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("gmail", inputEmail.getText().toString());
                                                    editor.putString("pass", inputPassword.getText().toString());
                                                    editor.commit();
                                                    startActivity(intent);
                                                }
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                                Log.d("sssssss", "onComplete:1" + e.getMessage());
                                            }
                                        } else {
                                            if (nhanVien != null) {
                                                nhanVien.setToken(task.getResult());
                                                try {
                                                    if (new DAONhanVien().updateNhanVien(nhanVien)) {
                                                        Intent intent = new Intent(login.this, GiaoDienChinh.class);
                                                        intent.putExtra("NV", nhanVien.getMaNv());
                                                        // lưu dữ liệu tý
                                                        SharedPreferences sharedPreferences = getSharedPreferences("thongtin", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("gmail", inputEmail.getText().toString());
                                                        editor.putString("pass", inputPassword.getText().toString());
                                                        editor.commit();
                                                        startActivity(intent);
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                inputEmail.setError("Sai mật khẩu hoặc email");
                                                inputPassword.setError("Sai mật khẩu hoặc email");
                                                dialog.cancel();
                                             }
                                        }
                                    }
                                }
                            });
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        });
    }

    @Override
    protected void onResume() {

        super.onResume();

    }
//    public class CustomProgress extends Dialog {
//
//        public CustomProgress(Context context) {
//            super(context);
//
//            View view = LayoutInflater.from(context).inflate(R.layout.item_progress, null);
//            setContentView(view);
//        }
//    }
}