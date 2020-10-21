/* Alana Gilston - 10/7/2020 - CS163 - Program 1
 * priority_manager.cpp
 *
 * This is the header file for the "priority manager" class. The "priority 
 * manager" class manages the entire project structure, including priorities
 * and their corresponding project lists.
 */
#include <iostream>
#include <cstring>
#include "priority_manager.h"
#include "utils.h"

using namespace std;



priority_manager::priority_manager(void) {
    first_priority = nullptr;
}



priority_manager::~priority_manager(void) {
    clear();
}



// Add a priority.
// INPUT:
//  new_priority: The new priority name to add
// OUTPUT:
//  Returns 0 if the priority is a duplicate or the add fails and 1 if it succeeds.
int priority_manager::add(const char * new_priority) {
    if(!new_priority || char_array_empty(new_priority)) return 0;

    priority_node * node = new priority_node;
    node->priority = nullptr;
    copy_char_array(node->priority, new_priority);
    node->next = nullptr;

    if(!first_priority) {
        first_priority = node;
    }
    else {
        priority_node * current = first_priority;

        while(current->next) {
            current = current->next;
        }

        current->next = node;
        current = current->next;
    }

    return 1;
}



// Remove a priority.
// INPUT:
//  priority: The priority to remove
// OUTPUT:
//  Returns 0 if the priority does not exist or the removal fails and 1 if it succeeds.
int priority_manager::remove(const char * priority) {
    priority_node * found = nullptr;
    priority_node * previous = nullptr;

    // find() already checks for null head, invalid priority, and null found
    if(!find(priority, found, previous)) return 0;

    if(!previous) {
        first_priority = first_priority->next;
    }
    else {
        previous->next = found->next;
    }

    delete [] found->priority;
    delete found;
    found = nullptr;
    return 1;
}



// Add a project to a priority.
// INPUT:
//  priority: The priority to add to
//  new_project: The project to add
// OUTPUT:
//  Returns 0 if the add fails due to missing data or 1 if it succeeds.
int priority_manager::add_project(const char * priority, const project & new_project) {
    priority_node * found = nullptr;

    if(!find(priority, found)) return 0;

    found->projects.add(new_project);
    return 1;
}



// Remove a project from a priority.
// INPUT:
//  priority: The priority to remove from
//  project_name: The project to remove
// OUTPUT:
//  Returns 0 if the project does not exist or removal fails and 1 if it succeeds.
int priority_manager::remove_project(const char * priority, const char * project_name) {
    priority_node * found = nullptr;

    if(!find(priority, found)) return 0;

    return found->projects.remove(project_name);
}



// Remove all priorities and their projects.
// OUTPUT:
//  Returns the number of priorities removed.
int priority_manager::clear(void) {
    if(!first_priority) return 0;

    priority_node * temp = nullptr;
    int count = 0;

    while(first_priority) {
        temp = first_priority->next;
        delete [] first_priority->priority;
        delete first_priority;
        first_priority = temp;
        ++count;
    } 

    first_priority = nullptr;
    return count;
}



// Display all the priorities.
// OUTPUT:
//  Returns the number of priorities displayed.
int priority_manager::display(void) const {
    if(!first_priority) return 0;

    priority_node * current = first_priority;
    int count = 0;
    int project_count = 0;

    cout << "Priorities:" << endl;

    while(current) {
        cout << current->priority;
       
        project_count = current->projects.count();

        if(project_count > 0)
            cout << " (" << project_count << " projects)" << endl;
        else
            cout << endl;

        current = current->next;
        ++count;
    }

    cout << endl;

    return count;
}



// Display one priority with its projects.
// INPUT:
//  priority: The priority to display
// OUTPUT:
//  Returns the number of projects displayed in the priority.
int priority_manager::display(const char * priority) const {
    priority_node * found = nullptr;

    if(!find(priority, found)) return 0;

    return found->projects.display(); 
}



// Check if the priorities list is empty.
// OUTPUT:
//  Returns 0 if the list has data and 1 if it is empty.
int priority_manager::is_empty(void) const {
    return first_priority != nullptr;
}



// Check if a priority's project list is empty.
// INPUT:
//  priority: The priority to check
// OUTPUT:
//  Returns 0 if the priority has data or 1 if it is empty.
int priority_manager::is_priority_empty(const char * priority) const {
    priority_node * found = nullptr;

    if(!find(priority, found)) return 0;

    return found->projects.is_empty();
}



// Check if a priority exists.
// INPUT:
//  priority: The priority to check for
// OUTPUT:
//  Returns 0 if the priority does not exist or 1 if it does.
int priority_manager::contains(const char * priority) const {
    priority_node * found = nullptr;
    return find(priority, found); 
}



// Check if a priority contains a project.
// INPUT:
//  priority: The priority to search
//  project_name: The project to search for
// OUTPUT:
//  Returns 0 if the project is not found and 1 if it is found.
int priority_manager::contains_project(const char * priority, const char * project_name) const {
    priority_node * found = nullptr;

    if(!find(priority, found)) return 0;
    return found->projects.contains(project_name);
}



// Find a priority node.
// INPUT:
//  priority: The priority to search for
//  result: The node to store the found priority
// OUTPUT:
//  Returns 0 if the node is not found or 1 if it is found.
int priority_manager::find(const char * priority, priority_node * & result) const {
    priority_node * previous = nullptr;
    return find(priority, result, previous);
}



// Find a priority node and capture its previous node.
// INPUT:
//  priority: The priority to search for
//  result: The node to store the found priority
//  previous: The node to store the previous priority
// OUTPUT:
//  Returns 0 if the priority is not found and 1 if it is found.
int priority_manager::find(const char * priority, priority_node * & result, 
        priority_node * & previous) const {
    if(!first_priority || !priority || char_array_empty(priority)) return 0;

    priority_node * current = first_priority;

    while(current && strcmp(current->priority, priority) != 0) {
        previous = current;
        current = current->next;
    }

    result = current;
    return result != nullptr;
}
