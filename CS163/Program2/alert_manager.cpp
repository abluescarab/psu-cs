/* Alana Gilston - 10/15/2020 - CS163 - Program 2
 * alert_manager.cpp
 *
 * This is the implementation file for the "alert manager" class. The "alert 
 * manager" class is one that manages the entire project structure, including 
 * the stack of alerts.
 */
#include <iostream>
#include "alert_manager.h"
#include "utils.h"

using namespace std;



alert_manager::alert_manager(void) {
    head = nullptr;
}



alert_manager::~alert_manager(void) {
    clear();
}



// Push a new alert to the head of the stack.
// INPUT:
//  new_alert: The alert to push onto the stack
// OUTPUT:
//  0 if the push failed
//  1 if the push succeeded
int alert_manager::push(const alert & new_alert) {
    if(new_alert.is_empty()) return 0;

    alert_node * temp = nullptr;

    // create a new node if there are no nodes or the last is full
    if(!head || top_index == ARRAY_MAX) {
        temp = new alert_node;
        temp->alerts = new alert[ARRAY_MAX];
        // if head is null, will set to null
        // if head is not null, will set to head
        temp->next = head;
        temp->alerts[0].copy_from(new_alert);
        top_index = 1;

        head = temp;
    }
    else {
        head->alerts[top_index].copy_from(new_alert);
        ++top_index;
    }

    return 1;
}



// Pop the last alert off the stack.
// INPUT:
//  date: Date the alert was broadcast
//  time: Time the alert was broadcast
// OUTPUT:
//  0 if the last alert failed to pop
//  1 if the pop succeeded
int alert_manager::pop(void) {
    if(!head) return 0;

    alert_node * temp = head->next;

    // delete the last node if it is empty
    if(top_index == 0) {
        delete [] head->alerts;
        delete head;
        head = temp;
        top_index = ARRAY_MAX;
    }

    --top_index;
    return 1;
}



// Clear the stack.
// OUTPUT:
//  Returns the number of alerts cleared
int alert_manager::clear(void) {
    if(!head) return 0;

    alert_node * temp = nullptr;
    int count = 0;

    while(head) {
        count += count_in_node(head);
        temp = head->next;
        delete [] head->alerts;
        delete head;
        head = temp;
    }

    top_index = 0;
    head = nullptr;

    return count;
}



// Peek at the head alert on the stack.
// INPUT:
//  result: The alert to return
// OUTPUT:
//  0 if the stack is empty
//  1 if the peek succeeded
int alert_manager::peek(alert & result) const {
    // top_index is one after the last filled, so it should never be 0 if
    // we have only one node
    if(!head || (!head->next && top_index == 0)) return 0;

    if(top_index == 0)
        result.copy_from(head->next->alerts[ARRAY_MAX - 1]);
    else 
        result.copy_from(head->alerts[top_index - 1]);

    return 1;
}


// Check if the stack is empty.
// OUTPUT:
//  0 if the stack has alerts
//  1 if the stack is empty
int alert_manager::is_empty(void) const {
    return head == nullptr;
}



// Display the contents of the stack.
// OUTPUT:
//  Returns the number of alerts displayed
int alert_manager::display(void) const {
    if(!head) return 0;

    alert_node * temp = head;
    // start from the final filled index
    int final_index = top_index - 1;
    int count = 0;

    while(temp) {
        // loop through the array backwards, since we fill first-to-last
        for(int i = final_index; i >= 0; --i) {
            temp->alerts[i].display();
            cout << endl;
            ++count;
        }

        // then set the final_index to the last index possible
        final_index = ARRAY_MAX - 1;
        temp = temp->next;
    }

    return count;
}



// Count the number of non-empty elements in a node.
// INPUT:
//  node: The node to count in
// OUTPUT:
//  Returns the number of non-empty elements in the node.
int alert_manager::count_in_node(alert_node * & node) const {
    if(!node) return 0;

    int count = 0;

    for(int i = 0; i < ARRAY_MAX; ++i) {
        if(!node->alerts[i].is_empty())
            ++count;
    }

    return count;
}
