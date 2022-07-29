package org.example.xml;

import org.example.models.Group;
import org.example.models.Student;
import org.example.models.Subject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class XMLUtils {
    public static Group readGroupFromXMLFile(Path xmlData)
            throws IOException, ParserConfigurationException, SAXException {

        Document doc = XMLProcessor.readXML(xmlData,true);
        return getGroupByDoc(doc);
    }

    public static void writeGroupToXMLFile(Group group, Path xmlData)
            throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root element
        Document doc = docBuilder.newDocument();

        fillDocByGroup(doc,group);

        // write dom document to a file
        try (FileOutputStream output = new FileOutputStream(xmlData.toFile())) {
            XMLProcessor.writeXML(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fillDocByGroup(Document doc, Group group) {

        Element rootElement = doc.createElement("group");
        doc.appendChild(rootElement);

        for (Student currStudent : group.getStudentList()) {
            // add xml elements
            Element student = doc.createElement("student");
            // add student to root
            rootElement.appendChild(student);
            // add xml attribute
            student.setAttribute("firstname", currStudent.getFirstName());
            student.setAttribute("lastname", currStudent.getLastName());
            student.setAttribute("groupnumber", currStudent.getGroup());

            for (Subject currSubject : currStudent.getSubjects()) {
                Element subject = doc.createElement("subject");
                subject.setAttribute("title", currSubject.getTitle());
                subject.setAttribute("mark", Integer.toString(currSubject.getMark()));
                student.appendChild(subject);
            }

            Element avg = doc.createElement("average");
            avg.setTextContent(Double.toString(currStudent.getAverage()));
            student.appendChild(avg);
        }

    }

    private static Group getGroupByDoc(Document doc) {
        Group group = new Group();
        List<Student> studentList = group.getStudentList();

        NodeList nList = doc.getElementsByTagName("student");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element studElement = (Element) nNode;
                String name = studElement.getAttribute("firstname");
                String lastName = studElement.getAttribute("lastname");
                String groupNumber = studElement.getAttribute("groupnumber");

                Student currStudent = new Student(name, lastName, groupNumber);
                List<Subject> subjects = currStudent.getSubjects();

                NodeList subElements = studElement.getElementsByTagName("subject");
                for (int j = 0; j < subElements.getLength(); j++) {
                    Subject currSubject = new Subject();
                    Node sNode = subElements.item(j);
                    if (sNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element subElement = (Element) sNode;
                        String title = subElement.getAttribute("title");
                        String mark = subElement.getAttribute("mark");
                        int currMark = Integer.parseInt(mark);
                        currSubject.setMark(currMark);
                        currSubject.setTitle(title);
                        subjects.add(currSubject);
                    }
                    currStudent.setSubjects(subjects);
                }
                NodeList avgElements = studElement.getElementsByTagName("average");
                if (subElements.getLength() > 0) {
                    String avgString = avgElements.item(0).getTextContent();
                    double average = Double.parseDouble(avgString);
                    currStudent.setAverage(average);
                }
                studentList.add(currStudent);
            }
        }
        group.setStudentList(studentList);

        return group;
    }
}
