/* Alana Gilston - 2/1/2021 - CS202 - Program 2
 * calendar.h
 *
 * This is the header file for the calendar class. The calendar class manages a weekly 
 * calendar with todos.
 */
#ifndef CALENDAR_H
#define CALENDAR_H
#include "day.h"

class calendar {
    public:
        calendar(void);
        calendar(const int weeks_to_add);
        calendar(const calendar & other_calendar);
        ~calendar(void);

        // Display the week with its reminders.
        int display(void);
        // Add a new week.
        int add_week(void);
        // Remove a week by week number.
        int remove_week(const int week_number);
        // Add a reminder to a specific day.
        int add_reminder(const int day, const reminder & to_add);
        // Remove a reminder from a specific day.
        int remove_reminder(const int day, const reminder & to_remove);
        // Clear all reminders.
        int clear_reminders(void);
        // Clear the whole calendar.
        int clear(void);

    private:
        const char days_of_week[] = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
        };

        // Display the week recursively.
        int display(day * & current);
        // Add a week recursively.
        int add_week(day * & current, int current_day);
        // Remove a week by week number recursively.
        int remove_week(day * & current, int current_day);
        // Add a reminder recursively.
        int add_reminder(int current, const int day, const reminder & to_add);
        // Remove a reminder recursively.
        int remove_reminder(int current, const int day, const reminder & to_remove);
        // Clear reminders recursively.
        int clear_reminders(day * & current);
        // Clear the whole calendar.
        int clear(day * & current);
        // Copy days and reminders from another calendar recursively.
        int copy_days(day * & current, day & other_day);
        // Get the last day of a week.
        day * & get_last_day_of_week(day * & current, int week_number);

        day * days; // days of the week
};

#endif
