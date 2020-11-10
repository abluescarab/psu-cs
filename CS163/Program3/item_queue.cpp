/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item_queue.cpp
 * 
 * This is the implementation file for the "item queue" class. The "item queue"
 * class is one that manages a queue of scavenger hunt items in the order they 
 * should be found using a circular linked list.
 */
#include <iostream>
#include <cstring>
#include "item_queue.h"
#include "utils.h"

using namespace std;



item_queue::item_queue(void) {
    rear = nullptr;
}



item_queue::~item_queue(void) {
    clear();
}



// Enqueue a new item.
// INPUT:
//  new_item: Item to add to the queue
// OUTPUT:
//  0 if missing data
//  1 if enqueue succeeds
int item_queue::enqueue(const char * new_item) {
    if(char_array_empty(new_item)) return 0;

    queue_node * new_node = new queue_node;
    new_node->item_name = nullptr;

    copy_char_array(new_node->item_name, new_item);
    new_node->next = nullptr;

    if(!rear) {
        rear = new_node;
        rear->next = rear;
    }
    else {
        queue_node * temp = rear->next;
        rear->next = new_node;
        rear = rear->next;
        rear->next = temp;
    }

    return 1;
}



// Dequeue an item.
// OUTPUT:
//  0 if the queue is empty
//  1 if dequeue succeeds
int item_queue::dequeue(void) {
    if(!rear) return 0;

    if(rear->next == rear) {
        delete rear;
        rear = nullptr;
    }
    else {
        queue_node * temp = rear->next->next;
        delete rear->next;
        rear->next = temp;
    }

    return 1;
}



// Peek at the last item.
// INPUT:
//  result: Item to return
// OUTPUT:
//  0 if queue is empty
//  1 if item is returned
int item_queue::peek(char * & result) const {
    if(!rear) {
        delete result;
        result = nullptr;
        return 0;
    }

    copy_char_array(result, rear->next->item_name);
    return 1;
}



// Clear the queue.
// OUTPUT:
//  Returns number of items cleared.
int item_queue::clear(void) {
    if(!rear) return 0;

    int count = 0;
    queue_node * temp = rear->next;
    rear->next = nullptr;

    while(temp) {
        rear = temp->next;
        delete rear;
        temp = rear;
        ++count;
    }

    return count;
}


// Check if the queue is empty.
// OUTPUT:
//  0 if queue has data
//  1 if queue is empty
int item_queue::is_empty(void) const {
    return rear == nullptr;
}



// Display the contents of the queue.
// OUTPUT:
//  Returns number of items displayed
int item_queue::display(void) const {
    if(!rear) return 0;

    int count = 1;
    queue_node * temp = rear->next;

    do {
        cout << temp->item_name << endl;
        temp = temp->next;
        ++count;
    } while(temp != rear->next);

    return count;
}
