/* Alana Gilston - 2/1/2021 - CS202 - Program 2
 * day.h
 *
 * This is the header file for the day class. The day class manages a day in a 
 * calendar week with all its corresponding todos.
 */
#ifndef DAY_H
#define DAY_H
#include "reminder.h"

class day {
    public:
        day(void);
        day(const char * new_date);
        day(const day & other_day);
        ~day(void);

        // Get the next day.
        day * & get_next(void);
        // Get the previous day.
        day * & get_prev(void);
        // Display the contents of the day.
        int display(void);
        // Add a reminder.
        int add(const reminder & to_add);
        // Remove the last reminder.
        int remove_last(void);
        // Clear the reminders.
        int clear(void);
        // Check if the date matches provided input.
        int date_matches(const char * other_date);
        // Check if day is empty.
        int is_empty(void) const;

    private:
        // Display the contents of the day recursively.
        int display(reminder * & current);
        // Add a reminder recursively.
        int add(reminder * & current, const reminder & to_add);
        // Remove the last reminder recursively.
        int remove_last(reminder * & current);
        // Clear reminders recursively.
        int clear(reminder * & current);
        // Copy reminders from another day.
        int copy_reminders(reminder * & current, reminder & other_reminder);

        char * date; // date of the current day
        reminder * reminders; // list of reminders
        day * next; // next day
        day * prev; // previous day
};

#endif
