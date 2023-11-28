from python-flask-server.openapi_server.models.course import *
import unittest
from datetime import date, datetime
from openapi_server.config_test import db, ma
from openapi_server.models.course import Course, CourseSchema

class TestCourseModel(unittest.TestCase):

    def setUp(self):
        db.create_all()

    def tearDown(self):
        db.session.remove()
        db.drop_all()

    def test_course_model(self):
        # Test creating a new course
        c = Course(name='Test Course', desc='This is a test course')
        db.session.add(c)
        db.session.commit()

        # Test retrieving the course from the database
        course = Course.query.filter_by(name='Test Course').first()
        self.assertIsNotNone(course, 'Course was not created')

        # Test serialization
        serialized_course = Course_schema.dump(course)
        self.assertIsInstance(serialized_course, dict, 'Serialized course is not a dictionary')
        self.assertEqual(serialized_course['name'], 'Test Course', 'Serialized course has incorrect name')
        self.assertEqual(serialized_course['desc'], 'This is a test course', 'Serialized course has incorrect description')

        # Test deserialization
        deserialized_course = Course_schema.load(serialized_course)
        self.assertIsInstance(deserialized_course, Course, 'Deserialized course is not a Course instance')
        self.assertEqual(deserialized_course.name, 'Test Course', 'Deserialized course has incorrect name')
        self.assertEqual(deserialized_course.desc, 'This is a test course', 'Deserialized course has incorrect description')

if __name__ == '__main__':
    unittest.main()