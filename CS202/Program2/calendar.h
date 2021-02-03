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
        // Add a reminder to a specific day.
        int add(const int day, const reminder & to_add);
        // Remove a reminder from a specific day.
        int remove(const int day, const reminder & to_remove);
        // Clear all reminders.
        int clear(void);

    private:
        // Display the week recursively.
        int display(day * & current);
        // Add a reminder recursively.
        int add(int current, const int day, const reminder & to_add);
        // Remove a reminder recursively.
        int remove(int current, const int day, const reminder & to_remove);
        // Clear reminders recursively.
        int clear(day * & current);

        day * days; // days of the week
};

#endif
