from python-flask-server.openapi_server.encoder import *
import unittest
from unittest.mock import MagicMock
from openapi_server.models.base_model_ import Model
from your_module_name import JSONEncoder

class TestJSONEncoder(unittest.TestCase):

    def setUp(self):
        self.encoder = JSONEncoder()

    def test_default_with_model(self):
        # Test that the default method returns a dictionary when given a Model instance
        model = Model()
        model.openapi_types = {"id": int, "name": str}
        model.attribute_map = {"id": "id", "name": "name"}
        model.id = 1
        model.name = "test"
        expected_result = {"id": 1, "name": "test"}
        self.assertEqual(self.encoder.default(model), expected_result)

    def test_default_without_model(self):
        # Test that the default method returns the default FlaskJSONEncoder behavior when given a non-Model instance
        obj = MagicMock()
        self.assertEqual(self.encoder.default(obj), obj)

    def test_default_with_nulls(self):
        # Test that the default method includes null values when include_nulls is True
        self.encoder.include_nulls = True
        model = Model()
        model.openapi_types = {"id": int, "name": str}
        model.attribute_map = {"id": "id", "name": "name"}
        model.id = None
        model.name = "test"
        expected_result = {"id": None, "name": "test"}
        self.assertEqual(self.encoder.default(model), expected_result)

    def test_default_without_nulls(self):
        # Test that the default method excludes null values when include_nulls is False
        self.encoder.include_nulls = False
        model = Model()
        model.openapi_types = {"id": int, "name": str}
        model.attribute_map = {"id": "id", "name": "name"}
        model.id = None
        model.name = "test"
        expected_result = {"name": "test"}
        self.assertEqual(self.encoder.default(model), expected_result)