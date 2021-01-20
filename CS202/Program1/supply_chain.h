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
        ~supply_chain(void);
        // Add a warehouse to the chain by company.
        int add(const char * warehouse_company, const distribution_type distribution);
        // Add a warehouse to the chain.
        int add(const warehouse & add_warehouse);
        // Remove a warehouse by company.
        int remove(const char * warehouse_company);
        // Remove a warehouse.
        int remove(const warehouse & remove_warehouse);
        // Clear all warehouses from the supply chain.
        int clear(void);
        // Search for a warehouse by company.
        int search(const char * warehouse_company, warehouse * & result) const;
        // Create a new product order by product name.
        int new_order(const char * product_name, const int amount) const;
        // Create a new product order.
        int new_order(const product & order_product, const int amount) const;

    private:
        warehouse * first_warehouse; // first warehouse in the chain
};

#endif
