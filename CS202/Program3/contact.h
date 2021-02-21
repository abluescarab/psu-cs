/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * contact.h
 *
 *
 */
#ifndef CONTACT_H
#define CONTACT_H

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
        contact * & get_left(void) const;
        // Get the right contact.
        contact * & get_right(void) const;
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
        cpp_string name; // contact name
        cpp_string address; // contact home address
        device * devices; // devices the contact owns
        contact * left; // left contact in the tree
        contact * right; // right contact in the tree
};

#endif
