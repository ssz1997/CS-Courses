A = [1,[2,3,[4,5,6,[7]]]]

for a in A:    
    if type(a) is int:
        print (a)
    if type(a) is list:
        for b in a:
            if type(b) is int:
                print (b)
            if type(b) is list:
                for c in b:
                    if type(c) is int:
                        print (c)
                    if type(c) is list:
                        for d in c:
                            print (d)
     

