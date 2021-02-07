/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * day.cpp
 *
 * This is the implementation file for the day class. The day class manages a 
 * day in a calendar week with all its corresponding todos.
 */
#include <iostream>
#include "day.h"
#include "utils.h"

using namespace std;



day::day(void) : name(nullptr), reminders(nullptr), next(nullptr), prev(nullptr) {}



day::day(const char * new_name) : 
    name(nullptr), 
    reminders(nullptr), 
    next(nullptr), 
    prev(nullptr) {
    copy_char_array(name, new_name);
}



day::day(const day & other_day) : 
    name(nullptr), 
    reminders(nullptr), 
    next(other_day.next), 
    prev(other_day.prev) {
    copy_char_array(name, other_day.name);
    copy_reminders(reminders, *other_day.reminders);
}



day::~day(void) {
    clear();
    delete [] name;
    name = nullptr;
    next = nullptr;
    prev = nullptr;
}



// Get the next day.
day * & day::get_next(void) {
    return next;
}



// Get the previous day.
day * & day::get_prev(void) {
    return prev;
}



// Display the contents of the day.
int day::display(void) {
    cout << name << ":" << endl;
    return display(reminders);
}



// Display the contents of the day recursively.
int day::display(reminder * & current) {
    return current->display() + display(current->get_next());
}



// Add a reminder.
int day::add(const reminder & to_add) {
    if(to_add.is_empty())
        return 0;

    return add(reminders, to_add);
}



// Add a reminder recursively.
int day::add(reminder * & current, const reminder & to_add) {
    if(!current) {
        current = new reminder(to_add);
        return 1;
    }
    
    return add(current->get_next(), to_add);
}



// Remove a reminder.
int day::remove(const reminder & to_remove) {
    if(to_remove.is_empty())
        return 0;

    return remove(reminders, to_remove);
}



// Remove a reminder recursively.
int day::remove(reminder * & current, const reminder & to_remove) {
    if(!current)
        return 0;

    reminder * temp = current->get_next();

    // downcasting?
    if(current->matches(to_remove)) {
        delete current;
        current = temp;
        return 1;
    }

    return remove(temp, to_remove);
}



// Clear the reminders.
int day::clear(void) {
    return clear(reminders);
}



// Clear reminders recursively.
int day::clear(reminder * & current) {
    if(!current)
        return 0;

    reminder * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear(temp) + 1;
}



// Copy reminders from another day.
int day::copy_reminders(reminder * & current, reminder & other_reminder) {
    if(other_reminder.is_empty())
        return 0;

    current = new reminder(other_reminder);
    return copy_reminders(current->get_next(), *other_reminder.get_next()) + 1;
}
