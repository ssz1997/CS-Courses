def odd_sentences(my_string):
    my_list = my_string.split()
    a = [len(my_list[i]) for i in range(0, len(my_list)) if (len(my_list[i]) % 2 == 1)]
    if len(a) >= len(my_list) / 2:

        print ('true')
    else:
        print ('false')
my_string = input('string?')
odd_sentences(my_string)


