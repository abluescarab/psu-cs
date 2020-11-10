/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item_table.h
 *
 * This is the header file for the "item table" class. The "item table"
 * class is one that manages a hash table with a list of items available
 * for a scavenger hunt.
 */
#ifndef item_table_H
#define item_table_H

#include "item.h"
#define SIZE 301
#define MAX_STREAM_SIZE 1024

struct table_node {
    item this_item;
    table_node * next;
};

class item_table {
    public:
        item_table(void);
        ~item_table(void);
        // Load items from an external file.
        int load_items(const char * filename);
        // Add an item to the table.
        int add(const char * name, const char * location, const char * hint);
        // Remove an item from the table by name.
        int remove(const char * item_name);
        // Find and return an item in the hash table by name.
        int find(const char * item_name, item & result) const;
        // Remove all items from the table.
        int clear(void);
        // Display all information from an item matching a key.
        int display(const char * item_name) const;
        // Display the contents of the table.
        int display_all(void) const;

    private:
        // Hash the name of an item and return its hashed value.
        int hash_name(const char * item_name) const;
        // Find a node by item name.
        int find(const char * item_name, table_node * & result) const;
        // Find a node by item name with a previous pointer.
        int find(const char * item_name, table_node * & previous, table_node * & result) const;

        table_node ** table; // the array of table nodes
};

#endif
