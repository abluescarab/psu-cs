/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * calendar.cpp
 *
 * This is the implementation file for the calendar class. The calendar class 
 * manages a weekly calendar with todos.
 */
#include <iostream>
#include "calendar.h"

using namespace std;



calendar::calendar(void) {

}



calendar::calendar(const calendar & other_calendar) {

}



calendar::~calendar(void) {

}



// Display the week with its reminders.
int calendar::display(void) {
    return 1;
}



// Add a reminder to a specific day.
int calendar::add(const int day, const reminder & to_add) {
    return 1;
}



// Remove a reminder from a specific day.
int calendar::remove(const int day, const reminder & to_remove) {
    return 1;
}



// Clear all reminders.
int calendar::clear(void) {
    return 1;
}



// Display the week recursively.
int calendar::display(day * & current) {
    return 1;
}



// Add a reminder recursively.
int calendar::add(int current, const int day, const reminder & to_add) {
    return 1;
}



// Remove a reminder recursively.
int calendar::remove(int current, const int day, const reminder & to_remove) {
    return 1;
}



// Clear reminders recursively.
int calendar::clear(day * & current) {
    return 1;
}
