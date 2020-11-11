/* Alana Gilston - 11/9/2020 - CS163 - Program 3
 * main.cpp
 *
 * This is the main function to test the item_table and item_queue classes.
 */
#include <iostream>
#include "item_table.h"
#include "item_queue.h"
#include "utils.h"
#define MAX_MENU_OPTION 5

using namespace std;



// Create a queue with values chosen from the item list.
// INPUT:
//  queue: The queue created in the main function
// OUTPUT:
//  0 if enqueuing failed
//  1 if all items enqueued successfully
int create_queue(item_queue * & queue) {
    // this will skip all other enqueues if one fails
    if(queue->enqueue("Horrifying Grim Reaper") &&
            queue->enqueue("Old werewolf") &&
            queue->enqueue("Green mummy") &&
            queue->enqueue("Red bogeyman") &&
            queue->enqueue("Gruesome corpse") &&
            queue->enqueue("Horrifying witch") &&
            queue->enqueue("Scary mad scientist") &&
            queue->enqueue("Horrifying devil") &&
            queue->enqueue("Dirty ghost") &&
            queue->enqueue("Horrifying spiderweb")) {
        return 0;
    }

    return 1;
}



// Main function.
// INPUT:
//  argc: Argument count provided by the terminal
//  argv: Arguments provided by the terminal
// OUTPUT:
//  0 if program terminated succesfully
//  1 if the file failed to load
//  2 if the queue failed to enqueue data
int main(int argc, char ** argv) {
    int option = 0;
    bool quit = false;
    int return_value = 0;
    char * filename = new char[FILENAME_MAX];
    char * peek_result = nullptr;
    item_table * table = new item_table;
    item_queue * queue = new item_queue;

    if(argc < 2) {
        cout << "Enter a file name to read from: ";
        cin.getline(filename, FILENAME_MAX);
    }
    else {
        copy_char_array(filename, argv[1]);
    }

    // if loading the table fails, set return_value to "failed to load" error
    if(table->load_items(filename) < 0) {
        cout << "Invalid file." << endl;
        return_value = 1;
    }

    // first check if the table load failed -- if not, check if the enqueueing
    // failed and return the "failed to enqueue" error
    if(return_value == 0 && create_queue(queue) < 0) {
        cout << "Failed to enqueue items." << endl;
        return_value = 2;
    }

    // if the return_value is not 0, skip the while loop
    if(return_value != 0)
        quit = true;
    else
        queue->peek(peek_result);

    while(!quit) {
        cout << "Let's do a scavenger hunt! Pick an option:" << endl;
        cout << "1) What do I need to find?" << endl;
        cout << "2) Where can I find it?" << endl;
        cout << "3) I need a hint!" << endl;
        cout << "4) I found it!" << endl;
        cout << "5) Exit" << endl;

        option = validate_input(0, MAX_MENU_OPTION);

        switch(option) {
            case 0: // hidden diagnostics option
                table->display_diagnostics();
                break;
            case 1: // print current item
                cout << "You need to find a..." << endl;
                table->display(peek_result, item_data::name);
                break;
            case 2: // print item location
                cout << "You can find it at..." << endl;
                table->display(peek_result, item_data::location);
                break;
            case 3: // print item hint
                cout << "Your hint is..." << endl;
                table->display(peek_result, item_data::hint);
                break;
            case 4: // dequeue item
                queue->dequeue();

                if(!queue->peek(peek_result)) {
                    cout << "You're done! Go collect your prize!" << endl;
                    quit = true;
                    break;
                }

                cout << "Congratulations! Your next item is..." << endl;
                table->display(peek_result, item_data::name);
                break;
            case 5: // exit case
                cout << "We're sad to see you go! Hope you had fun!" << endl;
                quit = true;
                break;
            default:
                option = validate_input(0, MAX_MENU_OPTION);
                break;
        }

        cout << endl;
    }

    if(peek_result)
        delete [] peek_result;

    delete [] filename;
    delete table;
    delete queue;
    return return_value;
}
