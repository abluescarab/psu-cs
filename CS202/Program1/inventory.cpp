/* Alana Gilston - 1/10/2021 - CS202 - Program 1
 * inventory.h
 *
 * This is the implementation file for the inventory and warehouse classes. The
 * inventory class manages a product inventory in a supply chain while the
 * warehouse class manages a warehouse where products are stored. */
#include "inventory.h"



inventory::inventory(void) {
    products = new product*[3];
}



inventory::inventory(const inventory & other_inventory) {

}



inventory::~inventory(void) {

}



// Add a new product to the inventory by name.
int inventory::add_product(const char * product_name, 
        const priority_type priority, const int amount) {
    return 1;
}



// Add a new product to the inventory.
int inventory::add_product(const product & add_product) {
    return 1;
}



// Remove a product from the inventory by name.
int inventory::remove_product(const char * product_name, const int amount) {
    return 1;
}



// Remove a product from the inventory.
int inventory::remove_product(const product & remove_product, const int amount) {
    return 1;
}



// Display the contents of the inventory.
int inventory::display(void) const {
    return 1;
}



// Get the next product in the inventory.
int inventory::get_next(product * & next_product) const {
    return 1;
}




warehouse::warehouse(void) {

}



warehouse::~warehouse(void) {

}



// Change the company.
int warehouse::change_company(const char * new_company) {
    return 1;
}



// Change the distribution type.
int warehouse::change_distribution(const distribution_type distribution) {
    return 1;
}



// Ship a product to another warehouse.
int warehouse::ship_to(const warehouse & other_warehouse,
        const char * product_name, const int amount) {
    return 1;
}



// Order a product from up the chain by name.
int warehouse::order_product(const priority_type priority,
        const char * product_name, const int amount) {
    return 1;
}



// Order a product from up the chain.
int warehouse::order_product(const priority_type priority,
        const product & order_product, const int amount) {
    return 1;
}



// Create a product by name.
int warehouse::fabricate(const char * product_name, const int amount) {
    return 1;
}



// Create a product.
int warehouse::fabricate(const product & fab_product, const int amount) {
    return 1;
}



// Get the left warehouse from the tree.
int warehouse::get_left(warehouse * & left_warehouse) const {
    return 1;
}



// Get the right warehouse from the tree.
int warehouse::get_right(warehouse * & right_warehouse) const {
    return 1;
}



// Display the warhouse.
int warehouse::display(void) const {
    return 1;
}
