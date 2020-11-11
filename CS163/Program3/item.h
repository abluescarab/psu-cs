/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item.h
 *
 * This is the header file for the "item" class. The "item" class is 
 * one that manages data members for a scavenger hunt item read from a file.
 */
#ifndef ITEM_H
#define ITEM_H

enum item_data {
    name,
    location,
    hint
};

class item {
    public:
        item(void);
        ~item(void);
        // Copy item data from multiple arguments.
        int copy_from(const char * new_name, const char * new_location, const char * new_hint);
        // Copy the item data from another item.
        int copy_from(const item & item_to_copy);
        // Display the content of the item.
        int display(void) const;
        // Display only a certain element of the item.
        int display(const item_data data) const;
        // Check if the item has no data.
        int is_empty(void) const;
        // Check if the item is a match to the provided title.
        int is_match(const char * item_name) const;

    private:
        char * name; // the name of the item
        char * location; // the location where the item can be found
        char * hint; // a hint to find the item
};

#endif
