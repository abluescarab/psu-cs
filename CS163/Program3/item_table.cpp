/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item_table.cpp
 *
 * This is the header file for the "item table" class. The "item table"
 * class is one that manages a hash table with a list of items available
 * for a scavenger hunt.
 */
#include <iostream>
#include <limits>
#include <fstream>
#include <cmath>
#include <cstring>
#include "item_table.h"

using namespace std;



item_table::item_table(void) {
    table = new table_node*[SIZE];

    for(int i = 0; i < SIZE; ++i)
        table[i] = nullptr;
}



item_table::~item_table(void) {
    clear();
}



// Load items from an external file.
// INPUT:
//  filename: The file name to read from
// OUTPUT:
//  -1 if the file does not exist
//  Otherwise returns the number of items added
int item_table::load_items(const char * filename) {
    size_t stream_size = numeric_limits<streamsize>::max();
    char * name = new char[MAX_STREAM_SIZE];
    char * location = new char[MAX_STREAM_SIZE];
    char * hint = new char[MAX_STREAM_SIZE];
    int count = 0;
    ifstream file(filename);

    if(!file.is_open()) return -1;

    while(!file.eof()) {
        file.get(name, stream_size, '|');
        file.ignore(stream_size, '|');
        file.get(location, stream_size, '|');
        file.ignore(stream_size, '|');
        // get the rest of the line as the hint, throwing away newline character
        file.getline(hint, stream_size);

        if(add(name, location, hint))
            ++count;
    }

    file.close();
    delete name;
    delete location;
    delete hint;
    return count;
}



// Add an item to the table.
// INPUT:
//  name: The name of the item to add
//  location: The location of the item to add
//  hint: The hint for the item to add
// OUTPUT:
//  0 if the item is missing data or failed to add
//  1 if the item was added successfully
int item_table::add(const char * name, const char * location, const char * hint) {
    int hash = hash_name(name);
    table_node * temp = table[hash];
    table_node * new_node = new table_node;
    new_node->next = nullptr;

    if(!new_node->this_item.copy_from(name, location, hint)) {
        delete new_node;
        return 0;
    }

    if(!table[hash])
        table[hash] = new_node;
    else {
        while(temp->next)
            temp = temp->next;

        temp->next = new_node;
    }

    return 1;
}



// Remove an item from the table by name.
// INPUT:
//  item_name: The item name to remove
// OUTPUT:
//  0 if the item does not exist or failed to remove
//  1 if the item was removed successfully
int item_table::remove(const char * item_name) {
    table_node * result = nullptr;
    table_node * previous = nullptr;
    table_node * next = nullptr;

    if(!find(item_name, previous, result))
        return 0;

    next = result->next;
    delete result;
    previous->next = next;
    return 1;
}



// Find and return an item in the hash table by name.
// INPUT:
//  item_name: The item name to find
//  result: The item to return
// OUTPUT:
//  0 if the item was not found
//  1 if the item was found
int item_table::find(const char * item_name, item & result) const {
    table_node * node = nullptr;

    if(!find(item_name, node))
        return 0;

    result.copy_from(node->this_item);
    return 1;
}



// Remove all items from the table.
// OUTPUT:
//  Returns the number of items removed
int item_table::clear(void) {
    if(!table) return 0;

    int count = 0;

    for(int i = 0; i < SIZE; ++i) {
        table_node * temp = table[i];

        if(temp) {
            while(temp->next) {
                table_node * next = temp->next;
                delete temp;
                temp = next;
                ++count;
            }
        }
    }

    return count;
}



// Display all information from an item matching a key.
// INPUT:
//  item_name: The item name to display
// OUTPUT:
//  0 if the item failed to display
//  1 if the item displayed successfully
int item_table::display(const char * item_name) const {
    table_node * node = nullptr;

    if(!find(item_name, node))
        return 0;

    return node->this_item.display();
}



// Display the contents of the table.
// OUTPUT:
//  Returns the number of items displayed
int item_table::display_all(void) const {
    int count = 0;
    int chains = 0;
    int chain_elements = 0;
    int largest_chain = 0;

    for(int i = 0; i < SIZE; ++i) {
        chain_elements = 0;
        table_node * temp = table[i];
    
        if(temp) {
            cout << "CHAIN " << i << ": " << endl;
            ++chains;
        }

        while(temp) {
            count += temp->this_item.display();
            cout << endl;
            temp = temp->next;
            ++chain_elements;
        }

        if(chain_elements > largest_chain)
            largest_chain = chain_elements;
    }

    cout << "Number of chains with data: " << chains << endl;
    cout << "Largest chain was: " << largest_chain << endl;
    return count;
}



// Hash the name of an item and return its hashed value.
// INPUT:
//  item_name: The item name to hash
// OUTPUT:
//  Returns the hashed item name
int item_table::hash_name(const char * item_name) const {
    int hash = 0;
    int length = strlen(item_name);

    for(int i = 0; i < length; ++i)
        hash += abs(item_name[i] + 1) * (i + 1);

    return hash % SIZE;
}



// Find a node by item name.
// INPUT:
//  item_name: The item name to find
//  result: The node to return
// OUTPUT:
//  0 if not found
//  1 if found
int item_table::find(const char * item_name, table_node * & result) const {
    table_node * previous = nullptr;
    return find(item_name, previous, result);
}



// Find a node by item name with a previous pointer.
// INPUT:
//  item_name: The item name to find
//  previous: A pointer to the previous node
//  result: The result of the search
// OUTPUT:
//  0 if the node was not found
//  1 if the node was found
int item_table::find(const char * item_name, table_node * & previous, table_node * & result) const {
    if(!table) return 0;

    int hash = hash_name(item_name);
    int found = 0;
    table_node * temp = table[hash];

    if(!temp)
        return 0;

    if(temp->this_item.is_match(item_name)) {
        previous = nullptr;
        result = temp;
        found = 1;
    }

    while(temp->next && !found) {
        if(temp->next->this_item.is_match(item_name)) {
            previous = temp;
            result = temp->next;
            found = 1;
        }

        temp = temp->next;
    }

    return found;
}
