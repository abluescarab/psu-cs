/* Alana Gilston - 10/7/2020 - CS163 - Program 1
 * project_list.h
 *
 * This is the header file for the "project list" class. The "project list"
 * class is one that manages a linked list of projects, with functions to
 * add and remove projects from the list, as well as display them to the user. 
 */
#ifndef PROJECT_LIST_H
#define PROJECT_LIST_H
#include "project.h"

struct project_node {
    project this_project;
    project_node * next;
};

class project_list {
    public:
        project_list(void);
        ~project_list(void);
        // Add a project to the list.
        int add(const project & add_project);
        // Remove a project from the list.
        int remove(const char * project_name);
        // Clear the list.
        int clear(void);
        // Display the list.
        int display(void) const;
        // Get the number of projects in the list.
        int count(void) const;
        // Check if the list contains a project.
        int contains(const char * project_name) const;
        // Check if the list is empty.
        int is_empty(void) const;

    private:
        project_node * head; // the first project in the list
};

#endif
