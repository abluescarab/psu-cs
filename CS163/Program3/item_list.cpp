/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item_list.cpp
 * 
 * This is the implementation file for the "item list" class. The "item list"
 * class is one that manages a list of scavenger hunt items in the order they 
 * should be found using a circular linked list.
 */
#include "item_list.h"



item_list::item_list(void) {
    rear = nullptr;
}



item_list::~item_list(void) {

}



// Enqueue a new item.
// INPUT:
//  new_item: Item to add to the queue
// OUTPUT:
//  0 if missing data
//  1 if enqueue succeeds
int item_list::enqueue(const item & new_item) {
    item_node * new_node = new node;
    new_node->this_item.copy_from(new_item);
    new_node->next = nullptr;

    if(!rear) {
        rear = new_node;
        rear->next = rear;
        return 1;
    }

    item_node * temp = rear->next;
    rear->next = new_node;
    rear = rear->next;
    rear->next = temp;
    return 1;
}



// Dequeue an item.
// OUTPUT:
//  0 if the list is empty
//  1 if dequeue succeeds
int item_list::dequeue(void) {
    if(!rear) return 0;

    if(rear->next == rear) {
        delete rear;
        rear = nullptr;
    }
    else {
        item_node * temp = rear->next->next;
        delete rear->next;
        rear->next = temp;
    }

    return 1;
}



// Peek at the last item.
// INPUT:
//  result: Item to return
// OUTPUT:
//  0 if list is empty
//  1 if item is returned
int item_list::peek(item & result) const {

}



// Clear the list.
// OUTPUT:
//  Returns number of items cleared.
int item_list::clear(void) {
    
}


// Check if the list is empty.
// OUTPUT:
//  0 if list has data
//  1 if list is empty
int item_list::is_empty(void) const {
    return rear == nullptr;
}



// Display the contents of the list.
// OUTPUT:
//  Returns number of items displayed
int item_list::display(void) const {

}
