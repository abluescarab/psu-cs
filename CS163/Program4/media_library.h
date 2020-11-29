/* Alana Gilston - 11/16/2020 - CS163 - Program 4
 * media_library.h
 *
 * This is the header file for the "media library" class. It manages a library 
 * of media items using a binary search tree.
 */
#ifndef MEDIA_LIBRARY_H
#define MEDIA_LIBRARY_H
#include "media.h"

struct vertex {
    media media_item;
    vertex * left;
    vertex * right;
};

class media_library {
    public:
        media_library(void);
        ~media_library(void);
        // Add a new media item.
        int add(const media & new_media);
        // Remove a media item.
        int remove(const char * title);
        // Remove all media items in a course.
        int remove_by_course(const char * course);
        // Clear the tree.
        int clear(void);
        // Search for a media item.
        int search(const char * title, media & result) const;
        // Display all media items.
        int display(void) const;
        // Display a media item.
        int display_by_title(const char * title) const;
        // Display all media items in a course.
        int display_by_course(const char * course) const;
        // Get the height of the tree.
        int height(void) const;

    private:
        vertex * root; // the root of the media item tree

        // Add a new media item recursively.
        int add(vertex * & root, const media & new_media);
        // Remove a media item by name recursively.
        int remove(vertex * & root, vertex * & parent, const char * title);
        // Remove all media items from a course recursively.
        int remove_by_course(vertex * & root, vertex * & parent, const char * course);
        // Remove a media item recursively.
        int remove(vertex * & root, vertex * & parent);
        // Clear the tree recursively.
        int clear(vertex * & root);
        // Search for a media item recursively.
        int search(const vertex * root, const char * title, media & result) const;
        // Display all media items recursively.
        int display(const vertex * root) const;
        // Display all media items in a course recursively.
        int display_by_course(const vertex * root, const char * course) const;
        // Get the height of the tree recursively.
        int height(const vertex * root) const;
        // Get the inorder successor of the current vertex.
        int inorder_successor(vertex * root, vertex * & result) const;
};

#endif
