package laptrinhandroid.fpoly.dnnhm3.Fragment.Fragment_baocao;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao.BaocaoAdapterLich;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOBaoCao;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.BaoCao;
import laptrinhandroid.fpoly.dnnhm3.R;

public class FragmentLaiLo extends Fragment implements BaocaoAdapterLich.IsenDataTime{
    double i = 0;
    double tongThu, tongChi;

    TextView tvTime, tvLoiNhuan;
    TextView tvTienDoanhThu, tvTienVanChuyen, tvTienKhuyenMai, tvTienChietKhau, tvTienThueVAT, tvTienHangThucBan;
    TextView tvTienChiPhiBanHang, tvTienThanhToanBangDiem, tvTienChiPhiGiaoHang, tvTienGiaVonHangHoa;
    TextView tvTienThuNhapKhac;
    TextView tvTienChiPhiKhac;
    ImageView imgDoanhThu, imgChiPhi ;
    RelativeLayout rllDoanhThu, rllChiPhi;
    CardView cvDoanhThu, cvChiPhi, cvLich;
    ViewGroup viewGroup;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<BaoCao> listBaoCao = new ArrayList<>();
    Context mcontext;
    DAOBaoCao daoBaoCao;
    BottomSheetDialog sheetDialogLich;

    public FragmentLaiLo() {
        // Required empty public constructor
    }

    //Giá vốn hàng bán = Giá trị hàng tồn kho đầu kỳ + Hàng mua trong kỳ – Giá trị hàng tồn kho cuối kỳ

