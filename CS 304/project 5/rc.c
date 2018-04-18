//
//  CS 304 Session 2
//  Assignment5
//
//  Created by Shouzhuo Sun on 3/22/18.
//  

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "rc.h"
#include "vec.h"


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
        node -> color.R = (double)r*255;
        node -> color.G = (double)g*255;
        node -> color.B = (double)b*255;
        
        //link the node to the linked list
        (*list)-> next = node;
        *list = (*list) -> next;
    }
}

/*
 * Computes intersection of ray and sphere; returns 1 if intersection, 0 otherwise; t is set to
 * distance from camera to sphere intersection
 */
int intersect_sphere (RAY_T ray, SPHERE_T sphere, double *t){
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
            }
            return 1;
        }
        //one negative and one positive solutions -> select positive one
        else if (c < 0){
            n = (-b + sqrt(delta))/2/a;
            distance = n * sqrt(dot(ray.direction, ray.direction));
            if (distance < *t){
                *t = distance;
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

/*
 * Iterates through linked list; finds closest intersection using intersect_sphere;
 * returns color of closest sphere
 */
COLOR_T cast(RAY_T ray, OBJ_T *list){
    COLOR_T color;
    double distance = 1000;
    double d_t = 900;
    double *t = &d_t;
    
    //set background (default) color
    color.B = (double)255;
    color.G = (double)255;
    color.R = (double)255;
    
    //traverse the list to find the closest sphere and finds its color
    while (list != NULL){
        if (intersect_sphere(ray, list -> sphere, t)){
            if (*t < distance){
                color.B = list -> color.B;
                color.G = list -> color.G;
                color.R = list -> color.R;
                distance = d_t;
            }
        }
        list = list -> next;
    }
    return color;
}


int main(int argc, const char * argv[]) {
    OBJ_T **list = NULL;
    OBJ_T *cur = NULL;
    OBJ_T header;
    cur = &header;
    list = &cur;
    COLOR_T color;
    double x, y;
    struct NODE *temp;
    
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
            color = cast(dir, &header);
            
            //color of the pixel
            
            printf("%c%c%c", (unsigned char)color.R, (unsigned char)color.G, (unsigned char)color.B);
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
