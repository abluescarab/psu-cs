/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * reminder.cpp
 *
 * This is the implementation file for the reminder class. The reminder class 
 * also has the subclasses appointment, class session, and task. The reminder 
 * class and its subclasses manage various types of daily todos.
 */
#include <iostream>
#include "reminder.h"

using namespace std;



note::note(void) {

}



note::note(const note & other_note) {

}



note::~note(void) {

}



// Get the next note.
note * & note::get_next(void) {

}



// Display the contents of the note.
int note::display(void) const {
    return 1;
}



reminder::reminder(void) {

}



reminder::reminder(const reminder & other_reminder) {

}



reminder::~reminder(void) {

}



// Get the next reminder in the LLL.
reminder * & reminder::get_next(void) {

}



// Display the reminder.
int reminder::display(void) const {
    return 1;
}



// Mark the reminder as complete.
int reminder::mark_complete(void) {
    return 1;
}



appointment::appointment(void) {

}



appointment::appointment(const char ** new_participants) {

}



appointment::appointment(const appointment & other_appointment) {

}



appointment::~appointment(void) {

}



// Display the appointment.
int appointment::display(void) const {
    return 1;
}



// Add a participant to the appointment.
int appointment::add_participant(char * participant) {
    return 1;
}



// Remove a participant from the appointment.
int appointment::remove_participant(char * participant) {
    return 1;
}



class_session::class_session(void) {

}



class_session::class_session(const char * new_instructor,
        const char ** new_homework) {

}



class_session::class_session(const class_session & other_session) {

}



class_session::~class_session(void) {

}



// Display the session.
int class_session::display(void) const {
    return 1;
}



// Add an assignment.
int class_session::add_homework(const char * assignment) {
    return 1;
}



// Remove an assignment.
int class_session::remove_homework(const char * assignment) {
    return 1;
}



task::task(void) {

}



task::task(const int priority) {

}



task::task(const task & other_task) {

}



task::~task(void) {

}



// Display the task.
int task::display(void) const {
    return 1;
}



// Change the priority.
int task::change_priority(const int new_priority) {
    return 1;
}



// Add a subtask to this task.
int task::add_subtask(const task & subtask) {
    return 1;
}



// Remove a subtask from this task.
int task::remove_subtask(const task & subtask) {
    return 1;
}
