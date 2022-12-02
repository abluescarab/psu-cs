#include "Road.h"

void Road::TexturedQuadStrip(Point3D & left, Point3D & right, Point3D offset,
    int count, bool stretch, bool moveLeft, bool moveRight) {
    GLfloat ty = 0.0f;
    int i = 0;

    for(int i = 0; i <= count; i++) {
        if(i > 0) {
            if(stretch) {
                if(count - 1 == 0) {
                    ty = 1.0f;
                }
                else {
                    ty = (float)i / (float)count;
                }
            }
            else {
                ty = (ty == 0.0f ? 1.0f : 0.0f);
            }
        }

        glTexCoord2f(0.0f, ty);
        glVertex3f(left.x, left.y, left.z);

        glTexCoord2f(1.0f, ty);
        glVertex3f(right.x, right.y, right.z);

        if(i < count) {
            if(moveLeft) {
                left += offset;
            }

            if(moveRight) {
                right += offset;
            }
        }
    }
}

bool Road::Initialize() {
    return TexturedObject::Initialize("road.tga");
}

void Road::InitializeDisplay() {
    Point3D left = Point3D(-20.0f, -20.0f, 0.1f);
    Point3D right = Point3D(-19.0f, -19.0f, 0.1f);

    glNormal3f(0.0, 0.0, 1.0);
    glBegin(GL_QUAD_STRIP);
    Road::TexturedQuadStrip(left, right, Point3D( 0.0f,  1.0f, 0.0f), 39, false);
    Road::TexturedQuadStrip(left, right, Point3D( 0.0f,  1.0f, 0.0f), 2, false, true, false);
    Road::TexturedQuadStrip(left, right, Point3D( 1.0f,  0.0f, 0.0f), 39, false);
    Road::TexturedQuadStrip(left, right, Point3D( 1.0f,  0.0f, 0.0f), 2, false, true, false);
    Road::TexturedQuadStrip(left, right, Point3D( 0.0f, -1.0f, 0.0f), 39, false);
    Road::TexturedQuadStrip(left, right, Point3D( 0.0f, -1.0f, 0.0f), 2, false, true, false);
    Road::TexturedQuadStrip(left, right, Point3D(-1.0f,  0.0f, 0.0f), 39, false);
    Road::TexturedQuadStrip(left, right, Point3D(-1.0f,  0.0f, 0.0f), 2, false, true, false);
    glEnd();
}
