package cz.cvut.fsv.webgama.util;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.context.MessageSource;

public class TimeFormatter {

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	private PeriodFormatter formatter;

	public String periodFromNow(DateTime date, Locale locale) {

		Period period = new Period(date, new DateTime());

		if (period.getYears() > 0) {
			formatter = new PeriodFormatterBuilder()
			.appendPrefix(messageSource.getMessage("time.formatter.pre.element", null, locale))
			.appendYears()
			.appendSuffix(messageSource.getMessage("time.formatter.year", null, locale), messageSource.getMessage("time.formatter.years", null, locale))
			.appendSuffix(messageSource.getMessage("time.formatter.post.element", null, locale)).toFormatter();
		} else if (period.getMonths() > 0) {
			formatter = new PeriodFormatterBuilder()
			.appendPrefix(messageSource.getMessage("time.formatter.pre.element", null, locale))
			.appendMonths()
			.appendSuffix(messageSource.getMessage("time.formatter.month", null, locale), messageSource.getMessage("time.formatter.months", null, locale))
			.appendSuffix(messageSource.getMessage("time.formatter.post.element", null, locale)).toFormatter();
		} else if (period.getWeeks() > 0) {
			formatter = new PeriodFormatterBuilder()
			.appendPrefix(messageSource.getMessage("time.formatter.pre.element", null, locale))
			.appendWeeks()
			.appendSuffix(messageSource.getMessage("time.formatter.week", null, locale), messageSource.getMessage("time.formatter.weeks", null, locale))
			.appendSuffix(messageSource.getMessage("time.formatter.post.element", null, locale)).toFormatter();
		} else if (period.getDays() > 0) {
			formatter = new PeriodFormatterBuilder()
			.appendPrefix(messageSource.getMessage("time.formatter.pre.element", null, locale))
			.appendDays()
			.appendSuffix(messageSource.getMessage("time.formatter.day", null, locale), messageSource.getMessage("time.formatter.days", null, locale))
			.appendSuffix(messageSource.getMessage("time.formatter.post.element", null, locale)).toFormatter();
		} else if (period.getHours() > 0) {
			formatter = new PeriodFormatterBuilder()
			.appendPrefix(messageSource.getMessage("time.formatter.pre.element", null, locale))
			.appendHours()
			.appendSuffix(messageSource.getMessage("time.formatter.hour", null, locale), messageSource.getMessage("time.formatter.hours", null, locale))
			.appendSuffix(messageSource.getMessage("time.formatter.post.element", null, locale)).toFormatter();
		} else if (period.getMinutes() > 0) {
			formatter = new PeriodFormatterBuilder()
			.appendPrefix(messageSource.getMessage("time.formatter.pre.element", null, locale))
			.appendMinutes()
			.appendSuffix(messageSource.getMessage("time.formatter.minute", null, locale), messageSource.getMessage("time.formatter.minutes", null, locale))
			.appendSuffix(messageSource.getMessage("time.formatter.post.element", null, locale)).toFormatter();
		} else {
			formatter = new PeriodFormatterBuilder()
					.appendPrefix(messageSource.getMessage("time.formatter.pre.element", null, locale))
					.appendSeconds()
					.appendSuffix(messageSource.getMessage("time.formatter.second", null, locale), messageSource.getMessage("time.formatter.seconds", null, locale))
					.appendSuffix(messageSource.getMessage("time.formatter.post.element", null, locale)).toFormatter();
		}

		String formattedDate = formatter.print(period);
		return formattedDate;

	}
}
