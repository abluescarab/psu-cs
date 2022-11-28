#include "Road.h"
#include "Point3D.h"
#include "utils.h"

bool Road::Initialize() {
    return TexturedObject::Initialize("road.tga");
}

void Road::InitializeDisplay() {
    Point3D left = Point3D(-20.0, -20.0, 0.1);
    Point3D right = Point3D(-19.0, -19.0, 0.1);
    
    glNormal3f(0.0, 0.0, 1.0);
    glBegin(GL_QUAD_STRIP);
    utils::TexturedQuadStrip(left, right, Point3D(0.0, 1.0, 0.0), 39, false);
    utils::TexturedQuadStrip(left, right, Point3D(0.0, 1.0, 0.0), 2, false, true, false);
    utils::TexturedQuadStrip(left, right, Point3D(1.0, 0.0, 0.0), 39, false);
    utils::TexturedQuadStrip(left, right, Point3D(1.0, 0.0, 0.0), 2, false, true, false);
    utils::TexturedQuadStrip(left, right, Point3D(0.0, -1.0, 0.0), 39, false);
    utils::TexturedQuadStrip(left, right, Point3D(0.0, -1.0, 0.0), 2, false, true, false);
    utils::TexturedQuadStrip(left, right, Point3D(-1.0, 0.0, 0.0), 39, false);
    utils::TexturedQuadStrip(left, right, Point3D(-1.0, 0.0, 0.0), 2, false, true, false);
    glEnd();
}
