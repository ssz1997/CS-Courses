def fib(n):
    a = 0
    b = 1
    i = 0
    while (i < n):
        c = a + b
        a= b
        b = c
        i = i + 1
    return a

n = int(input('afyeuh'))
x = fib(n)
print (x)
