package laptrinhandroid.fpoly.dnnhm3.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterHoaDon.Sanphamadpterban;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class hoadon11111 extends AppCompatActivity implements AAA{
    RecyclerView recyclerView;
    Toolbar toolbar;
    DAOSanPham daoSanPham;
    Sanphamadpterban sanphamadpterban;
    List<SanPham> list;
    CardView cardView;
    List<SanPham>sanPhams;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon11111);
        recyclerView=findViewById(R.id.rcvnhanvien);
        toolbar=findViewById(R.id.toolbar2);
        toolbar.setTitle("Chọn sản phẩm");
        cardView=findViewById(R.id.Carviewaa);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_24);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        daoSanPham= new DAOSanPham();
        sanPhams=new ArrayList<>() ;
        DAOSanPham daoSanPham=new DAOSanPham();

         try {
            list= daoSanPham.getListSanPham();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sanphamadpterban = new Sanphamadpterban(this,list);
        recyclerView.setAdapter(sanphamadpterban);


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void aaa(SanPham sanPham,int soLuong) {

        sanPhams.remove(sanPham);
        if (soLuong != 0) {
            sanPham.setSoLuong(soLuong);
            sanPhams.add(sanPham);
        }
        long l = 0;
        for (SanPham context : sanPhams) {
            l += (context.getSoLuong() * context.getGiaBan());

        }

        long finalL = l;
        Snackbar snackbar = Snackbar
                .make(toolbar, l + "", Snackbar.LENGTH_LONG)
                .setAction("Tiep tuc", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (finalL != 0) {
                            Intent intent = new Intent(hoadon11111.this, Chitiethoadon.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("key", (Serializable) sanPhams);
                            intent.putExtra("maNV", getIntent().getIntExtra("maNV",0));
                            startActivity(intent);
                            finish();
                        }


                    }
                });
        snackbar.setActionTextColor(Color.BLACK);

        snackbar.show();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
