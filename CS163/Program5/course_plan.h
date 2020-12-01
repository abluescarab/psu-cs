/* Alana Gilston - 11/23/2020 - CS163 - Program 5
 * course_plan.h
 *
 * This is the header file for the "course plan" class. It manages a course 
 * plan for a student at PSU.
 */
#ifndef COURSE_PLAN_H
#define COURSE_PLAN_H
#define LIST_SIZE 100
#include "course.h"

struct vertex {
    course * this_course;
    struct edge * head;
};

struct edge {
    vertex * adjacent;
    edge * next;
};

class course_plan {
    public:
        course_plan(void);
        ~course_plan(void);
        // Add a new vertex by course.
        int add(const course & to_add);
        // Connect an edge from the start course to the end course.
        int connect(const char * start_code, const char * end_code);
        // Display all courses.
        int display(void) const;
        // Display a course.
        int display(const char * code) const;
        // Display courses by department.
        int display_by_department(const char * department) const;
        // Display the adjacent vertices for the chosen vertex.
        int display_adjacent(const char * code) const;
        // Clear the graph.
        int clear(void);

    private:
        vertex * courses; // the adjacency list of courses

        // Display the adjacent vertices for the chosen vertex recursively.
        int display_adjacent(const edge * course_edge) const;
        // Find the index of a selected vertex.
        int find_index(const char * code) const;
        // Add a new edge to the selected vertex.
        int add_adjacent(vertex * & start_vertex, vertex * & end_vertex);
};

#endif
