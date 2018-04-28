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
#include "light.h"


/*
 * Reads spheres from .txt file and creates linked list
 */
void read_objs(OBJ_T **list){
  
    double cx, cy, cz, radius, r, g, b;
    
    while (scanf("%lf %lf %lf %lf %lf %lf %lf", &cx, &cy, &cz, &radius, &r, &g, &b) == 7){
        
        //create node and sets the parameters of the sphere
        OBJ_T *node = (OBJ_T*) malloc(sizeof(OBJ_T));
        node -> sphere.ctr_x = cx;
        node -> sphere.ctr_y = cy;
        node -> sphere.ctr_z = cz;
        node -> sphere.radius = radius;
        node -> color.R = (double)r;
        node -> color.G = (double)g;
        node -> color.B = (double)b;
        
        //link the node to the linked list
        (*list)-> next = node;
        *list = (*list) -> next;
    }
}



/*
 * Iterates through linked list; finds closest intersection using intersect_sphere;
 * returns color of closest sphere
 */
COLOR_T trace(RAY_T ray, OBJ_T *list, LIGHT_T light){
    COLOR_T color;
    COLOR_T surface_color;
    double distance = 1000;
    double d_t = 900;
    double *t = &d_t;
    VEC_T intersection, norm;
    VEC_T *int_pt = &intersection;
    VEC_T *normal = &norm;

    //set background (default) color
    color.B = (double)0;
    color.G = (double)0;
    color.R = (double)0;
    
    //traverse the list to find the closest sphere and finds its color
    while (list != NULL){
        
        if (intersect_sphere(ray, list -> sphere, t, int_pt, normal)){
            if (*t < distance){
                surface_color.B = list -> color.B;
                surface_color.G = list -> color.G;
                surface_color.R = list -> color.R;
                distance = d_t;
            }
        }
        list = list -> next;
    }
    if (distance != 1000){
        color = illuminate(ray, light, list, surface_color, *int_pt, *normal);
    }
    return color;
}


int main(int argc, const char * argv[]) {
    //initializations
    OBJ_T **list = NULL;
    OBJ_T *cur = NULL;
    OBJ_T header;
    cur = &header;
    list = &cur;
    COLOR_T color;
    double x, y;
    struct NODE *temp;
    LIGHT_T light;
    
    light.loc.x = 5;
    light.loc.y = 10;
    light.loc.z = 0;
    
    //create the linked list of the spheres
    read_objs(list);
    
    printf("P6\n1000 1000\n255\n");           //header for .ppm file
    
    //traverse all the pixels
    for (y = 0; y < 1000; y++){
        for (x = 0; x < 1000; x++){
            
            //create the direction vector
            RAY_T dir;
            dir.origin_x = dir.origin_y = dir.origin_z = 0;
            dir.direction.x = (double) -0.5 + x/1000;
            dir.direction.y = (double) 0.5 - y/1000;
            dir.direction.z = (double) 1;
            
            //Normalize the ray
            dir.direction = normalize(dir.direction);
            
            //check to see if there is a sphere on that direction and then sets the color of the pixel
            color = trace(dir, &header, light);
            if (color.R > 1){color.R = 1;}
            if (color.G > 1){color.G = 1;}
            if (color.B > 1){color.B = 1;}
            
            //color of the pixel
            //if (color.R * 255 > 240 && color.R * 255 < 241){
            //printf("%f %f %f\n", color.R * 255, color.B * 255, color.G*255);
            //}
            printf("%c%c%c", (unsigned char)(color.R * 255), (unsigned char)(color.G * 255), (unsigned char)(color.B * 255));
        }
    }
    
    //free the memory of the linked list
    cur = (&header) -> next;
    while (cur != NULL){
        temp = cur;
        cur = cur -> next;
        free(temp);
    }
    
    return 0;
}
