def median_of_three(a,b,c):
    if a <= b <= c or c < b < a:
        return b
    if a < c < b or b <=c < a:
        return c
    if c <= a <b or b < a <= c or c < a == b:
        return a

a = int(input('a?'))
b = int(input('b?'))
c = int(input('c?'))

y = median_of_three(a,b,c)

print (y)
