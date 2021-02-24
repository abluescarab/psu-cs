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
        if(other_contact.devices)
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

    if(source.devices)
        copy_devices(devices, *source.devices);

    left = source.left;
    right = source.right;
    return *this;
}



bool contact::operator==(const contact & source) const {
    return name == source.name && address == source.address;
}



bool operator<(const contact & original, const contact & compare) {
    return original.name < compare.name;
}



bool operator<=(const contact & original, const contact & compare) {
    return original.name <= compare.name;
}



bool operator>(const contact & original, const contact & compare) {
    return original.name > compare.name;
}



bool operator>=(const contact & original, const contact & compare) {
    return original.name >= compare.name;
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
int contact::set_left(contact & new_left) {
    if(new_left.is_empty())
        return 0;

    left = &new_left;
    return 1;
}



// Set the right contact.
int contact::set_right(contact & new_right) {
    if(new_right.is_empty())
        return 0;

    right = &new_right;
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

        

// Copy the data from another contact, minus left and right.
int contact::copy(const contact & copy_from) {
    if(copy_from.is_empty())
        return 0;

    name = copy_from.name;
    address = copy_from.address;
    clear_devices();

    if(copy_from.devices)
        copy_devices(devices, *copy_from.devices);

    return 1;
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



// Display the contact information.
int contact::display(void) {
    if(is_empty())
        return 0;

    cout << name << ": " << address << endl;
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
    if(other_current.is_empty())
        return 0;

    current = new device(other_current);
    return copy_devices(current->get_next(), *other_current.get_next()) + 1;
}



contact_list::contact_list(void) : contacts(nullptr) {}



contact_list::contact_list(const contact_list & other_list) :
    contacts(nullptr) {
    copy_contacts(contacts, *other_list.contacts);
}



contact_list::~contact_list(void) {
    clear();
}



// Add contact.
int contact_list::add(const contact & to_add) {
    if(to_add.is_empty())
        return 0;

    return add(contacts, to_add);
}



// Add contact recursively.
int contact_list::add(contact * & current, const contact & to_add) {
    if(!current) {
        current = new contact(to_add);
        return 1;
    }

    if(to_add < *current)
        return add(current->get_left(), to_add);

    return add(current->get_right(), to_add);
}



// Remove contact.
int contact_list::remove(const contact & to_remove) {
    if(to_remove.is_empty())
        return 0;
    
    return remove(contacts, to_remove);
}



// Remove contact recursively.
int contact_list::remove(contact * & current, const contact & to_remove) {
    if(!current)
        return 0;

    contact * left = current->get_left();
    contact * right = current->get_right();
    contact * child = nullptr;

    if(to_remove < *current)
        return remove(left, to_remove);
    else if(to_remove > *current)
        return remove(right, to_remove);

    if(left && right) { // two children
        inorder_successor(right, child);
        current->copy(*child);
        return remove(child, *child);
    }
    else if(left) {
        current->copy(*left);
    }
    else if(right) {
        current->copy(*right);
    }
    else {
        delete current;
        current = nullptr;
    }

    return 1;
   
    /*// xor to see if only one child exists
    else if(!left != !right) {
        // start with the left
        child = left;

        if(right)
            child = right;
    }
    else {

    }

    delete current;
    current = nullptr;

    if(child) {
        current = new contact(*child);
        
        if(left)
            current->set_left(*left);

        if(right)
            current->set_right(*right);

        return remove(child, *child);
    }

    return 1;*/
/*    if(!current)
        return 0;
    
    contact * child = nullptr;
    contact * left = current->get_left();
    contact * right = current->get_right();

    if(to_remove < *current)
        return remove(left, to_remove);
    else if(to_remove > *current)
        return remove(right, to_remove);
    else {
        if(!left)
            child = right;
        else if(!right)
            child = left;

        delete current;
        inorder_successor(right, current);
    }*/

    /*if(!current)
        return 0;

    contact * child = nullptr;
    contact * left = current->get_left();
    contact * right = current->get_right();

    if(left && right) { // two children
        inorder_successor(right, child);
    }
    // xor to see if only one contact exists
    else if(!left != !right) { // only one child
        // start with the left
        child = left;

        if(right)
            child = right;
    }
    
    delete current;
    current = nullptr;

    if(child) {
        current = new contact(*child);

        contact * left_child = child->get_left();
        contact * right_child = child->get_right();


        //current = new contact(*child);
        //delete child;
        //child = 
        //current->set_left(*left);
        //current->set_right(*right);
        //current = new contact(*child);
        //current = child;


        //current = new contact(*child);
        //current->set_left(*left);
        //current->set_right(*child->get_right());
        //delete child;
        //child = nullptr;
    }
    
    return 1;*/
}



// Clear contacts.
int contact_list::clear(void) {
    return clear(contacts);
}



// Clear contacts recursively.
int contact_list::clear(contact * & current) {
    if(!current)
        return 0;

    int count = clear(current->get_left()) + 
        clear(current->get_right());

    delete current;
    current = nullptr;
    return count + 1;
}



// Display entire contact list.
int contact_list::display(void) {
    return display(contacts);
}



// Display entire contact list recursively.
int contact_list::display(contact * & current) {
    if(!current)
        return 0;

    int count = display(current->get_left());
    count += current->display();
    count += display(current->get_right());

    return count;
}



// Find a contact.
int contact_list::find(contact * & current, 
        const contact & to_find, 
        contact * & result) {
    if(!current)
        return 0;

    if(current == &to_find) {
        result = new contact(*current);
        return 1;
    }

    return find(current->get_left(), to_find, result) || 
        find(current->get_right(), to_find, result);
}
        


// Copy contacts from another list recursively.
int contact_list::copy_contacts(contact * & current, contact & other_current) {
    return 1;
}



// Get the inorder successor of the current contact.
int contact_list::inorder_successor(contact * & current, contact * & result) {
    if(!current)
        return 0;

    contact * left = current->get_left();

    if(!left) {
        result = current;
        return 1;
    }

    return inorder_successor(left, result);
}
