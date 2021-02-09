/* Alana Gilston - 2/2/2021 - CS202 - Program 2
 * main.cpp
 *
 * This is the main file to manage the "calendar" class, which keeps a user's 
 * calendar with various tasks and to-dos.
 */
#include <iostream>
#include <cstring>
#include <climits>
#include "calendar.h"
#include "utils.h"
#define INPUT_MAX 1024

using namespace std;



// Display the main menu.
// INPUT:
//  menu: the menu to display
// OUTPUT:
//  Returns the user-selected option.
int display_menu(int menu) {
    int max_option = -1;

    switch(menu) {
        case 1: // manage day
            cout << "1) Display reminders" << endl;
            cout << "2) Add reminder" << endl;
            cout << "3) Remove reminder" << endl;
            cout << "4) Clear reminders" << endl;
            cout << "5) Back" << endl;
            
            max_option = 5;
            break;
        case 0:
        default: // main menu
            cout << "1) Display calendar" << endl;
            cout << "2) Manage day" << endl;
            cout << "3) Add day" << endl;
            cout << "4) Remove day" << endl;
            cout << "5) Clear all days" << endl;
            cout << "6) Quit" << endl;

            max_option = 6;
            break;
    }

    return validate_input(1, max_option);
}



int main(void) {
    // main variables
    bool quit = false;
    char input[INPUT_MAX] = "";
    int option = -1;
    int menu = 0;
    
    // individual reminders
    /*char text[INPUT_MAX] = "";
    char time[INPUT_MAX] = "";
    char location[INPUT_MAX] = "";
    char instructor[INPUT_MAX] = "";*/
    char * text = nullptr;
    char * time = nullptr;
    char * location = nullptr;
    char * instructor = nullptr;
    int priority = 0;
    int return_value = -1;

    // calendar-specific
    calendar * todo = new calendar();
    note * new_note = nullptr;
    char * current_day = nullptr;

    cout << "Calendar" << endl;
    cout << "--------" << endl;

    do {
        option = display_menu(menu);

        if(menu == 0) { // main menu
            switch(option) {
                case 1: // display calendar
                    return_value = todo->display();
                    cout << "Found " << return_value << " day(s)." << endl;
                    break;
                case 2: // manage day
                    cout << "Date: ";
                    cin.getline(input, INPUT_MAX);

                    if(!todo->has_day(input)) {
                        cout << "Date does not exist." << endl;
                        break;
                    }

                    copy_char_array(current_day, input);
                    menu = 1;
                    break;
                case 3: // add day
                    cout << "Date: ";
                    cin.getline(input, INPUT_MAX);

                    if(!todo->add_day(input)) {
                        cout << "Date already exists." << endl;
                        break;
                    }

                    copy_char_array(current_day, input);
                    cout << "Successfully added " << current_day << "." << endl;
                    menu = 1;
                    break;
                case 4: // remove day
                    cout << "Date: ";
                    cin.getline(input, INPUT_MAX);

                    if(!todo->remove_day(input)) {
                        cout << "Date does not exist." << endl;
                        break;
                    }

                    cout << "Successfully removed " << input << "." << endl;
                    break;
                case 5: // clear all days
                    cout << "Are you sure you want to clear the calendar? (y/n) ";

                    if(!validate_yes())
                        break;

                    cout << "Cleared " << todo->clear() << " days." << endl;
                    break;
                case 6: // quit
                    quit = true;
                    break;
                default:
                    option = display_menu(menu);
                    break;
            }
        }
        else { // day menu
            switch(option) {
                case 1: // display reminders
                    return_value = todo->display_day(current_day);
                    cout << "Found " << return_value << " reminder(s)." << endl;
                    break;
                case 2: // add reminder
                    cout << "Type of reminder:" << endl;
                    cout << "1) Appointment" << endl;
                    cout << "2) Class session" << endl;
                    cout << "3) Task" << endl;

                    option = validate_input(1, 3);

                    cout << "Reminder text: ";
                    cin.getline(input, INPUT_MAX);
                    copy_char_array(text, input);

                    new_note = new note(text);

                    cout << "Reminder time: ";
                    cin.getline(input, INPUT_MAX);
                    copy_char_array(time, input);

                    if(option == 1) { // appointment
                        cout << "Appointment location: ";
                        cin.getline(input, INPUT_MAX);
                        copy_char_array(location, input);

                        todo->add_reminder(current_day, 
                                *(reminder *)new appointment(time, 
                                    location, 
                                    *new_note));
                        cout << "Successfully added new appointment to " 
                            << current_day << "." << endl;
                    }
                    else if(option == 2) { // class session
                        cout << "Class instructor: ";
                        cin.getline(input, INPUT_MAX);
                        copy_char_array(instructor, input);

                        cout << "Class location: ";
                        cin.getline(input, INPUT_MAX);
                        copy_char_array(location, input);

                        todo->add_reminder(current_day, 
                                *(reminder *)new class_session(time, 
                                    location, 
                                    instructor, 
                                    *new_note));
                        cout << "Successfully added new class session to " 
                            << current_day << "." << endl;
                        
                    }
                    else if(option == 3) { // task
                        cout << "Task priority (#): ";

                        priority = validate_input(1, INT_MAX, false);

                        todo->add_reminder(current_day, 
                                *(reminder *)new task(time, 
                                    priority, 
                                    *new_note));
                        cout << "Successfully added new task to " 
                            << current_day << "." << endl;
                    }

                    break;
                case 3: // remove reminder
                    break;
                case 4: // clear reminders
                    break;
                case 5: // back
                    menu = 0;
                    break;
                default:
                    option = display_menu(menu);
                    break;
            }
        }


        strcpy(input, "");
        
        cout << endl;
    } while(!quit);

    delete current_day;
    delete todo;
    delete [] text;
    delete [] time;
    delete [] location;
    delete [] instructor;
    delete new_note;
    delete [] current_day;
    return 0;
}
