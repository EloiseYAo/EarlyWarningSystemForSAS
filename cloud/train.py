import numpy as np
from sklearn import svm,naive_bayes,ensemble,linear_model
from sklearn.metrics import accuracy_score
from sklearn.preprocessing import StandardScaler,normalize
import time
import pickle
filename = "ApneaData.pkl"
testPercent=20
features = []
classes = []
t = time.time()
f = open(filename,'rb')
data = pickle.load(f)
f.close()
np.random.shuffle(data)
for row in data:
    features.append(row[:-1])
    classes.append(row[-1])
inputLength = len(features)
testLength = int(inputLength*0.2)


train_features, train_classes=features[:-testLength], classes[:-testLength]
test_features,test_classes = features[-testLength:],classes[-testLength:]

print(len(train_features))
print(train_features[0])
print("preprocessing time:",(time.time()-t))
t=time.time()
clf=ensemble.RandomForestClassifier(n_estimators=30)
clf.fit(train_features,train_classes)
print("fitting time:",(time.time()-t))
t=time.time()
pred_classes=[]
for e in test_features:
    pred_classes.append(clf.predict([e])[0])
print(pred_classes)
score = accuracy_score(pred_classes,test_classes)*100
print("predicting time:",(time.time()-t))
print("Accuracy:",score)


# save model

# filename = 'finalized_model.sav'
# pickle.dump(clf, open(filename, 'wb'))
#
# loaded_model = pickle.load(open(filename, 'rb'))
#
# a=[0 for i in range(0,6000)]
# print(a)
# print(loaded_model.predict([a])[0])

# print(loaded_model.predict([test_features[0]])[0])
# print(type(test_features[0]))
# pred_classes=[]
# for e in test_features:
#     pred_classes.append(loaded_model.predict([e])[0])
#
#
# result = accuracy_score(pred_classes, test_classes)
# print(result)


#100Hz