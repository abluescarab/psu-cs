/* Alana Gilston - 11/16/2020 - CS163 - Program 4
 * media_library.cpp
 *
 * This is the implementation file for the "media library" class. It manages a 
 * library of media items using a binary search tree.
 */
#include <iostream>
#include "media_library.h"
#include "utils.h"

using namespace std;



media_library::media_library(void) {
    root = nullptr;
}



media_library::~media_library(void) {
    clear();
}



// Add a new media item.
// INPUT:
//  new_media: The media item to add
// OUTPUT:
//  0 if the media item is missing data
//  Otherwise returns value of recursive add
int media_library::media_library::add(const media & new_media) {
    if(new_media.is_empty())
        return 0;

    return add(root, new_media);
}



// Add a new media item recursively.
// INPUT:
//  root: The first vertex to start from
//  new_media: The media item to add
// OUTPUT:
//  Always returns 1 if adding succeeded
int media_library::add(vertex * & root, const media & new_media) {
    if(!root) {
        root = new vertex;
        root->media_item.copy(new_media);
        root->left = nullptr;
        root->right = nullptr;
        return 1;
    }

    int compare = root->media_item.compare_title(new_media);

    if(compare < 0)
        return add(root->left, new_media);
    
    return add(root->right, new_media);
}



// Remove a media item.
// INPUT:
//  title: The title of the item to remove
// OUTPUT:
//  0 if the title is empty
//  Otherwise returns value of recursive remove
int media_library::remove(const char * title) {
    if(char_array_empty(title))
        return 0;

    vertex * parent = nullptr;
    return remove(root, parent, title);
}



// Remove a media item recursively.
// INPUT:
//  root: The vertex to start from
//  parent: The parent of the current vertex
//  title: The title of the media item to remove
// OUTPUT:
//  0 if the media item does not exist
//  1 if the item is removed successfully
int media_library::remove(vertex * & root, vertex * & parent, const char * title) {
    if(!root)
        return 0;

    int compare = root->media_item.compare_title(title);

    if(compare < 0) {
        return remove(root->left, root, title);
    }
    else if(compare > 0) {
        return remove(root->right, root, title);
    }

    return remove(root, parent);
}



// Remove all media items in a course.
// INPUT:
//  course: Course for the items to remove
// OUTPUT:
//  0 if the course is empty
//  Otherwise returns value of the recursive remove
int media_library::remove_by_course(const char * course) {
    if(char_array_empty(course))
        return 0;

    vertex * parent = nullptr;
    return remove_by_course(root, parent, course);
}



// Remove all media items from a course recursively.
// INPUT:
//  root: The vertex to start from
//  parent: The parent of the current vertex
//  course: The course for the items to remove
// OUTPUT:
//  Returns the number of vertices removed
int media_library::remove_by_course(vertex * & root, vertex * & parent, const char * course) {
    if(!root)
        return 0;

    int count = remove_by_course(root->left, root, course) +
                remove_by_course(root->right, root, course);

    if(root->media_item.in_course(course))
        count += remove(root, parent);

    return count;
}



// Remove a media item recursively.
// INPUT:
//  root: The vertex to start from
//  parent: The parent of the current vertex
// OUTPUT:
//  0 if the vertex was not removed
//  1 if the vertex was removed successfully
int media_library::remove(vertex * & root, vertex * & parent) {
    if(!root)
        return 0;

    vertex * child = nullptr;
    bool left_child = false;

    if(root->left && root->right) { // two children
        inorder_successor(root->left, child);
        root->media_item.copy(child->media_item);
    }
    // xor to see if only one vertex exists
    else if(!root->left != !root->right) { // only one child
        // start with the left
        child = root->left;

        // since there is only one child, if the 
        // right child exists then the left does not
        if(root->right)
            child = root->right;

        // store whether the root is left or right child of parent
        if(parent && parent->left == root)
            left_child = true;
    }

    delete root;
    root = nullptr;

    if(child) {
        if(parent) {
            if(left_child)
                parent->left = child;
            else
                parent->right = child;
        }
        else
            this->root = child;
    }

    return 1;
}



