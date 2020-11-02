/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item.h
 *
 * This is the header file for the "item" class. The "item" class is 
 * one that manages data members for a scavenger hunt item read from a file.
 */
#ifndef ITEM_H
#define ITEM_H

class item {
    public:
        item(void);
        ~item(void);
        // Copy the item data from another item.
        int copy_from(const item & item_to_copy);
        // Display the content of the item.
        int display(void) const;
        // Check if the item has no data.
        int is_empty(void) const;
        // Check if the item is a match to the current data.
        int is_match(const char * name, const char * location, const char * hint) const;

    private:
        char * name; // the name of the item
        char * location; // the location where the item can be found
        char * hint; // a hint to find the item
};

#endif
