from python-flask-server.openapi_server.services.pagination_sorting import *
import unittest
from unittest.mock import MagicMock
from openapi_server.utils.constants import *
from openapi_server.services.searching import searching
from app import pagination_sorting

class TestPaginationSorting(unittest.TestCase):
    def setUp(self):
        self.Model = MagicMock()
        self.Model.query = MagicMock()

    def test_pagination_sorting_default_values(self):
        request.args.get = MagicMock(side_effect=[None, None, None, None])
        pagination_sorting(self.Model)
        self.Model.query.order_by.assert_called_with(self.Model.id.asc())
        self.Model.query.paginate.assert_called_with(page=1, per_page=10)

    def test_pagination_sorting_custom_values(self):
        request.args.get = MagicMock(side_effect=[2, 20, 'name', 'desc'])
        pagination_sorting(self.Model)
        self.Model.query.order_by.assert_called_with(self.Model.name.desc())
        self.Model.query.paginate.assert_called_with(page=2, per_page=20)

    def test_pagination_sorting_search_params(self):
        request.args.get = MagicMock(side_effect=[None, None, None, None])
        request.args.get('search', type=str, return_value='test')
        searching = MagicMock(return_value=self.Model.query)
        pagination_sorting(self.Model)
        searching.assert_called_with('test', self.Model)
        self.Model.query.order_by.assert_called_with(self.Model.id.asc())
        self.Model.query.paginate.assert_called_with(page=1, per_page=10)

    def test_pagination_sorting_sort_dir_asc(self):
        request.args.get = MagicMock(side_effect=[None, None, 'name', 'asc'])
        pagination_sorting(self.Model)
        self.Model.query.order_by.assert_called_with(self.Model.name.asc())
        self.Model.query.paginate.assert_called_with(page=1, per_page=10)

    def test_pagination_sorting_sort_dir_invalid(self):
        request.args.get = MagicMock(side_effect=[None, None, 'name', 'invalid'])
        pagination_sorting(self.Model)
        self.Model.query.order_by.assert_called_with(self.Model.name.asc())
        self.Model.query.paginate.assert_called_with(page=1, per_page=10)

if __name__ == '__main__':
    unittest.main()