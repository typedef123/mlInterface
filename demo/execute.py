#Model Test File
import tensorflow as tf
from keras.backend.tensorflow_backend import set_session
from keras.models import load_model
import sys
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation, Flatten, Conv2D, pooling
from keras.utils import np_utils
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

model = load_model(sys.argv[1])

test= np.loadtxt(sys.argv[2], delimiter=',', dtype='U', skiprows=4)

i = 0

arrX = []
cnt = int(sys.argv[3])

for i in range(0,cnt) :
	arrX.append(test[:, int(sys.argv[i+4])])

print("==============================")
x_test = []

for i in range(0,cnt - 1) :
	arrX[0] = np.hstack([arrX[0], arrX[i]])




#X_test = test[:, [1,3,4,5]]
X_test = np.array(arrX[0])
X_test[X_test == ''] = -9999
X_test = X_test.astype(np.float)
X_test2 = np.delete(X_test, np.where(X_test == -9999), None)
#X_test = X_test.astype(np.float)
#X_test = np.delete(X_test, '')



testX = []

for i in range(0, len(X_test) - 10):
	_x = X_test[i:i + 10]
	testX.append(_x)


testX = np.array(testX)
testX = testX.astype('float32')
testX /= 255
testX = testX.reshape(len(testX), 10, 4, 1)
print(len(testX))
output = model.predict_classes(testX)

np.savetxt("output.csv", output, delimiter=",")
