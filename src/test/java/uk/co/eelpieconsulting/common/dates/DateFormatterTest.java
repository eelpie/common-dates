package uk.co.eelpieconsulting.common.dates;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class DateFormatterTest {
    
	private static final DateTimeZone EUROPE_LONDON = DateTimeZone.forID("Europe/London");
	
	private final DateFormatter dateFormatter = new DateFormatter(EUROPE_LONDON);
	private final DateFormatter spanishDateFormatter = new DateFormatter(EUROPE_LONDON, "es");

	private final Date onceUponATime = new DateTime(2009, 10, 2, 14, 23, 10, 0, EUROPE_LONDON).toDate();
	
	@Test
    public void shouldBeAbleToOutputNiceTimeDeltas() throws Exception {    	
    	final DateTime now = DateTime.now().minusSeconds(1);
    	
    	DateTime lessThanOneMinuteAgo = now.minusSeconds(30);
    	assertEquals("just now", dateFormatter.timeSince(lessThanOneMinuteAgo.toDate()));
    	
    	DateTime fiveMinutesBefore = now.minusMinutes(5);
        assertEquals("5 minutes ago", dateFormatter.timeSince(fiveMinutesBefore.toDate()));   
       
        DateTime anHourAgo = now.minusHours(1);
        assertEquals("1 hour ago", dateFormatter.timeSince(anHourAgo.toDate()));
        
        DateTime twoHoursAgo = now.minusHours(2);
        assertEquals("2 hours ago", dateFormatter.timeSince(twoHoursAgo.toDate()));
                
        DateTime oneDayAgo = now.minusDays(1);
        assertEquals("1 day ago", dateFormatter.timeSince(oneDayAgo.toDate()));
        
        DateTime oneWeekAgo = now.minusWeeks(1).minusHours(1);
        assertEquals("1 week ago", dateFormatter.timeSince(oneWeekAgo.toDate()));
        
        DateTime nineMonthsAgo = now.minusMonths(9).minusWeeks(1);
        assertEquals("9 months ago", dateFormatter.timeSince(nineMonthsAgo.toDate()));        
    }
	
	@Test
	public void niceTimeDeltaCanBeOutputInAlterativeLangauges() throws Exception {
    	final DateTime now = DateTime.now().minusSeconds(1);

		DateTime lessThanOneMinuteAgo = now.minusSeconds(30);
    	assertEquals("En este momento", spanishDateFormatter.timeSince(lessThanOneMinuteAgo.toDate()));

    	DateTime oneMinuteAgo = now.minusSeconds(70);
		assertEquals("Hace 1 minuto", spanishDateFormatter.timeSince(oneMinuteAgo.toDate()));
		
		DateTime fiveMinutesBefore = now.minusMinutes(5);
        assertEquals("Hace 5 minutos", spanishDateFormatter.timeSince(fiveMinutesBefore.toDate()));
        
        DateTime anHourAgo = now.minusHours(1);
        assertEquals("Hace 1 hora", spanishDateFormatter.timeSince(anHourAgo.toDate()));
        
        DateTime twoHoursAgo = now.minusHours(2);
        assertEquals("Hace 2 horas", spanishDateFormatter.timeSince(twoHoursAgo.toDate()));
                
        DateTime oneDayAgo = now.minusDays(1);
        assertEquals("Hace 1 día", spanishDateFormatter.timeSince(oneDayAgo.toDate()));
        
        DateTime oneWeekAgo = now.minusWeeks(1).minusHours(1);
        assertEquals("Hace 1 semana", spanishDateFormatter.timeSince(oneWeekAgo.toDate()));
        
        DateTime nineMonthsAgo = now.minusMonths(9).minusWeeks(1);
        assertEquals("Hace 9 meses", spanishDateFormatter.timeSince(nineMonthsAgo.toDate()));        
    }
	
	@Test
    public void shouldBeAbleToOutputNiceTimeDeltaForDatesInTheFuture() throws Exception {
		final DateTime now = new DateTime();
    	
	  	DateTime fiveMinutesFromNow = now.plusMinutes(5).plusSeconds(5);
        assertEquals("5 minutes", dateFormatter.timeSince(fiveMinutesFromNow.toDate()));     
                
    	DateTime twoDaysFromNow = now.plusDays(2).plusHours(1);
        assertEquals("2 days", dateFormatter.timeSince(twoDaysFromNow.toDate()));
          
        DateTime oneWeekFromNow = now.plusWeeks(1).plusHours(1);
        assertEquals("1 week", dateFormatter.timeSince(oneWeekFromNow.toDate()));
        
        DateTime twoWeeksFromNow = now.plusWeeks(2).plusHours(1);
        assertEquals("2 weeks", dateFormatter.timeSince(twoWeeksFromNow.toDate()));
        
        DateTime sixMonthsFromNow = now.plusMonths(6).plusHours(1);
        assertEquals("6 months", dateFormatter.timeSince(sixMonthsFromNow.toDate()));
    }
	
	@Test
	public void canGenerateYearFormat() throws Exception {
		assertEquals("2009", dateFormatter.year(onceUponATime));
	}
	
	@Test
	public void canGenerateShortDayMonthYearFormat() throws Exception {
		assertEquals("2 Oct 2009", dateFormatter.dayMonthYear(onceUponATime));
	}
	
	@Test
	public void canGenerateShortDayMonthYearFormatInAlternativeLanguages() throws Exception {
		assertEquals("2 oct 2009", spanishDateFormatter.dayMonthYear(onceUponATime));
	}
	
	@Test
	public void canGenerateFullMonthYearFormat() throws Exception {
		assertEquals("October 2009", dateFormatter.fullMonthYear(onceUponATime));
	}

	@Test
	public void canGenerateFullMonthYearFormatInAlternativeLanguages() throws Exception {
		assertEquals("octubre 2009", spanishDateFormatter.fullMonthYear(onceUponATime));
	}
	
	@Test
	public void canGenerateFullDayMonthYearFormat() throws Exception {
		assertEquals("2 October 2009", dateFormatter.fullDayMonthYear(onceUponATime));
	}
	
	@Test
	public void canGenerateDayFullMonthYearFormatInAlternativeLanguages() throws Exception {
		assertEquals("2 octubre 2009", spanishDateFormatter.fullDayMonthYear(onceUponATime));
	}
	
	@Test
	public void canGenerateDayMonthYearTimeFormat() throws Exception {
		assertEquals("2 Oct 2009 14:23", dateFormatter.dayMonthYearTime(onceUponATime));
	}
	
	@Test
	public void canGenerateDayMonthYearTimeWithSecondsFormat() throws Exception {
		assertEquals("2 Oct 2009 14:23:10", dateFormatter.dayMonthYearTimeWithSeconds(onceUponATime));
	}
	
	@Test
	public void canGenerateW3CDateTimeFormattedDate() throws Exception {		
		assertEquals("2009-10-02T14:23:10+01:00", dateFormatter.w3cDateTime(onceUponATime));
	}
	
	@Test
	public void alternativeLanguageFormattersCanStillGenerateValueW3CDateTimeFormattedDate() throws Exception {		
		assertEquals("2009-10-02T14:23:10+01:00", spanishDateFormatter.w3cDateTime(onceUponATime));
	}
	
	@Test
	public void canGenerateYearMonthDayUrlStub() throws Exception {		
		assertEquals("2009/oct/2", dateFormatter.yearMonthDayUrlStub(onceUponATime));
	}
	
	@Test
	public void canGenerateYearMonthUrlStub() throws Exception {		
		assertEquals("2009/oct", dateFormatter.yearMonthUrlStub(onceUponATime));
	}
	
	@Test
	public void canGenerateDurationFromSeconds() throws Exception {
		assertEquals("04:05", dateFormatter.secondsToDuration(245));
	}
	
	@Test
	public void shouldShowHoursIdDurationIsAnHourOrMore() throws Exception {
		assertEquals("01:00:00", dateFormatter.secondsToDuration(3600));
	}
	
}