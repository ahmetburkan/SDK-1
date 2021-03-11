package com.app.softwaredevelopmentpraktijk1.Model;

import java.util.Calendar;

public class DateModel {
    private String monthString;
    private Integer monthInteger;

    public DateModel() {
        setMonthInteger();
        setMonthString();
    }

    public void setMonthInteger() {
        java.util.Date date = new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.monthInteger = c.get(Calendar.MONTH);
    }

    public Integer getMonthInteger() {
        return this.monthInteger;
    }

    public void setMonthString() {
        String[] listOfMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        this.monthString = listOfMonths[this.monthInteger];
    }

    public String getMonthString() {
        return this.monthString;
    }
}
