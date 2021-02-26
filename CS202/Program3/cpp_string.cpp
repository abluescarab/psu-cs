/* Alana Gilston
 * cpp_string.cpp
 *
 * This is the implementation file for the cpp_string class. The cpp_string 
 * class manages a resizeable string object.
 */
#include <cstring>
#include <iostream>
#include "cpp_string.h"
#define MAX_INPUT 1024

using namespace std;



// Create a new cpp_string.
cpp_string::cpp_string(void) : value(nullptr) {
    clear();
}



// Create a new cpp_string.
// INPUT:
//  source: char to copy
cpp_string::cpp_string(const char source) : value(new char[2]) {
    value[0] = source;
    value[1] = '\0';
}



// Create a new cpp_string.
// INPUT:
//  source: char array to copy from
cpp_string::cpp_string(const char * source) : value(nullptr) {
    if(!copy_char_array(value, source)) {
        clear();
    }
}



// Create a new cpp_string.
// INPUT:
//  source: another cpp_string to copy from
cpp_string::cpp_string(const cpp_string & source) : value(nullptr) {
    if(!copy_char_array(value, source.value)) {
        clear();
    }
}



// Destroy the cpp_string.
cpp_string::~cpp_string(void) {
    delete [] value;
    value = nullptr;
}



// Subscript operator.
// INPUT:
//  index: the index to access
// OUTPUT:
//  Returns the character at that index.
const char & cpp_string::operator[](size_t index) const {
    return value[index];
}



// Subscript operator.
// INPUT:
//  index: the index to change
// OUTPUT:
//  Returns the character at that index.
char & cpp_string::operator[](size_t index) {
    return value[index];
}



// Assignment operator.
// INPUT:
//  source: the cpp_string to copy from
cpp_string & cpp_string::operator=(const cpp_string & source) {
    if(this == &source)
        return *this;

    if(source.value[0] == '\0') {
        clear();
    }
    else {
        delete [] value;
        value = new char[strlen(source.value) + 1];
        strcpy(value, source.value);
    }

    return *this; 
}



// Assignment operator.
// INPUT:
//  source: single character to copy
cpp_string & cpp_string::operator=(const char source) {
    if(source == '\0') {
        clear();
    }
    else {
        delete [] value;
        value = new char[2];
        value[0] = source;
        value[1] = '\0';
    }

    return *this;
}



// Assignment operator.
// INPUT:
//  source: char array to copy from
cpp_string & cpp_string::operator=(const char * source) {
    if(!copy_char_array(value, source) && source[0] == '\0') {
        clear();
    }

    return *this;
}



// Compound addition.
// INPUT:
//  source: cpp_string to append from
cpp_string & cpp_string::operator+=(const cpp_string & source) {
    append(*this, source);
    return *this;
}



// Compound addition.
// INPUT:
//  source: char to append
cpp_string & cpp_string::operator+=(const char source) {
    append(*this, source);
    return *this;
}



// Compound addition.
// INPUT:
//  source: char array to append
cpp_string & cpp_string::operator+=(const char * source) {
    append(*this, source);
    return *this;
}



// Equality operator.
// INPUT:
//  compare: cpp_string to compare to
bool operator==(const cpp_string & original, const cpp_string & compare) {
    return (original.value[0] == '\0' && compare.value[0] == '\0') ||
            strcmp(original.value, compare.value) == 0;
}



// Equality operator.
// INPUT:
//  compare: char to compare to
bool operator==(const cpp_string & original, const char compare) {
    return strlen(original.value) == 1 && original.value[0] == compare;
}



// Equality operator.
// INPUT:
//  compare: char array to compare to
bool operator==(const cpp_string & original, const char * compare) {
    return (!compare && original.value[0] == '\0') || 
        (original.value[0] == '\0' && compare[0] == '\0') ||
        strcmp(original.value, compare) == 0;
}



// Addition operator.
// INPUT:
//  destination: the destination cpp_string
//  source: cpp_string to copy from
cpp_string operator+(cpp_string & destination, const cpp_string & source) {
    cpp_string result = destination;
    cpp_string::append(result, source);
    return result;
}



// Addition operator.
// INPUT:
//  destination: the destination cpp_string
//  source: char to copy
cpp_string operator+(cpp_string & destination, const char source) {
    cpp_string result = destination;
    cpp_string::append(result, source);
    return result;
}



// Addition operator.
// INPUT:
//  destination: the destination cpp_string
//  source: char array to copy
cpp_string operator+(cpp_string & destination, const char * source) {
    cpp_string result = destination;
    cpp_string::append(result, source);
    return result;
}



