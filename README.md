# Dots and Boxes

A game played on a grid of dots where the goal is to create boxes with lines. Each player takes turns connecting two adjacent dots and the player connecting the final line of the box owns that box. 

In the end, the player with higher number of boxes wins. 

```
o   o---o 

o   o   o  --> a player draws a vertical or horizontal line each turn
    |    
o   o   o

o---o---o 
| A | B |
o---o---o  --> Player A wins
| A | A | 
o---o---o

o---o---o
| B | B |
o---o---o  --> Player B wins
| B | A |
o---o---o
```