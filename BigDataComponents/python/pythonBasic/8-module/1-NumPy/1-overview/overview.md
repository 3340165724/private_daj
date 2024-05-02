# overview 概述

--------

<br>

### 概述
- NumPy (Numerical Python) is an open source Python library that’s used in almost every field of science and engineering.
- The NumPy API is used extensively in Pandas, SciPy, Matplotlib, scikit-learn, scikit-image and most other data science and scientific Python packages
- The NumPy library contains multidimensional array and matrix data structures. It provides ndarray, a homogeneous n-dimensional array object, with methods to efficiently operate on it. 
- NumPy can be used to perform a wide variety of mathematical operations on arrays. 
- It adds powerful data structures to Python that guarantee efficient calculations with arrays and matrices and it supplies an enormous library of high-level mathematical functions that operate on these arrays and matrices.

<br> 

- NumPy (Numerical Python)是一个开源Python库，几乎用于科学和工程的每个领域
- NumPy API广泛用于Pandas、SciPy、Matplotlib、scikit-learn、scikit-image以及大多数其他数据科学和科学Python包中
- NumPy库包含多维数组和矩阵数据结构，它提供了narray，一个同构的n维数组对象，以及有效操作它的方法
- NumPy可用于对数组执行各种各样的数学运算
- 它为Python添加了强大的数据结构，保证了数组和矩阵的高效计算，并提供了一个庞大的高级数学函数库，用于操作这些数组和矩阵。

<br>
<br>

### Python 列表和 NumPy 数组有什么区别
- NumPy gives you an enormous range of fast and efficient ways of creating arrays and manipulating numerical data inside them. 
- While a Python list can contain different data types within a single list, all of the elements in a NumPy array should be homogeneous. 
- The mathematical operations that are meant to be performed on arrays would be extremely inefficient if the arrays weren’t homogeneous.

<br> 

- NumPy为您提供了大量快速有效的方法来创建数组并在其中操作数值数据。
- 虽然Python列表可以在单个列表中包含不同的数据类型，但NumPy数组中的所有元素应该是同构的。
- 如果数组不是齐次的，在数组上执行的数学运算将是非常低效的。

<br> 
<br> 


### 为什么要使用NumPy
- NumPy arrays are faster and more compact than Python lists. An array consumes less memory and is convenient to use.
- NumPy uses much less memory to store data and it provides a mechanism of specifying the data types. This allows the code to be optimized even further.

<br> 

- NumPy数组比Python列表更快，更紧凑。数组占用内存少，使用方便。
- NumPy使用更少的内存来存储数据，它提供了一种指定数据类型的机制。这使得代码可以进一步优化。



<br>
<br> 


### 什么是数组
- An array is a central data structure of the NumPy library
- An array is a grid of values and it contains information about the raw data, how to locate an element, and how to interpret an element. It has a grid of elements that can be indexed in various ways. The elements are all of the same type, referred to as the array
- 数组是NumPy库的中心数据结构
- 数组是一个值的网格，它包含有关原始数据、如何定位元素以及如何解释元素的信息。它有一个可以以各种方式索引的元素网格。所有元素都是相同类型的，称为数组