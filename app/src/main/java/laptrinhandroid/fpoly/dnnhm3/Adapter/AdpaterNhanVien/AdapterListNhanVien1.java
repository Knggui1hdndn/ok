package laptrinhandroid.fpoly.dnnhm3.Adapter.AdpaterNhanVien;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.HashMap;

import laptrinhandroid.fpoly.dnnhm3.Activity.GiaoDienChinh;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.R;

public class AdapterListNhanVien1 extends RecyclerView.Adapter<AdapterListNhanVien1.AdapterListNhanVienViewholder> {
    HashMap<Integer, Integer> hashMap;


    public void setData(HashMap<Integer, Integer> hashMap) {
        this.hashMap = hashMap;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AdapterListNhanVienViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterListNhanVienViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nv2, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListNhanVienViewholder holder, @SuppressLint("RecyclerView") int position) {
        Integer firstKey = (Integer) hashMap.keySet().toArray()[position+3];
        Integer valueForFirstKey = hashMap.get(firstKey);
        try {
            NhanVien nhanVien = GiaoDienChinh.nhanVien1.getListNhanVien(firstKey);
            holder.sdt2.setText(valueForFirstKey + " giờ làm");
            holder.name2.setText(nhanVien.getHoTen());
            holder.img2.setImageBitmap(ConvertImg.convertBaseStringToBitmap(nhanVien.getAnh()));
            holder.aa2.setText(position+3+"");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if(hashMap.size()>3){
            return hashMap.size();

        }
        return 0;
    }

    public class AdapterListNhanVienViewholder extends RecyclerView.ViewHolder {
        ImageView img2;
        TextView sdt2, name2,aa2;
        RelativeLayout mRelativeLayout;

        public AdapterListNhanVienViewholder(@NonNull View itemView) {
            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.mRelativeLayout);
            aa2 = itemView.findViewById(R.id.aa2);
            sdt2 = itemView.findViewById(R.id.sdt2);
            name2 = itemView.findViewById(R.id.name2);
            img2 = itemView.findViewById(R.id.img2);
        }
    }
}
