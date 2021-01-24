/* Alana Gilston - 1/10/2021 - CS202 - Program 1
 * inventory.h
 *
 * This is the header file for the inventory and warehouse classes. The
 * inventory class manages a product inventory in a supply chain while the
 * warehouse class manages a warehouse where products are stored. */
#ifndef INVENTORY_H
#define INVENTORY_H
#include "product.h"

class inventory {
    public:
        inventory(void);
        inventory(const inventory & other_inventory);
        ~inventory(void);
        // Add a new product to the inventory by name.
        int add_product(const char * product_name, const priority_type priority, const int amount);
        // Add a new product to the inventory.
        int add_product(const product & add_product);
        // Remove a product from the inventory by name.
        int remove_product(const char * product_name, const int amount);
        // Remove a product from the inventory.
        int remove_product(const product & remove_product, const int amount);
        // Display the contents of the inventory.
        int display(void) const;
        // Get the next product in the inventory.
        int get_next(product * & next_product) const;

    private:
        product ** products; // products in the inventory
};

class warehouse : public inventory {
    public:
        warehouse(void);
        ~warehouse(void);
        // Change the company.
        int change_company(const char * new_company);
        // Change the distribution type.
        int change_distribution(const distribution_type new_distribution);
        // Ship a product to another warehouse.
        int ship_to(const warehouse & other_warehouse,
                const char * product_name, const int amount);
        // Order a product from up the chain by name.
        int order_product(const priority_type priority,
                const char * product_name, const int amount);
        // Order a product from up the chain.
        int order_product(const priority_type priority,
                const product & order_product, const int amount);
        // Create a product by name.
        int fabricate(const char * product_name, const int amount);
        // Create a product.
        int fabricate(const product & fab_product, const int amount);
        // Get the left warehouse from the tree.
        int get_left(warehouse * & left_warehouse) const;
        // Get the right warehouse from the tree.
        int get_right(warehouse * & right_warehouse) const;
        // Display the warhouse.
        int display(void) const;

    private:
        char * company; // company name
        distribution_type distribution; // distribution type
        warehouse * left; // left warehouse under this one
        warehouse * right; // right warehouse under this one
};

#endif
