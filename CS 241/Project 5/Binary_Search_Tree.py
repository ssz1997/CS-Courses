class Binary_Search_Tree:
  # TODO.I have provided the public method skeletons. You will need
  # to add private methods to support the recursive algorithms
  # discussed in class

  class __BST_Node:
    # TODO The Node class is private. You may add any attributes and
    # methods you need. Recall that attributes in an inner class
    # must be public to be reachable from the the methods.

    def __init__(self, value):
      self.value = value
      self._height = 1
      self._left = None
      self._right = None

  def __init__(self):
    self.__root = None


  def insert_element(self, value):
    self.__root = self._inserthelper(value, self.__root)

  def _inserthelper(self, value, curoot):
  	#recusrive private method for insert_element

  	#base case:
    if curoot == None:
        curoot = self.__BST_Node(value)
        return curoot
    elif curoot.value == value:
        raise ValueError
    elif curoot.value > value:
        curoot._left = self._inserthelper(value, curoot._left)
        if (curoot._left != None) and (curoot._right != None):
            curoot._height = max(curoot._left._height, curoot._right._height) + 1
        elif (curoot._left == None) and (curoot._right != None):
            curoot._height = curoot._right._height + 1
        else:
            curoot._height = curoot._left._height + 1

        return self._balance(curoot)

    elif curoot.value < value:
        curoot._right = self._inserthelper(value, curoot._right)
        if (curoot._left != None) and (curoot._right != None):
            curoot._height = max(curoot._left._height, curoot._right._height) + 1
        elif (curoot._left == None) and (curoot._right != None):
            curoot._height = curoot._right._height + 1
        else:
            curoot._height = curoot._left._height + 1
        return self._balance(curoot)


  def remove_element(self, value):
    self.__root = self._removehelper(value, self.__root)

  def _removehelper(self, value, curoot):
    #base case:
    if curoot == None:
        #this case checks value is in tree
        raise ValueError
    elif curoot.value == value:
        if curoot._left != None and curoot._right != None:
            smallest_local = self._findsmallest(curoot._right)
            self.remove_element(smallest_local)
            curoot.value = smallest_local
            return self._balance(curoot)
        elif curoot._left == None and curoot._right != None:
            curoot = curoot._right
            return curoot
        else:
            curoot = curoot._left
            return curoot
    elif curoot.value > value:
        curoot._left = self._removehelper(value, curoot._left)
        if (curoot._left != None) and (curoot._right != None):
            curoot._height = max(curoot._left._height, curoot._right._height) + 1
        elif (curoot._left == None) and (curoot._right != None):
            curoot._height = curoot._right._height + 1
        elif (curoot._left != None) and (curoot._right == None):
            curoot._height = curoot._left._height + 1
        else:
            curoot._height = 1
        return self._balance(curoot)
    elif curoot.value < value:
        curoot._right = self._removehelper(value, curoot._right)
        if (curoot._left != None) and (curoot._right != None):
            curoot._height = max(curoot._left._height, curoot._right._height) + 1
        elif (curoot._left == None) and (curoot._right != None):
            curoot._height = curoot._right._height + 1
        elif (curoot._left != None) and (curoot._right == None):
            curoot._height = curoot._left._height + 1
        else:
            curoot._height = 1
        return self._balance(curoot)

  def _findsmallest(self, curoot):

    if curoot._left == None:
      smallest = curoot.value
    else:
      smallest = self._findsmallest(curoot._left)
    return smallest

  def _balance(self,curoot):
    newroot = None
    if curoot._right == None and curoot._left != None:
      if curoot._height > 1:
        if curoot._left._right == None and curoot._left._left != None:
          #rotate right
          newroot = curoot._left
          temp = newroot._right
          newroot._right = curoot
          curoot._left = temp
          curoot._height = curoot._height - 2
        elif curoot._left._left == None and curoot._left._right != None:
          #double rotation
            #small rotation to left
          newroot = curoot._left._right
          temp = newroot._left
          newroot._left = curoot._left
          curoot._left._right = temp
          curoot._left = newroot
            #larger rotation to right
          temp = newroot._right
          newroot._right = curoot
          curoot._left = temp
          newroot._height = newroot._height + 1
          curoot._height = curoot._height - 2
          newroot._left._height = newroot._left._height - 1

        elif curoot._left._left != None and curoot._left._right != None:
          newroot = curoot._left
          temp = newroot._right
          newroot._right = curoot
          curoot._left = temp
          curoot._height = curoot._height - 1
          newroot._height = newroot._height + 1

        else:
          #no rotation
          newroot = curoot
      else:
        #no rotation
        newroot = curoot

    elif curoot._left == None and curoot._right != None:
      if curoot._height > 1:
          if curoot._right._left == None and curoot._right._right != None:
            #rotate left
            newroot = curoot._right
            temp = curoot._right._left
            curoot._right._left = curoot
            curoot._right = temp
            curoot._height = curoot._height - 2

          elif curoot._right._right == None and curoot._right._left != None:
            #double rotation
              #small rotation to right
            newroot = curoot._right._left
            temp = newroot._right
            newroot._right = curoot._right
            curoot._right._left = temp
            curoot._right = newroot
              #larger rotation to left
            temp = newroot._left
            newroot._left = curoot
            curoot._right = temp
            newroot._height = newroot._height + 1
            curoot._height = curoot._height - 2
            newroot._right._height = newroot._right._height - 1

          elif curoot._right._right != None and curoot._right._left != None:
            newroot = curoot._right
            temp = newroot._left
            newroot._left = curoot
            curoot._right = temp
            newroot._height += 1
            curoot._height = curoot._height - 1

          else:
            #no rotation
            newroot = curoot
      else:
          #no rotation
          newroot = curoot

    elif curoot._left != None and curoot._right != None:
      if curoot._right._height - curoot._left._height > 1:
        if curoot._right._right._height - curoot._right._left._height >= 0:
          #rotate left
          newroot = curoot._right
          #print(newroot.value)
          temp = curoot._right._left
          curoot._right._left = curoot
          curoot._right = temp
          curoot._height = curoot._height - 2
          #print(newroot.value)
        else:
          #double rotation
            #small rotation to right
          newroot = curoot._right._left
          temp = newroot._right
          newroot._right = curoot._right
          curoot._right._left = temp
          curoot._right = newroot

            #larger rotation to left
          temp = newroot._left
          newroot._left = curoot
          curoot._right = temp
          newroot._height = newroot._height + 1
          curoot._height = curoot._height - 2
          newroot._right._height = newroot._right._height - 1

      elif curoot._right._height - curoot._left._height < -1:
        if curoot._left._right._height - curoot._left._left._height <= 0:
          #rotate right
          newroot = curoot._left
          temp = curoot._left._right
          curoot._left._right = curoot
          curoot._left = temp
          curoot._height = curoot._height - 2

        else:
          #double rotation
            #small rotation to left
          newroot = curoot._left._right
          temp = newroot._left
          newroot._left = curoot._left
          curoot._left._right = temp
          curoot._left = newroot
            #larger rotation to right
          temp = newroot._right
          newroot._right = curoot
          curoot._left = temp
          newroot._height = newroot._height + 1
          curoot._height = curoot._height - 2
          newroot._left._height = newroot._left._height - 1

      else:
          #no rotation
          newroot = curoot

    else:
      newroot = curoot

    return newroot

  def in_order(self):
    if self.__root is None:
      return "[ ]"
    else:
      output = "[ "
      output += self._in_order_helper(self.__root)
      output = output[:-2]
      output += " ]"
      return output

  def _in_order_helper(self, curr): #method traverses down tree with left,parent,right fashion
    if curr != None:
        left = self._in_order_helper(curr._left)
        right = self._in_order_helper(curr._right)
        return left + str(curr.value) + ", " + right
    return ""
  def to_list(self):
      if self.__root is None:
          return []
      else:
          output = []
          output += (self._to_list_helper(self.__root))
          return output
  def _to_list_helper(self, curr): #method traverses down tree with left,parent,right fashion
      if curr != None:
        left = self._to_list_helper(curr._left)
        right = self._to_list_helper(curr._right)
        return left + [curr.value] + right
      return []

  def pre_order(self):
    if self.__root is None: #empty tree case
      return "[ ]"
    else:
      output = "[ "
      output += self._pre_order_helper(self.__root)
      output = output[:-2]
      output += " ]"
      return output

  def _pre_order_helper(self, curr): #method traverses down tree with parent, left, right fashion
    if curr is not None:
      return str(curr.value) + ", " + self._pre_order_helper(curr._left) + self._pre_order_helper(curr._right)
    else:
      return ""

  def post_order(self):
    if self.__root is None:
      return "[ ]"
    else:
      output = "[ "
      output += self._post_order_helper(self.__root)
      output = output[:-2]
      output += " ]"
      return output

  def _post_order_helper(self, curr): #method traverses down tree with left, right, parent fashion
    if curr is not None:
      return self._post_order_helper(curr._left) + self._post_order_helper(curr._right) + str(curr.value) + ", "
    else:
      return ""

  def get_height(self):
    if self.__root == None:
      return 0
    else:
      return self.__root._height



  def __str__(self):
    return self.in_order()

#if __name__ == '__main__':
