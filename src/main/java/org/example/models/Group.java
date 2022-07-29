package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Student> studentList = new ArrayList<>();

    public List<Student> getStudentList() { return studentList; }

    public void setStudentList(List<Student> studentList) { this.studentList = studentList; }

    public void calcAveragesMarks() {
        for (Student student : studentList) {
            int i = 0;
            double sum = 0;
            for (Subject subject : student.getSubjects()) {
                int currMark = subject.getMark();
                sum += currMark;
                i++;
            }
            double avg = (i == 0) ? 0 : sum / i;
            if (student.getAverage() != avg) {
                student.setAverage(avg);
            }
        }
    }
}
