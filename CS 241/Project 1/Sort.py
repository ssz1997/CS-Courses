import time
import random

def insertion_sort(arr):
    for k in range(1,len(arr)):
      item_to_place = arr[k]
      j = k
      while j> 0 and arr[j-1] > item_to_place:
          arr[j] = arr[j-1]
          j = j-1
          arr[j] = item_to_place
    return arr



def selection_sort(arr):
    for k in range(0, len(arr)):
        smallest = arr[k]
        j = k
        m = 0
        while j in range(k, len(arr)):
            if smallest > arr[j]:
                smallest = arr[j]
                m = j
            j = j + 1
        swap = arr[k]
        arr[k] = smallest
        arr[m] = swap
    return arr






if __name__ == '__main__':
    arr = [None] * 1000
    i = 0
    while i in range(0,len(arr)):
        arr[i] = random.randint(-10000,10000)
        i = i + 1
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print ("1000 Random Insertion: " + "{:.20f}".format(end-start))
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print ("1000 Random Selection: " + "{:.20f}".format(end-start))
    
    arr = [None] * 2500
    i = 0
    while i in range(0,len(arr)):
        arr[i] = random.randint(-10000,10000)
        i = i + 1
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 2500 Random Insertion: " + "{:.20f}".format(end-start))
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 2500 Random Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 5000
    i = 0
    while i in range(0,len(arr)):
        arr[i] = random.randint(-10000,10000)
        i = i + 1
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 5000 Random Insertion: " + "{:.20f}".format(end-start))
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 5000 Random Selection: " + "{:.20f}".format(end-start))
    
    arr = [None] * 7500
    i = 0
    while i in range(0,len(arr)):
        arr[i] = random.randint(-10000,10000)
        i = i + 1
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 7500 Random Insertion: " + "{:.20f}".format(end-start))
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 7500 Random Selection: " + "{:.20f}".format(end-start))
    
    arr = [None] * 10000
    i = 0
    while i in range(0,len(arr)):
        arr[i] = random.randint(-10000,10000)
        i = i + 1
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 10000 Random Insertion: " + "{:.20f}".format(end-start))
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 10000 Random Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 1000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 1000 Increasing Insertion: " + "{:.20f}".format(end-start))

    arr = [None] * 2500
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 2500 Increasing Insertion: " + "{:.20f}".format(end-start))

    arr = [None] * 5000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 5000 Increasing Insertion: " + "{:.20f}".format(end-start))

    arr = [None] * 7500
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 7500 Increasing Insertion: " + "{:.20f}".format(end-start))

    arr = [None] * 10000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 10000 Increasing Insertion: " + "{:.20f}".format(end-start))

    arr = [None] * 1000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 1000 Decreasing Insertion: " + "{:.20f}".format(end-start))

    arr = [None] * 2500
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 2500 Decreasing Insertion: " + "{:.20f}".format(end-start))
 
    arr = [None] * 5000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 5000 Decreasing Insertion: " + "{:.20f}".format(end-start))

    arr = [None] * 7500
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 7500 Decreasing Insertion: " + "{:.20f}".format(end-start))

    arr = [None] * 10000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    insertion_sort(arr)
    end = time.clock()
    print (" 10000 Decreasing Insertion: " + "{:.20f}".format(end-start))



    arr = [None] * 1000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 1000 Increasing Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 2500
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 2500 Increasing Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 5000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 5000 Increasing Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 7500
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 7500 Increasing Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 10000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a + 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 10000 Increasing Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 1000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 1000 Decreasing Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 2500
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 2500 Decreasing Selection: " + "{:.20f}".format(end-start))
 
    arr = [None] * 5000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 5000 Decreasing Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 7500
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 7500 Decreasing Selection: " + "{:.20f}".format(end-start))

    arr = [None] * 10000
    i = 0
    a = 0
    while i in range(0,len(arr)):
        a = a - 2
        arr[i] = a
        i = i + 1       
    start = time.clock()
    selection_sort(arr)
    end = time.clock()
    print (" 10000 Decreasing Selection: " + "{:.20f}".format(end-start))
