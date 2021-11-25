package dev.ricecx.frostygamerzone.minigameapi.utils;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Formats durations to a readable form
 *
 * @author khobbits, drtshock, vemacs
 * see: https://github.com/drtshock/Essentials/blob/2.x/Essentials/src/com/earth2me/essentials/utils/DateUtil.java
 */
public enum DurationFormatter {

    CONCISE {
        private final String[] names = new String[]{"y", "y", "m", "m", "d", "d", "h", "h", "m", "m", "s", "s"};

        @Override
        public String format(Calendar from, Calendar to) {
            return dateDiff(from, to, 4, this.names, true);
        }
    },

    CONCISE_MS {
        private final String[] names = new String[]{"y", "y", "m", "m", "d", "d", "h", "h", "m", "m", "s", "s"};

        @Override
        public String format(Calendar from, Calendar to) {
            return dateDiff(from, to, 4, this.names, true);
        }
    },

    CONCISE_LOW_ACCURACY {
        private final String[] names = new String[]{"y", "y", "m", "m", "d", "d", "h", "h", "m", "m", "s", "s"};

        @Override
        public String format(Calendar from, Calendar to) {
            return dateDiff(from, to, 2, this.names, true);
        }
    },

    LONG {
        private final String[] names = new String[]{"year", "years", "month", "months", "day", "days", "hour", "hours", "minute", "minutes", "second", "seconds"};

        @Override
        public String format(Calendar from, Calendar to) {
            return dateDiff(from, to, 4, this.names, false);
        }
    };

    /**
     * The calender type magic numbers to use when formatting
     */
    private static final int[] CALENDAR_TYPES = new int[]{
            Calendar.YEAR,
            Calendar.MONTH,
            Calendar.DAY_OF_MONTH,
            Calendar.HOUR_OF_DAY,
            Calendar.MINUTE,
            Calendar.SECOND
    };

    private static final int MAX_YEARS = 100000;

    /**
     * Formats the difference between two dates
     *
     * @param from        the start date
     * @param to          the end date
     * @param maxAccuracy how accurate the output should be (how many sections it'll have)
     * @param names       the names to use to format each of the corresponding {@link #CALENDAR_TYPES}
     * @return a formatted string
     */
    private static String dateDiff(Calendar from, Calendar to, int maxAccuracy, String[] names, boolean concise) {
        if (to.equals(from)) {
            return "now";
        }

        boolean future = to.after(from);

        StringBuilder sb = new StringBuilder();
        int accuracy = 0;
        for (int i = 0; i < CALENDAR_TYPES.length; i++) {
            if (accuracy > maxAccuracy) {
                break;
            }

            int diff = dateDiff(CALENDAR_TYPES[i], from, to, future);
            if (diff > 0) {
                int plural = diff > 1 ? 1 : 0;
                String unit = names[i * 2 + plural];

                sb.append(" ");
                sb.append(diff);
                if (!concise) {
                    sb.append(" ");
                }
                sb.append(unit);

                accuracy++;
            }
        }

        if (sb.length() == 0) {
            return "now";
        }

        return sb.toString().trim();
    }

    private static int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future) {
        int fromYear = fromDate.get(Calendar.YEAR);
        int toYear = toDate.get(Calendar.YEAR);
        if (Math.abs(fromYear - toYear) > MAX_YEARS) {
            toDate.set(Calendar.YEAR, fromYear + (future ? MAX_YEARS : -MAX_YEARS));
        }

        int diff = 0;
        long savedDate = fromDate.getTimeInMillis();
        while (future && !fromDate.after(toDate) || !future && !fromDate.before(toDate)) {
            savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future ? 1 : -1);
            diff++;
        }

        diff--;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }

    /**
     * Formats the time difference between two dates
     *
     * @param from the start date
     * @param to   the end date
     * @return the formatted duration string
     */
    public abstract String format(Calendar from, Calendar to);

    /**
     * Formats a duration, in seconds
     *
     * @param seconds the duration
     * @return the formatted duration string
     */
    public String format(long seconds) {
        Calendar from = new GregorianCalendar();
        from.setTimeInMillis(0);

        Calendar to = new GregorianCalendar();
        to.setTimeInMillis(seconds * 1000L);

        return format(from, to);
    }

    /**
     * Formats a duration, in millis
     *
     * @param millis the duration
     * @return the formatted duration string
     */
    public String formatMillis(long millis) {
        return format(millis / 1000);
    }


    /**
     * Formats the duration between the current time and the given millis timestamp
     *
     * @param millisTimestamp the timestamp, in milliseconds
     * @return the formatted duration string
     */
    public String formatDateDiff(long millisTimestamp) {
        long now = System.currentTimeMillis();
        return formatMillis(millisTimestamp - now);
    }
}
