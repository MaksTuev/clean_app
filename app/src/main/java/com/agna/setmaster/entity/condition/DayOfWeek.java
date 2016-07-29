package com.agna.setmaster.entity.condition;

/**
 *
 */
public enum DayOfWeek {

    /**
     * The singleton instance for the day-of-week of Monday.
     * This has the numeric value of {@code 1}.
     */
    MONDAY,
    /**
     * The singleton instance for the day-of-week of Tuesday.
     * This has the numeric value of {@code 2}.
     */
    TUESDAY,
    /**
     * The singleton instance for the day-of-week of Wednesday.
     * This has the numeric value of {@code 3}.
     */
    WEDNESDAY,
    /**
     * The singleton instance for the day-of-week of Thursday.
     * This has the numeric value of {@code 4}.
     */
    THURSDAY,
    /**
     * The singleton instance for the day-of-week of Friday.
     * This has the numeric value of {@code 5}.
     */
    FRIDAY,
    /**
     * The singleton instance for the day-of-week of Saturday.
     * This has the numeric value of {@code 6}.
     */
    SATURDAY,
    /**
     * The singleton instance for the day-of-week of Sunday.
     * This has the numeric value of {@code 7}.
     */
    SUNDAY;
    /**
     * Private cache of all the constants.
     */
    public static final DayOfWeek[] ENUMS = DayOfWeek.values();

    //-----------------------------------------------------------------------

    /**
     * Obtains an instance of {@code DayOfWeek} from an {@code int} value.
     * <p>
     * {@code DayOfWeek} is an enum representing the 7 days of the week.
     * This factory allows the enum to be obtained from the {@code int} value.
     * The {@code int} value follows the ISO-8601 standard, from 1 (Monday) to 7 (Sunday).
     *
     * @param dayOfWeek the day-of-week to represent, from 1 (Monday) to 7 (Sunday)
     * @return the day-of-week singleton, not null
     * @throws IllegalArgumentException if the day-of-week is invalid
     */
    public static DayOfWeek of(int dayOfWeek) {
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            throw new IllegalArgumentException("Invalid value for DayOfWeek: " + dayOfWeek);
        }
        return ENUMS[dayOfWeek - 1];
    }
}