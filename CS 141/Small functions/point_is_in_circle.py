def point_is_in_circle(origin_x, origin_y, diameter, x, y):
    d_square = (origin_x + diameter / 2 - x ) ** 2 + (origin_y - diameter / 2 - y ) ** 2
    if d_square > diameter ** 2:
        return False
    if d_square < diameter ** 2:
        return True
