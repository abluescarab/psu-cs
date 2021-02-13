#include <iostream>
#include <cstring>
#include "cpp_string.h"

using namespace std;

int main() {
    cpp_string test_string = "This Is A Test";
    cpp_string test2 = "This Is A Test";
    cpp_string test3 = "This is a test";
    cpp_string add_string = " added!";
    cpp_string upper = test_string.to_lower();
    cpp_string lower = test_string.to_upper();

    cout << test_string << endl;
    cout << upper << endl;
    cout << lower << endl;

    cout << endl;

    cout << "== char*: " << (test_string == "This Is A Test") << endl;
    cout << "== same:  " << (test_string == test2) << endl;
    cout << "== diff:  " << (test_string == test3) << endl;

    cout << endl;

    cout << "T:    " << test_string.contains('T') << endl;
    cout << "S:    " << test_string.contains('S') << endl;
    cout << "This: " << test_string.contains("This") << endl;
    cout << "THIS: " << test_string.contains("THIS") << endl;

    cout << test_string + 'a' << endl;
    cout << test_string + "abc" << endl;
    cout << test_string + add_string << endl;
    cout << test_string << endl;

    return 0;
}
