package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterSanPham;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;

import laptrinhandroid.fpoly.dnnhm3.DAO.DAOLoaiSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.LoaiSP;
import laptrinhandroid.fpoly.dnnhm3.R;

public class LoaiSanPhamAdapter extends RecyclerView.Adapter<LoaiSanPhamAdapter.userViewHolder> {
    private Context context;

    ArrayList<LoaiSP> arrayList;
    DAOLoaiSanPham daoLoaiSanPham = new DAOLoaiSanPham();


    public LoaiSanPhamAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<LoaiSP> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhmuc, parent, false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        LoaiSP loaiSP = arrayList.get(position);

        holder.tv_tenLoai.setText("" + loaiSP.getTenLoai());
        holder.img_delete.setOnClickListener(v -> {
            dialogDelete(loaiSP, position);
        });


    }


    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    private void dialogDelete(LoaiSP loaiSP, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(daoLoaiSanPham.deleteLoaiSanPham(loaiSP.getMaLoai())){
                    arrayList.remove(loaiSP);
                    notifyItemRemoved(i);
                    Toast.makeText(context, "Xóa thành công ", Toast.LENGTH_SHORT).show();
                }else {{
                    Toast.makeText(context, "Xóa Thất bại ", Toast.LENGTH_SHORT).show();

                }}

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public class userViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tenLoai;
        private ImageView img_delete;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenLoai = itemView.findViewById(R.id.txt_tendanhmuc);
            img_delete = itemView.findViewById(R.id.imv_deleteDM);
        }
    }
}
