/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * day.cpp
 *
 * This is the implementation file for the day class. The day class manages a 
 * day in a calendar week with all its corresponding todos.
 */
#include <iostream>
#include "day.h"

using namespace std;



day::day(void) {

}



day::day(const day & other_day) {

}



day::~day(void) {

}



// Get the next day.
day * & day::get_next(void) {

}



// Get the previous day.
day * & day::get_prev(void) {

}



// Display the contents of the day.
int day::display(void) const {
    return 1;
}



// Add a reminder.
int day::add(const reminder & to_add) {
    return 1;
}



// Remove a reminder.
int day::remove(const reminder & to_remove) {
    return 1;
}



// Clear the reminders.
int day::clear(void) {
    return 1;
}



// Display the contents of the day recursively.
int day::display(reminder * & current) {
    return 1;
}



// Add a reminder recursively.
int day::add(reminder * & current, const reminder & to_add) {
    return 1;
}



// Remove a reminder recursively.
int day::remove(reminder * & current, const reminder & to_remove) {
    return 1;
}



// Clear reminders recursively.
int day::clear(reminder * & current) {
    return 1;
}
