package com.testtask.stringparsing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseStringToTreeTest {

    private static ParseStringToTree parser;

    @BeforeAll
    private static void init() {
        parser = new ParseStringToTree();
    }

    @Test
    void whenValidString_thenCleanString() {

        String dirtyString = " ( id, created,employee(id,firstname, employeeType(id), lastname) ,location) ";
        String cleanString = parser.cleanInputString(dirtyString);

        Assertions.assertNotNull(cleanString);

        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(cleanString);
        boolean found = matcher.find();
        Assertions.assertFalse(found);

        matcher = pattern.matcher(dirtyString);
        found = matcher.find();
        Assertions.assertTrue(found);
    }

    @Test
    void whenValidString_thenNodesList() {

        String cleanString = "id,created,employee(id,firstname,employeeType(id),lastname),location";
        ArrayList<ParseStringToTree.Node> list = parser.buildTree(cleanString);

        Assertions.assertNotNull(list);
        Assertions.assertEquals(4, list.size());

        Assertions.assertEquals("id", list.get(0).getName());
        Assertions.assertNull(list.get(0).getChildren());

        Assertions.assertEquals("created", list.get(1).getName());
        Assertions.assertNull(list.get(1).getChildren());

        Assertions.assertEquals("employee", list.get(2).getName());
        Assertions.assertEquals(4, list.get(2).getChildren().size());
        Assertions.assertEquals("id", list.get(2).getChildren().get(0).getName());
        Assertions.assertNull(list.get(2).getChildren().get(0).getChildren());
        Assertions.assertEquals("firstname", list.get(2).getChildren().get(1).getName());
        Assertions.assertNull(list.get(2).getChildren().get(0).getChildren());
        Assertions.assertEquals("employeeType", list.get(2).getChildren().get(2).getName());
        Assertions.assertEquals(1, list.get(2).getChildren().get(2).getChildren().size());
        Assertions.assertEquals("id", list.get(2).getChildren().get(2).getChildren().get(0).getName());
        Assertions.assertEquals("lastname", list.get(2).getChildren().get(3).getName());
        Assertions.assertNull(list.get(2).getChildren().get(3).getChildren());

        Assertions.assertEquals("location", list.get(3).getName());
        Assertions.assertNull(list.get(3).getChildren());
    }

    @Test
    public void whenValidNode_thenStringified() {

        String expected = "employee" + System.lineSeparator() +
                "-id" + System.lineSeparator() +
                "-firstname" + System.lineSeparator() +
                "-employeeType" + System.lineSeparator() +
                "--id" + System.lineSeparator() +
                "-lastname";

        ParseStringToTree.Node node = new ParseStringToTree.Node();
        List<ParseStringToTree.Node> nodesList = new ArrayList<>();

        ParseStringToTree.Node node_id = new ParseStringToTree.Node();
        node_id.setName("id");
        nodesList.add(node_id);

        ParseStringToTree.Node node_fn = new ParseStringToTree.Node();
        node_fn.setName("firstname");
        nodesList.add(node_fn);

        ParseStringToTree.Node node_emp = new ParseStringToTree.Node();
        node_emp.setName("employeeType");
        List<ParseStringToTree.Node> empList = new ArrayList<>();
        ParseStringToTree.Node node_emp_type = new ParseStringToTree.Node();
        node_emp_type.setName("id");
        empList.add(node_emp_type);
        node_emp.setChildren(empList);
        nodesList.add(node_emp);

        ParseStringToTree.Node node_ln = new ParseStringToTree.Node();
        node_ln.setName("lastname");
        nodesList.add(node_ln);

        node.setName("employee");
        node.setChildren(nodesList);

        Assertions.assertEquals(expected, parser.traverseNodes(node));

    }

}
