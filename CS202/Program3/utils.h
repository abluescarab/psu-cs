/* Alana Gilston - 2/21/2021 - CS202 - Program 3
 * utils.h
 *
 * This is the header file for basic utility function(s).
 */
#ifndef UTILS_H
#define UTILS_H

// Validate numeric user input.
int validate_input(const int min_input, 
        const int max_input, 
        const bool show_prompt = true);
// Validate yes/no user input.
int validate_yes(void);
// Count number of digits in an integer.
int count_digits(const int number);

#endif
