from apitesting.main import *
import unittest
from unittest.mock import MagicMock
from openapi_server.services.course_service import CourseService

class TestCourseService(unittest.TestCase):

    def setUp(self):
        self.course = {'id': 1, 'name': 'Test Course', 'description': 'This is a test course', 'instructor': 'Test Instructor'}

    def test_get_course_list(self):
        # Setup
        CourseService.get_course_list = MagicMock(return_value=[self.course])

        # Execute
        response = CourseService.get_course_list()

        # Assert
        self.assertEqual(response, [self.course])

    def test_get_course(self):
        # Setup
        CourseService.get_course = MagicMock(return_value=self.course)

        # Execute
        response = CourseService.get_course(id=1)

        # Assert
        self.assertEqual(response, self.course)

    def test_add_course(self):
        # Setup
        CourseService.add_course = MagicMock(return_value=self.course)

        # Execute
        response = CourseService.add_course(self.course)

        # Assert
        self.assertEqual(response, self.course)

    def test_update_course(self):
        # Setup
        CourseService.update_course = MagicMock(return_value=self.course)

        # Execute
        response = CourseService.update_course(self.course)

        # Assert
        self.assertEqual(response, self.course)

    def test_delete_course(self):
        # Setup
        CourseService.delete_course = MagicMock(return_value='Course with ID: 1 successfully deleted')

        # Execute
        response = CourseService.delete_course(id=1)

        # Assert
        self.assertEqual(response, 'Course with ID: 1 successfully deleted')

if __name__ == '__main__':
    unittest.main()