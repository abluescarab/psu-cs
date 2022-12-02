#ifndef _DAILY_PLANET_H_
#define _DAILY_PLANET_H_

#include "Building.h"
#include "Sphere.h"

class DailyPlanet {
private:
    static const int SECTION_COUNT = 10;

    Building sections[SECTION_COUNT];
    Building topSection;
    Sphere topSphere;
    bool initialized;

public:
    DailyPlanet();
    bool Initialize(Point3D location, GLfloat topSize, bool center = true);
    void Draw();
    void Subdivide(unsigned int n);
    void ResetSubdivide();
};

#endif