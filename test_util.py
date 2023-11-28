from python-flask-server.openapi_server.util import *
import unittest
import datetime

class TestDeserialize(unittest.TestCase):

    def test_deserialize_primitive(self):
        self.assertEqual(_deserialize_primitive(5, int), 5)
        self.assertEqual(_deserialize_primitive(5.0, float), 5.0)
        self.assertEqual(_deserialize_primitive("hello", str), "hello")
        self.assertEqual(_deserialize_primitive(True, bool), True)

    def test_deserialize_object(self):
        self.assertEqual(_deserialize_object("hello"), "hello")

    def test_deserialize_date(self):
        self.assertEqual(deserialize_date("2022-10-10"), datetime.date(2022, 10, 10))
        self.assertEqual(deserialize_date(None), None)
        self.assertRaises(ValueError, deserialize_date, "invalid date format")

    def test_deserialize_datetime(self):
        self.assertEqual(deserialize_datetime("2022-10-10T05:30:00Z"), datetime.datetime(2022, 10, 10, 5, 30, 0))
        self.assertEqual(deserialize_datetime(None), None)
        self.assertRaises(ValueError, deserialize_datetime, "invalid datetime format")

    def test_deserialize_model(self):
        class TestModel:
            openapi_types = {'attr1': str, 'attr2': int}
            attribute_map = {'attr1': 'attr_1', 'attr2': 'attr_2'}

            def __init__(self, attr1=None, attr2=None):
                self.attr1 = attr1
                self.attr2 = attr2

        data = {'attr_1': 'hello', 'attr_2': 5}
        model_instance = TestModel(attr1='hello', attr2=5)
        self.assertEqual(_deserialize(data, TestModel), model_instance)

    def test_deserialize_list(self):
        data = ['hello', 'world']
        boxed_type = str
        self.assertEqual(_deserialize_list(data, boxed_type), ['hello', 'world'])

    def test_deserialize_dict(self):
        data = {'key1': 'value1', 'key2': 'value2'}
        boxed_type = str
        self.assertEqual(_deserialize_dict(data, boxed_type), {'key1': 'value1', 'key2': 'value2'})

if __name__ == '__main__':
    unittest.main()