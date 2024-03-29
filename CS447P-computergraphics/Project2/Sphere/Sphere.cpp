/*
 * Sphere.cpp: Code for sphere generation
 *
 * (c) 2001 Stephen Chenney, University of Wisconsin at Madison
 */

#include "Sphere.h"
#include <Fl/gl.h>

Vertex Sphere::VertexLocation(Vertex v, Point3D location) {
    Vertex result = Vertex(v);
    result.x[0] += location.x;
    result.x[1] += location.y;
    result.x[2] += location.z;
    return result;
}

Sphere::Sphere(void) {
    num_vertices = 6;
    num_edges = 12;
    num_faces = 8;

    vertices = new Vertex[num_vertices];
    edges = new Edge[num_edges];
    faces = new Triangle[num_faces];

    location = Point3D();
    size = 1.0;
}

Sphere::~Sphere(void) {
    delete[] vertices;
    delete[] edges;
    delete[] faces;
}

bool Sphere::Initialize(Point3D location, GLfloat size, unsigned int subdivisions) {
    this->location = location;
    this->size = size;

    vertices[0].x[0] = size;
    vertices[0].x[1] = 0.0f; // right vert
    vertices[0].x[2] = 0.0f;

    vertices[1].x[0] = -size;
    vertices[1].x[1] = 0.0f; // left vert
    vertices[1].x[2] = 0.0f;

    vertices[2].x[0] = 0.0f;
    vertices[2].x[1] = size;
    vertices[2].x[2] = 0.0f;

    vertices[3].x[0] = 0.0f;
    vertices[3].x[1] = -size;
    vertices[3].x[2] = 0.0f;

    vertices[4].x[0] = 0.0f;
    vertices[4].x[1] = 0.0f; // bottom vert
    vertices[4].x[2] = size;

    vertices[5].x[0] = 0.0f;
    vertices[5].x[1] = 0.0f; // top vert
    vertices[5].x[2] = -size;

    edges[0].vs = 0; edges[0].ve = 4;
    edges[1].vs = 2; edges[1].ve = 4;
    edges[2].vs = 1; edges[2].ve = 4;
    edges[3].vs = 3; edges[3].ve = 4;
    edges[4].vs = 0; edges[4].ve = 2;
    edges[5].vs = 2; edges[5].ve = 1;
    edges[6].vs = 1; edges[6].ve = 3;
    edges[7].vs = 3; edges[7].ve = 0;
    edges[8].vs = 0; edges[8].ve = 5;
    edges[9].vs = 2; edges[9].ve = 5;
    edges[10].vs = 1; edges[10].ve = 5;
    edges[11].vs = 3; edges[11].ve = 5;

    faces[0].edges[0] = 0; faces[0].forward[0] = false;
    faces[0].edges[1] = 4; faces[0].forward[1] = true;
    faces[0].edges[2] = 1; faces[0].forward[2] = true;
    faces[1].edges[0] = 1; faces[1].forward[0] = false;
    faces[1].edges[1] = 5; faces[1].forward[1] = true;
    faces[1].edges[2] = 2; faces[1].forward[2] = true;
    faces[2].edges[0] = 2; faces[2].forward[0] = false;
    faces[2].edges[1] = 6; faces[2].forward[1] = true;
    faces[2].edges[2] = 3; faces[3].forward[2] = true;
    faces[3].edges[0] = 3; faces[3].forward[0] = false;
    faces[3].edges[1] = 7; faces[3].forward[1] = true;
    faces[3].edges[2] = 0; faces[3].forward[2] = true;
    faces[4].edges[0] = 4; faces[4].forward[0] = false;
    faces[4].edges[1] = 8; faces[4].forward[1] = true;
    faces[4].edges[2] = 9; faces[4].forward[2] = false;
    faces[5].edges[0] = 5; faces[5].forward[0] = false;
    faces[5].edges[1] = 9; faces[5].forward[1] = true;
    faces[5].edges[2] = 10; faces[5].forward[2] = false;
    faces[6].edges[0] = 6; faces[6].forward[0] = false;
    faces[6].edges[1] = 10; faces[6].forward[1] = true;
    faces[6].edges[2] = 11; faces[6].forward[2] = false;
    faces[7].edges[0] = 7; faces[7].forward[0] = false;
    faces[7].edges[1] = 11; faces[7].forward[1] = true;
    faces[7].edges[2] = 8; faces[7].forward[2] = false;

    initialized = true;

    if(subdivisions > 0) {
        Subdivide(subdivisions);
    }

    return true;
}

