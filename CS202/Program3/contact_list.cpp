/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * contact_list.cpp
 *
 * This is the implementation file for the contact_list class. The contact_list 
 * class manages a list of contacts and their corresponding devices.
 */
#include <iostream>
#include "contact_list.h"
#define MAX_INPUT 1024

using namespace std;



// Create a new contact.
contact::contact(void) : 
    name(""), 
    devices(nullptr), 
    left(nullptr), 
    right(nullptr) {
    }



// Create a new contact.
// INPUT:
//  new_name: the name of the contact
contact::contact(const cpp_string & new_name) : 
    name(new_name), 
    devices(nullptr), 
    left(nullptr), 
    right(nullptr) {
    }



// Create a new contact.
// INPUT:
//  other_contact: another contact to copy from
contact::contact(const contact & other_contact) :
    name(other_contact.name),
    devices(nullptr),
    left(other_contact.left),
    right(other_contact.right) {
        if(other_contact.devices)
            copy_devices(devices, *other_contact.devices);
    }



// Delete the contact.
contact::~contact(void) {
    name = "";
    clear_devices();
    left = nullptr;
    right = nullptr;
}



// Less than operator.
// INPUT:
//  original: the original contact
//  compare: the contact to compare against
// OUTPUT:
//  Whether the original's name is less than compare's name.
bool operator<(const contact & original, const contact & compare) {
    return original.name < compare.name;
}



// Less than or equal to operator.
// INPUT:
//  original: the original contact
//  compare: the contact to compare against
// OUTPUT:
//  Whether the original's name is less than or equal to compare's name.
bool operator<=(const contact & original, const contact & compare) {
    return original.name <= compare.name;
}




// More than operator.
// INPUT:
//  original: the original contact
//  compare: the contact to compare against
// OUTPUT:
//  Whether the original's name is more than compare's name.
bool operator>(const contact & original, const contact & compare) {
    return original.name > compare.name;
}



// More than or equal to operator.
// INPUT:
//  original: the original contact
//  compare: the contact to compare against
// OUTPUT:
//  Whether the original's name is more than or equal to compare's name.
bool operator>=(const contact & original, const contact & compare) {
    return original.name >= compare.name;
}



// Input operator.
// INPUT:
//  in: the istream
//  in_contact: the contact to write to
// OUTPUT:
//  Returns the istream object.
std::istream & operator>>(std::istream & in, contact & in_contact) {
    char temp[MAX_INPUT];

    in >> temp;
    in_contact.name = temp;
    return in;
}



// Output operator.
// INPUT:
//  out: the ostream
//  out_contact: the contact to output
// OUTPUT:
//  Returns the ostream object.
std::ostream & operator<<(std::ostream & out, const contact & out_contact) {
    out << out_contact.name;
    return out;
}



// Set the left contact.
// INPUT:
//  new_left: the new left node
// OUTPUT:
//  0 if the new node does not exist.
//  1 if the set is successful.
int contact::set_left(contact * & new_left) {
    if(!new_left)
        return 0;

    delete left;
    left = new_left;
    return 1;
}



// Set the right contact.
// INPUT:
//  new_right: the new right node
// OUTPUT:
//  0 if the new node does not exist.
//  1 if the set is successful.
int contact::set_right(contact * & new_right) {
    if(!new_right)
        return 0;

    delete right;
    right = new_right;
    return 1;
}



// Clear the left contact.
// OUTPUT:
//  0 if the left node does not exist.
//  1 if the node is deleted successfully.
int contact::clear_left(void) {
    if(!left)
        return 0;

    delete left;
    left = nullptr;
    return 1;
}



// Clear the right contact.
// OUTPUT:
//  0 if the right node does not exist.
//  1 if the node is deleted successfully.
int contact::clear_right(void) {
    if(!right)
        return 0;

    delete right;
    right = nullptr;
    return 1;
}



// Get the left contact.
// OUTPUT: 
//  Returns the left node.
contact * & contact::get_left(void) {
    return left;
}



// Get the right contact.
// OUTPUT:
//  Returns the right node.
contact * & contact::get_right(void) {
    return right;
}

        

// Copy the data from another contact, minus left and right.
// INPUT:
//  copy_from: the contact to copy
// OUTPUT:
//  0 if the contact to copy is empty.
//  1 if the copy is successful.
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
// INPUT:
//  new_name: the name to change to
// OUPUT: 
//  1 when the name change is successful.
int contact::change_name(const cpp_string & new_name) {
    name = new_name;
    return 1;
}



