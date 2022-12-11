package laptrinhandroid.fpoly.dnnhm3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.SanPhamKhoAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.R;

public class BaoCaoPhanTichActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    TextView tvTitle, tvMassege;
    SanPhamKhoAdapter sanPhamKhoAdapter;

    List<SanPham> list;
    DAOSanPham daoSanPham;

    int is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_phan_tich);
        anhXa();
        setToolbar();

        Bundle bundle = getIntent().getBundleExtra("bun");
        is = bundle.getInt("is");
        switch (is){
            case 0:{
                list = (List<SanPham>) bundle.getSerializable("data");
                break;
            }
            case 1:{
                list.addAll(daoSanPham.getListSanPhamBaoCao(0));
                tvTitle.setText("Tổng quan số lượng sản phẩm");
                break;
            }
            case 2:{
                list.addAll(daoSanPham.getListSanPhamBaoCao(1));
                tvTitle.setText("Sản phẩm còn bán");
                break;
            }
            case 3:{
                list.addAll(daoSanPham.getListSanPhamBaoCao(2));
                tvTitle.setText("Sản phẩm hết hàng");
                break;
            }


        }

        if (list != null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            sanPhamKhoAdapter = new SanPhamKhoAdapter(this, (ArrayList<SanPham>) list);
            recyclerView.setAdapter(sanPhamKhoAdapter);
        }



    }

    private void anhXa(){
        toolbar = findViewById(R.id.baoCaoPhantich_toolbar);
        tvTitle = findViewById(R.id.baocaoPhanTich_tv_title_sanpham);
        tvMassege = findViewById(R.id.baocaoPhanTich_tv_messege);
        recyclerView = findViewById(R.id.baocaoPhanTich_rcv);

        list = new ArrayList<>();
        daoSanPham = new DAOSanPham();
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //tắt title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //nut back
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list.size() != 0){
            tvMassege.setVisibility(View.GONE);
        }else {
            tvMassege.setVisibility(View.VISIBLE);
        }
    }

}