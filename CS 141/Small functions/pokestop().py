from random import randint

def pokestop():
    y = randint(1,100)
    if 1 <= y <= 40:
        return 'Poke Ball'
    if 41 <= y <= 62:
        return 'Greant Ball'
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

a = pokestop()

print(a)
