package se.digitman.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created 2011-jan-24
 * @author thomas
 */
public class DateUtilities {

    private final Date date;
    private final static String[] MONTHS = {
        "januari", "februari", "mars", "april", "maj", "juni", "juli", "augusti", "september", "oktober", "november", "december"
    };
    private final static String[] WEEKDAYS = {
        "söndag", "måndag", "tisdag", "onsdag", "torsdag", "fredag", "lördag"
    };

    public DateUtilities(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        this.date = c.getTime();
    }

    public DateUtilities(int year, int month, int day) {
        this.date = new GregorianCalendar(year, month - 1, day).getTime();
    }

    public DateUtilities(String dateStr) {
        if (dateStr.length() == 0) {
            this.date = new Date();
        } else {
            try {
                this.date = getDateFormat().parse(dateStr);
            } catch (ParseException ex) {
                throw new DateUtilityException("Ogiltigt strängformat: \"" + dateStr + "\"", ex);
            }
        }
    }

    @Override
    public String toString() {
        return getDateFormat().format(date);
    }

    public final DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public DateUtilities addYears(int amount) {
        return addTime(Calendar.YEAR, amount);
    }

    public DateUtilities addMonths(int amount) {
        return addTime(Calendar.MONTH, amount);
    }

    public DateUtilities addDays(int amount) {
        return addTime(Calendar.DATE, amount);
    }

    public DateUtilities setYear(int year) {
        return setTime(Calendar.YEAR, year);
    }

    public int getYear() {
        return getTime(Calendar.YEAR);
    }

    public DateUtilities setMonth(int month) {
        return setTime(Calendar.MONTH, month - 1);
    }

    public int getMonth() {
        return getTime(Calendar.MONTH) + 1;
    }

    public String getMonthName() {
        return MONTHS[getMonth() - 1];
    }

    public String getWeekday() {
        return WEEKDAYS[new GregorianCalendar(getYear(), getMonth() - 1, getDay()).get(Calendar.DAY_OF_WEEK) - 1];
    }

    public DateUtilities setLastDayOfMonth() {
        DateUtilities result = addMonths(1);
        result = result.setDay(1);
        return result.addDays(-1);
    }

    public DateUtilities setDay(int day) {
        return setTime(Calendar.DAY_OF_MONTH, day);
    }

    public int getDay() {
        return getTime(Calendar.DAY_OF_MONTH);
    }

    public int getDiffInDays(Date comparedDate) {
        return (int) ((date.getTime() - comparedDate.getTime()) / 86400000L);
    }

    public Date get() {
        return date;
    }

    public boolean after(Date comparedDate) {
        return toString().compareTo(new DateUtilities(comparedDate).toString()) > 0;
    }

    public boolean before(Date comparedDate) {
        return toString().compareTo(new DateUtilities(comparedDate).toString()) < 0;
    }

    public boolean equalsDate(Date comparedDate) {
        return equals(new DateUtilities(comparedDate));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DateUtilities)) {
            return false;
        }
        return toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    private int getTime(int dateField) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(dateField);
    }

    private DateUtilities setTime(int dateField, int amount) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(dateField, amount);
        return new DateUtilities(c.getTime());
    }

    private DateUtilities addTime(int dateField, int amount) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(dateField, amount);
        return new DateUtilities(c.getTime());
    }
}
