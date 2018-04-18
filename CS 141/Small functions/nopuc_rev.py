import string
def nopuc_rev(A):
     
    B = A
    bad_string = string.whitespace + string.punctuation
    for a in B:
        if a in bad_string:
            B = B.replace(a, '')
    B = B[::-1]
    return B


