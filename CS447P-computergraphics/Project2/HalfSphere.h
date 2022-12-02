#ifndef _HALF_SPHERE_H_
#define _HALF_SPHERE_H_

#include "Point3D.h"
#include "Sphere.h"

class HalfSphere : public Sphere {
private:
    Orientation orientation;

public:
    bool Initialize(Point3D location, GLfloat size, Orientation orientation = Orientation::z_pos);
    const Orientation GetOrientation();
    void SetOrientation(Orientation orientation);
    void ResetSubdivide();
};

#endif
