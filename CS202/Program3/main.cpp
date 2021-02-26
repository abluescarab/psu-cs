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
    computer,
    none
};



// Display the main menu.
// INPUT:
//  menu: the menu to display
// OUTPUT:
//  Returns the user-selected option.
int display_menu(int menu, device * & current_device, device_type & submenu) {
    int max_option = -1;
    pager * current_pager = nullptr;
    cell_phone * current_phone = nullptr;
    computer * current_computer = nullptr;

    switch(menu) {
        case 1: // contact menu
            cout << "1) Display contact info" << endl;
            cout << "2) Add device" << endl;
            cout << "3) Remove device" << endl;
            cout << "4) Clear devices" << endl;
            cout << "5) Change contact name" << endl;
            cout << "6) Back" << endl;

            max_option = 6;
            break;
        case 2: // device menu
            cout << "1) Display device info" << endl;
            cout << "2) Send message to this device" << endl;
            cout << "3) Display sent messages" << endl;
            cout << "4) Clear sent messages" << endl;

            current_pager = dynamic_cast<pager*>(current_device);
            current_phone = dynamic_cast<cell_phone*>(current_device);
            current_computer = dynamic_cast<computer*>(current_device);

            if(current_pager) {
                submenu = device_type::pager;
                cout << "5) Change pager number" << endl;
                cout << "6) Change if pager supports text messages" << endl;
                cout << "7) Change if pager supports two-way messages" << endl;
            }
            else if(current_phone) {
                submenu = device_type::cell_phone;
                cout << "5) Change cell network" << endl;
                cout << "6) Change phone number" << endl;
                cout << "7) Change phone type" << endl;
            }
            else if(current_computer) {
                submenu = device_type::computer;
                cout << "5) Add a program" << endl;
                cout << "6) Remove a program" << endl;
                cout << "7) Clear all programs" << endl;
            }

            cout << "8) Back" << endl;
            
            max_option = 8;
            break;
        case 0: // main menu
        default:
            cout << "1) Manage contact" << endl;
            cout << "2) Display contacts" << endl;
            cout << "3) Add contact" << endl;
            cout << "4) Remove contact" << endl;
            cout << "5) Clear contacts" << endl;
            cout << "6) Quit" << endl;

            max_option = 6;
            break;
    }

    return validate_input(1, max_option);
}



// Display the main menu.
// INPUT:
//  menu: the menu to display
// OUTPUT:
//  Returns the user-selected option.
int display_menu(int menu) {
    device * current = nullptr;
    device_type submenu = device_type::none;

    return display_menu(menu, current, submenu);
}



// Add a device.
// INPUT:
//  current_contact: contact to add devices to
// OUTPUT:
//  0 if unsuccessful.
//  1 if successful.
int add_device(contact & current_contact) {
    int option = 0;
    bool quit = false;
    char input[INPUT_MAX] = "";
    // individual devices
    pager * new_pager = nullptr;
    cell_phone * new_phone = nullptr;
    computer * new_computer = nullptr;
    // all device info
    cpp_string name;
    int price = 0;
    // pager info
    cpp_string number;
    bool supports_text;
    bool two_way;
    // phone info
    cpp_string network;
    os_type phone_type;
    // computer info
    cpp_string program_name;
    cpp_string username;
    program * new_program = nullptr;

    cout << "What kind of device do you want to add?" << endl;
    cout << "1) Pager" << endl;
    cout << "2) Cell phone" << endl;
    cout << "3) Computer" << endl;

    option = validate_input(1, 3);

    cout << endl;
    cout << "Device name: ";

    cin.getline(input, INPUT_MAX);
    name = input;

    if(name.is_empty()) {
        cout << "Cancelled last device." << endl;
        return 0;
    }

    if(option == 1) { // pager
        cout << "Device subscription price: ";
        price = validate_input(0, INT_MAX, false);

        while(number.is_empty()) {
            cout << "Pager number: ";
            cin.getline(input, INPUT_MAX);
            number = input;
        }

        cout << "Does the pager support text messages? (y/n) ";
        supports_text = validate_yes();

        cout << "Does the pager support two-way messaging? (y/n) ";
        two_way = validate_yes();

        new_pager = new pager(name, price, number, supports_text, two_way);
        current_contact.add_device(*new_pager);
        delete new_pager;
    }
    else if(option == 2) { // cell phone
        cout << "Device subscription price: ";
        price = validate_input(0, INT_MAX, false);

        while(network.is_empty()) {
            cout << "Cell phone network: ";
            cin.getline(input, INPUT_MAX);
            network = input;
        }

        while(number.is_empty()) {
            cout << "Cell phone number: ";
            cin.getline(input, INPUT_MAX);
            number = input;
        }

        cout << "What type of phone is it?" << endl;
        cout << "1) iPhone" << endl;
        cout << "2) Android" << endl;
        cout << "3) Windows Phone" << endl;
        cout << "4) Other OS" << endl;
        cout << "5) Feature phone (not smartphone)" << endl;

        option = validate_input(1, 5);
        phone_type = static_cast<os_type>(option);
        new_phone = new cell_phone(name, price, network, number, phone_type);
        current_contact.add_device(*new_phone);
        delete new_phone;
    }
    else { // computer
        new_computer = new computer(name);

        while(!quit) {
            cout << "Do you want to add a program to the computer? (y/n) ";

            if(validate_yes()) {
                while(program_name.is_empty()) {
                    cout << "Program name: ";
                    cin.getline(input, INPUT_MAX);
                    program_name = input;
                }

                while(username.is_empty()) {
                    cout << "Username: ";
                    cin.getline(input, INPUT_MAX);
                    username = input;
                }

                new_program = new program(program_name, username);
                new_computer->add_program(*new_program);
                delete new_program;
                cout << "Added " << program_name << " to " << name << "." << endl;
            }
            else 
                quit = true;
        }

        current_contact.add_device(*new_computer);
        delete new_computer;
    }

    cout << "Successfully added " << name << " to ";
    current_contact.display_name();
    cout << endl;
    return 1;
}



