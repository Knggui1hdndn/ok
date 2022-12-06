/*
 *
 *   Created Your Name on 12/3/22, 2:00 AM
 *   Copyright Ⓒ 2022. All rights reserved Ⓒ 2022 http://freefuninfo.com/
 *   Last modified: 12/3/22, 2:00 AM
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENS... Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *    either express or implied. See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import laptrinhandroid.fpoly.dnnhm3.ConvertImg;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.BaoCao;
import laptrinhandroid.fpoly.dnnhm3.R;

public class TopSanPhamAdapter extends  RecyclerView.Adapter<TopSanPhamAdapter.ViewHolder> {
    Context context;
    List<BaoCao> list;
    int isCuaHang;

    ConvertImg convertImg;

    public TopSanPhamAdapter(Context context, List<BaoCao> list, int isCuahHang){
        this.context = context;
        this.list = list;
        this.isCuaHang = isCuahHang;
        convertImg = new ConvertImg();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topsanpham, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (isCuaHang){
            case 0:{
                SetDataTopSp(holder, position);
                break;
            }
            case 1:{
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSTT, tvName, tvTien, tvSoluong;
        ImageView imgAnh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSTT = itemView.findViewById(R.id.item_topsp_tv_id);
            tvName = itemView.findViewById(R.id.item_topsp_tv_name);
            tvTien = itemView.findViewById(R.id.item_topsp_tv_tien);
            tvSoluong = itemView.findViewById(R.id.item_topsp_tv_soluong);
            imgAnh = itemView.findViewById(R.id.item_topsp_img);
        }
    }

    private String forMatNumber(Double aDouble){
        DecimalFormat formatter  = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(aDouble);
    }

     private void SetDataTopSp(ViewHolder holder, int position){
         holder.tvSTT.setText(String.valueOf(position+1));
         holder.tvName.setText(String.valueOf(list.get(position).getSpTenSP()));
         holder.tvSoluong.setText("Sl: "+ String.valueOf(list.get(position).getCthdTongSoLuong()));
         holder.tvTien.setText("Doanh thu: "+ String.valueOf(forMatNumber(list.get(position).getCthdTongTien())+ " ₫"));
         holder.imgAnh.setImageBitmap(ConvertImg.convertBaseStringToBitmap(list.get(position).getSpAnh()));
     }
}
