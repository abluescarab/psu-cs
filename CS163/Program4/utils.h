/* Alana Gilston - 10/15/2020 - CS163 - Program 2
 * utils.h
 *
 * This is the header file for basic utility function(s).
 */
#ifndef UTILS_H
#define UTILS_H

// Ensure that a char array exists and is not only whitespace.
int char_array_empty(const char * array);
// Copy a char array into another char array.
int copy_char_array(char * & destination, const char * source); 
// Validate numeric user input.
int validate_input(const int min_input, const int max_input);
// Count number of digits in an integer.
int count_digits(const int number);

#endif
