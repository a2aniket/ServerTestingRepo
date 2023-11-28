from python-flask-server.openapi_server.controllers.course_controller import *
import unittest
from unittest.mock import MagicMock
from openapi_server import app
from openapi_server.services.course_service import CourseService

class TestCourseAPI(unittest.TestCase):

    def setUp(self):
        self.client = app.test_client()

    def test_add_course(self):
        # Test add_course with valid input
        course = {'id': 1, 'name': 'Maths'}
        response = self.client.post('/api/v3/course', json=course)
        self.assertEqual(response.status_code, 200)

        # Test add_course with invalid input
        response = self.client.post('/api/v3/course')
        self.assertEqual(response.status_code, 400)

    def test_delete_course(self):
        # Test delete_course with valid input
        response = self.client.delete('/api/v3/course/1')
        self.assertEqual(response.status_code, 200)

        # Test delete_course with invalid input
        response = self.client.delete('/api/v3/course/abc')
        self.assertEqual(response.status_code, 400)

    def test_get_course(self):
        # Test get_course with valid input
        response = self.client.get('/api/v3/course/1')
        self.assertEqual(response.status_code, 200)

        # Test get_course with invalid input
        response = self.client.get('/api/v3/course/abc')
        self.assertEqual(response.status_code, 400)

    def test_get_course_list(self):
        # Test get_course_list with valid input
        response = self.client.get('/api/v3/course')
        self.assertEqual(response.status_code, 200)

    def test_update_course(self):
        # Test update_course with valid input
        course = {'id': 1, 'name': 'Maths'}
        response = self.client.put('/api/v3/course', json=course)
        self.assertEqual(response.status_code, 200)

        # Test update_course with invalid input
        response = self.client.put('/api/v3/course')
        self.assertEqual(response.status_code, 400)

if __name__ == '__main__':
    unittest.main()