from Deque_Generator import get_deque, LL_DEQUE_TYPE, ARR_DEQUE_TYPE

class Stack:

  def __init__(self):
    # TODO replace pass with your implementation.
    self.__stack = get_deque(LL_DEQUE_TYPE)

  def __str__(self):
    # TODO replace pass with your implementation.
    return str(self.__stack)

  def __len__(self):
    # TODO replace pass with your implementation.
    return len(self.__stack)

  def push(self, val):
    # TODO replace pass with your implementation.
    self.__stack.push_front(val)

  def pop(self):
    # TODO replace pass with your implementation.
    if self.__stack.__len__() == 0:
      return None
    else:
      x = self.__stack.pop_front()
      return x

  def peek(self):
    # TODO replace pass with your implementation.
    if self.__stack.__len__() == 0:
      return None
    else:
      return self.__stack.peek_front()

# Unit tests make the main section unneccessary.
#if __name__ == '__main__':
 
