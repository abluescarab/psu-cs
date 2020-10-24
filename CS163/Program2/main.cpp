/* Alana Gilston - 10/16/2020 - CS163 - Program 2
 * main.cpp
 *
 * This is the main function to test the alert_manager class.
 */
#include <iostream>
#include <cstring>
#include <limits> // for numeric_limits::max()
#include <ios> // for streamsize
#include "alert_manager.h"
#include "utils.h"
#define MAX_INPUT_LENGTH 1024

using namespace std;



// Validate the user's input and prompt if it's incorrect.
// INPUT:
//  max_input: The maximum integer allowed for the main menu
// OUTPUT:
//  Returns the option entered by the user.
int validate_input(const int max_input) {
    int input;

    cout << "> ";
    cin.width(1);
    cin >> input;

    cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    if(cin.fail() || input < 1 || input > max_input) {
        cout << "Invalid input. Please try again." << endl;
        return validate_input(max_input);
    }

    cout << endl;
    return input;
}



// Validate a user's yes/no answer.
// INPUT:
//  input: The user's input
// OUTPUT:
//  0 if the user's input is no/other
//  1 if the user's input is yes
int validate_yes_no(void) {
    char input;

    cin.width(1);
    cin >> input; 

    cin.clear();
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    // check two conditions: that input was not invalid, and that input was 
    // equal to 'y'
    return !cin.fail() && (tolower(input) == 'y');
}



int main() {
    // input variables
    int option = 0;
    int result_count = 0;
    // new alert details
    char date[MAX_INPUT_LENGTH] = "";
    char time[MAX_INPUT_LENGTH] = "";
    char message[MAX_INPUT_LENGTH] = "";
    char agency[MAX_INPUT_LENGTH] = "";
    char origin[MAX_INPUT_LENGTH] = "";
    // alert objects
    alert new_alert;
    alert_manager * manager = new alert_manager;

    do {
        cout << "Alert System" << endl;
        cout << "----------------------" << endl;
        cout << "1) Broadcast new alert" << endl;
        cout << "2) Cancel last alert" << endl;
        cout << "3) Display last alert" << endl;
        cout << "4) Display all alerts" << endl;
        cout << "5) Clear all alerts" << endl;
        cout << "6) Quit" << endl << endl;

        option = validate_input(6);

        switch(option) {
            case 1: // broadcast new alert
                while(char_array_empty(date)) {
                    cout << "Date: ";
                    cin.getline(date, MAX_INPUT_LENGTH);
                }

                while(char_array_empty(time)) {
                    cout << "Time: ";
                    cin.getline(time, MAX_INPUT_LENGTH);
                }

                while(char_array_empty(message)) {
                    cout << "Message: ";
                    cin.getline(message, MAX_INPUT_LENGTH);
                }

                while(char_array_empty(agency)) {
                    cout << "Reporting agency: ";
                    cin.getline(agency, MAX_INPUT_LENGTH);
                }

                while(char_array_empty(origin)) {
                    cout << "Origin: ";
                    cin.getline(origin, MAX_INPUT_LENGTH);
                }

                new_alert.copy_from(date, time, message, agency, origin);
                manager->push(new_alert);
                cout << "Successfully added new alert." << endl;
                break;
            case 2: // cancel last alert
                if(manager->is_empty()) {
                    cout << "No alerts to cancel." << endl;
                    break;
                }

                cout << "Are you sure you want to cancel the last alert? (y/n) ";

                if(validate_yes_no()) {
                    manager->pop();
                    cout << "Successfully canceled last alert." << endl;
                }
                else
                    cout << "Did not cancel any alerts." << endl;

                break;
            case 3: // display last alert
                if(!manager->peek(new_alert)) {
                    cout << "No alerts to display." << endl;
                    break;
                }

                new_alert.display();
                break;
            case 4: // display all alerts
                result_count = manager->display();

                if(result_count == 0)
                    cout << "No alerts to display." << endl;
                else
                    cout << "Found " << result_count << " alerts." << endl;
                break;
            case 5: // clear all alerts
                if(manager->is_empty()) {
                    cout << "No alerts to clear." << endl;
                    break;
                }

                cout << "Are you sure you want to clear all alerts? (y/n) ";
                
                if(validate_yes_no()) {
                    result_count = manager->clear();
                    cout << "Cleared " << result_count << " alerts." << endl;
                }
                else
                    cout << "Did not clear any alerts." << endl;

                break;
            case 6: // exit case
                break;
            default:
                option = validate_input(6); 
                break;
        }

        // reset variables
        strcpy(origin, "");
        strcpy(date, "");
        strcpy(time, "");
        strcpy(message, "");
        strcpy(agency, "");

        // print a blank line
        cout << endl;
    } while(option != 6);

    delete manager;
    return 0;
}
