from python-flask-server.openapi_server.__init__ import *
import os

def get_file_extension(filename):
    return os.path.splitext(filename)[1]

def is_python_file(filename):
    ext = get_file_extension(filename)
    return ext == '.py' or ext == '.pyw'