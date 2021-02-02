/* Alana Gilston - 1/18/2021 - CS202 - Program 1
 * product.cpp
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
    next(nullptr) {
    }



path_node::path_node(const char * new_company) :
    company(nullptr),
    next(nullptr) {
        copy_char_array(company, new_company);
    }



path_node::path_node(const path_node & other_node) :
    company(nullptr),
    next(other_node.next) {
        copy_char_array(company, other_node.company);
    }



path_node::~path_node(void) {
    delete [] company;
    company = nullptr;
    // disconnect, but do not delete next node
    next = nullptr;
}



// Get the next node in the list.
// OUTPUT:
//  Returns the next node.
path_node * & path_node::get_next(void) {
    return next;
}



// Display the contents of the path node.
// OUTPUT:
//  Returns 1.
int path_node::display(void) const {
    cout << company;
    return 1;
}



product::product(void) :
    name(nullptr),
    amount(0),
    next(nullptr),
    path(nullptr) {
    }



product::product(const char * new_name) : product(new_name, 1) {}



product::product(const char * new_name,
        const int new_amount) : 
    name(nullptr),
    amount(new_amount), 
    next(nullptr), 
    path(nullptr) {
        copy_char_array(name, new_name);
    }



product::product(const product & other_product) : 
    name(nullptr),
    amount(other_product.amount),
    next(nullptr),
    path(nullptr) {
        copy_char_array(name, other_product.name);
    }



product::~product(void) {
    delete [] name;
    name = nullptr;
    amount = 0;
    // disconnect, but do not delete next node
    next = nullptr; 
    clear_path(path);
}



// Change the amount of the product.
// INPUT:
//  new_amount: the amount to change to
// OUTPUT:
//  Returns the new amount.
int product::change_amount(const int new_amount) {
    amount = new_amount;

    if(amount < 0)
        amount = 0;

    return amount;
}



// Increment the amount.
// INPUT:
//  new_amount: the amount to increment by
//  subtract: whether to subtract instead of add
// OUTPUT:
//  Returns the new amount.
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
// INPUT:
//  other_product: the product to copy from
//  subtract: whether to subtract instead of add
// OUTPUT:
//  Returns the new amount.
int product::increment_amount(const product & other_product, const bool subtract) {
    if(char_array_empty(other_product.name))
        return -1;

    return increment_amount(other_product.amount, subtract);
}



// Get the current amount of product.
// OUTPUT:
//  Returns the amount of product.
int product::get_amount(void) const {
    return amount;
}



// Update the path.
// INPUT:
//  company: the company to add to the path
// OUTPUT:
//  Returns the result of the recursive function.
int product::update_path(const char * company) {
    return update_path(path, company);
}



// Update the path recursively.
// INPUT:
//  node: current node
//  company: company to add
// OUTPUT:
//  Returns 1 when it inserts the new node.
int product::update_path(path_node * & node, const char * company) {
    if(!node) {
        node = new path_node(company);
        return 1;
    }

    return update_path(node->get_next(), company);
}



// Update the path from another product.
// INPUT:
//  other_product: product to copy from
// OUTPUT:
//  Returns result of recursive function.
int product::append_path(product & other_product) {
    return append_path(path, other_product.path);
}



// Update the path from another product recursively.
// INPUT:
//  node: current node
//  other_node: node to copy from
// OUTPUT:
//  Returns the number of nodes copied.
int product::append_path(path_node * & node, path_node * & other_node) {
    if(!other_node)
        return 0;

    if(node)
        return append_path(node->get_next(), other_node);

    node = new path_node(*other_node);
    return append_path(node->get_next(), other_node->get_next()) + 1;
}



// Get the next product in the list.
// OUTPUT:
//  Returns the next product.
product * & product::get_next(void) {
    return next;
}



// Check if the name matches the provided name.
// INPUT:
//  other_name: name of the other product
// OUTPUT:
//  Returns 0 if the names do not match.
//  Returns 1 if the names match.
int product::name_matches(const char * other_name) const {
    return strcmp(other_name, name) == 0;
}



// Check if the name matches the provided product.
// INPUT:
//  other_product: product to compare to
// OUTPUT:
//  Returns 0 if the names do not match.
//  Returns 1 if the names match.
int product::name_matches(const product & other_product) const {
    return name_matches(other_product.name);
}



// Display the product.
// OUTPUT:
//  Returns 0 if the product is missing data.
//  Returns 1 if the product is displayed.
int product::display(void) const {
    if(!name)
        return 0;

    cout << name << " (stored: " << amount << ")" << endl;

    return 1;
}



// Display the path.
// OUTPUT:
//  Returns the result of the recursive function.
int product::display_path(void) {
    return display_path(path);
}



// Display the path recursively.
// INPUT:
//  current: current path node
// OUTPUT:
//  Returns the number of nodes displayed.
int product::display_path(path_node * & current) {
    if(!current)
        return 0;

    return current->display() + display_path(current->get_next());
}



// Check if the product has no data.
// OUTPUT:
//  Returns 0 if the product has data.
//  Returns 1 if the product is empty.
int product::is_empty(void) const {
    return char_array_empty(name);
}



// Clear the path.
// INPUT:
//  current: the current path node
// OUTPUT:
//  Returns the number of nodes cleared.
int product::clear_path(path_node * & current) {
    if(!current)
        return 0;

    path_node * next = current->get_next();
    delete current;
    return clear_path(next) + 1;
}
