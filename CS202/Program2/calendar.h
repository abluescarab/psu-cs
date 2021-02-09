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
        // Add a new day.
        int add_day(const char * date);
        // Remove a day by date.
        int remove_day(const char * date);
        // Add a reminder to a specific day.
        int add_reminder(const char * date, const reminder & to_add);
        // Remove a reminder from a specific day.
        int remove_reminder(const char * date, reminder & to_remove);
        // Clear all reminders in a day.
        int clear_reminders(const char * date);
        // Clear the whole calendar.
        int clear(void);

    private:
        // Display the week recursively.
        int display(day * & current);
        // Add a day recursively.
        int add_day(day * & current, const char * date);
        // Remove a day recursively.
        int remove_day(day * & current, const char * date);
        // Add a reminder recursively.
        int add_reminder(day * & current, const char * date, const reminder & to_add);
        // Remove a reminder recursively.
        int remove_reminder(day * & current, const char * date, reminder & to_remove);
        // Clear reminders in a day recursively.
        int clear_reminders(day * & current, const char * date);
        // Clear the whole calendar.
        int clear(day * & current);
        // Copy days and reminders from another calendar recursively.
        int copy_days(day * & current, day & other_day);

        day * days; // days of the week
};

#endif
