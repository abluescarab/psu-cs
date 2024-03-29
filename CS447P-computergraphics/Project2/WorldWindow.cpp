/*
 * CS559 Maze Project
 *
 * Class file for the WorldWindow class.
 *
 * (c) Stephen Chenney, University of Wisconsin at Madison, 2001-2002
 *
 */

#include "WorldWindow.h"
#include <Fl/math.h>
#include <Fl/gl.h>
#include <GL/glu.h>
#include <stdio.h>

const double WorldWindow::FOV_X = 45.0;

WorldWindow::WorldWindow(int x, int y, int width, int height, char* label)
    : Fl_Gl_Window(x, y, width, height, label)
{
    button = -1;

    // Moved initial viewing parameters to ResetCamera()
    ResetCamera();
}


void
WorldWindow::draw(void)
{
    double  eye[3];
    float   color[4], dir[4];

    if(!valid())
    {
        // Stuff in here doesn't change from frame to frame, and does not
        // rely on any coordinate system. It only has to be done if the
        // GL context is damaged.

        double	fov_y;

        // Sets the clear color to sky blue.
        glClearColor(0.53f, 0.81f, 0.92f, 1.0);

        // Turn on depth testing
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        // Turn on back face culling. Faces with normals away from the viewer
        // will not be drawn.
        glEnable(GL_CULL_FACE);

        // Enable lighting with one light.
        glEnable(GL_LIGHT0);
        glEnable(GL_LIGHTING);

        // Ambient and diffuse lighting track the current color.
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
        glEnable(GL_COLOR_MATERIAL);

        // Turn on normal vector normalization. You don't have to give unit
        // normal vector, and you can scale objects.
        glEnable(GL_NORMALIZE);

        // Set up the viewport.
        glViewport(0, 0, w(), h());

        // Set up the persepctive transformation.
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        fov_y = 360.0f / M_PI * atan(h() * tan(FOV_X * M_PI / 360.0) / w());
        gluPerspective(fov_y, w() / (float)h(), 1.0, 1000.0);

        // Do some light stuff. Diffuse color, and zero specular color
        // turns off specular lighting.
        color[0] = 1.0f; color[1] = 1.0f; color[2] = 1.0f; color[3] = 1.0f;
        glLightfv(GL_LIGHT0, GL_DIFFUSE, color);
        color[0] = 0.0f; color[1] = 0.0f; color[2] = 0.0f; color[3] = 1.0f;
        glLightfv(GL_LIGHT0, GL_SPECULAR, color);

        // Initialize all the objects.
        ground.Initialize();
        traintrack.Initialize();
        road.Initialize();

        building[ 0].Initialize(Point3D(-16.0f, 24.0f, 0.1f), Point3D( 3.0f,  3.0f, 13.0f));
        building[ 1].Initialize(Point3D(-16.0f, 27.0f, 0.1f), Point3D( 3.0f,  6.0f, 11.0f));
        building[ 2].Initialize(Point3D(-16.0f, 33.0f, 0.1f), Point3D( 3.0f,  3.0f, 13.0f));
        building[ 3].Initialize(Point3D(-13.0f, 24.0f, 0.1f), Point3D( 6.0f,  3.0f, 11.0f));
        building[ 4].Initialize(Point3D(-13.0f, 33.0f, 0.1f), Point3D( 6.0f,  3.0f, 11.0f));
        building[ 5].Initialize(Point3D( -7.0f, 24.0f, 0.1f), Point3D( 3.0f,  3.0f, 13.0f));
        building[ 6].Initialize(Point3D( -7.0f, 27.0f, 0.1f), Point3D( 3.0f,  6.0f, 11.0f));
        building[ 7].Initialize(Point3D( -7.0f, 33.0f, 0.1f), Point3D( 3.0f,  3.0f, 13.0f));
        building[ 8].Initialize(Point3D( -7.0f, 30.0f, 8.0f), Point3D(10.0f,  3.0f,  2.0f));
        building[ 9].Initialize(Point3D( -3.0f, 24.0f, 0.1f), Point3D( 3.0f, 14.0f,  7.0f));
        building[10].Initialize(Point3D(  0.5f, 35.0f, 0.1f), Point3D(15.0f,  5.0f, 20.0f));
        building[11].Initialize(Point3D(  0.5f, 25.0f, 0.1f), Point3D( 5.0f, 10.0f, 20.0f));
        building[12].Initialize(Point3D(  8.0f, 30.0f, 0.1f), Point3D( 8.0f,  4.0f, 17.0f));
        building[13].Initialize(Point3D( 16.0f, 30.0f, 0.1f), Point3D( 3.0f,  9.0f, 17.0f));

        half_sphere[0].Initialize(Point3D(-14.5f, 25.5f, 13.1f), 1.5f, Orientation::z_pos, 1);
        half_sphere[1].Initialize(Point3D(-14.5f, 34.5f, 13.1f), 1.5f, Orientation::z_pos, 1);
        half_sphere[2].Initialize(Point3D( -5.5f, 25.5f, 13.1f), 1.5f, Orientation::z_pos, 1);
        half_sphere[3].Initialize(Point3D( -5.5f, 34.5f, 13.1f), 1.5f, Orientation::z_pos, 1);

        daily_planet.Initialize(Point3D(0.0f, 0.0f, 0.1f), 3.0f);
    }

    // Stuff out here relies on a coordinate system or must be done on every
    // frame.

    // Clear the screen. Color and depth.
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Set up the viewing transformation. The viewer is at a distance
    // dist from (x_at, y_ay, z_at) in the direction (theta, phi) defined
    // by two angles. They are looking at (x_at, y_ay, z_at) and z is up.
    eye[0] = x_at + dist * cos(theta * M_PI / 180.0) * cos(phi * M_PI / 180.0);
    eye[1] = y_at + dist * sin(theta * M_PI / 180.0) * cos(phi * M_PI / 180.0);
    eye[2] = z_at + dist * sin(phi * M_PI / 180.0);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    gluLookAt(eye[0], eye[1], eye[2], x_at, y_at, z_at, 0.0, 0.0, 1.0);

    // Position the light source. This has to happen after the viewing
    // transformation is set up, so that the light stays fixed in world
    // space. This is a directional light - note the 0 in the w component.
    dir[0] = 1.0; dir[1] = 1.0; dir[2] = 1.0; dir[3] = 0.0;
    glLightfv(GL_LIGHT0, GL_POSITION, dir);

    // Draw stuff. Everything.
    float posn[3];
    float new_phi;
    float new_theta;

    ground.Draw();
    traintrack.Draw(posn, new_phi, new_theta);

    if(camera_follow) {
        theta = new_theta;
        phi = -new_phi - 1.0f; // adjustment for better view angle
        x_at = posn[0];
        y_at = posn[1];
        z_at = posn[2] + 5.0f;
        dist = -0.1f;
    }

    road.Draw();
    daily_planet.Draw();

    for(int i = 0; i < HALF_SPHERE_COUNT; i++) {
        half_sphere[i].Render();
    }

    for(int i = 0; i < BUILDING_COUNT; i++) {
        building[i].Draw();
    }
}


