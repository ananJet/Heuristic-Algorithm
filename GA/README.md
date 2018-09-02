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
>- 将种群中个体的适应度值叠加，得到总适应度值
>- 每个个体的适应度值除以总适应度值得到个体被选择的概率
>- 计算个体的累积概率以构造一个轮盘
>- 轮盘选择：产生一个[0,1]区间内的随机数，若该随机数小于或等于个体的累积概率且大于个体1的累积概率，选择个体进入子代种群  
>- <img src="https://latex.codecogs.com/gif.latex?P_{_{select}}(a_{i})=\frac{f(a_{i})}{\sum_{i=1}^{n}f(a_{i})}" title="P_{_{select}}(a_{i})=\frac{f(a_{i})}{\sum_{i=1}^{n}f(a_{i})}" />

>(2) Tournament Selection(锦标赛)
>- 确定每次选择的个体数量(本文以占种群中个体个数的百分比表示)
>- 从种群中随机选择个个体(每个个体入选概率相同) 构成组，根据每个个体的适应度值，选择其中适应度值最好的个体进入子代种群
>- 重复步骤(2)次，得到的个体构成新一代种群
![image](https://github.com/ananJet/Heuristic-Algorithm/blob/master/GA/tournament%20selection.jpg)

>(3) Linear Ranking Selection(线性排序)
>- 种群中的个体首先根据适应度的值进行排序，然后给所有个体赋予一个序号，最好的个体为N, 被选中的概率为Pmax, 最差的个体序号为1, 被选中的概率为Pmin，于是其他的在他们中间的个体的概率可以由公式计算  

>(4) Exponential Ranking Selection(指数排序)
>- 类似于线性排序选择，将个体排名后，指数排序在确定每个个体的选择概率的时候使用了指数形式的表达式, 其中c为底数  

## 5 Genetic operators 1: Crossover  
| First Header  | Second Header |
| ------------- | ------------- |
| Content Cell  | Content Cell  |
| Content Cell  | Content Cell  |


