// ********************************************************
// Name:       Alana Gilston
// Class:      CS333
// Assignment: Lab 5
// File:       mythird.c
// ********************************************************
#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

typedef struct {
    int a;
    int b;
} myarg_t;

typedef struct {
    int x;
    int y;
} myret_t;

void *mythread(void *arg) {
    myarg_t *args = (myarg_t *) arg;
    printf("TID: %lu\n", pthread_self());
    printf("%d %d\n", args->a, args->b);
    return NULL;
}

int main(int argc, char *argv[]) {
    int thread_count = 0;
    pthread_t * p;
    myret_t *rvals;
    myarg_t args = { 10, 20 };

    if(argc != 2) {
        fprintf(stderr, "usage: %s <n>\n", argv[0]);
        fprintf(stderr, "where <n> is the number of threads to create\n");
        return 1;
    }

    thread_count = strtol(argv[1], NULL, 10);
    p = (pthread_t*)malloc(thread_count * sizeof(pthread_t));

    for(int i = 0; i < thread_count; i++) {
        int rc = pthread_create(&p[i], NULL, mythread, &args);
    }

    for(int i = 0; i < thread_count; i++) {
        pthread_join(p[i], (void **)&rvals);
    }

    free(p);
    return 0;
}
