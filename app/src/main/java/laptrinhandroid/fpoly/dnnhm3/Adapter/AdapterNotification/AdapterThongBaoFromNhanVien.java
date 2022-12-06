package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterNotification;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.ThongBao;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ThongBaoNV;
import laptrinhandroid.fpoly.dnnhm3.Activity.MainActivity;
import laptrinhandroid.fpoly.dnnhm3.R;

public class AdapterThongBaoFromNhanVien extends RecyclerView.Adapter<AdapterThongBaoFromNhanVien.ThongBaoViewHolder> {
    List<ThongBaoNV> ThongBaoFromNhanVien;
    NhanVien mNV;

    public AdapterThongBaoFromNhanVien(NhanVien mNV) {
        this.mNV = mNV;
    }

    public void setData(List<ThongBaoNV> ThongBaoFromNhanVien) {
        this.ThongBaoFromNhanVien = ThongBaoFromNhanVien;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThongBaoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noti_to_nhanvien, null));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ThongBaoNV nhanVien = ThongBaoFromNhanVien.get(position);
        if (nhanVien.getDoc()) {
            holder.mRelativeLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
        if (nhanVien.getNgay() != null) {
            if (nhanVien.getNgay().equals(String.valueOf(new Date(System.currentTimeMillis())).toString())) {
                holder.ngay.setText("Ngày hôm nay");
            }
            holder.message.setText(nhanVien.getMessage()+"");
            holder.ngay.setText("Ngày " + nhanVien.getNgay());
            switch (nhanVien.getTrangThai()) {
                case "0":
                    holder.trangthai.setText("Trạng thái :Chưa xác nhận");
                    holder.trangthai.setTextColor(Color.parseColor("#FFE500"));
                    break;
                case "2":
                    holder.trangthai.setText("Trạng thái :Không xác nhận");
                    holder.trangthai.setTextColor(Color.parseColor("#FF1100"));

                case "1":
                    holder.trangthai.setText("Trạng thái :Đã xác nhận");
                    holder.trangthai.setTextColor(Color.parseColor("#1EA323"));

                    break;
            }
            holder.mRelativeLayout.setOnClickListener(view -> {
                try {
                    ThongBao.thongBaoNVDAO.updateThongBaoNhanVien(true, nhanVien.getId());
                    notifyDataSetChanged();
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("NV", mNV.getMaNv());
                    view.getContext().startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.d("sssssssss", "onBindViewHolder: "+e.getMessage());
                }

            });
        }

    }

    @Override
    public int getItemCount() {
        return ThongBaoFromNhanVien.size();
    }

    public class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        TextView ngay, trangthai, message;
        RelativeLayout mRelativeLayout;

        public ThongBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            trangthai = itemView.findViewById(R.id.trangthai);
            message = itemView.findViewById(R.id.message);
            mRelativeLayout = itemView.findViewById(R.id.mRelativeLayout);
            ngay = itemView.findViewById(R.id.ngay);
        }
    }
}
