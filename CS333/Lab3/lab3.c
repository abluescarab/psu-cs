// ********************************************************
// Name:       Alana Gilston
// Class:      CS333
// Assignment: Lab3 (sillyshell)
// File:       lab3.c
// ********************************************************
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include "lab3.h"

#define CHAR_LIMIT 1024

int main() {
    char input[CHAR_LIMIT];
    char * result[CHAR_LIMIT];
    int index = 0;
    pid_t pid = -1;
    int in_background = 0;
    int pid_status;

    create_handlers();

    while(1) {
        printf("sillyshell$ ");

        // ensure that fgets gives valid input
        while(fgets(input, CHAR_LIMIT, stdin) == NULL) {
            fflush(stdin);
        }

        // remove the new line character
        input[strlen(input) - 1] = '\0';
        index = convert_to_tokens(input, result);

        // check if the last element in the result is an ampersand, store that
        // value, then null it so it doesn't affect execvp
        if(*result[index - 1] == '&') {
            result[index - 1] = NULL;
            in_background = 1;
        }
        else {
            in_background = 0;
        }

        pid = fork();

        if(pid < 0) {
            printf("Forking process failed.");
            return 1;
        }
        else if(pid == 0) {
            execvp(result[0], result);
            // ensure that invalid commands are closed afterward
            exit(0);
        }
        else {
            if(!in_background) {
                waitpid(pid, &pid_status, 0);
            }
        }

        if(strcmp(result[0], "exit") == 0) {
            // print the exiting message before the while loop
            printf("Exiting...\n");
            while(waitpid(-1, NULL, 0) > 0);
            exit(0);
        }
    }

    return 0;
}

// Modified from https://stackoverflow.com/a/10349393/567983
int convert_to_tokens(char * input, char ** result) {
    char * token;
    int index = 0;

    result[index++] = input;
    token = input;

    while(*token) {
        if(*token == ' ') {
            *token = 0;
            result[index++] = token + 1;
        }

        token++;
    }

    result[index] = NULL;
    return index;
}

// Create the signal handlers for SIGCHLD.
void create_handlers() {
    struct sigaction new_action;
    struct sigaction old_action;

    new_action.sa_handler = handle_sigchld;
    sigemptyset(&new_action.sa_mask);
    new_action.sa_flags = 0;

    sigaction(SIGCHLD, NULL, &old_action);

    if(old_action.sa_handler != SIG_IGN) {
        sigaction(SIGCHLD, &new_action, NULL);
    }
}

// Handler for SIGCHLD.
void handle_sigchld(int signal) {
    while(waitpid(-1, 0, WNOHANG) > 0);
}
