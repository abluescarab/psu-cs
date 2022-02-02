#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include "lab3.h"

#define CHAR_LIMIT 4096
#define FALSE 0
#define TRUE 1

int main() {
    char input[CHAR_LIMIT];
    char * result[CHAR_LIMIT];
    int index = 0;

    signal(SIGCHLD, handle_sigchld);

    while(TRUE) {
        printf("sillyshell$ ");

        // ensure that fgets gives valid input
        while(fgets(input, CHAR_LIMIT, stdin) == NULL) {
            fflush(stdin);
        }

        // remove the new line character
        input[strlen(input) - 1] = '\0';
        index = convert_to_tokens(input, result);

        printf("%d\n", index);

        if(strcmp(result[0], "exit") == 0) {
            exit(0);
        }

        // parse the input, setting in_background to whether the last token was &
        parse_input(result, *result[index - 1] == '&');
    }

    return 0;
}

// modified from https://stackoverflow.com/a/10349393/567983
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

int parse_input(char ** input, int in_background) {
    // TODO
    if(in_background == TRUE) {
        printf("yes");
    }

    return 0;
}

void handle_sigchld(int signal) {
    // TODO
}
