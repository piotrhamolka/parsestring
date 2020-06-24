package com.testtask.stringparsing;

public class ParseApplication {

    public static void main(String[] args) {
        ParseStringToTree parser = new ParseStringToTree();
        parser.parse(" ( id, created,employee(id,firstname, employeeType(id), lastname) ,location) ");
    }
}
