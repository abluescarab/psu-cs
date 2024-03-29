/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * device.h
 *
 * This is the header file for the device class. The device class is one that 
 * manages a contact's device and its corresponding data.
 */
#ifndef DEVICE_H
#define DEVICE_H
#include "cpp_string.h"

enum class device_type {
    none,
    pager,
    cell_phone,
    computer
};

enum class os_type {
    iphone,
    android,
    windows_phone,
    other_os,
    feature_phone
};

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

class program {
    public:
        program(void);
        program(const cpp_string & new_program_name, 
                const cpp_string & new_username);
        program(const program & other_program);
        ~program(void);
        
        // operators
        program & operator=(const program & source);
        bool operator==(const program & compare) const;
        friend std::istream & operator>>(std::istream & in, program & in_program);
        friend std::ostream & operator<<(std::ostream & out, const program & out_program);

        // Set the next program.
        int set_next(const program & new_program);
        // Get the next program.
        program * & get_next(void);
        // Get the program name.
        const cpp_string get_name(void) const;
        // Check if the program is empty.
        int is_empty(void) const;
        // Check if the program name matches another program.
        int program_matches(const program & other_program);
        // Check if the program name matches another name.
        int program_matches(const cpp_string & other_name);
        // Display the program.
        int display(void) const;

    private:
        cpp_string program_name; // name of the program
        cpp_string username; // username in the program
        program * next; // next program
};

class device {
    public:
        device(void);
        device(const cpp_string & new_name);
        device(const device & other_device);
        virtual ~device(void);

        // operators
        device & operator=(const device & source);
        bool operator==(const device & compare) const;
        friend std::istream & operator>>(std::istream & in, device & in_device);
        friend std::ostream & operator<<(std::ostream & out, const device & out_device);

        // Set next device.
        int set_next(const device & new_device);
        // Get next device.
        device * & get_next(void);
        // Get the device's name.
        const cpp_string get_name(void) const;
        // Send a new message.
        int send_message(const cpp_string & to_send);
        // Display all sent messages.
        int display_messages(void) const;
        // Clear all messages.
        int clear_messages(void);
        // Check if the device is empty.
        int is_empty(void) const;
        // Check if name matches another.
        int name_matches(const cpp_string & compare) const;
        // Display the device.
        virtual int display(void) const;
        // Display the device name.
        int display_name(void) const;

    private:
        cpp_string name; // name of the device
        message_list * messages;
        device * next; // next device in the list
};

class pager : public device {
    public:
        pager(void);
        pager(const cpp_string & new_name,
                const cpp_string & new_number, 
                const bool new_supports_text,
                const bool new_two_way);
        pager(const pager & other_pager);
        ~pager(void);
        
        // operators
        pager & operator=(const pager & source);
        bool operator==(const pager & compare) const;

        // Change the pager number.
        int change_number(const cpp_string & new_number);
        // Change if the pager supports text messages.
        int change_supports_text(const bool new_supports_text);
        // Change if the pager is two-way.
        int change_two_way(const bool new_two_way);
        // Display the pager.
        int display(void) const;

    private:
        cpp_string pager_number; // number for the pager
        bool supports_text; // if the pager supports text messages
        bool two_way; // if the pager supports two-way communication
};

class cell_phone : public device {
    public:
        cell_phone(void);
        cell_phone(const cpp_string & new_name,
                const cpp_string & new_network, 
                const cpp_string & new_number,
                const os_type new_type);
        cell_phone(const cell_phone & other_phone);
        ~cell_phone(void);

        // operators
        cell_phone & operator=(const cell_phone & source);
        bool operator==(const cell_phone & compare) const;

        // Change the phone network.
        int change_network(const cpp_string & new_network);
        // Change the phone number.
        int change_number(const cpp_string & new_number);
        // Change the phone's OS.
        int change_type(const os_type new_type);
        // Display the phone.
        int display(void) const;

    private:
        cpp_string network; // phone network, e.g. Verizon, Sprint, etc.
        cpp_string phone_number; // phone number
        os_type phone_type; // phone os, e.g. iphone, android, etc.
};

class computer : public device {
    public:
        computer(void);
        computer(const cpp_string & new_name);
        computer(const computer & other_computer);
        ~computer(void);

        // Add a program to the computer.
        int add_program(const program & to_add);
        // Remove a program from the computer.
        int remove_program(const cpp_string & to_remove);
        // Clear all programs from the computer.
        int clear_programs(void);
        // Display the computer.
        int display(void);

    private:
        // Add a program recursively.
        int add_program(program * & current, const program & to_add);
        // Remove a program recursively.
        int remove_program(program * & current, const cpp_string & to_remove);
        // Clear all programs recursively.
        int clear_programs(program * & current);
        // Display programs recursively.
        int display(program * & current);
        // Copy programs from another computer.
        int copy_programs(program * & current, program & other_current);

        program * programs; // programs on the computer
};

#endif
