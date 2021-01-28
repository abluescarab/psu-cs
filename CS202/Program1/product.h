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

class path_node {
    public:
        path_node(void);
        path_node(char * new_company, 
                const distribution_type new_distribution);
        path_node(const path_node & other_node);
        ~path_node(void);
        // Set the next node in the list.
        int set_next(path_node * & next_node);
        // Get the next node in the list.
        int get_next(path_node * & next_node) const;
        // Display the contents of the path node.
        int display(void) const;

    private:
        char * company; // company name
        distribution_type distribution; // distribution type
        path_node * next; // next path node in the list
};

class product {
    public:
        product(void);
        product(const char * new_name);
        product(const char * new_name, 
                const int new_amount);
        product(const product & other_product);
        ~product(void);
        // Change the amount.
        int change_amount(const int new_amount);
        // Increment the amount.
        int increment_amount(const int new_amount, 
                const bool subtract = false);
        // Increment the amount by another product.
        int increment_amount(const product & other_product, 
                const bool subtract = false);
        // Update the path.
        int update_path(char * company, distribution_type distribution);
        // Get the next product in the list.
        int get_next(product * & next_product) const;
        // Check if the name matches the provided name.
        int name_matches(const char * other_name) const;
        // Check if the name matches the provided product.
        int name_matches(const product & other_product) const;
        // Display the product.
        int display(void) const;
        
    private:
        // Update the path recursively.
        int update_path(path_node * & node, char * company, distribution_type distribution);

        char * name; // name of the product
        int amount; // amount in inventory
        product * next; // next product in list
        path_node * path; // path node list
};

#endif
