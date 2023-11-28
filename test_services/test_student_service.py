from python-flask-server.openapi_server.services.student_service import *
import unittest
from unittest.mock import patch
from openapi_server.services.pagination_sorting import pagination_sorting
from openapi_server.models.student import Student, Student_schema, Students_schema
from openapi_server.services.student_service import StudentService

class TestStudentService(unittest.TestCase):
    
    def setUp(self):
        self.student = {"id": 100, "name": "John Doe", "age": 20, "gender": "Male"}
        self.update_student = {"id": 100, "name": "Jane Doe", "age": 22, "gender": "Female"}

    def test_get_student_list(self):
        with patch('openapi_server.services.student_service.Student.query') as mock_query:
            mock_query.all.return_value = []
            result = StudentService.get_student_list()
            self.assertEqual(result, [])
    
    def test_get_student(self):
        with patch('openapi_server.services.student_service.Student.query') as mock_query:
            mock_query.filter.return_value.one_or_none.return_value = None
            with self.assertRaises(Exception):
                StudentService.get_student(1)
                
        with patch('openapi_server.services.student_service.Student.query') as mock_query:
            mock_query.filter.return_value.one_or_none.return_value = self.student
            result = StudentService.get_student(100)
            self.assertEqual(result.get("id"), self.student.get("id"))
            self.assertEqual(result.get("name"), self.student.get("name"))
            self.assertEqual(result.get("age"), self.student.get("age"))
            self.assertEqual(result.get("gender"), self.student.get("gender"))

    def test_add_student(self):
        with patch('openapi_server.services.student_service.Student.query') as mock_query:
            mock_query.filter.return_value.one_or_none.return_value = self.student
            with self.assertRaises(Exception):
                StudentService.add_student(self.student)
                
        with patch('openapi_server.services.student_service.Student_schema.load') as mock_load:
            mock_load.return_value = self.student
            with patch('openapi_server.services.student_service.db.session.add') as mock_add:
                with patch('openapi_server.services.student_service.db.session.commit') as mock_commit:
                    result = StudentService.add_student(self.student)
                    self.assertEqual(result.get("id"), self.student.get("id"))
                    self.assertEqual(result.get("name"), self.student.get("name"))
                    self.assertEqual(result.get("age"), self.student.get("age"))
                    self.assertEqual(result.get("gender"), self.student.get("gender"))

    def test_update_student(self):
        with patch('openapi_server.services.student_service.Student.query') as mock_query:
            mock_query.filter.return_value.one_or_none.return_value = None
            with self.assertRaises(Exception):
                StudentService.update_student(self.update_student)
                
        with patch('openapi_server.services.student_service.Student.query') as mock_query:
            mock_query.filter.return_value.one_or_none.return_value = self.student
            with patch('openapi_server.services.student_service.Student_schema.load') as mock_load:
                mock_load.return_value = self.update_student
                with patch('openapi_server.services.student_service.db.session.merge') as mock_merge:
                    with patch('openapi_server.services.student_service.db.session.commit') as mock_commit:
                        result = StudentService.update_student(self.update_student)
                        self.assertEqual(result.get("id"), self.update_student.get("id"))
                        self.assertEqual(result.get("name"), self.update_student.get("name"))
                        self.assertEqual(result.get("age"), self.update_student.get("age"))
                        self.assertEqual(result.get("gender"), self.update_student.get("gender"))

    def test_delete_student(self):
        with patch('openapi_server.services.student_service.Student.query') as mock_query:
            mock_query.filter.return_value.one_or_none.return_value = None
            with self.assertRaises(Exception):
                StudentService.delete_student(1)
                
        with patch('openapi_server.services.student_service.Student.query') as mock_query:
            mock_query.filter.return_value.one_or_none.return_value = self.student
            with patch('openapi_server.services.student_service.db.session.delete') as mock_delete:
                with patch('openapi_server.services.student_service.db.session.commit') as mock_commit:
                    result = StudentService.delete_student(100)
                    self.assertEqual(result, "Student with ID: 100 successfully deleted")

if __name__ == '__main__':
    unittest.main()