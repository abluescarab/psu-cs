/* Alana Gilston
 * cpp_string.h
 *
 * This is the header file for the cpp_string class. The cpp_string class 
 * manages a resizeable string object backed by character arrays.
 */
#ifndef CPP_STRING_H
#define CPP_STRING_H

class cpp_string {
    public:
        cpp_string(void);
        cpp_string(const char source);
        cpp_string(const char * source);
        cpp_string(const cpp_string & source);
        ~cpp_string(void);

        // subscript
        const char & operator[](size_t index) const;
        char & operator[](size_t index);
        // assignment operators
        cpp_string & operator=(const cpp_string & source);
        cpp_string & operator=(const char source);
        cpp_string & operator=(const char * source);
        // compound additions
        cpp_string & operator+=(const cpp_string & source);
        cpp_string & operator+=(const char source);
        cpp_string & operator+=(const char * source);
        // equality
        friend bool operator==(const cpp_string & original, const cpp_string & compare);
        friend bool operator==(const cpp_string & original, const char compare);
        friend bool operator==(const cpp_string & original, const char * compare);
        // addition
        friend cpp_string operator+(cpp_string & destination, const cpp_string & source);
        friend cpp_string operator+(cpp_string & destination, const char source);
        friend cpp_string operator+(cpp_string & destination, const char * source);
        // relational
        friend bool operator<(const cpp_string & original, const cpp_string & compare);
        friend bool operator<(const cpp_string & original, const char compare);
        friend bool operator<(const cpp_string & original, const char * compare);
        friend bool operator<=(const cpp_string & original, const cpp_string & compare);
        friend bool operator<=(const cpp_string & original, const char compare);
        friend bool operator<=(const cpp_string & original, const char * compare);
        friend bool operator>(const cpp_string & original, const cpp_string & compare);
        friend bool operator>(const cpp_string & original, const char compare);
        friend bool operator>(const cpp_string & original, const char * compare);
        friend bool operator>=(const cpp_string & original, const cpp_string & compare);
        friend bool operator>=(const cpp_string & original, const char compare);
        friend bool operator>=(const cpp_string & original, const char * compare);
        // input/output
        friend std::istream & operator>>(std::istream & in, cpp_string & in_string);
        friend std::ostream & operator<<(std::ostream & out, const cpp_string & out_string);

        // Return the string in uppercase.
        cpp_string to_upper(void);
        // Return the string in lowercase.
        cpp_string to_lower(void);
        // Check if the string contains a character.
        int contains(const char contains_char);
        // Check if the string contains a character array.
        int contains(const char * contains_chars);
        // Check if the string contains another string.
        int contains(const cpp_string & contains_string);
        // Find the first index of a character.
        int index_of(const char index_of_char);
        // Find the first index of a character array.
        int index_of(const char * index_of_chars);
        // Find the first index of a string.
        int index_of(const cpp_string & index_of_string);
        // Find an index of a char beginning from another index.
        int index_of(const char index_of_char, const int start_index);
        // Find an index of a character array beginning from another index.
        int index_of(const char * index_of_chars, const int start_index);
        // Find an index of a string beginning from another index.
        int index_of(const cpp_string index_of_string, const int start_index);
        // Get a substring of the string.
        cpp_string substring(const int start_index) const;
        // Get a substring of the string.
        cpp_string substring(const int start_index, const int substring_length) const;
        // Insert a character at an index.
        cpp_string insert(const int index, const char insert_char);
        // Insert a character array at an index.
        cpp_string insert(const int index, const char * insert_chars);
        // Insert another string at an index.
        cpp_string insert(const int index, const cpp_string & insert_string);
        // Replace some character with another character.
        cpp_string replace(const char old_char, const char new_char);
        // Replace some character array with another character array.
        cpp_string replace(const char * old_chars, const char * new_chars);
        // Replace some substring with another string.
        cpp_string replace(const cpp_string & old_string, const cpp_string & new_string);
        // Remove part of the string from some index to the end.
        cpp_string remove(const int start_index);
        // Remove part of the string from some index to another index.
        cpp_string remove(const int start_index, const int removal_length);
        // Check if the string is empty.
        int is_empty(void) const;
        // Return the length of the string.
        size_t length(void) const;
        // Display the string.
        int display(void) const;

    private:
        // Return the string with a different case.
        cpp_string change_case(bool uppercase);
        // Count the number of instances of a specified char array.
        int count_instances(char * current, const char * to_find);
        // Get the index of a character and return the remaining string.
        int index_of(const char index_of_char, const int start_index, char * & result);
        // Get the index of a character array and return the remaining string.
        int index_of(const char * index_of_chars, const int start_index, char * & result);
        // Get the index of a string and return the remaining string.
        int index_of(const cpp_string index_of_string, const int start_index, char * & result);
        // Append a character to the string.
        static int append(cpp_string & destination, const char source);
        // Append a character array to the string.
        static int append(cpp_string & destination, const char * source);
        // Append another string to the string.
        static int append(cpp_string & destination, const cpp_string & source);
        // Check if the string is empty without modifying the value.
        static int char_array_empty(const char * array);
        // Copy a char array into another char array.
        static int copy_char_array(char * & destination, const char * source);

        char * value; // the backing character array
};

#endif
