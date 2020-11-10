/* Alana Gilston - 11/9/2020 - CS163 - Program 3
 * main.cpp
 *
 * This is the main function to test the item_table and item_queue classes.
 */
#include <iostream>
#include "item_table.h"
#include "item_queue.h"
#include "utils.h"

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



int main(int argc, char ** argv) {
    int count = 0;
    int queue_count = 0;
    int option = 0;
    char * filename = nullptr;
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

    count = table->load_items(filename);

    if(count < 0) {
        cout << "Invalid file." << endl;
        return 1;
    }

    table->display_all();
    queue_count = create_queue(queue);

    if(queue_count < 0) {
        cout << "Failed to enqueue items." << endl;
        return 1;
    }
    
    do {
        cout << "Let's do a scavenger hunt! Pick an option:" << endl;
        cout << "1) What do I need to find?" << endl;
        cout << "2) I found it!" << endl;
        cout << "3) Exit" << endl;

        option = validate_input(3);

        switch(option) {
            case 1: // print current item
                cout << "You need to find..." << endl << endl;
                queue->peek(peek_result);
                table->display(peek_result);
                break;
            case 2:
                queue->dequeue();

                if(!queue->peek(peek_result)) {
                    cout << "You're done! Go collect your prize!" << endl;
                    break;
                }

                cout << "Congratulations! Your next item is..." << endl << endl;
                table->display(peek_result);
                break;
            case 3:
                cout << "We're sad to see you go! Hope you had fun!" << endl;
                break;
            default:
                option = validate_input(3);
                break;
        }

        cout << endl;
    } while(option != 3 && peek_result != nullptr);

    delete filename;
    delete table;
    delete queue;
    return 0;
}
