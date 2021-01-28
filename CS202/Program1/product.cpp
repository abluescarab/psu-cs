/* Alana Gilston - 1/18/2021 - CS202 - Program 1
 * product.h
 *
 * This is the implementation file for the product class. The product class
 * manages a product passed through the supply chain.
 */
#include <iostream>
#include <cstring>
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
    if(next_node)
        next_node = new path_node(*next_node);
    else
        next_node = nullptr;

    return next_node != nullptr;
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
    product(nullptr, 0) {}



product::product(const char * new_name) :
    product(new_name, 1) {}



product::product(const char * new_name,
        const int new_amount) : 
    amount(new_amount), 
    next(nullptr), 
    path(nullptr) {
    // cannot use initialization list if new_name is const
    copy_char_array(name, new_name);
}



product::product(const product & other_product) : 
    name(other_product.name),
    amount(other_product.amount),
    next(other_product.next),
    path(other_product.path) {
    }



product::~product(void) {
    delete [] name;
    name = nullptr;
    amount = 0;

    // todo: delete product
    // todo: delete path
}



// Change the amount of the product.
int product::change_amount(const int new_amount) {
    amount = new_amount;

    if(amount < 0)
        amount = 0;

    return amount;
}



// Increment the amount.
int product::increment_amount(const int new_amount, const bool subtract) {
    if(subtract)
        amount -= new_amount;
    else
        amount += new_amount;

    if(amount < 0)
        amount = 0;

    return amount;
}



// Increment the amount by another product.
int product::increment_amount(const product & other_product, const bool subtract) {
    if(char_array_empty(other_product.name))
        return -1;

    return increment_amount(other_product.amount, subtract);
}



// Update the path.
int product::update_path(char * company, distribution_type distribution) {
    return update_path(path, company, distribution);
}



// Update the path recursively.
int product::update_path(path_node * & node, char * company, distribution_type distribution) {
    if(!node) {
        node = new path_node(company, distribution);
        return 1;
    }

    node->get_next(node);
    return update_path(node, company, distribution);
}



// Get the next product in the list.
int product::get_next(product * & next_product) const {
    if(next)
        next_product = new product(*next);
    else
        next_product = nullptr;

    return next_product != nullptr;
}



// Check if the name matches the provided name.
int product::name_matches(const char * other_name) const {
    return strcmp(other_name, name) == 0;
}



// Check if the name matches the provided product.
int product::name_matches(const product & other_product) const {
    return name_matches(other_product.name);
}



// Display the product.
int product::display(void) const {
    if(!name)
        return 0;

    path_node * current = path;

    cout << name << endl;
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
