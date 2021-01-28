/* Alana Gilston - 1/10/2021 - CS202 - Program 1
 * inventory.h
 *
 * This is the implementation file for the inventory and warehouse classes. The
 * inventory class manages a product inventory in a supply chain while the
 * warehouse class manages a warehouse where products are stored. */
#include <iostream>
#include "inventory.h"
#include "utils.h"

using namespace std;



inventory::inventory(void) : products(new product*[3]) {}



inventory::inventory(const inventory & other_inventory) {
    // todo: copy inventory
}



inventory::~inventory(void) {
    clear();
    delete [] products;
}



// Add a new product to the inventory by name.
int inventory::add_product(const char * product_name, 
        const priority_type priority, 
        const int amount) {
    int result = 0;
    product * new_product = new product(product_name, amount);

    // store the result so we can delete the new object after
    result = add_product(products[priority], *new_product);
    delete new_product;
    return result;
}



// Add a new product to the inventory.
int inventory::add_product(const product & to_add, 
        const priority_type priority) {
    return add_product(products[priority], to_add);
}



// Add a product recursively.
int inventory::add_product(product * & current, const product & to_add) {
    if(!current)
        current = new product(to_add);
    else if(current->name_matches(to_add))
        current->increment_amount(to_add);
    else {
        current->get_next(current);
        return add_product(current, to_add);
    }

    return 1;
}



// Remove a product from the inventory by name.
int inventory::remove_product(const char * product_name, 
        const priority_type priority,
        const int amount) {
    int result = 0;
    product * find_product = new product(product_name, amount);

    // store the result so we can delete the new product
    result = remove_product(products[priority], *find_product);
    delete find_product;
    return result;
}



// Remove a product from the inventory.
int inventory::remove_product(const product & to_remove, 
        const priority_type priority) {
    return remove_product(products[priority], to_remove);
}



// Remove a product recursively.
int inventory::remove_product(product * & current, 
        const product & to_remove) {
    int result = -1;

    if(!current)
        return 0;

    if(current->name_matches(to_remove)) 
        result = current->increment_amount(to_remove, true);

    // todo: delete the item if the count is 0
    if(result == 0) {

    }

    return 1;
}



// Remove all of a product.
int inventory::remove_all(const product & to_remove,
        const priority_type priority) {
    return remove_all(products[priority], to_remove);
}



// Remove all of a product by name.
int inventory::remove_all(const char * product_name,
        const priority_type priority) {
    int result = 0;
    product * new_product = new product(product_name);

    // store the result so we can delete the new product after
    result = remove_all(products[priority], *new_product);
    delete new_product;
    return result;
}



// Remove all of a product recursively.
int inventory::remove_all(product * & current,
        const product & to_remove) {
    if(!current)
        return 0;

    if(current->name_matches(to_remove)) {
        // todo: remove product 
    }

    return 1;
}



// Clear the inventory.
int inventory::clear(void) {
    return 1;
}



// Display the contents of the inventory.
int inventory::display(void) const {
    int count = 0;
    int total = 0;
    product * current = nullptr;

    for(int i = 0; i < 3; ++i) {
        cout << "Shipping priority: ";

        if(i == priority_type::standard)
            cout << "Standard";
        else if(i == priority_type::two_day)
            cout << "Two-day";
        else if(i == priority_type::one_day)
            cout << "One-day";

        cout << endl << endl;
        cout << "Products:" << endl;

        current = products[i];
        count = display(current);
        total += count;

        if(count == 0)
            cout << "No products available.";
        else
            cout << count << " products available.";
    }

    return total;
}



// Display products recursively.
int inventory::display(product * & current) const {
    if(!current)
        return 0;

    current->display();
    current->get_next(current);
    return display(current) + 1;
}



warehouse::warehouse(void) :
    warehouse(nullptr, distribution_type::manufacturer) {}



warehouse::warehouse(const char * new_company) :
    warehouse(new_company, distribution_type::manufacturer) {}



warehouse::warehouse(const char * new_company, 
        const distribution_type new_distribution) :
    distribution(new_distribution),
    left(nullptr),
    right(nullptr) {
    copy_char_array(company, new_company);
}



warehouse::warehouse(const warehouse & other_warehouse) :
    company(other_warehouse.company),
    distribution(other_warehouse.distribution) {
    // todo: copy tree
}



warehouse::~warehouse(void) {
    delete [] company;
    distribution = distribution_type::manufacturer;
    // disconnect, but do not delete children
    left = nullptr;
    right = nullptr;
}



// Change the company.
int warehouse::change_company(const char * new_company) {
    return copy_char_array(company, new_company);
}



// Change the distribution type.
int warehouse::change_distribution(const distribution_type new_distribution) {
    distribution = new_distribution;
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
    // only a manufacturer should be able to fabricate
    if(distribution != distribution_type::manufacturer)
        return 0;


    return 1;
}



// Create a product.
int warehouse::fabricate(const product & fab_product, const int amount) {
    // only a manufacturer should be able to fabricate
    if(distribution != distribution_type::manufacturer)
        return 0;



    return 1;
}



// Get the left warehouse from the tree.
int warehouse::get_left(warehouse * & left_warehouse) const {
    if(!left) {
        left_warehouse = nullptr;
        return 0;
    }

    left_warehouse = new warehouse(*left);
    return 1;
}



// Get the right warehouse from the tree.
int warehouse::get_right(warehouse * & right_warehouse) const {
    if(!right) {
        right_warehouse = nullptr;
        return 0;
    }

    right_warehouse = new warehouse(*right);
    return 1;
}



// Display the warhouse.
int warehouse::display(void) const {
    return 1;
}
