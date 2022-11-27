#ifndef _UTILS_H_
#define _UTILS_H_

#include "FL/gl.h"
#include "Point3D.h"

class utils {
public:
    static void TexturedQuadStrip(Point3D& left, Point3D& right, Point3D offset,
        int count, bool stretch = false, bool moveLeft = true,
        bool moveRight = true);
};

#endif
