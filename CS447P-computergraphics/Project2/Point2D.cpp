#include "Point2D.h"

Point2D::Point2D() : Point2D(0.0, 0.0) {
}

Point2D::Point2D(GLfloat value) : Point2D(value, value) {
}

Point2D::Point2D(GLfloat x, GLfloat y) {
    this->x = x;
    this->y = y;
}

Point2D::Point2D(const Point2D& point) : Point2D(point.x, point.y) {
}

Point2D::~Point2D() {
    this->x = 0.0;
    this->y = 0.0;
}

std::ostream& operator<<(std::ostream& os, Point2D const& p) {
    return os << "(" << p.x << ", " << p.y << ")";
}

Point2D operator+(const Point2D& p1, const Point2D& p2) {
    return Point2D(p1.x + p2.x, p1.y + p2.y);
}

Point2D& Point2D::operator+=(const Point2D& other) {
    this->x += other.x;
    this->y += other.y;
    return *this;
}