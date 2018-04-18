class Linked_List:
  
  class __Node:
    def __init__(self, val):
      # declare and initialize the private attributes
      # for objects of the Node class.
      # TODO replace pass with your implementation
      self.val = val
      self.__next = None
      self.__prev = None

  def __init__(self):
    # declare and initialize the private attributes
    # for objects of the sentineled Linked_List class
    # TODO replace pass with your implementation
    self.__header = self.__Node(None)
    self.__trailer = self.__Node(None)
    self.__header.__next = self.__trailer
    self.__trailer.__prev = self.__header
    self.__size = 0
    
  def __len__(self):
    return self.__size

  def append_element(self, val):
    # increase the size of the list by one, and add a
    # node containing val at the new tail position. this 
    # is the only way to add items at the tail position.
    # TODO replace pass with your implementation
    newest = self.__Node(val)
    if self.__size == 0:
      newest.__next = self.__trailer
      self.__header.__next = newest
      self.__header.__next.__prev = self.__header
      newest.__next.__prev = newest
    else:
      newest.__next = self.__trailer
      self.__trailer.__prev.__next = newest
      newest.__prev = self.__trailer.__prev
      self.__trailer.__prev = newest
    self.__size = self.__size + 1
    

  def insert_element_at(self, val, index):
    if index >= self.__size or index < 0:
      raise IndexError
      
    newest = self.__Node(val)
    current = self.__header
    for i in range(0, index):
      current = current.__next
    newest.__next = current.__next
    current.__next.__prev = newest
    newest.__prev = current
    current.__next = newest
    self.__size = self.__size + 1
      
  def remove_element_at(self, index):
    if index >= self.__size:
      raise IndexError
    elif index < 0:
      raise IndexError
    elif(index == 0):
      value = self.__header.__next
      self.__header.__next = self.__header.__next.__next
      self.__header.__next.__prev = self.__header
    elif(index <= (self.__size / 2)):
      cur = self.__header.__next
      for i in range(1, index):
        cur = cur.__next
        value = cur.__next
      cur.__next = cur.__next.__next
      cur.__next.__prev = cur
    else:
      cur = self.__trailer.__prev
      index = (self.__size - index)
      for i in range(index):
        cur = cur.__prev
      value = cur.__next
      cur.__next = cur.__next.__next
      cur.__next.__prev = cur
		
    self.__size = (self.__size - 1)
    return value.val

  def get_element_at(self, index):
    if index >= self.__size:
      raise IndexError
    elif index < 0:
      raise IndexError
    elif(index == 0):
      return self.__header.__next.val
    elif(index <= (self.__size / 2)):
      cur = self.__header.__next
      for i in range(1, index):
        cur = cur.__next
      return cur.__next.val
    else:
      cur = self.__trailer
      index = (self.__size - index)
      for i in range(1, index):
        cur = cur.__prev
      return cur.__prev.val
  
  def rotate_left(self):
    move = self.__header.__next
    self.__header.__next = self.__header.__next.__next
    self.__header.__next.__prev = self.__header
    self.__trailer.__prev.__next = move
    move.__prev = self.__trailer.__prev
    self.__trailer.__prev = move
    move.__next = self.__trailer
    
    
  def __str__(self):
    if self.__size == 0:
      return '[ ]'
    string = '[ '
    cur = self.__header.__next
    for i in range(1, self.__size):
      string += str(cur.val)
      string += ', '
      cur = cur.__next
    string += str(cur.val)
    
    return string + " ]"

  def __iter__(self):
    # initialize a new attribute for walking through your list
    # TODO insert your initialization code before the return
    # statement. do not modify the return statement.
    self.__iter_current = self.__header.__next
    return self

  def __next__(self):
    # using the attribute that you initialized in __iter__(),
    # fetch the next value and return it. If there are no more 
    # values to fetch, raise a StopIteration exception.
    # TODO replace pass with your implementation
    if self.__iter_current == self.__trailer:
      raise StopIteration
    to_return = self.__iter_current.val
    self.__iter_current = self.__iter_current.__next
    return to_return

