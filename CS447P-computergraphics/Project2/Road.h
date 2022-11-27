#ifndef _ROAD_H_
#define _ROAD_H_

#include <Fl/gl.h>

class Road {
private:
    GLubyte display_list;
    GLuint texture_obj;
    bool initialized;

public:
    Road();
    ~Road();
    bool Initialize();
    void Draw();
};

#endif