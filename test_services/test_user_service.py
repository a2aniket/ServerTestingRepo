from python-flask-server.openapi_server.services.user_service import *
import unittest
from openapi_server.models.user import User
from openapi_server.config_test import db
from openapi_server.service import UserService

class TestUserService(unittest.TestCase):

    def test_create_user(self):
        user = UserService.create_user('testuser', 'testpassword')
        self.assertIsInstance(user, User)
        self.assertEqual(user.username, 'testuser')
        self.assertEqual(user.password, 'testpassword')

    def test_get_user_by_username(self):
        UserService.create_user('testuser', 'testpassword')
        user = UserService.get_user_by_username('testuser')
        self.assertIsInstance(user, User)
        self.assertEqual(user.username, 'testuser')
        self.assertEqual(user.password, 'testpassword')

    def test_get_user_by_invalid_username(self):
        user = UserService.get_user_by_username('invalidusername')
        self.assertIsNone(user)

if __name__ == '__main__':
    unittest.main()