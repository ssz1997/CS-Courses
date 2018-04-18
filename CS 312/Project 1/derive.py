import string
#! /usr/bin/python3


import sys



if sys.argv[1] == "-l":
  n = 3
else:
  n = int("".join(list(sys.argv[1])[2:]))
my_dict = {}
my_worklist = []
start_symbol = ""


#creat the dictionary
with open(sys.argv[2], "r") as grammer:
  grammer_file = grammer.readlines()
for i in range(0, len(grammer_file)):
  line = grammer_file[i].split(" = ")
  key = line[0].rstrip("\n")
  val = line[1].rstrip("\n")
  if key not in my_dict:
    my_dict[key] = []
    my_dict[key].append(val)
  else:
    my_dict[key].append(val)


#find the start symbol
firstline = open(sys.argv[2], "r").readline()
start_symbol = firstline.split(" = ")[0]


for i in range(0, len(my_dict[start_symbol])):
  my_worklist.append(my_dict[start_symbol][i])



def all_terminal(my_dict, string):
  for key in my_dict:
    if key in string:
      return False    
  return True

while my_worklist != []:
  check = my_worklist.pop()
  find_len_list = check.split()
  if len(find_len_list) > n:
    continue
  else:
    if all_terminal(my_dict, check) == True:
      print (check)
    else:
      final = 0
      find_nt_list = check.split()
      while find_nt_list[final] not in my_dict:
        final += 1  
      for i in range(0, len(my_dict[find_nt_list[final]])):
        tmp = my_dict[find_nt_list[final]][i]
        find_nt_list_helper = find_nt_list[:]
        find_nt_list_helper[final] = tmp
        tmp = " ".join(find_nt_list_helper)
        my_worklist.append(tmp)
        

  


