/* Alana Gilston - 11/2/2020 - CS163 - Program 3
 * item_queue.h
 *
 * This is the header file for the "item queue" class. The "item queue" class is 
 * one that manages a queue of scavenger hunt items in the order they should be
 * found using a circular linked list.
 */
#ifndef ITEM_QUEUE_H
#define ITEM_QUEUE_H

struct queue_node {
    char * item_name;
    queue_node * next;
};

class item_queue {
    public:
        item_queue(void);
        ~item_queue(void);
        // Enqueue a new item.
        int enqueue(const char * new_item);
        // Dequeue an item.
        int dequeue(void);
        // Peek at the last item.
        int peek(char * & result) const;
        // Clear the queue.
        int clear(void);
        // Check if the queue is empty.
        int is_empty(void) const;
        // Display the contents of the queue.
        int display(void) const;

    private:
        queue_node * rear;
};

#endif
