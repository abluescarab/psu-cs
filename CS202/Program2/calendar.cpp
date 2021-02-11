/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * calendar.cpp
 *
 * This is the implementation file for the calendar class. The calendar class 
 * manages a weekly calendar with todos.
 */
#include <iostream>
#include "calendar.h"

using namespace std;



calendar::calendar(void) : days(nullptr) {}



calendar::calendar(const calendar & other_calendar) : days(nullptr) {
    copy_days(days, *other_calendar.days);
}



calendar::~calendar(void) {
    clear(days);
}



// Display the calendar with its reminders.
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::display(void) {
    return display(days);
}



// Display the calendar recursively.
// INPUT:
//  current: the current calendar day
// OUTPUT:
//  Returns the number of days displayed.
int calendar::display(day * & current) {
    if(!current)
        return 0;

    current->display();
    cout << endl;
    return display(current->get_next()) + 1;
}



// Display a day.
// INPUT:
//  date: the date to display
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::display_day(const char * date) {
    return display_day(days, date);
}



// Display a day recursively.
// INPUT:
//  current: the current day
//  date: the date to display
// OUTPUT:
//  Returns the number of reminders displayed.
int calendar::display_day(day * & current, const char * date) {
    if(!current)
        return 0;

    if(current->date_matches(date))
        return current->display();

    return display_day(current->get_next(), date);
}



// Add a new day.
// INPUT:
//  date: the date to add
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::add_day(const char * date) {
    return add_day(days, date);
}



// Add a day recursively.
// INPUT:
//  current: the current day
//  date: the date to add
// OUTPUT: 
//  0 if the day cannot be added.
//  1 if the day is added.
int calendar::add_day(day * & current, const char * date) {
    // do not allow duplicates
    if(current && current->date_matches(date))
        return 0;

    if(!current) {
        current = new day(date);
        return 1;
    }

    return add_day(current->get_next(), date);
}



// Remove a day by date.
// INPUT:
//  date: the date to remove
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::remove_day(const char * date) {
    return remove_day(days, date);
}



// Remove a day recursively.
// INPUT:
//  current: the current day
//  date: the date to remove
// OUTPUT:
//  0 if the day does not exist.
//  1 if the day was removed.
int calendar::remove_day(day * & current, const char * date) {
    if(!current)
        return 0;

    day * temp = current->get_next();

    if(current->date_matches(date)) {
        delete current;
        current = temp;
        return 1;
    }

    return remove_day(temp, date);
}



// Add a reminder to a specific day.
// INPUT:
//  date: the date to add a reminder to
//  to_add: the reminder to add
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::add_reminder(const char * date, reminder & to_add) {
    return add_reminder(days, date, to_add);
}



// Add a reminder recursively.
// INPUT:
//  current: the current day
//  date: the date to add a reminder to
//  to_add: the reminder to add
// OUTPUT:
//  0 if the reminder could not be added.
//  1 if the reminder was added.
int calendar::add_reminder(day * & current, const char * date, reminder & to_add) {
    if(!current)
        return 0;

    if(current->date_matches(date))
        return current->add(to_add);

    return add_reminder(current->get_next(), date, to_add);
}



// Remove the last reminder from a specific day.
// INPUT:
//  date: the date to remove the last reminder from
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::remove_last_reminder(const char * date) {
    return remove_last_reminder(days, date);
}



// Remove the last reminder recursively.
// INPUT:
//  current: the current day
//  date: date to remove from
// OUTPUT:
//  0 if the reminder could not be removed.
//  1 if the reminder was removed.
int calendar::remove_last_reminder(day * & current, const char * date) {
    if(!current)
        return 0;

    if(current->date_matches(date))
        return current->remove_last();

    return remove_last_reminder(current->get_next(), date);
}



// Clear all reminders in a day.
// INPUT:
//  date: the date to clear reminders from
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::clear_reminders(const char * date) {
    return clear_reminders(days, date);
}



// Clear reminders in a day recursively.
// INPUT:
//  current: the current day
//  date: date to clear reminders from
// OUTPUT:
//  Returns the number of reminders cleared.
int calendar::clear_reminders(day * & current, const char * date) {
    if(!current)
        return 0;

    if(current->date_matches(date))
        return current->clear();

    return clear_reminders(current->get_next(), date);
}



// Clear the whole calendar.
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::clear(void) {
    return clear(days);
}



// Clear the whole calendar recursively.
// INPUT:
//  current: the current day
// OUTPUT:
//  Returns the number of days cleared.
int calendar::clear(day * & current) {
    if(!current)
        return 0;

    day * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear(temp) + 1;
}



// Check if a day exists.
// INPUT:
//  date: the date to check for
// OUTPUT:
//  Returns the result of the recursive function.
int calendar::has_day(char * date) {
    return has_day(days, date);
}



// Check if a day exists recursively.
// INPUT:
//  current: the current day
//  date: the date to check for
// OUTPUT:
//  0 if the date was not found.
//  1 if the date was found.
int calendar::has_day(day * & current, char * date) {
    if(!current)
        return 0;

    if(current->date_matches(date))
        return 1;

    return has_day(current->get_next(), date);
}



// Copy days and reminders from another calendar recursively.
// INPUT:
//  current: the current day
//  other_day: the other day to copy from
// OUTPUT:
//  Returns the number of days copied.
int calendar::copy_days(day * & current, day & other_day) {
    if(other_day.is_empty())
        return 0;

    current = new day(other_day);
    return copy_days(current->get_next(), *other_day.get_next()) + 1;
}
