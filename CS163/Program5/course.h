/* Alana Gilston - 11/23/2020 - CS163 - Program 5
 * course.h
 *
 * This is the header file for the "course" class. It manages a PSU course used
 * in a student course plan.
 */
#ifndef COURSE_H
#define COURSE_H

class course {
    public:
        course(void);
        ~course(void);
        // Copy data from individual inputs.
        int copy(const char * new_code, const char * new_name);
        // Copy data from another course.
        int copy(const course & to_copy);
        // Display the course data.
        int display(void) const;
        // Check if the course is missing required data.
        int is_empty(void) const;
        // Check if the course matches a provided course.
        int is_match(const course & to_compare) const;
        // Compare the code of the course with a provided code.
        int code_matches(const char * to_compare) const;
        // Compare the name of the course with a provided name.
        int name_matches(const char * to_compare) const;
        // Check if the code starts with some characters.
        int code_starts_with(const char * to_find) const;

    private:
        char * code; // the course code, e.g. CS163
        char * name; // the name of the course, e.g. Data Structures
};

#endif
