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
        calendar(const calendar & other_calendar);
        ~calendar(void);

        // Display the week with its reminders.
        int display(void);
        // Display a day.
        int display_day(const char * date);
        // Add a new day.
        int add_day(const char * date);
        // Remove a day by date.
        int remove_day(const char * date);
        // Add a reminder to a specific day.
        int add_reminder(const char * date, reminder & to_add);
        // Remove the last reminder from a specific day.
        int remove_last_reminder(const char * date);
        // Clear all reminders in a day.
        int clear_reminders(const char * date);
        // Clear the whole calendar.
        int clear(void);
        // Check if a day exists.
        int has_day(char * date);

    private:
        // Display the week recursively.
        int display(day * & current);
        // Display a day recursively.
        int display_day(day * & current, const char * date);
        // Add a day recursively.
        int add_day(day * & current, const char * date);
        // Remove a day recursively.
        int remove_day(day * & current, const char * date);
        // Add a reminder recursively.
        int add_reminder(day * & current, const char * date, reminder & to_add);
        // Remove the last reminder recursively.
        int remove_last_reminder(day * & current, const char * date);
        // Clear reminders in a day recursively.
        int clear_reminders(day * & current, const char * date);
        // Clear the whole calendar.
        int clear(day * & current);
        // Check if a day exists recursively.
        int has_day(day * & current, char * date);
        // Copy days and reminders from another calendar recursively.
        int copy_days(day * & current, day & other_day);

        day * days; // days of the week
};

#endif
