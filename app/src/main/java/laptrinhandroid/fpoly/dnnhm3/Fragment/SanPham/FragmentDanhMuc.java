package laptrinhandroid.fpoly.dnnhm3.Fragment.SanPham;

 
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

 
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 
 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.Button;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;
import java.util.ArrayList;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterSanPham.LoaiSanPhamAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOLoaiSanPham;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.LoaiSP;

import laptrinhandroid.fpoly.dnnhm3.R;


public class FragmentDanhMuc extends Fragment {
      RecyclerView recyclerView;
     FloatingActionButton floatingActionButton;
    private DAOLoaiSanPham daoLoaiSanPham;
    private LoaiSanPhamAdapter adapter;
    private ArrayList<LoaiSP> list = new ArrayList<>();
 

    public FragmentDanhMuc() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_danh_muc, container, false);
        recyclerView = view.findViewById(R.id.rcv_danhmuc);
        floatingActionButton = view.findViewById(R.id.flbtn_danhmuc);
        daoLoaiSanPham = new DAOLoaiSanPham();
        try {
            list = (ArrayList<LoaiSP>) daoLoaiSanPham.getListLoaiSanPham();
            adapter = new LoaiSanPhamAdapter(getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter.setData(list);
            recyclerView.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            floatingActionButton.setOnClickListener(v -> insert_danhmuc());

           return view;
    }
        private void insert_danhmuc () {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_danhmuc, null);
            TextInputEditText ed_tendanhmuc = view.findViewById(R.id.ed_tendanhmuc);
            Button btn_them = view.findViewById(R.id.btn_themdanhmuc);
            builder.setView(view);
            Dialog dialog = builder.create();
            dialog.show();
            btn_them.setOnClickListener(v -> {
                daoLoaiSanPham = new DAOLoaiSanPham();
                LoaiSP loaiSP = new LoaiSP();
                loaiSP.setTenLoai(ed_tendanhmuc.getText().toString());
                if (daoLoaiSanPham.addLoaiSanPham(loaiSP)) {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    list.add(loaiSP);
                    adapter.setData(list);                }
                dialog.cancel();
            });


        }


    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
 
     }


}