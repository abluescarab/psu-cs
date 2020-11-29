/* Alana Gilston - 11/16/2020 - CS163 - Program 4
 * media.h
 *
 * This is the header file for the "media" class. It manages a media item
 * (such as a video) for a college course.
 */
#ifndef MEDIA_H
#define MEDIA_H
#include "utils.h"

class media {
    public:
        media(void);
        ~media(void);
        // Copy from media item members.
        int copy(const char * new_title, const char * new_description,
                const char * new_course, const char * new_next);
        // Copy from another media item.
        int copy(const media & to_copy);
        // Display the content of the media item.
        int display(void) const;
        // Check if the media is missing data.
        int is_empty(void) const;
        // Check if the item is in a provided course.
        int in_course(const char * check_course) const;
        // Compare media title against provided title.
        int compare_title(const char * to_compare) const;
        // Compare media title against another media item.
        int compare_title(const media & to_compare) const;

    private:
        char * title; // the title of the media
        char * description; // the description of the material in the media
        char * course; // the course (class) the media is for
        char * next_media; // the next media item to watch in the same class
};

#endif
