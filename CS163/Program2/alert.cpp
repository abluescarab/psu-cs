/* Alana Gilston - 10/15/2020 - CS163 - Program 2
 * alert.cpp
 *
 * This is the implementation file for the "alert" class. The "alert" class is 
 * one that manages data members for a user-defined alert.
 */
#include <iostream>
#include <cstring>
#include "alert.h"
#include "utils.h"

using namespace std;



alert::alert(void) {
    origin = nullptr;
    date = nullptr;
    time = nullptr;
    message = nullptr;
    agency = nullptr;
}



alert::~alert(void) {
    delete [] origin;
    delete [] date;
    delete [] time;
    delete [] message;
    delete [] agency;
}



// Copy the alert data from user-defined input.
// INPUT:
//  new_origin: The news agency or source of the message
//  new_date: Date the alert was broadcast
//  new_time: Time the alert was broadcast
//  new_message: Text of the alert
//  new_agency: The agency that generated the alert
// OUTPUT:
//  0 if required data is missing
//  1 if successfully copied
int alert::copy_from(const char * new_date, const char * new_time, const char * new_message, const char * new_agency, const char * new_origin) {
    if(char_array_empty(new_origin) ||
       char_array_empty(new_date) ||
       char_array_empty(new_time) ||
       char_array_empty(new_message) ||
       char_array_empty(new_agency))
        return 0;

    copy_char_array(origin, new_origin);
    copy_char_array(date, new_date);
    copy_char_array(time, new_time);
    copy_char_array(message, new_message);
    copy_char_array(agency, new_agency);

    return 1;
}



// Copy the alert data from another alert instance.
// INPUT:
//  alert_to_copy: An instance of an alert to copy from
// OUTPUT:
//  0 if the copy fails due to missing data
//  1 if the copy succeeds
int alert::copy_from(const alert & alert_to_copy) {
    if(alert_to_copy.is_empty()) return 0;

    copy_char_array(origin, alert_to_copy.origin);
    copy_char_array(date, alert_to_copy.date);
    copy_char_array(time, alert_to_copy.time);
    copy_char_array(message, alert_to_copy.message);
    copy_char_array(agency, alert_to_copy.agency); 

    return 1;
}



// Display the alert data to the user.
// OUTPUT:
//  0 if the alert is missing required data (does not exist)
//  1 if the alert displays correctly
int alert::display(void) const {
    if(is_empty()) return 0;

    cout << "ALERT: " << time << " " << date << " (Source: " << origin << ")" << endl;
    cout << agency << " has reported: " << message << endl;

    return 1;
}



// Check if the alert has no data.
// OUTPUT:
//  0 if the project has data
//  1 if the project is missing required data
int alert::is_empty(void) const {
    return char_array_empty(origin) ||
           char_array_empty(date) ||
           char_array_empty(time) ||
           char_array_empty(message) ||
           char_array_empty(agency);
}



// Check if the alert is a match to the provided message.
// INPUT:
//  date: Date the alert was broadcast
//  time: Time the alert was broadcast
// OUTPUT:
//  0 if the project does not match
//  1 if the project matches
int alert::is_match(const char * compare_date, const char * compare_time) const {
    return strcmp(compare_date, date) == 0 && strcmp(compare_time, time) == 0;
}
