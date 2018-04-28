//
// CS 304 Session 2
// Assignment 8
// Shouzhuo Sun
// Created on 4/20/2018
//

#ifndef obj_h
#define obj_h
#include "rt.h"
#include "sphere.h"

/*
 * Linked list; each node contains a sphere, the color of the sphere, and a pointer to the next node
 */
typedef struct NODE{
    SPHERE_T sphere;
    COLOR_T color;
    struct NODE *next;
} OBJ_T;

#endif /* obj_h */
