package laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.KhachHang;
import laptrinhandroid.fpoly.dnnhm3.R;

public class Spinerkhachhang extends ArrayAdapter<KhachHang> {
    private Context context;
    List<KhachHang> list;
    TextView txtid,txtname;

    public Spinerkhachhang(@NonNull Context context, List<KhachHang> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if(v==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.spinernhanvien,null);

        }
        KhachHang tv= list.get(position);
        if (tv!=null){
            txtid=v.findViewById(R.id.spinertv1);
            txtid.setText(String.valueOf(tv.getHoTen()));

        }
        return v;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if(v==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.spinernhanvien,null);
        }
        KhachHang tv= list.get(position);
        if (tv!=null){
            txtid=v.findViewById(R.id.spinertv1);
            txtid.setText(String.valueOf(tv.getHoTen()));
        }
        return v;
    }
}
