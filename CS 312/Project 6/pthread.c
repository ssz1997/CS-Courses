//
//  main.c
//  lpthread
//
//

#include <stdio.h>
#include<pthread.h>
#include <stdlib.h>

int insert = 0;
pthread_mutex_t task_queue_lock;
int task[2] = {0,0};
int i;

void *producer(){
    while (insert < i){
        pthread_mutex_lock(&task_queue_lock);
        if (task[0] == 0 && task[1] == 0){
            task[0] = 100;
            insert ++;

            printf("%d This task is inserted by producer #1\n", insert);
            
        }
        else if (task[0] != 0 && task[1] == 0){
            task[1] = 100;
            insert ++;
            printf("This task is inserted by producer #1\n");
            
        }
        else{
            
        }
         pthread_mutex_unlock(&task_queue_lock);
    }
    pthread_exit(NULL);
}

void *consumer(void *s){
    
    int local_tasks = 0;
    int *myid = (int*)s;
    while (insert < i || task[0] == 100){
        pthread_mutex_lock(&task_queue_lock);
        if (task[0] == 100 && task[1] == 100){
            task[0] = 0;
            task[0] = 100;
            task[1] = 0;
            local_tasks ++;
            printf("This task is extracted by consumer #%d\n", *myid);
        }
        else if (task[0] == 100 && task[1] == 0){
            task[0] = 0;
            local_tasks ++;
            printf("This task is extracted by consumer #%d\n", *myid);
        }
        else{
            
        }
        pthread_mutex_unlock(&task_queue_lock);
    }
    printf("Cousumer #%d extracted %d tasks in total.\n", *myid, local_tasks);
    pthread_exit(0);
}





int main(int argc, char *argv[]){

    i = atoi (argv[1]);
    
    pthread_t p_threads[3];
    pthread_attr_t attr;
    pthread_attr_init(&attr);
    pthread_mutex_init(&task_queue_lock, NULL);
    int id[2] = {1,2};

    pthread_create(&p_threads[0], &attr, producer, NULL);
    pthread_create(&p_threads[1], &attr, consumer, (void*) &id[0]);
    pthread_create(&p_threads[2], &attr, consumer, (void*) &id[1]);
    
    pthread_join(p_threads[0], NULL);
    pthread_join(p_threads[1], NULL);
    pthread_join(p_threads[2], NULL);
    return 0;
 
}
