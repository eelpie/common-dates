package uk.co.eelpieconsulting.common.dates;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateFormatter {

	private static final String ENGLISH = "en";
	private static final String SPANISH = "es";
	private static final String DEFAULT_LANGUAGE = ENGLISH;
	
	private static final long ONE_MINUTE = 60 * 1000;
	private static final long ONE_HOUR = ONE_MINUTE * 60;
	private static final long ONE_DAY = ONE_HOUR * 24;
	private static final long ONE_WEEK = ONE_DAY * 7;
	private static final long ONE_MONTH = ONE_DAY * 31;
	
	private final DateTimeFormatter MMM;
	private final DateTimeFormatter YYYY;
    private final DateTimeFormatter D_MMM_YYYY;
    private final DateTimeFormatter MMMMM_YYYY;
    private final DateTimeFormatter D_MMM_YYYY_HHMM;
    private final DateTimeFormatter D_MMM_YYYY_HHMMSS;
    private final DateTimeFormatter W3C_DATETIME_FORMAT;
	private final DateTimeFormatter YEAR_SLASH_MONTH;
	private final DateTimeFormatter YEAR_SLASH_MONTH_SLASH_DAY;

	private static final DecimalFormat TWO_DIGITS = new DecimalFormat("00");
	
	private final DateTimeZone timeZone;
	private final String language;
	
	private final Map<String, Map<String, String>> phrases;
	
	public DateFormatter(String timeZoneId) {
		this(DateTimeZone.forID(timeZoneId));
	}
	
	public DateFormatter(DateTimeZone timeZone) {
		this(timeZone, ENGLISH);
	}

	public DateFormatter(DateTimeZone timeZone, String language) {
		this.timeZone = timeZone;
		this.language = language;
				
		MMM = DateTimeFormat.forPattern("MMM");
		YYYY = DateTimeFormat.forPattern("yyyy");
		D_MMM_YYYY = DateTimeFormat.forPattern("d MMM yyyy");
		MMMMM_YYYY = DateTimeFormat.forPattern("MMMMM yyyy");
		D_MMM_YYYY_HHMM = DateTimeFormat.forPattern("d MMM yyyy HH:mm");
		D_MMM_YYYY_HHMMSS = DateTimeFormat.forPattern("d MMM yyyy HH:mm:ss");
		W3C_DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ");
		YEAR_SLASH_MONTH = DateTimeFormat.forPattern("yyyy/MMM");
		YEAR_SLASH_MONTH_SLASH_DAY = DateTimeFormat.forPattern("yyyy/MMM/d");
		
		Map<String, String> englishPhrases = new HashMap<String, String>();
		englishPhrases.put("just now", "just now");
		englishPhrases.put("1 minute ago", "1 minute ago");
		englishPhrases.put("minutes ago", "? minutes ago");
		englishPhrases.put("1 hour ago", "1 hour ago");
		englishPhrases.put("hours ago", "? hours ago");
		englishPhrases.put("1 day ago", "1 day ago");
		englishPhrases.put("days ago", "? days ago");
		englishPhrases.put("1 week ago", "1 week ago");
		englishPhrases.put("weeks ago", "? weeks ago");	
		englishPhrases.put("1 month ago", "1 month ago");
		englishPhrases.put("months ago", "? months ago");
		
		Map<String, String> spanishPhrases = new HashMap<String, String>();
		spanishPhrases.put("just now", "En este momento");
		spanishPhrases.put("1 minute ago", "Hace 1 minuto");
		spanishPhrases.put("minutes ago", "Hace ? minutos");
		spanishPhrases.put("1 hour ago", "Hace 1 hora");
		spanishPhrases.put("hours ago", "Hace ? horas");
		spanishPhrases.put("1 day ago", "Hace 1 d’a");
		spanishPhrases.put("days ago", "Hace ? d’as");
		spanishPhrases.put("1 week ago", "Hace 1 semana");
		spanishPhrases.put("weeks ago", "Hace ? semanas");	
		spanishPhrases.put("1 month ago", "Hace 1 mes");
		spanishPhrases.put("months ago", "Hace ? meses");
		
		phrases = new HashMap<String, Map<String, String>>();
		phrases.put(ENGLISH, englishPhrases);
		phrases.put(SPANISH, spanishPhrases);
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
			return phrase("just now");
        }
        
        if (deltaInMills > ONE_MONTH) {        		
    		final int months = Months.monthsBetween(new DateTime(then), DateTime.now()).getMonths();
        	return months == 1 ? phrase("1 month ago") : phrase("months ago", months);
    	}        
        if (deltaInMills > ONE_WEEK) {
        	final int weeks = Math.round((deltaInMills / (1000 * 60 * 60 * 24 * 7)));
        	return weeks == 1 ? phrase("1 week ago") : phrase("weeks ago", weeks);
        }        
        if (deltaInMills > ONE_DAY) {
        	final int days = Math.round((deltaInMills / (1000 * 60 * 60 * 24)));
        	return days == 1 ? phrase("1 day ago") : phrase("days ago", days);
        }
        if (deltaInMills > ONE_HOUR) {
        	final int hours = Math.round((deltaInMills / (1000 * 60 * 60)));
        	return hours == 1 ? phrase("1 hour ago") : phrase("hours ago", hours);
        }
        if (deltaInMills > ONE_MINUTE) {        	
        	final int minutes = Math.round(deltaInMills / (1000 * 60));
        	return minutes == 1 ? phrase("1 minute ago") : phrase("minutes ago", minutes);
        }
        
		return phrase("just now");
    }

	private String phrase(String key, int minutes) {
		String phrase = getKey(key, language);
		return phrase != null ? phrase.replace("?", Integer.toString(minutes)) : "";
	}

	private String phrase(String key) {
		String phrase = getKey(key, language);
		return phrase != null ? phrase : "";
	}

	private String getKey(String key, String language) {
		if (!phrases.containsKey(language)) {
			language = DEFAULT_LANGUAGE;
		}
		
		Map<String, String> languagePhrases = phrases.get(language);
		if (languagePhrases.containsKey(key)) {
			return languagePhrases.get(key);
		}
		return null;
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
