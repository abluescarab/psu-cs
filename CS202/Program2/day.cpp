/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * day.cpp
 *
 * This is the implementation file for the day class. The day class manages a 
 * day in a calendar week with all its corresponding todos.
 */
#include <iostream>
#include <cstring>
#include "day.h"
#include "utils.h"

using namespace std;



day::day(void) : date(nullptr), reminders(nullptr), next(nullptr), prev(nullptr) {}



day::day(const char * new_date) : 
    date(nullptr), 
    reminders(nullptr), 
    next(nullptr), 
    prev(nullptr) {
    copy_char_array(date, new_date);
}



day::day(const day & other_day) : 
    date(nullptr), 
    reminders(nullptr), 
    next(other_day.next), 
    prev(other_day.prev) {
    copy_char_array(date, other_day.date);
    copy_reminders(reminders, *other_day.reminders);
}



day::~day(void) {
    clear();
    delete [] date;
    date = nullptr;
    next = nullptr;
    prev = nullptr;
}



// Get the next day.
// OUTPUT:
//  Returns the next pointer.
day * & day::get_next(void) {
    return next;
}



// Get the previous day.
// OUTPUT:
//  Returns the previous pointer.
day * & day::get_prev(void) {
    return prev;
}



// Display the contents of the day.
// OUTPUT:
//  Returns the result of the recursive function.
int day::display(void) {
    int reminder_count = 0;

    cout << date << ":" << endl;
    reminder_count = display(reminders);

    if(reminder_count == 0)
        cout << "No reminders for this date." << endl;

    return reminder_count;
}



// Display the contents of the day recursively.
// INPUT:
//  current: the current reminder
// OUTPUT: 
//  Returns the number of reminders displayed.
int day::display(reminder * & current) {
    if(!current)
        return 0;

    return current->display() + display(current->get_next());
}



// Add a reminder.
// INPUT:
//  to_add: the reminder to add
// OUTPUT:
//  Returns the result of the recursive function.
int day::add(const reminder & to_add) {
    if(to_add.is_empty())
        return 0;

    return add(reminders, to_add);
}



// Add a reminder recursively.
// INPUT:
//  current: the current reminder
//  to_add: the reminder to add
// OUTPUT:
//  1 when the reminder is added.
int day::add(reminder * & current, const reminder & to_add) {
    if(!current) {
        current = new reminder(to_add);
        return 1;
    }
    
    return add(current->get_next(), to_add);
}



// Remove the last reminder.
// OUTPUT:
//  0 if there are no reminders.
//  Otherwise returns the result of the recursive function.
int day::remove_last(void) {
    if(!reminders)
        return 0;

    return remove_last(reminders);
}



// Remove the last reminder recursively.
// INPUT:
//  current: the current day
// OUTPUT:
//  1 when the last reminder is removed.
int day::remove_last(reminder * & current) {
    reminder * next = current->get_next();

    if(!next) {
        delete current;
        return 1;
    }

    return remove_last(next);
}



// Clear the reminders.
// OUTPUT:
//  Returns the result of the recursive function.
int day::clear(void) {
    return clear(reminders);
}



// Clear reminders recursively.
// INPUT:
//  current: the current reminder
// OUTPUT:
//  Returns the number of reminders cleared.
int day::clear(reminder * & current) {
    if(!current)
        return 0;

    reminder * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear(temp) + 1;
}



// Check if the date matches provided input.
// INPUT:
//  other_date: the date to compare to
// OUTPUT:
//  Returns the result of strcmp.
int day::date_matches(const char * other_date) {
    return strcmp(other_date, date) == 0;
}



// Copy reminders from another day.
// INPUT:
//  current: the current reminder
//  other_reminder: the other reminder to copy from
// OUTPUT: 
//  Returns the number of reminders copied.
int day::copy_reminders(reminder * & current, reminder & other_reminder) {
    if(other_reminder.is_empty())
        return 0;

    current = new reminder(other_reminder);
    return copy_reminders(current->get_next(), *other_reminder.get_next()) + 1;
}
        


// Check if day is empty.
// OUTPUT:
//  Returns the result of char_array_empty.
int day::is_empty(void) const {
    return char_array_empty(date);
}
