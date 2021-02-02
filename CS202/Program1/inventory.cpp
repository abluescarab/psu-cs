/* Alana Gilston - 1/10/2021 - CS202 - Program 1 * inventory.cpp
 *
 * This is the implementation file for the inventory and warehouse classes. The
 * inventory class manages a product inventory in a supply chain while the
 * warehouse class manages a warehouse where products are stored. */
#include <cstring>
#include <iostream>
#include "inventory.h"
#include "utils.h"
#define PRIORITY_COUNT 3

using namespace std;



inventory::inventory(void) : products(new product*[PRIORITY_COUNT]) {
    for(int i = 0; i < PRIORITY_COUNT; ++i) {
        products[i] = nullptr;
    }
}



inventory::inventory(const inventory & other_inventory) :
    products(new product*[PRIORITY_COUNT]) {
        for(int i = 0; i < PRIORITY_COUNT; ++i) {
            if(other_inventory.products[i])
                copy(*other_inventory.products[i], products[i]);
            else
                products[i] = nullptr;
        }
    }



inventory::~inventory(void) {
    clear();
    delete [] products;
    products = nullptr;
}



// Add a new product to the inventory by name.
// INPUT:
//  product_name: name of the product
//  priority: shipping priority of the current product
//  amount: amount of the product to add
// OUTPUT:
//  Returns the result of the recursive function.
int inventory::add_product(const char * product_name, 
        const priority_type priority, 
        const int amount) {
    product * new_product = new product(product_name, amount);
    // store the result so we can delete the new object after
    int result = add_product(products[priority], *new_product);

    delete new_product;
    return result;
}



// Add a new product to the inventory.
// INPUT:
//  to_add: the product to add
//  priority: shipping priority to add the object to
// OUTPUT:
//  Returns the result of the recursive function
int inventory::add_product(product & to_add, const priority_type priority) {
    return add_product(products[priority], to_add);
}



// Add a product recursively.
// INPUT:
//  current: the current product node
//  to_add: the product to add
// OUTPUT:
//  Returns 1 when the product is successfully added.
int inventory::add_product(product * & current, product & to_add) {
    if(!current)
        current = new product(to_add);
    else if(current->name_matches(to_add)) {
        current->increment_amount(to_add);
        current->append_path(to_add);
    }
    else
        return add_product(current->get_next(), to_add);

    return 1;
}



// Remove a product from the inventory by name.
// INPUT:
//  product_name: name of the product to remove
//  priority: priority to remove the product from
//  amount: number of that product to remove
// OUTPUT:
//  Returns the result of the recursive function.
int inventory::remove_product(const char * product_name, 
        const priority_type priority,
        const int amount) {
    product * find_product = new product(product_name, amount);
    // store the result so we can delete the new product
    int result = remove_product(products[priority], *find_product);

    delete find_product;
    return result;
}



// Remove a product from the inventory.
// INPUT:
//  to_remove: the product to remove
//  priority: the priority list to remove from
// OUTPUT:
//  Returns the result of the recursive function.
int inventory::remove_product(const product & to_remove, 
        const priority_type priority) {
    return remove_product(products[priority], to_remove);
}



// Remove a product recursively.
// INPUT:
//  current: the current product node
//  to_remove: the product to remove
// OUTPUT:
//  Returns 0 if the product does not exist.
//  Returns 1 if the product is successfully removed.
int inventory::remove_product(product * & current, const product & to_remove) {
    int result = -1;
    product * temp = nullptr;

    if(!current)
        return 0;

    if(current->name_matches(to_remove)) 
        result = current->increment_amount(to_remove, true);

    if(result == 0) {
        temp = current->get_next();
        delete current;
        current = temp;
    }

    return 1;
}



// Clear the inventory.
// OUTPUT:
//  Returns the number of products removed.
int inventory::clear(void) {
    int count = 0;

    for(int i = 0; i < PRIORITY_COUNT; ++i) {
        count += clear(products[i]);
    }

    return count;
}



// Clear the inventory recursively.
// INPUT:
//  current: the current product node
// OUTPUT:
//  Returns the number of products removed.
int inventory::clear(product * & current) {
    if(!current)
        return 0;

    product * temp = current->get_next();
    delete current;
    current = nullptr;
    return clear(temp) + 1;
}



// Display the contents of the inventory.
// OUTPUT:
//  Returns the result of the recursive function.
int inventory::display(void) const {
    int count = 0;
    int total = 0;
    product * current = nullptr;

    cout << endl;

    for(int i = 0; i < PRIORITY_COUNT; ++i) {
        cout << "Products (";

        if(i == priority_type::standard)
            cout << "Standard";
        else if(i == priority_type::two_day)
            cout << "Two-day";
        else if(i == priority_type::one_day)
            cout << "One-day";

        cout << "):" << endl;

        current = products[i];
        count = display(current);
        total += count;
        cout << endl;

        if(count == 0)
            cout << "No products available.";
        else
            cout << count << " products available.";

        cout << endl;

        if(i != (PRIORITY_COUNT - 1))
            cout << endl;
    }

    return total;
}



