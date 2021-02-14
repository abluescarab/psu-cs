/* Alana Gilston - 2/12/2021 - CS202 - Program 3
 * cpp_string.cpp
 *
 * This is the implementation file for the cpp_string class. The cpp_string 
 * class manages a resizeable string object.
 */
#include <cstring>
#include <iostream>
#include "cpp_string.h"
#include "utils.h"

using namespace std;



cpp_string::cpp_string(void) : value(nullptr) {}



cpp_string::cpp_string(const char * source) : 
    value(nullptr) {
        copy_char_array(value, source);
    }



cpp_string::cpp_string(const cpp_string & source) :
    value(nullptr) {
        copy_char_array(value, source.value);
    }



cpp_string::~cpp_string(void) {
    if(value) {
        delete [] value;
        value = nullptr;
    }
}



cpp_string & cpp_string::operator=(const cpp_string & source) {
    if(this == &source)
        return *this;

    if(value)
        delete [] value;

    value = new char[strlen(source.value) + 1];
    strcpy(value, source.value);

    return *this; 
}



cpp_string & cpp_string::operator=(const char source) {
    if(value)
        delete [] value;
    
    value = new char[2];
    value[0] = source;
    value[1] = '\0';
    return *this;
}



cpp_string & cpp_string::operator=(const char * source) {
    copy_char_array(value, source);
    return *this;
}

        

cpp_string & cpp_string::operator+=(const cpp_string & source) {
    append(*this, source);
    return *this;
}



cpp_string & cpp_string::operator+=(const char source) {
    append(*this, source);
    return *this;
}



cpp_string & cpp_string::operator+=(const char * source) {
    append(*this, source);
    return *this;
}



bool cpp_string::operator==(const cpp_string & compare) const {
    return strcmp(value, compare.value) == 0;
}



bool cpp_string::operator==(const char * compare) const {
    return strcmp(value, compare) == 0;
}



cpp_string operator+(cpp_string & destination, const cpp_string & source) {
    cpp_string result = destination;
    cpp_string::append(result, source);
    return result;
}



cpp_string operator+(cpp_string & destination, const char source) {
    cpp_string result = destination;
    cpp_string::append(result, source);
    return result;
}



cpp_string operator+(cpp_string & destination, const char * source) {
    cpp_string result = destination;
    cpp_string::append(result, source);
    return result;
}



istream & operator>>(istream & in, const cpp_string & in_string) {
    // todo
    return in;
}



ostream & operator<<(ostream & out, const cpp_string & out_string) {
    out << out_string.value;
    return out;
}



// Return the string in uppercase.
cpp_string cpp_string::to_upper(void) {
    return change_case(true);
}



// Return the string in lowercase.
cpp_string cpp_string::to_lower(void) {
    return change_case(false);
}



// Find the first index of a character.
int cpp_string::index_of(const char index_of_char) {
    return index_of(index_of_char, 0);
}



// Find the first index of a character array.
int cpp_string::index_of(const char * index_of_chars) {
    return index_of(index_of_chars, 0);
}



// Find the first index of a string.
int cpp_string::index_of(const cpp_string & index_of_string) {
    return index_of(index_of_string, 0);
}



// Find an index of a char beginning from another index.
int cpp_string::index_of(const char index_of_char, const int start_index) {
    char * result = nullptr;
    int index = index_of(index_of_char, start_index, result);
    
    if(result)
        delete [] result;

    return index;
}



// Find an index of a character array beginning from another index.
int cpp_string::index_of(const char * index_of_chars, const int start_index) {
    char * result = nullptr;
    int index = index_of(index_of_chars, start_index, result);

    if(result)
        delete [] result;

    return index;
}



// Find an index of a string beginning from another index.
int cpp_string::index_of(const cpp_string index_of_string, const int start_index) {
    return index_of(index_of_string.value, start_index);
}



// Get the index of a character and return the remaining string.
int cpp_string::index_of(const char index_of_char, const int start_index, char * & result) {
    int index = start_index;
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
int cpp_string::index_of(const cpp_string index_of_string, const int start_index, char * & result) {
    return index_of(index_of_string.value, start_index, result);
}



// Get a substring of the string.
cpp_string cpp_string::substring(const int start_index) const {
    return cpp_string(value + start_index);
}



// Get a substring of the string.
cpp_string cpp_string::substring(const int start_index, const int substring_length) const {
    cpp_string result;
    int index = start_index;
    int len = length();

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
int cpp_string::contains(const char contains_char) {
    return index_of(contains_char) != -1;
}



// Check if the string contains a character array.
int cpp_string::contains(const char * contains_chars) {
    return index_of(contains_chars) != -1;
}



// Check if the string contains another string.
int cpp_string::contains(const cpp_string & contains_string) {
    return contains(contains_string.value);
}



// Insert a character at an index.
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
cpp_string cpp_string::insert(const int index, const cpp_string & insert_string) {
    return insert(index, insert_string.value);
}



// Replace some character with another character.
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

        // update the current index and found substring
        if(found != value)
            delete [] found;

        index = index_of(old_chars, index + 1, found) + 1;
    } while(index != 0);

    result = temp;
    delete [] temp;
    return result;
}



// Replace some substring with another string.
cpp_string cpp_string::replace(const cpp_string & old_string, const cpp_string & new_string) {
    return replace(old_string.value, new_string.value);
}



// Remove part of the string from some index to the end.
cpp_string cpp_string::remove(const int start_index) {
    // todo
    cpp_string result;
    char * temp = new char[1];

    result = temp;
    delete [] temp;
    return result;
}



// Remove part of the string from some index to another index.
cpp_string cpp_string::remove(const int start_index, const int end_index) {
    // todo
    cpp_string result;
    char * temp = new char[1];

    result = temp;
    delete [] temp;
    return result;
}



// Check if the value matches another string.
int cpp_string::equals(const cpp_string & compare) const {
    return strcmp(value, compare.value) == 0;
}



// Check if the value matches another character array.
int cpp_string::equals(const char * compare) const {
    return strcmp(value, compare) == 0;
}



// Check if the string is empty.
int cpp_string::is_empty(void) const {
    return char_array_empty(value);
}



// Return the length of the string.
int cpp_string::length(void) const {
    if(!value)
        return 0;

    return strlen(value);
}



// Display the string.
int cpp_string::display(void) const {
    cout << value;
    return 1;
}



// Return the string with a different case.
cpp_string cpp_string::change_case(bool uppercase) {
    if(is_empty())
        return 0;

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
int cpp_string::count_instances(char * current, const char * to_find) {
    char * found = strstr(current, to_find);

    if(!found)
        return 0;

    return count_instances(found + 1, to_find) + 1;
}



// Append a character to the string.
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
int cpp_string::append(cpp_string & destination, const cpp_string & source) {
    return append(destination, source.value);
}
