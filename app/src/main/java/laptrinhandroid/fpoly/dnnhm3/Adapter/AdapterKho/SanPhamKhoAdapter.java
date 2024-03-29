package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class SanPhamKhoAdapter extends RecyclerView.Adapter<SanPhamKhoAdapter.SanPhamKhoViewHolder> implements Filterable {
    Context context;
    ArrayList<SanPham> arrSP = new ArrayList<>();
DAOSanPham daoSanPham=new DAOSanPham();
    View viewAlert;
    LayoutInflater inflater;
    List<SanPham> listold;
;
    public SanPhamKhoAdapter(Context context,  ArrayList<SanPham> arrSP) {
        this.context = context;
        this.arrSP = arrSP;
    }
    public void setData( ArrayList<SanPham> arrSP) {
             this.arrSP = arrSP;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SanPhamKhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         ;
         viewAlert = parent;
        return new SanPhamKhoViewHolder(LayoutInflater.from(parent.getContext()) .inflate(R.layout.custom_sanphamkho, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamKhoViewHolder holder, int position) {
        SanPham sanPham = arrSP.get(position);
        if(sanPham != null) {
            try {
                holder.img_SP.setImageBitmap(ConvertImg.convertBaseStringToBitmap(sanPham.getAnh()));

            }catch (Exception e){

            }
            holder.tv_tenSP.setText(sanPham.getTenSP()+"");

            holder.tv_soluongton.setText("Tồn kho: "+sanPham.getSoLuong()+"");
            holder.tv_maSP.setText("SP"+sanPham.getMaSP());
            holder.tv_tongtienton.setText(String.format("%.0f", sanPham.getGiaNhap())  + " đ");

        }
    }



    @Override
    public int getItemCount() {
        return arrSP.size();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strsearch = charSequence.toString();
                if (strsearch.isEmpty()) {
                    try {
                        listold=daoSanPham.getListSanPham();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    arrSP = (ArrayList<SanPham>) listold;

                } else {
                    List<SanPham> listabc = new ArrayList<>();
                    for (SanPham hoaDonNhapKho1 : arrSP) {
                        if (String.valueOf(hoaDonNhapKho1.getTenSP()).toLowerCase().contains(strsearch.toLowerCase())) {
                            listabc.add(hoaDonNhapKho1);
                        }
                    }
                    arrSP = (ArrayList<SanPham>) listabc;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrSP;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrSP= (ArrayList<SanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class SanPhamKhoViewHolder extends RecyclerView.ViewHolder{
        ImageView img_SP;
        TextView tv_tenSP, tv_maSP, tv_soluongton,tv_tongtienton;
        public SanPhamKhoViewHolder(@NonNull View itemView) {
            super(itemView);
            img_SP=itemView.findViewById(R.id.img_SPkho);
            tv_tenSP = itemView.findViewById(R.id.tv_name_SP);
            tv_maSP = itemView.findViewById(R.id.tv_maSP);
            tv_soluongton = itemView.findViewById(R.id.tv_soluongtonSP);
            tv_tongtienton= itemView.findViewById(R.id.tv_tongtienSP);
        }
    }
}
