//  
//  Created by Shouzhuo Sun on 2/21/18.
//  CS304 Assignment 3 Session 2
//
//  Read in value in decimal/hex/octal and convert them to decimal/hex/octal and print them out


#include <stdio.h>
#include <string.h>
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
 * read in binary value stored in a character array, and return as an unsiged short
 */
unsigned short get_binary_op(char *bin){
    int i, j;
    int val = 0;
    int str_len;
    int power_of_two[16] = {32768,16384,8192,4096,2048,1024,512,256,128,64,32,16,8,4,2,1}; //hard code for easy calculation
    
    //initialize the character array to 0 for easy calculation
    for (i = 0; i < 20; i++){
        bin[i] = 0;
    }
    printf("Enter binary value: ");
    scanf("%s", bin);
    printf("%s\n", bin);
    str_len = strlen(bin);
    for (i = str_len - 1, j = 15; i >= 0; i--, j--){
        val += ((int)bin[i]-48) * power_of_two[j];
    }
    
    return (unsigned short) val;
}


/*
 * format the binary value in the character array bin for output
 */
void convert_to_binary(short acc, char *bin){
    int i;
    int abc = acc;
    int power_of_two[16] = {32768,16384,8192,4096,2048,1024,512,256,128,64,32,16,8,4,2,1}; //hard code for easy calculation
    
    //if the number is less than 0, convert it to unsigned for easier calculation.
    if(abc < 0){
        abc += 65536;
    }

    //calculate the binary value
    for (i = 0; i < 16; i++){
        if (abc % power_of_two[i] == abc){
            *(bin+i) = '0';
        }
        else{
            abc = abc % power_of_two[i];
            *(bin+i) = '1';
        }
    }
    
    //move the bits for spaces
    for (i = 18; i >=15; i--){
        bin[i] = bin[i-3];
    }
    for (i = 13; i >= 10; i--){
        bin[i] = bin[i-2];
    }
    for (i = 8; i >= 5; i--){
        bin[i] = bin[i-1];
    }
    
    //create the spaces between each 4-bits
    bin[4] = ' ';
    bin[9] = ' ';
    bin[14] = ' ';
}


/*
 *add number to accumulator and detect overflow/underflow
 */
void add(short *acc, char mode){
    int abc = *acc;
    short add_num;
    char bin[20];
    switch (mode) {
        case 'h':
            printf("Enter hex value: ");
            scanf("%hX", &add_num);
            printf("%hX\n", add_num);
            break;
        case 'o':
            printf("Enter octal value: ");
            scanf("%ho", &add_num);
            printf("%ho\n", add_num);
            break;
        case 'd':
            printf("Enter decimal value: ");
            scanf("%hd", &add_num);
            printf("%hd\n", add_num);
            break;
        case 'b':
            printf("Enter binary value: ");
            add_num = get_binary_op(bin);
            break;
        default:
            break;
    }
    
    //detect overflow or underflow
    *acc += (short)add_num;
    if (abc > 0 && add_num > 0 && *acc < 0){
        printf("Overflow Error\n");
    }
    else if (abc < 0 && add_num < 0 && *acc > 0){
        printf("Negative Overflow Error\n");
    }
}

/*
 *subtract number from accumulator and detect underflow
 */
void subtract(short *acc, char mode){
    int abc = *acc;
    short add_num;
    char bin[20];
    switch (mode) {
        case 'h':
            printf("Enter hex value: ");
            scanf("%hX", &add_num);
            printf("%hX\n", add_num);
            break;
        case 'o':
            printf("Enter octal value: ");
            scanf("%ho", &add_num);
            printf("%ho\n", add_num);
            break;
        case 'd':
            printf("Enter decimal value: ");
            scanf("%hd", &add_num);
            printf("%hd\n", add_num);
            break;
        case 'b':
            printf("Enter binary value: ");
            add_num = get_binary_op(bin);
            break;
        default:
            break;
    }
    *acc -= add_num;
    
    //detect overflow/underflow
    if (abc > 0 && add_num < 0 && *acc < 0){
        printf("Overflow Error\n");
    }
    else if (abc < 0 && add_num > 0 && *acc > 0){
        printf("Negative Overflow Error\n");
    }
}

/* 
 * Print out the Accumulator
 */
