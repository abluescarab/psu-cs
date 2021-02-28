/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * device.cpp
 *
 * This is the implementation file for the device class. The device class is 
 * one that manages a contact's device and its corresponding data.
 */
#include <iostream>
#include <iomanip>
#include "device.h"
#define MAX_INPUT 1024

using namespace std;



// Create a new program.
program::program(void) : program_name(""), username(""), next(nullptr) {}



// Create a new program.
// INPUT:
//  new_program_name: the program name
//  new_username: the program username
program::program(const cpp_string & new_program_name, 
        const cpp_string & new_username) :
    program_name(new_program_name),
    username(new_username),
    next(nullptr) {
    }



// Create a new program.
// INPUT:
//  other_program: the program to copy from
program::program(const program & other_program) :
    program_name(other_program.program_name),
    username(other_program.username),
    next(other_program.next) {
    }



// Delete the program.
program::~program(void) {
    program_name = "";
    username = "";
    next = nullptr;
}



// Assignment operator.
// INPUT:
//  source: the program to copy
// OUTPUT:
//  Returns the current program.
program & program::operator=(const program & source) {
    if(this == &source)
        return *this;

    program_name = source.program_name;
    username = source.username;
    next = source.next;
    return *this;
}



// Equality operator.
// INPUT: 
//  compare: the program to compare to
// OUTPUT:
//  Returns if the program names and usernames match.
bool program::operator==(const program & compare) const {
    return program_name == compare.program_name &&
        username == compare.username;
}



// Input operator.
// INPUT:
//  in: the istream object
//  in_program: the program to input
// OUTPUT:
//  Returns the istream object.
std::istream & operator>>(std::istream & in, program & in_program) {
    char temp[MAX_INPUT];

    in >> temp;
    in_program.program_name = temp;
    return in;
}



// Output operator.
// INPUT:
//  out: the ostream object
//  out_program: the program to output
// OUTPUT:
//  Returns the ostream object.
std::ostream & operator<<(std::ostream & out, const program & out_program) {
    out << out_program.program_name;
    return out;
}



// Set the next program.
// INPUT:
//  new_program: the program to set in the next node
// OUTPUT:
//  1 when the program is set.
int program::set_next(const program & new_program) {
    next = new program(new_program);
    return 1;
}



// Get the next program.
// OUTPUT:
//  Returns the next program.
program * & program::get_next(void) {
    return next;
}



// Get the program name.
// OUTPUT:
//  Returns the program name.
const cpp_string program::get_name(void) const {
    return program_name;
}



// Check if the program is empty.
// OUTPUT:
//  Returns if the program name is empty.
int program::is_empty(void) const {
    return program_name.is_empty();
}



// Check if the program name matches another program.
// INPUT:
//  other_program: the other program to compare to
// OUTPUT:
//  Returns if the program names match.
int program::program_matches(const program & other_program) {
    return program_name == other_program.program_name;
}



// Check if the program name matches another name.
// INPUT:
//  other_name: name to compare to
// OUTPUT:
//  Returns if the names match.
int program::program_matches(const cpp_string & other_name) {
    return program_name == other_name;
}



// Display the program.
// OUTPUT:
//  1 when the program is displayed.
int program::display(void) const {
    cout << "Program: " << program_name << endl;
    cout << "Username: " << username << endl;
    return 1;
}



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



// Create a new device.
device::device(void) : 
    name(""), 
    messages(new message_list(10)),
    next(nullptr) {
    }



// Create a new device.
// INPUT:
//  new_name: the device name
device::device(const cpp_string & new_name) : 
    name(new_name),
    messages(new message_list(10)),
    next(nullptr) {
    }



// Create a new device.
// INPUT:
//  other_device: the device to copy
device::device(const device & other_device) :
    name(other_device.name),
    messages(new message_list(*other_device.messages)),
    next(other_device.next) {
    }



// Delete the device.
device::~device(void) {
    name = "";
    delete messages;
    messages = nullptr;
    next = nullptr;
}



