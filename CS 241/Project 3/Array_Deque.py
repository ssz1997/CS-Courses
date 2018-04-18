from Deque import Deque

class Array_Deque(Deque):

  def __init__(self):
    # capacity starts at 1; we will grow on demand.
    self.__capacity = 1
    self.__contents = [None] * self.__capacity
    self.__front = 0
    self.__back = 0
    self.__size = 0
    # TODO replace pass with any additional initializations you need.
    
    
  def __str__(self):
    
    if self.__size == 0:
      return '[ ]'
    else: 
      if self.__front > self.__back:
           
        string = '[ ' 
        for n in self.__contents[self.__front:self.__capacity - 1]:
          string = string + str(n) + ', ' 
        string = string + str(self.__contents[self.__capacity - 1])
    
        for n in self.__contents[:self.__back + 1]:
          string = string + ', ' + str(n)
        string = string + ' ]'
        return string
        
      elif self.__front == self.__back: 
        return '[ ' + str(self.__contents[self.__front]) + ' ]'
        
      else: #front is lower index 
        string = '[ '     
        for n in self.__contents[self.__front:self.__back]:
          string = string + str(n) + ', '
        string = string + str(self.__contents[self.__back]) + ' ]'
        return string  


    
  def __len__(self):
    # TODO replace pass with an implementation that returns the number of
    # items in the deque. This method must run in constant time.
    return self.__size
            
  def __grow(self):
    # TODO replace pass with an implementation that doubles the capacity
    # and positions existing items in the deque starting in cell 0 (why is
    # necessary?)
    while self.__front != 0:
      value = self.__contents[self.__capacity-1]
      for n in range(self.__capacity - 1 , 0, -1):
        self.__contents[n] = self.__contents[n-1]
      self.__contents[0] = value
      self.__front = (self.__front + 1) % self.__capacity
      self.__back = (self.__back + 1) % self.__capacity
    self.__contents = self.__contents + [None] * self.__capacity
    self.__capacity = self.__capacity * 2

  def push_front(self, val):
    # TODO replace pass with your implementation, growing the array before
    # pushing if necessary.
    if self.__contents[self.__front] == None:
      self.__contents[self.__front] = val
    elif self.__size == self.__capacity:          
      self.__grow()     
    self.__contents[(self.__front - 1 + self.__capacity) % self.__capacity] = val
    self.__front = (-1 + self.__front + self.__capacity) % self.__capacity
    self.__size = self.__size + 1
    
    
  def pop_front(self):
    # TODO replace pass with your implementation. Do not reduce the capacity.
    if self.__size == 0:
      return None
    else:
      x = self.__contents[self.__front]
      self.__contents[self.__front] = None
      self.__front = (self.__front + 1 + self.__capacity) % self.__capacity
      self.__size = self.__size - 1
      return x
  
  def peek_front(self):
    if self.__size == 0:
      return None
    else:
      return self.__contents[self.__front]
    
  def push_back(self, val):
    if self.__contents[self.__back] == None:
      self.__contents[self.__back] = val
    elif self.__size == self.__capacity:
      self.__grow()
    self.__contents[(self.__back + 1 + self.__capacity) % self.__capacity] = val
    self.__back = (self.__back + 1 + self.__capacity) % self.__capacity
    self.__size = self.__size + 1
  
  def pop_back(self):
    # TODO replace pass with your implementation. Do not reduce the capacity.
    if self.__size == 0:
      return None
    else:
      x = self.__contents[self.__back]
      self.__contents[self.__back] = None
      self.__back = (self.__back - 1 - self.__capacity) % self.__capacity
      self.__size = self.__size - 1
      return x
    

  def peek_back(self):
    # TODO replace pass with your implementation.
    if self.__size == 0:
      return None
    else:
      return self.__contents[self.__back]

# No main section is necessary. Unit tests take its place.
#if __name__ == '__main__':
  #pass