void Sphere::Subdivide(unsigned int n) {
    if(!initialized)
        return;

    unsigned int    num_v;
    Vertex * new_v;
    unsigned int    num_e;
    Edge * new_e;
    unsigned int    num_f;
    Triangle * new_f;
    unsigned int    i, j;

    // Copy old vertices over
    new_v = new Vertex[num_vertices + num_edges];
    for(i = 0; i < num_vertices; i++) {
        new_v[i].x[0] = vertices[i].x[0];
        new_v[i].x[1] = vertices[i].x[1];
        new_v[i].x[2] = vertices[i].x[2];
    }
    num_v = num_vertices;

    // Split all the edges.
    new_e = new Edge[num_edges * 2 + num_faces * 3];
    num_e = 0;
    for(i = 0; i < num_edges; i++) {
        double l = 0.0;

        for(j = 0; j < 3; j++) {
            new_v[num_v].x[j] = (vertices[edges[i].vs].x[j]
                + vertices[edges[i].ve].x[j]) * 0.5f;
            l += new_v[num_v].x[j] * new_v[num_v].x[j];
        }
        l = sqrt(l) / size;
        new_v[num_v].x[0] /= (float)l;
        new_v[num_v].x[1] /= (float)l;
        new_v[num_v].x[2] /= (float)l;
        edges[i].v_new = num_v;

        new_e[num_e].vs = edges[i].vs;
        new_e[num_e].ve = num_v;
        edges[i].s_child = num_e++;

        new_e[num_e].vs = num_v;
        new_e[num_e].ve = edges[i].ve;
        edges[i].e_child = num_e++;

        num_v++;
    }

    // Re-work the faces.
    new_f = new Triangle[num_faces * 4];
    num_f = 0;
    for(i = 0; i < num_faces; i++) {
        unsigned int	e1 = num_e++;
        unsigned int	e2 = num_e++;
        unsigned int	e3 = num_e++;
        TrianglePtr 	f;

        new_e[e1].vs = edges[faces[i].edges[0]].v_new;
        new_e[e1].ve = edges[faces[i].edges[1]].v_new;

        new_e[e2].vs = edges[faces[i].edges[1]].v_new;
        new_e[e2].ve = edges[faces[i].edges[2]].v_new;

        new_e[e3].vs = edges[faces[i].edges[2]].v_new;
        new_e[e3].ve = edges[faces[i].edges[0]].v_new;

        f = &(new_f[num_f++]);
        if(faces[i].forward[0]) {
            f->edges[0] = edges[faces[i].edges[0]].e_child;
            f->forward[0] = true;
        }
        else {
            f->edges[0] = edges[faces[i].edges[0]].s_child;
            f->forward[0] = false;
        }
        if(faces[i].forward[1]) {
            f->edges[1] = edges[faces[i].edges[1]].s_child;
            f->forward[1] = true;
        }
        else {
            f->edges[1] = edges[faces[i].edges[1]].e_child;
            f->forward[1] = false;
        }
        f->edges[2] = e1;
        f->forward[2] = false;

        f = &(new_f[num_f++]);
        f->edges[0] = e1;
        f->forward[0] = true;
        f->edges[1] = e2;
        f->forward[1] = true;
        f->edges[2] = e3;
        f->forward[2] = true;

        f = &(new_f[num_f++]);
        f->edges[0] = e2;
        f->forward[0] = false;
        if(faces[i].forward[1]) {
            f->edges[1] = edges[faces[i].edges[1]].e_child;
            f->forward[1] = true;
        }
        else {
            f->edges[1] = edges[faces[i].edges[1]].s_child;
            f->forward[1] = false;
        }
        if(faces[i].forward[2]) {
            f->edges[2] = edges[faces[i].edges[2]].s_child;
            f->forward[2] = true;
        }
        else {
            f->edges[2] = edges[faces[i].edges[2]].e_child;
            f->forward[2] = false;
        }

        f = &(new_f[num_f++]);
        f->edges[0] = e3;
        f->forward[0] = false;
        if(faces[i].forward[2]) {
            f->edges[1] = edges[faces[i].edges[2]].e_child;
            f->forward[1] = true;
        }
        else {
            f->edges[1] = edges[faces[i].edges[2]].s_child;
            f->forward[1] = false;
        }
        if(faces[i].forward[0]) {
            f->edges[2] = edges[faces[i].edges[0]].s_child;
            f->forward[2] = true;
        }
        else {
            f->edges[2] = edges[faces[i].edges[0]].e_child;
            f->forward[2] = false;
        }
    }

    delete[] vertices;
    delete[] edges;
    delete[] faces;

    num_vertices = num_v;
    vertices = new_v;
    num_edges = num_e;
    edges = new_e;
    num_faces = num_f;
    faces = new_f;

    if(n > 1) {
        Subdivide(n - 1);
    }
}

