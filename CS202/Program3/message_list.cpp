/* Alana Gilston - 2/21/2021 - CS202 - Program 3
 * message_list.cpp
 *
 * This is the implementation file for the message_list class. The message_list
 * manages a list of messages that a device receives.
 */
#include <iostream>
#include "message_list.h"

using namespace std;



// Create a new message list.
message_list::message_list(void) : max_messages(10), sent_messages(new cpp_string[10]) { 
    clear();
}



// Create a new message list.
// INPUT:
//  new_max_messages: the number of messages supported by the list
message_list::message_list(const size_t new_max_messages) :
    sent_messages(nullptr) {
        if(new_max_messages <= 0)
            max_messages = 1;
        else
            max_messages = new_max_messages;

        sent_messages = new cpp_string[max_messages];
        clear();
}



// Create a new message list.
// INPUT:
//  other_list: the other message list to copy
message_list::message_list(const message_list & other_list) :
    max_messages(other_list.max_messages),
    sent_messages(new cpp_string[other_list.max_messages]) {
        if(other_list.sent_messages)
            copy_messages(0, other_list);
        else
            clear();
}



// Delete the message list.
message_list::~message_list(void) {
    max_messages = 0;

    if(sent_messages)
        delete [] sent_messages;

    sent_messages = nullptr;
}



// Subscript operator.
// INPUT:
//  index: the index of the object to return
// OUTPUT:
//  Returns the message at the index.
const cpp_string & message_list::operator[](size_t index) const {
    return sent_messages[index];
}



// Subscript operator.
// INPUT:
//  index: the index of the object to return
// OUTPUT:
//  Returns the message at the index.
cpp_string & message_list::operator[](size_t index) {
    return sent_messages[index];
}



// Assignment operator.
// INPUT:
//  source: the message list to copy from
// OUTPUT:
//  Returns this list.
message_list & message_list::operator=(const message_list & source) {
    if(this == &source)
        return *this;

    delete [] sent_messages;
    max_messages = source.max_messages;
    sent_messages = new cpp_string[max_messages];
    copy_messages(0, source);
    return *this;
}



// Addition operator.
// INPUT:
//  destination: the list to add to
//  source: the list to add from
// OUTPUT:
//  Returns the result of the addition.
message_list operator+(message_list & destination, const message_list & source) {
    size_t total = destination.max_messages + source.max_messages;
    message_list result;

    result.max_messages = total;
    result.sent_messages = new cpp_string[total];

    for(size_t i = 0; i < destination.max_messages; ++i)
        result.sent_messages[i] = destination.sent_messages[i];

    for(size_t i = destination.max_messages; i < total; ++i)
        result.sent_messages[i] = source.sent_messages[i];

    return result;
}



// Addition operator.
// INPUT:
//  destination: the list to add to
//  source: the cpp_string to add
// OUTPUT:
//  Returns the result of the addition.
message_list operator+(message_list & destination, const cpp_string & source) {
    message_list result;
    result.max_messages = destination.max_messages + 1;

    for(size_t i = 0; i < destination.max_messages; ++i)
        result.sent_messages[i] = destination.sent_messages[i];

    result.sent_messages[result.max_messages - 1] = source;
    return result;
}



// Addition operator.
// INPUT:
//  destination: the list to add to
//  source: the char array to add
// OUTPUT:
//  Returns the result of the addition.
message_list operator+(message_list & destination, const char * source) {
    return operator+(destination, cpp_string(source));
}



// Compound addition.
// INPUT:
//  source: the list to add
// OUTPUT:
//  Returns the new message list.
message_list & message_list::operator+=(const message_list & source) {
    message_list * temp = new message_list(*this);
    int old_max = max_messages;

    delete [] sent_messages;
    max_messages += source.max_messages;
    copy_messages(0, *temp);
    copy_messages(old_max, source);
    return *this;
}



// Compound addition.
// INPUT:
//  source: the string to add
// OUTPUT:
//  Returns the new message list.
message_list & message_list::operator+=(const cpp_string & source) {
    message_list * temp = new message_list(*this);
    delete [] sent_messages;
    max_messages += 1;
    copy_messages(0, *temp);
    sent_messages[max_messages - 1] = source;
    return *this;
}



// Compound addition.
// INPUT:
//  source: the char array to add
// OUTPUT:
//  Returns the new message list.
message_list & message_list::operator+=(const char * source) {
    return operator+=(cpp_string(source));
}



// Send a new message.
// INPUT:
//  to_send: the message to send
// OUTPUT:
//  0 if the message is empty or the messages do not exist.
//  1 if the message is sent.
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
// OUTPUT:
//  0 if the sent messages do not exist.
//  1 if the messages are cleared.
int message_list::clear(void) {
    if(!sent_messages)
        return 0;

    for(size_t i = 0; i < max_messages; ++i) {
        sent_messages[i] = "";
    }
    
    return 1;
}



// Display all sent_messages.
// OUTPUT:
//  Returns the number of messages displayed.
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



// Get the number of sent messages allowed.
// OUTPUT:
//  Returns the max messages allowed.
size_t message_list::count(void) const {
    return max_messages;
}



// Copy sent messages.
// INPUT:
//  other_list: the other message list to copy
// OUTPUT:
//  1 when the messages are copied.
int message_list::copy_messages(int first_index, const message_list & other_list) {
    for(size_t i = first_index; i < (first_index + other_list.max_messages); ++i)
        sent_messages[i] = other_list.sent_messages[i];

    return 1;
}
