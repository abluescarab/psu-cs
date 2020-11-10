/* Alana Gilston - 10/15/2020 - CS163 - Program 2
 * utils.cpp
 *
 * This is the implementation file for basic utility function(s).
 */
#include <limits>
#include <iostream>
#include <cstring>
#include <cctype>
#include "utils.h"

using namespace std;



// Ensure that a char array exists and is not only whitespace.
// INPUT:
//  array: The array to validate
// OUTPUT:
//  Returns 0 if the array is empty and 1 if it has valid characters.
int char_array_empty(const char * array) {
    if(!array) return 1;

    // loop through the array character by character
    while(*array != '\0') {
        if(!isspace(*array)) return 0;
        ++array;
    }

    return 1;
}



// Create a character array from a source array.
// INPUT:
//  destination: The array to copy to.
//  source: The array to copy from.
// OUTPUT:
//  Returns 0 if the copy failed due to a missing source and 1 if it succeeded.
int copy_char_array(char * & destination, const char * source) {
    if(!source) return 0;
    
    if(destination && !char_array_empty(destination))
        delete [] destination;

    destination = new char[strlen(source) + 1];
    strcpy(destination, source);
    return 1;
}



// Validate numeric user input.
// INPUT:
//  max_input: The maximum number allowed
// OUTPUT:
//  Returns the valid input
int validate_input(const int max_input) {
    int input;

    cout << "> ";
    cin.width(1);
    cin >> input;

    cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    if(cin.fail() || input < 1 || input > max_input) {
        cout << "Invalid input. Please try again." << endl;
        return validate_input(max_input);
    }

    cout << endl;
    return input;
}