// Less than operator.
// INPUT:
//  original: the original cpp_string
//  compare: the cpp_string to compare to the original
// OUTPUT:
//  Returns the result of strcmp.
bool operator<(const cpp_string & original, const cpp_string & compare) {
    return strcmp(original.value, compare.value) < 0;
}



// Less than operator.
// INPUT:
//  original: the original cpp_string
//  compare: the character to compare against
// OUTPUT:
//   0 if the strings do not match.
//   1 if they match.
bool operator<(const cpp_string & original, const char compare) {
    return strlen(original.value) == 1 && original.value[0] < compare;
}



// Less than operator.
// INPUT:
//  original: the original cpp_string
//  compare: the char array to compare to
// OUTPUT:
//  Returns the result of strcmp.
bool operator<(const cpp_string & original, const char * compare) {
    return strcmp(original.value, compare) < 0;
}



// Less than or equal to operator.
// INPUT:
//  original: the original cpp_string
//  compare: the cpp_string to compare to
// OUTPUT:
//  Returns the result of strcmp.
bool operator<=(const cpp_string & original, const cpp_string & compare) {
    return strcmp(original.value, compare.value) <= 0;
}



// Less than operator.
// INPUT:
//  original: the original cpp_string
//  compare: the character to compare against
// OUTPUT:
//   0 if the strings do not match.
//   1 if they match.
bool operator<=(const cpp_string & original, const char compare) {
    return strlen(original.value) == 1 && original.value[0] <= compare;
}



// Less than or equal to operator.
// INPUT:
//  original: the original cpp_string
//  compare: the cpp_string to compare to
// OUTPUT:
//  Returns the result of strcmp.
bool operator<=(const cpp_string & original, const char * compare) {
    return strcmp(original.value, compare) <= 0;
}



// More than to operator.
// INPUT:
//  original: the original cpp_string
//  compare: the cpp_string to compare to
// OUTPUT:
//  Returns the result of strcmp.
bool operator>(const cpp_string & original, const cpp_string & compare) {
    return strcmp(original.value, compare.value) > 0;
}



// More than operator.
// INPUT:
//  original: the original cpp_string
//  compare: the character to compare against
// OUTPUT:
//   0 if the strings do not match.
//   1 if they match.
bool operator>(const cpp_string & original, const char compare) {
    return strlen(original.value) == 1 && original.value[0] > compare;
}



// More than operator.
// INPUT:
//  original: the original cpp_string
//  compare: the char array to compare to
// OUTPUT:
//  Returns the result of strcmp.
bool operator>(const cpp_string & original, const char * compare) {
    return strcmp(original.value, compare) > 0;
}



// More than or equal to operator.
// INPUT:
//  original: the original cpp_string
//  compare: the cpp_string to compare to
// OUTPUT:
//  Returns the result of strcmp.
bool operator>=(const cpp_string & original, const cpp_string & compare) {
    return strcmp(original.value, compare.value) >= 0;
}



// More than or equal to operator.
// INPUT:
//  original: the original cpp_string
//  compare: the character to compare against
// OUTPUT:
//   0 if the strings do not match.
//   1 if they match.
bool operator>=(const cpp_string & original, const char compare) {
    return strlen(original.value) == 1 && original[0] >= compare;
}



// More than or equal to operator.
// INPUT:
//  original: the original cpp_string
//  compare: the char array to compare to
// OUTPUT:
//  Returns the result of strcmp.
bool operator>=(const cpp_string & original, const char * compare) {
    return strcmp(original.value, compare) >= 0;
}



// Input operator.
// INPUT:
//  in: the istream to input to
//  in_string: the cpp_string to input to
istream & operator>>(istream & in, cpp_string & in_string) {
    char temp[MAX_INPUT];

    in >> temp;
    in_string = temp;
    return in;
}



// Output operator.
// INPUT:
//  out: the ostream to output to
//  out_string: the cpp_string to output
ostream & operator<<(ostream & out, const cpp_string & out_string) {
    if(!out_string.is_empty())
        out << out_string.value;

    return out;
}



// Return the string in uppercase.
// OUTPUT:
//  Returns the result of the recursive function.
cpp_string cpp_string::to_upper(void) {
    return change_case(true);
}



// Return the string in lowercase.
// OUTPUT:
//  Returns the result of the recursive function.
cpp_string cpp_string::to_lower(void) {
    return change_case(false);
}



// Find the first index of a character.
// INPUT:
//  index_of_char: the char to find
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::index_of(const char index_of_char) {
    return index_of(index_of_char, 0);
}



