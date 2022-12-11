package laptrinhandroid.fpoly.dnnhm3.Fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.AdapterchitiethoadonNhap;

import laptrinhandroid.fpoly.dnnhm3.DAO.DAOChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private static String KEY_ORDER_OBJECT = "order_object";
    private HoaDonNhapKho mHoaDonNhap;
    private RecyclerView recyclerView;
    private TextView tv_maHD,tv_ngayHD,tv_tongsoluong, tv_tongtienhang, tv_tongchi,tv_tongchi2;
    AdapterchitiethoadonNhap adapter;
    DAOChiTietHoaDonNhap daoChiTietHoaDonNhap;
    DAOSanPham daoSanPham;
    ArrayList<SanPham> arrSP=new ArrayList<>();
    ArrayList<ChiTietHoaDonNhap> listchitiethoadonnhap=new ArrayList<>();
    int tongsoluong=0,tongtienhang=0,chietKhau=0;

    //    public static BottomSheetDialog newInstance(HoaDonNhapKho hoaDonNhapKho,ArrayList<SanPham> list){
//        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog();
//        Bundle bundle=new Bundle();
//        bundle.putSerializable(KEY_ORDER_OBJECT, hoaDonNhapKho);
//        bundle.pu
//        bottomSheetDialog.setArguments(bundle);
//        return bottomSheetDialog;
//    }
    public static BottomSheetDialog newInstance(HoaDonNhapKho hoaDonNhapKho){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog();
        Bundle bundle=new Bundle();
        bundle.putSerializable(KEY_ORDER_OBJECT, hoaDonNhapKho);
        bottomSheetDialog.setArguments(bundle);
        return bottomSheetDialog;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_button_sheet_kho, null);
        initView(view);
        setDataBill();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //      onViewCreatedđược gọi ngay sau onCreateView(phương thức bạn khởi tạo và tạo tất cả các đố
//                i tượng của mình, bao gồm cả của bạn TextView)
        super.onViewCreated(view, savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleReceive=getArguments();
        if (bundleReceive!=null){
            mHoaDonNhap= (HoaDonNhapKho) bundleReceive.get(KEY_ORDER_OBJECT);
        }
    }
    private void initView (View view){
        tv_maHD=view.findViewById(R.id.tv_id_chitietHDN);
        tv_ngayHD=view.findViewById(R.id.tv_chitietngayHDN);
        recyclerView=view.findViewById(R.id.recyclerview_lsProduct_indetail);
        tv_tongsoluong=view.findViewById(R.id.tv_chitietSoLuong);
        tv_tongtienhang=view.findViewById(R.id.tv_tongtienNH);
        tv_tongchi=view.findViewById(R.id.tv_tongchiNH);
        tv_tongchi2=view.findViewById(R.id.tv_tongchiNH2);
    }
    private void setDataBill(){
        if (mHoaDonNhap==null){
            return;
        }
        daoChiTietHoaDonNhap= new DAOChiTietHoaDonNhap();
        try {
            mHoaDonNhap.toString();
            listchitiethoadonnhap= (ArrayList<ChiTietHoaDonNhap>) daoChiTietHoaDonNhap.getIdchitietHoadonnhap(mHoaDonNhap);
            Log.d("ssssssssss", "onViewCreated: "+ Arrays.toString(listchitiethoadonnhap.toArray()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (ChiTietHoaDonNhap chiTietHoaDonNhap:listchitiethoadonnhap) {
            tongsoluong+=chiTietHoaDonNhap.getSoLuong();
            tongtienhang+=(chiTietHoaDonNhap.getDonGia()*chiTietHoaDonNhap.getSoLuong());
            //  chietKhau+=(chiTietHoaDonNhap.ge()*sanpham.getSoLuong()-sanpham.getGiaNhap()*sanpham.getSoLuong());
        }
        tv_maHD.setText("#NH"+mHoaDonNhap.getMaHDNhap());
        tv_ngayHD.setText(mHoaDonNhap.getNgayNhap()+"");
        tv_tongsoluong.setText(tongsoluong+"");
        tv_tongtienhang.setText(String.format("%.0f",mHoaDonNhap.getTongTien())+" đ");
        tv_tongchi.setText(String.format("%.0f",mHoaDonNhap.getTongTien())+" đ");
        tv_tongchi2.setText(String.format("%.0f",mHoaDonNhap.getTongTien())+" đ");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        for (ChiTietHoaDonNhap ch:listchitiethoadonnhap
        ) {
            Log.d("listchitiet", "setDataBill: "+listchitiethoadonnhap.toString());

        }
        adapter = new AdapterchitiethoadonNhap( getContext(), listchitiethoadonnhap);
        recyclerView.setAdapter(adapter);
    }
}
