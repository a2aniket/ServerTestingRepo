from python-flask-server.openapi_server.utils.constants import *
import unittest

class TestParameters(unittest.TestCase):
    
    def test_default_parameters(self):
        self.assertEqual(pageNumber, 1)
        self.assertEqual(pageSize, 100)
        self.assertEqual(sortBy, 'id')
        self.assertEqual(sortDir, 'asc')
        self.assertEqual(search, '')
    
    def test_page_number(self):
        new_page_number = 2
        self.assertNotEqual(pageNumber, new_page_number)
        global pageNumber
        pageNumber = new_page_number
        self.assertEqual(pageNumber, new_page_number)
    
    def test_page_size(self):
        new_page_size = 50
        self.assertNotEqual(pageSize, new_page_size)
        global pageSize
        pageSize = new_page_size
        self.assertEqual(pageSize, new_page_size)
    
    def test_sort_by(self):
        new_sort_by = 'name'
        self.assertNotEqual(sortBy, new_sort_by)
        global sortBy
        sortBy = new_sort_by
        self.assertEqual(sortBy, new_sort_by)
    
    def test_sort_dir(self):
        new_sort_dir = 'desc'
        self.assertNotEqual(sortDir, new_sort_dir)
        global sortDir
        sortDir = new_sort_dir
        self.assertEqual(sortDir, new_sort_dir)
    
    def test_search(self):
        new_search = 'test'
        self.assertNotEqual(search, new_search)
        global search
        search = new_search
        self.assertEqual(search, new_search)

if __name__ == '__main__':
    unittest.main()