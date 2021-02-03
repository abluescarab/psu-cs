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
        note(const note & other_note);
        ~note(void);

        // Get the next note.
        note * & get_next(void);
        // Display the contents of the note.
        int display(void) const;

    private:
        char * text; // text of the note
        note * next; // next note
};



class reminder {
    public:
        reminder(void);
        reminder(const reminder & other_reminder);
        virtual ~reminder(void);

        // Get the next reminder in the LLL.
        reminder * & get_next(void);
        // Display the reminder.
        virtual int display(void) const;
        // Mark the reminder as complete.
        int mark_complete(void);

    private:
        char * text; // text of the reminder
        char * time; // due time
        reminder * next; // next reminder in the LLL
};



class appointment : public reminder {
    public:
        appointment(void);
        appointment(const char ** new_participants);
        appointment(const appointment & other_appointment);
        ~appointment(void);

        // Display the appointment.
        int display(void) const;
        // Add a participant to the appointment.
        int add_participant(char * participant);
        // Remove a participant from the appointment.
        int remove_participant(char * participant);

    private:
        note * participants; // meeting participants
};



class class_session : public reminder {
    public:
        class_session(void);
        class_session(const char * new_instructor,
                const char ** new_homework);
        class_session(const class_session & other_session);
        ~class_session(void);

        // Display the session.
        int display(void) const;
        // Add an assignment.
        int add_homework(const char * assignment);
        // Remove an assignment.
        int remove_homework(const char * assignment);

    private:
        char * instructor; // instructor for the class
        note * assignments; // assignments due for this session
};



class task : public reminder {
    public:
        task(void);
        task(const int priority);
        task(const task & other_task);
        ~task(void);

        // Display the task.
        int display(void) const;
        // Change the priority.
        int change_priority(const int new_priority);
        // Add a subtask to this task.
        int add_subtask(const task & subtask);
        // Remove a subtask from this task.
        int remove_subtask(const task & subtask);

    private:
        int priority; // priority of the task
        task * subtasks; // subtasks for this task
};

#endif
