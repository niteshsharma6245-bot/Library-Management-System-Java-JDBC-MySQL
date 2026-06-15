package com.nitesh.dao;

import com.nitesh.model.Student;
import com.nitesh.util.DBConnection;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO
{
    public void addStudent(Student student) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "INSERT INTO students(name, email, phone) VALUES (?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, student.getName());
        ps.setString(2, student.getEmail());
        ps.setString(3, student.getPhone());

        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0)
        {
            System.out.println("Student Added Successfully");
        }
        else
        {
            System.out.println("Student Not Added");
        }

        ps.close();
        con.close();
    }

    public void viewAllStudents() throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM students";

        PreparedStatement ps = con.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            System.out.println("Student ID: " + rs.getInt("student_id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Email: " + rs.getString("email"));
            System.out.println("Phone: " + rs.getString("phone"));
            System.out.println("------------------------");
        }

        rs.close();
        ps.close();
        con.close();
    }

    public Student searchStudentById(int studentId) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM students WHERE student_id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, studentId);

        ResultSet rs = ps.executeQuery();

        Student student = null;

        if(rs.next())
        {
            student = new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone")
            );
        }

        rs.close();
        ps.close();
        con.close();

        return student;
    }

    public Student searchStudentByEmail(String email) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query = "SELECT * FROM students WHERE email = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        Student student = null;

        if(rs.next())
        {
            student = new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone")
            );
        }

        rs.close();
        ps.close();
        con.close();

        return student;
    }

    public void updateStudent(int studentId,
                              String name,
                              String email,
                              String phone) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query =
                "UPDATE students SET name = ?, email = ?, phone = ? WHERE student_id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, phone);
        ps.setInt(4, studentId);

        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0)
        {
            System.out.println("Student Updated Successfully");
        }
        else
        {
            System.out.println("Student Not Found");
        }

        ps.close();
        con.close();
    }

    public void deleteStudent(int studentId) throws SQLException
    {
        Connection con = DBConnection.getConnection();

        String query = "DELETE FROM students WHERE student_id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, studentId);

        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0)
        {
            System.out.println("Student Deleted Successfully");
        }
        else
        {
            System.out.println("Student Not Found");
        }

        ps.close();
        con.close();
    }


}
