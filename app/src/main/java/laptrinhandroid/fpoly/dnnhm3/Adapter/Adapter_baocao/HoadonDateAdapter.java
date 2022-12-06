package laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;

public class HoadonDateAdapter extends RecyclerView.Adapter<HoadonDateAdapter.viewholder> {

    Context context;
    List<HoaDonBan> listhoadon;

    public HoadonDateAdapter(Context context, List<HoaDonBan> listhoadon) {
        this.context = context;
        this.listhoadon = listhoadon;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_baocao_hoadon, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        HoaDonBan hd = listhoadon.get(position);
//        daokhachhang = new Daokhachhang();
        DAONhanVien daoNhanVien = new DAONhanVien();
        NhanVien nhanVien = null;
        try {
            nhanVien = (NhanVien) daoNhanVien.getListNhanVien(hd.getMaNV());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        holder.txtmanv.setText(String.valueOf("nhanVien.getHoTen()"));
        holder.txtngayban.setText(String.valueOf(hd.getNgayBan()));
        holder.txttongtien.setText(String.format("%.0f",hd.getTongTien()));

//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BottomSheetdigloghoadon bottomSheetdigloghoadon = BottomSheetdigloghoadon.getInstance(hd);
//                bottomSheetdigloghoadon.show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheetdigloghoadon.getTag());
//            }
//        });
//           holder.edgiathue.setText(String.valueOf(loaisach.getGiathue()));
    }


    @Override
    public int getItemCount() {
        if(listhoadon==null){
            return 0;
        }
        return listhoadon.size();
    }



    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtid, txtmanv, txtmakh, txtngayban, txttongtien, txtthanhtoan;
        CardView relativeLayout;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtmanv = itemView.findViewById(R.id.hoadonmanv);
//            relativeLayout = itemView.findViewById(R.id.rectangle_1);
            txtngayban = itemView.findViewById(R.id.hoadonngayban);
            txttongtien = itemView.findViewById(R.id.hoadontongtien);



        }

    }


}