// Assignment operator.
// INPUT:
//  source: the device to copy
// OUTPUT:
//  Returns the current device.
device & device::operator=(const device & source) {
    if(this == &source)
        return *this;

    name = source.name;
    next = source.next;
    return *this;
}



// Equality operator.
// INPUT:
//  compare: the device to compare to
// OUTPUT:
//  Returns if the device names match.
bool device::operator==(const device & compare) const {
    return name == compare.name;
}



// Input operator.
// INPUT:
//  in: the istream object
//  in_device: the device to input
// OUTPUT:
//  Returns the istream object.
std::istream & operator>>(std::istream & in, device & in_device) {
    char temp[MAX_INPUT];

    in >> temp;
    in_device.name = temp;
    return in;
}



// Output operator.
// INPUT:
//  out: the ostream object
//  out_device: the device to output
// OUTPUT:
//  Returns the ostream object.
std::ostream & operator<<(std::ostream & out, const device & out_device) {
    out << out_device.name;
    return out;
}



// Set next device.
// INPUT:
//  new_device: the device to add
// OUTPUT:
//  1 when the device is added.
int device::set_next(const device & new_device) {
    next = new device(new_device);
    return 1;
}



// Get next device.
// OUTPUT:
//  Returns the next device.
device * & device::get_next(void) {
    return next;
}



// Get the device's name.
// OUTPUT:
//  Returns the device's name.
const cpp_string device::get_name(void) const {
    return name;
}



// Send a new message.
// INPUT:
//  to_send: the message to send
// OUTPUT:
//  Returns the result of the send function.
int device::send_message(const cpp_string & to_send) {
    return messages->send(to_send);
}



// Display all sent messages.
// OUTPUT:
//  Returns the result of the display function.
int device::display_messages(void) const {
    return messages->display();
}



// Clear all messages.
// OUTPUT:
//  Returns the result of the clear function.
int device::clear_messages(void) {
    return messages->clear();
}



// Check if the device is empty.
// OUTPUT:
//  Returns if the name is empty.
int device::is_empty(void) const {
    return name.is_empty();
}



// Check if name matches another.
// INPUT:
//  compare: name to compare to
// OUTPUT:
//  Returns whether the names match.
int device::name_matches(const cpp_string & compare) const {
    return name == compare;
}



// Display the device.
// OUTPUT:
//  1 when the device is displayed.
int device::display(void) const {
    cout << "Device: " << name << endl;
    return 1;
}



// Display the device name.
// OUTPUT:
//  1 when the device name is displayed.
int device::display_name(void) const {
    cout << name << endl;
    return 1;
}



// Create a new pager.
pager::pager(void) : pager_number(""), supports_text(false), two_way(false) {}



// Create a new pager.
// INPUT:
//  new_name: the name of the pager
//  new_number: the pager number
//  new_supports_text: whether the pager supports text messages
//  new_two_way: whether the pager supports two-way messaging
pager::pager(const cpp_string & new_name, 
        const cpp_string & new_number, 
        const bool new_supports_text, 
        const bool new_two_way) :
    device(new_name), 
    pager_number(new_number), 
    supports_text(new_supports_text), 
    two_way(new_two_way) {
    }



// Create a new pager.
// INPUT:
//  other_pager: pager to copy
pager::pager(const pager & other_pager) :
    device(other_pager),
    pager_number(other_pager.pager_number),
    supports_text(other_pager.supports_text),
    two_way(other_pager.two_way) {
    }



// Delete the pager.
pager::~pager(void) {
    pager_number = "";
    supports_text = false;
    two_way = false;
}



// Assignment operator.
// INPUT:
//  source: the pager to copy
// OUTPUT:
//  Returns the current pager.
pager & pager::operator=(const pager & source) {
    if(this == &source)
        return *this;

    device::operator=(source);
    pager_number = source.pager_number;
    supports_text = source.supports_text;
    two_way = source.two_way;
    return *this;
}



