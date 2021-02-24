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
        contact(const cpp_string & new_name, const cpp_string & new_address);
        contact(const contact & other_contact);
        ~contact(void);

        // operators
        contact & operator=(const contact & source);
        bool operator==(const contact & source) const;
        // relational
        friend bool operator<(const contact & original, const contact & compare);
        friend bool operator<=(const contact & original, const contact & compare);
        friend bool operator>(const contact & original, const contact & compare);
        friend bool operator>=(const contact & original, const contact & compare);
        // i/o
        friend std::istream & operator>>(std::istream & in, contact & in_contact);
        friend std::ostream & operator<<(std::ostream & out, const contact & out_contact);

        // Set the left contact.
        int set_left(contact & new_left);
        // Set the right contact.
        int set_right(contact & new_right);
        // Get the left contact.
        contact * & get_left(void);
        // Get the right contact.
        contact * & get_right(void);
        // Copy the data from another contact, minus left and right.
        int copy(const contact & copy_from);
        // Change the contact's name.
        int change_name(const cpp_string & new_name);
        // Change the contact's address.
        int change_address(const cpp_string & new_address);
        // Add a device.
        int add_device(const device & to_add);
        // Remove a device.
        int remove_device(const device & to_remove);
        // Clear all devices.
        int clear_devices(void);
        // Check if the contact has data.
        int is_empty(void) const;
        // Display the contact information.
        int display(void);

    private:
        // Add a device recursively.
        int add_device(device * & current, const device & to_add);
        // Remove a device recursively.
        int remove_device(device * & current, const device & to_remove);
        // Clear devices recursively.
        int clear_devices(device * & current);
        // Copy devices recursively.
        int copy_devices(device * & current, device & other_current);
        // Display devices recursively.
        int display_devices(device * & current);

        cpp_string name; // contact name
        cpp_string address; // contact home address
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
        int add(const contact & to_add);
        // Remove contact.
        int remove(const contact & to_remove);
        // Clear contacts.
        int clear(void);
        // Display entire contact list.
        int display(void);

    private:
        // Add contact recursively.
        int add(contact * & current, const contact & to_add);
        // Remove contact recursively.
        int remove(contact * & current, const contact & to_remove);
        // Clear contacts recursively.
        int clear(contact * & current);
        // Display entire contact list recursively.
        int display(contact * & current);
        // Find a contact.
        int find(contact * & current, const contact & to_find, contact * & result);
        // Copy contacts from another list recursively.
        int copy_contacts(contact * & current, contact & other_current);
        // Get the inorder successor of the current contact.
        int inorder_successor(contact * & current, contact * & result);

        contact * contacts; // lll of contacts
};

#endif
