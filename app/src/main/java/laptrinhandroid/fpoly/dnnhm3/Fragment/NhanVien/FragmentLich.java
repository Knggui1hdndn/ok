package laptrinhandroid.fpoly.dnnhm3.Fragment.NhanVien;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.sql.SQLException;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Activity.GiaoDienChinh;
import laptrinhandroid.fpoly.dnnhm3.DAO.DAOChamCong;
import laptrinhandroid.fpoly.dnnhm3.XuLiNgay.DayViewDecoratorConfirmed;
import laptrinhandroid.fpoly.dnnhm3.XuLiNgay.DayViewDecoratorNoConfirm;
import laptrinhandroid.fpoly.dnnhm3.XuLiNgay.DayViewDecoratorUnconfirmed;
import laptrinhandroid.fpoly.dnnhm3.R;
import laptrinhandroid.fpoly.dnnhm3.XuLiNgay.FormatDay;

public class FragmentLich extends Fragment {
    MaterialCalendarView calendarView;
    TextView txtLam, txtNghi, txtChuaXN;
    View view;
    List<CalendarDay> list0, list1, list2;

    //0 là chưa có,1 là xấc nhận ,2 là không xác nhận
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cong, null);
        calendarView = view.findViewById(R.id.calendarView);
        txtLam = view.findViewById(R.id.txtLam);
        txtNghi = view.findViewById(R.id.txtNghi);
        txtChuaXN = view.findViewById(R.id.txtChuaXN);
        DAOChamCong daoChamCong = GiaoDienChinh.daoChamCong;
        try {
            Bundle bundle = getArguments();
            int nhanVien = bundle.getInt("nv");
            list0 = daoChamCong.getListChamCong(nhanVien, 0);
            list1 = daoChamCong.getListChamCong(nhanVien, 1);
            list2 = daoChamCong.getListChamCong(nhanVien, 2);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                calendarView.setCurrentDate(FormatDay.calendarDay());
//            }
            if (list0 != null || list0.size() == 0) {
                calendarView.addDecorator(new DayViewDecoratorUnconfirmed(list0));

            }
            if (list1 != null || list1.size() == 0) {
                calendarView.addDecorator(new DayViewDecoratorConfirmed(list1));

            }
            if (list2 != null || list2.size() == 0) {
                calendarView.addDecorator(new DayViewDecoratorNoConfirm(list2));
            }
            CalendarDay date= FormatDay.calendarDay();
            txtChuaXN.setText(getTrangThaiCongTrongThang(list0, date.getMonth()) + "");
            txtLam.setText(getTrangThaiCongTrongThang(list1, date.getMonth()) + "");
            txtNghi.setText(getTrangThaiCongTrongThang(list2, date.getMonth()) + "");

            calendarView.setOnMonthChangedListener((widget, date1) -> {
                txtChuaXN.setText(getTrangThaiCongTrongThang(list0, date1.getMonth()) + "");
                txtLam.setText(getTrangThaiCongTrongThang(list1, date1.getMonth()) + "");
                txtNghi.setText(getTrangThaiCongTrongThang(list2, date1.getMonth()) + "");
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    private int getTrangThaiCongTrongThang(List<CalendarDay> calendarDays, int month) {
        int i = 0;
        for (CalendarDay calendarDay : calendarDays) {
            if (calendarDay.getMonth() == month) {
                i++;
            }
        }
        return i;
    }


}
