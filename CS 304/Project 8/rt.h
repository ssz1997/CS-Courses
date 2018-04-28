//
// CS 304 Session 2
// Assignment 8
// Shouzhuo Sun
// Created on 4/20/2018
//

#ifndef rt_h
#define rt_h
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

#endif /* rt_h */
