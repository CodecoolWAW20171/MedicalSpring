package com.medbis.statistics;

import com.medbis.entity.Treatment;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Util {
    private static Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {

    }

    Map<Treatment, Integer> createMapOfSingleTreatmentResultInMonth(List<Treatment> treatments){
        Map<Treatment, Integer> singleTreatmentResult = new HashMap<>();
        for(Treatment treatment : treatments){
            singleTreatmentResult.put(treatment, 0);
        }
        return singleTreatmentResult;
    }

     LocalDate getLastDayOfMonth(int month) {
        return LocalDate.of(2019, month, Month.of(month).length(isLeapYear(2019)));
    }

    private static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }


}
