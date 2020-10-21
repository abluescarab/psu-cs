/* Alana Gilston - 10/7/2020 - CS163 - Program 1
 * priority_manager.h
 *
 * This is the header file for the "priority manager" class. The "priority
 * manager" class manages the entire project structure, including priorities
 * and their corresponding project lists.
 */
#ifndef priority_manager_H
#define priority_manager_H
#include "project_list.h"

struct priority_node {
    char * priority;
    project_list projects;
    priority_node * next;
};

class priority_manager {
    public:
        priority_manager(void);
        ~priority_manager(void);
        // Add a priority.
        int add(const char * new_priority);
        // Remove a priority.
        int remove(const char * priority);
        // Add a project to a priority.
        int add_project(const char * priority, const project & new_project);
        // Remove a project from a priority.
        int remove_project(const char * priority, const char * project_name);
        // Remove all priorities and their projects.
        int clear(void);
        // Display all the priorities with their projects.
        int display(void) const;
        // Display one priority with its projects.
        int display(const char * priority) const;
        // Check if the priorities list is empty.
        int is_empty(void) const;
        // Check if a priority's project list is empty.
        int is_priority_empty(const char * priority) const;
        // Check if a priority exists.
        int contains(const char * priority) const;
        // Check if a priority contains a project.
        int contains_project(const char * priority, const char * project_name) const;

    private:
        // Find a priority node.
        int find(const char * priority, priority_node * & result) const;
        // Find a priority node and capture its previous node.
        int find(const char * priority, priority_node * & result, priority_node * & previous) const;

        priority_node * first_priority; // the first priority in the list
};

#endif
