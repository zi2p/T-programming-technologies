package com.company.lab1.banks.dataTime;

import java.util.Calendar;
import java.util.Date;

public class DataTime {
    public int NOWDAY;
    public int NOWMONTH;
    public int NOWYEAR;
    Calendar calendar = Calendar.getInstance();

    public DataTime() {
        Date date = new Date();
        calendar.setTime(date);
        NOWDAY = calendar.get(Calendar.DAY_OF_MONTH);;
        NOWMONTH = calendar.get(Calendar.MONTH) + 1;
        NOWYEAR = calendar.get(Calendar.YEAR);
    }

    public boolean lessData(DataTime dataTime) {
        if (NOWYEAR > dataTime.NOWYEAR) return false;
        else if (NOWYEAR < dataTime.NOWYEAR) return true;
        else if (NOWMONTH > dataTime.NOWMONTH) return false;
        else if (NOWMONTH < dataTime.NOWMONTH) return true;
        else return NOWDAY <= dataTime.NOWDAY;
    }
}
