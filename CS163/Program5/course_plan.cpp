/* Alana Gilston - 11/23/2020 - CS163 - Program 5
 * course_plan.cpp
 *
 * This is the implementation file for the "course plan" class. It manages a
 * course plan for a student at PSU.
 */
#include <iostream>
#include <cstring>
#include "course_plan.h"
#include "utils.h"



course_plan::course_plan(void) {
    courses = new vertex[LIST_SIZE];

    for(int i = 0; i < LIST_SIZE; ++i) {
        courses[i].this_course = nullptr;
        courses[i].head = nullptr;
    }
}



course_plan::~course_plan(void) {
    clear();
    delete [] courses;
    courses = nullptr;
}



// Add a new vertex by course.
// INPUT:
//  to_add: The course to add
// OUTPUT:
//  -1 if to_add is empty or invalid
//  0 if the list is full
//  1 if the add was successful
int course_plan::add(const course & to_add) {
    if(to_add.is_empty())
        return -1;

    int index = 0;
    bool stop = false;

    while(!stop && (index < LIST_SIZE)) {
        if(!courses[index].this_course) {
            courses[index].this_course = new course;
            courses[index].this_course->copy(to_add);
            stop = true;
        }

        ++index;
    }

    if(!stop)
        return 0;

    return 1;
}



// Connect an edge from the start course to the end course.
// INPUT:
//  start_code: The course to connect from
//  end_code: The course to connect to
// OUTPUT:
//  -1 if either course codes are invalid
//  0 if either courses are not found
//  1 if the connection was successful
int course_plan::connect(const char * start_code, const char * end_code) {
    if(char_array_empty(start_code) ||
       char_array_empty(end_code))
        return -1;

    vertex * start = nullptr;
    vertex * end = nullptr;
    course * current = nullptr;
    int index = 0;

    while((index < LIST_SIZE) && (!start || !end)) {
        current = courses[index].this_course;

        if(current) {
            if(current->code_matches(start_code))
                start = &courses[index];
            else if(current->code_matches(end_code))
                end = &courses[index];
        }

        ++index;
    }

    if(!start || !end)
        return 0;

    return add_adjacent(start, end);
}



// Display all courses.
// OUTPUT:
//  Returns the number of courses displayed
int course_plan::display(void) const {
    int count = 0;

    for(int i = 0; i < LIST_SIZE; ++i) {
        if(courses[i].this_course)
            count += courses[i].this_course->display();
    }

    return count;
}



// Display courses by department.
// INPUT:
//  department: The department to display, e.g. CS or HST
// OUTPUT:
//  -1 if the department is invalid
//  Otherwise returns number of courses displayed
int course_plan::display_by_department(const char * department) const {
    if(char_array_empty(department))
        return -1;

    course * current = nullptr;
    int count = 0;

    for(int i = 0; i < LIST_SIZE; ++i) {
        current = courses[i].this_course;

        if(current && current->code_starts_with(department))
            count += current->display();
    }

    return count;
}



// Display a course.
// INPUT:
//  code: The course code to display
// OUTPUT:
//  -1 if the code is invalid
//  0 if the course was not found
//  1 if the course was found and displayed
int course_plan::display(const char * code) const {
    if(char_array_empty(code))
        return -1;

    int index = find_index(code);

    if(index == -1)
        return 0;

    courses[index].this_course->display();
    return 1;
}



// Display the adjacent vertices for the chosen vertex.
// INPUT:
//  code: The course code to display adjacent vertices for
// OUTPUT:
//  -1 if the course code is invalid or the course was not found
//  Otherwise returns count of courses displayed
int course_plan::display_adjacent(const char * code) const {
    if(char_array_empty(code))
        return -1;

    int index = find_index(code);

    if(index == -1)
        return -1;

    courses[index].this_course->display();
    return display_adjacent(courses[index].head);
}



// Clear the graph.
// OUTPUT:
//  Returns the number of courses cleared
int course_plan::clear(void) {
    int count = 0;

    for(int i = 0; i < LIST_SIZE; ++i) {
        if(courses[i].this_course) {
            delete courses[i].this_course;
            courses[i].this_course = nullptr;
            delete courses[i].head;
            courses[i].head = nullptr;
            ++count;
        }
    }

    return count;
}



// Display the adjacent vertices for the chosen vertex recursively.
int course_plan::display_adjacent(const edge * course_edge) const {
    if(!course_edge)
        return 0;

    return course_edge->adjacent->this_course->display() + display_adjacent(course_edge->next);
}



// Find the index of a selected vertex.
// INPUT:
//  code: The course code to find the index for
// OUTPUT:
//  -1 if the course was not found
//  Otherwise returns the index of the course
int course_plan::find_index(const char * code) const {
    int index = 0;
    int found_index = -1;
    course * found = nullptr;

    while(found_index == -1 && (index < LIST_SIZE)) {
        found = courses[index].this_course;

        if(found && found->code_matches(code))
            found_index = index;

        ++index;
    }

    return found_index;
}



// Add a new adjacent vertex to the selected vertex.
// INPUT:
//  start_vertex: The vertex to start the edge at
//  end_vertex: The vertex to end the edge at
// OUTPUT:
//  0 if either vertex is null
//  1 if the add was successful
int course_plan::add_adjacent(vertex * & start_vertex, vertex * & end_vertex) {
    if(!start_vertex || !end_vertex)
        return 0;

    edge * current = nullptr;
    edge * to_add = new edge;

    to_add->adjacent = end_vertex;
    to_add->next = nullptr;

    if(!start_vertex->head) {
        start_vertex->head = to_add;
        return 1;
    }
        
    current = start_vertex->head;

    while(current->next)
        current = current->next;

    current->next = to_add;
    return 1;
}
