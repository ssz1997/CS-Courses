//
//  CS 304 Session 2
//  Assignment5
//
//  Created by Shouzhuo Sun on 3/22/18.
//  

#ifndef vec_h
#define vec_h

/*
 * Type of vector
 */
typedef struct{
    double x, y, z;    //three components of a vector
}VEC_T;

VEC_T normalize(VEC_T v);

double dot(VEC_T v1, VEC_T v2);

#endif /* vec_h */
