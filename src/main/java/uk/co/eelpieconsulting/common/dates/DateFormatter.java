package uk.co.eelpieconsulting.common.dates;

import java.util.Calendar;
import java.util.Date;

public class DateFormatter {
   
    public String timeSince(Date then) {        
        final Date now = Calendar.getInstance().getTime();
        final long deltaInMills = now.getTime() - then.getTime();
                
        if (deltaInMills > 60 * 1000 * 60 * 24 * 7) {
        	final int weeks = Math.round((deltaInMills / (1000 * 60 * 60 * 24 * 7)));
        	if (weeks == 1) {
        		return weeks + " week ago";
        	} else {
        		return weeks + " weeks ago";
        	}        
        }
        
        if (deltaInMills > 60 * 1000 * 60 * 24) {
        	final int days = Math.round((deltaInMills / (1000 * 60 * 60 * 24)));
            if (days == 1) {
                return days + " day ago";
            } else {
                return days + " days ago";
            }
        }
        
        if (deltaInMills > 60 * 1000 * 60) {
        	final int hours = Math.round((deltaInMills / (1000 * 60 * 60)));
        	if (hours == 1) {
        		return hours + " hour ago";
        	} else {
        		return hours + " hours ago";
        	}        	
        }

        if (deltaInMills > 60 * 1000) {        	
        	 if (deltaInMills < 120 * 1000) {
                 return "1 minute ago";
             } else {
            	 float minutes = (deltaInMills / (1000 * 60));
            	 return (Math.round(minutes) + " minutes ago");	
             }
        }
                
        return "less than 1 minute ago";
    }
    
}
