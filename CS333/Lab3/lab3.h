#ifndef LAB3_H
#define LAB3_H

int convert_to_tokens(char *, char **);
int parse_input(char **, int);
void create_handlers();
void handle_sigchld(int);

#endif
