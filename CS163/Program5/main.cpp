/* Alana Gilston - 11/23/2020 - CS163 - Program 5
 * main.cpp
 *
 * This is the main function to test the course_plan class.
 */
#include <iostream>
#include <cstring>
#include "course.h"
#include "course_plan.h"
#include "utils.h"
#define MAX_MENU_OPTION 6
#define MAX_INPUT 256

using namespace std;



int main(void) {
    int option = 0;
    int return_value = 0;
    bool quit = false;
    char name[MAX_INPUT] = "";
    char code[MAX_INPUT] = "";
    char end_code[MAX_INPUT] = "";
    course new_course;
    course_plan * planner = new course_plan;

    while(!quit) {
        cout << "Course Planner" << endl;
        cout << "---------------------------" << endl;
        cout << "1) Add a course" << endl;
        cout << "2) Connect existing courses" << endl;
        cout << "3) Display all courses" << endl;
        cout << "4) Display courses by department" << endl;
        cout << "5) Display connected courses" << endl;
        cout << "6) Quit" << endl;

        option = validate_input(1, MAX_MENU_OPTION);

        switch(option) {
            case 1: // add a course
                cout << "Course code (e.g., CS163): ";
                cin.getline(code, MAX_INPUT);

                // cancel by entering nothing
                if(char_array_empty(code)) {
                    cout << "Invalid course code." << endl;
                    break;
                }

                cout << "Course name (e.g. Data Structures): ";
                cin.getline(name, MAX_INPUT);

                if(!new_course.copy(code, name)) {
                    cout << "Invalid course data." << endl;
                    break;
                }

                cout << endl;

                if(planner->add(new_course) == 1)
                    cout << "Added " << code << " to course plan." << endl;
                else
                    cout << "Couldn't add " << code << " to course plan." << endl;

                break;
            case 2: // connect existing courses
                cout << "First course code (e.g., CS163): ";
                cin.getline(code, MAX_INPUT);

                if(char_array_empty(code)) {
                    cout << "Invalid course code." << endl;
                    break;
                }

                cout << "Second course code (e.g., CS202): ";
                cin.getline(end_code, MAX_INPUT);
                cout << endl;

                if(planner->connect(code, end_code) == 1)
                    cout << "Connected " << code << " to " << end_code << "." << endl;
                else
                    cout << "Couldn't connect " << code << " to " << end_code << "." << endl;

                break;
            case 3: // display all courses
                return_value = planner->display();
                cout << endl;

                if(return_value == 0)
                    cout << "No courses found." << endl;
                else
                    cout << "Found " << return_value << " courses." << endl;
                break;
            case 4: // display courses by department
                cout << "Course department (e.g., CS): ";
                cin.getline(code, MAX_INPUT);

                return_value = planner->display_by_department(code);

                if(return_value == -1) {
                    cout << "Invalid course department." << endl;
                    break;
                }

                cout << endl;

                if(return_value == 0)
                    cout << "Couldn't find any courses in that department." << endl;
                else
                    cout << "Found " << return_value << " courses in that department." << endl;

                break;
            case 5: // display connected courses
                cout << "Course code: ";
                cin.getline(code, MAX_INPUT);

                return_value = planner->display_adjacent(code);

                if(return_value == -1) {
                    cout << "Course not found." << endl;
                    break;
                }

                cout << endl;

                if(return_value == 0)
                    cout << "No connected courses found." << endl;
                else
                    cout << "Found " << return_value << " connected courses." << endl;

                break;
            case 6: // quit
                quit = true;
                break;
            default:
                option = validate_input(1, MAX_MENU_OPTION);
                break;
        }

        strcpy(code, "");
        strcpy(name, "");
        strcpy(end_code, "");

        cout << endl;
    }

    delete planner;
}
