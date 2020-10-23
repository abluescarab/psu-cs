/* Alana Gilston - 10/15/2020 - CS163 - Program 2
 * alert_manager.h
 *
 * This is the header file for the "alert manager" class. The "alert manager" class is one
 * that manages the entire project structure, including the stack of alerts.
 */
#ifndef ALERT_MANAGER_H
#define ALERT_MANAGER_H
#define ARRAY_MAX 5
#include "alert.h"

struct alert_node {
    alert * alerts;
    alert_node * next;
};

class alert_manager {
    public:
        alert_manager(void);
        ~alert_manager(void);
        // Push a new alert to the top of the stack.
        int push(const alert & new_alert);
        // Pop the last alert off the stack.
        int pop(void);
        // Clear the stack.
        int clear(void);
        // Peek at the top alert on the stack.
        int peek(alert & result) const;
        // Check if the stack is empty.
        int is_empty(void) const;
        // Display the contents of the stack.
        int display(void) const;

    private:
        // Count the number of non-empty elements in a node.
        int count_in_node(alert_node * & node) const;
        alert_node * head;        
        int top_index;
};

#endif
