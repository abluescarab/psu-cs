#include <iostream>
#include "media.h"
#include "utils.h"

using namespace std;



media::media(void) {
    title = nullptr;
    description = nullptr;
    course = nullptr;
    next_media = nullptr;
}



~media::media(void) {
    delete [] title;
    title = nullptr;
    delete [] description;
    description = nullptr;
    delete [] course;
    course = nullptr;
    delete [] next_media;
    next_media = nullptr;
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
       char_array_empty(new_course) ||
       char_array_empty(new_next))
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
int media::copy(const & media to_copy) {
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
    cout << description << endl;
    cout << "Class: " << course << endl;
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
           char_array_empty(course) ||
           char_array_empty(next_media);
}



// Check if the media name matches a provided title.
// INPUT:
//  media_title: The media title to compare
// OUTPUT:
//  0 if the title is not a match
//  1 if the title is a match
int media::is_match(const char * media_title) const {
    return strcmp(title, media_title) == 0;
}
