from python-flask-server.openapi_server.models.base_model_ import *
import unittest
from unittest.mock import MagicMock
from openapi_server import util


class TestModel(unittest.TestCase):
    
    def test_from_dict(self):
        # Test if the function returns an instance of the model
        dikt = {"attr1": "value1", "attr2": "value2"}
        model_instance = MagicMock()
        util.deserialize_model = MagicMock(return_value=model_instance)

        result = Model.from_dict(dikt)

        self.assertIsInstance(result, Model)
        util.deserialize_model.assert_called_once_with(dikt, Model)

    def test_to_dict(self):
        # Test if the function returns a dictionary
        model_instance = Model()
        model_instance.attr1 = "value1"
        model_instance.attr2 = "value2"

        result = model_instance.to_dict()

        self.assertIsInstance(result, dict)
        self.assertEqual(result, {"attr1": "value1", "attr2": "value2"})

    def test_to_str(self):
        # Test if the function returns a string
        model_instance = Model()
        model_instance.attr1 = "value1"
        model_instance.attr2 = "value2"

        result = model_instance.to_str()

        self.assertIsInstance(result, str)
        self.assertEqual(result, "{'attr1': 'value1', 'attr2': 'value2'}")

    def test__eq__(self):
        # Test if the function returns True if two objects are equal
        model_instance_1 = Model()
        model_instance_1.attr1 = "value1"
        model_instance_1.attr2 = "value2"

        model_instance_2 = Model()
        model_instance_2.attr1 = "value1"
        model_instance_2.attr2 = "value2"

        self.assertTrue(model_instance_1 == model_instance_2)

    def test__ne__(self):
        # Test if the function returns True if two objects are not equal
        model_instance_1 = Model()
        model_instance_1.attr1 = "value1"
        model_instance_1.attr2 = "value2"

        model_instance_2 = Model()
        model_instance_2.attr1 = "value1"
        model_instance_2.attr2 = "value3"

        self.assertTrue(model_instance_1 != model_instance_2)