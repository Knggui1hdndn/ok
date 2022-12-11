package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.SanPhamActivity;
import laptrinhandroid.fpoly.dnnhm3.Activity.hoadon11111;
import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class Sanphamadpterban extends RecyclerView.Adapter<Sanphamadpterban.viewholder> {
    Context context;
    List<SanPham> list;
    DAOSanPham daoSanPham;
    hoadon11111 hoaDon11111;
//    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");

    public Sanphamadpterban(hoadon11111 hoaDon11111, List<SanPham> list) {
        this.hoaDon11111 = hoaDon11111;
        this.list = list;
        daoSanPham = new DAOSanPham();
    }

    public Sanphamadpterban() {
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsanpham, parent, false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        int[] i = {0};
        if (position < list.size()) {
            SanPham sp = list.get(position);
            if (sp != null) {

                try {
                    holder.txtimg.setImageBitmap(ConvertImg.convertBaseStringToBitmap(sp.getAnh()));

                } catch (Exception e) {

                }
                holder.txten.setText(sp.getTenSP());
                holder.txtgia.setText(String.format("%.0f", sp.getGiaBan()) + " đ");
                holder.txtsoluong.setText("Đã bán: " + sp.getSoLuongDaBan() + "");
                holder.cardViewsp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sp.getSoLuong() < 1) {
                            holder.carviewadptersp.setVisibility(View.GONE);
                            Toast.makeText(hoaDon11111.getApplicationContext(), "hết hàng", Toast.LENGTH_SHORT).show();
                        } else {
                            holder.carviewadptersp.setVisibility(View.VISIBLE);
                            i[0] = 1;
                            hoaDon11111.aaa(sp, i[0]);
                        }
                    }
                });
                holder.BTN2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        SanPham sanPham1 = new SanPham();
                        sanPham1 = daoSanPham.getIdSP(String.valueOf(sp.getMaSP()));
                        if (sanPham1.getSoLuong() == i[0]) {
                            Toast.makeText(hoaDon11111.getApplicationContext(), "hết hàng", Toast.LENGTH_SHORT).show();
                        } else {
                            i[0]++;
                            if (i[0] <= 100) {
                                hoaDon11111.aaa(sp, i[0]);
                                holder.number.setText(i[0] + "");

                            }

//                              }
//
                        }

                    }
                });
                holder.BTN1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (i[0] == 1) {
                            holder.carviewadptersp.setVisibility(View.GONE);
                            i[0]--;
                            hoaDon11111.aaa(sp, i[0]);
                        } else {
                            i[0]--;
                            if (i[0] < 10) {
                                hoaDon11111.aaa(sp, i[0]);
                                holder.number.setText(i[0] + "");
                            }
                        }
                    }
                });
            }


        } else {
            if (list.size() == position) {
                holder.txten.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(hoaDon11111, SanPhamActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        hoaDon11111.startActivity(intent);
                    }
                });
            } else {
                holder.txten.setVisibility(View.GONE);
                holder.txtimg.setVisibility(View.GONE);
                holder.txtsoluong.setVisibility(View.GONE);
                holder.txtgia.setVisibility(View.GONE);
                holder.view.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        if (list.size() < 12) {
            return 12;
        } else {
            return list.size() + 1;
        }

    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView txten, txtgia, txtsoluong;
        ImageView txtimg;
        TextView number;
        TextView BTN1, BTN2;
        LinearLayout carviewadptersp;
        CardView cardViewsp;
        View view;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.viewgach);
            txten = itemView.findViewById(R.id.txttensanpham);
            txtgia = itemView.findViewById(R.id.Itemgia);
            txtsoluong = itemView.findViewById(R.id.Itemsolong);
            txtimg = itemView.findViewById(R.id.SpImg);
            BTN1 = itemView.findViewById(R.id.removeBtn);
            BTN2 = itemView.findViewById(R.id.addBtn);
            number = itemView.findViewById(R.id.itemQuanEt);
            carviewadptersp = itemView.findViewById(R.id.item_btn_number);
            cardViewsp = itemView.findViewById(R.id.carviewsp);

        }

    }


}