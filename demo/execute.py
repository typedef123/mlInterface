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
model = load_model(sys.argv[2])

test= np.loadtxt(sys.argv[3], delimiter=',', dtype='U', skiprows=4)

i = 0

arrX = []
cnt = int(sys.argv[4])
for i in range(0,cnt) :
	arrX.append(test[:, int(sys.argv[i+5])])

x_test = []


x_test=np.fliplr(np.rot90(arrX,3))	
		

testX = []
for i in range(0, len(x_test) - 10):
	_x = x_test[i:i + 10]
	testX.append(_x)

testX = np.array(testX)
testX = testX.astype('float32')
testX /= 255
testX = testX.reshape(len(testX), 10, cnt, 1)
print(len(testX))
output = model.predict_classes(testX)
np.savetxt("./output.csv", output, delimiter=",")