import unittest
from Deque_Generator import get_deque, LL_DEQUE_TYPE, ARR_DEQUE_TYPE
from Stack import Stack
from Queue import Queue

class DSQTester(unittest.TestCase):
  
  def setUp(self):
    # Run your tests with each deque type to ensure that
    # they behave identically.
    self.__deque = get_deque(ARR_DEQUE_TYPE)
    self.__stack = Stack()
    self.__queue = Queue()

  def test_empty_deque(self):
    self.assertEqual('[ ]', str(self.__deque))
  
#FRONT IMPLEMENTATIONS

  def test_front_pop_empty_deque(self):
    returned = self.__deque.pop_front()
    self.assertEqual(None, returned)

  def test_back_pop_empty_deque(self):
    returned = self.__deque.pop_back()
    self.assertEqual(None, returned)
    
  def test_front_peek_empty_deque(self): 
    returned = self.__deque.peek_front()
    self.assertEqual(None, returned)
        
  def test_front_push_and_peek_deque(self):
    self.__deque.push_front(1)
    self.assertEqual('[ 1 ]', str(self.__deque))
    returned = self.__deque.peek_front()
    self.assertEqual(1, returned)
    self.__deque.pop_front()
    self.assertEqual('[ ]' , str(self.__deque))

  def test_FRONT_fill_up_deque_to_capacity(self):
    self.__deque.push_front(1)
    self.__deque.push_front(2)
    self.__deque.push_front(3)
    self.__deque.push_front(4)
    self.assertEqual('[ 4, 3, 2, 1 ]', str(self.__deque))
    
  def test_front_popping_of_full_deque(self):
    self.__deque.push_front(1)
    self.__deque.push_front(2)
    self.__deque.push_front(3)
    self.__deque.push_front(4)
    self.__deque.pop_front()
    self.assertEqual('[ 3, 2, 1 ]', str(self.__deque))
    
  def test_front_pushing_onto_full_deque(self):
    self.__deque.push_front(1)
    self.__deque.push_front(2)
    self.__deque.push_front(3)
    self.__deque.push_front(4)
    self.__deque.pop_front()
    self.__deque.push_front(5)
    self.assertEqual('[ 5, 3, 2, 1 ]', str(self.__deque))  
    
  def test_front_pushing_on_deque_past_capacity(self):
    self.__deque.push_front(1)
    self.__deque.push_front(2)
    self.__deque.push_front(3)
    self.__deque.push_front(4)
    self.__deque.push_front(5)
    self.assertEqual('[ 5, 4, 3, 2, 1 ]', str(self.__deque))

#BACK IMPLEMENTATIONS

  def test_back_peek_empty_deque(self): 
    returned = self.__deque.peek_back()
    self.assertEqual(None, returned)
  
  def test_back_push_and_peek_deque(self):
    self.__deque.push_back(1)
    self.assertEqual('[ 1 ]', str(self.__deque))
    returned = self.__deque.peek_back()
    self.assertEqual(1, returned)
    self.__deque.pop_back()
    self.assertEqual('[ ]' , str(self.__deque))
  
  def test_BACK_fill_up_deque_to_capacity(self):
    self.__deque.push_back(1)
    self.__deque.push_back(2)
    self.__deque.push_back(3)
    self.__deque.push_back(4)
    self.assertEqual('[ 1, 2, 3, 4 ]', str(self.__deque))
    
  def test_back_popping_of_full_deque(self):
    self.__deque.push_back(1)
    self.__deque.push_back(2)
    self.__deque.push_back(3)
    self.__deque.push_back(4)
    self.__deque.pop_back()
    self.assertEqual('[ 1, 2, 3 ]', str(self.__deque))  
    
  def test_back_pushing_onto_full_deque(self):
    self.__deque.push_back(1)
    self.__deque.push_back(2)
    self.__deque.push_back(3)
    self.__deque.push_back(4)
    self.__deque.pop_back()
    self.__deque.push_back(5)
    self.assertEqual('[ 1, 2, 3, 5 ]', str(self.__deque))   
  
  def test_back_pushing_on_deque_past_capacity(self):
    self.__deque.push_back(1)
    self.__deque.push_back(2)
    self.__deque.push_back(3)
    self.__deque.push_back(4)
    self.__deque.push_back(5)
    self.assertEqual('[ 1, 2, 3, 4, 5 ]', str(self.__deque))

