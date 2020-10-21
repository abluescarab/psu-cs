/* Alana Gilston - 10/7/2020 - CS163 - Program 1
 * project_list.cpp
 *
 * This is the implementation file for the "project list" class. The "project
 * list" class is one that manages a linked list of projects, with functions to
 * add and remove projects from the list, as well as display them to the user. 
 */
#include <iostream>
#include <cstring>
#include "project_list.h"
#include "utils.h"

using namespace std;




project_list::project_list(void) {
    head = nullptr;
}



project_list::~project_list(void) {
    clear();
}



// Add a project to the list.
// INPUT:
//  add_project: Project to add to the list
// OUTPUT:
//  Returns 0 if there is missing data or the add failed; 1 if it succeeded.
int project_list::add(const project & add_project) {
    if(add_project.is_empty()) return 0;

    project_node * node = new project_node;
    node->this_project.copy_from(add_project);
    node->next = nullptr;

    if(!head) {
        head = node;
    }
    else {
        project_node * current = head;

        while(current->next) {
            current = current->next;
        }

        current->next = node;
        current = current->next;
    }

    return 1;    
    
}



// Remove a project from the list.
// INPUT:
//  project_name: The name of the project to remove
// OUTPUT:
//  Returns 0 if the removal failed and 1 if the removal succeeded.
int project_list::remove(const char * project_name) {
    project_node * current = head;
    project_node * temp = nullptr;

    if(head->this_project.is_match(project_name)) {
        temp = head->next;
        delete head;
        head = temp;
        return 1;
    }

    while(current) {
        // compare the provided name with the current project's name
        if(current->this_project.is_match(project_name)) {
            temp->next = current->next; 
            delete current;
            return 1;
        }

        temp = current;
        current = current->next;
    }

    return 0;
    
}



// Clear the list.
// OUTPUT:
//  Returns the number of projects removed.
int project_list::clear(void) {
    if(!head) return 0; 

    int count = 0;
    project_node * temp = nullptr;

    while(head) {
        temp = head->next;
        delete head;
        head = temp;
        ++count;
    }

    head = nullptr;

    return count;
    
}



// Display the list.
// OUTPUT:
//  Returns the number of projects displayed.
int project_list::display(void) const {
    if(!head) return 0;

    int count = 0;
    project_node * current = head;

    while(current) {
        current->this_project.display();
        ++count;
        current = current->next;
    }    

    return count;

}



// Get the number of projects in the list.
// OUTPUT:
//  Returns the number of projects in the list.
int project_list::count(void) const {
    int count = 0;
    project_node * current = head;

    while(current) {
        ++count;
        current = current->next;
    }

    return count;
}


// Check if the list contains a project.
// INPUT:
//  project_name: The project to search for
// OUTPUT:
//  Returns 0 if not found and 1 if found.
int project_list::contains(const char * project_name) const {
    project_node * current = head;

    while(current && !current->this_project.is_match(project_name)) {
        current = current->next;
    }

    return current != nullptr;
}



// Check if the list is empty.
// OUTPUT:
//  Returns 0 if the list has data and 1 if the list is empty.
int project_list::is_empty(void) const {
    return head == nullptr;
}
