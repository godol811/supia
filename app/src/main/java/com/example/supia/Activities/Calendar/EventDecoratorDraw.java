package com.example.supia.Activities.Calendar;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.example.supia.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;


public class EventDecoratorDraw implements DayViewDecorator {
    private final Drawable drawable;
    private final HashSet<CalendarDay> dates;

    public EventDecoratorDraw(Collection<CalendarDay> dates, Activity context) {
        drawable = ResourcesCompat.getDrawable((Resources.getSystem()) , R.drawable.birth, null);

        this.dates = new HashSet<>(dates);

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }



    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);

    }


}
