# !/usr/bin/python

import os
for root, dirs, files in os.walk("C:\\Users\\sue_12\\Downloads\\TIKA\\", topdown=False):
    for name in files:
        print(os.path.join(root, name))
    for name in dirs:
        print(os.path.join(root, name))