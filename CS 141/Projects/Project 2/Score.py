class Score(object):

  def __init__(self, player_name):
      self.player_name = player_name
      self.current_score = 0
      self.lives_remaining = 3
      self.current_level = 0
      self.current_multiplier = 1
    
  
  def add_points(self, amount):
      self.current_score = self.current_score + amount * self.current_multiplier
      while 2 ** self.current_level * 10000 <= self.current_score:
          self.current_level = self.current_level + 1
      return self.current_score
          
    # implement this method by adding the number of points
    # specified by amount times the current_multiplier value
    # to the current_score. If the new score value should
    # result in the level changing, then change current_level.
    # return the new value of current_score.
    #TODO replace pass with your implementation
    


  def subtract_points(self, amount):
      self.current_multiplier = 1
      self.current_score = self.current_score- amount
      if self.current_score < 0:
        self.current_score = 0
        self.current_level = 0
      return self.current_score
      while self.current_score < 2 ** (self.current_level - 1) * 100000:
        self.current_level = self.current_level - 1
            
      return self.current_score
    
    # reset current_multipler to 1. subtract the number of
    # points specified by amount from current_score, and update
    # current_level if necessary.
    # return the new value of current_score.
    #TODO replace pass with your implementation
    

  def get_multiplier(self):
      return self.current_multiplier
    
    # return the current value of the multiplier attribute.
    #TODO replace pass with your implementation
    

  def increment_multiplier(self):
      self.current_multiplier = self.current_multiplier + 1
      return self.current_multiplier
    # increase the value of current_multiplier by one.
    # return the new value of current_multiplier.
    #TODO replace pass with your implementation
    

  def get_score(self):
      return self.current_score
    
    # return the current value of the score attribute.
    #TODO replace pass with your implementation
    

  def get_level(self):
      return self.current_level
    
    # return the current value of the level attribute.
    #TODO replace pass with your implementation
    

  def get_lives(self):
      return self.lives_remaining
   
    # return the number of lives remaining.
    #TODO replace pass with your implementation
    

  def lose_life(self):
        self.lives_remaining = self.lives_remaining - 1
        if self.lives_remaining == 0:
            return False
        if self.lives_remaining > 0:
            return True
      
    # decrement the number of lives remaining. If, after you
    # have decremented the lives attribute, that attribute
    # has a positive value, return True, indicating play can
    # continue. If the number is zero, return false,
    # indicating that the game is over.
    #TODO replace pass with your implementation
    

  def gain_life(self):
      self.lives_remaining = self.lives_remaining + 1
      
    # increase the current value of the lives attribute
    # by one.
    #TODO replace pass with your implementation
    

  def __str__(self):
      return 'Player: ' + str(self.player_name) + ', Score: ' +\
             str(self.get_score()) + ', Level: ' + str(self.get_level()) +\
             ', Multiplier: ' + str(self.get_multiplier()) + ', Lives: ' +\
             str(self.get_lives())

                                
      
    # construct and return a string object that represents this
    # score, including the values of all class variables. Note
    # the required format specified in the project description.
    #TODO replace pass with your implementation
    

if __name__ == '__main__':
  #TODO replace pass with your tests for your Score object.


  #a stands for add_points
  #b stands for subtract_points
  #c stands for increment_multiplier
  #d stands for lose_life
  #e stands for gain_life
    a = Score('a')
    a.add_points(10000)
    print (a)

    b = Score('b')
    b.add_points(10000)
    b.subtract_points(100)
    print (b)

    ab = Score('ab')
    ab.add_points(90000)
    ab.subtract_points(10000)
    print (ab)

    c = Score('c')
    c.increment_multiplier()
    print (c)

    d = Score('d')
    d.lose_life()
    d.lose_life()
    d.lose_life()
    print (d)

    e = Score('e')
    e.gain_life()
    print (e)

    ac = Score('ac')
    ac.add_points(20000)
    ac.increment_multiplier()
    print (ac)

    ca = Score('ca')
    ca.increment_multiplier()
    ca.add_points(10000)
    print (ca)

    aca = Score('aca')
    aca.add_points(1)
    aca.increment_multiplier()
    aca.add_points(1)
    print (aca)

    acab = Score('acab')
    acab.add_points(1)
    acab.increment_multiplier()
    acab.add_points(1)
    acab.subtract_points(3)
    print (acab)

    ddda = Score('ddda')
    ddda.lose_life()
    ddda.lose_life()
    ddda.lose_life()
    #ddda.lose_life()
    ddda.add_points(20000)
    print (ddda)
    
    acdbceabdcabe = Score('acdbceabdcabe')
    acdbceabdcabe.add_points(39999)
    acdbceabdcabe.increment_multiplier()
    acdbceabdcabe.lose_life()
    acdbceabdcabe.subtract_points(23105)
    acdbceabdcabe.increment_multiplier()
    acdbceabdcabe.gain_life()
    acdbceabdcabe.add_points(49601603)
    acdbceabdcabe.subtract_points(4625016)
    acdbceabdcabe.lose_life()
    acdbceabdcabe.increment_multiplier()
    acdbceabdcabe.add_points(462952)
    acdbceabdcabe.subtract_points(395061)
    acdbceabdcabe.gain_life()
    print (acdbceabdcabe)
    









  

