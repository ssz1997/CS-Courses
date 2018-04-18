import threading, time, random

class Firecrackers(object):
    

    def __light_fuse(self,i):
        j = random.randint(-999,999)
        print ('Lighting firecracker #%d' %i)
        time.sleep(2+j/1000)
        
        print ('Firecracker #%d'%i, 'goes boom in', str(2+j/1000), 'seconds.')

   

    def go(self):
        for i in range(5):
            interrupt_thread = threading.Thread(target=self.__light_fuse, args=(i,))
           

            interrupt_thread.start()
        
        
      
        
        
if (__name__ == '__main__'):
    ex = Firecrackers()
    ex.go()

    

