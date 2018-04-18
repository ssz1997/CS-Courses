//
//  CS304 session 2
//  
//  Shouzhuo Sun
//  
//

#include <stdio.h>

int addsquare(int i){
    
    int counter;
    int j = i;
    
    for (counter = 1; counter < j; counter ++){
        //add i itself i times to get its squre
        i = i + j;
    }
    return i;
}

int bitsquare(int i){
    
    int sum = 0, mult = i, bit = 1;
    
    while (bit <= i){
        // if the bit bitand with i is not 0 means that bit in i is 1, so we need to add it to the square
        if ((bit & i) != 0){
            sum = sum + mult;
        }
        
        bit = bit + bit;        //move the bit to the next bit
        mult = mult + mult;
    }
    
    return sum;
}

int main(int argc, const char * argv[]) {
    int i, add, bit;
    
    for (i = 1; i < 21; i ++){
        add = addsquare(i);
        
        bit = bitsquare(i);
        
        printf("%d  %d %d\n", i, add, bit);
    }
    
    return 0;
}