// Add a device.
// INPUT:
//  to_add: the device to add
// OUTPUT:
//  0 if the device to add is empty.
//  Otherwise returns the result of the recursive function.
int contact::add_device(device & to_add) {
    if(to_add.is_empty())
        return 0;

    return add_device(devices, to_add);
}



// Add a device recursively.
// INPUT:
//  current: the current device in the list
//  to_add: the device to add
// OUTPUT:
//  1 when the device is added.
int contact::add_device(device * & current, device & to_add) {
    pager * new_pager = dynamic_cast<pager*>(&to_add);
    cell_phone * new_phone = dynamic_cast<cell_phone*>(&to_add);
    computer * new_computer = dynamic_cast<computer*>(&to_add);

    if(!current) {
        if(new_pager)
            current = new pager(*new_pager);
        else if(new_phone)
            current = new cell_phone(*new_phone);
        else
            current = new computer(*new_computer);

        return 1;
    }

    return add_device(current->get_next(), to_add);
} 



// Remove a device.
// INPUT:
//  to_remove: the device to remove
// OUTPUT:
//  0 if the device to remove is empty.
//  Otherwise returns the result of the recursive function.
int contact::remove_device(const cpp_string & to_remove) {
    if(to_remove.is_empty())
        return 0;

    return remove_device(devices, to_remove);
}



// Remove a device recursively.
// INPUT:
//  current: the current device
//  to_remove: the device to remove
// OUTPUT:
//  0 if the device does not exist in the list.
//  1 if the device is removed.
int contact::remove_device(device * & current, const cpp_string & to_remove) {
    if(!current)
        return 0;

    device * temp = current->get_next();

    if(current->name_matches(to_remove)) { 
        delete current;
        current = temp;
        return 1;
    }

    return remove_device(temp, to_remove);
}



// Clear all devices.
// OUTPUT:
//  Returns the result of the recursive function.
int contact::clear_devices(void) {
    return clear_devices(devices);
}



// Clear devices recursively.
// INPUT:
//  current: the current device
// OUTPUT: 
//  Returns the number of devices removed.
int contact::clear_devices(device * & current) {
    if(!current)
        return 0;

    device * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear_devices(temp) + 1;
}



// Display a device.
// INPUT:
//  to_display: device to display
// OUTPUT:
//  0 if the device is not displayed.
//  1 if the device is displayed.
int contact::display_device(const cpp_string & to_display) {
    device * current_device = nullptr;

    if(find_device(devices, to_display, current_device))
        return current_device->display();

    return 0;
}



// Check if a device exists and returns its type.
// INPUT:
//  to_find: device name to find
// OUTPUT:
//  Returns the type of the device.
device_type contact::has_device(const cpp_string & to_find) {
    device * current_device = nullptr;
    device_type current_type = device_type::none;

    if(find_device(devices, to_find, current_device)) {
        if(dynamic_cast<pager*>(current_device))
            current_type = device_type::pager;
        else if(dynamic_cast<cell_phone*>(current_device))
            current_type = device_type::cell_phone;
        else if(dynamic_cast<computer*>(current_device))
            current_type = device_type::computer;
    }
    
    return current_type;
}



// Add a program to a device.
// INPUT:
//  to_device: name of the device to add to
//  to_add: program to add
// OUTPUT:
//  0 if the program fails to add.
//  1 if the program adds.
int contact::add_program(const cpp_string to_device, const program & to_add) {
    device * current_device = nullptr;
    computer * current_computer = nullptr;

    if(find_device(devices, to_device, current_device)) {
        current_computer = dynamic_cast<computer*>(current_device);

        if(current_computer)
            return current_computer->add_program(to_add);
    }

    return 0;
}



// Remove a program from a device.
// INPUT:
//  from_device: name of the device to remove from
//  to_remove: name of the program to remove
// OUTPUT:
//  0 if the program fails to remove.
//  1 if the program removes.
int contact::remove_program(const cpp_string from_device, const cpp_string & to_remove) {
    device * current_device = nullptr;
    computer * current_computer = nullptr;

    if(find_device(devices, from_device, current_device)) {
        current_computer = dynamic_cast<computer*>(current_device);

        if(current_computer)
            return current_computer->remove_program(to_remove);
    }

    return 0;
}