// Display products recursively.
// INPUT:
//  current: the current product node
// OUTPUT:
//  Returns the number of products displayed.
int inventory::display(product * & current) const {
    if(!current)
        return 0;

    current->display();
    return display(current->get_next()) + 1;
}



// Copy another inventory category recursively.
// INPUT:
//  copy_from: product to copy from
//  copy_to: product to copy to
// OUTPUT:
//  Returns the number of products copied.
int inventory::copy(product & copy_from, product * & copy_to) {
    if(copy_from.is_empty())
        return 0;

    copy_to = new product(copy_from);
    return copy(*copy_from.get_next(), copy_to->get_next()) + 1;
}



warehouse::warehouse(void) :
    warehouse(nullptr, distribution_type::national) {
    }



warehouse::warehouse(const char * new_company) :
    warehouse(new_company, distribution_type::national) {
    }



warehouse::warehouse(const char * new_company, 
        const distribution_type new_distribution) :
    company(nullptr),
    distribution(new_distribution),
    left(nullptr),
    right(nullptr) {
        copy_char_array(company, new_company);
    }



warehouse::warehouse(const warehouse & other_warehouse) :
    inventory(other_warehouse),
    company(nullptr),
    distribution(other_warehouse.distribution),
    left(nullptr),
    right(nullptr) {
        copy_char_array(company, other_warehouse.company);
    }



warehouse::~warehouse(void) {
    delete [] company;
    company = nullptr;
    distribution = distribution_type::national;
    // disconnect, but do not delete children
    left = nullptr;
    right = nullptr;
}



// Check if company matches.
// INPUT:
//  to_compare: company name to compare
// OUTPUT:
//  Returns 0 if the companies do not match.
//  Returns 1 if the companies match.
int warehouse::company_matches(const char * to_compare) {
    if(!company)
        return 0;

    return strcmp(to_compare, company) == 0;
}



// Check if company matches.
// INPUT:
//  other_warehouse: warehouse to compare to
// OUTPUT:
//  Returns the result of strcmp.
int warehouse::company_matches(const warehouse & other_warehouse) {
    if(other_warehouse.is_empty())
        return 0;

    return strcmp(other_warehouse.company, company) == 0;
}



// Check if the warehouse has the specified amount of product.
// INPUT:
//  product_name: the name of the product to search for
//  amount: the amount of product to search for
// OUTPUT:
//  Returns the result of the recursive function.
int warehouse::has_product(const char * product_name, const int amount) {
    product * new_product = new product(product_name, amount);
    int result = has_product(*new_product);

    delete new_product;
    return result;
}



// Check if the warehouse has the specified amount of product.
// INPUT:
//  to_check: the product to check for
// OUTPUT:
//  Returns the result of the recursive function.
int warehouse::has_product(const product & to_check) {
    return has_product(products[0], to_check) ||
        has_product(products[1], to_check) ||
        has_product(products[2], to_check);
}



// Check if the warehouse has the specified amount of product recursively.
// INPUT:
//  current: the current product node
//  to_check: the product to check for
// OUTPUT:
//  Returns 0 if the product does not exist or if there is not enough of it.
//  Returns 1 if the product does exist and there is enough of it.
int warehouse::has_product(product * & current, const product & to_check) {
    if(!current)
        return 0;

    if(current->name_matches(to_check) && 
            current->get_amount() >= to_check.get_amount())
        return 1;

    return has_product(current->get_next(), to_check);
}



// Get the name of the warehouse.
// OUTPUT:
//  Returns the company.
const char * warehouse::get_company(void) const {
    return company;
}



// Get the left warehouse from the tree.
// OUTPUT:
//  Returns the left child vertex.
warehouse * & warehouse::get_left(void) {
    return left;
}



// Get the right warehouse from the tree.
// OUTPUT:
//  Returns the right child vertex.
warehouse * & warehouse::get_right(void) {
    return right;
}



// Display the warehouse.
// INPUT:
//  show_inventory: whether to show the inventory or just the warehouse info
// OUTPUT:
//  Returns 1 if displayed correctly.
//  Returns 0 if warehouse is invalid.
int warehouse::display(bool show_inventory) const {
    if(char_array_empty(company))
        return 0;

    cout << "Company: " << company << endl;
    cout << "Distribution type: ";

    if(distribution == distribution_type::local)
        cout << "Local";
    else if(distribution == distribution_type::regional)
        cout << "Regional";
    else
        cout << "National";

    cout << endl;

    if(show_inventory)
        inventory::display();

    return 1;
}



// Check if the warehouse has no data.
// OUTPUT:
//  Returns the result of char_array_empty.
int warehouse::is_empty(void) const {
    return char_array_empty(company);
}
