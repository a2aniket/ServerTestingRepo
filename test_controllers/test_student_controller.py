from python-flask-server.openapi_server.controllers.student_controller import *
import unittest
from openapi_server.services.student_service import StudentService

class TestStudentService(unittest.TestCase):

    def test_add_student(self):
        # Test adding a new student
        student = {"id": 1, "name": "John Doe", "age": 20, "email": "john.doe@example.com"}
        self.assertEqual(StudentService.add_student(student), "Student added successfully")

        # Test adding a student with missing fields
        student = {"id": 2, "name": "Jane Doe"}
        self.assertEqual(StudentService.add_student(student), "Student details incomplete")

        # Test adding a student with duplicate ID
        student = {"id": 1, "name": "Bob Smith", "age": 25, "email": "bob.smith@example.com"}
        self.assertEqual(StudentService.add_student(student), "Student with ID 1 already exists")

    def test_delete_student(self):
        # Test deleting an existing student
        self.assertEqual(StudentService.delete_student(1), "Student deleted successfully")

        # Test deleting a non-existent student
        self.assertEqual(StudentService.delete_student(99), "Student not found")

    def test_get_student(self):
        # Test getting an existing student
        expected_student = {"id": 2, "name": "Jane Doe", "age": 21, "email": "jane.doe@example.com"}
        self.assertEqual(StudentService.get_student(2), expected_student)

        # Test getting a non-existent student
        self.assertEqual(StudentService.get_student(99), "Student not found")

    def test_get_student_list(self):
        # Test getting a list of students
        expected_student_list = [
            {"id": 2, "name": "Jane Doe", "age": 21, "email": "jane.doe@example.com"},
            {"id": 3, "name": "Bob Smith", "age": 25, "email": "bob.smith@example.com"}
        ]
        self.assertEqual(StudentService.get_student_list(), expected_student_list)

    def test_update_student(self):
        # Test updating an existing student
        student = {"id": 2, "name": "Jane Smith", "age": 22, "email": "jane.smith@example.com"}
        self.assertEqual(StudentService.update_student(student), "Student updated successfully")

        # Test updating a non-existent student
        student = {"id": 99, "name": "John Doe", "age": 20, "email": "john.doe@example.com"}
        self.assertEqual(StudentService.update_student(student), "Student not found")