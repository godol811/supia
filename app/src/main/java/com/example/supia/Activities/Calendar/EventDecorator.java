package com.example.supia.Activities.Calendar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.example.supia.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EventDecorator implements DayViewDecorator {
    //private final Drawable drawable;
    public static String TAG = "이벵데코드로";
    private Drawable drawable;

    private final HashSet<CalendarDay> dates;


    public EventDecorator(Activity context, Collection<CalendarDay> dates) {
        // drawable = ResourcesCompat.getDrawable((Resources.getSystem()) , R.drawable.calendar_select_background, null);

        this.dates = new HashSet<>(dates);
        drawable = context.getDrawable(R.drawable.line);

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }


    @Override
    public void decorate(DayViewFacade view) {

        view.setBackgroundDrawable(drawable);
//        view.setSelectionDrawable(drawable);
//    view.addSpan(drawable);
//        view.setBackgroundDrawable();
//        int id = R.drawable.calendar_select_background;
//        Log.v(TAG, "ㅇㅇㅇ"+id);

    }


}