// Equality operator.
// INPUT:
//  compare: the pager to compare to
// OUTPUT:
//  Returns if the pagers match.
bool pager::operator==(const pager & compare) const {
    return device::operator==(compare) && 
        pager_number == compare.pager_number && 
        supports_text == compare.supports_text && 
        two_way == compare.two_way;
}



// Change the pager number.
// INPUT:
//  new_number: the number to change to
// OUTPUT:
//  1 when the number is changed.
int pager::change_number(const cpp_string & new_number) {
    pager_number = new_number;
    return 1;
}



// Change if the pager supports text messages.
// INPUT:
//  new_supports_text: whether the pager supports text messages
// OUTPUT:
//  1 when the value is changed.
int pager::change_supports_text(const bool new_supports_text) {
    supports_text = new_supports_text;
    return 1;
}



// Change if the pager is two-way.
// INPUT:
//  new_two_way: whether the pager supports two-way messaging
// OUTPUT:
//  1 when the value is changed.
int pager::change_two_way(const bool new_two_way) {
    two_way = new_two_way;
    return 1;
}



// Display the pager.
// OUTPUT:
//  1 when the pager is displayed.
int pager::display(void) const {
    device::display();

    cout << "Number: " << pager_number << endl;
    cout << "Supports text: ";

    if(supports_text)
        cout << "Yes";
    else
        cout << "No";
   
    cout << endl;
    cout << "Two way: ";

    if(two_way)
        cout << "Yes";
    else
        cout << "No";

    cout << endl;
    return 1;
}



// Create a new cell phone.
cell_phone::cell_phone(void) : 
    network(""), 
    phone_number(""), 
    phone_type(os_type::feature_phone) {
    }



// Create a new cell phone.
// INPUT:
//  new_name: the cell phone name
//  new_network: the cell phone network
//  new_number: the cell phone number
//  new_type: the cell phone OS
cell_phone::cell_phone(const cpp_string & new_name,
        const cpp_string & new_network, 
        const cpp_string & new_number,
        const os_type new_type) :
    device(new_name),
    network(new_network),
    phone_number(new_number),
    phone_type(new_type) {
    }



// Create a new cell phone.
// INPUT:
//  other_phone: the phone to copy from
cell_phone::cell_phone(const cell_phone & other_phone) :
    device(other_phone),
    network(other_phone.network),
    phone_number(other_phone.phone_number),
    phone_type(other_phone.phone_type) {
    }



// Delete the cell phone.
cell_phone::~cell_phone(void) {
    network = "";
    phone_number = "";
    phone_type = os_type::feature_phone;
}



// Assignment operator.
// INPUT:
//  source: the cell phone to copy
// OUTPUT:
//  Returns the current cell phone.
cell_phone & cell_phone::operator=(const cell_phone & source) {
    if(this == &source)
        return *this;

    device::operator=(source);
    network = source.network;
    phone_number = source.phone_number;
    phone_type = source.phone_type;
    return *this;
}



// Equality operator.
// INPUT:
//  compare: the cell phone to compare to
// OUTPUT:
//  Whether the cell phones match.
bool cell_phone::operator==(const cell_phone & compare) const {
    return device::operator==(compare) &&
        network == compare.network &&
        phone_number == compare.phone_number &&
        phone_type == compare.phone_type;
}



// Change the phone network.
// INPUT:
//  new_network: the cell phone network
// OUTPUT:
//  1 when the network is changed.
int cell_phone::change_network(const cpp_string & new_network) {
    network = new_network;
    return 1;
}



// Change the phone number.
// INPUT:
//  new_number: the cell phone number
// OUTPUT:
//  1 when the number is changed.
int cell_phone::change_number(const cpp_string & new_number) {
    phone_number = new_number;
    return 1;
}



// Change the phone's OS.
// INPUT:
//  new_type: the phone OS
// OUTPUT:
//  1 when the OS is changed.
int cell_phone::change_type(const os_type new_type) {
    phone_type = new_type;
    return 1;
}