// Find the first index of a character array.
// INPUT:
//  index_of_chars: the char array to find
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::index_of(const char * index_of_chars) {
    return index_of(index_of_chars, 0);
}



// Find the first index of a string.
// INPUT:
//  index_of_string: the cpp_string to find
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::index_of(const cpp_string & index_of_string) {
    return index_of(index_of_string, 0);
}



// Find an index of a char beginning from another index.
// INPUT:
//  index_of_char: the char to find the index of
//  start_index: the index to begin from
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::index_of(const char index_of_char, const int start_index) {
    char * result = nullptr;
    int index = index_of(index_of_char, start_index, result);

    if(result)
        delete [] result;

    return index;
}



// Find an index of a character array beginning from another index.
// INPUT:
//  index_of_chars: the char array to find
//  start_index: the index to begin from
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::index_of(const char * index_of_chars, const int start_index) {
    char * result = nullptr;
    int index = index_of(index_of_chars, start_index, result);

    if(result)
        delete [] result;

    return index;
}



// Find an index of a string beginning from another index.
// INPUT:
//  index_of_string: the cpp_string to find
//  start_index: the index to begin from
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::index_of(const cpp_string index_of_string, const int start_index) {
    return index_of(index_of_string.value, start_index);
}



// Get the index of a character and return the remaining string.
// INPUT:
//  index_of_char: the char to search for
//  start_index: the index to start from
//  result: the string starting from the found index
// OUTPUT:
//  -1 if the char was not found.
//  Otherwise returns the index of the found character.
int cpp_string::index_of(const char index_of_char, const int start_index, char * & result) {
    size_t index = start_index;
    bool found = false;

    if(index < 0)
        index = 0;

    while(!found && index < length()) {
        if(value[index] == index_of_char)
            found = true;
        else
            ++index;
    }

    if(!found) {
        result = nullptr;
        return -1;
    }

    result = new char[strlen(value + index) + 1];
    strcpy(result, value + index);
    return index;
}



// Get the index of a character array and return the remaining string.
// INPUT:
//  index_of_chars: the char array to search for
//  start_index: the index to start from
//  result: the string starting from the found index
// OUTPUT:
//  -1 if the char array was not found.
//  Otherwise returns the index of the found char array.
int cpp_string::index_of(const char * index_of_chars, const int start_index, char * & result) {
    int index = start_index;
    int len = length();
    char * temp = nullptr;

    if(index < 0)
        index = 0;
    else if(index >= len)
        return -1;

    temp = strstr(value + index, index_of_chars);

    if(!temp) {
        result = nullptr;
        return -1;
    }

    result = new char[strlen(temp) + 1];
    strcpy(result, temp);
    return strlen(value) - strlen(temp);
}



// Get the index of a string and return the remaining string.
// INPUT:
//  index_of_string: the string to find
//  start_index: the index to start from
//  result: the substring starting from the found index
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::index_of(const cpp_string index_of_string, const int start_index, char * & result) {
    return index_of(index_of_string.value, start_index, result);
}



// Get a substring of the string.
// INPUT:
//  start_index: the start index of the substring
// OUTPUT:
//  Returns the substring.
cpp_string cpp_string::substring(const int start_index) const {
    int index = start_index;
    int len = length();

    if(!value || len == 0)
        return cpp_string();

    if(index < 0)
        index = 0;
    else if(index > len)
        return cpp_string();

    return cpp_string(value + start_index);
}



// Get a substring of the string.
// INPUT:
//  start_index: the index to start from
//  substring_length: the length of the returned substring
// OUTPUT:
//  Returns the substring.
cpp_string cpp_string::substring(const int start_index, const int substring_length) const {
    cpp_string result;
    int index = start_index;
    int len = length();

    if(!value || len == 0)
        return cpp_string();

    if(index < 0)
        index = 0;
    else if(index > len)
        index = len;

    char * temp = new char[substring_length + 1];

    strncpy(temp, value + index, substring_length);
    temp[substring_length] = '\0';

    result = temp;
    delete [] temp;
    return result;
}



// Check if the string contains a character.
// INPUT:
//  contains_char: the character to find
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::contains(const char contains_char) {
    return index_of(contains_char) != -1;
}



// Check if the string contains a character array.
// INPUT:
//  contains_chars: the char array to search for
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::contains(const char * contains_chars) {
    return index_of(contains_chars) != -1;
}



// Check if the string contains another string.
// INPUT:
//  contains_string: the string to search for
// OUTPUT:
//  Returns the result of the recursive function.
int cpp_string::contains(const cpp_string & contains_string) {
    return contains(contains_string.value);
}



