/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * device.cpp
 *
 *
 */
#include <iostream>
#include <iomanip>
#include "device.h"
#define MAX_INPUT 1024

using namespace std;



program::program(void) : program_name(""), username(""), next(nullptr) {}



program::program(const cpp_string & new_program_name, 
        const cpp_string & new_username) :
    program_name(new_program_name),
    username(new_username),
    next(nullptr) {
    }



program::program(const program & other_program) :
    program_name(other_program.program_name),
    username(other_program.username),
    next(other_program.next) {
    }



program::~program(void) {
    program_name = "";
    username = "";
    next = nullptr;
}



program & program::operator=(const program & source) {
    if(this == &source)
        return *this;

    program_name = source.program_name;
    username = source.username;
    next = source.next;
    return *this;
}



bool program::operator==(const program & compare) const {
    return program_name == compare.program_name &&
        username == compare.username;
}

        

std::istream & operator>>(std::istream & in, program & in_program) {
    char temp[MAX_INPUT];

    in >> temp;
    in_program.program_name = temp;
    return in;
}


std::ostream & operator<<(std::ostream & out, const program & out_program) {
    out << out_program.program_name;
    return out;
}



// Set the next program.
int program::set_next(const program & new_program) {
    next = new program(new_program);
    return 1;
}



// Get the next program.
program * & program::get_next(void) {
    return next;
}



// Change the username.
int program::change_username(const cpp_string & new_username) {
    username = new_username;
    return 1;
}



// Check if the program is empty.
int program::is_empty(void) const {
    return program_name.is_empty();
}



// Check if the program name matches another program.
int program::name_matches(const program & other_program) {
    return program_name == other_program.program_name;
}



// Display the program.
int program::display(void) const {
    cout << "Program: " << program_name << endl;
    cout << "Username: " << username << endl;
    return 1;
}



device::device(void) : name(""), price(0.0), next(nullptr) {}



device::device(const cpp_string & new_name, const float new_price) :
    name(new_name),
    price(new_price),
    next(nullptr) {
    }



device::device(const device & other_device) :
    name(other_device.name),
    price(other_device.price),
    next(other_device.next) {
    }



device::~device(void) {
    name = "";
    price = 0.0;
    next = nullptr;
}

        

device & device::operator=(const device & source) {
    if(this == &source)
        return *this;

    name = source.name;
    price = source.price;
    next = source.next;
    return *this;
}
        


bool device::operator==(const device & compare) const {
    return name == compare.name && price == compare.price;
}
        


std::istream & operator>>(std::istream & in, device & in_device) {
    char temp[MAX_INPUT];

    in >> temp;
    in_device.name = temp;
    return in;
}
        


std::ostream & operator<<(std::ostream & out, const device & out_device) {
    out << out_device.name;
    return out;
}



// Set next device.
int device::set_next(const device & new_device) {
    next = new device(new_device);
    return 1;
}



// Get next device.
device * & device::get_next(void) {
    return next;
}



// Change the device's subscription price.
int device::change_price(const float new_price) {
    price = new_price;
    return 1;
}



// Check if the device is empty.
int device::is_empty(void) const {
    return name.is_empty();
}



// Check if the device matches another.
int device::matches(const device & other_device) const {
    return name == other_device.name &&
        price == other_device.price;
}



// Display the device.
int device::display(void) const {
    cout << name << endl;
    cout << "Price: " << fixed << setprecision(2) << price;
    return 1;
}



pager::pager(void) : pager_number(""), supports_text(false), two_way(false) {}



pager::pager(const cpp_string & new_number, const bool new_supports_text,
        const bool new_two_way) :
    pager_number(new_number),
    supports_text(new_supports_text),
    two_way(new_two_way) {
    }



pager::pager(const pager & other_pager) :
    device(other_pager),
    pager_number(other_pager.pager_number),
    supports_text(other_pager.supports_text),
    two_way(other_pager.two_way) {
    }



pager::~pager(void) {
    pager_number = "";
    supports_text = false;
    two_way = false;
}


        
pager & pager::operator=(const pager & source) {
    if(this == &source)
        return *this;

    device::operator=(source);
    pager_number = source.pager_number;
    supports_text = source.supports_text;
    two_way = source.two_way;
    return *this;
}
        


