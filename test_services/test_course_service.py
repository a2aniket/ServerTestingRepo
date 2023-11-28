from python-flask-server.openapi_server.services.course_service import *
import unittest
from unittest.mock import patch, MagicMock
from openapi_server.services.pagination_sorting import pagination_sorting
from openapi_server.models.course import Course, Course_schema, Courses_schema
from openapi_server.config_test import db
from openapi_server.services.course_service import CourseService
from flask import abort

class TestCourseService(unittest.TestCase):

    def setUp(self):
        db.create_all()

    def tearDown(self):
        db.session.remove()
        db.drop_all()

    def test_get_course_list(self):
        with patch.object(CourseService, 'get_course_list', return_value=Courses_schema.dump([])):
            result = CourseService.get_course_list()
            self.assertEqual(result, [])

    def test_get_course(self):
        course_mock = MagicMock()
        course_mock.id = 1
        course_mock.name = 'Course 1'
        db.session.add(course_mock)
        db.session.commit()
        with patch.object(CourseService, 'get_course', return_value=Course_schema.dump(course_mock)):
            result = CourseService.get_course(1)
            self.assertEqual(result['name'], 'Course 1')
            self.assertEqual(result['id'], 1)

    def test_add_course(self):
        course = {
            'id': 1,
            'name': 'Course 1',
            'description': 'Description of course 1'
        }
        with patch.object(CourseService, 'add_course', return_value=Course_schema.dump(course)):
            result = CourseService.add_course(course)
            self.assertEqual(result['id'], 1)
            self.assertEqual(result['name'], 'Course 1')
            self.assertEqual(result['description'], 'Description of course 1')

    def test_add_course_existing_id(self):
        course_mock = MagicMock()
        course_mock.id = 1
        db.session.add(course_mock)
        db.session.commit()
        course = {
            'id': 1,
            'name': 'Course 1',
            'description': 'Description of course 1'
        }
        with self.assertRaises(abort):
            CourseService.add_course(course)

    def test_update_course(self):
        course_mock = MagicMock()
        course_mock.id = 1
        course_mock.name = 'Course 1'
        db.session.add(course_mock)
        db.session.commit()
        course = {
            'id': 1,
            'name': 'Course 2',
            'description': 'Description of course 2'
        }
        with patch.object(CourseService, 'update_course', return_value=Course_schema.dump(course_mock)):
            result = CourseService.update_course(course)
            self.assertEqual(result['id'], 1)
            self.assertEqual(result['name'], 'Course 2')
            self.assertEqual(result['description'], 'Description of course 2')

    def test_update_course_invalid_id(self):
        course = {
            'id': -1,
            'name': 'Course 1',
            'description': 'Description of course 1'
        }
        with self.assertRaises(abort):
            CourseService.update_course(course)

    def test_delete_course(self):
        course_mock = MagicMock()
        course_mock.id = 1
        db.session.add(course_mock)
        db.session.commit()
        with patch.object(CourseService, 'delete_course', return_value='Course with ID: 1 successfully deleted'):
            result = CourseService.delete_course(1)
            self.assertEqual(result, 'Course with ID: 1 successfully deleted')

    def test_delete_course_not_found(self):
        with self.assertRaises(abort):
            CourseService.delete_course(1)