/* Alana Gilston - 2/12/2021 - CS202 - Program 3
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
        cpp_string(const char * source);
        cpp_string(const cpp_string & source);
        ~cpp_string(void);

        // assignment operators
        cpp_string & operator=(const cpp_string & source);
        cpp_string & operator=(const char source);
        cpp_string & operator=(const char * source);
        // compound additions
        cpp_string & operator+=(const cpp_string & source);
        cpp_string & operator+=(const char source);
        cpp_string & operator+=(const char * source);
        // equality
        bool operator==(const cpp_string & compare) const;
        bool operator==(const char * compare) const;
        // addition
        friend cpp_string operator+(cpp_string & destination, const cpp_string & source);
        friend cpp_string operator+(cpp_string & destination, const char source);
        friend cpp_string operator+(cpp_string & destination, const char * source);
        // input/output
        friend std::istream & operator>>(std::istream & in, const cpp_string & in_string);
        friend std::ostream & operator<<(std::ostream & out, const cpp_string & out_string);

        // Return the string in uppercase.
        cpp_string to_upper(void);
        // Return the string in lowercase.
        cpp_string to_lower(void);
        // Check if the string contains a character.
        int contains(const char contains_char) const;
        // Check if the string contains a character array.
        int contains(const char * contains_chars) const;
        // Check if the string contains another string.
        int contains(const cpp_string & contains_string) const;
        // Insert a character at an index.
        int insert(const int index, const char insert_char);
        // Insert a character array at an index.
        int insert(const int index, const char * insert_chars);
        // Insert another string at an index.
        int insert(const int index, const cpp_string & insert_string);
        // Replace some character with another character.
        int replace(const char old_char, const char new_char);
        // Replace some character array with another character array.
        int replace(const char * old_chars, const char * new_chars);
        // Replace some substring with another string.
        int replace(const cpp_string & old_string, const cpp_string & new_string);
        // Remove part of the string from some index to the end.
        int remove(const int start_index);
        // Remove part of the string from some index to another index.
        int remove(const int start_index, const int end_index);
        // Check if the value matches another string.
        int equals(const cpp_string & compare) const;
        // Check if the value matches another character array.
        int equals(const char * compare) const;
        // Check if the string is empty.
        int is_empty(void) const;
        // Return the length of the string.
        int length(void) const;
        // Display the string.
        int display(void) const;

    private:
        // Return the string with a different case.
        cpp_string change_case(bool uppercase);
        // Append another string to the string.
        static int append(cpp_string & destination, const cpp_string & source);
        // Append a character to the string.
        static int append(cpp_string & destination, const char source);
        // Append a character array to the string.
        static int append(cpp_string & destination, const char * source);

        char * value; // the backing character array
};

#endif