// Clear programs from a device.
// INPUT:
//  from_device: device name to remove programs from
// OUTPUT:
//  Returns the number of programs removed.
int contact::clear_programs(const cpp_string from_device) {
    device * current_device = nullptr;
    computer * current_computer = nullptr;

    if(find_device(devices, from_device, current_device)) {
        current_computer = dynamic_cast<computer*>(current_device);

        if(current_computer)
            return current_computer->clear_programs();
    }

    return 0;
}



// Send a message to a device.
// INPUT:
//  to_device: device to send message to
//  message: message to send
// OUTPUT:
//  0 if the message was not sent.
//  1 if the message was sent.
int contact::send_message(const cpp_string & to_device, const cpp_string & message) {
    device * current_device = nullptr;

    if(find_device(devices, to_device, current_device))
        return current_device->send_message(message);

    return 0;
}



// Clear messages from a device.
// INPUT:
//  from_device: device to clear messages from
// OUTPUT:
//  0 if the messages were not cleared.
//  1 if the messages were cleared.
int contact::clear_messages(const cpp_string & from_device) {
    device * current_device = nullptr;

    if(find_device(devices, from_device, current_device))
        return current_device->clear_messages();

    return 0;
}



// Display messages from a device.
// INPUT:
//  from_device: device to display messages from
// OUTPUT:
//  0 if the messages were not displayed.
//  1 if the messages were displayed.
int contact::display_messages(const cpp_string & from_device) {
    device * current_device = nullptr;

    if(find_device(devices, from_device, current_device))
        return current_device->display_messages();

    return 0;
}



// Find and return a device recursively.
// INPUT:
//  current: the current device
//  to_find: the name of the device
//  result: the result of the search
// OUTPUT:
//  0 if the device was not found.
//  1 if the device was found.
int contact::find_device(device * & current, const cpp_string & to_find, device * & result) {
    if(!current)
        return 0;

    if(current->name_matches(to_find)) {
        result = current;
        return 1;
    }

    return find_device(current->get_next(), to_find, result);
}



// Check if the contact has data.
// OUTPUT:
//  Returns the result of the is_empty function.
int contact::is_empty(void) const {
    return name.is_empty();
}

        

// Check if a contact matches another contact.
// INPUT:
//  compare: the cpp_string to compare to
// OUTPUT:
//  Returns whether name equals compare.
int contact::matches(const cpp_string & compare) const {
    return name == compare;
}



// Check if a contact matches another contact.
// INPUT:
//  compare: the contact to compare to
// OUTPUT:
//  Returns if the name matches the compare's name.
int contact::matches(const contact & compare) const {
    return name == compare.name;
}



// Display the contact information.
// OUTPUT:
//  0 if the contact is empty.
//  1 if the contact displays successfully.
int contact::display(void) {
    if(is_empty())
        return 0;

    cout << name << endl;
    display_devices(devices);
    cout << endl;
    return 1;
}



// Display devices recursively.
// INPUT:
//  current: the current device
// OUTPUT:
//  Returns the number of devices displayed.
int contact::display_devices(device * & current) {
    if(!current)
        return 0;

    return current->display() + display_devices(current->get_next());
}



// Display only the contact name.
// OUTPUT:
//  0 if the contact is empty.
//  1 if the contact's name is displayed.
int contact::display_name(void) {
    if(is_empty())
        return 0;

    cout << name << endl;
    return 1;
}



// Copy devices recursively.
// INPUT:
//  current: the current device
//  other_current: the other contact's current device
// OUTPUT:
//  Returns the number of devices copied.
int contact::copy_devices(device * & current, device & other_current) {
    current = new device(other_current);

    if(!other_current.get_next())
        return 1;

    return copy_devices(current->get_next(), *other_current.get_next()) + 1;
}



// Create a new contact list.
contact_list::contact_list(void) : contacts(nullptr) {}



// Create a new contact list.
// INPUT:
//  other_list: the list to copy from
contact_list::contact_list(const contact_list & other_list) :
    contacts(nullptr) {
    copy_contacts(contacts, *other_list.contacts);
}



// Delete the contact list.
contact_list::~contact_list(void) {
    clear_contacts();
}



// Add contact.
// INPUT:
//  to_add: the contact to add
// OUTPUT:
//  0 if the contact to add is empty.
//  Otherwise returns the result of the recursive function.
int contact_list::add_contact(const contact & to_add) {
    if(to_add.is_empty())
        return 0;

    return add_contact(contacts, to_add);
}



