/* Alana Gilston - 1/10/2021 - CS202 - Program 1
 * product.h
 *
 * This is the header file for the product class. The product class manages a 
 * product passed through the supply chain.
 */
#ifndef PRODUCT_H
#define PRODUCT_H

enum distribution_type {
    local,
    regional,
    national,
    manufacturer
};

enum priority_type {
    standard,
    two_day,
    one_day
};

class path_node {
    public:
        path_node(void);
        path_node(const char * new_company, 
                const distribution_type new_distribution);
        path_node(const path_node & other_node);
        ~path_node(void);
        // Get the next node in the list.
        int get_next(path_node & next_node) const;
        // Display the contents of the list.
        int display(void) const;

    private:
        char * company; // company name
        distribution_type distribution; // distribution type
        path_node * next; // next path node in the list
};

class product {
    public:
        product(void);
        product(const char * new_name, const priority_type new_priority,
                const int new_amount);
        product(const product & other_product);
        ~product(void);
        // Change the amount.
        int change_amount(const bool increment, const int new_amount);
        // Update the path.
        int update_path(const char * company, distribution_type distribution);
        // Get the next product in the list.
        int get_next(product * & next_product) const;
        // Display the product.
        int display(void) const;
        
    private:
        char * name; // name of the product
        priority_type priority; // shipping priority
        int amount; // amount in inventory
        product * next; // next product in list
        path_node * path; // path node list
};

#endif
