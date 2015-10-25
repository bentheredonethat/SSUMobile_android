package com.app.ssumobile.ssumobile_android.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ben on 10/15/15.
 */
public class CalendarFormatModel {

    public String PRODID = "-//Trumba Corporation//Trumba Calendar Services 0.11.12520//EN";
    public String CALSCALE = "GREGORIAN";
    public String X_WR_CALNAME = "SSUCalendar|All Events";
    public String X_WR_TIMEZONE = "America/Los_Angeles";
    public String METHOD = "PUBLISH";
    public String VTIMEZONE_TZID = "America/Los_Angeles";
    public Map<String,Map<String,String>> VTIMEZONE;
    private Map STANDARD = new HashMap<String,String>();
    private Map DAYLIGHT = new HashMap<String,String>();
    public CalendarFormatModel(){

        STANDARD.put("TZOFFSETFROM", "-0700");
        STANDARD.put("TZOFFSETTO", "-0800");
        STANDARD.put("TZNAME", "PST");
        STANDARD.put("DTSTART", "20071104T020000");
        STANDARD.put("RRULE", "FREQ=YEARLY;BYMONTH=11;BYDAY=1SU");
        VTIMEZONE.put("STANDARD", STANDARD);


        DAYLIGHT.put("TZOFFSETFROM", "-0700");
        DAYLIGHT.put("TZOFFSETTO", "-0800");
        DAYLIGHT.put("TZNAME", "PDT");
        DAYLIGHT.put("DTSTART", "20070311T020000");
        DAYLIGHT.put("RRULE", "FREQ=YEARLY;BYMONTH=3;BYDAY=2SU");
        VTIMEZONE.put("DAYLIGHT", DAYLIGHT);
    }

}
