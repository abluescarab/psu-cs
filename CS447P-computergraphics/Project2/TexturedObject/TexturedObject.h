#ifndef _TEXTURED_OBJECT_H_
#define _TEXTURED_OBJECT_H_

#include <Fl/gl.h>

class TexturedObject {
protected:
    GLubyte display_list;
    GLuint texture_obj;
    bool initialized;

public:
    TexturedObject();
    ~TexturedObject();
    virtual bool Initialize(const char * texture);
    virtual void InitializeDisplay();
    virtual void Draw();
};

#endif