if __name__ == '__main__':
  # Your test code should go here. Be sure to look at cases
  # when the list is empty, when it has one element, and when 
  # it has several elements. Do the indexed methods raise exceptions
  # when given invalid indices? Do they position items
  # correctly when given valid indices? Does the string
  # representation of your list conform to the specified format?
  # Does removing an element function correctly regardless of that
  # element's location? Does a for loop iterate through your list
  # from head to tail? Your writeup should explain why you chose the
  # test cases. Leave all test cases in your code when submitting.
  # TODO replace pass with your tests
  test = Linked_List()
  print(test)
  print("Linked List has " + str(len(test)) + " elements\n")

# append_element should always be good
  test.append_element(30)
  test.append_element(31)
  test.append_element(32)
  test.append_element(33)
  test.append_element(34)
  print(test)
  print("Linked List has " + str(len(test)) + " elements\n")

# insert at middle and beginning
  try:
    test.insert_element_at(999, 4)
    test.insert_element_at(0, 0)
  except IndexError:
    print('ERROR: insert_element_at ... INDEX OUT OF RANGE\n')
  print(test)

# attempt and fail to insert at negative and too-large index
  try:
    test.insert_element_at(8, -3)
  except IndexError:
    print('ERROR: insert_element_at ... INDEX OUT OF RANGE\n')
  print('>>>>>This should match the last list!:')
  print(test, '\n')

  try:
    test.insert_element_at(8, 8000)
  except IndexError:
    print('ERROR: insert_element_at ... INDEX OUT OF RANGE\n')
  print('>>>>>This should also match the last list! Two failed inserts!:')
  print(test, '\n')

  # Real removal
  try:
    remove = test.remove_element_at(2)
  except IndexError:
    print('ERROR: remove_element_at ... INDEX OUT OF RANGE\n')
  print('>>>>>Did we remove index #2?')
  print(test, '\n')
  
  # Invalid removal
  try:
    test.remove_element_at(-1)
  except IndexError:
    print('ERROR: remove_element_at ... INDEX OUT OF RANGE\n')
  print(test)

  try:
    test.remove_element_at(800)
  except IndexError:
    print('ERROR: remove_element_at ... INDEX OUT OF RANGE\n')
  print('>>>>>Same list as before right? Two failed removals:')
  print (test, '\n')

# Real removal (last item in list)
  try:
    remove = test.remove_element_at(5)
  except IndexError:
    print('ERROR: remove_element_at ... INDEX OUT OF RANGE\n')
  print('>>>>>We removed the last item?')
  print (test, '\n')

# Return some values.
  try:
    element_0 = test.get_element_at(0)
  except IndexError:
    print('ERROR: get_element_at ... INDEX OUT OF RANGE\n')
  print ('>>>>>What is item #1?')
  print (element_0, '\n')
  try:
    element_4 = test.get_element_at(4)
  except IndexError:
    print('ERROR: get_element_at ... INDEX OUT OF RANGE\n')
  print ('>>>>>What is item #5?')
  print (element_4, '\n')
  
#Fail to return a couple
  print ('>>>>>What is item #3000?')
  try:
    print (test.get_element_at(3000))
  except IndexError:
    print('ERROR: get_element_at ... INDEX OUT OF RANGE\n')
    
  print ('>>>>>What is item #-1?')
  try:
    print (test.get_element_at(-1))
  except IndexError:
    print('ERROR: get_element_at ... INDEX OUT OF RANGE\n')

# Rotaaaate!
  print(test, '\n')
  print('>>>>>Rotate!')
  test.rotate_left()
  print (test, '\n')
  print("Linked List has " + str(len(test)) + " elements\n")

# Walk toward...the end of the code
  print("Iterator loop:")
  for val in test:
    print (val)
    
# Try everything one more time
  print('Running each function one more time for confirmation. \nAppend 1000, insert 2000, remove at 2, get at 1, rotate')
  test.append_element(1000)
  print(test)
  test.insert_element_at(2000,1)
  print(test)
  test.remove_element_at(2)
  print(test)
  print('Element in index 1 is:', test.get_element_at(1))
  test.rotate_left()
  print(test)
  print('Done.')
