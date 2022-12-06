package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterSanPham;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.banhang;
import laptrinhandroid.fpoly.dnnhm3.R;

public class Sanphamadpter extends RecyclerView.Adapter<Sanphamadpter.viewholder> {
    private Context context;
    List<SanPham> list;
    DAOSanPham daoSanPham;
//    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");

    public Sanphamadpter(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
        daoSanPham = new DAOSanPham();
    }

    public Sanphamadpter() {
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemsanpham, parent, false);
        return new viewholder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        SanPham hd = list.get(position);
        holder.txtmanv.setText(String.valueOf(hd.getTenSP()));
        holder.txtimg.setImageResource(R.drawable.group);
//        holder.txttongtien.setText(String.valueOf(hd.getTongTien()));
//        holder.txtmanv.setText(String.valueOf(hd.getMaNV()));
//           holder.edgiathue.setText(String.valueOf(loaisach.getGiathue()));
        holder.cardViewsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.cardViewsp.setVisibility(View.VISIBLE);
                holder.txtimg.setVisibility(View.GONE);


            }
        });


        int[] i = {0};
        holder.BTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i[0]++;

                if (i[0] <= 10) {
                    holder.editText.setText(i[0] + "");
                }
                if (i[0] >= 1) {
                    holder.editText.getText();
                    Toast.makeText(context, "abc", Toast.LENGTH_SHORT).show();

                }
            }
        });
        holder.editText.addTextChangedListener(new TextWatcher() {
            CoordinatorLayout coordinatorLayout;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                Toast.makeText(context.getApplicationContext(), Integer.parseInt(charSequence.toString())*2+"", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(holder.cardViewsp, Integer.parseInt(charSequence.toString()) * 2 + "", Snackbar.LENGTH_LONG)
                        .setAction("Undo Clicked", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, banhang.class);
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);

                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();

                snackbar.show();
                snackbar.show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i[0]--;
                if (i[0] >= 0) {
                    holder.editText.setText(i[0] + "");
                    if (i[0] == 0) {
                        holder.cardViewsp.setVisibility(View.GONE);
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtmanv;
        ImageView txtimg;
        EditText editText;
        Button BTN1, BTN2;
         CardView cardViewsp;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtmanv = itemView.findViewById(R.id.txttensanpham);
            txtimg = itemView.findViewById(R.id.SpImg);
            BTN1 = itemView.findViewById(R.id.removeBtn);
            BTN2 = itemView.findViewById(R.id.addBtn);
            editText = itemView.findViewById(R.id.itemQuanEt);

            cardViewsp = itemView.findViewById(R.id.carviewsp);

        }

    }


}
