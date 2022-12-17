package laptrinhandroid.fpoly.dnnhm3.Fragment.SanPham;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.AddNhanVien;
import laptrinhandroid.fpoly.dnnhm3.Activity.SanPhamActivity;
import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterSanPham.SanPhamadapter;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOLoaiSanPham;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.BottomSheetSelectImg;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.LoaiSP;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;

import laptrinhandroid.fpoly.dnnhm3.Interface.InforSearch;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.setImg;


public class FragmentSanPham extends Fragment implements setImg, InforSearch {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private ArrayList<SanPham> list = new ArrayList<>();
    private DAOSanPham daoSanPham;
    private SanPhamadapter adapter;
    private DAOLoaiSanPham daoLoaiSanPham;
    ImageView img;


    public FragmentSanPham() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sanpham, container, false);

        daoLoaiSanPham = new DAOLoaiSanPham();
        daoSanPham = new DAOSanPham();
        recyclerView = view.findViewById(R.id.rcv_sanpham);
        floatingActionButton = view.findViewById(R.id.flbtn_sanpham);
        try {
            list = (ArrayList<SanPham>) daoSanPham.getListSanPham();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new SanPhamadapter(getActivity(), list);
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSanPham();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;

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
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent gallery = new Intent();
                    gallery.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(gallery, 1);
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA}, 2);
                }
            }
        }
    }

    public void dialogSanPham() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sanpham, null);
        builder.setView(v);
        TextInputEditText ed_tenSanPham, ed_giaban, ed_giavon;
        Spinner spn_loaiSP;
        Button btn_them, btn_huy, btnanh;
        ed_giaban = v.findViewById(R.id.ed_giaban);


        ed_tenSanPham = v.findViewById(R.id.ed_tensanpham);
        ed_giavon = v.findViewById(R.id.ed_giavon);
        spn_loaiSP = v.findViewById(R.id.spn_danhmuc);
        btn_them = v.findViewById(R.id.btn_themsp);
        btn_huy = v.findViewById(R.id.btn_huy);
        img = v.findViewById(R.id.imv_anhsanpham);
        btnanh = v.findViewById(R.id.btn_themanhsp);
        List<String> loaiSP = new ArrayList<>();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        try {
            for (LoaiSP listLoaiSP : daoLoaiSanPham.getListLoaiSanPham()) {
                loaiSP.add(listLoaiSP.getMaLoai() + "." + listLoaiSP.getTenLoai());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapterLoaiSP = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, loaiSP);
        spn_loaiSP.setAdapter(adapterLoaiSP);

        btn_them.setOnClickListener(v1 -> {
            if (validate(ed_giaban) & validate(ed_tenSanPham) & validate(ed_giavon)) {
                SanPham sanPham = new SanPham();
                String LoaiSP = (String) spn_loaiSP.getSelectedItem();
                String[] maloai = LoaiSP.split("\\.");
                sanPham.setTenSP(ed_tenSanPham.getText().toString());
                sanPham.setGiaNhap(Float.parseFloat(ed_giavon.getText().toString()));
                sanPham.setGiaBan(Float.parseFloat(ed_giaban.getText().toString()));
                sanPham.setLoaiSP(Integer.parseInt(maloai[0]));
                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                sanPham.setAnh(ConvertImg.convertBitmapToBaseString(bitmap));
                if (((SanPhamActivity) getContext()).addSP(sanPham)) {
                    Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
                    if (list.size() > 0) {
                        sanPham.setMaSP(list.get(list.size() - 1).getMaSP() + 1);
                    } else {
                        try {
                            list = (ArrayList<SanPham>) daoSanPham.getListSanPham();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    list.add(sanPham);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "!OK", Toast.LENGTH_SHORT).show();

                }
                alertDialog.cancel();
                adapter.notifyDataSetChanged();
            }


        });
        btn_huy.setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        btnanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });
    }

    private boolean validate(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError("Không bỏ trống");
            return false;
        }

        return true;
    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }

    private void show() {
        BottomSheetSelectImg bottomSheetSelectImg = new BottomSheetSelectImg(getActivity(), this);
        bottomSheetSelectImg.show(getActivity().getSupportFragmentManager(), "sss");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent gallery = new Intent();
            gallery.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(gallery, 1);
        } else {
            Toast.makeText(getActivity(), "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            if (requestCode == 0) {
                img.setImageURI(data.getData());
            } else if (requestCode == 1) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void InforSearch(String s) {
        adapter.getFilter().filter(s);
    }
}