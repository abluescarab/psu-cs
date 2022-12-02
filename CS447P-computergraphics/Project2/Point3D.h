#ifndef _POINT3D_H_
#define _POINT3D_H_

#include <Windows.h> // needed to avoid errors with importing gl.h
#include <GL/gl.h>
#include <ostream>

struct Point3D {
    GLfloat x;
    GLfloat y;
    GLfloat z;

    Point3D();
    Point3D(GLfloat value);
    Point3D(GLfloat x, GLfloat y, GLfloat z);
    Point3D(const Point3D& point);
    ~Point3D();

    friend std::ostream& operator<<(std::ostream& os, Point3D const& p);
    friend Point3D operator+(const Point3D& p1, const Point3D& p2);
    friend Point3D operator+(const Point3D& p1, const GLfloat add);
    friend Point3D operator-(const Point3D& p1, const Point3D& p2);
    friend Point3D operator-(const Point3D& p1, const GLfloat sub);
    friend Point3D operator*(const Point3D& p1, const Point3D& p2);
    friend Point3D operator*(const Point3D& p1, const GLfloat mul);
    friend Point3D operator/(const Point3D& p1, const Point3D& p2);
    friend Point3D operator/(const Point3D& p1, const GLfloat div);
    Point3D& operator+=(const Point3D& other);
    Point3D& operator-=(const Point3D& other);
    Point3D& operator*=(const Point3D& other);
    Point3D& operator/=(const Point3D& other);
};

#endif