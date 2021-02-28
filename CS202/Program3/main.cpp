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



// Display the main menu.
// INPUT:
//  menu: the menu to display
// OUTPUT:
//  Returns the user-selected option.
int display_menu(const int menu, const device_type submenu) {
    int max_option = -1;

    switch(menu) {
        case 1: // contact menu
            cout << "1) Display contact info" << endl;
            cout << "2) Manage device" << endl;
            cout << "3) Add device" << endl;
            cout << "4) Remove device" << endl;
            cout << "5) Clear devices" << endl;
            cout << "6) Change contact name" << endl;
            cout << "7) Back" << endl;

            max_option = 7;
            break;
        case 2: // device menu
            cout << "1) Display device info" << endl;
            cout << "2) Send message to this device" << endl;
            cout << "3) Display sent messages" << endl;
            cout << "4) Clear sent messages" << endl;

            if(submenu == device_type::pager) {
                cout << "5) Change pager number" << endl;
                cout << "6) Change if pager supports text messages" << endl;
                cout << "7) Change if pager supports two-way messages" << endl;
            }
            else if(submenu == device_type::cell_phone) {
                cout << "5) Change cell network" << endl;
                cout << "6) Change phone number" << endl;
                cout << "7) Change phone type" << endl;
            }
            else if(submenu == device_type::computer) {
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



// Add a program.
// INPUT:
//  result: the new program
// OUTPUT:
//  1 when successful.
int add_program(program * & result) {
    char input[INPUT_MAX] = "";
    cpp_string program_name;
    cpp_string username;
    program * new_program = nullptr;

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
    result = new_program;
    return 1;
}



// Add a device.
// INPUT:
//  new_contact: contact to add devices to
//  result: the new device
// OUTPUT:
//  0 if unsuccessful.
//  1 if successful.
int add_device(device * & result) {
    int option = 0;
    bool quit = false;
    char input[INPUT_MAX] = "";
    // individual devices
    pager * new_pager = nullptr;
    cell_phone * new_phone = nullptr;
    computer * new_computer = nullptr;
    // all device info
    cpp_string name;
    // pager info
    cpp_string number;
    bool supports_text;
    bool two_way;
    // phone info
    cpp_string network;
    os_type phone_type;
    // computer info
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
        while(number.is_empty()) {
            cout << "Pager number: ";
            cin.getline(input, INPUT_MAX);
            number = input;
        }

        cout << "Does the pager support text messages? (y/n) ";
        supports_text = validate_yes();

        cout << "Does the pager support two-way messaging? (y/n) ";
        two_way = validate_yes();

        new_pager = new pager(name, number, supports_text, two_way);
    }
    else if(option == 2) { // cell phone
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
        phone_type = static_cast<os_type>(option - 1);
        new_phone = new cell_phone(name, network, number, phone_type);
    }
    else { // computer
        new_computer = new computer(name);

        while(!quit) {
            cout << "Do you want to add a program to the computer? (y/n) ";

            if(validate_yes()) {
                add_program(new_program);
                new_computer->add_program(*new_program);
                delete new_program;
                cout << "Added " << new_program->get_name() << " to " << name << 
                    "." << endl;
            }
            else 
                quit = true;
        }
    }

    if(new_pager) 
        result = new_pager;
    else if(new_phone)
        result = new_phone;
    else 
        result = new_computer;

    return 1;
}



int main() {
    bool quit = false;
    char input[INPUT_MAX] = "";
    int option = -1;
    int menu = 0;
    int result = 0;
    device_type submenu = device_type::none;

    // current vars
    cpp_string current_contact;
    cpp_string current_device;
    // individual details
    cpp_string name;
    cpp_string old_name;
    cpp_string message;
    cpp_string number;
    os_type phone_type = os_type::feature_phone;

    contact_list * contacts = new contact_list();
    contact * new_contact = nullptr;
    device * new_device = nullptr;
    program * new_program = nullptr;

    do {
        if(menu == 0) { // main menu
            cout << "Contact Device List" << endl;
            cout << "------------------------------------------------" << endl;
        }
        else if(menu == 1) { // contact menu
            cout << "Current contact: " << current_contact << endl;
            cout << "------------------------------------------------" << endl;
        }
        else if(menu == 2) { // device menu
            cout << "Current device: " << current_device << endl;
            cout << "------------------------------------------------" << endl;
        }

        option = display_menu(menu, submenu);

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

                    if(!contacts->has_contact(name)) {
                        cout << "Contact not found." << endl;
                        break;
                    }

                    current_contact = name;
                    menu = 1;
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
                        cout << "Cancelled adding new contact." << endl;
                        break;
                    }

                    new_contact = new contact(name);

                    cout << "Do you want to add a device to this contact? (y/n) ";

                    if(validate_yes()) {
                        if(!add_device(new_device))
                            cout << "Cancelled adding a new device." << endl;

                        new_contact->add_device(*new_device);
                        delete new_device;
                        new_device = nullptr;
                    }

                    contacts->add_contact(*new_contact);
                    cout << "Successfully added " << name << "." << endl;
                    delete new_contact;
                    new_contact = nullptr;

                    current_contact = name;
                    menu = 1;
                    break;
                case 4: // remove contact
                    cout << "Contact name: ";
                    cin.getline(input, INPUT_MAX);
                    name = input;

                    if(name.is_empty()) {
                        cout << "Cancelled removing contact." << endl;
                        break;
                    }

                    if(contacts->remove_contact(name))
                        cout << "Successfully removed " << name << "." << endl;
                    else
                        cout << name << " not found." << endl;
                    break;
                case 5: // clear contacts
                    cout << "Are you sure you want to clear all contacts? (y/n) " << endl;

                    if(validate_yes()) {
                        result = contacts->clear_contacts();

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
                    option = display_menu(menu, submenu);
                    break;
            }
        }
        else if(menu == 1) { // contact menu
            switch(option) {
                case 1: // display contact info
                    contacts->display_contact(current_contact);
                    break;
                case 2: // manage device
                    cout << "Device name: ";
                    cin.getline(input, INPUT_MAX);
                    name = input;

                    if(name.is_empty()) {
                        cout << "Invalid name." << endl;
                        break;
                    }

                    submenu = contacts->has_device(current_contact, name);

                    if(submenu == device_type::none) {
                        cout << "Device not found." << endl;
                        break;
                    }

                    current_device = name;
                    menu = 2;
                    break;
                case 3: // add device
                    if(!add_device(new_device)) {
                        cout << "Cancelled adding new device." << endl;
                        break;
                    }

                    contacts->add_device(current_contact, *new_device);
                    cout << "Successfully added " << new_device << "." << endl;

                    delete new_device;
                    new_device = nullptr;
                    break;
                case 4: // remove device
                    cout << "Device name: ";
                    cin.getline(input, INPUT_MAX);
                    name = input;

                    if(name.is_empty()) {
                        cout << "Cancelled removing device." << endl;
                        break;
                    }

                    if(contacts->remove_device(current_contact, name)) 
                        cout << "Sucessfully removed " << name << "." << endl;
                    else
                        cout << "Could not find " << name << "." << endl;

                    break;
                case 5: // clear devices
                    cout << "Are you sure you want to clear all devices from " << 
                        current_contact << "? (y/n) ";

                    if(!validate_yes()) {
                        cout << "Cancelled clearing all devices." << endl;
                        break;
                    }

                    result = contacts->clear_devices(current_contact);

                    if(result == 0)
                        cout << "No devices to remove." << endl;
                    else
                        cout << "Removed " << result << " devices." << endl;

                    break;
                case 6: // change contact name
                    cout << "Contact name: ";
                    cin.getline(input, INPUT_MAX);
                    name = input;

                    if(name.is_empty()) {
                        cout << "Cancelled changing contact name." << endl;
                        break;
                    }
                    
                    contacts->change_contact_name(current_contact, name);
                    cout << "Changed name to " << name << "." << endl;
                    current_contact = name;
                    break;
                case 7: // back
                    current_contact = "";
                    menu = 0;
                    break;
                default:
                    option = display_menu(menu, submenu);
                    break;
            }
        }
        else if(menu == 2) { // device menu
            switch(option) {
                case 1: // display device info
                    if(!contacts->display_device(current_contact, current_device))
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

                    if(contacts->send_message(current_contact, current_device, message))
                        cout << "Successfully sent message." << endl;
                    else
                        cout << "Failed to send message." << endl;

                    break;
                case 3: // display sent messages
                    result = contacts->display_messages(current_contact, current_device);

                    if(result == 0)
                        cout << "No messages found." << endl;
                    else
                        cout << "Displayed " << result << " messages." << endl;
                    break;
                case 4: // clear sent messages
                    cout << "Are you sure you want to clear all sent messages from " <<
                        current_device << "? (y/n) ";

                    if(!validate_yes()) {
                        cout << "Cancelled clearing all messages." << endl;
                        break;
                    }

                    if(contacts->clear_messages(current_contact, current_device))
                        cout << "Cleared sent messages." << endl;
                    else
                        cout << "No messages to clear." << endl;

                    break;
                case 5: // change pager number/change cell network/add program
                    if(submenu == device_type::pager) { // change pager number
                        cout << "Pager number: ";
                        cin.getline(input, INPUT_MAX);
                        number = input;

                        if(number.is_empty()) {
                            cout << "Cancelled changing pager number." << endl;
                            break;
                        }

                        contacts->change_number(current_contact, current_device,
                                number);
                        cout << "Successfully changed pager number to " << 
                            number << endl;
                    }
                    else if(submenu == device_type::cell_phone) { // change phone network
                        cout << "Cell phone network: ";
                        cin.getline(input, INPUT_MAX);
                        name = input;

                        if(name.is_empty()) {
                            cout << "Cancelled changing cell phone network." << endl;
                            break;
                        }

                        contacts->change_network(current_contact, current_device,
                                name);
                        cout << "Successfully changed cell phone network to " <<
                            name << "." << endl;
                    }
                    else { // add program
                        if(!add_program(new_program)) {
                            cout << "Cancelled adding a new program." << endl;
                            break;
                        }

                        contacts->add_program(current_contact, current_device,
                                *new_program);
                        cout << "Successfully added a new program." << endl;
                    }
                    break;
                case 6: // change supports text/change phone number/remove program
                    if(submenu == device_type::pager) { // change supports text
                        cout << "Does this pager support text messages? (y/n) ";

                        if(!validate_yes()) {
                            cout << "Cancelled changing text message support." << endl;
                            break;
                        }

                        contacts->change_supports_text(current_contact, 
                                current_device, true);
                        cout << "Successfully changed text message support." << endl;
                    }
                    else if(submenu == device_type::cell_phone) { // change phone number
                        cout << "Phone number: ";
                        cin.getline(input, INPUT_MAX);
                        number = input;

                        if(number.is_empty()) {
                            cout << "Cancelled changing phone number." << endl;
                            break;
                        }

                        contacts->change_number(current_contact, current_device,
                                number);
                        cout << "Successfully changed phone number." << endl;
                    }
                    else { // remove program
                        cout << "Program name: ";
                        cin.getline(input, INPUT_MAX);
                        name = input;

                        if(name.is_empty()) {
                            cout << "Cancelled removing a program." << endl;
                            break;
                        }

                        if(!contacts->remove_program(current_contact, current_device,
                                    name))
                            cout << "Could not remove " << name << "." << endl;
                        else
                            cout << "Successfully removed " << name << "." << endl;
                    }
                    break;
                case 7: // change supports two-way/change phone type/clear programs
                    if(submenu == device_type::pager) { // change two-way
                        cout << "Does the pager support two-way messaging? (y/n) " << endl;

                        if(!validate_yes()) {
                            cout << "Cancelled changing two-way messaging support." << endl;
                            break;
                        }

                        contacts->change_two_way(current_contact, current_device,
                                true);
                        cout << "Successfully changed two-way messaging support." << endl;
                    }
                    else if(submenu == device_type::cell_phone) { // change phone type
                        cout << "What type of phone is it?" << endl;
                        cout << "1) iPhone" << endl;
                        cout << "2) Android" << endl;
                        cout << "3) Windows Phone" << endl;
                        cout << "4) Other OS" << endl;
                        cout << "5) Feature phone (not smartphone)" << endl;

                        option = validate_input(1, 5);
                        phone_type = static_cast<os_type>(option - 1);

                        contacts->change_phone_type(current_contact, current_device,
                                phone_type);
                        cout << "Successfully changed phone type." << endl;
                    }
                    else { // clear programs
                        cout << "Are you sure you want to clear all programs from " << 
                            current_device << "? (y/n) ";

                        if(!validate_yes()) {
                            cout << "Cancelled clearing all programs." << endl;
                            break;
                        }

                        result = contacts->clear_programs(current_contact,
                                current_device);

                        if(result == 0)
                            cout << "No programs to clear." << endl;
                        else
                            cout << "Successfully cleared " << result << 
                                " programs." << endl;
                    }
                    break;
                case 8: // back
                    current_device = "";
                    menu = 1;
                    break;
                default:
                    option = display_menu(menu, submenu);
                    break;
            }
        }

        cout << endl;
    } while(!quit);

    delete contacts;
    return 0;
}
