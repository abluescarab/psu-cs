#ifndef _POINT2D_H_
#define _POINT2D_H_

#include <Windows.h> // needed to avoid errors with importing gl.h
#include <GL/gl.h>
#include <ostream>

struct Point2D {
    GLfloat x;
    GLfloat y;
    
    Point2D();
    Point2D(GLfloat value);
    Point2D(GLfloat x, GLfloat y);
    Point2D(const Point2D& point);
    ~Point2D();

    friend std::ostream& operator<<(std::ostream& os, Point2D const& p);
    friend Point2D operator+(const Point2D& p1, const Point2D& p2);
    Point2D& operator+=(const Point2D& other);
};

#endif