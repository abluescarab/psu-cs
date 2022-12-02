#ifndef _DAILY_PLANET_H_
#define _DAILY_PLANET_H_

#include "Building.h"
#include "../Sphere/Sphere.h"
#include "../Sphere/QuarterSphere.h"

class DailyPlanet {
private:
    static const int SECTION_COUNT = 10;
    static const int SIDE_SECTION_COUNT = 4;

    Building sections[SECTION_COUNT];
    Building top_section;
    QuarterSphere side_spheres[SIDE_SECTION_COUNT];
    Sphere top_sphere;
    bool initialized;

public:
    DailyPlanet();
    bool Initialize(Point3D location, GLfloat topSize, bool center = true);
    void Draw();
    void Subdivide(unsigned int n);
    void ResetSubdivide();
};

#endif