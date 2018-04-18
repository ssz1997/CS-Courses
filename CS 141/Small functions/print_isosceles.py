def print_isosceles(h):
    i = 0
    while i < h:
        i = i + 1
        print (' ' * (h-i), '*' * (2*i-1))

h = int(input('a'))

print_isosceles(h)

