from python-flask-server.openapi_server.models.user import *
import unittest
from werkzeug.security import generate_password_hash, check_password_hash
from openapi_server.models import User, UserSchema
from openapi_server.config_test import db, ma

class UserTestCase(unittest.TestCase):

    def setUp(self):
        db.create_all()
        self.user = User(username='test_user', password='test_password')
        db.session.add(self.user)
        db.session.commit()

    def tearDown(self):
        db.session.remove()
        db.drop_all()

    def test_user_creation(self):
        new_user = User(username='new_user', password='new_password')
        db.session.add(new_user)
        db.session.commit()
        self.assertIsInstance(new_user, User)
        self.assertEqual(new_user.username, 'new_user')
        self.assertTrue(check_password_hash(new_user.password, 'new_password'))

    def test_set_password(self):
        password_hash = generate_password_hash('test_password')
        self.assertEqual(self.user.password, password_hash)

    def test_check_password(self):
        self.assertTrue(self.user.check_password('test_password'))
        self.assertFalse(self.user.check_password('wrong_password'))

    def test_to_dict(self):
        user_dict = self.user.to_dict()
        self.assertIsInstance(user_dict, dict)
        self.assertEqual(user_dict['id'], self.user.id)
        self.assertEqual(user_dict['username'], self.user.username)

    def test_user_schema(self):
        user_schema = UserSchema()
        user_data = user_schema.dump(self.user)
        self.assertIsInstance(user_data, dict)
        self.assertEqual(user_data['id'], self.user.id)
        self.assertEqual(user_data['username'], self.user.username)