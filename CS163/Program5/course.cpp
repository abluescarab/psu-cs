/* Alana Gilston - 11/23/2020 - CS163 - Program 5
 * course.h
 *
 * This is the implementation file for the "course" class. It manages a PSU
 * course used in a student course plan.
 */
#include <iostream>
#include <cstring>
#include "course.h"
#include "utils.h"

using namespace std;



course::course(void) {
    code = nullptr;
    name = nullptr;
}



course::~course(void) {
    if(code) {
        delete [] code;
        code = nullptr;
    }
    
    if(name) {
        delete [] name;
        name = nullptr;
    }
}



// Copy data from individual inputs.
// INPUT:
//  new_code: The course code to copy
//  new_name: The name to copy
// OUTPUT:
//  0 if either input is empty
//  1 if the copy was successful
int course::copy(const char * new_code, const char * new_name) {
    if(char_array_empty(new_code) ||
       char_array_empty(new_name))
        return 0;

    copy_char_array(code, new_code);
    copy_char_array(name, new_name);
    return 1;
}



// Copy data from another course.
// INPUT:
//  to_copy: The course to copy from
// OUTPUT:
//  0 if the course to copy is missing data
//  1 if the copy succeeded
int course::copy(const course & to_copy) {
    if(to_copy.is_empty())
        return 0;

    copy_char_array(code, to_copy.code);
    copy_char_array(name, to_copy.name);
    return 1;
}



// Display the course data.
// OUTPUT:
//  0 if the course is empty
//  1 if the course displayed successfully
int course::display(void) const {
    if(is_empty())
        return 0;

    cout << code << ": " << name << endl;
    return 1;
}



// Check if the course is missing required data.
// OUTPUT:
//  0 if either the name or code is empty
//  1 if both have data
int course::is_empty(void) const {
    return char_array_empty(code) ||
           char_array_empty(name);
}



// Check if the course matches a provided course.
// INPUT:
//  to_compare: The course to compare to
// OUTPUT:
//  0 if the compare course is empty or any data does not match
//  1 if the courses match
int course::is_match(const course & to_compare) const {
    if(to_compare.is_empty())
        return 0;

    return strcmp(code, to_compare.code) == 0 &&
           strcmp(name, to_compare.name) == 0;
}



// Compare the code of the course with a provided code.
// INPUT:
//  to_compare: The course code to compare
// OUTPUT:
//  0 if the compare code is empty or the data does not match
//  1 if the codes match
int course::code_matches(const char * to_compare) const {
    if(char_array_empty(to_compare))
        return 0;

    return strcmp(code, to_compare) == 0;
}



// Compare the name of the course with a provided name.
// INPUT:
//  to_compare: The course name to match
// OUTPUT:
//  0 if the course name is empty or the data does not match
//  1 if the data matches
int course::name_matches(const char * to_compare) const {
    if(char_array_empty(to_compare))
        return 0;

    return strcmp(name, to_compare) == 0;
}



// Check if the code starts with some characters.
// INPUT:
//  to_find: The characters to find
// OUTPUT:
//  0 if the code does not start with to_find
//  1 if the code starts with to_find
int course::code_starts_with(const char * to_find) const {
    if(char_array_empty(to_find))
        return 0;

    return strncmp(to_find, code, strlen(to_find)) == 0;
}