// Add contact recursively.
// INPUT:
//  current: the current contact
//  to_add: the contact to add
// OUTPUT:
//  Returns 1 when the contact is added.
int contact_list::add_contact(contact * & current, const contact & to_add) {
    if(!current) {
        current = new contact(to_add);
        return 1;
    }

    if(to_add < *current)
        return add_contact(current->get_left(), to_add);

    return add_contact(current->get_right(), to_add);
}



// Remove contact.
// INPUT:
//  to_remove: the name of the contact to remove
// OUTPUT:
//  0 if the string is empty.
//  Otherwise returns the result of the recursive function.
int contact_list::remove_contact(const cpp_string & to_remove) {
    if(to_remove.is_empty())
        return 0;

    int result = 0;
    contact * parent = nullptr;
    contact * contact_obj = new contact(to_remove);

    result = remove_contact(contacts, parent, *contact_obj);
    delete contact_obj;
    return result;
}


// Remove contact recursively.
// INPUT:
//  current: the current contact
//  parent: the parent of the current contact
//  to_remove: the contact to remove
// OUTPUT:
//  0 if the contact to remove does not exist.
//  1 if the contact is removed.
int contact_list::remove_contact(contact * & current, contact * & parent, const contact & to_remove) {
    if(!current)
        return 0;

    contact * left = current->get_left();
    contact * right = current->get_right();
    contact * child = nullptr;

    if(to_remove < *current)
        return remove_contact(left, current, to_remove);
    else if(to_remove > *current)
        return remove_contact(right, current, to_remove);

    if(left && right) { // two children
        child = right;

        if(child->get_left())
            inorder_successor(right, parent, child);

        current->copy(*child);

        if(parent)
            return remove_contact(child, parent, *child);
        else
            return remove_contact(child, current, *child);
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



// Display a contact.
// INPUT:
//  to_display: contact to display
// OUTPUT:
//  0 if the contact was not displayed.
//  1 if the contact was displayed.
int contact_list::display_contact(const cpp_string & to_display) {
    contact * current_contact = nullptr;
    
    if(find_contact(contacts, to_display, current_contact))
        return current_contact->display();

    return 0;
}


        
// Change the contact's name.
// INPUT:
//  old_name: contact to find
//  new_name: name to change to
// OUTPUT:
//  0 if the contact was not found.
//  1 if the name was changed.
int contact_list::change_contact_name(const cpp_string & old_name, 
        const cpp_string & new_name) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, old_name, current_contact))
        return current_contact->change_name(new_name);

    return 0;
}



// Add a device to a contact.
// INPUT:
//  to_contact: contact name to add to
//  to_add: device to add
// OUTPUT:
//  0 if the device fails to add.
//  1 if the device adds.
int contact_list::add_device(const cpp_string & to_contact, device & to_add) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, to_contact, current_contact))
        return current_contact->add_device(to_add);
        
    return 0;
}



// Remove a device from a contact.
// INPUT:
//  from_contact: contact name to remove from
//  to_remove: device name to remove
// OUTPUT:
//  0 if the device fails to remove.
//  1 if the device removes.
int contact_list::remove_device(const cpp_string & from_contact, const cpp_string & to_remove) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, from_contact, current_contact))
        return current_contact->remove_device(to_remove);
        
    return 0;
}



// Clear devices from a contact.
// INPUT:
//  from_contact: contact name to clear devices from
// OUTPUT:
//  Returns the number of devices cleared.
int contact_list::clear_devices(const cpp_string & from_contact) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, from_contact, current_contact))
        return current_contact->clear_devices();

    return 0;
}



// Display a device.
// INPUT:
//  from_contact: contact which device is in
//  to_display: device to display
// OUTPUT:
//  0 if the device does not display.
//  1 if the device displays.
int contact_list::display_device(const cpp_string & from_contact, const cpp_string & to_display) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, from_contact, current_contact))
        return current_contact->display_device(to_display);

    return 0;
}


        
// Add a program to a device.
// INPUT:
//  to_contact: contact name to add program to
//  to_device: device name to add to
//  to_add: program to add
// OUTPUT:
//  0 if the program fails to add.
//  1 if the program adds.
int contact_list::add_program(const cpp_string & to_contact, 
        const cpp_string & to_device, 
        const program & to_add) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, to_contact, current_contact))
        return current_contact->add_program(to_device, to_add);

    return 0;
}



// Remove a program from a device.
// INPUT:
//  from_contact: contact name to remove from
//  from_device: device name to remove from
//  to_remove: program to remove
// OUTPUT:
//  0 if the program fails to remove.
//  1 if the program removes.
int contact_list::remove_program(const cpp_string & from_contact,
        const cpp_string & from_device,
        const cpp_string & to_remove) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, from_contact, current_contact))
        return contacts->remove_program(from_device, to_remove);

    return 0;
}



