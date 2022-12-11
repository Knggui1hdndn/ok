package laptrinhandroid.fpoly.dnnhm3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import laptrinhandroid.fpoly.dnnhm3.Activity.hoadon11111;
import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon.HoadonAdapter;
import laptrinhandroid.fpoly.dnnhm3.Adapter.Spinernhanvien;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOhoadon;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.Interface.InforSearch;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.XuLiNgay.send;

public class haodon1 extends Fragment implements send {

    RecyclerView recyclerView;
    FloatingActionButton faa;
    DAOhoadon daohoadon;
    HoadonAdapter hoadonAdapter;
    List<HoaDonBan> list;


    public haodon1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_haodon1, null);
        recyclerView = view.findViewById(R.id.Relvhoadon);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        faa = view.findViewById(R.id.floating1);
        daohoadon= new DAOhoadon();
        try {
            list=daohoadon.getListHoadonban();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hoadonAdapter=new HoadonAdapter(getContext(),list);
        recyclerView.setAdapter(hoadonAdapter);
        faa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), hoadon11111.class);
                intent.putExtra("maNV", getArguments().getInt("maNV"));
                startActivity(intent);
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        try {
            list.addAll(daohoadon.getListHoadonban()) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hoadonAdapter.notifyDataSetChanged();
    }

    @Override
    public void send(String s) {
            hoadonAdapter.getFilter().filter(s);
    }
}