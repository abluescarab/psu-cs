#ifndef _ROAD_H_
#define _ROAD_H_

#include <Fl/gl.h>
#include "TexturedObject.h"
#include "Point3D.h"

class Road : public TexturedObject {
private:
    static void TexturedQuadStrip(Point3D& left, Point3D& right, Point3D offset,
        int count, bool stretch = false, bool moveLeft = true,
        bool moveRight = true);

public:
    bool Initialize();
    void InitializeDisplay();
};

#endif