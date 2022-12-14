package laptrinhandroid.fpoly.dnnhm3.Fragment.Fragment_baocao;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.GridLayout;
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
import com.google.android.material.tabs.TabLayout;

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

import laptrinhandroid.fpoly.dnnhm3.Adapter.Adapter_baocao.BaocaoAdapterLich;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOBaoCao;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.R;

public class FragmentThuChi extends Fragment implements BaocaoAdapterLich.IsenDataTime {

    int isNgay = 0;
    boolean isSelectThu = true;
    Date isDate;

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
            isDate = date;  //l???y ????? set d??? li???u ng??y l??n bieru d???
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
        tabLayout.addTab(tabLayout.newTab().setCustomView(getViewTablayout(0, "T???ng thu", getTongThu())));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getViewTablayout(1, "T???ng chi", getTongChi())));

        //l???ng nghe tablayout v?? set color
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
//            //n???u ??ang ??? chi m?? ?????i d??? li???u th?? s??t tab v???n ??? ????
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
        tvTien.setText(forMatNumber(tien) + " ???");

        //set text v?? tab color default
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
        sheetDialogLich.getWindow().setGravity(Gravity.CENTER);
        sheetDialogLich.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        sheetDialogLich.show();

        RecyclerView recyclerView = sheetDialogLich.findViewById(R.id.dialog_buttomshett_baocao_lich_rcv);

        List<String> listlich = new ArrayList<>();
        listlich.add("H??m nay");
        listlich.add("Tu???n n??y");
        listlich.add("Th??ng n??y");
        listlich.add("Tu???n tr?????c");
        listlich.add("Th??ng tr?????c");
        listlich.add("Th???i gian kh??c");

        BaocaoAdapterLich baocaoAdapterLich = new BaocaoAdapterLich(getActivity(), listlich, this);
        recyclerView.setAdapter(baocaoAdapterLich);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);   //d??ng k??? gi???a m???i item
        recyclerView.addItemDecoration(itemDecoration);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setChoise(int choice) {
        Calendar calendar = Calendar.getInstance();
        switch (choice) {
            case 0:
                tvTime.setText("H??m nay");
                isNgay = 0;
                getAllDataByDate(0, Calendar.getInstance().getTime());
                break;
            case 1:
                tvTime.setText("Tu???n n??y");
                isNgay = 1;
                getAllDataByDate(1, Calendar.getInstance().getTime());
                break;
            case 2:
                tvTime.setText("Th??ng n??y");
                isNgay = 2;
                getAllDataByDate(2, Calendar.getInstance().getTime());
                break;
            case 3:
                tvTime.setText("Tu???n tr?????c");
                isNgay = 3;
                getAllDataByDate(3, Calendar.getInstance().getTime());
                break;
            case 4:
                tvTime.setText("Th??ng tr?????c");
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpChart(boolean isThu) {

        lineChart.clear();

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

//        lineChart.setBackground(R.drawable.transparent_background);


        lineChart.setDrawGridBackground(true);
//        lineChart.set


        LineDataSet set1 = new LineDataSet(getValuesChart(true), "T???ng thu");
        LineDataSet set2 = new LineDataSet(getValuesChart(false), "T??ng chi");

        set1.setFillAlpha(110);
        set1.setColor(getResources().getColor(R.color.teal_200));
        set1.setLineWidth(3f);
        set1.setDrawCircles(false);

        set2.setFillAlpha(110);
        set2.setColor(Color.RED);
        set2.setLineWidth(3f);
        set2.setDrawCircles(false);

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


        //l???y tr???c x t??? bi???u ?????
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyXValueFormat(getValueDay()));

        xAxis.setGranularity(1f);       //gi???i h???n gi?? tr???

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Entry> getValuesChart(boolean isThu) {
        ArrayList<Entry> values = new ArrayList<>();
        int isLoaiNgay = 0;
        int tongNgay = 0;
        if (isNgay == 0 || isNgay == 5){
            tongNgay = 1;
            isLoaiNgay = 0;
        }
        else if (isNgay == 1 || isNgay == 3){
            tongNgay = 7;
            isLoaiNgay = 2;
        }
        else{
            isLoaiNgay = 3;
            if (listHoaDonBan.size() > 0){
                int thang = Integer.parseInt(sdfMonth.format(listHoaDonBan.get(0).getNgayBan()));
                if (thang == 1 || thang ==3 || thang ==3 || thang ==3 || thang ==3 || thang ==3 || thang ==3) tongNgay = 31;
                else if (thang == 2) tongNgay = 28;
                else  tongNgay = 30;
            }else {
                int thang = Integer.parseInt(sdfMonth.format(Calendar.getInstance().getTime()));
                if (thang == 1 || thang ==3 || thang ==3 || thang ==3 || thang ==3 || thang ==3 || thang ==3) tongNgay = 31;
                else if (thang == 2) tongNgay = 28;
                else  tongNgay = 30;
            }
        }

        // GET DATA THU
        if (isThu){
            // lo???i ng??y 3 l?? 30 ng??y
            if (isLoaiNgay == 3){
                for (int i = 1 ; i <= tongNgay; i++){
                    float tongtien = 0f;
                    for (HoaDonBan hb : listHoaDonBan){
                        if (i == Integer.parseInt(sdfDate.format(hb.getNgayBan()))){
                            tongtien += Float.valueOf(hb.getTongTien());
                        }
                    }
                    values.add(new Entry(i, tongtien));
                }
            }
            //lo???i ng??y 2 l?? 7 ng??y
            else if (isLoaiNgay == 2){
                //l???y ng??y ?????u tu???n ??? daoBaoCao
                Calendar cal = Calendar.getInstance();
                if (listHoaDonBan.size() > 0) cal.setTime(listHoaDonBan.get(0).getNgayBan());
                else cal.getTime();
                LocalDate localDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
                String stringngaydautuan = daoBaoCao.getFirstDayThisWeek(localDate, cal).substring(8);      //c???t 8 k?? t??? ?????u
                int ngaydautuan = Integer.parseInt(stringngaydautuan);

                for (int i = ngaydautuan ; i <= ngaydautuan+6; i++){

                    float tongtien = 0f;
                    for (HoaDonBan hb : listHoaDonBan){
                        int ngayHint = i;
                        if (ngayHint > 30) ngayHint -= 30;
                        if (ngayHint == Integer.parseInt(sdfDate.format(hb.getNgayBan()))){
                            tongtien += Float.valueOf(hb.getTongTien());
                        }
                    }

                    values.add(new Entry(i, tongtien));
                }
                // lo???i 1 ng??y
            }else {
                for (int i = 1 ; i <= tongNgay; i++){
                    float tongtien = 0f;
                    for (HoaDonBan hb : listHoaDonBan){
                        tongtien += Float.valueOf(hb.getTongTien());
                    }
                    values.add(new Entry(Integer.parseInt(sdfDate.format(isDate)), tongtien));
                }
            }

            // GET DATA CHI
        }else {
            // lo???i ng??y 3 l?? 30 ng??y
            if (isLoaiNgay == 3){
                for (int i = 1 ; i <= tongNgay; i++){
                    float tongtien = 0f;
                    for (HoaDonNhapKho hb : listHoaDonNhapKho){
                        if (i == Integer.parseInt(sdfDate.format(hb.getNgayNhap()))){
                            tongtien += Float.valueOf(hb.getTongTien());
                        }
                    }
                    values.add(new Entry(i, tongtien));
                }
            }
            //lo???i ng??y 2 l?? 7 ng??y
            else if (isLoaiNgay == 2){
                //l???y ng??y ?????u tu???n ??? daoBaoCao
                Calendar cal = Calendar.getInstance();
                if (listHoaDonBan.size() > 0) cal.setTime(listHoaDonBan.get(0).getNgayBan());
                else cal.getTime();
                LocalDate localDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
                String stringngaydautuan = daoBaoCao.getFirstDayThisWeek(localDate, cal).substring(8); //c???t 8 k?? t??? ?????u
                int ngaydautuan = Integer.parseInt(stringngaydautuan);

                for (int i = ngaydautuan ; i <= ngaydautuan+6; i++){
                    float tongtien = 0f;
                    for (HoaDonNhapKho hb : listHoaDonNhapKho){
                        int ngayHint = i;
                        if (ngayHint > 30) ngayHint -= 30;
                        if (ngayHint == Integer.parseInt(sdfDate.format(hb.getNgayNhap()))){
                            tongtien += Float.valueOf(hb.getTongTien());
                        }
                    }
                    values.add(new Entry(i, tongtien));
                }
                // lo???i 1 ng??y
            }else {
                for (int i = 1 ; i <= tongNgay; i++){
                    float tongtien = 0f;
                    for (HoaDonNhapKho hb : listHoaDonNhapKho){
                        tongtien += Float.valueOf(hb.getTongTien());
                    }
                    values.add(new Entry(Integer.parseInt(sdfDate.format(isDate)), tongtien));
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

            //check kh c?? data thi thang nay
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

        if (isThu) tvTbThu.setText(String.valueOf(forMatNumber(getTongThu() / tongNgay) + " ???"));
        else tvTbThu.setText(String.valueOf(forMatNumber(getTongChi() / tongNgay) + " ???"));

        if (isThu) {
            tvtitleChart.setText("Bi???u ????? t???ng thu");
            tvtitleTbThu.setText("Trung b??nh thu");
        } else {
            tvtitleChart.setText("Bi???u ????? t???ng chi");
            tvtitleTbThu.setText("Trung b??nh chi");
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