    // lợi nhuận = doanh thu bán hàng + lợi nhuận khác - chi phí bán hàng - chi phí khác

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lai_lo, container, false);
        anhxa(view);

        getAllDataByDate(0, Calendar.getInstance().getTime());

        cvLich.setOnClickListener(v -> {
            showButtonSheetDialog();
        });
        cvDoanhThu.setOnClickListener(v -> {
            clickAnimationCardView(rllDoanhThu, imgDoanhThu);
        });

        cvChiPhi.setOnClickListener(v ->{
            clickAnimationCardView(rllChiPhi, imgChiPhi);
        });

        return view;
    }

    private void anhxa(View view){
        tvTime = view.findViewById(R.id.frg_lailo_tv_time);
        tvLoiNhuan = view.findViewById(R.id.baocao_lailo_loinhuan);

        tvTienDoanhThu = view.findViewById(R.id.baocao_lailo_tv_tiendoanhthu);
        tvTienVanChuyen = view.findViewById(R.id.baocao_lailo_tv_tienthuphivanchuyen);
        tvTienKhuyenMai = view.findViewById(R.id.baocao_lailo_tv_tienkhuyenmai);
        tvTienChietKhau = view.findViewById(R.id.baocao_lailo_tv_tienchietkhau);
        tvTienThueVAT = view.findViewById(R.id.baocao_lailo_tv_tienthuevat);
        tvTienHangThucBan = view.findViewById(R.id.baocao_lailo_tv_tientongbangia);

        tvTienChiPhiBanHang = view.findViewById(R.id.baocao_lailo_tv_tienchiphibanhang);
        tvTienThanhToanBangDiem = view.findViewById(R.id.baocao_lailo_tv_tienthanhtoanbangdien);
        tvTienChiPhiGiaoHang = view.findViewById(R.id.baocao_lailo_tv_tienchiphigiaohangdoitac);
        tvTienGiaVonHangHoa = view.findViewById(R.id.baocao_lailo_tv_tiengiavonhanghoa);

        tvTienThuNhapKhac = view.findViewById(R.id.baocao_lailo_tv_tienthunhapkhac);
        tvTienChiPhiKhac = view.findViewById(R.id.baocao_lailo_tv_tienchiphikhac);

        cvDoanhThu = view.findViewById(R.id.baocao_lailo_cardview_doanhthu);
        cvChiPhi = view.findViewById(R.id.baocao_lailo_cardview_chiphibanhang);
        cvLich = view.findViewById(R.id.frg_lailo_cardview_time);
        viewGroup = view.findViewById(R.id.baocao_lailo_scrollView);
        imgDoanhThu= view.findViewById(R.id.baocao_lailo_img_doanhthu);
        imgChiPhi= view.findViewById(R.id.baocao_lailo_img_tienchiphibanhang);
        rllDoanhThu = view.findViewById(R.id.baocao_lailo_rcv_doanhthu);
        rllChiPhi = view.findViewById(R.id.baocao_lailo_rcv_chiphibanhang);


        mcontext = getActivity();
        daoBaoCao = new DAOBaoCao();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAllDataByDate(int positon , Date date){
        try {
            listBaoCao.clear();
            listBaoCao.addAll(daoBaoCao.getListLaiLo(positon ,date));
            setUpData();
            Log.i("lengthListlailo",  "Length " + listBaoCao.size() );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUpData(){
        double doanhThu = getDoanhThu();
        double tienChiPhiBanHang = getTienChiPhiBanHang();
        double tienThuNhapKhac = getTienTienThuNhapKhac();
        double tienChiPhiKhac = getTienChiPhiKhac();
        double loinhuan = getDoanhThu() - tienChiPhiBanHang + tienChiPhiKhac - tienChiPhiKhac;

        tvTienDoanhThu.setText(String.valueOf(forMatNumber(doanhThu) ));
        tvTienChiPhiBanHang.setText(String.valueOf(forMatNumber(tienChiPhiBanHang)));
        tvTienThuNhapKhac.setText(String.valueOf(forMatNumber(tienThuNhapKhac)));
        tvTienChiPhiKhac.setText(String.valueOf(forMatNumber(tienChiPhiKhac)));

        runLoiNhuan(loinhuan);
    }

    private double getDoanhThu(){
        Double tongtien = getTienVanChuyen() + getTienKhuyenMai() + getTienChietKhau() + getTienThueVAT() + getTienhangThucBan();
        return tongtien;
    }
    private double getTienVanChuyen(){
        double tongtien = 0;
        tvTienVanChuyen.setText(String.valueOf(forMatNumber(tongtien)));
        return tongtien;
    }

    private double getTienKhuyenMai(){
        double tongtien = 0;
        tvTienKhuyenMai.setText(String.valueOf(forMatNumber(tongtien)));
        return tongtien;
    }

    private double getTienChietKhau(){
        double tongtien = 0;
        tvTienChietKhau.setText(String.valueOf(forMatNumber(tongtien)));
        return tongtien;
    }

    private double getTienThueVAT(){
        double tongtien = 0;
        tvTienThueVAT.setText(String.valueOf(forMatNumber(tongtien)));
        return tongtien;
    }

    private double getTienhangThucBan(){
        double tongtien = 0;
        for (BaoCao bc: listBaoCao){
            tongtien += (bc.getCthdSoLuong()* bc.getSpGiaBan());
        }
        tvTienHangThucBan.setText(String.valueOf(forMatNumber(tongtien)));
        return tongtien;
    }

    private double getTienChiPhiBanHang(){
        double tongtien = getTienThanhToanBangDiem() + getTienChiPhiGiaoHang() + getGiaVonHangHoa();
        return tongtien;
    }

    private double getTienThanhToanBangDiem(){
        double tongtien = 0;
        tvTienThanhToanBangDiem.setText(String.valueOf(forMatNumber(tongtien)));
        return tongtien;
    }

    private double getTienChiPhiGiaoHang(){
        double tongtien = 0;
        tvTienChiPhiGiaoHang.setText(String.valueOf(forMatNumber(tongtien)));
        return tongtien;
    }

    private double getGiaVonHangHoa(){
        double tongtien = 0;
        for (BaoCao bc: listBaoCao){
            tongtien += (bc.getCthdSoLuong()* bc.getSpGiaNhap());
        }
        tvTienGiaVonHangHoa.setText(String.valueOf(forMatNumber(tongtien)));
        return tongtien;
    }
    private double getTienTienThuNhapKhac(){
        double tongtien = 0;
        return tongtien;
    }

    private double getTienChiPhiKhac(){
        double tongtien = 0;
        return tongtien;
    }

    private void runLoiNhuan(double loinhuan){
        i = 0;
        Handler handler1 = new Handler();
        handler1.post(new Runnable() {
            @Override
            public void run() {
                if (i <= loinhuan){
                    i = i + 1234;
                    tvLoiNhuan.setText(forMatNumber(i )+ " ₫");
                }else {
                    tvLoiNhuan.setText(forMatNumber(loinhuan) + " ₫");
                    return;
                }
                handler1.postDelayed(this, 1);
            }
        });
        tvLoiNhuan.setText(forMatNumber(loinhuan) + " ₫");
    }

    private void clickAnimationCardView(RelativeLayout relativeLayout, ImageView imageView){
        TransitionManager.beginDelayedTransition(viewGroup);;
        if (relativeLayout.getVisibility() == View.GONE) {
            relativeLayout.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.icon_down);

        } else {
            relativeLayout.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.icon_right);
        }
    }

    private void showButtonSheetDialog() {
        sheetDialogLich = new BottomSheetDialog(getContext());
        sheetDialogLich.setContentView(getLayoutInflater().inflate(R.layout.dialog_button_sheet_baocao_lich, null));
        sheetDialogLich.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sheetDialogLich.show();

        RecyclerView recyclerView = sheetDialogLich.findViewById(R.id.dialog_buttomshett_baocao_lich_rcv);

        List<String> listlich = new ArrayList<>();
        listlich.add("Hôm nay");
        listlich.add("Tuần này");
        listlich.add("Tháng này");
        listlich.add("Tuần trước");
        listlich.add("Tháng trước");
        listlich.add("Thời gian khác");

        BaocaoAdapterLich baocaoAdapterLich = new BaocaoAdapterLich(getActivity(), listlich, this);
        recyclerView.setAdapter(baocaoAdapterLich);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);   //dòng kẻ giữa mỗi item
        recyclerView.addItemDecoration(itemDecoration);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void sendData(int time){
        setChoise(time);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setChoise(int choice) {
        Calendar calendar = Calendar.getInstance();
        switch (choice) {
            case 0:
                tvTime.setText("Hôm nay");
                getAllDataByDate(0, Calendar.getInstance().getTime());
                break;
            case 1:
                tvTime.setText("Tuần này");
                getAllDataByDate(1, Calendar.getInstance().getTime());
                break;
            case 2:
                tvTime.setText("Tháng này");
                getAllDataByDate(2, Calendar.getInstance().getTime());
                break;
            case 3:
                tvTime.setText("Tuần trước");
                getAllDataByDate(3, Calendar.getInstance().getTime());
                break;
            case 4:
                tvTime.setText("Tháng trước");
                getAllDataByDate(4, Calendar.getInstance().getTime());
                break;
            case 5:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month,dayOfMonth);
                        tvTime.setText(simpleDateFormat.format(calendar.getTime()));
                        getAllDataByDate(5, calendar.getTime());
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

//                materialDatePicker.show(getActivity().getSupportFragmentManager(), "ALO");

                break;
        }
        sheetDialogLich.dismiss();
    }

    private String forMatNumber(Double aDouble){
        DecimalFormat formatter  = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(aDouble);
    }

}