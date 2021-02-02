/* Alana Gilston - 1/10/2021 - CS202 - Program 1
 * inventory.h
 *
 * This is the header file for the inventory and warehouse classes. The
 * inventory class manages a product inventory in a supply chain while the
 * warehouse class manages a warehouse where products are stored. */
#ifndef INVENTORY_H
#define INVENTORY_H
#include "product.h"

enum distribution_type {
    local,
    regional,
    national
};

enum priority_type {
    standard,
    two_day,
    one_day
};

class inventory {
    public:
        inventory(void);
        inventory(const inventory & other_inventory);
        ~inventory(void);

        // Add a new product to the inventory by name.
        int add_product(const char * product_name, 
                const priority_type priority, 
                const int amount);
        // Add a new product to the inventory.
        int add_product(product & to_add, const priority_type priority);
        // Remove a product from the inventory by name.
        int remove_product(const char * product_name, 
                const priority_type priority, 
                const int amount);
        // Remove a product from the inventory.
        int remove_product(const product & to_remove, 
                const priority_type priority);
        // Clear the inventory.
        int clear(void);
        // Display the contents of the inventory.
        int display(void) const;

    protected:
        product ** products; // products in the inventory

    private:
        // Add a product recursively.
        int add_product(product * & current, product & to_add);
        // Remove a product recursively.
        int remove_product(product * & current, const product & to_remove);
        // Clear the inventory recursively.
        int clear(product * & current);
        // Display products recursively.
        int display(product * & current) const;
        // Copy another inventory category recursively.
        int copy(product & copy_from, product * & copy_to);
};

class warehouse : public inventory {
    public:
        warehouse(void);
        warehouse(const char * new_company);
        warehouse(const char * new_company, 
                const distribution_type new_distribution);
        warehouse(const warehouse & other_warehouse);
        ~warehouse(void);
        // Check if company matches.
        int company_matches(const char * to_compare);
        // Check if company matches.
        int company_matches(const warehouse & other_warehouse);
        // Get the left warehouse from the tree.
        warehouse * & get_left(void);
        // Get the right warehouse from the tree.
        warehouse * & get_right(void);
        // Display the warehouse.
        int display(bool show_inventory) const;
        // Check if the warehouse has no data.
        int is_empty(void) const;
        // Check if the warehouse has the specified amount of product.
        int has_product(const char * product_name, const int amount);
        // Check if the warehouse has the specified amount of product.
        int has_product(const product & to_check);
        // Get the name of the warehouse.
        const char * get_company(void) const;

    private:
        // Check if the warehouse has the specified amount of product recursively.
        int has_product(product * & current, const product & to_check);

        char * company; // company name
        distribution_type distribution; // distribution type
        warehouse * left; // left warehouse under this one
        warehouse * right; // right warehouse under this one
};

#endif
