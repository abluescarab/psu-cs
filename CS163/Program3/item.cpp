/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item.cpp
 *
 * This is the implementation file for the "item" class. The "item" class is 
 * one that manages data members for a scavenger hunt item read from a file.
 */
#include "item.h"



item(void) {

}



~item(void) {

}



// Copy the item data from another item.
// INPUT:
//  item_to_copy: Item from copy from
// OUTPUT:
//  0 if item is missing data or copy fails
//  1 if copy succeeds
int copy_from(const item & item_to_copy) {

}



// Display the content of the item.
// OUTPUT:
//  Returns number of elements displayed
int display(void) const {

}



// Check if the item has no data.
// OUTPUT:
//  0 if the item has data
//  1 if the item is empty
int is_empty(void) const {

}



// Check if the item is a match to the current data.
// INPUT:
//  name: Name to compare
//  location: Location to compare
//  hint: Hint to compare
// OUTPUT:
//  0 if item is not a match
//  1 if item is a match
int is_match(const char * name, const char * location, const char * hint) const {

}
