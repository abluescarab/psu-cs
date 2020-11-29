/* Alana Gilston - 11/16/2020 - CS163 - Program 4
 * media.cpp
 *
 * This is the implementation file for the "media" class. It manages a media
 * item (such as a video) for a college course.
 */
#include <iostream>
#include <cstring>
#include "media.h"
#include "utils.h"

using namespace std;



media::media(void) {
    title = nullptr;
    description = nullptr;
    course = nullptr;
    next_media = nullptr;
}



media::~media(void) {
    if(title) {
        delete [] title;
        title = nullptr;
    }

    if(description) {
        delete [] description;
        description = nullptr;
    }

    if(course) {
        delete [] course;
        course = nullptr;
    }

    if(next_media) {
        delete [] next_media;
        next_media = nullptr;
    }
}



// Copy from media item members.
// INPUT:
//  new_title: The title to copy
//  new_description: The description to copy
//  new_course: The course (class) title to copy
//  new_next: The next media item's name
// OUTPUT:
//  0 if the copy fails due to missing data
//  1 if the copy succeeds
int media::copy(const char * new_title, const char * new_description,
        const char * new_course, const char * new_next) {
    if(char_array_empty(new_title) ||
       char_array_empty(new_description) ||
       char_array_empty(new_course))
        return 0;

    copy_char_array(title, new_title);
    copy_char_array(description, new_description);
    copy_char_array(course, new_course);
    copy_char_array(next_media, new_next);
    return 1;
}



// Copy from another media item.
// INPUT:
//  to_copy: The media item to copy
// OUTPUT:
//  0 if the copy is missing data
//  1 if the copy succeeds
int media::copy(const media & to_copy) {
    if(to_copy.is_empty()) return 0;

    copy_char_array(title, to_copy.title);
    copy_char_array(description, to_copy.description);
    copy_char_array(course, to_copy.course);
    copy_char_array(next_media, to_copy.next_media);
    return 1;
}



// Display the content of the media item.
// OUTPUT:
//  0 if the item is missing data
//  1 if the display succeeds
int media::display(void) const {
    if(is_empty()) return 0;

    cout << "\"" << title << "\"" << endl;
    cout << "Description: " << description << endl;
    cout << "Course: " << course << endl;

    if(char_array_empty(next_media))
        cout << "Next media: N/A" << endl;
    else
        cout << "Next media: " << next_media << endl;

    return 1;
}



// Check if the media is missing data.
// OUTPUT:
//  0 if the media item has all data
//  1 if the media item is missing data
int media::is_empty(void) const {
    return char_array_empty(title) ||
           char_array_empty(description) ||
           char_array_empty(course);
}



// Check if the item is in a provided course.
// INPUT:
//  check_course: The course to check for
// OUTPUT:
//  0 if the data is not a match
//  1 if the data matches
int media::in_course(const char * check_course) const {
    if(char_array_empty(course) ||
       char_array_empty(check_course))
           return 0;

    return strcmp(course, check_course) == 0;
}



// Compare media title against provided title.
// INPUT:
//  to_compare: The title to compare against
// OUTPUT:
//  Returns the result of strcmp with the original title on the right
int media::compare_title(const char * to_compare) const {
    return strcmp(to_compare, title);
}



// Compare media title against another media item.
// INPUT:
//  to_compare: The media item to compare against
// OUTPUT:
//  Returns the value of strcmp with the original title on the right
int media::compare_title(const media & to_compare) const {
    return compare_title(to_compare.title);
}
