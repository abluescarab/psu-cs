/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * scavenger_hunt.h
 *
 * This is the header file for the "scavenger hunt" class. The "scavenger hunt"
 * class is one that manages a scavenger hunt with a list of items available
 * and a queue with the items in the order they must be found.
 */
#ifndef SCAVENGER_HUNT_H
#define SCAVENGER_HUNT_H
#define SIZE 1001
#include "item.h"

struct table_node {
    item this_item;
    table_node * next;
};

class scavenger_hunt {
    public:
        scavenger_hunt(void);
        ~scavenger_hunt(void);
        // Load items from an external file.
        int load_items(const char * file_name);
        // Add an item to the table.
        int add(const item & item);
        // Remove an item from the table by name.
        int remove(const char * item_name);
        // Find and return an item in the hash table by name.
        int find(const char * item_name, item * & result) const;
        // Remove all items from the table.
        int clear(void);
        // Display all information from an item matching a key.
        int display(const char * item_name) const;
        // Display the contents of the table.
        int display_all(void) const;

    private:
        // Hash the name of an item and return its hashed value.
        int hash(const char * item) const;

        hash_node ** table; // the array of table nodes
};

#endif
