package laptrinhandroid.fpoly.dnnhm3.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;
import java.util.ArrayList;

import laptrinhandroid.fpoly.dnnhm3.Adapter.AdapterKho.SanPhamChonAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOSanPham;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.Interface.SanPhamInterface;
import laptrinhandroid.fpoly.dnnhm3.R;


public class ChoseProducts extends AppCompatActivity implements SanPhamInterface {
    Toolbar toolbar_chosepr;
    LayoutInflater inflater;
    EditText txt_search;
    ArrayList<SanPham> arrSP = new ArrayList<>();
    ArrayList<SanPham> arrSP1 = new ArrayList<>();
    RecyclerView rcySP;
    ExtendedFloatingActionButton btn_continute;
    DAOSanPham daoSanPham;
    SanPhamChonAdapter adapter;
int maNV;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_products);
        toolbar_chosepr=findViewById(R.id.toolbar_chosepr);
        toolbar_chosepr.setTitleTextColor(Color.WHITE);
        toolbar_chosepr.setTitle("Chọn sản phẩm");
        setSupportActionBar(toolbar_chosepr);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

        inflater = getLayoutInflater();
        txt_search=findViewById(R.id.txt_search_kho_productChose);
        rcySP= findViewById(R.id.recyclerview_lsProductChose);
        btn_continute=findViewById(R.id.btn_continute);
        maNV=getIntent().getIntExtra("maNV",0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcySP.setLayoutManager(layoutManager);
        btn_continute.setVisibility(View.GONE);
        daoSanPham = new DAOSanPham();
        try {
            arrSP = (ArrayList<SanPham>) daoSanPham.getListSanPham();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("loiii", "onViewCreated: "+e.getMessage());
        }
        adapter = new SanPhamChonAdapter(this, arrSP);
        rcySP.setAdapter(adapter);
//        txt_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                arrSP.clear();
//                //hàm lấy ra danh sách sp
//                //hiện lên adapter
//                arrSP= (ArrayList<SanPham>) daoSanPham.getSearchSP(txt_search.getText().toString());
//                adapter = new SanPhamChonAdapter(ChoseProducts.this, (ArrayList<SanPham>) arrSP);
//                rcySP.setAdapter(adapter);
//            }
//        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.item_kho, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void sanPhamInterface(SanPham sanPham,int soLuong) {
        arrSP1.remove(sanPham);
        if (soLuong!=0) {
            sanPham.setSoLuong(soLuong);
            Log.d("soLuong", "sanPhamInterface: " + soLuong);
            arrSP1.add(sanPham);
        }
        long l=0;
        for (SanPham context:arrSP1){
            l+=(context.getSoLuong()*context.getGiaNhap());
        }

        long finalL = l;
        Snackbar snackbar = Snackbar
                .make(toolbar_chosepr, l+"" , 60000)
                .setAction("Tiep tuc", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (finalL !=0) {
                            Intent intent = new Intent(ChoseProducts.this, ChiTietHoaDonNhapActivity.class);
                            intent.putExtra("key", arrSP1);
                            intent.putExtra("maNV",maNV);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(ChoseProducts.this, "Hãy chọn sản phẩm", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        snackbar.show();

    }
    public boolean CheckGia(float soTien){
        if(soTien<1){
            return false;
        }
        return true;
    }

}