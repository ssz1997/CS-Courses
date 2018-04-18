//  
//  Created by Shouzhuo Sun on 2/10/18.
//  CS304 Assignment 2 Session 2
//
//  Read in value in decimal/hex/octal and convert them to decimal/hex/octal and print them out


#include <stdio.h>

/*
 * read in the value in different mode and store them
 */
short get_operand(char mode){
    int acc;
    short operand;
    switch (mode) {                             
        case 'd':
            printf("Enter decimal value: ");
            scanf("%d", &acc);
            printf("%d\n", acc);
            break;
        case 'o':
            printf("Enter octal value: ");
            scanf("%o", &acc);
            printf("%o\n", acc);
            break;
        case 'h':
            printf("Enter hex value: ");
            scanf("%x", &acc);
            printf("%X\n", acc);
            break;
        default:
            break;
    }
    operand = (short)acc;

    
    return operand;
    
    
};


/* 
 * Print out the Accumulator
 */
void print_acc(short acc){
    printf("\n");
    printf("**************************************\n");
    printf("* Accumulator:                       *\n");
    printf("*   Hex     :   %04hX                 *\n", acc);
    printf("*   Octal   :   %06ho               *\n", acc);
    printf("*   Decimal :   %d               ", acc);
    if (acc <= -10000){                                 //the length of the decimal is not predetermined, so here I manually adjust the number of spaces to match the position of the star sign.
        printf("*\n");
    }
    else if ((-9999 <= acc && acc <= -1000) || (acc >= 10000)){
        printf(" *\n");
    }
    else if (( -999 <= acc && acc <= -100) || (1000 <= acc && acc <= 9999)){
        printf("  *\n");
    }
    else if ((-99 <= acc && acc <= -10) || (100 <= acc && acc <= 999)){
        printf("   *\n");
    }
    else if ((-9 <= acc && acc <= -1) || (10 <= acc && acc <= 99)){
        printf("    *\n");
    }
    else{
        printf("     *\n");
    }
    
    
    
    printf("**************************************\n");
    printf("\n");



};

/*
 * Print the menu, read in the option and return it to the main function
 */

char print_menu(){
    char option[30];
    char mode;
    do{
        int i;
        for (i = 0; i < 30; i++){
            option[i] = 0;
        }
        printf("Please select one of the following options:\n\n");
        
        printf("O Octal Mode\n");
        printf("H Hexidecimal Mode\n");
        printf("D Decimal Mode\n\n");

        printf("C Clear Accumulator\n");
        printf("S Set Accumulator\n\n");

        printf("Q Quit\n\n");

        scanf("%s", option);
        printf("Option: ");
        
        //Check if the input option is more than one character
        if (option[1] != 0){
            printf("%s\n\n", option);

            printf("Invalid option: %s\n\n", option);
        }
        else{
            mode = option[0];
            
            if ((mode >= 65) && (mode <= 90)){
                mode += 32;
            }
            
            //check if the input is valid. If not, reprint the menu and do the whole thing again. If yes, store the input
            switch (mode) {
                case 'o':
                    printf("%c\n", option[0]);
                    printf("Mode is Octal\n");
                    break;
                case 'h':
                    printf("%c\n", option[0]);
                    printf("Mode is Hexidecimal\n");
                    break;
                case 'd':
                    printf("%c\n", option[0]);
                    printf("Mode is Decimal\n");
                    break;
                case 'c':
                    printf("%c\n", option[0]);
                    break;
                case 's':
                    printf("%c\n", option[0]);
                    break;
                case 'q':
                    printf("%c\n\n", option[0]);
                    break;
                default:
                    printf("%c\n", option[0]);
                    printf("\n");
                    printf("Invalid option: %c\n", option[0]);
                    printf("\n");
                    option[1] = 1;
                    break;
            }
        }
    }while (option[1] != 0);
    
    return mode;
};



int main(int argc, const char * argv[]) {
    int quit = 1;
    char mode = 'd';
    char option;
    short acc = 0;
    
    
    //if quit == 0, then stop the program; if quit == 1, we continue
    while (quit == 1){
        print_acc(acc);
        option = print_menu();
        //store the mode
        if (option == 'o' || option == 'h' || option == 'd'){
            
            mode = option;
        }
        //clear the accumulator
        else if (option == 'c'){
            acc = 0;
        }
        //set a value
        else if (option == 's'){
            acc = get_operand(mode);
        }
        //quit the program
        else if (option == 'q'){
            quit = 0;
        }
    }
    
    
    
    return 0;
}
