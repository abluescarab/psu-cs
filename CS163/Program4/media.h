#ifndef MEDIA_H
#define MEDIA_H

class media {
    public:
        media(void);
        ~media(void);
        // Copy from media item members.
        int copy(const char * new_title, const char * new_description,
                const char * new_course, const char * new_next);
        // Copy from another media item.
        int copy(const & media to_copy);
        // Display the content of the media item.
        int display(void) const;
        // Check if the media is missing data.
        int is_empty(void) const;
        // Check if the media name matches a provided title.
        int is_match(const char * media_title) const;

    private:
        char * title; // the title of the media
        char * description; // the description of the material in the media
        char * course; // the course (class) the media is for
        char * next_media; // the next media item to watch in the same class
};

#endif
