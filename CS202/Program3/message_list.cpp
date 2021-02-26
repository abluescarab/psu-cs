/* Alana Gilston - 2/21/2021 - CS202 - Program 3
 * message_list.cpp
 *
 *
 */
#include <iostream>
#include "message_list.h"

using namespace std;



message_list::message_list(void) : max_messages(10), sent_messages(new cpp_string[10]) { 
    clear();
}



message_list::message_list(const size_t new_max_messages) :
    sent_messages(nullptr) {
        if(new_max_messages <= 0)
            max_messages = 1;
        else
            max_messages = new_max_messages;

        sent_messages = new cpp_string[max_messages];
        clear();
}



message_list::message_list(const message_list & other_list) :
    max_messages(other_list.max_messages),
    sent_messages(new cpp_string[other_list.max_messages]) {
        if(other_list.sent_messages)
            copy_messages(other_list.sent_messages);
        else
            clear();
}



// invalid write error
// to reproduce:
// create new contact
// delete contact
message_list::~message_list(void) {
    max_messages = 0;

    if(sent_messages)
        delete [] sent_messages;

    sent_messages = nullptr;
}



// subscript
const cpp_string & message_list::operator[](size_t index) const {
    return sent_messages[index];
}



cpp_string & message_list::operator[](size_t index) {
    return sent_messages[index];
}



// assignment operators
message_list & message_list::operator=(const message_list & source) {
    if(this == &source)
        return *this;

    // todo
    return *this;
}



message_list & message_list::operator=(const cpp_string & source) {
    // todo
    return *this;
}



message_list & message_list::operator=(const char * source) {
    // todo
    return *this;
}



// addition
message_list operator+(message_list & destination, const message_list & source) {
    message_list result;
    // todo
    return result;
}



message_list operator+(message_list & destination, const cpp_string & source) {
    message_list result;
    // todo
    return result;
}



message_list operator+(message_list & destination, const char * source) {
    message_list result;
    // todo
    return result;
}



// compound additions
message_list & message_list::operator+=(const message_list & source) {
    // todo
    return *this;
}



message_list & message_list::operator+=(const cpp_string & source) {
    // todo
    return *this;
}



message_list & message_list::operator+=(const char * source) {
    // todo
    return *this;
}



// input/output
std::istream & operator>>(std::istream & in, message_list & in_list) {
    // todo
    return in;
}



std::ostream & operator<<(std::ostream & out, const message_list & out_list) {
    // todo
    return out;
}



// Send a new message.
int message_list::send(const cpp_string & to_send) {
    if(!sent_messages || to_send.is_empty())
        return 0;

    size_t index = 0;

    while(!sent_messages[index].is_empty())
        ++index;

    if(index >= max_messages)
        return 0;

    sent_messages[index] = to_send;
    return 1;
}



// Clear all sent_messages.
int message_list::clear(void) {
    if(!sent_messages)
        return 0;

    for(size_t i = 0; i < max_messages; ++i) {
        sent_messages[i] = "";
    }
    
    return 1;
}



// Display all sent_messages.
int message_list::display(void) const {
    if(!sent_messages)
        return 0;

    int filled = 0;

    for(size_t i = 0; i < max_messages; ++i) {
        if(!sent_messages[i].is_empty()) {
            cout << sent_messages[i] << endl;
            ++filled;
        }
    }

    return filled;
}



// Get the number of sent_messages allowed.
size_t message_list::count(void) const {
    return max_messages;
}



// Copy sent_messages.
int message_list::copy_messages(const cpp_string * other_messages) {
    for(size_t i = 0; i < max_messages; ++i)
        sent_messages[i] = other_messages[i];

    return 1;
}
