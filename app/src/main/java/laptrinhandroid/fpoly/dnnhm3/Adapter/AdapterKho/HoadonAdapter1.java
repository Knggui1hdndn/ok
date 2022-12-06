package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.BaoCaoDonhangActivity;
import laptrinhandroid.fpoly.dnnhm3.Activity.MainActivityhoadon;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOhoadon;
import laptrinhandroid.fpoly.dnnhm3.DAO.Daokhachhang;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;

import laptrinhandroid.fpoly.dnnhm3.Fragment.BottomSheetdigloghoadon;
import laptrinhandroid.fpoly.dnnhm3.R;

public class HoadonAdapter1 extends RecyclerView.Adapter<HoadonAdapter1.viewholder>   {
    Context context;
    List<HoaDonBan> listhoadon;
    List<HoaDonBan> listold;
    DAOhoadon daOhoadon;
    DAONhanVien daoNhanVien=new DAONhanVien();
    List<NhanVien> listnv;
//    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");

    public HoadonAdapter1(Context context, List<HoaDonBan> listhoadon) {
        this.context = context;
        this.listhoadon = listhoadon;
        this.listold = listhoadon;
        daOhoadon = new DAOhoadon();
    }

    public HoadonAdapter1() {
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemhoadon, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        HoaDonBan hd = listhoadon.get(position);
        ((BaoCaoDonhangActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NhanVien nhanVien = null;
                try {
                    nhanVien = daoNhanVien.getListNhanVien(hd.getMaNV());
                    holder.txtmanv.setText(String.valueOf(nhanVien.getHoTen()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.txtngayban.setText(String.valueOf(hd.getNgayBan()));
        holder.txttongtien.setText(String.format("%.0f", hd.getTongTien()) + " đ");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetdigloghoadon bottomSheetdigloghoadon =   new BottomSheetdigloghoadon();
                Bundle bundle= new Bundle();
                bundle.putSerializable("keyhoadan",  hd);
                bottomSheetdigloghoadon.setArguments(bundle);;
                bottomSheetdigloghoadon.show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheetdigloghoadon.getTag());
            }
        });
//           holder.edgiathue.setText(String.valueOf(loaisach.getGiathue()));
    }


    @Override
    public int getItemCount() {
        if (listhoadon == null) {
            return 0;
        }
        return listhoadon.size();
    }



    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtid, txtmanv, txtmakh, txtngayban, txttongtien, txtthanhtoan;
        CardView relativeLayout;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtmanv = itemView.findViewById(R.id.hoadonmanv1);
            relativeLayout = itemView.findViewById(R.id.rectangle_12);
            txtngayban = itemView.findViewById(R.id.hoadonngayban1);
            txttongtien = itemView.findViewById(R.id.hoadontongtien1);


        }

    }


}
