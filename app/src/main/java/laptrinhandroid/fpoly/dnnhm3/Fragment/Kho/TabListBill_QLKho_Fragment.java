package laptrinhandroid.fpoly.dnnhm3.Fragment.Kho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;

import laptrinhandroid.fpoly.dnnhm3.Activity.ChoseProducts;
import laptrinhandroid.fpoly.dnnhm3.Activity.QuanLyKho;
import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.HoaDonNhapAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.Interface.InforSearch;
import laptrinhandroid.fpoly.dnnhm3.R;

public class TabListBill_QLKho_Fragment extends Fragment implements InforSearch {
    LayoutInflater inflater;
    FloatingActionButton floatingActionButton;
    Context mContext;
    View viewDialogAddPhieuMuon;
    ArrayList<HoaDonNhapKho> arrHDN = new ArrayList<>();
    ArrayList<HoaDonNhapKho> arrHDN1 = new ArrayList<>();
    RecyclerView rcyPm;
    DAOHoaDonNhap daoHoaDonNhap;
    HoaDonNhapAdapter adapter;
    QuanLyKho quanLyKho;
    int maNV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_list_bill__q_l_kho_, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inflater = getLayoutInflater();
        floatingActionButton = view.findViewById(R.id.floatbtn_addbill);
        rcyPm = view.findViewById(R.id.recyclerview_lsBill);
        rcyPm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        maNV = getArguments().getInt("maNV");
        adapter = new HoaDonNhapAdapter((QuanLyKho) mContext, arrHDN);
        rcyPm.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChoseProducts.class);
                intent.putExtra("maNV", maNV);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                daoHoaDonNhap = new DAOHoaDonNhap();
                try {
                    arrHDN = (ArrayList<HoaDonNhapKho>) daoHoaDonNhap.getListHoaDonNhap();
                    adapter.setData(arrHDN);

                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.d("loiii", "onViewCreated: " + e.getMessage());
                }
            }
        });


    }

    @Override
    public void InforSearch(String s) {
        if (adapter != null) {
            adapter.getFilter().filter(s);
        }
    }
}