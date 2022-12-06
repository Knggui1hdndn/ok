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
import java.util.Arrays;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon.AdapterchitiethoadonSheet;

import laptrinhandroid.fpoly.dnnhm3.DAO.Daochitiethoadon;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonban;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.R;

public class BottomSheetdigloghoadon extends BottomSheetDialogFragment {
    TextView name, soluong,tontien,mahoadon,date;
List<ChiTietHoaDonban> listchitiethoadon;
RecyclerView recyclerView;
int position;

AdapterchitiethoadonSheet adapterchitiethoadonSheet;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buttonsheet, null);
//        name = view.findViewById(R.id.item_CTHD_tensp);
        mahoadon=view.findViewById(R.id.tv_id_chitietHDN);
        recyclerView=view.findViewById(R.id.rcvchitiethoadon);
        soluong = view.findViewById(R.id.item_CTHD_Soluong);
        tontien=view.findViewById(R.id.item_CTHD_thanhtien);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //      onViewCreatedđược gọi ngay sau onCreateView(phương thức bạn khởi tạo và tạo tất cả các đố
//                i tượng của mình, bao gồm cả của bạn TextView)

        super.onViewCreated(view, savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);
        Bundle bundle=getArguments();
        HoaDonBan hoaDonBan = (HoaDonBan) bundle.getSerializable("keyhoadan");
        Daochitiethoadon daochitiethoadon= new Daochitiethoadon();
        try {
            listchitiethoadon=  daochitiethoadon.getIdchitietHodon(hoaDonBan);
            Log.d("ssssssssss", "onViewCreated: "+ Arrays.toString(listchitiethoadon.toArray()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        name.setText(String.valueOf(listchitiethoadon.get(0).getTenSP())+"");
        int v=0;int v1=0;int v2=0;String date;
       for(ChiTietHoaDonban chiTietHoaDonban:listchitiethoadon){

          v+= chiTietHoaDonban.getSoLuong();
          v1+= chiTietHoaDonban.getThanhTien();
          v2+= chiTietHoaDonban.getMaHD();

       }
             soluong.setText(v+"") ;
             tontien.setText(v1+""+" đ"+"");
             mahoadon.setText("HD"+v2+"");

        adapterchitiethoadonSheet = new AdapterchitiethoadonSheet(getContext(),listchitiethoadon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapterchitiethoadonSheet);
    }
        public static BottomSheetdigloghoadon getInstance(HoaDonBan hoaDonBan){

        return null;
    }
}
