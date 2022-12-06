package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon;

import android.content.Context;
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
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class Adapterchitiet extends RecyclerView.Adapter<Adapterchitiet.viewholder> {
    Context context;
    List<SanPham> list= new ArrayList<>();
    DAOSanPham daoSanPham;

    public Adapterchitiet(Context context, List<SanPham>list) {
        this.context = context;
        this.list = list;
        daoSanPham = new DAOSanPham();
    }

    public Adapterchitiet() {
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.itemchitiethoadon,parent,false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        SanPham sp = list.get(position);
        try {
            holder.imageView.setImageBitmap(ConvertImg.convertBaseStringToBitmap(sp.getAnh()));

        }catch (Exception e){

        }
        holder.txtname.setText(String.valueOf(sp.getTenSP()));
        holder.txtsoluong.setText("số lượng: "+(sp.getSoLuong()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtname,txtsoluong;
        ImageView imageView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.CTHD_img);
            txtname=itemView.findViewById(R.id.CTHD_namesp);
            txtsoluong=itemView.findViewById(R.id.CTHD_soluong);
//



        }
    }
}
