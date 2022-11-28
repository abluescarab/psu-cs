#include "Building.h"
#include "utils.h"
#include "Point2D.h"

bool Building::Initialize(Point3D location, Point3D size) {
    this->location = location;
    this->size = size;
    return TexturedObject::Initialize("building.tga");
}

void Building::InitializeDisplay() {
    /*
                       (0, 0, 1)     (0, 1, 0)
                               ^     ^
                               |    /
           x, y + s, z + s /|--------/| x + s, y + s, z + s
                          / |       / |
                         /  |      /  |
            x, y, z + s |---+-----|   | x + s, y, z + s
                        |   |     |   |
        (-1, 0, 0) <-   |   |     |   |   -> (1, 0, 0)
                        |   |     |   |
            x, y + s, z |  /------+--/ x + s, y + s, z
                        | /       | /
                x, y, z |/________|/ x + s, y, z
                           /    |
                          v     v
                 (0, -1, 0)     (0, 0, -1)
    */

    Point3D l = location;
    Point3D s = size;
    Point3D imageScale = Point3D(s.y / 10, s.x / 10, s.z / 10);

    glBegin(GL_QUADS);

    // top face
    glNormal3f(0.0f, 0.0f, 1.0f);
    glVertex3f(l.x, l.y, l.z + s.z);                // bottom left
    glVertex3f(l.x + s.x, l.y, l.z + s.z);          // bottom right
    glVertex3f(l.x + s.x, l.y + s.y, l.z + s.z);    // top right
    glVertex3f(l.x, l.y + s.y, l.z + s.z);          // top left
    // bottom face
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(l.x, l.y, l.z);                      // bottom left
    glVertex3f(l.x, l.y + s.y, l.z);                // top left
    glVertex3f(l.x + s.x, l.y + s.y, l.z);          // top right
    glVertex3f(l.x + s.x, l.y, l.z);                // bottom right
    // right face
    glNormal3f(1.0f, 0.0f, 0.0f);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(l.x + s.x, l.y, l.z);                // bottom left
    glTexCoord2f(imageScale.x, 0.0);
    glVertex3f(l.x + s.x, l.y + s.y, l.z);          // bottom right
    glTexCoord2f(imageScale.x, imageScale.z);
    glVertex3f(l.x + s.x, l.y + s.y, l.z + s.z);    // top right
    glTexCoord2f(0.0, imageScale.z);
    glVertex3f(l.x + s.x, l.y, l.z + s.z);          // top left
    // left face
    glNormal3f(-1.0f, 0.0f, 0.0f);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(l.x, l.y, l.z);                      // bottom left
    glTexCoord2f(0.0, imageScale.z);
    glVertex3f(l.x, l.y, l.z + s.z);                // top left
    glTexCoord2f(imageScale.x, imageScale.z);
    glVertex3f(l.x, l.y + s.y, l.z + s.z);          // top right
    glTexCoord2f(imageScale.x, 0.0);
    glVertex3f(l.x, l.y + s.y, l.z);                // bottom right
    // front face
    glNormal3f(0.0f, -1.0f, 0.0f);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(l.x, l.y, l.z);                      // bottom left
    glTexCoord2f(imageScale.y, 0.0);
    glVertex3f(l.x + s.x, l.y, l.z);                // bottom right
    glTexCoord2f(imageScale.y, imageScale.z);
    glVertex3f(l.x + s.x, l.y, l.z + s.z);          // top right
    glTexCoord2f(0.0, imageScale.z);
    glVertex3f(l.x, l.y, l.z + size.z);             // top left
    // back face
    glNormal3f(0.0f, 1.0f, 0.0f);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(l.x, l.y + s.y, l.z);                // bottom left
    glTexCoord2f(0.0, imageScale.z);
    glVertex3f(l.x, l.y + s.y, l.z + s.z);          // top left
    glTexCoord2f(imageScale.y, imageScale.z);
    glVertex3f(l.x + s.x, l.y + s.y, l.z + s.z);    // top right
    glTexCoord2f(imageScale.y, 0.0);
    glVertex3f(l.x + s.x, l.y + s.y, l.z);          // bottom right

    glEnd();
}
