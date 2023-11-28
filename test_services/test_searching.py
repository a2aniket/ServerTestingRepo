from python-flask-server.openapi_server.services.searching import *
import unittest
from app import searching, db, Model

class TestSearching(unittest.TestCase):

    def setUp(self):
        self.test_model = Model(field1='test1', field2=5)
        db.session.add(self.test_model)
        db.session.commit()

    def tearDown(self):
        db.session.delete(self.test_model)
        db.session.commit()

    def test_equals_operator(self):
        search_params = 'field1 = "test1"'
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

    def test_less_than_operator(self):
        search_params = 'field2 < 10'
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

    def test_greater_than_operator(self):
        search_params = 'field2 > 2'
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

    def test_less_than_equals_operator(self):
        search_params = 'field2 <= 5'
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

    def test_greater_than_equals_operator(self):
        search_params = 'field2 >= 5'
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

    def test_like_operator(self):
        search_params = 'field1 like "%test%"'
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

    def test_between_operator(self):
        search_params = 'field2 BETWEEN 0 AND 10'
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

    def test_multiple_filters(self):
        search_params = 'field1 = "test1"; field2 > 2'
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

    def test_no_filters(self):
        search_params = ''
        result = searching(search_params, Model).all()
        self.assertEqual(result, [self.test_model])

if __name__ == '__main__':
    unittest.main()