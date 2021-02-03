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
        day(const day & other_day);
        ~day(void);

        // Get the next day.
        day * & get_next(void);
        // Get the previous day.
        day * & get_prev(void);
        // Display the contents of the day.
        int display(void) const;
        // Add a reminder.
        int add(const reminder & to_add);
        // Remove a reminder.
        int remove(const reminder & to_remove);
        // Clear the reminders.
        int clear(void);

    private:
        // Display the contents of the day recursively.
        int display(reminder * & current);
        // Add a reminder recursively.
        int add(reminder * & current, const reminder & to_add);
        // Remove a reminder recursively.
        int remove(reminder * & current, const reminder & to_remove);
        // Clear reminders recursively.
        int clear(reminder * & current);

        char * name; // name of the day, e.g. Sunday
        reminder * reminders; // list of reminders
        day * next; // next day
        day * prev; // previous day
};

#endif
