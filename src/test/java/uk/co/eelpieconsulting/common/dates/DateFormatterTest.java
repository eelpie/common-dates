package uk.co.eelpieconsulting.common.dates;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

public class DateFormatterTest {
    
	DateFormatter dateFormatter = new DateFormatter();
	
	@Test
    public void testShouldGiveNiceTimeDeltas() throws Exception {    	
    	DateTime now = new DateTime();
    	
    	DateTime lessThanOneMinuteAgo = now.minusSeconds(30);
    	assertEquals("less than 1 minute ago", dateFormatter.timeSince(lessThanOneMinuteAgo.toDate()));
    	
    	DateTime fiveMinutesBefore = now.minusMinutes(5);
        assertEquals("5 minutes ago", dateFormatter.timeSince(fiveMinutesBefore.toDate()));   
       
        DateTime anHourAgo = now.minusHours(1);
        assertEquals("1 hour ago", dateFormatter.timeSince(anHourAgo.toDate()));
        
        DateTime twoHoursAgo = now.minusHours(2);
        assertEquals("2 hours ago", dateFormatter.timeSince(twoHoursAgo.toDate()));
                
        DateTime oneDayAgo = now.minusDays(1);
        assertEquals("1 day ago", dateFormatter.timeSince(oneDayAgo.toDate()));
        
        DateTime oneWeekAgo = now.minusWeeks(1);
        assertEquals("1 week ago", dateFormatter.timeSince(oneWeekAgo.toDate()));                        
    }
    
}
