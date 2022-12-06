package laptrinhandroid.fpoly.dnnhm3.Fragment.NhanVien;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.GiaoDienChinh;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.BangLuong;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChamCong;
import laptrinhandroid.fpoly.dnnhm3.R;

public class FragmentLuong extends Fragment {
    View view;
    TextView txtSoGio, txtNgayThuong, txtChuNhat, txtLuongCb, txtUngLuong, txtSoTienThuong, txtTong;
    Spinner spinner;
    LinearLayout mRelativeLayout;
    int x = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_luong, null);
        txtSoGio = view.findViewById(R.id.txtSoGio);
        txtNgayThuong = view.findViewById(R.id.txtNgayThuong);
        txtLuongCb = view.findViewById(R.id.txtLuongCb);
        txtChuNhat = view.findViewById(R.id.txtChuNhat);
        txtUngLuong = view.findViewById(R.id.txtUngLuong);
        txtSoTienThuong = view.findViewById(R.id.txtSoTienThuong);
        txtTong = view.findViewById(R.id.txtTong);
        spinner = view.findViewById(R.id.spn);
        mRelativeLayout = view.findViewById(R.id.mRelativeLayout);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);
        Bundle bundle = getArguments();
        int nhanVien = bundle.getInt("nv");
        String ad = bundle.getString("ad");
        try {
            List<BangLuong> bangLuong = (List<BangLuong>) GiaoDienChinh.bangLuong.getListBangLuong(nhanVien);
            Log.d("sssddđ", "onCreateView: 0"+bangLuong.size()+"  "+nhanVien);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < bangLuong.size(); i++) {
                list.add("Bảng lương:" + bangLuong.get(i).getNgayThang());
            }
            arrayAdapter.addAll(list);
            spinner.setAdapter(arrayAdapter);
            spinner.setSelection(list.size() - 1);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    float soH = 0f;
                    try {
                        if (ad != null) {
                            mRelativeLayout.setOnClickListener(view13 -> {
                                Dialog dialog = createDialog();
                                TextInputLayout inputLayout = dialog.findViewById(R.id.luong);
                                Button huy = dialog.findViewById(R.id.btn_huy);
                                Button xn = dialog.findViewById(R.id.xacNhan);
                                inputLayout.getEditText().setText(bangLuong.get(i).getLuongCB() + "");
                                huy.setOnClickListener(view12 -> dialog.cancel());
                                xn.setOnClickListener(view1 -> {
                                    if (inputLayout.getEditText().getText().toString().equals("")) {
                                        inputLayout.setErrorEnabled(true);
                                        inputLayout.setError("Không để trống");
                                    } else {
                                        BangLuong bangLuong1 = bangLuong.get(i);
                                        bangLuong1.setLuongCB(Float.parseFloat(inputLayout.getEditText().getText().toString()));
                                        try {
                                            if (GiaoDienChinh.bangLuong.updateBangLuong(bangLuong1)) {
                                                Toast.makeText(getContext(), "Update thành công", Toast.LENGTH_SHORT).show();
                                                txtLuongCb.setText(Float.parseFloat(inputLayout.getEditText().getText().toString()) + " ₫");
                                                dialog.cancel();
                                            } else {
                                                Toast.makeText(getContext(), "Update thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                            Log.d("sssssss", "onItemSelected: " + e.getMessage());
                                        }
                                    }
                                });
                                dialog.show();
                            });
                        }
                        Calendar calendar = Calendar.getInstance();
                        float hChuNhat = 0;
                        int soChuNhat = 0;
                        int soNgayThuong = 0;
                        for (ChamCong chamCong : GiaoDienChinh.daoChamCong.getListChamCong(nhanVien, bangLuong.get(i).getNgayThang())) {
                            if (chamCong.getXacNhanChamCong() == 1 && chamCong.getGioBatDau() != null && chamCong.getGioKetThuc() != null) {
                                soH += chamCong.getGioKetThuc().getTime() - chamCong.getGioBatDau().getTime();
                                calendar.setTime(chamCong.getNgay());

                                soNgayThuong++;
                                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                                    soChuNhat++;
                                    hChuNhat += (float) (chamCong.getGioKetThuc().getTime() - chamCong.getGioBatDau().getTime()) / (1000 * 60 * 60);
                                }
                            }
                        }
                        float h = (float) soH / (1000 * 60 * 60);
                        float v = bangLuong.get(i).getLuongCB();
                        float congMotNgay = v / 26;
                        float congMotGio = congMotNgay / 8;
                        float tongLuong = ((h - hChuNhat) + (hChuNhat * 2)) * congMotGio;
                        txtLuongCb.setText(v + " ₫");
                        txtSoGio.setText(h + " giờ");
                        txtChuNhat.setText(soChuNhat + " ca");
                        txtNgayThuong.setText((soNgayThuong - soChuNhat) + " ca");
                        txtUngLuong.setText(bangLuong.get(i).getUngLuong() + " ₫");
                        txtSoTienThuong.setText(bangLuong.get(i).getThuong() + " ₫");
                        txtTong.setText((tongLuong - bangLuong.get(i).getUngLuong() + bangLuong.get(i).getThuong()) + " ₫");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (SQLException e) {
            Log.d("gggggggggg", "onCreateView: " + e.toString());
            e.printStackTrace();
        }
        return view;
    }

    private Dialog createDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.cap_nhat_bang_luong);
        return dialog;
    }
}
