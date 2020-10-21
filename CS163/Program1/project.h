/* Alana Gilston - 10/7/2020 - CS163 - Program 1
 * project.h
 *
 * This is the header file for the "project" class. The "project" class is one
 * that manages data members for a user-defined project.
 */
#ifndef PROJECT_H
#define PROJECT_H

class project {
    public:
        project();
        ~project();
        // Copy the project data from user input.
        int copy_from(const char * name, const float cost, const char * length,
                const char * contractor, const char * due, const char * notes);
        // Copy the project data from another project.
        int copy_from(const project & project_to_copy);
        // Display the project.
        int display(void) const;
        // Checks if the project is empty or has data.
        int is_empty(void) const;
        // Checks if the project matches the provided name.
        int is_match(const char * project_name) const;

    private:
        char * name; // name of the project
        float cost; // estimated cost
        char * length; // estimated length of time to complete
        char * contractor; // contractor(s), if any, to hire
        char * due; // due date
        char * notes; // any notes
};

#endif
