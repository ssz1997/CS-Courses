//
// CS 304 Session 2
// Assignment 8
// Shouzhuo Sun
// Created on 4/20/2018
//

#ifndef sphere_h
#define sphere_h

/*
 * Type of sphere, containing its center and radius
 */
typedef struct{
    double ctr_x, ctr_y, ctr_z;        //The position of a sphere
    double radius;                     //The radius of a sphere
} SPHERE_T;

int intersect_sphere (RAY_T ray, SPHERE_T sphere, double *t,
                      VEC_T *int_pt, VEC_T *normal);

#endif /* sphere_h */
