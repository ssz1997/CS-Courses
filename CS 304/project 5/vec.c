//
//  CS 304 Session 2
//  Assignment5
//
//  Created by Shouzhuo Sun on 3/22/18.
//  

#include <stdio.h>
#include <math.h>
#include "vec.h"

/*
 * Normalize a vector for easier computation for intersect_sphere
 */
VEC_T normalize(VEC_T v){
    VEC_T normalized_v;
    double value = sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
    normalized_v.x = v.x / value;
    normalized_v.y = v.y / value;
    normalized_v.z = v.z / value;
    return normalized_v;
}

/*
 * Compute the dot product for easier computation for intersect_sphere
 */
double dot(VEC_T v1, VEC_T v2){
    double result = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    return result;
}
