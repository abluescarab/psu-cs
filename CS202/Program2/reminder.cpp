/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * reminder.cpp
 *
 * This is the implementation file for the reminder class. The reminder class 
 * also has the subclasses appointment, class session, and task. The reminder 
 * class and its subclasses manage various types of daily todos.
 */
#include <iostream>
#include "reminder.h"
#include "utils.h"

using namespace std;



note::note(void) : text(nullptr), next(nullptr) {}



note::note(const note & other_note) : text(nullptr), next(other_note.next) {
    copy_char_array(text, other_note.text);
}



note::~note(void) {
    delete [] text;
    text = nullptr;
    next = nullptr;
}



// Get the next note.
note * & note::get_next(void) {
    return next;
}



// Set the next node.
int note::set_next(const note & new_next) {
    if(new_next.is_empty())
        return 0;

    next = new note(new_next);
    return 1;
}



// Display the contents of the note.
int note::display(void) const {
    cout << text << endl;
    return 1;
}



// Check if note is empty.
int note::is_empty(void) const {
    return char_array_empty(text);
}



// Checks if the note is a match.
int note::matches(const note & other_note) {
    return strcmp(text, other_note.text) == 0;
}



reminder::reminder(void) : 
    complete(false),
    time(nullptr),
    notes(nullptr),
    next(nullptr) {
    }



reminder::reminder(const char * new_time, const note & new_notes) :reminder() {
    copy_char_array(time, new_time);
    copy_notes(notes, new_notes);
}



reminder::reminder(const reminder & other_reminder) : 
    reminder(), 
    next(other_reminder.next) {
        copy_char_array(time, other_reminder.time);
        copy_notes(notes, other_reminder.notes);
    }



reminder::~reminder(void) {
    complete = false;
    delete [] time;
    next = nullptr;
    clear_notes(notes);
}



// Get the next reminder in the LLL.
reminder * & reminder::get_next(void) {
    return next;
}



// Set the next reminder in the LLL.
int reminder::set_next(const reminder & new_reminder) {
    if(new_reminder.is_empty())
        return 0;

    next = new reminder(new_reminder);
    return 1;
}



// Display the reminder.
int reminder::display(void) const {
    return 1;
}



// Mark the reminder as complete.
int reminder::mark_complete(void) {
    complete = true;
    return 1;
}



// Check if the reminder is missing required data.
int reminder::is_empty(void) const {
    return char_array_empty(text);
}



// Check if the reminder matches another reminder.
int reminder::matches(const reminder & other_reminder) {
    return 1;
}



// Add a note.
int add_note(const note & to_add) {
    if(to_add.is_empty())
        return 0;

    return add_note(notes, to_add);
}



// Add a note recursively.
int reminder::add_note(note * & current, const note & to_add) {
    if(!current) {
        current = new note(to_add);
        return 1;
    }

    return add_note(current->get_next(), to_add);
}



// Remove a note.
int reminder::remove_note(const note & to_remove) {
    if(to_remove.is_empty())
        return 0;

    return remove_note(notes, to_remove);
}



// Copy notes from another object recursively.
int reminder::copy_notes(note * & current, const note & other_note) {
    if(other_note.is_empty())
        return 0;

    current = new note(other_note);
    return copy_notes(current->get_next(), *other_note.get_next()) + 1;
}



// Clear notes recursively.
int reminder::clear_notes(note * & current) {
    if(!current)
        return 0;

    note * temp = current->get_next();
    delete current;
    return clear_notes(temp) + 1;
}



// Remove a note recursively.
int reminder::remove_note(note * & current, const note & to_remove) {
    if(!current)
        return 0;

    note * temp = current->get_next();

    if(current.matches(to_remove)) {
        delete current;
        current = temp;
        return 1;
    }

    return remove_participant(temp, to_remove);
}



appointment::appointment(void) : location(nullptr) {}



appointment::appointment(const char * new_time, 
        const char * new_location, 
        const note & new_notes) :
    reminder(new_time, new_notes), 
    location(nullptr) {
        copy_char_array(location, new_location);
    }



appointment::appointment(const appointment & other_appointment) : 
    appointment(other_appointment.time, 
            other_appointment.location, 
            other_appointment.notes),
    next(other_appointment.next),
    complete(other_appointment.complete) {}



    appointment::~appointment(void) {
        delete [] location;
        location = nullptr;
    }



// Display the appointment.
int appointment::display(void) const {
    return 1;
}



class_session::class_session(void) : instructor(nullptr), location(nullptr) {}



class_session::class_session(const char * new_time, 
        const char * new_location, 
        const char * new_instructor, 
        const note & new_notes) :
    reminder(new_time, new_notes),
    instructor(nullptr),
    location(nullptr) {
        copy_char_array(instructor, new_instructor);
        copy_char_array(location, new_location);
    }



class_session::class_session(const class_session & other_session) :
    class_session(other_session.time, 
            other_session.location,
            other_session.instructor,
            other_session.notes) {
    }



class_session::~class_session(void) {
    delete [] instructor;
    instructor = nullptr;
    delete [] location;
    location = nullptr;
}



// Display the session.
int class_session::display(void) const {
    return 1;
}



task::task(void) : priority(0), subtasks(nullptr) {}



task::task(const char * new_time, const int new_priority, const note & new_notes) :
    reminder(new_time, new_notes),
    priority(new_priority),
    subtasks(nullptr) {
}



task::task(const task & other_task) : 
    task(other_task.time, other_task.priority, other_task.notes) {
        copy_subtasks(subtasks, other_task.subtasks);
    }



task::~task(void) {
    priority = 0;
    clear_subtasks(subtasks);
}



// Display the task.
int task::display(void) const {
    return 1;
}



// Change the priority.
int task::change_priority(const int new_priority) {
    priority = new_priority;
    return 1;
}



// Get the next task.
task * & task::get_next(void) {
    return dynamic_cast<task*>(next);
}



// Clear subtasks recursively.
int task::clear_subtasks(task * & current) {
    if(!current)
        return 0;

    task * temp = dynamic_cast<task *>(current->get_next());
    delete current;
    return clear_subtasks(temp) + 1;
}



// Copy subtasks from another task.
int task::copy_subtasks(task * & current, task & other_subtask) {
    if(other_subtask.is_empty())
        return 0;

    current = new task(other_subtask);
    return copy_subtasks(current->get_next(), *other_subtask.get_next()) + 1;
}
