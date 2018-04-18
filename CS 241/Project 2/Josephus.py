from Linked_List import Linked_List


def Josephus(ll):
  # solve the Josephus problem following the following algorithm:
  # rotate the list to the left by one position circularly, 
  # and then delete the first element; 
  # repeat it until there is only one element left in the list.
  # print the sequence of survivors after each death, 
  # and finally print the survivor’s number.
  # TODO replace pass with your implementation 
  while len(ll) != 1:
    Linked_List.rotate_left(ll)
    Linked_List.remove_element_at(ll,0)
    print (ll)
  print("The survivor is:", Linked_List.get_element_at(ll,0))

if __name__ == '__main__':
  # create a new doubly linked list object called ll
  # with 41 elements named 1 to 41.
  # TODO insert your implementation before the print statement
  ll = Linked_List()
  for i in range(1,42):
    ll.append_element(i)
  print("Initial order:", ll)
  Josephus(ll)
