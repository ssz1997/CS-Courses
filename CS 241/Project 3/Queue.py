from Deque_Generator import get_deque, LL_DEQUE_TYPE, ARR_DEQUE_TYPE

class Queue:

  def __init__(self):
    # TODO replace pass with your implementation.
    self.__queue = get_deque(LL_DEQUE_TYPE)

  def __str__(self):
    # TODO replace pass with your implementation.
    return str(self.__queue)

  def __len__(self):
    # TODO replace pass with your implementation.
    return len(self.__queue)

  def enqueue(self, val):
    # TODO replace pass with your implementation.
    self.__queue.push_back(val)

  def dequeue(self):
    # TODO replace pass with your implementation.
    if self.__queue.__len__() == 0:
      return None
    else:
      x = self.__queue.pop_front()
      return x

# Unit tests make the main section unneccessary.
#if __name__ == '__main__':
#  pass
  
