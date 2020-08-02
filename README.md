# Equations solver

Console app for solving systems of linear equations. It handles equations with either real or complex coefficients.

## How to start

```bash
git clone https://github.com/ljurak/linear-equations.git
cd linear-equations
mvn clean package
cd target
java -jar linear-equations.jar -in input.txt -out output.txt
```

The application requires two parameters:

| Launch parameters | Permitted values              |
|-------------------|-------------------------------|
| -in               | file with input data          |
| -out              | file for saving results       |

Example input file
```bash
3 3
4 2+3i 5i 3
5-6i 4 4 2+i
4+2i 6 6-i 4
``` 
First line contains two numbers M, N. M is the number of unknowns for the system, N is the number of equations.
The next N lines contains M+1 coefficients for each equation. 