// Insert a character at an index.
// INPUT:
//  index: the index to insert at
//  insert_char: the char to insert
// OUTPUT:
//  Returns the new string with the inserted character.
cpp_string cpp_string::insert(const int index, const char insert_char) {
    cpp_string result;
    int len = length();
    char * temp = nullptr;

    if(index >= len) {
        result = value;
        append(result, insert_char);
        return result;
    }

    temp = new char[len + 2];
    // make the char array empty
    strcpy(temp, "");
    // copy the first part of the value
    strncat(temp, value, index);
    // add the new character to the chosen index
    temp[index] = insert_char;
    // if we don't do this, the character will be followed by junk
    temp[index + 1] = '\0';
    // add the next part of the value
    strcat(temp, value + index);

    result = temp;
    delete [] temp;
    return result;
}



// Insert a character array at an index.
// INPUT:
//  index: the index to insert at
//  insert_chars: the char array to insert
// OUTPUT:
//  Returns the new string with the inserted characters.
cpp_string cpp_string::insert(const int index, const char * insert_chars) {
    cpp_string result;
    int len = length();
    char * temp = nullptr;

    if(index >= len) {
        result = value;
        append(result, insert_chars);
        return result;
    }

    temp = new char[len + strlen(insert_chars) + 1];
    strcpy(temp, "");
    strncat(temp, value, index);
    strcat(temp, insert_chars);
    strcat(temp, value + index);

    result = temp;
    delete [] temp;
    return result;
}



// Insert another string at an index.
// INPUT:
//  index: the index to insert at
//  insert_string: the string to insert
// OUTPUT:
//  Returns the new string with the inserted string.
cpp_string cpp_string::insert(const int index, const cpp_string & insert_string) {
    return insert(index, insert_string.value);
}



// Replace some character with another character.
// INPUT:
//  old_char: the char to replace
//  new_char: the char to replace with
// OUTPUT:
//  Returns the new string with the old char replaced.
cpp_string cpp_string::replace(const char old_char, const char new_char) {
    cpp_string result;
    int len = length();
    char * temp = new char[len + 1];

    for(int i = 0; i < len; ++i) {
        if(value[i] == old_char)
            temp[i] = new_char;
        else
            temp[i] = value[i];
    }

    temp[len] = '\0';
    result = temp;
    delete [] temp;
    return result;
}



// Replace some character array with another character array.
// INPUT:
//  old_chars: the char array to replace
//  new_chars: the char array to replace with
// OUTPUT:
//  Returns the new string with the old char array replaced.
cpp_string cpp_string::replace(const char * old_chars, const char * new_chars) {
    cpp_string result;
    int index = 0;
    int next_index = -1;
    int count = count_instances(value, old_chars);
    char * found = value;
    char * temp = nullptr;

    if(count == 0) {
        result = value;
        return result;
    }

    // get the difference between the old length and the new length
    temp = new char[strlen(value) + (count * strlen(new_chars)) - (count * strlen(old_chars)) + 1];
    strcpy(temp, "");

    do {
        // get the next index of the characters to replace
        next_index = index_of(old_chars, index);

        // if we are at the beginning of the string, don't skip any characters
        if(found == value)
            strncat(temp, found, next_index - index);
        else
            strncat(temp, found + 1, next_index - index);

        // only copies the next instance when we have more to copy
        if(next_index > 0)
            strcat(temp, new_chars);

        // delete the found substring in prep for the next index_of call
        if(found != value)
            delete [] found;

        // update the current index
        index = index_of(old_chars, index + 1, found) + 1;
    } while(index != 0);

    result = temp;
    delete [] temp;
    return result;
}



// Replace some substring with another string.
// INPUT:
//  old_string: the string to replace
//  new_string: the string to replace with
// OUTPUT:
//  Returns the new string with the old string replaced.
cpp_string cpp_string::replace(const cpp_string & old_string, const cpp_string & new_string) {
    return replace(old_string.value, new_string.value);
}



// Remove part of the string from some index to the end.
// INPUT:
//  start_index: the index to remove from
// OUTPUT:
//  Returns the new string without the removed text.
cpp_string cpp_string::remove(const int start_index) {
    cpp_string result;
    char * temp = new char[strlen(value) - start_index + 1];

    strcpy(temp, "");
    strncat(temp, value, start_index);

    result = temp;
    delete [] temp;
    return result;
}