void Sphere::Render(const bool smooth) {
    if(!initialized)
        return;

    double  	    l;
    unsigned int    i, j;

    glBegin(GL_TRIANGLES);
    for(i = 0; i < num_faces; i++) {
        unsigned int    a, b, c;
        float		v1[3];
        float		v2[3];
        float   	n[3];

        a = faces[i].forward[0] ? edges[faces[i].edges[0]].vs
            : edges[faces[i].edges[0]].ve;
        b = faces[i].forward[1] ? edges[faces[i].edges[1]].vs
            : edges[faces[i].edges[1]].ve;
        c = faces[i].forward[2] ? edges[faces[i].edges[2]].vs
            : edges[faces[i].edges[2]].ve;

        if(smooth) {
            Vertex va = VertexLocation(vertices[a], location);
            Vertex vb = VertexLocation(vertices[b], location);
            Vertex vc = VertexLocation(vertices[c], location);

            glNormal3fv(va.x);
            glVertex3fv(va.x);
            glNormal3fv(vb.x);
            glVertex3fv(vb.x);
            glNormal3fv(vc.x);
            glVertex3fv(vc.x);
        }
        else {
            for(j = 0; j < 3; j++) {
                v1[j] = vertices[b].x[j] - vertices[a].x[j];
                v2[j] = vertices[c].x[j] - vertices[a].x[j];
            }
            n[0] = v1[1] * v2[2] - v1[2] * v2[1];
            n[1] = v1[2] * v2[0] - v1[0] * v2[2];
            n[2] = v1[0] * v2[1] - v1[1] * v2[0];
            l = sqrt(n[0] * n[0] + (n[1] * n[1]) + n[2] * n[2]);
            n[0] /= (float)l;
            n[1] /= (float)l;
            n[2] /= (float)l;

            glNormal3fv(n);
            glVertex3fv(VertexLocation(vertices[a], location).x);
            glVertex3fv(VertexLocation(vertices[b], location).x);
            glVertex3fv(VertexLocation(vertices[c], location).x);
        }
    }
    glEnd();
}

void Sphere::Reset() {
    delete[] vertices;
    delete[] edges;
    delete[] faces;

    num_vertices = 6;
    num_edges = 12;
    num_faces = 8;

    vertices = new Vertex[num_vertices];
    edges = new Edge[num_edges];
    faces = new Triangle[num_faces];
}

void Sphere::ResetSubdivide(unsigned int subdivisons) {
    if(initialized) {
        Reset();
        Initialize(this->location, this->size, subdivisons);
    }
}