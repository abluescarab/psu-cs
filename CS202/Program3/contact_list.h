/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * contact_list.h
 *
 *
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
        friend std::istream & operator>>(std::istream & in, contact & in_contact);
        friend std::ostream & operator<<(std::ostream & out, const contact & out_contact);

        // Set the left contact.
        int set_left(const contact & new_left);
        // Set the right contact.
        int set_right(const contact & new_right);
        // Get the left contact.
        contact * & get_left(void);
        // Get the right contact.
        contact * & get_right(void);
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
        // Check if the contact matches another contact.
        int matches(const contact & other_contact) const;
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
        int add_contact(const contact & to_add);
        // Remove contact.
        int remove_contact(const contact & to_remove);
        // Clear contacts.
        int clear_contacts(void);
        // Display entire contact list.
        int display(void);

    private:
        // Add contact recursively.
        int add_contact(contact * & current, const contact & to_add);
        // Remove contact recursively.
        int remove_contact(contact * & current, const contact & to_remove);
        // Clear contacts recursively.
        int clear_contacts(contact * & current);
        // Display entire contact list recursively.
        int display(contact * & current);
        // Find a contact.
        int find_contact(const contact & to_find, contact * & result);
        // Copy contacts from another list recursively.
        int copy_contacts(contact * & current, contact & other_current);

        contact * contacts; // lll of contacts
};

#endif
