def perfect(n):
    x = 1
    y = 0
    while x <= n ** (1/2):
        a = n / x
        b = int(a)
        if (abs(x * b - n) < 0.1):
            y = y + x + b
        x = x + 1
    if int(y) - n == n:
        return True
    if int(y) - n < n or int(y) - n > n:
        return False




