/* Alana Gilston - 2/21/2021 - CS202 - Program 3
 * message_list.h
 *
 * This is the header file for the message_list class. The message_list manages 
 * a list of messages that a device receives.
 */
#ifndef MESSAGE_LIST_H
#define MESSAGE_LIST_H
#include "cpp_string.h"

class message_list {
    public:
        message_list(void);
        message_list(const size_t new_max_messages);
        message_list(const message_list & other_list);
        ~message_list(void);

        // subscript
        const cpp_string & operator[](size_t index) const;
        cpp_string & operator[](size_t index);
        // assignment operators
        message_list & operator=(const message_list & source);
        // addition
        friend message_list operator+(message_list & destination, const message_list & source);
        friend message_list operator+(message_list & destination, const cpp_string & source);
        friend message_list operator+(message_list & destination, const char * source);
        // compound additions
        message_list & operator+=(const message_list & source);
        message_list & operator+=(const cpp_string & source);
        message_list & operator+=(const char * source);

        // Send a new message.
        int send(const cpp_string & to_send);
        // Clear all messages.
        int clear(void);
        // Display all messages.
        int display(void) const;
        // Get the number of messages allowed.
        size_t count(void) const;

    private:
        // Copy messages.
        int copy_messages(int first_index, const message_list & other_list);

        size_t max_messages; // maximum number of messages the list can hold
        cpp_string * sent_messages; // array of sent messages
};

#endif
