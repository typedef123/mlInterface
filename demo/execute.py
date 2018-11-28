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
np.savetxt("output.csv", output.astype(int), fmt='%5.0f', delimiter=",")


fig,(ax1) = plt.subplots(nrows=1)
fig.set_size_inches((18.5, 10.5))



ax1.plot(test[:, [3]] * np.amax(X_test), 'g')
ax1.plot(test[:, [1]],'k')

for (start, end) in range_list:
    ax1.axvspan(start, end,facecolor='red', alpha=0.5)


ax1.set_yticks(np.arange(0, np.amax(X_test), step=10))


plt.tight_layout()
plt.savefig('pict.png')
