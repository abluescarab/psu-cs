/* Alana Gilston - 1/18/2021 - CS202 - Program 1
 * product.h
 *
 * This is the implementation file for the product class. The product class
 * manages a product passed through the supply chain.
 */
#include "product.h"



path_node::path_node(void) {
    company = nullptr;
    distribution = distribution_type::manufacturer;
    next = nullptr;
}



path_node::path_node(const char * new_company, 
        const distribution_type new_distribution) {
    
}



path_node::path_node(const path_node & other_node) {

}



path_node::~path_node(void) {

}



// Get the next node in the list.
int path_node::get_next(path_node & next_node) const {
    return 1;
}



// Display the contents of the list.
int path_node::display(void) const {
    return 1;
}



product::product(void) {

}



product::product(const char * new_name, const priority_type new_priority,
        const int new_amount) {

}



product::product(const product & other_product) {

}



product::~product(void) {

}



// Change the amount.
int product::change_amount(const bool increment, const int new_amount) {
    return 1;
}



// Update the path.
int product::update_path(const char * company, distribution_type distribution) {
    return 1;
}



// Get the next product in the list.
int product::get_next(product * & next_product) const {
    return 1;
}



// Display the product.
int product::display(void) const {
    return 1;
}
