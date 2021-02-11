/* Alana Gilston - 2/1/2021 - CS202 - Program 2
 * reminder.h
 *
 * This is the header file for the reminder class. The reminder class also has 
 * the subclasses appointment, class session, and task. The reminder class and 
 * its subclasses manage various types of daily todos.
 */
#ifndef REMINDER_H
#define REMINDER_H

class reminder {
    public:
        reminder(void);
        reminder(const char * new_time, const char * new_text);
        reminder(const reminder & other_reminder);
        virtual ~reminder(void);

        // Get the next reminder in the LLL.
        reminder * & get_next(void);
        // Display the reminder.
        virtual int display(void);
        // Mark the reminder as complete.
        int mark_complete(void);
        // Check if the reminder is missing required data.
        int is_empty(void) const;
        // Check if the reminder matches another reminder.
        virtual int matches(reminder & other_reminder);

    private:
        bool complete; // whether the reminder is completed
        char * time; // due time
        char * text; // text of the reminder
        reminder * next; // next reminder in the LLL
};



class appointment : public reminder {
     public:
        appointment(void);
        appointment(const char * new_time, const char * new_text, const char * new_location);
        appointment(const appointment & other_appointment);
        ~appointment(void);

        // Display the appointment.
        int display(void);
        // Check if the reminder matches another reminder.
        int matches(reminder & other_reminder);

    private:
        char * location; // location of the appointment
};



class class_session : public reminder {
    public:
        class_session(void);
        class_session(const char * new_time, const char * new_text, const char * new_location, const char * new_instructor);
        class_session(const class_session & other_session);
        ~class_session(void);

        // Display the session.
        int display(void);
        // Check if the reminder matches another reminder.
        int matches(reminder & other_reminder);

    private:
        char * instructor; // instructor for the class
        char * location; // building/room of the class
};



class task : public reminder {
    public:
        task(void);
        task(const char * new_time, const char * new_text, const int new_priority);
        task(const task & other_task);
        ~task(void);

        // Display the task.
        int display(void);
        // Change the priority.
        int change_priority(const int new_priority);
        // Check if the reminder matches another reminder.
        int matches(reminder & other_reminder);

    private:
        // Display with subtasks recursively.
        int display(task * & current);
        // Clear subtasks recursively.
        int clear_subtasks(task * & current);
        // Copy subtasks from another task.
        int copy_subtasks(task * & current, task & other_subtask); 

        int priority; // priority of the task
        task * subtasks; // subtasks for this task
};

#endif
