package laptrinhandroid.fpoly.dnnhm3.Fragment.Fragment_baocao;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import laptrinhandroid.fpoly.dnnhm3.Activity.BaoCaoDonhangActivity;
import laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao.BaocaoAdapterLich;
import laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao.TopSanPhamAdapter;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOBaoCao;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.BaoCao;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.R;

public class FragmentCuaHang extends Fragment implements BaocaoAdapterLich.IsenDataTime {

    int i = 0;
    int isNgay = 0;
    double doanhthu;
    Date isDate;

    CardView cvTime, cvDonHang, cvDonhuy;
    TextView tvTime, tvDoanhThu, tvDonHang, tvDonHuy;
    LineChart lineChart;
    RecyclerView recyclerView;

    TopSanPhamAdapter topSanPhamAdapter;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    List<HoaDonBan> listHoaDonBan;
    List<BaoCao> listTopSp;
    Context mcontext;
    DAOBaoCao daoBaoCao;
    BottomSheetDialog sheetDialogLich;
    private LineGraphSeries<DataPoint> line1;

    public FragmentCuaHang() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cua_hang, container, false);
        anhxa(view);

        getAllDataByDate(0, Calendar.getInstance().getTime());

        cvTime.setOnClickListener(v -> {
            showButtonSheetDialog();
        });

        cvDonHang.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BaoCaoDonhangActivity.class);
            Bundle bundle = new Bundle();
            if (listHoaDonBan.size() > 0)
                bundle.putSerializable("data", (Serializable) listHoaDonBan);
            else bundle.putSerializable("data", null);
            bundle.putBoolean("is", true);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        cvDonhuy.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BaoCaoDonhangActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", null);
            bundle.putBoolean("is", false);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        return view;
    }

    private void anhxa(View view) {
        tvTime = view.findViewById(R.id.frg_cuahang_tv_time);
        cvTime = view.findViewById(R.id.frg_cuahang_cardview_time);
        cvDonHang = view.findViewById(R.id.cuahang_cv_donhang);
        cvDonhuy = view.findViewById(R.id.cuahang_cv_donhuy);
        tvDoanhThu = view.findViewById(R.id.cuahang_tv_doanhthu);
        tvDonHang = view.findViewById(R.id.cuahang_tv_soluongdonhang);
        tvDonHuy = view.findViewById(R.id.cuahang_tv_soluongdonhuy);
        lineChart = view.findViewById(R.id.cuahang_graphview);
        recyclerView = view.findViewById(R.id.cuahang_rcv_top);

        listHoaDonBan = new ArrayList<>();
        daoBaoCao = new DAOBaoCao();
        listTopSp = new ArrayList<>();
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
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);   //dòng kẻ giữa mỗi item
        recyclerView.addItemDecoration(itemDecoration);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void sendData(int time) {
        setChoise(time);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setChoise(int choice) {
        Calendar calendar = Calendar.getInstance();
        switch (choice) {
            case 0:
                tvTime.setText("Hôm nay");
                isNgay = 0;
                getAllDataByDate(0, Calendar.getInstance().getTime());
                break;
            case 1:
                tvTime.setText("Tuần này");
                isNgay = 1;
                getAllDataByDate(1, Calendar.getInstance().getTime());
                break;
            case 2:
                tvTime.setText("Tháng này");
                isNgay = 2;
                getAllDataByDate(2, Calendar.getInstance().getTime());
                break;
            case 3:
                tvTime.setText("Tuần trước");
                isNgay = 3;
                getAllDataByDate(3, Calendar.getInstance().getTime());
                break;
            case 4:
                tvTime.setText("Tháng trước");
                isNgay = 4;
                getAllDataByDate(4, Calendar.getInstance().getTime());
                break;
            case 5:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        tvTime.setText(simpleDateFormat.format(calendar.getTime()));
                        getAllDataByDate(5, calendar.getTime());
                        isNgay = 5;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

//                materialDatePicker.show(getActivity().getSupportFragmentManager(), "ALO");

                break;
        }
        sheetDialogLich.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAllDataByDate(int positon, Date date) {
        try {
            isDate = date;      //get date được chọn để set biểu đồ
            listHoaDonBan.clear();
            listTopSp.clear();
            listHoaDonBan.addAll(daoBaoCao.getListHoaDonBanByDay(positon, date));
            listTopSp.addAll(daoBaoCao.getListTopSanPham(positon, date));
            setUpData();
            setUpChart();
            setUpTopSP();
            Log.i("lengthListlailo", "Length " + listHoaDonBan.size());
            Log.i("lengthListlailo", "Length TOP SP " + listTopSp.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUpData() {
        getSumDonHang();
        getSumDonHuy();
        doanhthu = getDoanhThu();
    }

    private Double getDoanhThu() {
        double tong = 0;
        for (HoaDonBan hd : listHoaDonBan) {
            tong += hd.getTongTien();
        }
        runTien(tong);
        return tong;
    }

    private void runTien(double tong) {
        i = 0;
        Handler handler1 = new Handler();
        handler1.post(new Runnable() {
            @Override
            public void run() {
                if (i <= tong) {
                    i = i + 5234;
                    tvDoanhThu.setText(forMatNumber((double) i) + " ₫");
                } else {
                    tvDoanhThu.setText(forMatNumber(tong) + " ₫");
                    return;
                }
                handler1.postDelayed(this, 1);
            }
        });
        tvDoanhThu.setText(forMatNumber(tong) + " ₫");
    }

    private int getSumDonHang() {
        int sum = 0;
        for (HoaDonBan hd : listHoaDonBan) {
            if (hd.getTongTien() > 0) {
                ++sum;
            }
        }
        tvDonHang.setText(String.valueOf(sum));
        return sum;
    }

    private int getSumDonHuy() {
        int sum = 0;
        for (HoaDonBan hd : listHoaDonBan) {
            if (hd.getTongTien() == 0) {
                ++sum;
            }
        }
        tvDonHuy.setText(String.valueOf(sum));
        return sum;
    }

    private String forMatNumber(Double aDouble) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(aDouble);
    }

    private void setUpTopSP() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        topSanPhamAdapter = new TopSanPhamAdapter(getContext(), listTopSp, 0);
        recyclerView.setAdapter(topSanPhamAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));   //kẻ ngang

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpChart() {

        lineChart.clear();
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(true);

        LineDataSet set1 = new LineDataSet(getValuesChart(true), "Đơn hàng");
        set1.setDrawCircles(false);
        set1.setFillAlpha(110);
        set1.setColor(getResources().getColor(R.color.blue));
        set1.setLineWidth(3f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);

        //lấy trục x từ biểu đồ
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyXValueFormat());

        xAxis.setGranularity(1f);       //giới hạn giá trị


    }


    public class MyXValueFormat extends ValueFormatter implements IAxisValueFormatter {
        private ArrayList<String> list;
        public MyXValueFormat(){
        }
        public MyXValueFormat(ArrayList<String> list){
            this.list = list;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String ngay = list.get((int) value);
            return value + "$";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Entry> getValuesChart(boolean isThu) {
        ArrayList<Entry> values = new ArrayList<>();

        int tongNgay = 0;
        int isLoaiNgay = 0;
        if (isNgay == 0 || isNgay == 5) {
            isLoaiNgay = 0;
            tongNgay = 1;
        } else if (isNgay == 1 || isNgay == 3) {
            isLoaiNgay = 2;
            tongNgay = 7;
        } else {
            isLoaiNgay = 3;
            //check tháng trong list
            if (listHoaDonBan.size() > 0) {
                int thang = Integer.parseInt(sdfMonth.format(listHoaDonBan.get(0).getNgayBan()));
                if (thang == 1 || thang == 3 || thang == 3 || thang == 3 || thang == 3 || thang == 3 || thang == 3)
                    tongNgay = 31;
                else if (thang == 2) tongNgay = 28;
                else tongNgay = 30;
                // không thì lấy tháng hiện tại
            } else {
                int thang = Integer.parseInt(sdfMonth.format(Calendar.getInstance().getTime()));
                if (thang == 1 || thang == 3 || thang == 3 || thang == 3 || thang == 3 || thang == 3 || thang == 3)
                    tongNgay = 31;
                else if (thang == 2) tongNgay = 28;
                else tongNgay = 30;
            }
        }

//        for (int i = 1; i <= tongNgay; i++) {
//            int sodon = 0;
//            for (HoaDonBan hb : listHoaDonBan) {
//                if (i == Integer.parseInt(sdfDate.format(hb.getNgayBan()))) {
//                    sodon++;
//                }
//            }
//            values.add(new Entry(i, Float.valueOf(sodon)));
//        }

//GETTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
        // loại ngày 3 là 30 ngày
        if (isLoaiNgay == 3) {
            for (int i = 1; i <= tongNgay; i++) {
                int sodon = 0;
                for (HoaDonBan hb : listHoaDonBan) {
                    if (i == Integer.parseInt(sdfDate.format(hb.getNgayBan()))) {
                        sodon++;
                    }
                }
                values.add(new Entry(i, Float.valueOf(sodon)));
            }
        }
        //loại ngày 2 là 7 ngày
        else if (isLoaiNgay == 2) {
            //lấy ngày đầu tuần ở daoBaoCao
            Calendar cal = Calendar.getInstance();
            if (listHoaDonBan.size() > 0) cal.setTime(listHoaDonBan.get(0).getNgayBan());
            else cal.getTime();
            LocalDate localDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
            String stringngaydautuan = daoBaoCao.getFirstDayThisWeek(localDate, cal).substring(8); //cắt 8 ký tự đầu
            int ngaydautuan = Integer.parseInt(stringngaydautuan);

            for (int i = ngaydautuan; i <= ngaydautuan + 6; i++) {
                int sodon = 0;
                for (HoaDonBan hb : listHoaDonBan) {
                    int ngayHint = i;
                    if (ngayHint > 30) ngayHint -= 30;
                    if (ngayHint == Integer.parseInt(sdfDate.format(hb.getNgayBan()))) {
                        sodon++;
                    }
                }
                values.add(new Entry(i, Float.valueOf(sodon)));
            }
            // loại 1 ngày
        } else {
            for (int i = 1; i <= tongNgay; i++) {
                int sodon = 0;
                for (HoaDonBan hb : listHoaDonBan) {
                    sodon++;
                }
                values.add(new Entry(Integer.parseInt(sdfDate.format(isDate)), Float.valueOf(sodon)));
            }
        }


        return values;
    }

}