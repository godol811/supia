package com.example.supia.Activities.Calendar;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.example.supia.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;


public class EventDecoratorDraw2 implements DayViewDecorator {

    public static String TAG = "이벵데코드로";
    private Drawable drawable;

    private final HashSet<CalendarDay> dates;


    public EventDecoratorDraw2(Activity context, Collection<CalendarDay> dates) {
        // drawable = ResourcesCompat.getDrawable((Resources.getSystem()) , R.drawable.calendar_select_background, null);

        this.dates = new HashSet<>(dates);
        drawable = context.getDrawable(R.drawable.birthday);


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
