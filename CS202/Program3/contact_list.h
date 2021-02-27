/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * contact_list.h
 *
 * This is the header file for the contact_list class. The contact_list class 
 * manages a list of contacts and their corresponding devices.
 */
#ifndef CONTACT_LIST_H
#define CONTACT_LIST_H
#include "device.h"

class contact {
    public:
        contact(void);
        contact(const cpp_string & new_name);
        contact(const contact & other_contact);
        ~contact(void);

        // relational operators
        friend bool operator<(const contact & original, const contact & compare);
        friend bool operator<=(const contact & original, const contact & compare);
        friend bool operator>(const contact & original, const contact & compare);
        friend bool operator>=(const contact & original, const contact & compare);
        // i/o operators
        friend std::istream & operator>>(std::istream & in, contact & in_contact);
        friend std::ostream & operator<<(std::ostream & out, const contact & out_contact);

        // Set the left contact.
        int set_left(contact * & new_left);
        // Set the right contact.
        int set_right(contact * & new_right);
        // Clear the left contact.
        int clear_left(void);
        // Clear the right contact.
        int clear_right(void);
        // Get the left contact.
        contact * & get_left(void);
        // Get the right contact.
        contact * & get_right(void);
        // Copy the data from another contact, minus left and right.
        int copy(const contact & copy_from);
        // Change the contact's name.
        int change_name(const cpp_string & new_name);
        // Add a device.
        int add_device(device & to_add);
        // Remove a device.
        int remove_device(const cpp_string & to_remove);
        // Clear all devices.
        int clear_devices(void);
        // Display a device.
        int display_device(const cpp_string & to_display);
        // Check if a device exists.
        device_type has_device(const cpp_string & to_find);
        // Add a program to a device.
        int add_program(const cpp_string to_device, const program & to_add);
        // Remove a program from a device.
        int remove_program(const cpp_string from_device, const cpp_string & to_remove);
        // Clear programs from a device.
        int clear_programs(const cpp_string from_device);
        // Send a message to a device.
        int send_message(const cpp_string & to_device, const cpp_string & message);
        // Clear messages from a device.
        int clear_messages(const cpp_string & from_device);
        // Display messages from a device.
        int display_messages(const cpp_string & from_device);
        // Check if the contact has data.
        int is_empty(void) const;
        // Check if a contact matches another contact.
        int matches(const cpp_string & compare) const;
        // Check if a contact matches another contact.
        int matches(const contact & compare) const;
        // Display the contact information.
        int display(void);
        // Display only the contact name.
        int display_name(void);

    private:
        // Add a device recursively.
        int add_device(device * & current, device & to_add);
        // Remove a device recursively.
        int remove_device(device * & current, const cpp_string & to_remove);
        // Clear devices recursively.
        int clear_devices(device * & current);
        // Find and return a device recursively.
        int find_device(device * & current, const cpp_string & to_find, device * & result);
        // Copy devices recursively.
        int copy_devices(device * & current, device & other_current);
        // Display devices recursively.
        int display_devices(device * & current);

        cpp_string name; // contact name
        device * devices; // devices the contact owns
        contact * left; // left contact in the tree
        contact * right; // right contact in the tree
};



class contact_list {
    public:
        contact_list(void);
        contact_list(const contact_list & other_list);
        ~contact_list(void);

        contact_list & operator=(const contact_list & source);
        contact_list & operator+=(const contact_list & source);
        friend contact_list operator+(contact_list & destination, 
                const contact_list & source);

        // Add contact.
        int add_contact(const contact & to_add);
        // Remove contact.
        int remove_contact(const cpp_string & to_remove);
        // Display a contact.
        int display_contact(const cpp_string & to_display);
        // Change the contact's name.
        int change_contact_name(const cpp_string & old_name, const cpp_string & new_name);
        // Add a device to a contact.
        int add_device(const cpp_string & to_contact, device & to_add);
        // Remove a device from a contact.
        int remove_device(const cpp_string & from_contact, const cpp_string & to_remove);
        // Clear devices from a contact.
        int clear_devices(const cpp_string & from_contact);
        // Display a device.
        int display_device(const cpp_string & from_contact, const cpp_string & to_display);
        // Add a program to a device.
        int add_program(const cpp_string & to_contact, 
                const cpp_string & to_device, 
                const program & to_add);
        // Remove a program from a device.
        int remove_program(const cpp_string & from_contact,
                const cpp_string & from_device,
                const cpp_string & to_remove);
        // Clear programs from a device.
        int clear_programs(const cpp_string & from_contact, const cpp_string & from_device);
        // Send a message to a device.
        int send_message(const cpp_string & to_contact, 
                const cpp_string & to_device, 
                const cpp_string & message);
        // Clear messages from a device.
        int clear_messages(const cpp_string & from_contact, const cpp_string & from_device);
        // Display messages from a device.
        int display_messages(const cpp_string & from_contact, const cpp_string & from_device);
        // Check if the list contains a contact.
        int has_contact(const cpp_string & to_find);
        // Check if a contact has a device.
        device_type has_device(const cpp_string & in_contact, const cpp_string & to_find);
        // Clear contacts.
        int clear_contacts(void);
        // Display entire contact list.
        int display(void);

    private:
        // Add contact recursively.
        int add_contact(contact * & current, const contact & to_add);
        // Remove contact recursively.
        int remove_contact(contact * & current, contact * & parent, const contact & to_remove);
        // Clear contacts recursively.
        int clear_contacts(contact * & current);
        // Display entire contact list recursively.
        int display(contact * & current);
        // Find a contact recursively.
        int find_contact(contact * & current, const cpp_string & to_find, contact * & result);
        // Copy contacts from another list recursively.
        int copy_contacts(contact * & current, contact & other_current);
        // Get the inorder successor of the current contact.
        int inorder_successor(contact * & current, contact * & parent, contact * & result);

        contact * contacts; // lll of contacts
};

#endif
