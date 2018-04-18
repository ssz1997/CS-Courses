def print_isosceles(h):
    i = 0
    while i < h:
        i = i + 1
        print (' ' * (h-i), '*' * (2*i-1))




def median_of_three(a,b,c):
    if a <= b <= c or c < b < a:
        return b
    if a < c < b or b <=c < a:
        return c
    if c <= a <b or b < a <= c or c < a == b:
        return a




def point_is_in_circle(origin_x, origin_y, diameter, x, y):
    d_square = (origin_x + diameter / 2 - x ) ** 2 + (origin_y - diameter / 2 - y ) ** 2
    if d_square > diameter ** 2:
        return False
    if d_square < diameter ** 2:
        return True




from random import randint

def pokestop():
    y = randint(1,100)
    if 1 <= y <= 40:
        return 'Poke Ball'
    if 41 <= y <= 62:
        return 'Great Ball'
    if 63 <= y <= 74:
        return 'Razz Berry'
    if 75 <= y <= 86:
        return 'Potion'
    if 87 <= y <= 93:
        return 'Super Potion'
    if 94 <= y <= 97:
        return 'Hyper Potion'
    if 98 <= y <= 99:
        return 'Revive'
    if y == 100:
        return 'Egg'



i = 3

while i < 5:
    

    print ('Here is the menu!')
    print ('1. print_isosceles(h)')
    print ('2. median_of_three(a,b,c)')
    print ('3. point_is_in_circle(origin_x, origin_y, diameter, x, y)')
    print ('4. pokestop()')
    print ('5. exit the program.')
    pro_choose = int(input('which function you would like to see or quit now? Please type in the number'))

    if pro_choose == 1:
        h = int(input('how large you want the triangle to be? Type in a positive integer.'))
        print_isosceles(h)

    if pro_choose == 2:
        a = int(input('Please enter a number.'))
        b = int(input('Please enter another number.'))
        c = int(input('Please enter another number.'))
 
        m = median_of_three(a,b,c)

        print ('The median of', a,',',b,',',c,', is',m)

    if pro_choose == 3:
        origin_x = float(input('Please enter the x for the upper left hand corner of the square.'))
        origin_y = float(input('Please enter the y for the upper left hand corner of the square.'))
        diameter = float(input('Please enter the diameter of the circle.'))
        x = float(input('Please enter the x for the point you want to check.'))
        y = float(input('Please enter the y for the point you want to check.'))

        print (point_is_in_circle(origin_x, origin_y, diameter, x, y))

    if pro_choose == 4:
        p = pokestop()
        print (p)
            

    if pro_choose == 5:
        i = i + 100

    if pro_choose < 0 or pro_choose > 5:
        print ('Please reselct a number, because there are only four functions and an option of exit!')
























                 
    
  
