Class:   CS410P - Advanced Programming in Java
Project: Phone Bill
Name:    Alana Gilston

This project implements a phone bill that stores a number of phone calls with caller and callee numbers as well as start and end times.

usage: java edu.pdx.cs410J.agilston.Project3 [options] <args>
  args are (in this order):
    customer               Person whose phone bill we’re modeling
    callerNumber           Phone number of caller
    calleeNumber           Phone number of person who was called
    begin                  Date and time (am/pm) call began
    end                    Date and time (am/pm) call ended
  options are (options may appear in any order):
    -pretty file           Pretty print the phone bill to a text file
                           or standard out (file -).
    -textFile file         Where to read/write the phone bill
    -print                 Prints a description of the new phone call
    -README                Prints a README for this project and exits
