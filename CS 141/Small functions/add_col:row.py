def add_col(A, v):
    x = 0
    while x < len(A):
        A[x].append(v[x])
        x = x + 1
    return A

def add_row(A,v):
    A.append(v)
    return A


