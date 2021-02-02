/* Alana Gilston - 1/10/2021 - CS202 - Program 1
 * product.h
 *
 * This is the header file for the product class. The product class manages a 
 * product passed through the supply chain.
 */
#ifndef PRODUCT_H
#define PRODUCT_H

class path_node {
    public:
        path_node(void);
        path_node(const char * new_company);
        path_node(const path_node & other_node);
        ~path_node(void);
        // Get the next node in the list.
        path_node * & get_next(void);
        // Display the contents of the path node.
        int display(void) const;

    private:
        char * company; // company name
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
        // Get the current amount of product.
        int get_amount(void) const;
        // Update the path.
        int update_path(const char * company);
        // Update the path from another product.
        int append_path(product & other_product);
        // Get the next product in the list.
        product * & get_next(void);
        // Check if the name matches the provided name.
        int name_matches(const char * other_name) const;
        // Check if the name matches the provided product.
        int name_matches(const product & other_product) const;
        // Display the product.
        int display(void) const;
        // Display the path.
        int display_path(void);
        // Check if the product has no data.
        int is_empty(void) const;
        
    private:
        // Update the path recursively.
        int update_path(path_node * & node, const char * company);
        // Update the path from another product recursively.
        int append_path(path_node * & node, path_node * & other_node);
        // Clear the path.
        int clear_path(path_node * & current);
        // Display the path recursively.
        int display_path(path_node * & current);

        char * name; // name of the product
        int amount; // amount in inventory
        product * next; // next product in list
        path_node * path; // path node list
};

#endif
