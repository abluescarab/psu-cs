#include "QuarterSphere.h"

void QuarterSphere::RotateV() {
    Orientation new_v = (v_face == Orientation::z_pos 
                                    ? Orientation::z_neg 
                                    : Orientation::z_pos);
    SetOrientations(new_v, h_face);
}

void QuarterSphere::RotateH() {
    Orientation new_h = h_face;

    switch(h_face) {
        case Orientation::y_pos:
            new_h = Orientation::x_pos;
            break;
        case Orientation::x_pos:
            new_h = Orientation::y_neg;
            break;
        case Orientation::y_neg:
            new_h = Orientation::x_neg;
            break;
        case Orientation::x_neg:
            new_h = Orientation::y_pos;
            break;
    }

    SetOrientations(v_face, new_h);
}

bool QuarterSphere::Initialize(Point3D location, GLfloat size, 
    Orientation v_face, Orientation h_face) {
    Sphere::Initialize(location, size);

    this->v_face = v_face;
    this->h_face = h_face;

    switch(v_face) {
        case Orientation::z_pos:
            vertices[5].x[2] = 0.0f;
            break;
        case Orientation::z_neg:
            vertices[4].x[2] = 0.0f;
            break;
        default:
            // same as z_neg orientation
            vertices[4].x[2] = 0.0f;
            break;
    }

    switch(h_face) {
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
        default:
            // same as y_pos orientation
            vertices[3].x[1] = 0.0f;
            break;
    }

    initialized = true;
    return true;
}

void QuarterSphere::Rotate(bool rotate_v, bool rotate_h) {
    if(rotate_v) {
        RotateV();
    }
    else if(rotate_h) {
        RotateH();
    }
}

void QuarterSphere::SetOrientations(Orientation v_face, Orientation h_face) {
    if(initialized) {
        ResetSubdivide();
        Initialize(location, size, v_face, h_face);
    }
}

void QuarterSphere::ResetSubdivide() {
    if(initialized) {
        Reset();
        Initialize(location, size, v_face, h_face);
    }
}
