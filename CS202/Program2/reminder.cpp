/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * reminder.cpp
 *
 * This is the implementation file for the reminder class. The reminder class 
 * also has the subclasses appointment, class session, and task. The reminder 
 * class and its subclasses manage various types of daily todos.
 */
#include <iostream>
#include <cstring>
#include "reminder.h"
#include "utils.h"

using namespace std;



reminder::reminder(void) : 
    complete(false),
    time(nullptr),
    text(nullptr),
    next(nullptr) {
    }



reminder::reminder(const char * new_time, const char * new_text) : 
    complete(false),
    time(nullptr),
    text(nullptr),
    next(nullptr) {
        copy_char_array(time, new_time);
        copy_char_array(text, new_text);
    }



reminder::reminder(const reminder & other_reminder) : 
    complete(other_reminder.complete),
    time(nullptr),
    text(nullptr),
    next(other_reminder.next) {
        copy_char_array(time, other_reminder.time);
        copy_char_array(text, other_reminder.text);
    }



reminder::~reminder(void) {
    complete = false;
    delete [] time;
    time = nullptr;
    delete [] text;
    text = nullptr;
    next = nullptr;
}



// Get the next reminder in the LLL.
// OUTPUT:
//  Returns the next pointer.
reminder * & reminder::get_next(void) {
    return next;
}



// Display the reminder.
// OUTPUT:
//  1 when the reminder is displayed.
int reminder::display(void) {
    if(complete)
        cout << "(Completed) ";
    else
        cout << "(Not completed) ";

    cout <<  time << ": " << text << endl;
    return 1;
}



// Mark the reminder as complete.
// OUTPUT:
//  1 when the reminder is marked complete.
int reminder::mark_complete(void) {
    complete = true;
    return 1;
}



// Check if the reminder is missing required data.
// OUTPUT:
//  Returns the result of char_array_empty.
int reminder::is_empty(void) const {
    return char_array_empty(time);
}



// Check if the reminder matches another reminder.
// INPUT:
//  other_reminder: other reminder to compare against
// OUTPUT:
//  0 if the reminders do not match.
//  1 if the reminders match.
int reminder::matches(reminder & other_reminder) {
    return strcmp(other_reminder.text, text) == 0 && 
        strcmp(other_reminder.time, time) == 0 &&
        complete == other_reminder.complete;
}



appointment::appointment(void) : location(nullptr) {}



appointment::appointment(const char * new_time, 
        const char * new_text,
        const char * new_location) :
    reminder(new_time, new_text),
    location(nullptr) {
        copy_char_array(location, new_location);
    }



appointment::appointment(const appointment & other_appointment) : 
    reminder(other_appointment),
    location(nullptr) {
        copy_char_array(location, other_appointment.location);
    }



appointment::~appointment(void) {
    delete [] location;
    location = nullptr;
}



// Display the appointment.
// OUTPUT:
//  1 when the appointment is displayed.
int appointment::display(void) {
    reminder::display();
    cout << "Appointment at: " << location << endl;
    return 1;
}



// Check if the reminder matches another reminder.
// INPUT:
//  other_reminder: reminder to check against
// OUTPUT:
//  0 if the appointments do not match.
//  1 if the appointments match.
int appointment::matches(reminder & other_reminder) {
    appointment * other_appointment = dynamic_cast<appointment*>(&other_reminder);

    if(!other_appointment)
        return 0;

    return reminder::matches(other_reminder) && 
        strcmp(other_appointment->location, location) == 0;
}



class_session::class_session(void) : instructor(nullptr), location(nullptr) {}



class_session::class_session(const char * new_time, 
        const char * new_text,
        const char * new_location, 
        const char * new_instructor) :
    reminder(new_time, new_text),
    instructor(nullptr),
    location(nullptr) {
        copy_char_array(instructor, new_instructor);
        copy_char_array(location, new_location);
    }



class_session::class_session(const class_session & other_session) :
    reminder(other_session),
    instructor(nullptr),
    location(nullptr) {
        copy_char_array(instructor, other_session.instructor);
        copy_char_array(location, other_session.location);
    }



class_session::~class_session(void) {
    delete [] instructor;
    instructor = nullptr;
    delete [] location;
    location = nullptr;
}



// Display the session.
//  1 when the session is displayed.
int class_session::display(void) {
    reminder::display();
    cout << "Instructor: " << instructor << endl;
    cout << "Class location: " << location << endl;
    return 1;
}



// Check if the reminder matches another reminder.
// INPUT:
//  other_reminder: the reminder to compare against
// OUTPUT:
//  0 if the sessions do not match.
//  1 if the sessions match.
int class_session::matches(reminder & other_reminder) {
    class_session * other_session = dynamic_cast<class_session*>(&other_reminder);

    if(!other_session)
        return 0;

    return reminder::matches(other_reminder) && 
        strcmp(other_session->instructor, instructor) == 0 &&
        strcmp(other_session->location, location) == 0;
}



task::task(void) : priority(0), subtasks(nullptr) {}



task::task(const char * new_time, const char * new_text, const int new_priority) :
    reminder(new_time, new_text),
    priority(new_priority),
    subtasks(nullptr) {
}



task::task(const task & other_task) : 
    reminder(other_task),
    priority(other_task.priority),
    subtasks(nullptr) {
        copy_subtasks(subtasks, *other_task.subtasks);
    }



task::~task(void) {
    priority = 0;
    clear_subtasks(subtasks);
}



// Display the task.
// OUTPUT:
//  Returns the result of the recursive function + 1 (number of tasks displayed).
int task::display(void) {
    reminder::display();
    cout << "Priority: " << priority << endl;
    return display(subtasks) + 1;
}



// Display with subtasks recursively.
// INPUT: 
//  current: the current task
// OUTPUT:
//  Returns the number of subtasks displayed.
int task::display(task * & current) {
    if(!current)
        return 0;

    task * next = dynamic_cast<task*>(current->get_next());
    current->display();
    return display(next);
}



// Change the priority.
// INPUT:
//  new_priority: the priority to change to
// OUTPUT:
//  1 when the priority is changed.
int task::change_priority(const int new_priority) {
    priority = new_priority;
    return 1;
}



// Check if the reminder matches another reminder.
// INPUT:
//  other_reminder: the reminder to match against
// OUTPUT:
//  0 if the tasks do not match.
//  1 if the tasks match.
int task::matches(reminder & other_reminder) {
    task * other_task = dynamic_cast<task*>(&other_reminder);

    if(!other_task)
        return 0;

    return reminder::matches(other_reminder) && (priority == other_task->priority);
}



// Clear subtasks recursively.
// INPUT:
//  current: the current subtask
// OUTPUT:
//  Returns the number of subtasks cleared.
int task::clear_subtasks(task * & current) {
    if(!current)
        return 0;

    task * temp = dynamic_cast<task *>(current->get_next());
    delete current;
    return clear_subtasks(temp) + 1;
}



// Copy subtasks from another task.
// INPUT:
//  current: the current subtask
//  other_subtask: the task to copy from
// OUPUT:
//  Returns the number of tasks copied.
int task::copy_subtasks(task * & current, task & other_subtask) {
    if(other_subtask.is_empty())
        return 0;

    current = new task(other_subtask);
    current = dynamic_cast<task*>(current->get_next());
    
    return copy_subtasks(current, *dynamic_cast<task*>(other_subtask.get_next())) + 1;
}