// Clear the tree.
// OUTPUT:
//  Returns the value of the recursive clear
int media_library::clear(void) {
    return clear(root);
}


// Clear the tree recursively.
// INPUT:
//  root: The vertex to start from
// OUTPUT:
//  Returns the number of vertices removed
int media_library::clear(vertex * & root) {
    if(!root)
        return 0;

    int count = clear(root->left) + clear(root->right);

    delete root;
    root = nullptr;
    return count + 1;
}



// Search for a media item.
// INPUT:
//  title: The title to search for
//  result: The media item to return
// OUTPUT:
//  0 if the title is empty
//  Otherwise returns the value of the recursive search
int media_library::search(const char * title, media & result) const {
    if(char_array_empty(title))
        return 0;

    return search(root, title, result);
}



// Search for a media item recursively.
// INPUT:
//  root: The vertex to start from
//  title: The title for search for
//  result: The media item to return
// OUTPUT:
//  0 if the item is not found
//  1 if the item was found and returned
int media_library::search(const vertex * root, const char * title, media & result) const {
    if(!root)
        return 0;

    int compare = root->media_item.compare_title(title);

    if(compare < 0) {  
        return search(root->left, title, result);
    }
    else if(compare > 0) {
        return search(root->right, title, result);
    }

    result.copy(root->media_item);
    return 1;
}



// Display all media items.
// OUTPUT:
//  Returns the value of the recursive display
int media_library::display(void) const {
    return display(root);
}



// Display all media items recursively.
// INPUT:
//  root: The vertex to start from
// OUTPUT:
//  Returns the number of vertices displayed
int media_library::display(const vertex * root) const {
    if(!root)
        return 0;

    int count = display(root->left);
    count += root->media_item.display();
    cout << endl;
    count += display(root->right);

    return count;
}



// Display a media item by title.
// INPUT:
//  title: The title of the item to display
// OUTPUT:
//  0 if the title is empty or item not found
//  1 if the item is found and displays successfully
int media_library::display_by_title(const char * title) const {
    if(char_array_empty(title))
        return 0;

    media media_item;

    if(root)
        cout << endl;

    if(!search(title, media_item))
        return 0;

    return media_item.display();
}



// Display all media items in a course.
// INPUT:
//  course: The course for each item
// OUTPUT:
//  0 if the course is empty
//  Otherwise returns the value of recursive display
int media_library::display_by_course(const char * course) const {
    if(char_array_empty(course))
        return 0;

    if(root)
        cout << endl;

    return display_by_course(root, course);
}



// Display all media items in a course recursively.
// INPUT:
//  root: The vertex to start from
//  course: The course for each item
// OUTPUT:
//  Returns the number of vertices displayed
int media_library::display_by_course(const vertex * root, const char * course) const {
    if(!root)
        return 0;

    int count = display_by_course(root->left, course);
    
    if(root->media_item.in_course(course)) {
        root->media_item.display();
        cout << endl;
        ++count;
    }

    return count + display_by_course(root->right, course);
}



// Get the height of the tree.
// OUTPUT:
//  Returns the value of the recursive height
int media_library::height(void) const {
    return height(root);
}



// Get the height of the tree recursively.
// INPUT:
//  root: The vertex to start from
// OUTPUT:
//  Returns the height of the tree
int media_library::height(const vertex * root) const {
    if(!root)
        return 0;

    int left_height = height(root->left);
    int right_height = height(root->right);

    return std::max(left_height, right_height) + 1;
}



// Get the inorder successor of the current vertex.
// INPUT:
//  root: The vertex to start from
//  result: The vertex to return
// OUTPUT:
//  0 if successor is not found
//  1 if the successor is found and returned
int media_library::inorder_successor(vertex * root, vertex * & result) const {
    if(!root)
        return 0;

    if(!root->right) {
        result = root;
        return 1;
    }

    return inorder_successor(root->right, result);
}