void
WorldWindow::Drag(float dt)
{
    int	    dx = x_down - x_last;
    int     dy = y_down - y_last;

    switch(button)
    {
        case FL_LEFT_MOUSE:
            // Left button changes the direction the viewer is looking from.
            theta = theta_down + 360.0f * dx / (float)w();
            while(theta >= 360.0f)
                theta -= 360.0f;
            while(theta < 0.0f)
                theta += 360.0f;
            phi = phi_down + 90.0f * dy / (float)h();
            if(phi > 89.0f)
                phi = 89.0f;
            if(phi < -5.0f)
                phi = -5.0f;
            break;
        case FL_MIDDLE_MOUSE:
            // Middle button moves the viewer in or out.
            dist = dist_down - (0.5f * dist_down * dy / (float)h());
            if(dist < 1.0f)
                dist = 1.0f;
            break;
        case FL_RIGHT_MOUSE: {
            // Right mouse button moves the look-at point around, so the world
            // appears to move under the viewer.
            float	x_axis[2];
            float	y_axis[2];

            x_axis[0] = -(float)sin(theta * M_PI / 180.0);
            x_axis[1] = (float)cos(theta * M_PI / 180.0);
            y_axis[0] = x_axis[1];
            y_axis[1] = -x_axis[0];

            x_at = x_at_down + 100.0f * (x_axis[0] * dx / (float)w()
                + y_axis[0] * dy / (float)h());
            y_at = y_at_down + 100.0f * (x_axis[1] * dx / (float)w()
                + y_axis[1] * dy / (float)h());
        } break;
        default:;
    }
}

void WorldWindow::ChangeCamera(float xAngle, float zAngle) {
    phi = xAngle;
    theta = zAngle;
    dist = 100.0f;
    x_at = 0.0f;
    y_at = 0.0f;
    z_at = 2.0f;
    camera_follow = false;
}

void WorldWindow::ResetCamera() {
    ChangeCamera(45.0f, 0.0f);
}

bool
WorldWindow::Update(float dt)
{
    // Update the view. This gets called once per frame before doing the
    // drawing.

    if(button != -1 && !camera_follow) // Only do anything if the mouse button is down.
        Drag(dt);

    // Animate the train.
    traintrack.Update(dt);

    return true;
}


int
WorldWindow::handle(int event)
{
    // Event handling routine. Only looks at mouse events.
    // Stores a bunch of values when the mouse goes down and keeps track
    // of where the mouse is and what mouse button is down, if any.
    switch(event)
    {
        case FL_PUSH:
            button = Fl::event_button();
            x_last = x_down = Fl::event_x();
            y_last = y_down = Fl::event_y();
            phi_down = phi;
            theta_down = theta;
            dist_down = dist;
            x_at_down = x_at;
            y_at_down = y_at;
            return 1;
        case FL_DRAG:
            x_last = Fl::event_x();
            y_last = Fl::event_y();
            return 1;
        case FL_RELEASE:
            button = -1;
            return 1;
        case FL_KEYBOARD:
            switch(Fl::event_key()) {
                case 's': // subdivide
                    daily_planet.Subdivide(1);

                    for(int i = 0; i < HALF_SPHERE_COUNT; i++) {
                        half_sphere[i].Subdivide(1);
                    }

                    return 1;
                case 'r': // reset subdivisions
                    daily_planet.ResetSubdivide();

                    for(int i = 0; i < HALF_SPHERE_COUNT; i++) {
                        half_sphere[i].ResetSubdivide();
                    }

                    return 1;
                case '1': // front view
                    ChangeCamera(0.0, -90.0);
                    return 1;
                case '2': // left view
                    ChangeCamera(0.0, 180.0);
                    return 1;
                case '3': // back view
                    ChangeCamera(0.0, 90.0);
                    return 1;
                case '4': // right view
                    ChangeCamera(0.0, 0.0);
                    return 1;
                case '5': // top view
                    ChangeCamera(90.0, -90.0);
                    return 1;
                case '6': // reset view
                    ResetCamera();
                    return 1;
                case ' ': // space key follows car
                    camera_follow = true;
                    return 1;
            }
    }

    // Pass any other event types on the superclass.
    return Fl_Gl_Window::handle(event);
}
