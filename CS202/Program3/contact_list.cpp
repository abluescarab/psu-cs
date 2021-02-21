/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * contact_list.cpp
 *
 *
 */
#include <iostream>
#include "contact_list.h"
#define MAX_INPUT 1024

using namespace std;



contact::contact(void) : 
    name(""), 
    address(""), 
    devices(nullptr), 
    left(nullptr), 
    right(nullptr) {
    }



contact::contact(const cpp_string & new_name, const cpp_string & new_address) : 
    name(new_name), 
    address(new_address), 
    devices(nullptr), 
    left(nullptr), 
    right(nullptr) {
    }



contact::contact(const contact & other_contact) :
    name(other_contact.name),
    address(other_contact.address),
    devices(nullptr),
    left(other_contact.left),
    right(other_contact.right) {
        copy_devices(devices, *other_contact.devices);
    }



contact::~contact(void) {
    name = "";
    address = "";
    clear_devices();
    left = nullptr;
    right = nullptr;
}



contact & contact::operator=(const contact & source) {
    if(this == &source)
        return *this;

    name = source.name;
    address = source.address;
    copy_devices(devices, *source.devices);
    left = source.left;
    right = source.right;
    return *this;
}



bool contact::operator==(const contact & source) const {
    return name == source.name && address == source.address;
}



std::istream & operator>>(std::istream & in, contact & in_contact) {
    char temp[MAX_INPUT];

    in >> temp;
    in_contact.name = temp;
    return in;
}



std::ostream & operator<<(std::ostream & out, const contact & out_contact) {
    out << out_contact.name;
    return out;
}



// Set the left contact.
int contact::set_left(const contact & new_left) {
    left = new contact(new_left);
    return 1;
}



// Set the right contact.
int contact::set_right(const contact & new_right) {
    right = new contact(new_right);
    return 1;
}



// Get the left contact.
contact * & contact::get_left(void) {
    return left;
}



// Get the right contact.
contact * & contact::get_right(void) {
    return right;
}



// Change the contact's name.
int contact::change_name(const cpp_string & new_name) {
    name = new_name;
    return 1;
}



// Change the contact's address.
int contact::change_address(const cpp_string & new_address) {
    address = new_address;
    return 1;
}



// Add a device.
int contact::add_device(const device & to_add) {
    if(to_add.is_empty())
        return 0;

    return add_device(devices, to_add);
}



// Add a device recursively.
int contact::add_device(device * & current, const device & to_add) {
    if(!current) {
        current = new device(to_add);
        return 1;
    }

    return add_device(current->get_next(), to_add);
} 



// Remove a device.
int contact::remove_device(const device & to_remove) {
    if(to_remove.is_empty())
        return 0;

    return remove_device(devices, to_remove);
}



// Remove a device recursively.
int contact::remove_device(device * & current, const device & to_remove) {
    if(!current)
        return 0;

    device * temp = current->get_next();

    if(current == &to_remove) { 
        delete current;
        current = temp;
        return 1;
    }

    return remove_device(temp, to_remove);
}



// Clear all devices.
int contact::clear_devices(void) {
    return clear_devices(devices);
}



// Clear devices recursively.
int contact::clear_devices(device * & current) {
    if(!current)
        return 0;

    device * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear_devices(temp) + 1;
}



// Check if the contact has data.
int contact::is_empty(void) const {
    return name.is_empty();
}



// Check if the contact matches another contact.
int contact::matches(const contact & other_contact) const {
    return name == other_contact.name &&
        address == other_contact.address;
}



// Display the contact information.
int contact::display(void) {
    if(is_empty())
        return 0;

    cout << name << endl;
    cout << address << endl;
    return display_devices(devices);
}

        

// Display devices recursively.
int contact::display_devices(device * & current) {
    if(!current)
        return 0;

    int count = current->display();
    return display_devices(current->get_next()) + count;
}



// Copy devices recursively.
int contact::copy_devices(device * & current, device & other_current) {
    current = new device(other_current);
    return copy_devices(current->get_next(), *other_current.get_next()) + 1;
}



contact_list::contact_list(void) : contacts(nullptr) {}



contact_list::contact_list(const contact_list & other_list) :
    contacts(nullptr) {
    copy_contacts(contacts, *other_list.contacts);
}



contact_list::~contact_list(void) {
    clear_contacts();
}



// Add contact.
int contact_list::add_contact(const contact & to_add) {
    if(to_add.is_empty())
        return 0;

    return add_contact(contacts, to_add);
}



// Add contact recursively.
int contact_list::add_contact(contact * & current, const contact & to_add) {
    return 1;
}



// Remove contact.
int contact_list::remove_contact(const contact & to_remove) {
    if(to_remove.is_empty())
        return 0;
    
    return remove_contact(contacts, to_remove);
}



// Remove contact recursively.
int contact_list::remove_contact(contact * & current, const contact & to_remove) {
    return 1;
}



// Clear contacts.
int contact_list::clear_contacts(void) {
    return clear_contacts(contacts);
}



// Clear contacts recursively.
int contact_list::clear_contacts(contact * & current) {
    if(!current)
        return 0;

    return 1;
}



// Display entire contact list.
int contact_list::display(void) {
    return 1;
}



// Display entire contact list recursively.
int contact_list::display(contact * & current) {
    return 1;
}



// Find a contact.
int contact_list::find_contact(const contact & to_find, contact * & result) {
    return 1;
}
        


// Copy contacts from another list recursively.
int contact_list::copy_contacts(contact * & current, contact & other_current) {
    return 1;
}
