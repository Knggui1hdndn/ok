package laptrinhandroid.fpoly.dnnhm3.Adapter.AdpaterNhanVien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.QuanLiNhanVien;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.Fragment.NhanVien.BottomSheetNhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;

public class AdapterListNhanVien extends RecyclerView.Adapter<AdapterListNhanVien.AdapterListNhanVienViewholder> {
    List<NhanVien> nhanViens;
    QuanLiNhanVien quanLiNhanVien;

    public List<NhanVien> getNhanViens() {
        return nhanViens;
    }

    public void setData(List<NhanVien> nhanViens) {
        this.nhanViens = nhanViens;
        notifyDataSetChanged();
    }

    ;

    public AdapterListNhanVien(QuanLiNhanVien quanLiNhanVien) {
        this.quanLiNhanVien = quanLiNhanVien;

    }

    @NonNull
    @Override
    public AdapterListNhanVienViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterListNhanVienViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhanvien, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListNhanVienViewholder holder, int position) {
        NhanVien nhanVien = nhanViens.get(position);
        holder.img.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnh()));
        holder.name.setText(nhanVien.getHoTen());
        holder.sdt.setText(nhanVien.getSoDT());
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetNhanVien bottomSheetNhanVien = new BottomSheetNhanVien();
                Bundle bundle = new Bundle();
                bundle.putInt("maNV", nhanVien.getMaNv());
                bundle.putInt("i", position);
                bottomSheetNhanVien.setArguments(bundle);
                bottomSheetNhanVien.show(quanLiNhanVien.getSupportFragmentManager(), String.valueOf(nhanVien.getMaNv()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return nhanViens.size();
    }

    public class AdapterListNhanVienViewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView sdt, name;
        RelativeLayout mRelativeLayout;

        public AdapterListNhanVienViewholder(@NonNull View itemView) {
            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.mRelativeLayout);
            sdt = itemView.findViewById(R.id.sdt);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
        }
    }
}
