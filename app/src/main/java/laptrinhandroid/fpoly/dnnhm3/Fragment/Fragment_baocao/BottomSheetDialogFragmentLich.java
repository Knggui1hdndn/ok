package laptrinhandroid.fpoly.dnnhm3.Fragment.Fragment_baocao;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon.AdapterchitiethoadonSheet;
import laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao.BaocaoAdapterLich;
import laptrinhandroid.fpoly.dnnhm3.DAO.Daochitiethoadon;
import laptrinhandroid.fpoly.dnnhm3.Fragment.BottomSheetdigloghoadon;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonban;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.R;

public class BottomSheetDialogFragmentLich extends BottomSheetDialogFragment {
//    RecyclerView recyclerView;
//
//
//    @SuppressLint("MissingInflatedId")
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.dialog_button_sheet_baocao_lich, null);
//
//        recyclerView = view.findViewById(R.id.dialog_buttomshett_baocao_lich_rcv);
//
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        Bundle bundle = getArguments();
//        ArrayList<String> listLich = bundle.getStringArrayList("listLich");
//
//        BaocaoAdapterLich baocaoAdapterLich = new BaocaoAdapterLich(getActivity(), listLich, );
//        recyclerView.setAdapter(baocaoAdapterLich);
//        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);   //dòng kẻ giữa mỗi item
//        recyclerView.addItemDecoration(itemDecoration);
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    public static BottomSheetDialogFragmentLich getInstance(ArrayList<String> listlich) {
//        BottomSheetDialogFragmentLich bottomSheetDialogFragmentLich = new BottomSheetDialogFragmentLich();
//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList("listLich", listlich);
//        bottomSheetDialogFragmentLich.setArguments(bundle);
//        return bottomSheetDialogFragmentLich;
//    }
}
