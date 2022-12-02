#include "HalfSphere.h"
#include <Fl/gl.h>

void HalfSphere::RotateX() {
    Orientation new_o = orientation;

    switch(orientation) {
        case Orientation::z_pos:
            new_o = Orientation::y_pos;
            break;
        case Orientation::y_pos:
            new_o = Orientation::z_neg;
            break;
        case Orientation::z_neg:
            new_o = Orientation::y_neg;
            break;
        case Orientation::y_neg:
            new_o = Orientation::z_pos;
            break;
    }

    SetOrientation(new_o);
}

void HalfSphere::RotateY() {
    Orientation new_o = orientation;

    switch(orientation) {
        case Orientation::z_pos:
            new_o = Orientation::x_pos;
            break;
        case Orientation::x_pos:
            new_o = Orientation::z_neg;
            break;
        case Orientation::z_neg:
            new_o = Orientation::x_neg;
            break;
        case Orientation::x_neg:
            new_o = Orientation::z_pos;
            break;
    }

    SetOrientation(new_o);
}

void HalfSphere::RotateZ() {
    Orientation new_o = orientation;

    switch(orientation) {
        case Orientation::y_pos:
            new_o = Orientation::x_pos;
            break;
        case Orientation::x_pos:
            new_o = Orientation::y_neg;
            break;
        case Orientation::y_neg:
            new_o = Orientation::x_neg;
            break;
        case Orientation::x_neg:
            new_o = Orientation::y_pos;
            break;
    }

    SetOrientation(new_o);
}

bool HalfSphere::Initialize(Point3D location, GLfloat size,
    Orientation orientation, unsigned int subdivisions = 0) {
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
            vertices[5].x[2] = 0.0f;
            break;
        case Orientation::z_neg:
            vertices[4].x[2] = 0.0f;
            break;
        default:
            // same as z_neg orientation
            vertices[5].x[2] = 0.0f;
            break;
    }

    initialized = true;

    if(subdivisions > 0) {
        Subdivide(subdivisions);
    }

    return true;
}

void HalfSphere::Rotate(bool rotate_x, bool rotate_y, bool rotate_z) {
    if(rotate_x) {
        RotateX();
    }
    else if(rotate_y) {
        RotateY();
    }
    else if(rotate_z) {
        RotateZ();
    }
}

void HalfSphere::SetOrientation(Orientation orientation) {
    if(initialized) {
        ResetSubdivide();
        Initialize(location, size, orientation);
    }
}

void HalfSphere::ResetSubdivide(unsigned int subdivisons = 0) {
    if(initialized) {
        Reset();
        Initialize(location, size, orientation);
    }
}