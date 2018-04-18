def phonetic(s):
    alphabet = {'a':'Alfa', 'b': 'Bravo', 'c':'Charlie', 'd': 'Delta', \
                'e': 'Echo', 'f': 'Foxtrot', 'g': 'Golf', 'h':'Hotel', \
                'i':'India', 'j':'Juliett', 'k':'Kilo', 'l': 'Lima', \
                'm':'Mike', 'n':'November', 'o': 'Oscar', 'p':'Papa',\
                'q':'Quebec', 'r':'Romeo','s':'Sierra', 't':'Tango',\
                'u':'uniform', 'v':'Victor', 'w':'Whiskey', 'x':'Xray',\
                'y':'Yankee', 'z':'Zulu'}
    new_str = ''
    a = list(s.lower())
    for i in range(0,len(a)):
        new_str = new_str + ' ' + alphabet[a[i]] 
    return new_str[1:]
s = input('string?')
b = phonetic(s)
print (b)
