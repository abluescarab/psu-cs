/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item.cpp
 *
 * This is the implementation file for the "item" class. The "item" class is 
 * one that manages data members for a scavenger hunt item read from a file.
 */
#include <cstring>
#include <iostream>
#include "item.h"
#include "utils.h"

using namespace std;



item::item(void) {
    name = nullptr;
    location = nullptr;
    hint = nullptr;
}



item::~item(void) {
    delete [] name;
    name = nullptr;
    delete [] location;
    location = nullptr;
    delete [] hint;
    hint = nullptr;
}



// Copy item data from multiple arguments.
// INPUT:
//  new_name: Name to copy
//  new_location: Location to copy
//  new_hint: Hint to copy
// OUTPUT:
//  0 if the data is missing or empty
//  1 if the data is copied successfully
int item::copy_from(const char * new_name, const char * new_location, const char * new_hint) {
    if(char_array_empty(new_name) ||
       char_array_empty(new_location) ||
       char_array_empty(new_hint))
        return 0;

    copy_char_array(name, new_name);
    copy_char_array(location, new_location);
    copy_char_array(hint, new_hint);
    return 1;
}



// Copy the item data from another item.
// INPUT:
//  item_to_copy: Item from copy from
// OUTPUT:
//  0 if item is missing data or copy fails
//  1 if copy succeeds
int item::copy_from(const item & item_to_copy) {
    if(item_to_copy.is_empty()) return 0;

    copy_char_array(name, item_to_copy.name);
    copy_char_array(location, item_to_copy.location);
    copy_char_array(hint, item_to_copy.hint);
    return 1;
}



// Display the content of the item.
// OUTPUT:
//  0 if the item is missing data or failed to display
//  1 if the item displayed successfully
int item::display(void) const {
    if(is_empty()) return 0;

    display(item_data::name);
    display(item_data::location);
    display(item_data::hint);
    return 1;
}



// Display only a certain element of the item.
// INPUT:
//  data: The data of the item to display
// OUTPUT:
//  0 if the data passed was invalid or the item is empty
//  1 if the data displayed correctly
int item::display(const item_data data) const {
    if(is_empty()) return 0;

    switch(data) {
        case item_data::name:
            cout << name;
            break;
        case item_data::location:
            cout << location;
            break;
        case item_data::hint:
            cout << hint;
            break;
        default:
            return 0;
    }

    cout << endl;
    return 1;
}



// Check if the item has no data.
// OUTPUT:
//  0 if the item has data
//  1 if the item is empty
int item::is_empty(void) const {
    return char_array_empty(name) ||
           char_array_empty(location) ||
           char_array_empty(hint);
}



// Check if the item is a match to the provided title.
// INPUT:
//  item_name: Name to compare to
// OUTPUT:
//  0 if item is not a match
//  1 if item is a match
int item::is_match(const char * item_name) const {
    return strcmp(name, item_name) == 0; 
}
