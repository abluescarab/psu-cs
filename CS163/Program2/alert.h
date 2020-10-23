/* Alana Gilston - 10/15/2020 - CS163 - Program 2
 * alert.h
 *
 * This is the header file for the "alert" class. The "alert" class is one
 * that manages data members for a user-defined alert.
 */
#ifndef ALERT_H
#define ALERT_H

class alert {
    public:
        alert(void);
        ~alert(void);
        // Copy the alert data from user-defined input.
        int copy_from(const char * new_date, const char * new_time, const char * new_message, const char * new_agency, const char * new_origin);
        // Copy the alert data from another alert instance.
        int copy_from(const alert & alert_to_copy);
        // Display the alert data to the user.
        int display(void) const;
        // Check if the alert has no data.
        int is_empty(void) const;
        // Check if the alert is a match to the provided message.
        int is_match(const char * compare_date, const char * compare_time) const;

    private:
        char * date; // date the alert was broadcast
        char * time; // time the alert was broadcast
        char * message; // text of the alert
        char * agency; // agency that generated the alert
        char * origin; // the news agency or source of the message
};

#endif
