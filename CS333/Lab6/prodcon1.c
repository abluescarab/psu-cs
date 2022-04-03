// ********************************************************
// Name:       Alana Gilston
// Class:      CS333
// Assignment: Lab 6
// File:       prodcon1.c
// ********************************************************
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct {
    char * buffer;
    char filename[8];
    int file_number;
} threadarg_t;

typedef struct {
    // int x;
    // int y;
} threadret_t;

void * producer_thread(void * arg) {
    threadarg_t * args = (threadarg_t *)arg;
    FILE * file = fopen(args->filename, "r");

    if(file == NULL) {
        printf("Could not read from %s.\n", args->filename);
        exit(1);
    }

    fgets(args->buffer, 256, file);
    return NULL;
}

void * consumer_thread(void * arg) {
    threadarg_t * args = (threadarg_t *)arg;
    FILE * file = fopen(args->filename, "w");

    if(file == NULL) {
        printf("Could not write to %s.\n", args->filename);
        exit(1);
    }

    fprintf(file, "This is file %d.", args->file_number);
    return NULL;
}

int main(int argc, char *argv[]) {
    pthread_t thread[20];
    char buffer[256];
    threadarg_t args;
    int file_number = 0;

    for(int i = 0; i < 20; i += 2) {
        sprintf(args.filename, "in%d.txt", file_number);
        args.buffer = &buffer[0];
        args.file_number = file_number;

        file_number++;

        int cons_rc = pthread_create(&thread[i], NULL, consumer_thread, &args);
        int prod_rc = pthread_create(&thread[i + 1], NULL, producer_thread, &args);
    }

    // for(int i = 0; i < 10; i++) {
    //     sprintf(filename, "in%d.txt", i);
    //     output = fopen(filename, "w");

    //     if(output == NULL) {
    //         printf("Cannot write to file. Exiting.\n");
    //         return 1;
    //     }

    //     fprintf(output, "This is file %d.", i);
    //     fclose(output);
    // }

    return 0;
}

// int main(int argc, char *argv[]) {
//     int thread_count = 0;
//     pthread_t * p;
//     threadret_t *rvals;
//     threadarg_t args = { 10, 20 };

//     if(argc != 2) {
//         fprintf(stderr, "usage: %s <n>\n", argv[0]);
//         fprintf(stderr, "where <n> is the number of threads to create\n");
//         return 1;
//     }

//     thread_count = strtol(argv[1], NULL, 10);
//     p = (pthread_t*)malloc(thread_count * sizeof(pthread_t));

//     for(int i = 0; i < thread_count; i++) {
//         int rc = pthread_create(&p[i], NULL, mythread, &args);
//     }

//     for(int i = 0; i < thread_count; i++) {
//         pthread_join(p[i], (void **)&rvals);
//     }

//     free(p);
//     return 0;
// }
