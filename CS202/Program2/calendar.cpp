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



// Display the week with its reminders.
int calendar::display(void) {
    cout << "Calendar:" << endl;
    return display(days);
}



// Display the calendar recursively.
int calendar::display(day * & current) {
    return 1;
}



// Add a new day.
int calendar::add_day(const char * date) {
    return add_day(days, date);
}



// Add a day recursively.
int calendar::add_day(day * & current, const char * date) {
    if(!current) {
        current = new day(date);
        return 1;
    }

    return add_day(current->get_next(), date);
}



// Remove a day by date.
int calendar::remove_day(const char * date) {
    return remove_day(days, date);
}



// Remove a day recursively.
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
int calendar::add_reminder(const char * date, const reminder & to_add) {
    return add_reminder(days, date, to_add);
}



// Add a reminder recursively.
int calendar::add_reminder(day * & current, const char * date, const reminder & to_add) {
    if(!current)
        return 0;

    if(current->date_matches(date))
        return current->add(to_add);

    return add_reminder(current->get_next(), date, to_add);
}



// Remove a reminder from a specific day.
int calendar::remove_reminder(const char * date, reminder & to_remove) {
    return remove_reminder(days, date, to_remove);
}



// Remove a reminder recursively.
int calendar::remove_reminder(day * & current, const char * date, reminder & to_remove) {
    if(!current)
        return 0;

    if(current->date_matches(date))
        return current->remove(to_remove);

    return remove_reminder(current->get_next(), date, to_remove);
}



// Clear all reminders in a day.
int calendar::clear_reminders(const char * date) {
    return clear_reminders(days, date);
}



// Clear reminders in a day recursively.
int calendar::clear_reminders(day * & current, const char * date) {
    if(!current)
        return 0;

    if(current->date_matches(date))
        return current->clear();

    return clear_reminders(current->get_next(), date);
}



// Clear the whole calendar.
int calendar::clear(void) {
    return clear(days);
}



// Clear the whole calendar recursively.
int calendar::clear(day * & current) {
    if(!current)
        return 0;

    day * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear(temp) + 1;
}



// Copy days and reminders from another calendar recursively.
int calendar::copy_days(day * & current, day & other_day) {
    if(other_day.is_empty())
        return 0;

    current = new day(other_day);
    return copy_days(current->get_next(), *other_day.get_next()) + 1;
}
