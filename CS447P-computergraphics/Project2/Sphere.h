/*
 * Sphere.h: Code for sphere generation
 *
 * (c) 2001 Stephen Chenney, University of Wisconsin at Madison
 */

#ifndef _SPHERE_H_
#define _SPHERE_H_

#include <stdio.h>
#include <math.h>
#include "Point3D.h"

typedef struct _Vertex {
    GLfloat x[3];
} Vertex, * VertexPtr;

typedef struct _Edge {
    unsigned int vs;      // start vertex
    unsigned int ve;      // end vertex
    unsigned int v_new;  
    unsigned int s_child; // start child
    unsigned int e_child; // end child
} Edge, * EdgePtr;

typedef struct _Triangle {
    unsigned int edges[3];
    bool forward[3];
} Triangle, * TrianglePtr;


class Sphere {
private:
    unsigned int num_vertices;
    Vertex* vertices;
    unsigned int num_edges;
    Edge* edges;
    unsigned int num_faces;
    Triangle* faces;
    bool initialized = false;
    Point3D location;
    GLfloat size;

    Vertex VertexLocation(Vertex v, Point3D location);

public:
    Sphere(void);
    ~Sphere(void);

    bool Initialize(Point3D location, GLfloat size);
    void Subdivide(unsigned int);
    void Render(const bool smooth = true);
};

#endif