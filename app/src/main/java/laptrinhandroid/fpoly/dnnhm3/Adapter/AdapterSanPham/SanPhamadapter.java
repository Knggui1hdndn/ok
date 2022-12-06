package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterSanPham;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOLoaiSanPham;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.LoaiSP;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class SanPhamadapter extends RecyclerView.Adapter<SanPhamadapter.SanPhamViewHolder> implements Filterable {
    Context context;
    ArrayList<SanPham> arrSP ;
    ArrayList<SanPham> arrSP1 ;
    DAOSanPham daoSanPham=new DAOSanPham();


    public SanPhamadapter(Context context,  ArrayList<SanPham> arrSP) {
        this.context = context;
        this.arrSP = arrSP;
        this.arrSP1=arrSP;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SanPhamViewHolder(  LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
         SanPham sanPham = arrSP.get(position);
        if(sanPham != null) {
            try {
                holder.img_SP.setImageBitmap(ConvertImg.convertBaseStringToBitmap(sanPham.getAnh()));
            }catch (Exception e){
            }
            holder.tv_tenSP.setText(sanPham.getTenSP()+"");
            holder.tv_maSP.setText("SP"+sanPham.getMaSP());
            holder.cv_sanpham.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    opendialog(holder.getAdapterPosition());
                }
            });
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDelete(sanPham);
                }
            });
        }
    }

    private void opendialog(int position) {
        AlertDialog.Builder builder=new  AlertDialog.Builder(context);
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View v=inflater.inflate(R.layout.dialog_sanpham,null);
        builder.setView(v);
        TextInputEditText ed_tenSanPham, ed_giaban, ed_giavon;
        Spinner spn_loaiSP;
        Button btn_them, btn_huy;
        ed_giaban = v.findViewById(R.id.ed_giaban);
        ed_tenSanPham = v.findViewById(R.id.ed_tensanpham);
        ed_giavon = v.findViewById(R.id.ed_giavon);
        spn_loaiSP = v.findViewById(R.id.spn_danhmuc);
        btn_them = v.findViewById(R.id.btn_themsp);
        btn_huy = v.findViewById(R.id.btn_huy);  v.findViewById(R.id.btn_themanhsp).setVisibility(View.GONE);
        btn_them.setText("Cap nhat");
        List<String> loaiSP = new ArrayList<>();
        Dialog dialog=builder.create();
        dialog.show();
        try {
            for (LoaiSP listLoaiSP : new DAOLoaiSanPham().getListLoaiSanPham()) {
                loaiSP.add(listLoaiSP.getMaLoai() + "." + listLoaiSP.getTenLoai());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("fffffffffffffffff", "opendialog: "+e.getMessage());
         }
        ArrayAdapter<String> adapterLoaiSP = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, loaiSP);
        spn_loaiSP.setAdapter(adapterLoaiSP);

        btn_them.setOnClickListener(v1 -> {
            SanPham sanPham = new SanPham();
            sanPham=arrSP.get(position);
            String LoaiSP = (String) spn_loaiSP.getSelectedItem();
            String[] maloai = LoaiSP.split("\\.");
            sanPham.setTenSP(ed_tenSanPham.getText().toString()+"");
            sanPham.setGiaNhap(Float.parseFloat(ed_giavon.getText().toString()));
            sanPham.setGiaBan(Float.parseFloat(ed_giaban.getText().toString()));
            sanPham.setLoaiSP(Integer.parseInt(maloai[0]));
            sanPham.setAnh(sanPham.getAnh());
 //            ((SanPhamActivity) context).addSP(sanPham);
            try {
                if (daoSanPham.updateSanPham(sanPham)){
                    Toast.makeText(context, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "Cập nhật sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        });
        btn_huy.setOnClickListener(v1 -> {
            dialog.cancel();
        });
    }
    private void dialogDelete(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                daoSanPham.deleteSanPham(sanPham.getMaSP());
                try {
                    arrSP = (ArrayList<SanPham>) daoSanPham.getListSanPham();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
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
                    arrSP = arrSP1;
                } else {
                    List<SanPham> listabc = new ArrayList<>();
                    for (SanPham hoaDonNhapKho1 : arrSP) {
                         if (hoaDonNhapKho1.getTenSP().toLowerCase(Locale.ROOT).contains(strsearch.toLowerCase(Locale.ROOT))) {
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


    public static class SanPhamViewHolder extends RecyclerView.ViewHolder{
        ImageView img_SP, img_delete;
        TextView tv_tenSP, tv_maSP;
        CardView cv_sanpham;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_sanpham=itemView.findViewById(R.id.cv_sanpham);
            img_SP=itemView.findViewById(R.id.imv_sanpham);
            tv_tenSP = itemView.findViewById(R.id.txt_tensanpham);
            tv_maSP = itemView.findViewById(R.id.txtgiasanpham);
            img_delete = itemView.findViewById(R.id.imv_delete);

        }
    }
}
