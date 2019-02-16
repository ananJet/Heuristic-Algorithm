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
>- Determine the number of individuals selected each time(or the percentage in the population)
>- Individuals are selected randomly from the population to form a group, the individual with the maximum fitness value will be selected to enter the offspring population
>- Acquire individuals to form the new population by repeating the step 2
![image](https://github.com/ananJet/Heuristic-Algorithm/blob/master/GA/tournament%20selection.jpg)

>(3) Linear Ranking Selection(线性排序)
>- Firstly, the individuals in the population are sorted according to the fitness value, and then all individuals are assigned an ordinal number. 
>- The individual with the maximum fitness value is N, the probability of being selected is Pmax. 
>- The individual with the minimum fitness value is 1, and the probability of being selected is Pmin.
>- So the probability of other individuals among them can be calculated by the formula
>- <img src="https://latex.codecogs.com/gif.latex?P_{i}=P_{min}&plus;(P_{max}-P_{min})\frac{i-1}{N-1}" title="P_{i}=P_{min}+(P_{max}-P_{min})\frac{i-1}{N-1}" />

>(4) Exponential Ranking Selection(指数排序)
>- Similar to linear ranking selection, exponential ranking selection calculates the probability of being selected by the formula in exponential form, which c is the base(0<c<1)
>- <img src="https://latex.codecogs.com/gif.latex?P_{i}=\frac{c^{N-i}}{\sum_{j=1}^{N}c^{N-i}}" title="P_{i}=\frac{c^{N-i}}{\sum_{j=1}^{N}c^{N-i}}" />

## 5 Genetic operators 2: Crossover  
>- Partial-Mapped Crossover (PMX)  
>- Order Crossover (OX)  
>- Position-based Crossover (PBX)  
>- Cycle Crossover (CX)  
>- Subtour Exchange Crossover (SEC)  

## 6 Genetic operators 3: Mutation  
>- Exchange：When chromosomes adopt symbolic coding, the mutation effect can be achieved by exchanging values at different locations
>- Change：When chromosomes adopt float coding or binary coding and so on, the mutation effect can be achieved by change values at one location

