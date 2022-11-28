#ifndef _ROAD_H_
#define _ROAD_H_

#include <Fl/gl.h>
#include "TexturedObject.h"

class Road : public TexturedObject {
public:
    bool Initialize();
    void InitializeDisplay();
};

#endif