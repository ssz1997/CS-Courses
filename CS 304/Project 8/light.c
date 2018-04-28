//
// CS 304 Session 2
// Assignment 8
// Shouzhuo Sun
// Created on 4/20/2018
//

#include <stdio.h>
#include <math.h>
#include "light.h"

COLOR_T illuminate (RAY_T ray, LIGHT_T light, OBJ_T *list,
                    COLOR_T surface_col, VEC_T int_pt, VEC_T normal){
    COLOR_T color;
    //ambient - 0.2 * surface color
    color.R = 0.2 * surface_col.R;
    color.G = 0.2 * surface_col.G;
    color.B = 0.2 * surface_col.B;
    

    //diffuse - depends on the angle between the normal and the light
    VEC_T l;  //initialize the light
    
    l.x = light.loc.x - int_pt.x;
    l.y = light.loc.y - int_pt.y;
    l.z = light.loc.z - int_pt.z;
    l = normalize(l);
    double dot_prod = dot(l, normal);
    
    if (dot_prod > 0){     //if the point can be reached by the light
        
        color.R += dot_prod * surface_col.R;
        color.G += dot_prod * surface_col.G;
        color.B += dot_prod * surface_col.B;
    
        //specular - depends on the angle between the view and the reflection
        VEC_T r;     //view
        r.x = l.x - 2 * normal.x * dot_prod;
        r.y = l.y - 2 * normal.y * dot_prod;
        r.z = l.z - 2 * normal.z * dot_prod;
        
        r = normalize(r);
   
        if (dot(r, ray.direction) > 0){    //if we can see the light from the view point
            
            color.R += pow(dot(r, ray.direction), 200);
            color.G += pow(dot(r, ray.direction), 200);
            color.B += pow(dot(r, ray.direction), 200);

        }

    }
    
    
    return color;
}
