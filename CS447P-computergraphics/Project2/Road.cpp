#include "Road.h"
#include "Point3D.h"
#include "libtarga.h"
#include "utils.h"
#include <GL/glu.h>
#include <iostream>

Road::Road() {
    display_list = 0; 
    texture_obj = 0;
    initialized = false;
}

Road::~Road() {
    if(initialized) {
        glDeleteLists(display_list, 1);
        glDeleteTextures(1, &texture_obj);
    }
}

bool Road::Initialize() {
    ubyte* image_data;
    int width;
    int height;

    if(!(image_data = (ubyte*)tga_load("road.tga", &width, &height, TGA_TRUECOLOR_24))) {
        fprintf(stderr, "Road::Initialize: Couldn't load \"road.tga\"\n.");
        return false;
    }

    // create and bind the texture
    glGenTextures(1, &texture_obj);
    glBindTexture(GL_TEXTURE_2D, texture_obj);
    // how to load the texture
    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
    // build the texture
    gluBuild2DMipmaps(GL_TEXTURE_2D, 3, width, height, GL_RGB, GL_UNSIGNED_BYTE, image_data);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
    // modulate texture by underlying color
    glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
    // create geometry list
    display_list = glGenLists(1);
    glNewList(display_list, GL_COMPILE);
    // use white color
    glColor3f(1.0, 1.0, 1.0);
    // use normals facing up
    glNormal3f(0.0, 0.0, 1.0);
    // turn on texturing and bind
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, texture_obj);

    // draw road
    Point3D left = Point3D(-20.0, -20.0, 0.1);
    Point3D right = Point3D(-19.0, -19.0, 0.1);
    
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

    glDisable(GL_TEXTURE_2D);
    glEndList();

    initialized = true;
    return true;
}

void Road::Draw() {
    glPushMatrix();
    glCallList(display_list);
    glPopMatrix();
}