// Remove part of the string from some index to another index.
// INPUT:
//  start_index: the index to start removing from
//  end_index: the index to stop removing at
// OUTPUT:
//  Returns the new string without the removed text.
cpp_string cpp_string::remove(const int start_index, const int removal_length) {
    int len = length();
    cpp_string result;
    char * temp = nullptr;

    if(start_index > len)
        return *this;

    if((start_index + removal_length) > len)
        return remove(start_index);

    temp = new char[strlen(value) - (start_index + removal_length)+ 1];

    strcpy(temp, "");
    strncat(temp, value, start_index);
    strcat(temp, value + start_index + removal_length);

    result = temp;
    delete [] temp;
    return result;
}



// Clear the string.
// OUTPUT:
//  1 when the string is cleared.
int cpp_string::clear(void) {
    delete [] value;
    value = new char[1];
    value[0] = '\0';
    return 1;
}



// Check if the string is empty.
// OUTPUT:
//  Returns the result of char_array_empty.
int cpp_string::is_empty(void) const {
    return char_array_empty(value);
}



// Return the length of the string.
// OUTPUT:
//  0 if the string does not exist.
//  Otherwise returns the result of strlen.
size_t cpp_string::length(void) const {
    if(!value)
        return 0;

    return strlen(value);
}



// Display the string.
// OUTPUT:
//  Returns 1.
int cpp_string::display(void) const {
    cout << value;
    return 1;
}



// Return the string with a different case.
// INPUT:
//  uppercase: whether to make uppercase (t) or lowercase (f)
// OUTPUT:
//  Returns the new string in either uppercase or lowercase.
cpp_string cpp_string::change_case(bool uppercase) {
    if(is_empty())
        return cpp_string();

    int len = length();
    char * temp = new char[len + 1];
    cpp_string result;

    for(int i = 0; i < len; ++i) {
        if(uppercase)
            temp[i] = toupper(value[i]);
        else
            temp[i] = tolower(value[i]);
    }

    temp[len] = '\0';
    result = temp;
    delete [] temp;
    return result;
}



// Count the number of instances of a specified char array.
// INPUT:
//  current: the current char array substring
//  to_find: the char array to find
// OUTPUT:
//  Returns the number of instances of the char array found.
int cpp_string::count_instances(char * current, const char * to_find) {
    char * found = strstr(current, to_find);

    if(!found)
        return 0;

    return count_instances(found + 1, to_find) + 1;
}



// Append a character to the string.
// INPUT:
//  destination: the cpp_string to append to
//  source: the char to append
// OUTPUT:
//  Returns 1.
int cpp_string::append(cpp_string & destination, const char source) {
    int len = destination.length();

    if(len == 0) {
        destination = source;
        return 1;
    }

    char * temp = new char[len + 2];

    strcpy(temp, destination.value);
    temp[len] = source;
    temp[len + 1] = '\0';
    copy_char_array(destination.value, temp);
    delete [] temp;

    return 1;
}



// Append a character array to the string.
// INPUT:
//  destination: the cpp_string to append to
//  source: the char array to append
// OUTPUT:
//  0 if the source does not exist.
//  1 when the append happens.
int cpp_string::append(cpp_string & destination, const char * source) {
    if(!source)
        return 0;

    if(destination.length() == 0) {
        destination = source;
        return 1;
    }

    char * temp = new char[strlen(destination.value) + strlen(source) + 1];

    strcpy(temp, destination.value);
    strcat(temp, source);
    copy_char_array(destination.value, temp);
    delete [] temp;

    return 1;
}



// Append another string to the string.
// INPUT:
//  destination: the cpp_string to append to
//  source: the cpp_string to append
// OUTPUT:
//  Returns the result of the other append function.
int cpp_string::append(cpp_string & destination, const cpp_string & source) {
    return append(destination, source.value);
}



// Copy a char array into another char array.
// INPUT:
//  destination: the char array to copy to
//  source: the char array to copy from
// OUTPUT:
//  0 if the source does not exist.
//  1 if the copy was successful.
int cpp_string::copy_char_array(char * & destination, const char * source) {
    if(!source || char_array_empty(source))
        return 0;

    if(destination)
        delete [] destination;

    destination = new char[strlen(source) + 1];
    strcpy(destination, source);
    return 1;
}



// Check if the string is empty without modifying the value.
// INPUT:
//  array: the array to check
// OUTPUT:
//  0 if the array has valid text.
//  1 if the array is null, empty, or whitespace.
int cpp_string::char_array_empty(const char * array) {
    if(!array)
        return 1;

    // loop through the array character by character
    while(*array != '\0') {
        if(!isspace(*array)) 
            return 0;
        ++array;
    }

    return 1;
}
