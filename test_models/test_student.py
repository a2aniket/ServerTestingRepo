from python-flask-server.openapi_server.models.student import *
import unittest
from openapi_server.config_test import db
from openapi_server.models.student import Student, StudentSchema

class TestStudent(unittest.TestCase):

    def setUp(self):
        db.create_all()

    def tearDown(self):
        db.session.remove()
        db.drop_all()

    def test_student_model_creation(self):
        student = Student(id=1, name='John Doe', address='123 Main St', email='johndoe@example.com', phone='123-456-7890')
        db.session.add(student)
        db.session.commit()
        self.assertEqual(student.id, 1)
        self.assertEqual(student.name, 'John Doe')
        self.assertEqual(student.address, '123 Main St')
        self.assertEqual(student.email, 'johndoe@example.com')
        self.assertEqual(student.phone, '123-456-7890')

    def test_student_schema_dump(self):
        student = Student(id=1, name='John Doe', address='123 Main St', email='johndoe@example.com', phone='123-456-7890')
        db.session.add(student)
        db.session.commit()
        result = StudentSchema().dump(student)
        self.assertEqual(result['id'], 1)
        self.assertEqual(result['name'], 'John Doe')
        self.assertEqual(result['address'], '123 Main St')
        self.assertEqual(result['email'], 'johndoe@example.com')
        self.assertEqual(result['phone'], '123-456-7890')

    def test_student_schema_load(self):
        data = {'id': 1, 'name': 'John Doe', 'address': '123 Main St', 'email': 'johndoe@example.com', 'phone': '123-456-7890'}
        result = StudentSchema().load(data)
        self.assertEqual(result.id, 1)
        self.assertEqual(result.name, 'John Doe')
        self.assertEqual(result.address, '123 Main St')
        self.assertEqual(result.email, 'johndoe@example.com')
        self.assertEqual(result.phone, '123-456-7890')

    def test_student_schema_dump_many(self):
        students = [
            Student(id=1, name='John Doe', address='123 Main St', email='johndoe@example.com', phone='123-456-7890'),
            Student(id=2, name='Jane Doe', address='456 Elm St', email='janedoe@example.com', phone='987-654-3210')
        ]
        db.session.add_all(students)
        db.session.commit()
        result = StudentSchema(many=True).dump(students)
        self.assertEqual(len(result), 2)
        self.assertEqual(result[0]['id'], 1)
        self.assertEqual(result[0]['name'], 'John Doe')
        self.assertEqual(result[0]['address'], '123 Main St')
        self.assertEqual(result[0]['email'], 'johndoe@example.com')
        self.assertEqual(result[0]['phone'], '123-456-7890')
        self.assertEqual(result[1]['id'], 2)
        self.assertEqual(result[1]['name'], 'Jane Doe')
        self.assertEqual(result[1]['address'], '456 Elm St')
        self.assertEqual(result[1]['email'], 'janedoe@example.com')
        self.assertEqual(result[1]['phone'], '987-654-3210')

    def test_student_schema_load_many(self):
        data = [
            {'id': 1, 'name': 'John Doe', 'address': '123 Main St', 'email': 'johndoe@example.com', 'phone': '123-456-7890'},
            {'id': 2, 'name': 'Jane Doe', 'address': '456 Elm St', 'email': 'janedoe@example.com', 'phone': '987-654-3210'}
        ]
        result = StudentSchema(many=True).load(data)
        self.assertEqual(len(result), 2)
        self.assertEqual(result[0].id, 1)
        self.assertEqual(result[0].name, 'John Doe')
        self.assertEqual(result[0].address, '123 Main St')
        self.assertEqual(result[0].email, 'johndoe@example.com')
        self.assertEqual(result[0].phone, '123-456-7890')
        self.assertEqual(result[1].id, 2)
        self.assertEqual(result[1].name, 'Jane Doe')
        self.assertEqual(result[1].address, '456 Elm St')
        self.assertEqual(result[1].email, 'janedoe@example.com')
        self.assertEqual(result[1].phone, '987-654-3210')