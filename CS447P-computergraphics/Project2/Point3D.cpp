#include "Point3D.h"

Point3D::Point3D() : Point3D(0.0, 0.0, 0.0) {
}

Point3D::Point3D(GLfloat value) : Point3D(value, value, value) {
}

Point3D::Point3D(GLfloat x, GLfloat y, GLfloat z) {
    this->x = x;
    this->y = y;
    this->z = z;
}

Point3D::Point3D(const Point3D & point) : Point3D(point.x, point.y, point.z) {
}

Point3D::~Point3D() {
    this->x = 0.0;
    this->y = 0.0;
    this->z = 0.0;
}

std::ostream & operator<<(std::ostream & os, Point3D const & p) {
    return os << "(" << p.x << ", " << p.y << ", " << p.z << ")";
}

Point3D operator+(const Point3D & p1, const Point3D & p2) {
    return Point3D(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
}

Point3D operator+(const Point3D & p1, const GLfloat add) {
    return Point3D(p1.x + add, p1.y + add, p1.z + add);
}

Point3D operator-(const Point3D & p1, const Point3D & p2) {
    return Point3D(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
}

Point3D operator-(const Point3D & p1, const GLfloat sub) {
    return Point3D(p1.x - sub, p1.y - sub, p1.z - sub);
}

Point3D operator*(const Point3D & p1, const Point3D & p2) {
    return Point3D(p1.x * p2.x, p1.y * p2.y, p1.z * p2.z);
}

Point3D operator*(const Point3D & p1, const GLfloat mul) {
    return Point3D(p1.x * mul, p1.y * mul, p1.z * mul);
}

Point3D operator/(const Point3D & p1, const Point3D & p2) {
    return Point3D(p1.x / p2.x, p1.y / p2.y, p1.z / p2.z);
}

Point3D operator/(const Point3D & p1, const GLfloat div) {
    return Point3D(p1.x / div, p1.y / div, p1.z / div);
}

Point3D & Point3D::operator+=(const Point3D & other) {
    this->x += other.x;
    this->y += other.y;
    this->z += other.z;
    return *this;
}

Point3D & Point3D::operator-=(const Point3D & other) {
    this->x -= other.x;
    this->y -= other.y;
    this->z -= other.z;
    return *this;
}

Point3D & Point3D::operator*=(const Point3D & other) {
    this->x *= other.x;
    this->y *= other.y;
    this->z *= other.z;
    return *this;
}

Point3D & Point3D::operator/=(const Point3D & other) {
    this->x /= other.x;
    this->y /= other.y;
    this->z /= other.z;
    return *this;
}
