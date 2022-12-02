#include "TexturedObject.h"
#include "../libtarga.h"
#include <iostream>
#include <GL/glu.h>

TexturedObject::TexturedObject() {
    display_list = 0;
    texture_obj = 0;
    initialized = false;
}

TexturedObject::~TexturedObject() {
    if(initialized) {
        glDeleteLists(display_list, 1);
        glDeleteTextures(1, &texture_obj);
    }
}

bool TexturedObject::Initialize(const char * texture) {
    ubyte* image_data;
    int width;
    int height;

    if(!(image_data = (ubyte*)tga_load(texture, &width, &height, TGA_TRUECOLOR_24))) {
        fprintf(stderr, "TexturedObject::Initialize: Couldn't load \"%s\"\n.", texture);
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
    // turn on texturing and bind
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, texture_obj);

    InitializeDisplay();

    glDisable(GL_TEXTURE_2D);
    glEndList();
    initialized = true;
    return true;
}

void TexturedObject::InitializeDisplay() {
    return;
}

void TexturedObject::Draw() {
    glPushMatrix();
    glCallList(display_list);
    glPopMatrix();
}