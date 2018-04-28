//
// CS 304 Session 2
// Assignment 8
// Shouzhuo Sun
// Created on 4/20/2018
//

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "rt.h"
#include "obj.h"
#include "sphere.h"
#include "vec.h"


/*
 * Computes intersection of ray and sphere; returns 1 if intersection, 0 otherwise; t is set to
 * distance from camera to sphere intersection
 */
int intersect_sphere (RAY_T ray, SPHERE_T sphere, double *t, VEC_T *int_pt, VEC_T *normal){
    double a = 1, b, c, delta, n, distance;
    b = 2 * (ray.direction.x * (ray.origin_x - sphere.ctr_x)+
             ray.direction.y * (ray.origin_y - sphere.ctr_y)+
             ray.direction.z * (ray.origin_z - sphere.ctr_z));
    c = sphere.ctr_x * sphere.ctr_x + sphere.ctr_y * sphere.ctr_y + sphere.ctr_z * sphere.ctr_z - sphere.radius * sphere.radius;
    delta = b * b - 4 * a * c;

    //There is solution -> intersect
    if (delta > 0){
        //two positive solutions -> select smaller one
        if (-b > 0 && c > 0){
            n = (-b - sqrt(delta))/2/a;
            distance = n * sqrt(dot(ray.direction, ray.direction));
            if (distance < *t){
                *t = distance;
                int_pt -> x = ray.origin_x + ray.direction.x * *t;
                int_pt -> y = ray.origin_y + ray.direction.y * *t;
                int_pt -> z = ray.origin_z + ray.direction.z * *t;
                normal -> x = (int_pt -> x - sphere.ctr_x) / sphere.radius;
                normal -> y = (int_pt -> y - sphere.ctr_y) / sphere.radius;
                normal -> z = (int_pt -> z - sphere.ctr_z) / sphere.radius;
            }
            return 1;
        }
        //one negative and one positive solutions -> select positive one
        else if (c < 0){
            n = (-b + sqrt(delta))/2/a;
            distance = n * sqrt(dot(ray.direction, ray.direction));
            if (distance < *t){
                *t = distance;
                int_pt -> x = ray.origin_x + ray.direction.x * *t;
                int_pt -> y = ray.origin_y + ray.direction.y * *t;
                int_pt -> z = ray.origin_z + ray.direction.z * *t;
                normal -> x = (int_pt -> x - sphere.ctr_x) / sphere.radius;
                normal -> y = (int_pt -> y - sphere.ctr_y) / sphere.radius;
                normal -> z = (int_pt -> z - sphere.ctr_z) / sphere.radius;
            }
            return 1;
        }
        //two negative solutions -> cannot see -> return 0
        else{
            return 0;
        }
    }

    //There is no solution -> not intersect -> return 0
    else{
        return 0;
    }
}
