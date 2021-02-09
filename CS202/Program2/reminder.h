/* Alana Gilston - 2/1/2021 - CS202 - Program 2
 * reminder.h
 *
 * This is the header file for the reminder class. The reminder class also has 
 * the subclasses appointment, class session, and task. The reminder class and 
 * its subclasses manage various types of daily todos.
 */
#ifndef REMINDER_H
#define REMINDER_H

class note {
    public:
        note(void);
        note(note & other_note);
        ~note(void);

        // Get the next note.
        note * & get_next(void);
        // Set the next node.
        int set_next(note & new_next);
        // Display the contents of the note.
        int display(void) const;
        // Check if note is empty.
        int is_empty(void) const;
        // Checks if the note is a match.
        int matches(note & other_note);

    private:
        char * text; // text of the note
        note * next; // next note
};



class reminder {
    public:
        reminder(void);
        reminder(const char * new_time, note & new_notes);
        reminder(const reminder & other_reminder);
        virtual ~reminder(void);

        // Get the next reminder in the LLL.
        reminder * & get_next(void);
        // Set the next reminder in the LLL.
        int set_next(const reminder & new_reminder);
        // Display the reminder.
        virtual int display(void) const;
        // Mark the reminder as complete.
        int mark_complete(void);
        // Check if the reminder is missing required data.
        int is_empty(void) const;
        // Check if the reminder matches another reminder.
        virtual int matches(reminder & other_reminder);
        // Add a note.
        int add_note(note & to_add);
        // Remove a note.
        int remove_note(note & to_remove);

    private:
        // Add a note recursively.
        int add_note(note * & current, note & to_add);
        // Remove a note recursively.
        int remove_note(note * & current, note & to_remove);
        // Copy notes from another object recursively.
        int copy_notes(note * & current, note & other_note);
        // Clear notes recursively.
        int clear_notes(note * & current);

        bool complete; // whether the reminder is completed
        char * time; // due time
        note * notes; // notes in the reminder
        reminder * next; // next reminder in the LLL
};



class appointment : public reminder {
     public:
        appointment(void);
        appointment(const char * new_time, const char * new_location, note & new_notes);
        appointment(const appointment & other_appointment);
        ~appointment(void);

        // Display the appointment.
        int display(void) const;
        // Check if the reminder matches another reminder.
        int matches(reminder & other_reminder);

    private:
        char * location; // location of the appointment
};



class class_session : public reminder {
    public:
        class_session(void);
        class_session(const char * new_time, const char * new_location, const char * new_instructor, note & new_notes);
        class_session(const class_session & other_session);
        ~class_session(void);

        // Display the session.
        int display(void) const;
        // Check if the reminder matches another reminder.
        int matches(reminder & other_reminder);

    private:
        char * instructor; // instructor for the class
        char * location; // building/room of the class
};



class task : public reminder {
    public:
        task(void);
        task(const char * new_time, const int new_priority, note & new_notes);
        task(const task & other_task);
        ~task(void);

        // Display the task.
        int display(void) const;
        // Change the priority.
        int change_priority(const int new_priority);
        // Check if the reminder matches another reminder.
        int matches(reminder * & other_reminder);

    private:
        // Clear subtasks recursively.
        int clear_subtasks(task * & current);
        // Copy subtasks from another task.
        int copy_subtasks(task * & current, task & other_subtask); 

        int priority; // priority of the task
        task * subtasks; // subtasks for this task
};

#endif
