package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import laptrinhandroid.fpoly.dnnhm3.Activity.MainActivityhoadon;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAONhanVien;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOhoadon;
import laptrinhandroid.fpoly.dnnhm3.DAO.Daokhachhang;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;

import laptrinhandroid.fpoly.dnnhm3.Fragment.BottomSheetdigloghoadon;
import laptrinhandroid.fpoly.dnnhm3.R;

public class HoadonAdapter extends RecyclerView.Adapter<HoadonAdapter.viewholder> implements Filterable {
    Context context;
    List<HoaDonBan> listhoadon;
    List<HoaDonBan> listold;

//    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");

    public HoadonAdapter(Context context, List<HoaDonBan> listhoadon) {
        this.context = context;
        this.listhoadon = listhoadon;
        this.listold = listhoadon;
    }

    public HoadonAdapter() {
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
        holder.txtmanv.setText(String.valueOf("HD"+hd.getMaHDBan()));
        holder.txtngayban.setText(String.valueOf(hd.getNgayBan()));
        holder.txttongtien.setText(String.format("%.0f", hd.getTongTien()) + " đ");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetdigloghoadon bottomSheetdigloghoadon = new BottomSheetdigloghoadon();
                Bundle bundle = new Bundle();
                bundle.putSerializable("keyhoadan", hd);
                bottomSheetdigloghoadon.setArguments(bundle);
                bottomSheetdigloghoadon.show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheetdigloghoadon.getTag());
            }
        });

    }


    @Override
    public int getItemCount() {
        if (listhoadon == null) {
            return 0;
        }
        return listhoadon.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                Log.d("lllljjh", "performFiltering: 0"+charSequence);

                String strsearch = charSequence.toString();
                if (strsearch.isEmpty()) {
                    listhoadon = listold;
                } else {
                  List<HoaDonBan> listabc= new ArrayList<>();
                    for (HoaDonBan hoaDonBan:listhoadon) {
                        if (String.valueOf(hoaDonBan.getMaHDBan()).toLowerCase().contains(strsearch.toLowerCase())) {
                           listabc.add(hoaDonBan);

                        }
                    }
                    listhoadon = listabc;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listhoadon;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listhoadon = (List<HoaDonBan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
