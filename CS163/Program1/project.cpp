/* Alana Gilston - 10/7/2020 - CS163 - Program 1
 * project.h
 *
 * This is the implementation file for the "project" class. The "project" class
 * is one that manages data members for a user-defined project.
 */
#include <iostream>
#include <cstring>
#include <iomanip>
#include "project.h"
#include "utils.h"

using namespace std;



project::project() {
    name = nullptr;
    cost = 0.0;
    length = nullptr;
    contractor = nullptr;
    due = nullptr;
    notes = nullptr;
}



project::~project() {
    delete [] name;
    cost = 0.0;
    delete [] length;

    if(contractor)
        delete [] contractor;

    delete [] due;

    if(notes)
        delete [] notes;
}



// Copy the project data from user input.
// INPUT:
//  name: The project's name
//  cost: Estimated cost of the project
//  length: How long the project will take
//  contractor: Contractor(s), if any, to hire
//  due: A due date
//  notes: Any notes
// OUTPUT:
//  Returns 0 if the project fails due to missing data and 1 if it succeeds.
int project::copy_from(const char * new_name, const float new_cost, 
        const char * new_length, const char * new_contractor,
        const char * new_due, const char * new_notes) {
    if(char_array_empty(new_name) ||
            new_cost < 0.01 ||
            char_array_empty(new_length) ||
            char_array_empty(new_due))
        return 0;

    copy_char_array(name, new_name);
    cost = new_cost;
    copy_char_array(length, new_length);
    copy_char_array(contractor, new_contractor);
    copy_char_array(due, new_due);
    copy_char_array(notes, new_notes);

    return 1;
}



// Copy the project data from another project.
// INPUT:
//  project_to_copy: The project to copy data from
// OUTPUT:
//  Returns 0 if the copy fails or there is missing data; returns 1 if copy is successful
int project::copy_from(const project & project_to_copy) {
    if(project_to_copy.is_empty()) return 0;

    copy_char_array(name, project_to_copy.name);
    cost = project_to_copy.cost;
    copy_char_array(length, project_to_copy.length);
    copy_char_array(contractor, project_to_copy.contractor);
    copy_char_array(due, project_to_copy.due);
    copy_char_array(notes, project_to_copy.notes); 

    return 1;
}



// Display the project.
// OUTPUT:
//  Returns 0 if the project exists and 1 if not.
int project::display(void) const {
    if(is_empty()) return 0;

    cout << "Project: " << name << endl;
    cout << fixed << setprecision(2) << "Estimated cost: $" << cost << endl;
    cout << "Estimated length: " << length << " (due " << due << ")" << endl;

    if(char_array_empty(contractor))
        cout << "Contractor(s): N/A" << endl;
    else
        cout << "Contractor(s): " << contractor << endl;

    if(char_array_empty(notes))
        cout << "Notes: N/A" << endl;
    else {
        cout << "Notes:" << endl;
        cout << notes << endl;
    }

    cout << endl;
    return 1;
}



// Checks if the project is empty or has data.
// OUTPUT:
//  Returns 0 if the project is empty and 1 if it has data.
int project::is_empty(void) const {
    // if any required data is empty, then the data is invalid
    return char_array_empty(name) ||       
        cost < 0.01 ||
        char_array_empty(length) ||
        char_array_empty(due); 
}



// Checks if the project matches the provided name.
// OUTPUT:
//  Returns 0 if the project is not a match and 1 if it is.
int project::is_match(const char * project_name) const {
    return strcmp(name, project_name) == 0;
}
