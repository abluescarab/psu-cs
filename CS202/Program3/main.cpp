/* Alana Gilston - 2/16/2021 - CS202 - Program 3
 * main.cpp
 *
 *
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
            cout << "2) Change contact name" << endl;
            cout << "3) Change contact address" << endl;
            cout << "4) Add device" << endl;
            cout << "5) Remove device" << endl;
            cout << "6) Clear devices" << endl;
            cout << "7) Back" << endl;
            break;
        case 2: // device menu
            cout << "1) Display device info" << endl;
            cout << "2) Send message to this device" << endl;
            cout << "3) Display sent messages" << endl;
            cout << "4) Clear sent messages" << endl;
            cout << "5) Change device subscription price" << endl;

            if(dynamic_cast<pager *>(current_device) != nullptr) {
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
            }

            cout << "9) Back" << endl;
            break;
        case 0: // main menu
            cout << "1) Manage contact" << endl;
            cout << "2) Display contacts" << endl;
            cout << "3) Add contact" << endl;
            cout << "4) Remove contact" << endl;
            cout << "5) Clear contacts" << endl;
            cout << "6) Exit" << endl;
        default:
            break;
    }

    return validate_input(1, max_option);
}



int main() {
    

    return 0;
}
