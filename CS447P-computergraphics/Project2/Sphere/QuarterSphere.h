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

public:
    bool Initialize(Point3D location, GLfloat size,
        Orientation v_face = Orientation::z_neg,
        Orientation h_face = Orientation::y_pos,
        unsigned int subdivisions = 0);
    void Rotate(bool rotate_v, bool rotate_h);
    void SetOrientations(Orientation v_face, Orientation h_face);
    void ResetSubdivide(unsigned int subdivisons = 0);
};

#endif
