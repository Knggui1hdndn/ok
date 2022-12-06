package laptrinhandroid.fpoly.dnnhm3.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import laptrinhandroid.fpoly.dnnhm3.Activity.AddNhanVien;
import laptrinhandroid.fpoly.dnnhm3.Activity.ChiTietNhanVien;
import laptrinhandroid.fpoly.dnnhm3.Activity.SanPhamActivity;
import laptrinhandroid.fpoly.dnnhm3.Fragment.SanPham.FragmentSanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class BottomSheetSelectImg extends BottomSheetDialog {
    private MaterialButton btnLibrary, btnCamera;
    Activity activity;
   FragmentSanPham fragmentSanPham;

    public BottomSheetSelectImg(Activity activity, FragmentSanPham fragmentSanPham) {
        this.activity = activity;
        this.fragmentSanPham = fragmentSanPham;
    }

    public BottomSheetSelectImg(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_camera, null);
        btnLibrary = view.findViewById(R.id.btnLibrary);
        btnCamera = view.findViewById(R.id.btnCamera);

        btnLibrary.setOnClickListener(view12 -> {
          if(activity instanceof SanPhamActivity){
              ( fragmentSanPham).check(R.id.btnLibrary);

          } else if(activity instanceof ChiTietNhanVien){
                ((ChiTietNhanVien)activity).check(R.id.btnLibrary);
            }else{
                ((AddNhanVien)activity).check(R.id.btnLibrary);

            }
            dismiss();
        });
        btnCamera.setOnClickListener(view13 -> {
            if(activity instanceof SanPhamActivity){
                (( fragmentSanPham)).check(R.id.btnCamera);

            } else if(activity instanceof ChiTietNhanVien){
                ((ChiTietNhanVien)activity).check(R.id.btnCamera);
            }else {
                ((AddNhanVien)activity).check(R.id.btnCamera);

            }
            dismiss();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);
    }
}
