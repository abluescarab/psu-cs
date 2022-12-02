#ifndef _BUILDING_H_
#define _BUILDING_H_

#include <FL/gl.h>
#include "TexturedObject.h"
#include "../Point3D.h"

class Building : public TexturedObject {
private:
    Point3D size;
    Point3D location;

public:
    bool Initialize(Point3D location, Point3D size);
    void InitializeDisplay();
};

#endif