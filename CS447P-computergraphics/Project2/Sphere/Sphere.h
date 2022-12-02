/*
 * Sphere.h: Code for sphere generation
 *
 * (c) 2001 Stephen Chenney, University of Wisconsin at Madison
 */

#ifndef _SPHERE_H_
#define _SPHERE_H_

#include "../Point3D.h"

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

enum class Orientation { x_neg, x_pos, y_neg, y_pos, z_neg, z_pos };

class Sphere {
protected:
    unsigned int num_vertices;
    Vertex * vertices;
    unsigned int num_edges;
    Edge * edges;
    unsigned int num_faces;
    Triangle * faces;
    bool initialized = false;
    Point3D location;
    GLfloat size;

    Vertex VertexLocation(Vertex v, Point3D location);
    void Reset();

public:
    Sphere(void);
    ~Sphere(void);

    virtual bool Initialize(Point3D location, GLfloat size, unsigned int subdivisions = 0);
    void Subdivide(unsigned int);
    void Render(const bool smooth = true);
    virtual void ResetSubdivide(unsigned int subdivisons = 0);
};

#endif