bool pager::operator==(const pager & compare) const {
    return device::operator==(compare) && 
        pager_number == compare.pager_number && 
        supports_text == compare.supports_text && 
        two_way == compare.two_way;
}



// Change the pager number.
int pager::change_number(const cpp_string & new_number) {
    pager_number = new_number;
    return 1;
}



// Change if the pager supports text messages.
int pager::change_supports_text(const bool new_supports_text) {
    supports_text = new_supports_text;
    return 1;
}



// Change if the pager is two-way.
int pager::change_two_way(const bool new_two_way) {
    two_way = new_two_way;
    return 1;
}



// Display the pager.
int pager::display(void) const {
    device::display();

    cout << "Number: " << pager_number << endl;
    cout << "Supports text: " << supports_text << endl;
    cout << "Two way: " << two_way << endl;
    return 1;
}



cell_phone::cell_phone(void) : 
    network(""), 
    phone_number(""), 
    phone_type(os_type::feature_phone) {
    }



cell_phone::cell_phone(const cpp_string & new_network, 
        const cpp_string & new_number,
        const os_type new_type) :
    network(new_network),
    phone_number(new_number),
    phone_type(new_type) {
    }



cell_phone::cell_phone(const cell_phone & other_phone) :
    device(other_phone),
    network(other_phone.network),
    phone_number(other_phone.phone_number) {
    }



cell_phone::~cell_phone(void) {
    network = "";
    phone_number = "";
    phone_type = os_type::feature_phone;
}



cell_phone & cell_phone::operator=(const cell_phone & source) {
    if(this == &source)
        return *this;

    device::operator=(source);
    network = source.network;
    phone_number = source.phone_number;
    phone_type = source.phone_type;
    return *this;
}



bool cell_phone::operator==(const cell_phone & compare) const {
    return device::operator==(compare) &&
        network == compare.network &&
        phone_number == compare.phone_number &&
        phone_type == compare.phone_type;
}



// Change the phone network.
int cell_phone::change_network(const cpp_string & new_network) {
    network = new_network;
    return 1;
}



// Change the phone number.
int cell_phone::change_number(const cpp_string & new_number) {
    phone_number = new_number;
    return 1;
}



// Change the phone's OS.
int cell_phone::change_type(const os_type new_type) {
    phone_type = new_type;
    return 1;
}



// Display the phone.
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



computer::computer(void) : programs(nullptr) {}



computer::computer(const computer & other_computer) :
    device(other_computer),
    programs(nullptr) {
        copy_programs(programs, *other_computer.programs);
    }



computer::~computer(void) {
    clear_programs();
}



// Add a program to the computer.
int computer::add_program(const program & to_add) {
    if(to_add.is_empty())
        return 0;

    return add_program(programs, to_add);
}



// Add a program recursively.
int computer::add_program(program * & current, const program & to_add) {
    if(!current) {
        current = new program(to_add);
        return 1;
    }

    return add_program(current->get_next(), to_add);
}



// Remove a program from the computer.
int computer::remove_program(const program & to_remove) {
    if(to_remove.is_empty())
        return 0;

    return remove_program(to_remove);
}



// Remove a program recursively.
int computer::remove_program(program * & current, const program & to_remove) {
    if(!current)
        return 0;

    program * temp = current->get_next();

    if(current->name_matches(to_remove)) {
        delete current;
        current = temp;
        return 1;
    }

    return remove_program(temp, to_remove);
}



// Clear all programs from the computer.
int computer::clear_programs(void) {
    if(!programs)
        return 0;

    return clear_programs(programs);
}



// Clear all programs recursively.
int computer::clear_programs(program * & current) {
    if(!current)
        return 0;

    program * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear_programs(temp) + 1;
}



// Display the computer.
int computer::display(void) {
    if(is_empty())
        return 0;

    device::display();
    return display(programs);
}



// Display programs recursively.
int computer::display(program * & current) {
    if(!current)
        return 0;

    current->display();
    return display(current->get_next()) + 1;
}



// Copy programs from another computer.
int computer::copy_programs(program * & current, program & other_current) {
    current = new program(other_current);
    return copy_programs(current->get_next(), *other_current.get_next()) +  1;
}
