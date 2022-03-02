// ********************************************************
// Name:       Alana Gilston
// Class:      CS333
// Assignment: Lab 5
// File:       myfourth.c
// ********************************************************
#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

typedef struct {
    int a;
    int b;
    int * runningtot;
    pthread_mutex_t * lock;
} myarg_t;

typedef struct {
    int x;
    int y;
} myret_t;

void *mythread(void *arg) {
    myarg_t *args = (myarg_t *) arg;

    pthread_mutex_lock(args->lock);
    ++(*args->runningtot);

    printf("Running total (during): %d\n", *args->runningtot);
    printf("TID: %lu\n", pthread_self());
    printf("%d %d\n", args->a, args->b);

    pthread_mutex_unlock(args->lock);

    return NULL;
}

int main(int argc, char *argv[]) {
    int thread_count = 0;
    int runningtot = 0;
    pthread_t * p;
    pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
    myret_t *rvals;
    myarg_t args = { 10, 20, &runningtot, &lock };

    if(argc != 2) {
        fprintf(stderr, "usage: %s <n>\n", argv[0]);
        fprintf(stderr, "where <n> is the number of threads to create\n");
        return 1;
    }

    thread_count = strtol(argv[1], NULL, 10);
    p = (pthread_t*)malloc(thread_count * sizeof(pthread_t));

    for(int i = 0; i < thread_count; i++) {
        printf("Running total (before): %d\n", runningtot);
        int rc = pthread_create(&p[i], NULL, mythread, &args);
        printf("Running total (after):  %d\n", runningtot);
    }

    for(int i = 0; i < thread_count; i++) {
        pthread_join(p[i], (void **)&rvals);
    }

    free(p);
    return 0;
}
