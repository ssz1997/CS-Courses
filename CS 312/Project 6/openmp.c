//
//  main.c
//  fopenmp
//


#include <omp.h>
#include <stdio.h>
#include <stdlib.h>

int hits[4] = {0,0,0,0};

int compute_pi(int a){

    double x, y;
    int local_hits = 0;
    int i = 10000000;
    int j;
    for (j = 0; j < i; j++ ){
        x = (double)rand() / (RAND_MAX)-0.5;
        
        y = (double)rand() / (RAND_MAX)-0.5;
        if (x * x + y * y <= 0.25){
            local_hits += 1;
        }
    }
    hits[a] = i;
    return local_hits;
}

int main(){
    double pi;
    int sum = 0, sum2 = 0;
#pragma omp parallel reduction(+:sum, sum2) num_threads(4)
    {
#pragma omp sections
        {
#pragma omp section
            {
                sum = compute_pi(0);
                sum2 = hits[0];
            }
#pragma omp section
            {
                sum = compute_pi(1);
                sum2 = hits[1];
            }
#pragma omp section
            {
                sum = compute_pi(2);
                sum2 = hits[2];
            }
#pragma omp section
            {
                sum = compute_pi(3);
                sum2 = hits[3];
            }
        }
#pragma omp barrier

        
        
    }
    
    
    pi = (double)sum/sum2*4;
    printf("run %d times\n", sum2);
    printf("pi is equal to %f.\n", pi);
    
    
}




