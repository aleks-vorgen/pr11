package org.example;

import org.example.models.Group;
import org.example.xml.XMLUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2 || args[0].isEmpty() || args[1].isEmpty()){
            System.out.println("Incorrect arguments!");
            return;
        }

        Path pathSource = Paths.get(args[0]);
        Path pathResult = Paths.get(args[1]);

        if (!Files.exists(pathSource, LinkOption.NOFOLLOW_LINKS)
                && !Files.isRegularFile(pathSource, LinkOption.NOFOLLOW_LINKS)){
            System.out.println("Source file does not exist");
            return;
        }

        Group group;
        try {
            group = XMLUtils.readGroupFromXMLFile(pathSource);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            return;
        }

        group.calcAveragesMarks();

        try {
            XMLUtils.writeGroupToXMLFile(group,pathResult);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("File " + pathResult + " was updated");
    }
}
