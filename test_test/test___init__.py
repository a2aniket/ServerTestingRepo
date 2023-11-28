from python-flask-server.openapi_server.test.__init__ import *
import unittest
import logging

import connexion
from flask_testing import TestCase

from openapi_server.encoder import JSONEncoder


class BaseTestCaseTest(unittest.TestCase):

    def test_create_app(self):
        """
        Test for creating Flask app
        """
        base_test_case = BaseTestCase()
        app = base_test_case.create_app()
        self.assertIsInstance(app, connexion.FlaskApp)

    def test_logging(self):
        """
        Test for logging configuration
        """
        base_test_case = BaseTestCase()
        app = base_test_case.create_app()
        self.assertEqual(app.logger.getEffectiveLevel(), logging.ERROR)

    def test_json_encoder(self):
        """
        Test for JSON Encoder
        """
        base_test_case = BaseTestCase()
        app = base_test_case.create_app()
        self.assertIsInstance(app.json_encoder, JSONEncoder)

    def test_add_api(self):
        """
        Test for adding OpenAPI specification
        """
        base_test_case = BaseTestCase()
        app = base_test_case.create_app()
        app.add_api('openapi.yaml', pythonic_params=True)
        self.assertIn('/v1', app.url_map._rules_by_endpoint.keys())