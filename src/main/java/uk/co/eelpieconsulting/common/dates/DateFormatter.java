package uk.co.eelpieconsulting.common.dates;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateFormatter {

	private static final long ONE_MINUTE = 60 * 1000;
	private static final long ONE_HOUR = ONE_MINUTE * 60;
	private static final long ONE_DAY = ONE_HOUR * 24;
	private static final long ONE_WEEK = ONE_DAY * 7;
	private static final long ONE_MONTH = ONE_DAY * 31;
	
	private static final DateTimeFormatter MMM = DateTimeFormat.forPattern("MMM");
	private static final DateTimeFormatter YYYY = DateTimeFormat.forPattern("yyyy");
    private static final DateTimeFormatter D_MMM_YYYY = DateTimeFormat.forPattern("d MMM yyyy");
    private static final DateTimeFormatter MMMMM_YYYY = DateTimeFormat.forPattern("MMMMM yyyy");
    private static final DateTimeFormatter D_MMM_YYYY_HHMM = DateTimeFormat.forPattern("d MMM yyyy HH:mm");
    private static final DateTimeFormatter D_MMM_YYYY_HHMMSS = DateTimeFormat.forPattern("d MMM yyyy HH:mm:ss");
    private static final DateTimeFormatter W3C_DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ");	// TODO check usages - not an ISO format?
	private static final DateTimeFormatter YEAR_SLASH_MONTH = DateTimeFormat.forPattern("yyyy/MMM");
	private static final DateTimeFormatter YEAR_SLASH_MONTH_SLASH_DAY = DateTimeFormat.forPattern("yyyy/MMM/d");

	private static final DecimalFormat TWO_DIGITS = new DecimalFormat("00");
	
	private final DateTimeZone timeZone;
	
	public DateFormatter(String timeZoneId) {
		this.timeZone = DateTimeZone.forID(timeZoneId);		
	}
	
	public DateFormatter(DateTimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public String timeSince(Date then) {
		if (then == null) {
			return null;
		}
		
        final Date now = Calendar.getInstance().getTime();
        final long deltaInMills = now.getTime() - then.getTime();
        
        if (deltaInMills < 0) {        	
        	if (deltaInMills < -ONE_MONTH) {        		
        		final long months = Months.monthsBetween(DateTime.now(), new DateTime(then)).getMonths();
        		return months + (months == 1 ? " month" : " months");
        	}
        	if (deltaInMills < -ONE_WEEK) {
        		final int days = -Math.round((deltaInMills / (1000 * 60 * 60 * 24 * 7)));
        		return days + (days == 1 ? " week" : " weeks");
        	}
        	if (deltaInMills < -ONE_DAY) {
        		final int days = -Math.round((deltaInMills / (1000 * 60 * 60 * 24)));
        		return days + (days == 1 ? " day" : " days");
        	}
			if (deltaInMills < -ONE_HOUR) {
				final int hours = -Math.round((deltaInMills / (1000 * 60 * 60)));
				return hours + (hours == 1 ? " hour" : " hours");
			}
			if (deltaInMills < -ONE_MINUTE) {
				final int minutes = -Math.round(deltaInMills / (1000 * 60));
				return minutes + (minutes == 1 ? " minute" : " minutes");
			}
			return "just now";
        }
        
        if (deltaInMills > ONE_MONTH) {        		
    		final long months = Months.monthsBetween(new DateTime(then), DateTime.now()).getMonths();
    		return months + (months == 1 ? " month ago" : " months ago");
    	}        
        if (deltaInMills > ONE_WEEK) {
        	final int weeks = Math.round((deltaInMills / (1000 * 60 * 60 * 24 * 7)));        	
        	return weeks + (weeks == 1 ? " week ago" : " weeks ago"); 
        }        
        if (deltaInMills > ONE_DAY) {
        	final int days = Math.round((deltaInMills / (1000 * 60 * 60 * 24)));
        	return days + (days == 1 ? " day ago" : " days ago");
        }        
        if (deltaInMills > ONE_HOUR) {
        	final int hours = Math.round((deltaInMills / (1000 * 60 * 60)));
        	return hours + (hours == 1 ? " hour ago" : " hours ago");
        }
        if (deltaInMills > ONE_MINUTE) {        	
        	final int minutes = Math.round(deltaInMills / (1000 * 60));
        	return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        }       
        return "just now";
    }

	public String dayMonthYear(Date date) {
		return date != null ? D_MMM_YYYY.print(new DateTime(date, timeZone)) : null;
	}

	public String dayMonthYearTime(Date date) {
		return date != null ? D_MMM_YYYY_HHMM.print(new DateTime(date, timeZone)) : null;
	}
	
	public String dayMonthYearTimeWithSeconds(Date date) {
		return date != null ? D_MMM_YYYY_HHMMSS.print(new DateTime(date, timeZone)) : null;
	}
	
	public String fullMonthYear(Date date) {
		return date != null ? MMMMM_YYYY.print(new DateTime(date, timeZone)) : null;
	}
	
	public String month(Date date) {
		return date != null ? MMM.print(new DateTime(date, timeZone)): null;
	}

	public String year(Date date) {
		return date != null ? YYYY.print(new DateTime(date, timeZone)) : null;
	}
	
	public String w3cDateTime(Date date) {
		return date != null ? W3C_DATETIME_FORMAT.print(new DateTime(date, timeZone)) : null;
	}

	public String yearMonthDayUrlStub(Date date) {
		return date != null ? YEAR_SLASH_MONTH_SLASH_DAY.print(new DateTime(date, timeZone)).toLowerCase() : null;
	}

	public String yearMonthUrlStub(Date date) {
		return date != null ? YEAR_SLASH_MONTH.print(new DateTime(date, timeZone)).toLowerCase() : null;
	}
	
	public String secondsToDuration(int seconds) {
		return secondsToDuration(new Long(seconds));
	}
	
	public String secondsToDuration(long seconds) {
		final Period period = new Period(seconds * 1000);
		final StringBuilder output = new StringBuilder();
		if (period.getHours() > 0) {
			output.append(TWO_DIGITS.format(period.getHours())+ ":");
		}
		output.append(TWO_DIGITS.format(period.getMinutes())+ ":");
		output.append(TWO_DIGITS.format(period.getSeconds()));		
		return output.toString();
	}
	
}