void print_acc(short acc){
    char bin[20];
    
    //call the function to make the binary output
    convert_to_binary(acc, bin);
    
    printf("\n");
    printf("**************************************\n");
    printf("* Accumulator:                       *\n");
    printf("*   Binary  :   %s  *\n", bin);
    printf("*   Hex     :   %04hX                 *\n", acc);
    printf("*   Octal   :   %06ho               *\n", acc);
    printf("*   Decimal :   %d               ", acc);
    if (acc <= -10000){                                 //the length of the decimal is not predetermined, so here I manually adjust the number of spaces to match the position of the star sign. There is easier way to do this. Since this works, no need (too lazy) to change.
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
        
        printf("Please select one of the following options:\n\n");
        
        printf("B Binary Mode\t\t& AND with Accumulator\t\t+ Add to Accumulator\n");
        printf("O Octal Mode\t\t| OR with Accumulator\t\t- Subtract from Accumulator\n");
        printf("H Hexidecimal Mode\t^ XOR with Accumulator\t\tN Negate\n");
        printf("D Decimal Mode\t\t~ Complement Accumulator\n\n");

        printf("C Clear Accumulator\t< Shift Accumulator Left\n");
        printf("S Set Accumulator\t> Shift Accumulator Right\n\n");

        printf("Q Quit\n\n");

        scanf("%s", option);
        printf("Option: ");
        
        //Check if the input option is more than one character
        if (strlen(option) > 1){
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
                    printf("Mode is Hexadecimal\n");
                    break;
                case 'd':
                    printf("%c\n", option[0]);
                    printf("Mode is Decimal\n");
                    break;
                case 'b':
                    printf("%c\n", option[0]);
                    printf("Mode is Binary\n");
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
                case '&':
                    printf("%c\n", option[0]);
                    break;
                case '|':
                    printf("%c\n", option[0]);
                    break;
                case '^':
                    printf("%c\n", option[0]);
                    break;
                case '~':
                    printf("%c\n\n", option[0]);
                    break;
                case '+':
                    printf("%c\n", option[0]);
                    break;
                case '-':
                    printf("%c\n", option[0]);
                    break;
                case 'n':
                    printf("%c\n\n", option[0]);
                    break;
                case '>':
                    printf("%c\n", option[0]);
                    break;
                case '<':
                    printf("%c\n", option[0]);
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
    short operand;
    short *val = &acc;
    char val_binary[20];
    
    
    //if quit == 0, then stop the program; if quit == 1, we continue
    while (quit == 1){
        print_acc(acc);
        option = print_menu();
        
        switch (option) {
            case 'o':
                mode = 'o'; break;
            case 'h':
                mode = 'h'; break;
            case 'd':
                mode = 'd'; break;
            case 'b':
                mode = 'b'; break;
            case '&':
                switch (mode) {
                    case 'h':
                        printf("Enter hex value: ");
                        scanf("%hX", &operand);
                        printf("%04hX\n", operand);
                        break;
                    case 'o':
                        printf("Enter octal value: ");
                        scanf("%ho", &operand);
                        printf("%ho\n", operand);
                        break;
                    case 'd':
                        printf("Enter decimal value: ");
                        scanf("%hd", &operand);
                        printf("%hd\n", operand);
                        
                        break;
                    case 'b':
                        printf("Enter binary value: ");
                        operand = get_binary_op(val_binary);
                        break;
                        
                    default:
                        break;
                }
                acc = acc & operand;
                break;
            case '|':
                switch (mode) {
                    case 'h':
                        printf("Enter hex value: ");
                        scanf("%hX", &operand);
                        printf("%04hX\n", operand);
                        break;
                    case 'o':
                        printf("Enter octal value: ");
                        scanf("%ho", &operand);
                        printf("%ho\n", operand);
                        break;
                    case 'd':
                        printf("Enter decimal value: ");
                        scanf("%hd", &operand);
                        printf("%hd\n", operand);
                        break;
                    case 'b':
                        printf("Enter binary value: ");
                        operand = get_binary_op(val_binary);
                        break;
                        
                    default:
                        break;
                }
                acc = acc | operand;
                break;
            case '^':
                switch (mode) {
                    case 'h':
                        printf("Enter hex value: ");
                        scanf("%hX", &operand);
                        printf("%04hX\n", operand);
                        break;
                    case 'o':
                        printf("Enter octal value: ");
                        scanf("%ho", &operand);
                        printf("%ho\n", operand);
                        break;
                    case 'd':
                        printf("Enter decimal value: ");
                        scanf("%hd", &operand);
                        printf("%hd\n", operand);
                        break;
                    case 'b':
                        printf("Enter binary value: ");
                        operand = get_binary_op(val_binary);
                        break;
                        
                    default:
                        break;
                }
                acc = acc ^ operand;
                break;
            case '~':
                acc = ~acc;
                break;
            case '<':
                printf("Enter number of positions to left shift accumulator: ");
                scanf("%hd", &operand);
                printf("%hd\n", operand);
                acc = acc << (int) operand;
                break;
            case '>':
                printf("Enter number of positions to right shift accumulator: ");
                scanf("%hd", &operand);
                printf("%hd\n", operand);
                acc = acc >> (int) operand;
                break;
            case 'n':
                acc = -acc;
                break;
            case '+':
                add(val, mode);
                break;
            case '-':
                subtract(val, mode);
                break;
            default:
                break;
        }
        
     
        //store the mode
        if (option == 'o' || option == 'h' || option == 'd' || option == 'b'){
            
            mode = option;
        }
        //clear the accumulator
        else if (option == 'c'){
            acc = 0;
        }
        //set a value
        else if (option == 's'){
            switch (mode) {
                case 'b':
                    acc = get_binary_op(val_binary);
                    break;
                default:
                    acc = get_operand(mode);
                    break;
            }
        }
        //quit the program
        else if (option == 'q'){
            quit = 0;
        }
        
    }
    
    return 0;
}
