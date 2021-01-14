package com.example.supia.Activities.Calendar;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.supia.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;


public class EventDecoratorDraw implements DayViewDecorator {

    public static String TAG = "이벵데코드로";
    private Drawable drawable;

    private final HashSet<CalendarDay> dates;


    public EventDecoratorDraw(Activity context,Collection<CalendarDay> dates) {
        // drawable = ResourcesCompat.getDrawable((Resources.getSystem()) , R.drawable.calendar_select_background, null);

        this.dates = new HashSet<>(dates);
        drawable = context.getDrawable(R.drawable.calendar_select_background);


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
