package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.R;

public class AdapterchitiethoadonNhap extends RecyclerView.Adapter<AdapterchitiethoadonNhap.viewholder> {
    Context context;
    List<ChiTietHoaDonNhap> list = new ArrayList<>();
    DAOChiTietHoaDonNhap daoChiTietHoaDonNhap = new DAOChiTietHoaDonNhap();
    ChiTietHoaDonNhap chiTietHoaDonNhap = new ChiTietHoaDonNhap();

    public AdapterchitiethoadonNhap(Context context, ArrayList<ChiTietHoaDonNhap> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_product_indetail, parent, false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        chiTietHoaDonNhap = list.get(position);
        Log.d("TAGcheck", "onBindViewHolder: " + chiTietHoaDonNhap.toString());
        if (chiTietHoaDonNhap != null) {
            try {
                holder.img_SPChon.setImageBitmap(ConvertImg.convertBaseStringToBitmap(chiTietHoaDonNhap.getAnh()));

            } catch (Exception e) {

            }
            holder.tv_tenSPChon.setText(chiTietHoaDonNhap.getTenSP() + "");
            holder.tv_soluongtonChon.setText(String.format("%.0f", chiTietHoaDonNhap.getDonGia()) + " x " + chiTietHoaDonNhap.getSoLuong());
            holder.tv_maSPChon.setText("SP" + chiTietHoaDonNhap.getMaSp());
            holder.tv_thanhtien.setText(String.format("%.0f", chiTietHoaDonNhap.getThanhTien()) + " đ");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView img_SPChon;
        TextView tv_tenSPChon, tv_maSPChon, tv_soluongtonChon, tv_thanhtien;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            img_SPChon = itemView.findViewById(R.id.img_SPkho_thanhtoan);
            tv_tenSPChon = itemView.findViewById(R.id.tv_name_SP_thanhtoan);
            tv_maSPChon = itemView.findViewById(R.id.tv_maSP_thanhtoan);
            tv_soluongtonChon = itemView.findViewById(R.id.tv_soluongSP_thanhtoan);
            tv_thanhtien = itemView.findViewById(R.id.tv_tongtienSP_thanhtoan);

//


        }
    }
}
