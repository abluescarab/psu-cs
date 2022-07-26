Class:   CS410P - Advanced Programming in Java
Project: Phone Bill Web
Name:    Alana Gilston

This project implements a phone bill web application that stores a number of phone calls with caller and callee numbers as well as start and end times.

usage: java -jar target/phonebill-client.jar [options] <args>
  args are (in this order):
    customer               Person whose phone bill we’re modeling
    callerNumber           Phone number of caller
    calleeNumber           Phone number of person who was called
    begin                  Date and time call began
    end                    Date and time call ended
  options are (options may appear in any order):
    -host hostname         Host computer on which the server runs
    -port port             Port on which the server is listening
    -search                Phone calls should be searched for
    -print                 Prints a description of the new phone call
    -README                Prints a README for this project and exits
