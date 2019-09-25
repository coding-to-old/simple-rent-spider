package cto.github.rent.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateFormUtils {


    private static final DateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat DAY_SDF= new SimpleDateFormat("yyyy-MM-dd");


    public static String getNowDay(){
        return DAY_FORMAT.format(new Date());

    }
    public static String getNowTime(){
        return DATE_FORMAT.format(new Date());
    }

    public static String dateToDay(Date date){
        return DAY_FORMAT.format(date);
    }

    public static String dateFormat(Date date){
        return DATE_FORMAT.format(date);
    }

    public static Date getThisDateBeforeNowDate(int day){
        return getThisDateBeforeDate(new Date(),day);
    }

    public static Date getThisDateBeforeDate(Date date , int day){
        Calendar temp = Calendar.getInstance();
        Date _date = new Date(date.getTime());
        temp.setTime(_date);
        temp.add(Calendar.DATE, day);
        return temp.getTime();
    }

    public static List<String> getNowDateBetweenDay(Date beginDate){
        return getDateBetweenDay(beginDate , new Date());
    }

    public static List<String> getDateBetweenDayStr(String beginDate , String endDate){
        try {
            return getDateBetweenDay(DAY_SDF.parse(beginDate),DAY_SDF.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public static List<String> getDateBetweenDay(Date beginDate , Date endDate){

        if (beginDate == null || endDate == null) {
            return Collections.emptyList();
        }

        List<String> days = new ArrayList<String>();

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(beginDate);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endDate);
        tempEnd.add(Calendar.DATE, +1);

        while (tempStart.before(tempEnd)) {
            days.add(DAY_FORMAT.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }

        return days;
    }

    public static Date strToDay(String date) {
        try {
            return DAY_SDF.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
