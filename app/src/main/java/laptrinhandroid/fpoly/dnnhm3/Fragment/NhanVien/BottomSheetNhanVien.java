package laptrinhandroid.fpoly.dnnhm3.Fragment.NhanVien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.sql.SQLException;

import laptrinhandroid.fpoly.dnnhm3.Activity.ChiTietNhanVien;
import laptrinhandroid.fpoly.dnnhm3.Activity.GiaoDienChinh;
import laptrinhandroid.fpoly.dnnhm3.Activity.ListChamCongBangLuong;
import laptrinhandroid.fpoly.dnnhm3.Activity.QuanLiNhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;

public class BottomSheetNhanVien extends BottomSheetDialogFragment {
    private Button btnChiTiet, btnChamCong, btnBangLuong,btnXoa;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_nhanvien, null); ;
        btnChamCong = view.findViewById(R.id.btnChamCong);
        btnBangLuong = view.findViewById(R.id.btnBangLuong);
        btnChiTiet = view.findViewById(R.id.btnChiTiet);
        btnXoa= view.findViewById(R.id.btnXoa);
        int nhanVien =   getArguments().getInt("maNV");
        btnChamCong.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ListChamCongBangLuong.class);
            intent.putExtra("maNV", nhanVien );
            intent.putExtra("CC", 1);
            getActivity().startActivity(intent);
            dismiss();
        });
        btnBangLuong.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), ListChamCongBangLuong.class);
            intent.putExtra("BL", 2);
            intent.putExtra("maNV",  nhanVien  );

            getActivity().startActivity(intent);
            dismiss();

        });
        btnChiTiet.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), ChiTietNhanVien.class);
            intent.putExtra("maNV", nhanVien );
            getActivity().startActivity(intent);
            dismiss();

        }); btnXoa.setOnClickListener(view13 -> {
            try {
                if(GiaoDienChinh.nhanVien1.deleteNhanVien(nhanVien)){
                    Toast.makeText(getContext(), "Đã xóa nhân viên", Toast.LENGTH_SHORT).show();
                   ((QuanLiNhanVien) getActivity()).notifiDataChange(getArguments().getInt("i"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);
    }
}
