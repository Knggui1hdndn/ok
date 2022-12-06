package laptrinhandroid.fpoly.dnnhm3.Fragment.Fragment_baocao;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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
import com.google.android.material.tabs.TabLayout;

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
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.R;

public class FragmentThuChi extends Fragment implements BaocaoAdapterLich.IsenDataTime {

    int isNgay = 0;
    boolean isSelectThu = true;

    private TabLayout tabLayout;
    private CardView cvTime;
    private TextView tvTime, tvtitleChart, tvtitleTbThu, tvTbThu;
    //    GraphView graphView;
    LineChart lineChart;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    BottomSheetDialog sheetDialogLich;
    //    private LineGraphSeries<DataPoint> line1;
    List<HoaDonBan> listHoaDonBan;
    List<HoaDonNhapKho> listHoaDonNhapKho;

    Context mcontext;
    DAOBaoCao daoBaoCao;


    public FragmentThuChi() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu_chi, container, false);

        tvTime = view.findViewById(R.id.frg_thuchi_tv_time);
        tvtitleChart = view.findViewById(R.id.frg_thuchi_tv_title_bieudo);
        tvtitleTbThu = view.findViewById(R.id.frg_thuchi_tv_title_tbthutheongay);
        tvTbThu = view.findViewById(R.id.frg_thuchi_tv_tbthutheongay);
        tabLayout = view.findViewById(R.id.baoCao_tablayout);
        cvTime = view.findViewById(R.id.frg_thuchi_cardview_time);
//        graphView = view.findViewById(R.id.graph_view);
        lineChart = view.findViewById(R.id.frg_thuchi_chart);

        daoBaoCao = new DAOBaoCao();
        mcontext = getActivity();
        listHoaDonBan = new ArrayList<>();
        listHoaDonNhapKho = new ArrayList<>();

//        getActivity().getSupportFragmentManager();

        getAllDataByDate(0, Calendar.getInstance().getTime());
        setUpChart(true);
        setUpData(true);

