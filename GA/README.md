# Genetic Algorithm (GA)

## 1 Introduction
- Simulates the natural selection of Darwin's biological evolution theory and the biological evolutionary process of the genetic mechanism
- Key word: gene, population, individual, generation, fitness
- Genetic operators: selection, crossover, mutation  
![image](https://github.com/ananJet/Heuristic-Algorithm/blob/master/GA/flow.jpg)  


## 2 Encoding
<table><tr><th>Name</th><th>Example</th><th>Note</th></tr><tr><td>Binary</td><td>0 1 0 1 1 0<br></td><td>Represents a decimal number(26)</td></tr><tr><td>Gray</td><td>0 1 1 1 0 1</td><td>Represents a decimal number(26) and only one bit of code is different between the coded values of two consecutive integers</td></tr><tr><td>Float</td><td>2.3 4.5 2.4 1.2 1.6 1.7</td><td>Suitable for dealing with high precision and complex constraints</td></tr><tr><td>Char</td><td>A B C D E F G</td><td>A sequence with no numerical meaning</td></tr></table>

## 3 Fitness  
<img src="https://latex.codecogs.com/gif.latex?F_{fitness}=F_{object(max)}\;&space;\;&space;\;&space;or\;&space;\;&space;\;&space;F_{fitness}=1/F_{object(min)}" title="F_{fitness}=F_{object(max)}\; \; \; or\; \; \; F_{fitness}=1/F_{object(min)}" />  

## 4 Genetic operators 1: Selection  
>(1) Proportionate Roulette Wheel Selection(轮盘赌)
>- Sum the fitness values of individuals to obtain the total fitness value;
>- The probability that each individual is selected is obtained by the fitness value of the individual divided by the total fitness value of the population;
>- In order to build a roulette wheel, calculate the cumulative probability of each individual;
>- Roulette selection, generate a random number within the interval of [0, 1], if the number is less than or equal to the cumulative probability of individual 1 and larger than the cumulative probability of individual 2, individual 1 should be chosen to enter the offspring population;
>- <img src="https://latex.codecogs.com/gif.latex?P_{_{select}}(a_{i})=\frac{f(a_{i})}{\sum_{i=1}^{n}f(a_{i})}" title="P_{_{select}}(a_{i})=\frac{f(a_{i})}{\sum_{i=1}^{n}f(a_{i})}" />

>(2) Tournament Selection(锦标赛)
>- 确定每次选择的个体数量(本文以占种群中个体个数的百分比表示)
>- 从种群中随机选择个个体(每个个体入选概率相同) 构成组，根据每个个体的适应度值，选择其中适应度值最好的个体进入子代种群
>- 重复步骤(2)次，得到的个体构成新一代种群
![image](https://github.com/ananJet/Heuristic-Algorithm/blob/master/GA/tournament%20selection.jpg)

>(3) Linear Ranking Selection(线性排序)
>- 种群中的个体首先根据适应度的值进行排序，然后给所有个体赋予一个序号，最好的个体为N, 被选中的概率为Pmax, 最差的个体序号为1, 被选中的概率为Pmin，于是其他的在他们中间的个体的概率可以由公式计算  
>- <img src="https://latex.codecogs.com/gif.latex?P_{i}=P_{min}&plus;(P_{max}-P_{min})\frac{i-1}{N-1}" title="P_{i}=P_{min}+(P_{max}-P_{min})\frac{i-1}{N-1}" />

>(4) Exponential Ranking Selection(指数排序)
>- 类似于线性排序选择，将个体排名后，指数排序在确定每个个体的选择概率的时候使用了指数形式的表达式, 其中c为底数,0＜c＜1  
>- <img src="https://latex.codecogs.com/gif.latex?P_{i}=\frac{c^{N-i}}{\sum_{j=1}^{N}c^{N-i}}" title="P_{i}=\frac{c^{N-i}}{\sum_{j=1}^{N}c^{N-i}}" />

## 5 Genetic operators 2: Crossover  
>- Partial-Mapped Crossover (PMX)  
>- Order Crossover (OX)  
>- Position-based Crossover (PBX)  
>- Cycle Crossover (CX)  
>- Subtour Exchange Crossover (SEC)  

| First Header  | Second Header |
| ------------- | ------------- |
| Content Cell  | Content Cell  |
| Content Cell  | Content Cell  |

## 6 Genetic operators 3: Mutation  
>- 互换
>- 变值

