/**
 * Created by Rahul Thapar on 18-05-2016.
 */
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.net.UnknownHostException;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import javax.swing.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class App extends Frame implements ActionListener {


    String NAME , EMAIL , COMMENTS;
    String SEMESTER , AGE , COURSE;

    public static void main(String g[]) {
        App stu = new App();
        stu.setSize(new Dimension(500, 500));
        stu.setTitle("Student Blog");
        stu.setVisible(true);

    }

    Button b1 = new Button("SUBMIT");

    Label l1 = new Label("Name:", Label.LEFT);
    Label l2 = new Label("age:", Label.LEFT);
    Label l3 = new Label("Email", Label.LEFT);
    Label l4 = new Label("Comments:", Label.LEFT);
    Label l5 = new Label("Course:", Label.LEFT);
    Label l6 = new Label("Semester:", Label.LEFT);
    Label l7 = new Label("", Label.RIGHT);
    TextField name = new TextField();
    TextField email = new TextField();
    TextArea comments = new TextArea("", 180, 90, TextArea.SCROLLBARS_VERTICAL_ONLY);
    Choice course = new Choice();
    Choice sem = new Choice();
    Choice age = new Choice();

    public App() {
        addWindowListener(new myWindowAdapter());
        setBackground(Color.cyan);
        setForeground(Color.black);
        setLayout(null);
        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);
        add(l6);
        add(l7);
        add(name);
        add(comments);
        add(email);
        add(course);
        add(sem);
        add(age);
        add(b1);
        b1.addActionListener(this);
        add(b1);
        course.add("Computer Science");
        course.add("Mechanical");
        course.add("Chemical");
        course.add("Civil");
        course.add("Electronics and Communication");
        course.add("Electrical");
        sem.add("1");
        sem.add("2");
        sem.add("3");
        sem.add("4");
        sem.add("5");
        sem.add("6");
        sem.add("7");
        sem.add("8");
        age.add("18");
        age.add("19");
        age.add("20");
        age.add("21");
        age.add("22");
        l1.setBounds(25, 65, 90, 20);
        l2.setBounds(25, 90, 90, 20);
        l3.setBounds(25, 120, 90, 20);
        l4.setBounds(25, 185, 90, 20);
        l5.setBounds(25, 260, 90, 20);
        l6.setBounds(25, 290, 90, 20);
        l7.setBounds(25, 260, 90, 20);
        name.setBounds(120, 65, 170, 20);
        comments.setBounds(120, 185, 170, 60);
        email.setBounds(120, 118, 170, 20);
        course.setBounds(120, 260, 100, 20);
        sem.setBounds(120, 290, 50, 20);
        age.setBounds(120, 90, 50, 20);
        b1.setBounds(120, 350, 50, 30);
    }

    public void paint(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //System.out.println("button is clicked");
        getData();
//        saveData();

        //new ViewAllStudents().setVisible(true);

        ViewAllStudents v_stu = new ViewAllStudents();
        v_stu.setSize(new Dimension(500, 500));
        v_stu.setTitle("Student Blog");
        v_stu.setVisible(true);

    }

    private void getData() {

        NAME = name.getText().toString();
        EMAIL = email.getText().toString();
        COMMENTS = comments.getText().toString();


        SEMESTER = sem.getSelectedItem().toString();
        AGE = age.getSelectedItem().toString();
        COURSE = course.getSelectedItem().toString();

        saveData(NAME, EMAIL, COMMENTS, SEMESTER, AGE, COURSE);
    }

    private void saveData(String name,String email,String comments,String semester, String age, String course) {


        try {

            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("testdb");
            DBCollection table = db.getCollection("user");

            /**** Insert ****/

            BasicDBObject document = new BasicDBObject();
            document.put("name", name);
            document.put("email", email);
            document.put("age", age);
            document.put("course", course);
            document.put("semester", semester);
            document.put("comments", comments);
            document.put("createdDate", new Date());
            table.insert(document);

            /**** Find and display ****/
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", name);

            DBCursor cursor = table.find(searchQuery);

            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }



            /**** Done ****/
            System.out.println("Done");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }

    }
}

class ViewAllStudents extends Frame implements ActionListener{

    String S_NAME;
    String d_name,d_email,d_course,d_semester,d_age,d_comments,d_date;

    Button v_b1 = new Button("SEARCH");

    Label l1 = new Label("Name:", Label.LEFT);
    TextField s_name = new TextField();
    Label display_name = new Label("",Label.CENTER);
    Label display_email = new Label("",Label.CENTER);
    Label display_age = new Label("",Label.CENTER);
    Label display_comments = new Label("",Label.CENTER);
    Label display_course = new Label("",Label.CENTER);
    Label display_semester = new Label("",Label.CENTER);
    Label display_date = new Label("",Label.CENTER);

    public ViewAllStudents() {
        addWindowListener(new myWindowAdapter());
        setBackground(Color.cyan);
        setForeground(Color.black);
        setLayout(null);
        add(l1);
        add(s_name);
        add(display_name);
        add(display_email);
        add(display_age);
        add(display_comments);
        add(display_course);
        add(display_semester);
        add(display_date);
        add(v_b1);
        v_b1.addActionListener(this);
        add(v_b1);

        l1.setBounds(25, 65, 90, 20);
        s_name.setBounds(120, 65, 170, 20);
        v_b1.setBounds(120, 100, 90, 30);
        display_name.setBounds(105,150,170,150);
        display_email.setBounds(105,170,170,150);
        display_age.setBounds(105,150,190,150);
        display_comments.setBounds(105,210,170,150);
        display_course.setBounds(105,150,230,150);
        display_semester.setBounds(105,150,250,150);
        display_date.setBounds(105,150,270,150);
    }
    public void paint(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        S_NAME = s_name.getText().toString();
        searchData(S_NAME);

       // System.out.println("STRING :"+S_NAME);
    }

    private String searchData(String s_name) {

        try {

            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("testdb");
            DBCollection table = db.getCollection("user");

            //SEARCH QUERY
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", s_name);
            DBCursor cursor = table.find(searchQuery);

            while (cursor.hasNext()) {
                d_name = (String) cursor.next().get("name");
                d_email = (String) cursor.next().get("email");
              System.out.println(d_name);
                System.out.println(d_email);

            }



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }


}

class myWindowAdapter extends WindowAdapter
{public void windowClosing(WindowEvent we)
{
    System.exit(0);
}
}