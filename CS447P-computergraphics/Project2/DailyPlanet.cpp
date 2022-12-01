#include "DailyPlanet.h"

DailyPlanet::DailyPlanet() {
    initialized = false;
}

bool DailyPlanet::Initialize(Point3D location, GLfloat topSize) {
    float heights[] = { 3.5, 1.5, 3.5, 4.5, 1.5, 1.5, 3.5, 8.5, 1.5, 1.5 };
    Point3D size = Point3D(12.0, 12.0, heights[0]);
    int i = 0;

    for(int i = 0; i < SECTION_COUNT; i++) {
        size.z = heights[i];
        sections[i].Initialize(location, size);
        location.x += 0.5;
        location.y += 0.5;
        location.z += heights[i];
        size.x -= 1.0;
        size.y -= 1.0;
    }
    
    Point3D topSectionSize = Point3D(1.0, 1.0, 2.0);

    topSection.Initialize(
        location + 
        Point3D(size.x / 2 - topSectionSize.x / 2,
                size.y / 2 - topSectionSize.x / 2, 0.0), 
        topSectionSize);
    topSphere.Initialize(
        location + 
        Point3D(1.0, 1.0, topSize + topSectionSize.z - (topSize / 16)), 
        topSize);
    topSphere.Subdivide(1);
    initialized = true;
    return true;
}

void DailyPlanet::Draw() {
    for(int i = 0; i < SECTION_COUNT; i++) {
        sections[i].Draw();
    }

    topSection.Draw();
    topSphere.Render(true);
}

void DailyPlanet::Subdivide(unsigned int n) {
    topSphere.Subdivide(n);
}
