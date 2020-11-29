/* Alana Gilston - 11/16/2020 - CS163 - Program 4
 * main.cpp
 *
 * This is the main function to test the media_library class.
 */
#include <iostream>
#include <cstring>
#include "media.h"
#include "media_library.h"
#include "utils.h"
#define MAX_MENU_OPTION 8
#define MAX_INPUT 1024

using namespace std;



int main(void) {
    int option = 0;
    int return_value = 0;
    bool quit = false;
    bool again = false;
    char title[MAX_INPUT] = "";
    char last_title[MAX_INPUT] = "";
    char description[MAX_INPUT] = "";
    char course[MAX_INPUT] = "";
    media media_item;
    media_library * library = new media_library;

    while(!quit) {
        cout << "Media Library" << endl;
        cout << "--------------------------" << endl;
        cout << "1) Add an item" << endl;
        cout << "2) Display an item" << endl;
        cout << "3) Remove an item" << endl;
        cout << "4) Display items by course" << endl;
        cout << "5) Remove items by course" << endl;
        cout << "6) Display all items" << endl;
        cout << "7) Remove all items" << endl;
        cout << "8) Exit" << endl;

        option = validate_input(1, MAX_MENU_OPTION);

        switch(option) {
            case 1: // add an item
                do {
                    // clear input before starting again
                    strcpy(title, "");
                    cout << "Title: ";
                    cin.getline(title, MAX_INPUT);

                    // enter nothing to cancel, but still save last entry if 
                    // entering again
                    if(char_array_empty(title)) {
                        if(again) {
                            media_item.copy(last_title, description, course, "");
                            library->add(media_item);
                        }

                        cout << "Invalid title." << endl;
                        break;
                    }

                    // if we have previously entered a next item
                    if(again) {
                        media_item.copy(last_title, description, course, title);
                        library->add(media_item);
                    }

                    strcpy(description, "");
                    while(char_array_empty(description)) {
                        cout << "Description: ";
                        cin.getline(description, MAX_INPUT);
                    }

                    if(!again) {
                        strcpy(course, "");
                        while(char_array_empty(course)) {
                            cout << "Course: ";
                            cin.getline(course, MAX_INPUT);
                        }
                    }

                    cout << endl;
                    cout << "Add another media item in the same course? (y/n) ";

                    if(validate_yes()) {
                        again = true;
                        strcpy(last_title, title);
                        cout << endl;
                    }
                    else {
                        again = false;
                        media_item.copy(title, description, course, "");
                        library->add(media_item);
                    }
                    
                } while(again);

                break;
            case 2: // display an item
                cout << "Title: ";
                cin.getline(title, MAX_INPUT);

                // enter nothing to cancel
                if(char_array_empty(title)) {
                    cout << "Invalid title." << endl;
                    break;
                }

                if(!library->display_by_title(title))
                    cout << "Didn't find a media item with that title." << endl;
                break;
            case 3: // remove an item
                cout << "Title: ";
                cin.getline(title, MAX_INPUT);

                // enter nothing to cancel
                if(char_array_empty(title)) {
                    cout << "Invalid title." << endl;
                    break;
                }

                if(library->remove(title))
                    cout << "Successfully removed \"" << title << "\"." << endl;
                else
                    cout << "Couldn't find \"" << title << "\"." << endl;
                break;
            case 4: // display items by course
                cout << "Course: ";
                cin.getline(course, MAX_INPUT);

                // enter nothing to cancel
                if(char_array_empty(course)) {
                    cout << "Invalid course." << endl;
                    break;
                }

                return_value = library->display_by_course(course);

                if(return_value == 0)
                    cout << "Didn't find any items in that course." << endl;
                else
                    cout << "Found " << return_value << " items." << endl;
                break;
            case 5: // remove items by course
                cout << "Course: ";
                cin.getline(course, MAX_INPUT);

                // enter nothing to cancel
                if(char_array_empty(course)) {
                    cout << "Invalid course." << endl;
                    break;
                }

                return_value = library->remove_by_course(course);

                if(return_value == 0)
                    cout << "Didn't find any items to remove." << endl;
                else
                    cout << "Removed " << return_value << " items." << endl;

                break;
            case 6: // display all items
                return_value = library->display();

                if(return_value == 0)
                    cout << "No items to display." << endl;
                else
                    cout << "Found " << return_value << " items." << endl;
                break;
            case 7: // remove all items
                return_value = library->clear();

                if(return_value == 0)
                    cout << "Didn't remove any items." << endl;
                else
                    cout << "Removed " << return_value << " items." << endl;
                break;
            case 8: // exit
                quit = true;
                break;
            default:
                option = validate_input(1, MAX_MENU_OPTION);
                break;
        }

        strcpy(last_title, "");
        strcpy(title, "");
        strcpy(description, "");
        strcpy(course, "");

        cout << endl;
    }

    delete library;
}
