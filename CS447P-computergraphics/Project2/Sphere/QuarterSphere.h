#ifndef _QUARTER_SPHERE_H_
#define _QUARTER_SPHERE_H_

#include "../Point3D.h"
#include "Sphere.h"

class QuarterSphere : public Sphere {
private:
    Orientation v_face;
    Orientation h_face;

    void RotateV();
    void RotateH();

    int rotation_count = 0;

public:
    bool Initialize(Point3D location, GLfloat size, 
        Orientation v_face = Orientation::z_neg, 
        Orientation h_face = Orientation::y_pos);
    void Rotate();
    void SetOrientations(Orientation o1, Orientation o2);
    void ResetSubdivide();
};

#endif
