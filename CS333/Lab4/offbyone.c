// ********************************************************
// Name:       Alana Gilston
// Class:      CS333
// Assignment: Lab 4
// File:       offbyone.c
// ********************************************************
#include <stdlib.h>

int main() {
    int * data = (int*)malloc(100 * sizeof(int)); 

    data[100] = 0;
    free(data);
}
