#include "utils.h"

void utils::TexturedQuadStrip(Point3D& left, Point3D& right, Point3D offset,
    int count, bool stretch, bool moveLeft, bool moveRight) {
    GLfloat ty = 0.0;
    int i = 0;

    for(int i = 0; i <= count; i++) {
        if(i > 0) {
            if(stretch) {
                if(count - 1 == 0) {
                    ty = 1.0;
                }
                else {
                    ty = (float)i / (float)count;
                }
            }
            else {
                ty = (ty == 0.0 ? 1.0 : 0.0);
            }
        }

        glTexCoord2f(0.0, ty);
        glVertex3f(left.x, left.y, left.z);

        glTexCoord2f(1.0, ty);
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
