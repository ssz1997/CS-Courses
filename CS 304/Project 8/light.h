//
// CS 304 Session 2
// Assignment 8
// Shouzhuo Sun
// Created on 4/20/2018
//

#ifndef light_h
#define light_h

#include <stdio.h>
#include "rt.h"
#include "obj.h"
#include "vec.h"

typedef struct{
    VEC_T loc;
} LIGHT_T;

COLOR_T illuminate (RAY_T ray, LIGHT_T light, OBJ_T *list,
                    COLOR_T surface_col, VEC_T int_pt, VEC_T normal);

static int shadow_test (LIGHT_T light, OBJ_T *list, VEC_T int_pt);

#endif /* light_h */
