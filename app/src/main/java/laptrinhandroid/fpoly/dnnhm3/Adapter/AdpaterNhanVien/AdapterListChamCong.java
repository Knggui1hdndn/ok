package laptrinhandroid.fpoly.dnnhm3.Adapter.AdpaterNhanVien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.ListChamCongBangLuong;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChamCong;
import laptrinhandroid.fpoly.dnnhm3.R;

public class AdapterListChamCong extends RecyclerView.Adapter<AdapterListChamCong.AdapterListChamCongViewHolder> {
    public List<ChamCong> chamCongs;

    public void setData(List<ChamCong> chamCongs) {
        this.chamCongs = chamCongs;
    }

    @NonNull
    @Override
    public AdapterListChamCongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterListChamCongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cham_cong, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListChamCongViewHolder holder, int position) {
        ChamCong chamCong = chamCongs.get(position);
        holder.gioBatDau.setText("Giờ bắt đầu:" + chamCong.getGioBatDau());
        holder.gioKetThuc.setText("Giờ kết thúc:" + chamCong.getGioKetThuc());
        holder.macong.setText("Mã công:" + chamCong.getMaCong());
        holder.ngay.setText("Ngày " + chamCong.getNgay());
        holder.show.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                holder.mLinearLayout.setVisibility(View.VISIBLE);
            } else {
                holder.mLinearLayout.setVisibility(View.GONE);
            }
        });
        switch (chamCong.getXacNhanChamCong()) {
            case 1:
                ((RadioButton) holder.radioGroup.getChildAt(1)).setChecked(true);

                break;
            case 2:
                ((RadioButton) holder.radioGroup.getChildAt(2)).setChecked(true);

                break;
            case 0:
                ((RadioButton) holder.radioGroup.getChildAt(0)).setChecked(true);
                break;
        }
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int a) {
                for (int i = 0; i < 3; i++) {
                    if (((RadioButton) radioGroup.getChildAt(i)).isChecked()) {
                        chamCong.setXacNhanChamCong(i);
                        break;
                    }
                }

                ((ListChamCongBangLuong) radioGroup.getContext()).sendData(chamCong);

            }
        });
        holder.relativeLayout.setOnClickListener(view -> {


        });
    }

    @Override
    public int getItemCount() {
        return chamCongs.size();
    }


    public class AdapterListChamCongViewHolder extends RecyclerView.ViewHolder {
        public TextView ngay, macong, gioBatDau, gioKetThuc;
        CheckBox show;
        RadioGroup radioGroup;
        public RelativeLayout relativeLayout;
        LinearLayout mLinearLayout;

        public AdapterListChamCongViewHolder(@NonNull View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.mLinearLayout);
            relativeLayout = itemView.findViewById(R.id.mRelativeLayout);
            macong = itemView.findViewById(R.id.macong);
            ngay = itemView.findViewById(R.id.ngay);
            gioBatDau = itemView.findViewById(R.id.gioBatDau);
            gioKetThuc = itemView.findViewById(R.id.gioKetThuc);
            show = itemView.findViewById(R.id.show);
            radioGroup = itemView.findViewById(R.id.radioGroup);

        }
    }
}
