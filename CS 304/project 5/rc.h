//
//  CS 304 Session 2
//  Assignment5
//
//  Created by Shouzhuo Sun on 3/22/18.
//  

#ifndef rc_h
#define rc_h
#include "vec.h"


/*
 * Type of ray, containing its origin and direction
 */
typedef struct{
    double origin_x;
    double origin_y;
    double origin_z;    //The camera is at position (0, 0, 0)
    VEC_T direction;                              //The derection is a 3-dimentional vector
} RAY_T;

/*
 * Type of color, containing three components of a color
 */
typedef struct{
    double R, G, B;       //red, green, and blue
} COLOR_T;

/*
 * Type of sphere, containing its center and radius
 */
typedef struct{
    double ctr_x, ctr_y, ctr_z;        //The position of a sphere
    double radius;                     //The radius of a sphere
} SPHERE_T;

/*
 * Linked list; each node contains a sphere, the color of the sphere, and a pointer to the next node
 */
typedef struct NODE{
    SPHERE_T sphere;
    COLOR_T color;
    struct NODE *next;
} OBJ_T;
#endif /* rc_h */