int main() {
    bool quit = false;
    char input[INPUT_MAX] = "";
    int option = -1;
    int menu = 0;
    int submenu = 0;
    int result = 0;

    // individual details
    cpp_string name;
    cpp_string message;

    contact_list * contacts = new contact_list();
    contact * current_contact = nullptr;
    device * current_device = nullptr;

    do {
        if(menu == 0) { // main menu
            cout << "Contact Device List" << endl;
            cout << "-------------------" << endl;
        }
        else if(menu == 1) { // contact menu
            cout << "Current contact: ";
            current_contact->display_name();
            cout << "------------------" << endl;
        }
        else if(menu == 2) { // device menu
            cout << "Current device: ";
            current_device->display_name();
            cout << "-----------------" << endl;
        }

        option = display_menu(menu);
        cout << endl;

        if(menu == 0) { // main menu 
            switch(option) {
                case 1: // manage contact
                    cout << "Contact name: ";
                    cin.getline(input, INPUT_MAX);
                    name = input;

                    if(name.is_empty()) {
                        cout << "Invalid name." << endl;
                        break;
                    }
                    
                    if(contacts->find(name, current_contact)) {
                        menu = 1;
                    }
                    else {
                        cout << "Contact not found." << endl;
                    }

                    break;
                case 2: // display contacts
                    result = contacts->display();
                    cout << endl;
                    cout << "Displayed " << result << " contact(s)." << endl;
                    break;
                case 3: // add contact
                    cout << "Contact name: ";
                    cin.getline(input, INPUT_MAX);
                    name = input;

                    if(name.is_empty()) {
                        cout << "Invalid name." << endl;
                        break;
                    }

                    delete current_contact;
                    current_contact = new contact(name);

                    cout << "Do you want to add a device to this contact? (y/n) ";

                    if(validate_yes()) {
                        add_device(*current_contact);
                    }

                    contacts->add(*current_contact);
                    cout << "Successfully added " << name << "." << endl;
                    break;
                case 4: // remove contact
                    cout << "Contact name: ";
                    cin.getline(input, INPUT_MAX);
                    name = input;

                    if(name.is_empty()) {
                        cout << "Invalid name." << endl;
                        break;
                    }

                    if(contacts->remove(name))
                        cout << "Successfully removed " << name << "." << endl;
                    else
                        cout << name << " not found." << endl;
                    break;
                case 5: // clear contacts
                    cout << "Are you sure you want to clear all contacts? (y/n) " << endl;

                    if(validate_yes()) {
                        result = contacts->clear();

                        if(result > 0)
                            cout << "Successfully deleted " << result << " contacts." << endl;
                        else
                            cout << "No contacts to delete." << endl;
                    }

                    break;
                case 6: // quit
                    quit = true;
                    break;
                default:
                    option = display_menu(menu);
                    break;
            }
        }
        else if(menu == 1) { // contact menu
            switch(option) {
                case 1: // display contact info
                    break;
                case 2: // add device
                    break;
                case 3: // remove device
                    break;
                case 4: // clear devices
                    break;
                case 5: // change contact name
                    break;
                case 6: // back
                    menu = 0;
                    break;
                default:
                    option = display_menu(menu);
                    break;
            }
        }
        else if(menu == 2) { // device menu
            switch(option) {
                case 1: // display device info
                    if(!current_device || !current_device->display())
                        cout << "Device is empty." << endl;
                    break;
                case 2: // send message
                    cout << "Message to send: ";
                    cin.getline(input, INPUT_MAX);
                    message = input;

                    if(message.is_empty()) {
                        cout << "Cancelled sending a message." << endl;
                        break;
                    }

                    current_device->send_message(message);
                    cout << "Successfully sent message." << endl;
                    break;
                case 3: // display sent messages
                    result = current_device->display_messages();

                    if(result == 0)
                        cout << "No messages found." << endl;
                    else
                        cout << "Displayed " << result << " messages." << endl;
                    break;
                case 4: // clear sent messages
                    current_device->clear_messages();
                    break;
                case 8: // back
                    menu = 1;
                    break;
                default:
                    option = display_menu(menu);
                    break;
            }
        }

        cout << endl;
    } while(!quit);

    delete contacts;

    if(current_contact)
        delete current_contact;

    if(current_device)
        delete current_device;
    return 0;
}
