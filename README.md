# Javapractice

## Week1
- Introduction (JAVA,Git,MarkdDown)

<<<<<<< HEAD
## Week2
  - JAVA   
    * inheritnce
      > Inheritance is a comprehensive succession of property and status by the death of a person. It is stated in Wikipedia. If so, is the meaning of inheritance in Java much different from what is stated in the encyclopedia? no. It is similar. Just as a child inherits physical property or intellectual property inherited from a parent in the real world, a child class inherited from a parent class in Java has all the resources and methods of the parent class You can use it as your own.
=======
I would explain about simple Linear Regression using python module, *Scikit-learn*
We would use many modules to data analysis.
![python](https://i.ytimg.com/vi/erfZsVZbGJI/maxresdefault.jpg)
>>>>>>> 08aa7b1146bf19ed672bb7171fdfbcf4127db97c


    
  - Git&GitHub
    * stage
    * git init 
    * git add
    * commit
    * push
    * pull
    ![cheat Sheat](https://zeroturnaround.com/wp-content/uploads/2016/05/Git-Cheat-Sheet-by-RebelLabs.png)
  - MarkDown
    * 기본 사용법
    ![Cheat Sheat](https://i.pinimg.com/originals/33/19/81/3319813c4fd34c1e5d8663ea3a632329.jpg)
    * 직접 readme파일 만들어보기
## Week3
  - Java
    * Abstraction (추상화) 
      > OOP is a programming paradigm that uses abstraction to model real-world.     
      Abstraction is literally ignoring detailed information and constructing it with only the information that is needed by necessity.

    * Practice
    
<<<<<<< HEAD
|           Class          | Variable   | method              |
|:------------------------:|------------|---------------------|
| Animal(super)            | picture    | sleep               |
|                          | food       | makeNoise(abstract) |
|                          | hunger     | eat(abstract)       |
|                          | boundaries | roam(abstract)      |
|                          | location   |                     |
| Canine[Animal] (abstract) |            | roam                |
| Feline[Animal] (abstract) |            | roam                |
| Hippo[Animal]            |            | roam                |
|                          |            | makenoise           |
|                          |            | eat                 |
| Dog[Canine]              |            | makenoise           |
|                          |            | eat                 |
| Wolf[Canine]             |            | makenoise           |
|                          |            | eat                 |
| Lion[Feline]             |            | makenoise           |
|                          |            | eat                 |
| Tiger[Feline]            |            | makenoise           |
|                          |            | eat                 |
| Cat[Feline]              |            | makenoise           |
|                          |            | eat                 |
=======
-    [Third,Linear Regression](#Third,Linear-Regression-using-Gradient-Descent)
       - [Using Gradient Descent](#Gradient-Descent)
       - [Using Sickit Learn.](#Linear-Regression-using-Scikit-Learn)
-    [Fourth,Performance Mearsure](#performance-measure)   

---

## First, We use pandas module to preprocessing.


```python
import pandas as pd
import io

df=pd.read_csv(io.StringIO(uploaded['slr06.csv'].decode('utf-8')))

df.head()
```




<div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>X</th>
      <th>Y</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>108</td>
      <td>392.5</td>
    </tr>
    <tr>
      <th>1</th>
      <td>19</td>
      <td>46.2</td>
    </tr>
    <tr>
      <th>2</th>
      <td>13</td>
      <td>15.7</td>
    </tr>
    <tr>
      <th>3</th>
      <td>124</td>
      <td>422.2</td>
    </tr>
    <tr>
      <th>4</th>
      <td>40</td>
      <td>119.4</td>
    </tr>
  </tbody>
</table>
</div>




```python
import matplotlib.pyplot as plt
%matplotlib inline
import numpy as np
```

---

## Second,EDA and visualiztion data using matplotlib


```python
raw_X = df["X"].values.reshape(-1, 1)
y = df["Y"].values
```


```python
plt.figure(figsize=(10,5))
plt.plot(raw_X,y, 'o', alpha=0.5)
```




    [<matplotlib.lines.Line2D at 0x7f1ae713c080>]




![pg](output_10_1.png)



```python
X = np.concatenate( (np.ones((len(raw_X),1)), raw_X ), axis=1) # b값을 1로 초기화시켜주기
X[:5]
```




    array([[  1., 108.],
           [  1.,  19.],
           [  1.,  13.],
           [  1., 124.],
           [  1.,  40.]])




```python
w = np.random.normal((2,1))  # w값 초기화하기. 정규분포 수중에서 뽑아오는 것
# w = np.array([5,3])
w
```




    array([2.9289942 , 1.69365812])




```python
plt.figure(figsize=(10,5))
y_predict = np.dot(X, w)
plt.plot(raw_X,y,"o", alpha=0.5)
plt.plot(raw_X,y_predict)
```




    [<matplotlib.lines.Line2D at 0x7f1ae51122e8>]




![png](output_13_1.png)


---

## Third,Linear Regression using Gradient Descent

###  Define HYPOTHESIS AND COST FUNCTION


```python
def hypothesis_function(X, theta): # 가설함수에 대한 정의
    return X.dot(theta)
```


```python
def cost_function(h, y):  # cost함수에 대한 정의
    return (1/(2*len(y))) * np.sum((h-y)**2)
```


```python
h = hypothesis_function(X,w)
cost_function(h, y)
```




    3013.5223324010303



### Gradient Descent


```python
def gradient_descent(X, y, w, alpha, iterations):# alpha= learning rate
    theta = w # weight값 저장
    m = len(y)
>>>>>>> 08aa7b1146bf19ed672bb7171fdfbcf4127db97c
    

  - GitHub
    * readme파일 수정 및 [git pages](https://younghyundev.github.io/javapractice/) 만들어보기
    
