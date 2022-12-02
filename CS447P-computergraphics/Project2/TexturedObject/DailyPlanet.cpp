#include "DailyPlanet.h"

DailyPlanet::DailyPlanet() {
    initialized = false;
}

bool DailyPlanet::Initialize(Point3D location, GLfloat top_size, bool center) {
    float heights[] = { 3.5, 1.5, 3.5, 4.5, 1.5, 1.5, 3.5, 8.5, 1.5, 1.5 };
    Point3D size = Point3D(12.0, 12.0, heights[0]);
    int i = 0;

    if(center) {
        location.x -= size.x / 2;
        location.y -= size.y / 2;
    }

    for(int i = 0; i < SECTION_COUNT; i++) {
        size.z = heights[i];
        sections[i].Initialize(location, size);
        location.x += 0.5;
        location.y += 0.5;
        location.z += heights[i];
        size.x -= 1.0;
        size.y -= 1.0;
    }

    Point3D top_section_size = Point3D(1.0, 1.0, 1.5);
    GLfloat side_size = 1.0;
    Point3D side_loc = Point3D(location.x + 0.5, location.y + 1.0, location.z);
     
    side_spheres[0].Initialize(side_loc, side_size, Orientation::z_pos, 
        Orientation::x_pos, 1);
    side_loc.x += 1.0;
    side_spheres[1].Initialize(side_loc, side_size, Orientation::z_pos, 
        Orientation::x_neg, 1);
    side_loc.x -= 0.5;
    side_loc.y -= 0.5;
    side_spheres[2].Initialize(side_loc, side_size, Orientation::z_pos, 
        Orientation::y_neg, 1);
    side_loc.y += 1.0;
    side_spheres[3].Initialize(side_loc, side_size, Orientation::z_pos, 
        Orientation::y_pos, 1);

    top_section.Initialize(
        location + 
        Point3D(size.x / 2 - top_section_size.x / 2,
                size.y / 2 - top_section_size.x / 2, 0.0), 
        top_section_size);
    top_sphere.Initialize(
        location + 
        Point3D(1.0, 1.0, top_size + top_section_size.z - (top_size / 16)), 
        top_size,
        1);
    initialized = true;
    return true;
}

void DailyPlanet::Draw() {
    if(initialized) {
        for(int i = 0; i < SECTION_COUNT; i++) {
            sections[i].Draw();
        }

        for(int i = 0; i < SIDE_SECTION_COUNT; i++) {
            side_spheres[i].Render();
        }

        top_section.Draw();
        top_sphere.Render(true);
    }
}

void DailyPlanet::Subdivide(unsigned int n) {
    top_sphere.Subdivide(n);

    for(int i = 0; i < SIDE_SECTION_COUNT; i++) {
        side_spheres[i].Subdivide(n);
    }
}

void DailyPlanet::ResetSubdivide() {
    top_sphere.ResetSubdivide();
}