#COMBINATIONS
    
  def test_combination_of_back_and_front_operations(self):
    self.__deque.push_front(5)
    self.__deque.push_front(4)
    self.__deque.push_front(3)
    self.__deque.push_front(2)
    self.__deque.push_front(1)
    self.__deque.push_back(6)
    self.__deque.push_back(7)
    self.__deque.push_back(8)
    self.__deque.push_back(9)
    self.__deque.push_back(10)
    self.assertEqual('[ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]', str(self.__deque))
    
  def test_front_and_back_peek_of_combinations(self):
    self.__deque.push_front(5)
    self.__deque.push_front(4)
    self.__deque.push_front(3)
    self.__deque.push_front(2)
    self.__deque.push_front(1)
    self.__deque.push_back(6)
    self.__deque.push_back(7)
    self.__deque.push_back(8)
    self.__deque.push_back(9)
    self.__deque.push_back(10)
    self.assertEqual('[ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]', str(self.__deque))
    returned = self.__deque.peek_front()
    self.assertEqual(1, returned)
    returned = self.__deque.peek_back()
    self.assertEqual(10, returned)
    
  def test_front_and_back_pop_of_combinations(self):
    self.__deque.push_front(5)
    self.__deque.push_front(4)
    self.__deque.push_front(3)
    self.__deque.push_front(2)
    self.__deque.push_front(1)
    self.__deque.push_back(6)
    self.__deque.push_back(7)
    self.__deque.push_back(8)
    self.__deque.push_back(9)
    self.__deque.push_back(10)
    self.__deque.pop_front()
    self.__deque.pop_back()
    self.assertEqual('[ 2, 3, 4, 5, 6, 7, 8, 9 ]', str(self.__deque))
    returned = self.__deque.peek_front()
    self.assertEqual(2, returned)
    returned = self.__deque.peek_back()
    self.assertEqual(9, returned)
  
  def test_front_and_back_pop_until_empty(self):
    self.__deque.push_front(5)
    self.__deque.push_front(4)
    self.__deque.push_front(3)
    self.__deque.push_front(2)
    self.__deque.push_front(1)
    self.__deque.push_back(6)
    self.__deque.push_back(7)
    self.__deque.push_back(8)
    self.__deque.push_back(9)
    self.__deque.push_back(10)
    self.__deque.pop_front()
    self.__deque.pop_back()
    self.__deque.pop_front()
    self.__deque.pop_back()
    self.__deque.pop_front()
    self.__deque.pop_back()
    self.__deque.pop_front()
    self.__deque.pop_back()
    self.__deque.pop_front()
    self.__deque.pop_back()
    self.assertEqual('[ ]', str(self.__deque))
    returned = self.__deque.peek_front()
    self.assertEqual(None, returned)
    returned = self.__deque.peek_back()
    self.assertEqual(None, returned)

#QUEUE
    
  def test_empty_queue(self):
    self.assertEqual('[ ]', str(self.__queue))

  def test_dequeue_empty_queue(self):
    returned = self.__queue.dequeue()
    self.assertEqual(None, returned)
    self.assertEqual('[ ]', str(self.__queue))
  
  def test_queue_enqueue_one(self):
    self.__queue.enqueue(0)
    self.assertEqual('[ 0 ]', str(self.__queue))

  def test_queue_enqueue_two(self):
    self.__queue.enqueue(0)
    self.__queue.enqueue(1)    
    self.assertEqual('[ 0, 1 ]', str(self.__queue))

  def test_queue_enqueue_four(self):
    self.__queue.enqueue(0)
    self.__queue.enqueue(1)
    self.__queue.enqueue(2)
    self.__queue.enqueue(3)
    self.assertEqual('[ 0, 1, 2, 3 ]', str(self.__queue))

  def test_queue_enqueue_one_dequeue_one(self):
    self.__queue.enqueue(0)
    returned = self.__queue.dequeue()
    self.assertEqual(0, returned)
    self.assertEqual('[ ]', str(self.__queue))
                  
  def test_queue_enqueue_two_dequeue_one(self):
    self.__queue.enqueue(0)
    self.__queue.enqueue(1)
    returned = self.__queue.dequeue()
    self.assertEqual(0, returned)
    self.assertEqual('[ 1 ]', str(self.__queue))

  def test_queue_enqueue_one_dequeue_one_enone_deone(self):
    self.__queue.enqueue(0)
    returned = self.__queue.dequeue()
    self.assertEqual(0, returned)
    self.__queue.enqueue(1)
    returned1 = self.__queue.dequeue()
    self.assertEqual(1, returned1)
    self.assertEqual('[ ]', str(self.__queue))

  def test_queue_enqueue_two_dequeue_two(self):
    self.__queue.enqueue(0)
    self.__queue.enqueue(1)
    returned = self.__queue.dequeue()
    self.assertEqual(0, returned)
    returned1 = self.__queue.dequeue()
    self.assertEqual(1, returned1)
    self.assertEqual('[ ]', str(self.__queue))

  def test_queue_enqueue_four_dequeue_two(self):
    self.__queue.enqueue(0)
    self.__queue.enqueue(1)
    self.__queue.enqueue(2)
    self.__queue.enqueue(3)
    returned = self.__queue.dequeue()
    self.assertEqual(0, returned)
    returned1 = self.__queue.dequeue()
    self.assertEqual(1, returned1)
    self.assertEqual('[ 2, 3 ]', str(self.__queue))

  def test_queue_enqueue_five_dequeue_two(self):
    self.__queue.enqueue(0)
    self.__queue.enqueue(1)
    self.__queue.enqueue(2)
    self.__queue.enqueue(3)
    self.__queue.enqueue(4)
    returned = self.__queue.dequeue()
    self.assertEqual(0, returned)
    returned1 = self.__queue.dequeue()
    self.assertEqual(1, returned1)
    self.assertEqual('[ 2, 3, 4 ]', str(self.__queue))

