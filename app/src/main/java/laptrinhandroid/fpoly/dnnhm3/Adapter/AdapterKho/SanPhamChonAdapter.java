package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

import laptrinhandroid.fpoly.dnnhm3.Activity.ChoseProducts;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class SanPhamChonAdapter extends RecyclerView.Adapter<SanPhamChonAdapter.SanPhamChonViewHolder> {
    Context context;
    ArrayList<SanPham> arrSP = new ArrayList<>();
    DAOSanPham daoSanPham = new DAOSanPham();

    View viewAlert;
    ChoseProducts choseProducts;
    ExtendedFloatingActionButton btn_continute;
    LayoutInflater inflater;
    ArrayList<ChiTietHoaDonNhap> arrCTHD;
    int soluong;

    public SanPhamChonAdapter(ChoseProducts choseProducts,  ArrayList<SanPham> arrSP) {
        this.choseProducts = choseProducts;
        this.arrSP = arrSP;
    }
    public SanPhamChonAdapter(Context context,  ArrayList<ChiTietHoaDonNhap> arrCTHD) {
        this.context = context;
        this.arrCTHD = arrCTHD;
    }


    @NonNull
    @Override
    public SanPhamChonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.custom_sanpham_chon, parent, false);
        viewAlert = parent;
        return new SanPhamChonViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamChonViewHolder holder, int position) {
        SanPham sanPham = arrSP.get(position);
        int[] i = {0};
        if(sanPham != null) {
            try {
                holder.img_SPChon.setImageBitmap(ConvertImg.convertBaseStringToBitmap(sanPham.getAnh()));

            }catch (Exception e){

            }
            holder.tv_tenSPChon.setText(sanPham.getTenSP()+"");
            holder.tv_soluongtonChon.setText("Tồn kho: "+sanPham.getSoLuong()+"");
            holder.tv_maSPChon.setText("SP"+sanPham.getMaSP());
            holder.item_btn_number.setVisibility(View.GONE);
            btn_continute=viewAlert.findViewById(R.id.btn_continute);
             holder.item_spChon_frag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.item_btn_number.setVisibility(View.VISIBLE);
                    i[0]=1;
                    choseProducts.sanPhamInterface(sanPham,i[0]);
                 }
            });
            holder.tv_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (i[0]<2&&i[0]>0){
                       holder.item_btn_number.setVisibility(View.GONE);
                       i[0]--;
                       choseProducts.sanPhamInterface(sanPham,i[0]);
                    }else {
                       i[0]--;
                       if (i[0] <= 10) {
                           choseProducts.sanPhamInterface(sanPham,i[0]);

                           holder.tv_soluongChot.setText(i[0] + "");
                       }
                   }

                }
            });
            holder.tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i[0]++;
                    if (i[0] <= 10) {
                        choseProducts.sanPhamInterface(sanPham,i[0]);
                        holder.tv_soluongChot.setText(i[0] + "");
                    }
                }
            });



        }

    }




    @Override
    public int getItemCount() {
        return arrSP.size();
    }

    public static class SanPhamChonViewHolder extends RecyclerView.ViewHolder{
        ImageView img_SPChon;
        TextView tv_tenSPChon, tv_maSPChon, tv_soluongtonChon, tv_sub, tv_soluongChot, tv_add;
        View item_spChon_frag,item_btn_number;
        public SanPhamChonViewHolder(@NonNull View itemView) {
            super(itemView);
            item_spChon_frag=itemView.findViewById(R.id.item_spChon_frag);
            item_btn_number=itemView.findViewById(R.id.item_btn_number);
            img_SPChon=itemView.findViewById(R.id.img_SPChose);
            tv_tenSPChon = itemView.findViewById(R.id.tv_name_SPChose);
            tv_maSPChon = itemView.findViewById(R.id.tv_maSPChose);
            tv_soluongtonChon = itemView.findViewById(R.id.tv_SLTonChose);
            tv_sub=itemView.findViewById(R.id.tv_sub);
            tv_soluongChot=itemView.findViewById(R.id.tv_soluongNhap);
            tv_add=itemView.findViewById(R.id.tv_add);

        }
    }
}
