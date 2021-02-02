/* Alana Gilston - 10/18/2021 - CS202 - Program 1
 * main.cpp
 *
 * This is the implementation file to test the supply chain class.
 */
#include <climits>
#include <iostream>
#include <cstring>
#include "supply_chain.h"
#include "utils.h"
#define INPUT_MAX 1024

using namespace std;



// Create an existing supply chain.
// INPUT:
//  chain: dynamically-allocated chain object
void create_chain(supply_chain * & chain) {
    chain->add(nullptr, "Shipping USA", distribution_type::national);
    chain->add("Shipping USA", "Shipping West", distribution_type::regional);
    chain->add("Shipping USA", "Shipping East", distribution_type::regional);
    chain->add("Shipping West", "Shipping Idaho", distribution_type::local);
    chain->add("Shipping West", "Shipping Colorado", distribution_type::local);
    chain->add("Shipping East", "Shipping Pennsylvania", distribution_type::local);
    chain->add("Shipping East", "Shipping Georgia", distribution_type::local);
}



// Display a menu.
// INPUT:
//  menu: the current menu to display
// OUTPUT:
//  Returns the user input option.
int display_menu(int menu) {
    int max_option = -1;

    switch(menu) {
        case 1: // manage warehouse
            cout << "1) Order product" << endl;
            cout << "2) Display warehouse info" << endl;
            cout << "3) Display inventory" << endl;
            cout << "4) Back" << endl;

            max_option = 5;
            break;
        case 0:
        default: // main menu
            cout << "1) Display all warehouses" << endl;
            cout << "2) Manage a warehouse" << endl;
            cout << "3) Quit" << endl;

            max_option = 4;
            break;
    }

    return validate_input(1, max_option);
}



int main(void) {
    int menu = 0;
    int option = -1;
    char input[MAX_INPUT] = "";
    int amount = -1;
    int priority = -1;
    bool quit = false;

    supply_chain * chain = new supply_chain();
    warehouse * current_warehouse = nullptr;

    create_chain(chain);
    cout << "Distribution Center" << endl;
    cout << "-------------------" << endl;

    do {
        option = display_menu(menu);
        
        if(menu == 0) { // main menu
            switch(option) {
                case 1: // display all warehouses
                    chain->display();
                    break;
                case 2: // manage a warehouse
                    cout << "Warehouse company: ";
                    cin.getline(input, MAX_INPUT);

                    if(char_array_empty(input) || !chain->search(input, current_warehouse)) {
                        cout << "Invalid warehouse company." << endl;
                        break;
                    }

                    menu = 1;
                    break;
                case 3: // quit
                    quit = true;
                    break;
                default:
                    option = display_menu(menu);
                    break;
            }
        }
        else if(menu == 1) { // warehouse menu
            switch(option) {
                case 1: // order product
                    cout << "Product name: ";
                    cin.getline(input, MAX_INPUT);

                    if(char_array_empty(input)) {
                        cout << "Invalid product name." << endl;
                        break;
                    }

                    while(priority < 1 || priority > 3) {
                        cout << "1) Standard" << endl;
                        cout << "2) Two-day" << endl;
                        cout << "3) One-day" << endl;
                        cout << "Product priority: ";
                        cin >> priority;

                        if(priority < 1 || priority > 3)
                            cout << "Invalid priority." << endl;
                    }

                    while(amount < 1) {
                        cout << "Product amount: ";
                        cin >> amount;

                        if(amount < 1) {
                            cout << "Invalid product amount.";
                        }
                    }

                    chain->order_product(*current_warehouse, input, 
                            (priority_type)(priority - 1), amount);
                    break;
                case 2: // display warehouse info
                    current_warehouse->display(false);
                    break;
                case 3: // display inventory
                    current_warehouse->display(true);
                    break;
                case 4: // back
                    menu = 0;
                    break;
                default:
                    option = display_menu(menu);
                    break;
            }
        }

        strcpy(input, "");
        priority = -1;
        amount = -1;

        cout << endl;
    } while(!quit);

    delete current_warehouse;
    delete chain;
    return 0;
}
