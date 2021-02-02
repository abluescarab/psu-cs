/* Alana Gilston - 1/18/2021 - CS202 - Program 1
 * supply_chain.cpp
 *
 * This is the implementation file for the supply chain class. The supply chain
 * manages a distribution and supply chain of storage facilities.
 */
#include <iostream>
#include "supply_chain.h"

using namespace std;


supply_chain::supply_chain(void) {
    root_warehouse = nullptr;
}



supply_chain::supply_chain(const supply_chain & other_chain) {
    copy(root_warehouse, *other_chain.root_warehouse);
}



supply_chain::~supply_chain(void) {
    clear();
}



// Add a warehouse to the chain by company.
// INPUT:
//  parent_company: company to add as a child to
//  warehouse_company: company to add
//  distribution: distribution type of the new warehouse
// OUTPUT:
//  Returns the result of the recursive function.
int supply_chain::add(const char * parent_company, 
        const char * warehouse_company, 
        const distribution_type distribution) {
    return add(root_warehouse, parent_company, warehouse_company, distribution);
}



// Add a new warehouse recursively.
// INPUT:
//  current: current warehouse
//  parent_company: company to add to as child
//  warehouse_company: company to add
//  distribution: distribution type of new warehouse
// OUTPUT:
//  Returns 0 if addition fails.
//  Returns 1 if addition succeeds.
int supply_chain::add(warehouse * & current, 
        const char * parent_company,
        const char * warehouse_company,
        const distribution_type distribution) {
    if(!current) {
        current = new warehouse(warehouse_company, distribution);
        return 1;
    }

    if(current->company_matches(parent_company)) {
        if(!current->get_left())
            return add(current->get_left(), parent_company, warehouse_company, distribution);
        
        if(!current->get_right())
            return add(current->get_right(), parent_company, warehouse_company, distribution);
        
        return 0;
    }

    return add(current->get_left(), parent_company, warehouse_company, distribution) ||
        add(current->get_right(), parent_company, warehouse_company, distribution);
}



// Remove a warehouse by company.
// INPUT:
//  warehouse_company: company to remove
// OUTPUT:
//  Returns the result of the recursive function.
int supply_chain::remove(const char * warehouse_company) {
    return remove(root_warehouse, warehouse_company);
}



// Remove a warehouse recursively.
// INPUT:
//  current: current warehouse
//  warehouse_company: company to remove
// OUTPUT:
//  Returns the number of nodes removed.
int supply_chain::remove(warehouse * & current, const char * warehouse_company) {
    if(!current)
        return 0;

    int count = 0;
    warehouse * left = nullptr;
    warehouse * right = nullptr;

    if(!current->company_matches(warehouse_company))
        return remove(current->get_left(), warehouse_company) ||
            remove(current->get_right(), warehouse_company);

    left = current->get_left();
    right = current->get_right();

    if(left)
        count += remove(left, warehouse_company);

    if(right)
        count += remove(right, warehouse_company);

    delete current;
    return count + 1;
}



// Clear all warehouses from the supply chain.
// OUTPUT:
//  Returns the result of the recursive function.
int supply_chain::clear(void) {
    return clear(root_warehouse);
}



// Clear all warehouses from the supply chain recursively.
// INPUT:
//  current: current warehouse
// OUTPUT:
//  Returns the number of warehouses removed.
int supply_chain::clear(warehouse * & current) {
    if(!current)
        return 0;

    int count = clear(current->get_left()) + clear(current->get_right());

    delete current;
    current = nullptr;
    return count + 1;
}



// Search for a warehouse by company.
// INPUT:
//  warehouse_company: company to find
//  result: result of the search
// OUTPUT:
//  Returns the result of the recursive function.
int supply_chain::search(const char * warehouse_company, warehouse * & result) {
    return search(root_warehouse, warehouse_company, result);
}



// Search for a warehouse recursively.
// INPUT:
//  current: current warehouse
//  warehouse_company: company to search for
//  result: result of the search
// OUTPUT:
//  Returns 0 if the company was not found.
//  Returns 1 if found.
int supply_chain::search(warehouse * & current, const char * warehouse_company, warehouse * & result) {
    if(!current)
        return 0;

    if(current->company_matches(warehouse_company)) {
        result = new warehouse(*current);
        return 1;
    }

    return search(current->get_left(), warehouse_company, result) || 
        search(current->get_right(), warehouse_company, result);
}



