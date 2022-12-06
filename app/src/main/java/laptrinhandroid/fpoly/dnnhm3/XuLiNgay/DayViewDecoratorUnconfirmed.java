package laptrinhandroid.fpoly.dnnhm3.XuLiNgay;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.List;

public class DayViewDecoratorUnconfirmed implements com.prolificinteractive.materialcalendarview.DayViewDecorator {
    @SuppressLint("SimpleDateFormat")
     List<CalendarDay> strings;


    public DayViewDecoratorUnconfirmed(List<CalendarDay> strings ) {
        this.strings = strings;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return strings.contains(day);


    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, Color.parseColor("#FF9800")));

    }

}