// Clear programs from a device.
// INPUT:
//  from_contact: contact to clear programs from
//  from_device: device to clear programs from
// OUTPUT:
//  Returns the number of programs cleared.
int contact_list::clear_programs(const cpp_string & from_contact, 
        const cpp_string & from_device) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, from_contact, current_contact))
        return current_contact->clear_programs(from_device);

    return 0;
}



// Send a message to a device.
// INPUT:
//  to_contact: contact to message
//  to_device: contact device to message
//  message: message to send
// OUTPUT:
//  0 if the message was not sent.
//  1 if the message was sent.
int contact_list::send_message(const cpp_string & to_contact, 
        const cpp_string & to_device, 
        const cpp_string & message) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, to_contact, current_contact))
        return current_contact->send_message(to_device, message);

    return 0;
}



// Clear messages from a device.
// INPUT:
//  from_contact: contact to clear messages from
//  from_device: device to clear messages from
// OUTPUT:
//  0 if the messages were not cleared.
//  1 if the messages were cleared.
int contact_list::clear_messages(const cpp_string & from_contact, 
        const cpp_string & from_device) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, from_contact, current_contact))
        return current_contact->clear_messages(from_device);

    return 0;
}



// Display messages from a device.
// INPUT:
//  from_contact: contact to display messages from
//  from_device: device to display messages from
// OUTPUT:
//  Return result of display function.
int contact_list::display_messages(const cpp_string & from_contact, 
        const cpp_string & from_device) {
    contact * current_contact = nullptr;

    if(find_contact(contacts, from_contact, current_contact))
        return current_contact->display_messages(from_device);

    return 0;
}



// Check if the list contains a contact.
// INPUT:
//  to_find: contact to find
// OUTPUT:
//  Returns the result of the recursive function.
int contact_list::has_contact(const cpp_string & to_find) {
    contact * result = nullptr;
    return find_contact(contacts, to_find, result);
}

        

// Check if a contact has a device and returns its type.
// INPUT:
//  in_contact: contact device is in
//  to_find: device to find
// OUTPUT:
//  Returns the device type.
device_type contact_list::has_device(const cpp_string & in_contact, const cpp_string & to_find) {
    contact * current_contact = nullptr;
    
    if(find_contact(contacts, in_contact, current_contact))
        return current_contact->has_device(to_find);

    return device_type::none;
}



// Clear contacts.
// OUTPUT:
//  Returns the result of the recursive function.
int contact_list::clear_contacts(void) {
    return clear_contacts(contacts);
}



// Clear contacts recursively.
// INPUT:
//  current: the current contact
// OUTPUT:
//  Returns the number of contacts removed.
int contact_list::clear_contacts(contact * & current) {
    if(!current)
        return 0;

    int count = clear_contacts(current->get_left()) + clear_contacts(current->get_right());

    delete current;
    current = nullptr;
    return count + 1;
}



// Display entire contact list.
// OUTPUT:
//  Returns the result of the recursive function.
int contact_list::display(void) {
    return display(contacts);
}



// Display entire contact list recursively.
// INPUT:
//  current: the current contact
// OUTPUT:
//  Returns the number of contacts displayed.
int contact_list::display(contact * & current) {
    if(!current)
        return 0;

    int count = display(current->get_left());
    count += current->display();
    count += display(current->get_right());

    return count;
}



// Find a contact recursively.
// INPUT:
//  current: the current contact
//  to_find: the contact name to find
//  result: the result of the search
// OUTPUT:
//  0 if the contact is not found.
//  1 if the contact is found.
int contact_list::find_contact(contact * & current, 
        const cpp_string & to_find, 
        contact * & result) {
    if(!current)
        return 0;

    if(current->matches(to_find)) {
        result = current;
        return 1;
    }

    return find_contact(current->get_left(), to_find, result) || 
        find_contact(current->get_right(), to_find, result);
}
        


// Copy contacts from another list recursively.
// INPUT:
//  current: the current contact
//  other_current: the other contact list's current contact
// OUTPUT:
//  Returns the number of contacts copied.
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
// INPUT:
//  current: the current contact
//  parent: the parent of the inorder successor
//  result: the inorder successor
// OUTPUT:
//  0 if the IOS was not found.
//  1 if the IOS was found.
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
