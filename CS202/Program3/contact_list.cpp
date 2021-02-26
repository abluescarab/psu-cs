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
    devices(nullptr), 
    left(nullptr), 
    right(nullptr) {
    }



contact::contact(const cpp_string & new_name) : 
    name(new_name), 
    devices(nullptr), 
    left(nullptr), 
    right(nullptr) {
    }



contact::contact(const contact & other_contact) :
    name(other_contact.name),
    devices(nullptr),
    left(other_contact.left),
    right(other_contact.right) {
        if(other_contact.devices)
            copy_devices(devices, *other_contact.devices);
    }



contact::~contact(void) {
    name = "";
    clear_devices();
    left = nullptr;
    right = nullptr;
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
int contact::set_left(contact * & new_left) {
    if(!new_left)
        return 0;

    delete left;
    left = new_left;
    return 1;
}



// Set the right contact.
int contact::set_right(contact * & new_right) {
    if(!new_right)
        return 0;

    delete right;
    right = new_right;
    return 1;
}



// Clear the left contact.
int contact::clear_left(void) {
    if(!left)
        return 0;

    delete left;
    left = nullptr;
    return 1;
}



// Clear the right contact.
int contact::clear_right(void) {
    if(!right)
        return 0;

    delete right;
    right = nullptr;
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

        

// Check if a contact matches another contact.
int contact::matches(const cpp_string & compare) const {
    return name == compare;
}



// Check if a contact matches another contact.
int contact::matches(const contact & compare) const {
    return name == compare.name;
}



// Display the contact information.
int contact::display(void) {
    if(is_empty())
        return 0;

    cout << name << endl;
    display_devices(devices);
    cout << endl;
    return 1;
}



// Display devices recursively.
int contact::display_devices(device * & current) {
    if(!current)
        return 0;

    int count = current->display();
    return display_devices(current->get_next()) + count;
}



// Display only the contact name.
int contact::display_name(void) {
    if(is_empty())
        return 0;

    cout << name << endl;
    return 1;
}



// Copy devices recursively.
int contact::copy_devices(device * & current, device & other_current) {
    current = new device(other_current);

    if(other_current.get_next())
        return copy_devices(current->get_next(), *other_current.get_next()) + 1;
    else
        return 1;
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
int contact_list::remove(const cpp_string & to_remove) {
    if(to_remove.is_empty())
        return 0;

    int result = 0;
    contact * parent = nullptr;
    contact * contact_obj = new contact(to_remove);

    result = remove(contacts, parent, *contact_obj);
    delete contact_obj;
    return result;
}


// Remove contact recursively.
int contact_list::remove(contact * & current, contact * & parent, const contact & to_remove) {
    if(!current)
        return 0;

    contact * left = current->get_left();
    contact * right = current->get_right();
    contact * child = nullptr;

    if(to_remove < *current)
        return remove(left, current, to_remove);
    else if(to_remove > *current)
        return remove(right, current, to_remove);

    if(left && right) { // two children
        child = right;

        if(child->get_left())
            inorder_successor(right, parent, child);

        current->copy(*child);

        if(parent)
            return remove(child, parent, *child);
        else
            return remove(child, current, *child);
    }
    else if(left && !right)  { // left child
        if(!parent)
            current = left;
        else {
            if(parent->get_left() == current)
                parent->set_left(left);
            else
                parent->set_right(left);
        }
    }
    else if(right && !left) { // right child
        if(!parent)
            current = right;
        else {
            if(parent->get_left() == current)
                parent->set_left(right);
            else
                parent->set_right(right);
        }
    }
    else { // no children
        if(!parent) {
            delete current;
            current = nullptr;
        }
        else { // if the current node's parent exists
            if(parent->get_left() == current) {
                parent->clear_left();
            }
            else if(parent->get_right() == current) {
                parent->clear_right();
            }
        }
    }

    return 1;
}



// Clear contacts.
int contact_list::clear(void) {
    return clear(contacts);
}



// Clear contacts recursively.
int contact_list::clear(contact * & current) {
    if(!current)
        return 0;

    int count = clear(current->get_left()) + clear(current->get_right());

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
int contact_list::find(const cpp_string & to_find, contact * & result) {
    return find(contacts, to_find, result);
}



// Find a contact recursively.
int contact_list::find(contact * & current, 
        const cpp_string & to_find, 
        contact * & result) {
    if(!current)
        return 0;

    if(current->matches(to_find)) {
        result = new contact(*current);
        return 1;
    }

    return find(current->get_left(), to_find, result) || 
        find(current->get_right(), to_find, result);
}
        


// Copy contacts from another list recursively.
int contact_list::copy_contacts(contact * & current, contact & other_current) {
    if(other_current.is_empty())
        return 0;

    int count = 1;
    current = new contact(other_current);

    if(other_current.get_left())
        count += copy_contacts(current->get_left(), *other_current.get_left());

    if(other_current.get_right())
        count += copy_contacts(current->get_right(), *other_current.get_right());

    return count;
}



// Get the inorder successor of the current contact.
int contact_list::inorder_successor(contact * & current, 
        contact * & parent, 
        contact * & result) {
    if(!current)
        return 0;

    contact * left = current->get_left();

    if(left)
        parent = current;
    else {
        result = current;
        return 1;
    }

    return inorder_successor(left, parent, result);
}
