package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.QuanLyKho;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.Fragment.BottomSheetDialog;
import laptrinhandroid.fpoly.dnnhm3.R;

public class HoaDonNhapAdapter extends RecyclerView.Adapter<HoaDonNhapAdapter.HoaDonNhapViewHolder> implements Filterable {

    List<HoaDonNhapKho> listold;
    Context context;
    ArrayList<HoaDonNhapKho> arrHDN = new ArrayList<>();
    DAOHoaDonNhap daoHoaDonNhap = new DAOHoaDonNhap();
    View viewAlert,viewUpdatePM;
    LayoutInflater inflater;
    QuanLyKho activity;
    HoaDonNhapKho hoaDonNhapKho;
    BottomSheetBehavior bottomSheetBehavior;
     public HoaDonNhapAdapter(QuanLyKho activity,  ArrayList<HoaDonNhapKho> arrHDN) {
        this.activity = activity;
        this.arrHDN = arrHDN;
    }
    @NonNull
    @Override
    public HoaDonNhapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.custom_hoadonnhap, parent, false);
        viewAlert = parent;
        return  new HoaDonNhapViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonNhapViewHolder holder, int position) {

         hoaDonNhapKho = arrHDN.get(position);
         if(hoaDonNhapKho != null) {
            holder.tv_IdHDN.setText("#NH"+hoaDonNhapKho.getMaHDNhap());
            holder.tv_ngay_HDN.setText(hoaDonNhapKho.getNgayNhap().toString()+"");
            holder.tv_tien_HDN.setText(String.format("%.0f", hoaDonNhapKho.getTongTien())  + " đ");
         }

        holder.itemBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              openDialogChosePr(arrHDN.get(holder.getAdapterPosition()));

            }
        });
    }

    private void openDialogChosePr(HoaDonNhapKho hoaDonNhapKho) {
//        if(viewAlert.getParent() != null) {
//            ((ViewGroup)viewAlert.getParent()).removeAllViews();
//        }

        BottomSheetDialog bottomSheetDialog=BottomSheetDialog.newInstance(hoaDonNhapKho);
       bottomSheetDialog.show(activity.getSupportFragmentManager(),bottomSheetDialog.getTag());

    }


    @Override
    public int getItemCount() {
        if(arrHDN==null){
            return 0;
        }
         return arrHDN.size();
    }

       @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strsearch = charSequence.toString();
                if (strsearch.isEmpty()) {
                    try {
                        listold=daoHoaDonNhap.getListHoaDonNhap();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    arrHDN = (ArrayList<HoaDonNhapKho>) listold;

                } else {
                    List<HoaDonNhapKho> listabc = new ArrayList<>();
                    for (HoaDonNhapKho hoaDonNhapKho1 : arrHDN) {
                        Log.d("sssssăă", "performFiltering: "+hoaDonNhapKho1.getMaNV());
                        // HoaDonNhapKho hoaDonNhapTam = daoHoaDonNhap.getIdHD(String.valueOf(hoaDonNhapKho1.getMaHDNhap()));
                        if (String.valueOf(hoaDonNhapKho1.getMaHDNhap()).toLowerCase().contains(strsearch.toLowerCase())) {
                            listabc.add(hoaDonNhapKho1);
                        }
                    }
                    arrHDN = (ArrayList<HoaDonNhapKho>) listabc;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrHDN;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrHDN= (ArrayList<HoaDonNhapKho>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class HoaDonNhapViewHolder extends RecyclerView.ViewHolder{
        TextView tv_IdHDN, tv_tien_HDN, tv_ngay_HDN;
        View itemBill;
        public HoaDonNhapViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBill=itemView.findViewById(R.id.item_bill_frag_kho);
            tv_IdHDN = itemView.findViewById(R.id.tv_id_HDN);
            tv_tien_HDN = itemView.findViewById(R.id.tv_tienHDN);
            tv_ngay_HDN = itemView.findViewById(R.id.tv_ngayHDN);
        }
    }
}
