package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class SanPhamThanhToanAdapter extends RecyclerView.Adapter<SanPhamThanhToanAdapter.SanPhamThanhToanViewHolder> {
    Context context;
    ArrayList<SanPham> arrSP = new ArrayList<>();
    View viewAlert;
    LayoutInflater inflater;

    public SanPhamThanhToanAdapter(Context context, ArrayList<SanPham> arrSP) {
        this.context = context;
        this.arrSP = arrSP;
    }

    @NonNull
    @Override
    public SanPhamThanhToanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.custom_product_indetail, parent, false);
        viewAlert = parent;
        return new SanPhamThanhToanViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamThanhToanViewHolder holder, int position) {
        SanPham sanPham = arrSP.get(position);
        if (sanPham != null) {
            try {
                holder.img_SPChon.setImageBitmap(ConvertImg.convertBaseStringToBitmap(sanPham.getAnh() + ""));
            } catch (Exception e) {

            }
            holder.tv_tenSPChon.setText(sanPham.getTenSP() + "");

            holder.tv_soluongtonChon.setText(String.format("%.0f", sanPham.getGiaNhap()) + " x " + sanPham.getSoLuong());
            holder.tv_maSPChon.setText("SP" + sanPham.getMaSP());
            holder.tv_thanhtien.setText(String.format("%.0f", sanPham.getGiaNhap() * sanPham.getSoLuong()) + " đ");
            ;
        }
    }

    @Override
    public int getItemCount() {
        return arrSP.size();
    }

    public static class SanPhamThanhToanViewHolder extends RecyclerView.ViewHolder {
        ImageView img_SPChon;
        TextView tv_tenSPChon, tv_maSPChon, tv_soluongtonChon, tv_thanhtien;

        public SanPhamThanhToanViewHolder(@NonNull View itemView) {
            super(itemView);
            img_SPChon = itemView.findViewById(R.id.img_SPkho_thanhtoan);
            tv_tenSPChon = itemView.findViewById(R.id.tv_name_SP_thanhtoan);
            tv_maSPChon = itemView.findViewById(R.id.tv_maSP_thanhtoan);
            tv_soluongtonChon = itemView.findViewById(R.id.tv_soluongSP_thanhtoan);
            tv_thanhtien = itemView.findViewById(R.id.tv_tongtienSP_thanhtoan);
        }
    }
}
