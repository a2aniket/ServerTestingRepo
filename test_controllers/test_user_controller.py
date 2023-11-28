from python-flask-server.openapi_server.controllers.user_controller import *
import unittest
from openapi_server import app


class TestUserEndpoints(unittest.TestCase):
    def test_create_user(self):
        """
        Test creating a new user with valid data
        """
        with app.test_client() as client:
            response = client.post('/users', json={'username': 'testuser', 'password': 'testpassword'})
            self.assertEqual(response.status_code, 201)
            self.assertEqual(response.json['username'], 'testuser')
            self.assertEqual(response.json['password'], 'testpassword')

    def test_get_user_by_username(self):
        """
        Test retrieving an existing user by username
        """
        with app.test_client() as client:
            client.post('/users', json={'username': 'testuser', 'password': 'testpassword'})
            response = client.get('/users/testuser')
            self.assertEqual(response.status_code, 200)
            self.assertEqual(response.json['username'], 'testuser')
            self.assertEqual(response.json['password'], 'testpassword')

    def test_get_user_by_nonexistent_username(self):
        """
        Test retrieving a non-existent user by username
        """
        with app.test_client() as client:
            response = client.get('/users/nonexistentuser')
            self.assertEqual(response.status_code, 404)
            self.assertEqual(response.json['message'], 'User not found')

    def test_create_user_with_missing_data(self):
        """
        Test creating a new user with missing data
        """
        with app.test_client() as client:
            response = client.post('/users', json={'username': 'testuser'})
            self.assertEqual(response.status_code, 400)
            self.assertEqual(response.json['message'], 'Bad Request')

    def test_create_user_with_existing_username(self):
        """
        Test creating a new user with an existing username
        """
        with app.test_client() as client:
            client.post('/users', json={'username': 'testuser', 'password': 'testpassword'})
            response = client.post('/users', json={'username': 'testuser', 'password': 'testpassword2'})
            self.assertEqual(response.status_code, 409)
            self.assertEqual(response.json['message'], 'Conflict')


if __name__ == '__main__':
    unittest.main()