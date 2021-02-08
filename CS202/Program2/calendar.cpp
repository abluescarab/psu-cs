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



calendar::calendar(const int weeks_to_add) : days(nullptr) {
    for(int i = 0; i < weeks_to_add; ++i) {
        add_week();
    }
}



calendar::calendar(const calendar & other_calendar) : days(nullptr) {
    copy_days(days, other_calendar.days);
}



calendar::~calendar(void) {
    clear(days);
}



// Display the week with its reminders.
int calendar::display(void) {
    return 1;
}



// Add a new week.
int calendar::add_week(void) {
    return add_week(days, 0);
}



// Add a week recursively.
int calendar::add_week(day * & current, int current_day) {
    if(!current) {
        current = new day(days_of_week[current_day]);
        ++current_day;
    }

    if(current_day == 6)
        return 1;

    return add_week(current->get_next(), current_day);
}



// Remove a week by week number.
int calendar::remove_week(const int week_number) {
    day * week = get_last_day_of_week(week_number);
    
    if(!week)
        return 0;

    return remove_week(week->get_next(), 0);
}



// Remove a week by week number recursively.
int calendar::remove_week(day * & current, int current_day) {
    if(!current && current_day != 6)
        return 0;
    else if(current_day == 6)
        return 1;

    day * next = current->get_next();
    delete current;
    return remove_week(next, ++current_day);
}



// Add a reminder to a specific day.
int calendar::add_reminder(const int day, const reminder & to_add) {
    return 1;
}



// Remove a reminder from a specific day.
int calendar::remove_reminder(const int day, const reminder & to_remove) {
    return 1;
}



// Clear all reminders.
int calendar::clear_reminders(void) {
    return 1;
}



// Display the week recursively.
int calendar::display(day * & current) {
    return 1;
}



// Add a reminder recursively.
int calendar::add_reminder(int current, const int day, const reminder & to_add) {
    return 1;
}



// Remove a reminder recursively.
int calendar::remove_reminder(int current, const int day, const reminder & to_remove) {
    return 1;
}



// Clear reminders recursively.
int calendar::clear_reminders(day * & current) {
    return 1;
}



// Copy days and reminders from another calendar recursively.
int calendar::copy_days(day * & current, day & other_day) {
    return 1;
}



// Get the last day of a week.
day * & calendar::get_last_day_of_week(day * & current, int week_number) {
    if(!current)
        return nullptr;

    if(current->name_matches("Saturday")) {
        --week_number;
        
        if(week_number == 1)
            return current;
    }

    return get_last_day_of_week(current->get_next(), skip_weeks);
}
