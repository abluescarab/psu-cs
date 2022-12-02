#include "HalfSphere.h"
#include <Fl/gl.h>

bool HalfSphere::Initialize(Point3D location, GLfloat size, Orientation orientation) {
    Sphere::Initialize(location, size);

    this->orientation = orientation;

    switch(orientation) {
        case Orientation::x_neg:
            vertices[1].x[0] = 0.0f;
            break;
        case Orientation::x_pos:
            vertices[0].x[0] = 0.0f;
            break;
        case Orientation::y_neg:
            vertices[2].x[1] = 0.0f;
            break;
        case Orientation::y_pos:
            vertices[3].x[1] = 0.0f;
            break;
        case Orientation::z_pos:
            vertices[4].x[2] = 0.0f;
            break;
        case Orientation::z_neg:
            vertices[5].x[2] = 0.0f;
            break;
        default:
            // same as z_neg orientation
            vertices[5].x[2] = 0.0f;
            break;
    }

    initialized = true;
    return true;
}

const Orientation HalfSphere::GetOrientation() {
    return orientation;
}

void HalfSphere::SetOrientation(Orientation orientation) {
    Reset();
    Initialize(this->location, this->size, orientation);
}

void HalfSphere::ResetSubdivide() {
    Reset();
    Initialize(this->location, this->size, this->orientation);
}