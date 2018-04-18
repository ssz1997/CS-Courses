def fib(c):
    fib_a = 0
    fib_b = 1
    fib_c = 0
    start = 2
    bound = c + 1
    for c in range(start, bound):
        fib_c = fib_a + fib_b
        fib_a = fib_b
        fib_b = fib_c
    return fib_c

c = int(input('n=?'))

y = fib(c)

print (y)

