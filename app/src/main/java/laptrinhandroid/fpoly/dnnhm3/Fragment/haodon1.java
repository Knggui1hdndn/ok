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

public class haodon1 extends Fragment implements InforSearch {

    RecyclerView recyclerView;
    FloatingActionButton faa;
    DAOhoadon daohoadon;
    HoadonAdapter hoadonAdapter;
    HashMap<HoaDonBan, String> list;
    HashMap<HoaDonBan, String> hoaDonBans = new HashMap<>();

    public haodon1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_haodon1, null);
        recyclerView = view.findViewById(R.id.Relvhoadon);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        faa = view.findViewById(R.id.floating1);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                daohoadon = new DAOhoadon();
                try {
                    list = daohoadon.getAllhoadon1();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        List<Map.Entry<HoaDonBan, String>> list1 = new ArrayList(list.entrySet());


        // Sorting a List
        Collections.sort(list1, new Comparator<Map.Entry<HoaDonBan, String>>() {
            @Override
            public int compare(Map.Entry<HoaDonBan, String> hoaDonBanStringEntry, Map.Entry<HoaDonBan, String> t1) {
                return hoaDonBanStringEntry.getKey().getNgayBan().compareTo(t1.getKey().getNgayBan());
            }
        });

        // Convert List to Map
        HashMap<HoaDonBan, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<HoaDonBan, String> entry : list1) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        hoadonAdapter = new HoadonAdapter(getContext(), sortedMap);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    list = daohoadon.getAllhoadon1();
//                     hoadonAdapter = new HoadonAdapter(getContext(), list);
//                     recyclerView.setAdapter(hoadonAdapter);
                    hoadonAdapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void InforSearch(String s) {

            hoadonAdapter.getFilter().filter(s);


    }
}