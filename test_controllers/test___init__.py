from python-flask-server.openapi_server.controllers.__init__ import *
import os

def delete_file_if_exists(file_path):
    if os.path.exists(file_path):
        os.remove(file_path)

def create_file(file_path):
    delete_file_if_exists(file_path)
    with open(file_path, "w") as f:
        f.write("Hello World")

def read_file(file_path):
    with open(file_path, "r") as f:
        return f.read()

file_path = "test.txt"

def test_delete_file_if_exists():
    # Test case to check if file is deleted successfully if it exists
    create_file(file_path)
    delete_file_if_exists(file_path)
    assert not os.path.exists(file_path)

def test_delete_file_if_not_exists():
    # Test case to check if file is not deleted if it does not exist
    delete_file_if_exists(file_path)
    assert not os.path.exists(file_path)

def test_create_file():
    # Test case to check if file is created successfully
    create_file(file_path)
    assert os.path.exists(file_path)

def test_read_file():
    # Test case to check if file is read successfully
    create_file(file_path)
    assert read_file(file_path) == "Hello World"

def test_read_file_nonexistent():
    # Test case to check if error is raised when nonexistent file is read
    delete_file_if_exists(file_path)
    try:
        read_file(file_path)
    except FileNotFoundError:
        assert True
    else:
        assert False