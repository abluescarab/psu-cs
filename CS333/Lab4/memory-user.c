// ********************************************************
// Name:       Alana Gilston
// Class:      CS333
// Assignment: Lab 4
// File:       memory-user.c
// ********************************************************
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char * argv[]) {
    int arr_size = 0;
    int index = 0;
    int bytes = 0;
    char element;
    char * array;

    if(argc != 2) {
        fprintf(stderr, "usage: %s <mb>\n", argv[0]);
        fprintf(stderr, "where <mb> is the number of megabytes\n");
        return 1;
    }

    printf("Current process ID: %d\n", getpid());

    arr_size = strtol(argv[1], NULL, 10);
    bytes = arr_size * 1000000 * sizeof(char);

    array = (char*)malloc(bytes);
    memset(array, 'a', bytes);

    while(1) {
        if(index == arr_size) {
            index = 0;
        }

        element = array[index];
    }

    free(array);
}
