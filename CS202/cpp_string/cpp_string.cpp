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



// Check if the string contains a character.
int cpp_string::contains(const char contains_char) const {
    if(is_empty())
        return 0;

    size_t index = 0;
    bool found = false;

    while(index < strlen(value) && !found) {
        if(value[index] == contains_char)
            found = true;

        ++index;
    }

    return found;
}



// Check if the string contains a character array.
int cpp_string::contains(const char * contains_chars) const {
    if(is_empty())
        return 0;

    return strstr(value, contains_chars) != nullptr;
}



// Check if the string contains another string.
int cpp_string::contains(const cpp_string & contains_string) const {
    return contains(contains_string.value);
}



// Insert a character at an index.
int cpp_string::insert(const int index, const char insert_char) {
    // todo
    return 1;
}



// Insert a character array at an index.
int cpp_string::insert(const int index, const char * insert_chars) {

    // todo
    return 1;
}



// Insert another string at an index.
int cpp_string::insert(const int index, const cpp_string & insert_string) {
    // todo
    return 1;
}



// Replace some character with another character.
int cpp_string::replace(const char old_char, const char new_char) {
    // todo
    return 1;
}



// Replace some character array with another character array.
int cpp_string::replace(const char * old_chars, const char * new_chars) {
    // todo
    return 1;
}



// Replace some substring with another string.
int cpp_string::replace(const cpp_string & old_string, const cpp_string & new_string) {
    // todo
    return 1;
}



// Remove part of the string from some index to the end.
int cpp_string::remove(const int start_index) {
    // todo
    return 1;
}



// Remove part of the string from some index to another index.
int cpp_string::remove(const int start_index, const int end_index) {
    // todo
    return 1;
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

    int length = strlen(value);
    char * temp = new char[length + 1];
    cpp_string result;

    for(int i = 0; i < length; ++i) {
        if(uppercase)
            temp[i] = toupper(value[i]);
        else
            temp[i] = tolower(value[i]);
    }

    temp[length] = '\0';
    result = temp;
    delete [] temp;
    return result;
}
        


// Append another string to the string.
int cpp_string::append(cpp_string & destination, const cpp_string & source) {
    if(source.is_empty())
        return 0;

    char * temp = new char[strlen(destination.value) + strlen(source.value) + 1];

    strcpy(temp, destination.value);
    strcat(temp, source.value);
    copy_char_array(destination.value, temp);
    delete [] temp;

    return 1;
}



// Append a character to the string.
int cpp_string::append(cpp_string & destination, const char source) {
    int length = strlen(destination.value);
    char * temp = new char[length + 2];

    strcpy(temp, destination.value);
    temp[length] = source;
    temp[length + 1] = '\0';
    copy_char_array(destination.value, temp);
    delete [] temp;

    return 1;
}



// Append a character array to the string.
int cpp_string::append(cpp_string & destination, const char * source) {
    if(!source)
        return 0;

    char * temp = new char[strlen(destination.value) + strlen(source) + 1];
    
    strcpy(temp, destination.value);
    strcat(temp, source);
    copy_char_array(destination.value, temp);
    delete [] temp;

    return 1;
}