#STACK

  def test_empty_stack(self):
    self.assertEqual('[ ]', str(self.__stack))

  def test_pop_empty_stack(self):
    returned = self.__stack.pop()
    self.assertEqual(None, returned)
    self.assertEqual('[ ]', str(self.__stack))

  def test_peek_empty_stack(self):
    returned = self.__stack.peek()
    self.assertEqual(None, returned)
    self.assertEqual('[ ]', str(self.__stack))

  def test_stack_push_one(self):
    self.__stack.push(0)
    self.assertEqual('[ 0 ]', str(self.__stack))

  def test_stack_push_one_peek(self):
    self.__stack.push(0)
    returned = self.__stack.peek()
    self.assertEqual(0, returned)
    self.assertEqual('[ 0 ]', str(self.__stack))

  def test_stack_push_one_pop_one(self):
    self.__stack.push(0)
    returned = self.__stack.pop()
    self.assertEqual(0, returned)
    self.assertEqual('[ ]', str(self.__stack))
    
  def test_stack_push_two(self):
    self.__stack.push(0)
    self.__stack.push(1)
    self.assertEqual('[ 1, 0 ]', str(self.__stack))

  def test_stack_push_two_peek(self):
    self.__stack.push(0)
    self.__stack.push(1)
    returned = self.__stack.peek()
    self.assertEqual(1, returned)
    self.assertEqual('[ 1, 0 ]', str(self.__stack))

  def test_stack_push_one_pop_one_push_one_pop_one(self):
    self.__stack.push(0)
    returned = self.__stack.pop()
    self.assertEqual(0, returned)
    self.__stack.push(1)
    returned1 = self.__stack.pop()
    self.assertEqual(1, returned1)
    self.assertEqual('[ ]', str(self.__stack))

  def test_stack_push_two_pop_one_peek(self):
    self.__stack.push(0)
    self.__stack.push(1)
    returned = self.__stack.pop()
    self.assertEqual(1, returned)
    returned1 = self.__stack.peek()
    self.assertEqual(0, returned1)
    self.assertEqual('[ 0 ]', str(self.__stack))

  def test_stack_push_two_pop_two(self):
    self.__stack.push(0)
    self.__stack.push(1)
    returned = self.__stack.pop()
    self.assertEqual(1, returned)
    returned1 = self.__stack.pop()
    self.assertEqual(0, returned1)
    self.assertEqual('[ ]', str(self.__stack))

  def test_stack_push_four(self):
    self.__stack.push(0)
    self.__stack.push(1)
    self.__stack.push(2)
    self.__stack.push(3)
    self.assertEqual('[ 3, 2, 1, 0 ]', str(self.__stack))

  def test_stack_push_four_peek(self):
    self.__stack.push(0)
    self.__stack.push(1)
    self.__stack.push(2)
    self.__stack.push(3)
    returned = self.__stack.peek()
    self.assertEqual(3, returned)
    self.assertEqual('[ 3, 2, 1, 0 ]', str(self.__stack))
    
  def test_stack_push_four_pop_two_peek(self):
    self.__stack.push(0)
    self.__stack.push(1)
    self.__stack.push(2)
    self.__stack.push(3)
    returned = self.__stack.pop()
    self.assertEqual(3, returned)
    returned1 = self.__stack.pop()
    self.assertEqual(2, returned1)
    returned2 = self.__stack.peek()
    self.assertEqual(1, returned2)
    self.assertEqual('[ 1, 0 ]', str(self.__stack))
    
if __name__ == '__main__': 
  unittest.main()