//        Log.e("dataArr", listThuchi.toString());

        cvTime.setOnClickListener(v -> {
            showButtonSheetDialog();
        });


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAllDataByDate(int positon, Date date) {
        try {
            listHoaDonBan.clear();
            listHoaDonNhapKho.clear();
            listHoaDonBan.addAll(daoBaoCao.getListHoaDonBanByDay(positon, date));
            listHoaDonNhapKho.addAll(daoBaoCao.getListHoaDonNhapByDay(positon, date));
            setUpTablayout();
            Log.i("lengthListThuChi", "Length " + listHoaDonBan.size());
            Log.i("lengthListThuChi", "Length " + listHoaDonNhapKho.size());
            Log.e("Kiemtra1", positon + " ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUpTablayout() {

        tabLayout.removeAllTabs();

        //get Tab and set title
        tabLayout.addTab(tabLayout.newTab().setCustomView(getViewTablayout(0, "Tổng thu", getTongThu())));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getViewTablayout(1, "Tổng chi", getTongChi())));

        //lắng nghe tablayout và set color
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        isSelectThu = true;
                        setUpChart(true);
                        setUpData(true);
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF03DAC5"));
                        setColorViewItemTablayout(tab.getPosition(), getResources().getColor(R.color.teal_200));
                        break;
                    case 1:
                        isSelectThu = false;
                        setUpChart(false);
                        setUpData(false);
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
                        setColorViewItemTablayout(tab.getPosition(), getResources().getColor(R.color.red));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setColorViewItemTablayout(tab.getPosition(), getResources().getColor(R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
//        if (isSelectThu){
//        }else {
//            //nếu đang ở chi mà đổi dữ liệu thì sét tab vẫn ở đó
//            tabLayout.getTabAt(1).select();
//            isSelectThu = false;
//            setUpChart(false);
//            setUpData(false);
//            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
//            setColorViewItemTablayout(1, getResources().getColor(R.color.red));
//        }
    }

    private View getViewTablayout(int position, String ten, double tien) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_custom_tab, null);     //set view

        TextView tvName = (TextView) v.findViewById(R.id.item_tab_name);
        TextView tvTien = (TextView) v.findViewById(R.id.item_tab_tien);
        tvName.setText(ten);
        tvTien.setText(forMatNumber(tien) + " ₫");

        //set text và tab color default
        if (position == 0) tvTien.setTextColor(getResources().getColor(R.color.teal_200));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF03DAC5"));

        return v;
    }

    private void setColorViewItemTablayout(int position, int color) {
        TabLayout.Tab tabAt = tabLayout.getTabAt(position);
        View tabView = tabAt.getCustomView();
        TextView tvTien = tabView.findViewById(R.id.item_tab_tien);
        tvTien.setTextColor(color);
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
//                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
//
//                final MaterialDatePicker materialDatePicker = builder.build();

                DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext, new DatePickerDialog.OnDateSetListener() {
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

    private void setUpChart(boolean isThu) {

        lineChart.clear();

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        LineDataSet set1 = new LineDataSet(getValuesChart(true), "Data set 1");
        LineDataSet set2 = new LineDataSet(getValuesChart(false), "Data set 2");

        set1.setFillAlpha(110);
        set1.setColor(getResources().getColor(R.color.teal_200));
        set1.setLineWidth(3f);

        set2.setFillAlpha(110);
        set2.setColor(Color.RED);
        set2.setLineWidth(3f);

        if (isThu){
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData lineData = new LineData(dataSets);

            lineChart.setData(lineData);
        }else {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set2);

            LineData lineData = new LineData(dataSets);

            lineChart.setData(lineData);
        }


        XAxis xAxis = lineChart.getXAxis();
        List<String> mlist = getValueDay();
        xAxis.setValueFormatter(new MyXValueFormat((ArrayList<String>) mlist));
//        xAxis.setGranularity(1f);

    }

    public class MyXValueFormat extends ValueFormatter implements IAxisValueFormatter{
        private ArrayList<String> list;
        public MyXValueFormat(ArrayList<String> list){
            this.list = list;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String ngay = list.get((int) value);
            return ngay;
        }
    }

    private ArrayList<Entry> getValuesChart(boolean isThu) {
        ArrayList<Entry> values = new ArrayList<>();

        int tongNgay = 0;
        if (isNgay == 0 || isNgay == 5) tongNgay = 1;
        else if (isNgay == 1 || isNgay == 3) tongNgay = 7;
        else tongNgay = 30;


        if (isThu){
            for (int i = 1 ; i < tongNgay; i++){
                for (HoaDonBan hb : listHoaDonBan){
                    if (i == Integer.parseInt(sdfDate.format(hb.getNgayBan()))){
                        values.add(new Entry(i, Float.valueOf(hb.getTongTien())));
                    }else {
                        values.add(new Entry(i, Float.valueOf(i)));
                    }
                }
            }
        }else {
            for (int i = 1 ; i < tongNgay; i++){
                for (HoaDonNhapKho hb : listHoaDonNhapKho){
                    if (i == Integer.parseInt(sdfDate.format(hb.getNgayNhap()))){
                        values.add(new Entry(i, Float.valueOf(hb.getTongTien())));
                    }else {
                        values.add(new Entry(i, Float.valueOf(i)));
                    }
                }
            }
        }

        return values;
    }

    private ArrayList<String> getValueDay(){
        ArrayList<String> mlist = new ArrayList<>();

        int tongNgay = 0;
        if (isNgay == 0 || isNgay == 5) tongNgay = 1;
        else if (isNgay == 1 || isNgay == 3) tongNgay = 7;
        else tongNgay = 30;

        for (int i = 1 ; i <= tongNgay; i++){
            for (HoaDonBan hb : listHoaDonBan){
                if (i == Integer.parseInt(sdfDate.format(hb.getNgayBan()))){
                    mlist.add(String.valueOf(sdfDate.format(hb.getNgayBan()))+ "/" + String.valueOf(sdfMonth.format(hb.getNgayBan())) );
                }
            }

            //check kh có data thi thang nay
            if (listHoaDonBan.size() > 0){
                mlist.add( i + "/"  +  String.valueOf(sdfMonth.format(listHoaDonBan.get(0).getNgayBan())));
            }else {
                mlist.add( i + "/"  +  String.valueOf(sdfMonth.format(Calendar.getInstance().getTime())));
            }

        }
        Log.e("formatngay", mlist.toString());
        return mlist;
    }


    private void setUpData(boolean isThu) {

        int tongNgay = 0;
        if (isNgay == 0 || isNgay == 5) tongNgay = 1;
        else if (isNgay == 1 || isNgay == 3) tongNgay = 7;
        else tongNgay = 30;

        if (isThu) tvTbThu.setText(String.valueOf(forMatNumber(getTongThu() / tongNgay) + " ₫"));
        else tvTbThu.setText(String.valueOf(forMatNumber(getTongChi() / tongNgay) + " ₫"));

        if (isThu) {
            tvtitleChart.setText("Biểu đồ tổng thu");
            tvtitleTbThu.setText("Trung bình thu");
        } else {
            tvtitleChart.setText("Biểu đồ tổng chi");
            tvtitleTbThu.setText("Trung bình chi");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void sendData(int time) {
        setChoise(time);
    }

    private double getTongThu() {
        double tongthu = 0;
        for (HoaDonBan bc : listHoaDonBan) {
            tongthu += bc.getTongTien();
        }
        return tongthu;
    }

    private double getTongChi() {
        double tongchi = 0;
        for (HoaDonNhapKho bc : listHoaDonNhapKho) {
            tongchi += bc.getTongTien();
        }
        return tongchi;
    }

    private String forMatNumber(Double aDouble) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(aDouble);
    }
}