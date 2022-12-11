package laptrinhandroid.fpoly.dnnhm3.Fragment.Kho;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.SanPhamKhoAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;


public class TabListProduct_QLKho_Fragment extends Fragment {
    LayoutInflater inflater;
    Context mContext;
    ArrayList<SanPham> arrSP = new ArrayList<>();
    RecyclerView rcySP;
    DAOSanPham daoSanPham;
    SanPhamKhoAdapter adapter;
    int giatritongSP = 0, soluongton = 0;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rcySP = view.findViewById(R.id.recyclerview_lsProduct);
         rcySP.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        TextView tv_tongSP, tv_giatriton, tv_soLuongton;
        tv_tongSP = view.findViewById(R.id.txt_tongSPton);
        tv_giatriton = view.findViewById(R.id.tv_giatriton);
        tv_soLuongton = view.findViewById(R.id.tv_tongSoLuong);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                daoSanPham = new DAOSanPham();
                try {
                    arrSP = (ArrayList<SanPham>) daoSanPham.getListSanPham();
                    for (SanPham sanPham : arrSP) {
                        giatritongSP += (sanPham.getSoLuong() * sanPham.getGiaNhap());
                        soluongton += sanPham.getSoLuong();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        adapter = new SanPhamKhoAdapter(mContext, arrSP);
        rcySP.setAdapter(adapter);
        tv_tongSP.setText(arrSP.size() + " sản phẩm");
        tv_giatriton.setText(giatritongSP + " đ");
        tv_soLuongton.setText(soluongton + "");
        Log.e("ListSuze", arrSP.size() + "");
        Log.e("giatritong", giatritongSP + "");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_list_product__q_l_kho_, container, false);
        return view;
    }
}