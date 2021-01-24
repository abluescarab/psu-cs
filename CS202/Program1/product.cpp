/* Alana Gilston - 1/18/2021 - CS202 - Program 1
 * product.h
 *
 * This is the implementation file for the product class. The product class
 * manages a product passed through the supply chain.
 */
#include <iostream>
#include "product.h"
#include "utils.h"

using namespace std;



path_node::path_node(void) : 
    company(nullptr),
    distribution(distribution_type::manufacturer),
    next(nullptr) {
    }



path_node::path_node(char * new_company,
        const distribution_type new_distribution) : 
    company(new_company),
    distribution(new_distribution),
    next(nullptr) {
    }



path_node::path_node(const path_node & other_node) :
    company(other_node.company),
    distribution(other_node.distribution),
    next(other_node.next) {
    }



path_node::~path_node(void) {
    delete [] company;
    company = nullptr;
    // disconnect, but do not delete next node
    next = nullptr;
}



// Set the next node in the list.
int path_node::set_next(path_node * & next_node) {
    if(!next_node)
        return 0;

    next = next_node;
    return 1;
}



// Get the next node in the list.
int path_node::get_next(path_node * & next_node) const {

    return 1;
}



// Display the contents of the path node.
int path_node::display(void) const {
    cout << company << " (";

    switch(distribution) {
        case distribution_type::manufacturer:
            cout << "Manufacturer";
            break;
        case distribution_type::national:
            cout << "National";
            break;
        case distribution_type::regional:
            cout << "Regional";
            break;
        case distribution_type::local:
            cout << "Local";
            break;
        default:
            cout << "N/A";
            break;
    }

    cout << ")" << endl;
    return 1;
}



product::product(void) : 
    name(nullptr),
    priority(priority_type::standard),
    amount(0),
    next(nullptr), 
    path(nullptr) {
    }



product::product(char * new_name,
        const priority_type new_priority,
        const int new_amount) : 
    name(new_name), 
    priority(new_priority),
    amount(new_amount), 
    next(nullptr), 
    path(nullptr) {
    }



product::product(const product & other_product) : 
    name(other_product.name),
    priority(other_product.priority),
    amount(other_product.amount),
    next(other_product.next),
    path(other_product.path) {
    }



product::~product(void) {
    delete [] name;
    name = nullptr;
    amount = 0;

    // todo: delete all products
    // todo: delete path
}



// Change the amount.
int product::change_amount(const bool increment, const int new_amount) {
    if(increment) {
        amount += new_amount;

        if(amount < 0)
            amount = 0;
    }
    else
        amount = new_amount;

    return 1;
}



// Update the path.
int product::update_path(char * company, distribution_type distribution) {
    path_node * new_node = new path_node(company, distribution);
    path_node * last_node = path;

    if(!path) {
        path = new_node;
    }
    else if(!path->get_next(last_node)) {
        last_node->set_next(new_node);
    }

    return 1;
}



// Get the next product in the list.
int product::get_next(product * & next_product) const {
    return 1;
}



// Display the product.
int product::display(void) const {
    if(!name)
        return 0;

    path_node * current = path;

    cout << name << endl;
    cout << "Shipping priority: ";

    switch(priority) {
        case priority_type::standard:
            cout << "Standard";
            break;
        case priority_type::two_day:
            cout << "Two-day";
            break;
        case priority_type::one_day:
            cout << "One-day";
            break;
        default:
            cout << "N/A";
            break;
    }

    cout << endl;
    cout << "Amount stored: " << amount << endl;
    cout << "Path traveled: " << endl;
    
    if(!current)
        cout << "N/A" << endl;

    while(current) {
        current->display();
        current->get_next(current);
    }

    return 1;
}
