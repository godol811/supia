package com.example.supia.Dto;

import java.util.ArrayList;

public class CalendarDTO {
int calendarNo;
String calendarStartDate;
String calendarFinishDate;
String calendarDeliveryDate;
String calendarBirthDate;
String userId;


    public CalendarDTO( String calendarStartDate, String calendarFinishDate, String calendarDeliveryDate, String calendarBirthDate) {
        
        this.calendarStartDate = calendarStartDate;
        this.calendarFinishDate = calendarFinishDate;
        this.calendarDeliveryDate = calendarDeliveryDate;
        this.calendarBirthDate = calendarBirthDate;

    }

    public CalendarDTO(String calendarStartDate, String calendarFinishDate, String userId) {
        this.calendarStartDate = calendarStartDate;
        this.calendarFinishDate = calendarFinishDate;
        this.userId = userId;
    }

    public CalendarDTO(String calendarDeliveryDate, String userId) {
        this.calendarDeliveryDate = calendarDeliveryDate;
        this.userId = userId;
    }


    public int getCalendarNo() {
        return calendarNo;
    }

    public void setCalendarNo(int calendarNo) {
        this.calendarNo = calendarNo;
    }

    public String getCalendarStartDate() {
        return calendarStartDate;
    }

    public void setCalendarStartDate(String calendarStartDate) {
        this.calendarStartDate = calendarStartDate;
    }

    public String getCalendarFinishDate() {
        return calendarFinishDate;
    }

    public void setCalendarFinishDate(String calendarFinishDate) {
        this.calendarFinishDate = calendarFinishDate;
    }

    public String getCalendarDeliveryDate() {
        return calendarDeliveryDate;
    }

    public void setCalendarDeliveryDate(String calendarDeliveryDate) {
        this.calendarDeliveryDate = calendarDeliveryDate;
    }

    public String getCalendarBirthDate() {
        return calendarBirthDate;
    }

    public void setCalendarBirthDate(String calendarBirthDate) {
        this.calendarBirthDate = calendarBirthDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
