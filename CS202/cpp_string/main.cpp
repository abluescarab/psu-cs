#include <iostream>
#include <cstring>
#include <climits>
#include "cpp_string.h"

using namespace std;

int main() {
    int option = 0;
    bool quit = false;
    cpp_string test_string = "This Is A Test";
    cpp_string test2 = "This Is A Test";
    cpp_string test3 = "This is a test";
    cpp_string add_string = " added!";
    cpp_string replace_string = "This this this is a test";
    cpp_string upper;
    cpp_string lower;

    while(!quit) {
        cout << "1)  upper/lower" << endl;
        cout << "2)  ==" << endl;
        cout << "3)  contains" << endl;
        cout << "4)  +" << endl;
        cout << "5)  insert" << endl;
        cout << "6)  replace" << endl;
        cout << "7)  index of" << endl;
        cout << "8)  substring" << endl;
        cout << "9)  +=" << endl;
        cout << "10) remove" << endl;

        cin >> option;
        cout << endl;

        switch(option) {
            case 1:
                upper = test_string.to_lower();
                lower = test_string.to_upper();

                cout << test_string << endl;
                cout << upper << endl;
                cout << lower << endl;
                break;
            case 2:
                cout << "char* ==: " << ("This Is A Test" == test_string) << endl;
                cout << "same ==: " << (test2 == test_string) << endl;
                cout << "diff ==: " << (test3 == test_string) << endl;
                cout << "== char*: " << (test_string == "This Is A Test") << endl;
                cout << "== same:  " << (test_string == test2) << endl;
                cout << "== diff:  " << (test_string == test3) << endl;
                break;
            case 3:
                cout << "T:    " << test_string.contains('T') << endl;
                cout << "S:    " << test_string.contains('S') << endl;
                cout << "This: " << test_string.contains("This") << endl;
                cout << "THIS: " << test_string.contains("THIS") << endl;
                cout << "str:  " << test_string.contains(cpp_string("This")) << endl;
                break;
            case 4:
                cout << test_string + '1' << endl;
                cout << test_string + "123" << endl;
                cout << test_string + add_string << endl;
                break;
            case 5:
                cout << test_string.insert(0, '1') << endl;
                cout << test_string.insert(1, '2') << endl;
                cout << test_string.insert(14, '3') << endl;

                cout << endl;

                cout << test_string.insert(0, "123") << endl;
                cout << test_string.insert(1, "123") << endl;
                cout << test_string.insert(14, "123") << endl;

                cout << endl;

                cout << test_string.insert(0, add_string) << endl;
                cout << test_string.insert(1, add_string) << endl;
                cout << test_string.insert(14, add_string) << endl;
                break;
            case 6:
                cout << test_string.replace('T', '0') << endl;
                cout << test_string.replace("t", "0") << endl;
                cout << test_string.replace("t", cpp_string("0")) << endl;
                break;
            case 7:
                cout << test_string.index_of('z') << endl;
                cout << test_string.index_of('T') << endl;
                cout << test_string.index_of("T") << endl;
                cout << test_string.index_of("z") << endl;
                cout << test_string.index_of(cpp_string("T")) << endl;
                cout << test_string.index_of(cpp_string("z")) << endl;

                cout << endl;

                cout << test_string.index_of('T') << endl;
                cout << test_string.index_of('T', test_string.index_of('T') + 1) << endl;
                break;
            case 8:
                cout << test_string.substring(1) << endl;
                cout << test_string.substring(1, 3) << endl;
                break;
            case 9:
                cout << (test_string += '0') << endl;
                cout << (test_string += "1") << endl;
                cout << (test_string += cpp_string("2")) << endl;
                break;
            case 10:
                cout << test_string.remove(4) << endl;
                cout << test_string.remove(4, 6) << endl;
                break;
            default:
                quit = true;
                break;
        }

        cout << endl;
        cin.clear();
    }

    return 0;
}
