//
//  CS304 session2
//
//  Shouzhuo Sun
//

#include <stdio.h>

int rmult(int i, int j){
    if (j == 1){
        return i;
    } 
    else{
        return i + rmult(i, j - 1);
    }
}
    
int rpow(int b, int p){
    if (p == 1){
        return b;
    }
    else{
        return rmult(b, rpow(b, p - 1));
    }
}

int main(int argc, const char * argv[]) {
    int base, power;
    printf("************************\n");
    for (base = 2; base <= 5; base++){
        for (power = 1; power <= 8; power ++){
            int result = rpow(base, power);
            printf(" %d^%d = %6d 0x%08x\n", base, power, result, result);
        }
        printf("************************\n");
    }
            
    return 0;
    
}