// Order a product.
// INPUT:
//  order_warehouse: warehouse that ordered the product
//  product_name: product to order
//  priority: priority of the product
//  amount: amount of product
// OUTPUT:
//  Returns the result of the recursive function.
int supply_chain::order_product(warehouse & order_warehouse,
        const char * product_name,
        const priority_type priority,
        const int amount) {
    product * new_product = new product(product_name, amount);
    int result = order_product(root_warehouse, order_warehouse, *new_product, priority);

    delete new_product;
    return result;
}



// Order a product recursively.
// INPUT:
//  current: current warehouse
//  order_warehouse: warehouse that ordered the product
//  to_order: product being ordered
//  priority: priority of the product
// OUTPUT:
//  Returns 0 if the item could not be ordered.
//  Returns 1 if the item was ordered.
int supply_chain::order_product(warehouse * & current,
        warehouse & order_warehouse,
        product & to_order,
        const priority_type priority) {
    if(!current)
        return 0;

    warehouse * parent = nullptr;

    if(!get_parent(current, order_warehouse, parent)) {
        // top of the chain -- just create the product
        to_order.update_path(order_warehouse.get_company());
        order_warehouse.add_product(to_order, priority);
        return 1;
    }

    if(parent->has_product(to_order)) 
        return ship(to_order, priority, *parent, *current);

    return order_product(parent, order_warehouse, to_order, priority);
}



// Ship a product from one warehouse to another.
// INPUT:
//  to_ship: product to ship
//  priority: shipping priority
//  company_from: company to ship from
//  company_to: company to ship to
// OUTPUT:
//  Returns 0 if the item could not be shipped.
//  Returns 1 if the item was shipped.
int supply_chain::ship(product & to_ship, 
        const priority_type priority,
        const char * company_from, 
        const char * company_to) {
    warehouse * from = nullptr;
    warehouse * to = nullptr;

    if(!search(company_from, from) || !search(company_to, to))
        return 0;

    return ship(to_ship, priority, *from, *to);
}



// Ship a product from one warehouse to another.
// INPUT:
//  to_ship: product to ship
//  priority: shipping priority
//  from: start warehouse
//  to: end warehouse
// OUTPUT:
//  Returns 0 if the item could not be shipped.
//  Returns 1 if the item was shipped.
int supply_chain::ship(product & to_ship,
        const priority_type priority,
        warehouse & from,
        warehouse & to) {
    if(from.is_empty() || to.is_empty())
        return 0;

    if(!from.remove_product(to_ship, priority))
        return 0;

    return to.add_product(to_ship, priority);
}



// Display the whole supply chain.
// OUTPUT:
//  Returns the result of the recursive function.
int supply_chain::display(void) {
    return display(root_warehouse);
}



// Display the whole supply chain recursively.
// INPUT:
//  current: current warehouse
// OUTPUT:
//  Returns the number of warehouses displayed.
int supply_chain::display(warehouse * & current) {
    if(!current)
        return 0;

    int count = current->display(false);

    cout << endl;

    return display(current->get_left()) + display(current->get_right()) + count;
}



// Copy a warehouse tree recursively.
// INPUT:
//  current: current warehouse
//  other_current: warehouse to copy from
// OUTPUT:
//  Returns the number of warehouses copied.
int supply_chain::copy(warehouse * & current, warehouse & other_current) {
    if(other_current.is_empty())
        return 0;

    current = new warehouse(other_current);

    return copy(current->get_left(), *other_current.get_left()) +
        copy(current->get_right(), *other_current.get_right()) + 1;
}



// Get a warehouse's parent.
// INPUT:
//  current: current warehouse
//  child: child warehouse
//  result: result of the search
// OUTPUT:
//  Returns 0 if the parent could not be found.
//  Returns 1 if the parent was found.
int supply_chain::get_parent(warehouse * & current, 
        const warehouse & child,
        warehouse * & result) {
    if(!current)
        return 0;

    if(current->company_matches(child))
        return 0;

    if(current->get_left()->company_matches(child) ||
            current->get_right()->company_matches(child)) {
        result = current;
        return 1;
    }

    return get_parent(current->get_left(), child, result) ||
        get_parent(current->get_right(), child, result);
}
