package com.example.socialmediaapp_1.Utils;

import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import java.util.Calendar;

public class GeneralUtils {

    public static Spanned getCaption(String profileName, String caption) {
        return Html.fromHtml("<b>" + profileName + "</b> " + caption);
    }

    public static Spanned getCaption(String profileName, String activityText, String timestamp) {
        return Html.fromHtml("<b>" + profileName + "</b> " + activityText +
                " <font color='#787878'>" + timestamp + "</font>");
    }

    public static String mapTimestamp(int timestamp) {
        // TODO: write the proper code
//        return "" + timestamp;

        long currUnixTime = Calendar.getInstance().getTimeInMillis() / 1000;
        long secondsElapsedAfterPosting = (currUnixTime - timestamp);   // Not subtracting 19800 because we are using GMT time

        if(secondsElapsedAfterPosting / 31540000 > 0)
            return "" + (secondsElapsedAfterPosting / (31540000)) + "y";
        else if(secondsElapsedAfterPosting / 2628000 > 0)
            return "" + (secondsElapsedAfterPosting / (2628000)) + "m";
        else if(secondsElapsedAfterPosting / 604800 > 0)
            return "" + (secondsElapsedAfterPosting / (604800)) + "w";
        else if(secondsElapsedAfterPosting / 86400 > 0)
            return "" + (secondsElapsedAfterPosting / (86400)) + "d";
        else if(secondsElapsedAfterPosting / 3600 > 0)
            return "" + (secondsElapsedAfterPosting / (3600)) + "h";
        else
            return "" + (secondsElapsedAfterPosting / (60)) + "m";
    }
}
