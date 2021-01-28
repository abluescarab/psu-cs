/* Alana Gilston - 1/18/2021 - CS202 - Program 1
 * supply_chain.h
 *
 * This is the implementation file for the supply chain class. The supply chain
 * manages a distribution and supply chain of storage facilities.
 */
#include "supply_chain.h"



supply_chain::supply_chain(void) {
    first_warehouse = nullptr;
}



supply_chain::supply_chain(const supply_chain & other_chain) {

}



supply_chain::~supply_chain(void) {

}



// Add a warehouse to the chain by company.
int supply_chain::add(const char * warehouse_company, const distribution_type distribution) {
    return 1;
}



// Add a warehouse to the chain.
int supply_chain::add(const warehouse & add_warehouse) {
    return 1;
}



// Add a new warehouse recursively.
int supply_chain::add(warehouse * & current, const warehouse & to_add) {
    return 1;
}



// Remove a warehouse by company.
int supply_chain::remove(const char * warehouse_company) {
    return 1;
}



// Remove a warehouse.
int supply_chain::remove(const warehouse & remove_warehouse) {
    return 1;
}



// Remove a warehouse recursively.
int supply_chain::remove(warehouse * & current, const warehouse & to_remove) {
    return 1;
}



// Clear all warehouses from the supply chain.
int supply_chain::clear(void) {
    return 1;
}



// Search for a warehouse by company.
int supply_chain::search(const char * warehouse_company, warehouse * & result) const {
    return 1;
}



// Create a new product order by product name.
int supply_chain::new_order(const char * product_name, const int amount) const {
    return 1;
}



// Create a new product order.
int supply_chain::new_order(const product & order_product, const int amount) const {
    return 1;
}


// Display the whole supply chain.
int supply_chain::display(void) const {
    return 1;
}



// Display the whole supply chain recursively.
int supply_chain::display(warehouse * & current) const {
    return 1;
}
