#ifndef _HALF_SPHERE_H_
#define _HALF_SPHERE_H_

#include "../Point3D.h"
#include "Sphere.h"

class HalfSphere : public Sphere {
private:
    Orientation orientation;

    void RotateX(); // rotate around x axis
    void RotateY(); // rotate around y axis
    void RotateZ(); // rotate around z axis

public:
    bool Initialize(Point3D location, GLfloat size, Orientation orientation = Orientation::z_pos);
    void Rotate();
    void SetOrientation(Orientation orientation);
    void ResetSubdivide();
};

#endif
