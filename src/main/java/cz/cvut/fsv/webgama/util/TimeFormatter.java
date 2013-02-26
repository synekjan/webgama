package cz.cvut.fsv.webgama.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class TimeFormatter {

	private PeriodFormatter formatter;

	public String periodFromNow(DateTime date) {

		Period period = new Period(date, new DateTime());

		if (period.getYears() > 0) {
			formatter = new PeriodFormatterBuilder().appendYears()
					.appendSuffix(" year ", " years ").toFormatter();
		} else if (period.getMonths() > 0) {
			formatter = new PeriodFormatterBuilder().appendMonths()
					.appendSuffix(" month ", " months ").toFormatter();
		} else if (period.getWeeks() > 0) {
			formatter = new PeriodFormatterBuilder().appendWeeks()
					.appendSuffix(" week ", " weeks ").toFormatter();
		} else if (period.getDays() > 0) {
			formatter = new PeriodFormatterBuilder().appendDays()
					.appendSuffix(" day ", " days ").toFormatter();
		} else if (period.getHours() > 0) {
			formatter = new PeriodFormatterBuilder().appendHours()
					.appendSuffix(" hour ", " hours ").toFormatter();
		} else if (period.getMinutes() > 0) {
			formatter = new PeriodFormatterBuilder().appendMinutes()
					.appendSuffix(" minute ", " minutes ").toFormatter();
		} else {
			formatter = new PeriodFormatterBuilder().appendSeconds()
					.appendSuffix(" second ", " seconds ").toFormatter();
		}
		
		
		String formattedDate = formatter.print(period) + " ago";
		return formattedDate;

	}

}
