/* Alana Gilston - 1/10/2021 - CS202 - Program 1
 * supply_chain.h
 *
 * This is the header file for the supply chain class. The supply chain manages
 * a distribution and supply chain of storage facilities.
 */
#ifndef SUPPLY_CHAIN_H
#define SUPPLY_CHAIN_H
#include "inventory.h"

class supply_chain {
    public:
        supply_chain(void);
        supply_chain(const supply_chain & other_chain);
        ~supply_chain(void);
        // Add a warehouse to the chain by company.
        int add(const char * parent_company, 
                const char * warehouse_company, 
                const distribution_type distribution);
        // Remove a warehouse by company.
        int remove(const char * warehouse_company);
        // Clear all warehouses from the supply chain.
        int clear(void);
        // Search for a warehouse by company.
        int search(const char * warehouse_company, warehouse * & result);
        // Order a product.
        int order_product(warehouse & order_warehouse,
                const char * product_name,
                const priority_type priority,
                const int amount);
        // Ship a product from one warehouse to another.
        int ship(product & to_ship,
                const priority_type priority,
                const char * company_from, 
                const char * company_to);
        // Ship a product from one warehouse to another.
        int ship(product & to_ship,
                const priority_type priority,
                warehouse & from,
                warehouse & to);
        // Display the whole supply chain.
        int display(void);

    private:
        // Add a new warehouse recursively.
        int add(warehouse * & current,
                const char * parent_company, 
                const char * warehouse_company, 
                const distribution_type distribution);
        // Remove a warehouse recursively.
        int remove(warehouse * & current, const char * warehouse_company);
        // Clear all warehouses from the supply chain recursively.
        int clear(warehouse * & current);
        // Search for a warehouse recursively.
        int search(warehouse * & current, const char * warehouse_company, warehouse * & result);
        // Order a product recursively.
        int order_product(warehouse * & current,
                warehouse & order_warehouse,
                product & to_order,
                const priority_type priority);
        // Display the whole supply chain recursively.
        int display(warehouse * & current);
        // Copy a warehouse tree recursively.
        int copy(warehouse * & current, warehouse & other_current);
        // Get a warehouse's parent.
        int get_parent(warehouse * & current, 
                const warehouse & child,
                warehouse * & result);

        warehouse * root_warehouse; // first warehouse in the chain
};

#endif
