/* Alana Gilston - 10/7/2020 - CS163 - Program 1
 * main.cpp
 *
 * This is the main function to test the priority_manager class.
 */
#include <iostream>
#include <cstring>
#include <limits> // for numeric_limits::max()
#include <ios> // for streamsize
#include "priority_manager.h"
#include "utils.h"
#define MAX_INPUT_LENGTH 1024

using namespace std;



// Validate the user's input and prompt if it's incorrect.
// INPUT:
//  max_input: The maximum integer allowed
// OUTPUT:
//  Returns the number entered by the user.
int input_validation(int max_input) {
    int input;

    cout << "> ";
    cin.width(1);
    cin >> input;

    cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    if(cin.fail() || input < 1 || input > max_input) {
        cout << "Invalid input. Please try again." << endl;
        return input_validation(max_input);
    }

    cout << endl;
    return input;
}



int main() {
    // input variables
    int option = 0;
    int result_count = 0;
    char input[MAX_INPUT_LENGTH] = "";
    char priority[MAX_INPUT_LENGTH] = "";
    // new project details
    char name[MAX_INPUT_LENGTH] = "";
    float cost = 0.0;
    char length[MAX_INPUT_LENGTH] = "";
    char contractor[MAX_INPUT_LENGTH] = "";
    char due[MAX_INPUT_LENGTH] = "";
    char notes[MAX_INPUT_LENGTH] = "";
    // project objects
    project new_project;
    priority_manager * manager = new priority_manager;

    do {
        cout << "Project Manager" << endl;
        cout << "------------------" << endl;
        cout << "1) List priorities" << endl;
        cout << "2) Add priority" << endl;
        cout << "3) Remove priority" << endl;
        cout << "4) List projects in priority" << endl;
        cout << "5) Add project" << endl;
        cout << "6) Remove project" << endl;
        cout << "7) Exit" << endl;

        option = input_validation(7);

        switch(option) {
            case 1: // list the priorities
                result_count = manager->display();

                if(result_count == 0)
                    cout << "No priorities to display." << endl;
                else
                    cout << "Found " << result_count << " priorities." << endl;

                break;
            case 2: // add a priority
                cout << "Enter a new priority: ";
                cin.getline(input, MAX_INPUT_LENGTH);

                if(manager->contains(input)) 
                    cout << "Priority already exists." << endl;
                else if(!manager->add(input))
                    cout << "Invalid priority name." << endl;
                else
                    cout << "Successfully created \"" << input << "\"." << endl;

                break;
            case 3: // remove a priority
                cout << "Enter a priority to remove: ";
                cin.getline(input, MAX_INPUT_LENGTH);

                // return to the main menu on failed input - allows the user to
                // back out with invalid input and/or review list of priorities
                if(!manager->remove(input))
                    cout << "That priority does not exist." << endl;
                else
                    cout << "Successfully removed \"" << input << "\"." << endl;

                break;
            case 4: // list projects in a priority
                cout << "Enter a priority: ";
                cin.getline(input, MAX_INPUT_LENGTH);                
                cout << endl;

                result_count = manager->display(input);

                if(result_count == 0)
                    cout << "No projects to display." << endl;
                else
                    cout << "Found " << result_count << " projects." << endl;

                break;
            case 5: // add a project
                cout << "Enter the priority to add to: ";
                cin.getline(priority, MAX_INPUT_LENGTH);

                // create the priority if input is valid and it does not exist
                if(!manager->contains(priority) && !manager->add(priority)) {
                    cout << "Invalid priority name." << endl;
                    break;
                }

                while(char_array_empty(name)) {
                    cout << "Project name: ";
                    cin.getline(name, MAX_INPUT_LENGTH);
                }

                while(cost < 0.01) {
                    cout << "Estimated cost: ";
                    cin >> cost;
                    cin.clear();
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                }

                while(char_array_empty(length)) {
                    cout << "Estimated time to finish: "; 
                    cin.getline(length, MAX_INPUT_LENGTH);
                }

                cout << "Contractor(s) to hire (if any): ";
                cin.getline(contractor, MAX_INPUT_LENGTH);

                while(char_array_empty(due)) {
                    cout << "Due date: ";
                    cin.getline(due, MAX_INPUT_LENGTH);
                }

                cout << "Notes: ";
                cin.getline(notes, MAX_INPUT_LENGTH);

                new_project.copy_from(name, cost, length, contractor, due, notes);
                manager->add_project(priority, new_project);
                break;
            case 6: // remove a project
                cout << "Enter the priority to remove from: ";
                cin.getline(priority, MAX_INPUT_LENGTH);

                if(!manager->contains(priority))
                    cout << "That priority does not exist." << endl;
                else {
                    cout << "Enter a project to remove: ";
                    cin.getline(input, MAX_INPUT_LENGTH); 

                    if(!manager->remove_project(priority, input))
                        cout << "Project does not exist." << endl;
                    else
                        cout << "Successfully deleted project \"" << input << "\"." << endl;
                }

                break;
            case 7: // exit case
                break;
            default:
                option = input_validation(7);
                break;
        }

        // reset project variables
        strcpy(name, "");
        cost = 0.0;
        strcpy(length, "");
        strcpy(contractor, "");
        strcpy(due, "");
        strcpy(notes, "");

        // blank line after every cout
        cout << endl;
    } while(option != 7);

    delete manager;
    return 0;
}
