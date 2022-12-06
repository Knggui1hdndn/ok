package laptrinhandroid.fpoly.dnnhm3.Fragment.Kho;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.HoaDonNhapAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class open_Add_HDFragment extends Fragment {
    LayoutInflater inflater;
    Context mContext;
    ArrayList<SanPham> arrSP = new ArrayList<>();
    RecyclerView rcySP;
    DAOSanPham daoSanPham;
    HoaDonNhapAdapter adapter;
    public open_Add_HDFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_open__add__h_d, container, false);
        return view;
    }
}