/* Alana Gilston - 2/21/2021 - CS202 - Program 3
 * utils.cpp
 *
 * This is the implementation file for basic utility function(s).
 */
#include <algorithm>
#include <limits>
#include <iostream>
#include <cstring>
#include <cctype>
#include "utils.h"

using namespace std;



// Validate numeric user input.
// INPUT:
//  max_input: The maximum number allowed
// OUTPUT:
//  Returns the valid input
int validate_input(const int min_input, 
        const int max_input, 
        const bool show_prompt) {
    int input;
    int min_width = count_digits(min_input) + (min_input < 0);
    int max_width = count_digits(max_input) + (max_input < 0);
    int width = max(min_width, max_width);

    if(show_prompt)
        cout << "> ";

    cin.width(width);
    cin >> input;

    cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    if(cin.fail() || input < min_input || input > max_input) {
        cout << "Invalid input. Please try again." << endl;
        return validate_input(min_input, max_input, show_prompt);
    }

    return input;
}



// Get yes/no user input.
// OUTPUT:
//  0 if the input was anything other than "y" or "yes"
//  1 if the input was "y" or "yes"
int validate_yes(void) {
    char input[4] = ""; // long enough for "yes" and null character

    cin.getline(input, 4);

    for(int i = 0; i < 4; ++i) {
        input[i] = tolower(input[i]);
    }

    // check if input was invalid and whether input was equal to "y" or "yes"
    return !cin.fail() &&
        (strcmp(input, "y") == 0 || strcmp(input, "yes") == 0);
}



// Count number of digits in an integer.
// INPUT:
//  number: The number to count digits for
// OUTPUT:
//  Returns the number of digits counted
int count_digits(const int number) {
    if(number == 0) return 0;
    return count_digits(number / 10) + 1;
}
