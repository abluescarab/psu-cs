#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include "lab3.h"

#define CHAR_LIMIT 4096

int main() {
    char input[CHAR_LIMIT];
    char * result[CHAR_LIMIT];
    int index = 0;
    int quit = 0;

    signal(SIGCHLD, handle_sigchld);

    while(!quit) {
        while(fgets(input, CHAR_LIMIT, stdin) == NULL) {
            fflush(stdin);
            printf("sillyshell$ ");
        }
        // fgets(input, CHAR_LIMIT, stdin);

        index = convert_to_tokens(input, result);

        printf("%s", result[index - 1]);

        if(*result[index - 1] == '&') {
            printf("yes\n");
        }

        // implement support for exit
        exit(0);
    }

    return 0;
}

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

int parse_input(char ** input) {
    // TODO
    return 0;
}

void handle_sigchld(int signal) {
    // TODO
}
