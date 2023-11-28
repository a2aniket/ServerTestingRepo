from python-flask-server.openapi_server.controllers.security_controller_ import *
import unittest
from unittest.mock import patch, MagicMock
from openapi_server import app
from openapi_server.services.user_service import UserService

class TestAuthentication(unittest.TestCase):

    def setUp(self):
        self.client = app.test_client()

    def test_authenticate_with_valid_credentials(self):
        user = MagicMock()
        user.check_password.return_value = True
        UserService.get_user_by_username.return_value = user
        with app.app_context():
            response = self.client.post('/login', json={"username": "testuser", "password": "testpass"})
            data = response.get_json()
            self.assertEqual(response.status_code, 200)
            self.assertIn("token", data)

    def test_authenticate_with_invalid_credentials(self):
        UserService.get_user_by_username.return_value = None
        with app.app_context():
            response = self.client.post('/login', json={"username": "testuser", "password": "testpass"})
            data = response.get_json()
            self.assertEqual(response.status_code, 401)
            self.assertIn("message", data)
            self.assertEqual(data["message"], "Invalid username or password")

    def test_create_token(self):
        with app.app_context():
            token = create_token(1)
            self.assertIsNotNone(token)

    def test_authorize_with_valid_token(self):
        user = MagicMock()
        UserService.get_user_by_username.return_value = user
        with app.app_context():
            token = create_token(1)
            response = self.client.get('/protected', headers={"Authorization": f"Bearer {token}"})
            data = response.get_json()
            self.assertEqual(response.status_code, 200)

    def test_authorize_with_expired_token(self):
        with app.app_context():
            token = jwt.encode({"sub": 1, "iat": datetime.utcnow(), "exp": datetime.utcnow() - timedelta(hours=1)}, app.config.get("JWT_SECRET_KEY").encode('utf-8'), algorithm="HS256")
            response = self.client.get('/protected', headers={"Authorization": f"Bearer {token}"})
            data = response.get_json()
            self.assertEqual(response.status_code, 401)
            self.assertIn("message", data)
            self.assertEqual(data["message"], "Token has expired")

    def test_authorize_with_invalid_token(self):
        with app.app_context():
            response = self.client.get('/protected', headers={"Authorization": "Bearer invalid_token"})
            data = response.get_json()
            self.assertEqual(response.status_code, 401)
            self.assertIn("message", data)
            self.assertEqual(data["message"], "Token is invalid")