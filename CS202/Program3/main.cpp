/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * main.cpp
 *
 * This is the implementation file to test the contact_list class.
 */
#include <iostream>
#include <climits>
#include "contact_list.h"
#include "utils.h"
#define INPUT_MAX 1024

using namespace std;



enum class device_type {
    pager,
    cell_phone,
    computer
};



// Display the main menu.
// INPUT:
//  menu: the menu to display
// OUTPUT:
//  Returns the user-selected option.
int display_menu(int menu, const device & current_device, device_type * & submenu) {
    int max_option = -1;

    switch(menu) {
        case 1: // contact menu
            cout << "1) Display contact info" << endl;
            cout << "2) Add device" << endl;
            cout << "3) Remove device" << endl;
            cout << "4) Clear devices" << endl;
            cout << "5) Change contact name" << endl;
            cout << "6) Change contact address" << endl;
            cout << "7) Back" << endl;
            break;
        case 2: // device menu
            cout << "1) Display device info" << endl;
            cout << "2) Send message to this device" << endl;
            cout << "3) Display sent messages" << endl;
            cout << "4) Clear sent messages" << endl;
            cout << "5) Change device subscription price" << endl;

            /*if(dynamic_cast<pager *>(current_device) != nullptr) {
                submenu = device_type::pager;
                cout << "6) Change pager number" << endl;
                cout << "7) Change if pager supports text messages" << endl;
                cout << "8) Change if pager supports two-way messages" << endl;
            }
            else if(dynamic_cast<cell_phone *>(current_device) != nullptr) {
                submenu = device_type::cell_phone;
                cout << "6) Change cell network" << endl;
                cout << "7) Change phone number" << endl;
                cout << "8) Change phone type" << endl;
            }
            else if(dynamic_cast<computer *>(current_device) != nullptr) {
                submenu = device_type::computer;
                cout << "6) Add a program" << endl;
                cout << "7) Remove a program" << endl;
                cout << "8) Clear all programs" << endl;
            }*/

            cout << "9) Back" << endl;
            break;
        case 0: // main menu
            cout << "1) Manage contact" << endl;
            cout << "2) Display contacts" << endl;
            cout << "3) Add contact" << endl;
            cout << "4) Remove contact" << endl;
            cout << "5) Clear contacts" << endl;
            cout << "6) Quit" << endl;
        default:
            break;
    }

    return validate_input(1, max_option);
}



int main() {
    contact_list * my_contacts = new contact_list();
#define CONTACT_COUNT 9
    contact ** contacts = new contact*[CONTACT_COUNT] {
        new contact("Alana", "N 3rd St."),//0
        new contact("Adrienne", "Oxbow Loop"),//1
        new contact("Griselda", "Barr Av."),//2
        new contact("Philip", "N 3rd St."),//3
        new contact("Maggie", "that apartment complex"),//5
        new contact("Jake", "test"),//7
        new contact("Oscar", "test"),//8
        new contact("Karen", "test"),//9
        new contact("Agamemnon", "test")
    };

    for(int i = 0; i < CONTACT_COUNT; ++i)
        my_contacts->add(*contacts[i]);

    my_contacts->display();
    cout << endl;

    for(int i = 0; i < CONTACT_COUNT; ++i) {
        cout << "Removing ";
        contacts[i]->display();
        cout << endl;
        my_contacts->remove(*contacts[i]);
        my_contacts->display();
        cout << endl;
        cin.get();
    }

    for(int i = 0; i < CONTACT_COUNT; ++i)
        delete contacts[i];

    delete [] contacts;
    delete my_contacts;
    return 0;
}
