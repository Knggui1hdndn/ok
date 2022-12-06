package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterNotification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.GiaoDienChinh;
import laptrinhandroid.fpoly.dnnhm3.Activity.ListChamCongBangLuong;
import laptrinhandroid.fpoly.dnnhm3.Activity.ThongBao;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ThongBaoAdmin;

public class AdapterThongBaoFromAdmin extends RecyclerView.Adapter<AdapterThongBaoFromAdmin.ThongBaoViewHolder> {
    List<ThongBaoAdmin> thongBaoFromAdmins;
     Context context;
    public AdapterThongBaoFromAdmin(  Context context) {
        this.context = context;

    }

    public void setData(List<ThongBaoAdmin> thongBaoFromAdmins) {
        this.thongBaoFromAdmins = thongBaoFromAdmins;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThongBaoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noti_to_admin, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ThongBaoAdmin thongBaoAdmin = thongBaoFromAdmins.get(position);
        try {

            NhanVien nhanVien = GiaoDienChinh.nhanVien1.getListNhanVien(thongBaoAdmin.getMaNV());
            if (thongBaoAdmin.getDoc()) {
                holder.mRelativeLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            }
            holder.anh.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnh()));
            holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        holder.mRelativeLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                        ThongBao.thongBaoAdminDAO.updateThongBaoAdmin(true,thongBaoAdmin.getId());
                        Intent intent=new Intent(context, ListChamCongBangLuong.class);
                        intent.putExtra("maNV",nhanVien.getMaNv());
                        intent.putExtra("ngay",thongBaoAdmin.getNgay());
                        intent.putExtra("ad", "ad");

                        context.startActivity(intent);
                     } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.message.setText("Nhân viên " + nhanVien.getHoTen() + " yêu cầu xác nhận công");
            if(thongBaoAdmin.getNgay().equals(new Date(System.currentTimeMillis()))){
                holder.ngay.setText("Hôm nay");
            }else{
                holder.ngay.setText(thongBaoAdmin.getNgay());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return thongBaoFromAdmins.size();
    }

    public class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        TextView ngay, message;
        ImageView anh;
      RelativeLayout mRelativeLayout;
        public ThongBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.anh);
            message = itemView.findViewById(R.id.message);
            mRelativeLayout = itemView.findViewById(R.id.mRelativeLayout);
            ngay = itemView.findViewById(R.id.ngay);
        }
    }
}
