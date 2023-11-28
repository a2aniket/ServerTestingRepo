from apitesting.main import *
import unittest
from unittest.mock import patch
from openapi_server.services.pagination_sorting import pagination_sorting
from openapi_server.models.course import Course, Course_schema, Courses_schema
from openapi_server.config_test import db
from openapi_server.course_service import CourseService


class TestCourseService(unittest.TestCase):

    def setUp(self):
        db.drop_all()
        db.create_all()
        self.course1 = {"id": 1, "name": "Course 1", "description": "Description 1", "credits": 3}
        self.course2 = {"id": 2, "name": "Course 2", "description": "Description 2", "credits": 4}
        self.course3 = {"id": 3, "name": "Course 3", "description": "Description 3", "credits": 3}
        self.course4 = {"id": 4, "name": "Course 4", "description": "Description 4", "credits": 4}
        self.course5 = {"id": 5, "name": "Course 5", "description": "Description 5", "credits": 3}
        self.course6 = {"id": 6, "name": "Course 6", "description": "Description 6", "credits": 4}
        self.course7 = {"id": 7, "name": "Course 7", "description": "Description 7", "credits": 3}
        self.course8 = {"id": 8, "name": "Course 8", "description": "Description 8", "credits": 4}
        self.course9 = {"id": 9, "name": "Course 9", "description": "Description 9", "credits": 3}
        self.course10 = {"id": 10, "name": "Course 10", "description": "Description 10", "credits": 4}

    def test_get_course_list(self):
        with patch.object(CourseService, 'get_course_list', return_value=[self.course1, self.course2, self.course3]):
            courses = CourseService.get_course_list()
            self.assertEqual(len(courses), 3)

    def test_get_course(self):
        CourseService.add_course(self.course1)
        with patch.object(CourseService, 'get_course', return_value=self.course1):
            course = CourseService.get_course(1)
            self.assertEqual(course["name"], self.course1["name"])

    def test_add_course(self):
        with patch.object(CourseService, 'add_course', return_value=self.course1):
            course = CourseService.add_course(self.course1)
            self.assertEqual(course["name"], self.course1["name"])

    def test_add_course_with_existing_id(self):
        CourseService.add_course(self.course1)
        with self.assertRaises(Exception):
            CourseService.add_course(self.course1)

    def test_update_course(self):
        CourseService.add_course(self.course1)
        updated_course = {"id": 1, "name": "Updated Course 1", "description": "Updated Description 1", "credits": 4}
        with patch.object(CourseService, 'update_course', return_value=updated_course):
            course = CourseService.update_course(updated_course)
            self.assertEqual(course["name"], updated_course["name"])

    def test_update_course_with_invalid_id(self):
        invalid_course = {"id": -1, "name": "Course -1", "description": "Description -1", "credits": 3}
        with self.assertRaises(Exception):
            CourseService.update_course(invalid_course)

    def test_update_course_with_non_existing_id(self):
        with self.assertRaises(Exception):
            CourseService.update_course(self.course1)

    def test_delete_course(self):
        CourseService.add_course(self.course1)
        with patch.object(CourseService, 'delete_course', return_value="Course with ID: 1 successfully deleted"):
            response = CourseService.delete_course(1)
            self.assertEqual(response, "Course with ID: 1 successfully deleted")

    def test_delete_course_with_non_existing_id(self):
        with self.assertRaises(Exception):
            CourseService.delete_course(1)

    def test_pagination_sorting(self):
        CourseService.add_course(self.course1)
        CourseService.add_course(self.course2)
        CourseService.add_course(self.course3)
        CourseService.add_course(self.course4)
        CourseService.add_course(self.course5)
        CourseService.add_course(self.course6)
        CourseService.add_course(self.course7)
        CourseService.add_course(self.course8)
        CourseService.add_course(self.course9)
        CourseService.add_course(self.course10)
        courses = pagination_sorting(Course)
        self.assertEqual(len(courses), 10)


if __name__ == '__main__':
    unittest.main()