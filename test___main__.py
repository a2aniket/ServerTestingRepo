from python-flask-server.openapi_server.__main__ import *
import unittest
from unittest.mock import patch
from openapi_server import config_test
from openapi_server.controllers import security_controller_, user_controller

class TestAPI(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.app = config_test.connex_app.test_client()

    def test_create_user(self):
        # Test create user endpoint with valid data
        data = {
            'username': 'testuser',
            'password': 'testpassword'
        }
        response = self.app.post('/users', json=data)
        self.assertEqual(response.status_code, 201)
        self.assertIn('id', response.json)
        self.assertIsInstance(response.json['id'], int)

        # Test create user endpoint with invalid data
        data = {
            'username': '',
            'password': ''
        }
        response = self.app.post('/users', json=data)
        self.assertEqual(response.status_code, 400)

    def test_login(self):
        # Test login endpoint with valid credentials
        data = {
            'username': 'testuser',
            'password': 'testpassword'
        }
        response = self.app.post('/login', json=data)
        self.assertEqual(response.status_code, 200)

        # Test login endpoint with invalid credentials
        data = {
            'username': 'testuser',
            'password': 'wrongpassword'
        }
        response = self.app.post('/login', json=data)
        self.assertEqual(response.status_code, 401)

if __name__ == '__main__':
    unittest.main()