// Display the phone.
// OUTPUT:
//  1 when the cell phone is displayed.
int cell_phone::display(void) const {
    device::display();

    cout << "Network: " << network << endl;
    cout << "Number: " << phone_number << endl;
    cout << "Type: ";

    switch(phone_type) {
        case os_type::iphone:
            cout << "iPhone";
            break;
        case os_type::android:
            cout << "Android";
            break;
        case os_type::windows_phone:
            cout << "Windows Phone";
            break;
        case os_type::other_os:
            cout << "Other OS";
            break;
        case os_type::feature_phone:
            cout << "Feature phone";
            break;
        default:
            cout << "N/A";
            break;
    }

    cout << endl;
    return 1;
}



// Create a new computer.
computer::computer(void) : programs(nullptr) {}



// Create a new computer.
// INPUT:
//  new_name: computer name
computer::computer(const cpp_string & new_name) : device(new_name), programs(nullptr) {}



// Create a new computer.
// INPUT:
//  other_computer: the computer to copy
computer::computer(const computer & other_computer) :
    device(other_computer),
    programs(nullptr) {
        if(other_computer.programs)
            copy_programs(programs, *other_computer.programs);
    }



// Delete the computer.
computer::~computer(void) {
    clear_programs();
}



// Add a program to the computer.
// INPUT:
//  to_add: the program to add
// OUTPUT:
//  0 if the program to add is empty.
//  Otherwise returns the result of the recursive function.
int computer::add_program(const program & to_add) {
    if(to_add.is_empty())
        return 0;

    return add_program(programs, to_add);
}



// Add a program recursively.
// INPUT:
//  current: the current program
//  to_add: the program to add
// OUTPUT:
//  1 when the program is added.
int computer::add_program(program * & current, const program & to_add) {
    if(!current) {
        current = new program(to_add);
        return 1;
    }

    return add_program(current->get_next(), to_add);
}



// Remove a program from the computer.
// INPUT:
//  to_remove: the program to remove
// OUTPUT:
//  0 if the program to remove does not exist.
//  Otherwise returns the result of the recursive function.
int computer::remove_program(const cpp_string & to_remove) {
    if(to_remove.is_empty())
        return 0;

    return remove_program(to_remove);
}



// Remove a program recursively.
// INPUT:
//  current: the current program
//  to_remove: the program to remove
// OUTPUT:
//  0 if the program to remove does not exist.
//  1 if the program is deleted.
int computer::remove_program(program * & current, const cpp_string & to_remove) {
    if(!current)
        return 0;

    program * temp = current->get_next();

    if(current->program_matches(to_remove)) {
        delete current;
        current = temp;
        return 1;
    }

    return remove_program(temp, to_remove);
}



// Clear all programs from the computer.
// OUTPUT:
//  0 if there are no programs.
//  Otherwise returns the result of the recursive function.
int computer::clear_programs(void) {
    if(!programs)
        return 0;

    return clear_programs(programs);
}



// Clear all programs recursively.
// INPUT:
//  current: the current program
// OUTPUT:
//  Returns the number of programs removed.
int computer::clear_programs(program * & current) {
    if(!current)
        return 0;

    program * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear_programs(temp) + 1;
}



// Display the computer.
// OUTPUT:
//  0 if the computer is empty.
//  Otherwise returns the result of the recursive function.
int computer::display(void) {
    if(is_empty())
        return 0;

    device::display();
    return display(programs);
}



// Display programs recursively.
// INPUT:
//  current: the current program
// OUTPUT:
//  Returns the number of programs displayed.
int computer::display(program * & current) {
    if(!current)
        return 0;

    current->display();
    return display(current->get_next()) + 1;
}



// Copy programs from another computer.
// INPUT:
//  current: the current program
//  other_current: the other current program
// OUTPUT:
//  Returns the number of programs copied.
int computer::copy_programs(program * & current, program & other_current) {
    current = new program(other_current);

    if(!other_current.get_next())
        return 1;

    return copy_programs(current->get_next(), *other_current.get_next()) +  1;
}
