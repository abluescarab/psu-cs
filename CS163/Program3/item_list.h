/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item_list.h
 *
 * This is the header file for the "item list" class. The "item list" class is 
 * one that manages a list of scavenger hunt items in the order they should be
 * found using a circular linked list.
 */
#ifndef ITEM_LIST_H
#define ITEM_LIST_H
#include "item.h"

struct item_node {
    item this_item;
    item_node * next;
};

class item_list {
    public:
        item_list(void);
        ~item_list(void);
        // Enqueue a new item.
        int enqueue(const item & new_item);
        // Dequeue an item.
        int dequeue(void);
        // Peek at the last item.
        int peek(item & result) const;
        // Check if the list is empty.
        int is_empty(void) const;
        // Display the contents of the list.
        int display(void) const;

    private:
        item_node * rear;
};

#endif
