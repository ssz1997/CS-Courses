class Linked_List:
  
  class __Node:
    
    def __init__(self, val):
      self.value = val
      self.__next = None
      self.__previous = None
      
      

  def __init__(self):
    self.header = self.__Node(None)
    self.trailer = self.__Node(None)  
    self.header.__next = self.trailer
    self.header.__previous = None
    self.trailer.__next = None
    self.trailer.__previous = self.header
    self.__size = 0


    
  def __len__(self):
    return self.__size     

  def append_element(self, val):
    newest = Linked_List.__Node(val)
    current = self.trailer.__previous
    
    newest.__next = self.trailer
    newest.__previous = current  
    current.__next = newest
    self.trailer.__previous = newest
    
    self.__size = self.__size + 1
    
          

  def insert_element_at(self, val, index):
    
    if index < 0 or index >= self.__size:
      raise IndexError
      
    current = self.header
    i = 0
    while(i < index):
      current = current.__next
      i = i + 1
    
    newest = Linked_List.__Node(val)
    newest.__next = current.__next
    newest.__previous = current    
    current.__next.__previous = newest
    current.__next = newest
    self.__size = self.__size + 1
    
  

  def remove_element_at(self, index):  
    if index < 0 or index >= self.__size:
      raise IndexError
    current = self.header  
    i = 0
    while i < index:
      current = current.__next
      i = i + 1
    remove_value = current.__next.value
    current.__next.__next.__previous = current
    current.__next = current.__next.__next
    self.__size = self.__size - 1
    return (remove_value)
    
    

  def get_element_at(self, index):
    if index < 0 or index >= self.__size:
      raise IndexError      
    current = self.header.__next
    i = 0
    while(i < index):
      current = current.__next
      i = i + 1
    return (current.value)
    
    

  def rotate_left(self):
  
    if (self.__size > 1): 
      self.append_element(self.header.__next.value)
      self.header.__next.__next.__previous = self.header
      self.header.__next = self.header.__next.__next
      self.__size = self.__size - 1
  
    
    
  def __str__(self):   
   
   contents = "[ "
   if self.__size > 0:
      current = self.header.__next     
      if self.__size > 1:
        while (current.__next != self.trailer):
          contents = contents + str(current.value) + ", "
          current = current.__next          
      contents = contents + str(current.value) + " "
   contents = contents + "]"
   return contents
    
    

  def __iter__(self):  
    self.__iter_index = self.header.__next
    self.current_node = self.__iter_index
    self.index = 0
    return self


  def __next__(self): 
    if self.index == self.__size:
      raise StopIteration
    return_it = self.current_node.value
    self.current_node = self.current_node.__next
    self.index = self.index + 1
    return return_it

    

if __name__ == '__main__':
    
  #testing append function
  list = Linked_List()
  list.append_element(33)
  list.append_element(444)
  list.append_element(1)
  list.append_element(22)
  list.append_element(444)
  list.append_element(3)
    
  print(list.__len__())
  print(list)
  
  #testing insert element function
  try:
    list.insert_element_at(33,0)
    list.insert_element_at(55,5)
    list.insert_element_at(66,6)
    list.insert_element_at(77,7)
  except IndexError:
    print ('Index Error!')
  print(list.__len__())
  print(list)
  
  #should fail index
  try:
    list.insert_element_at(33,-1)
    list.insert_element_at(33,33)
  except IndexError:
    print ('Index Error!')
  print(list.__len__())
  print(list)
  
  #testing remove element
  try:
    list.remove_element_at(0)
    list.remove_element_at(0)
    list.remove_element_at(2)
    list.remove_element_at(2)
    list.remove_element_at(2)
  except IndexError:
    print ('Index Error!')
  print(list.__len__())
  print(list)
    
  #should fail index
  try:
    list.remove_element_at(-1)
    list.remove_element_at(11)
    list.remove_element_at(5)
  except IndexError:
    print ('Index Error!')
  print(list.__len__())
  print(list)
  
  #testing get element
  try:
    list.get_element_at(0)
    list.get_element_at(2)
    list.get_element_at(4)

  except IndexError:
    print ('Index Error!')
  print(list.__len__())
  print(list)
  
  #should fail index
  try:
    list.get_element_at(-1)
    list.get_element_at(5)
    list.get_element_at(7)
  except IndexError:
    print ('Index Error!')
  print(list.__len__())
  print(list)
  
  #testing rotate left
  list.rotate_left()
  print(list.__len__())
  print(list)
  
  #testing iterator
  print()
  print('testing iterator:')
  for val in list:
    print(val)
  print()

  
  


  
  

  
  
  
