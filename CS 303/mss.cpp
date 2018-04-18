//
//  main.cpp
//  mss.cpp
//
//  Created by 孙守拙 on 6/5/17.
//  Copyright © 2017 孙守拙. All rights reserved.
//

#include <iostream>
#include <time.h>
#include <iomanip>

double mss1(int arr[], int length){
    int maxSum = 0;
    
    clock_t startTime = clock();
    for (int i = 0; i < length; ++i )
        for (int j = i; j < length; ++j)
        {
            int thisSum = 0;
            
            for ( int k = i; k <= j; ++k){
                
                thisSum += arr[k];
            if (thisSum > maxSum)
                maxSum = thisSum;
            }
        }
    clock_t endTime = clock();
    
    std::cout << "Algorithms 1: " << maxSum << '\n';
    return (endTime - startTime) / (double)CLOCKS_PER_SEC * 1000000;
}

double mss2(int arr[], int length){
    int maxSum = 0;
    
    clock_t startTime = clock();
    for (int i = 0; i < length; ++i)
    {
        int thisSum = 0;
        for (int j = i; j < length; ++j)
        {
            thisSum += arr[j];
            
            if ( thisSum > maxSum)
                maxSum = thisSum;
        }
    }
    clock_t endTime = clock();
    std::cout << "Algorithms 2: " << maxSum << '\n';
    return (endTime - startTime) / (double)CLOCKS_PER_SEC * 1000000;
}




double mss3Helper(int arr[], int left, int right ){
    
    if ( left == right )
        
            return arr[left];
    
    int center = (left + right) / 2;
    int maxLeftSum = mss3Helper (arr, left, center);
    int maxRightSum = mss3Helper (arr, center + 1, right);
    
    int maxLeftBorderSum = 0, leftBorderSum = 0;
    for(int i = center; i >= left; --i)
    {
        leftBorderSum += arr[i];
        if (leftBorderSum > maxLeftBorderSum)
            maxLeftBorderSum = leftBorderSum;
    }
    
    int maxRightBorderSum = 0, rightBorderSum = 0;
    for(int j = center + 1; j <= right; ++j)
    {
        rightBorderSum += arr[j];
        if (rightBorderSum > maxRightBorderSum)
            maxRightBorderSum = rightBorderSum;
    }
    
    return std::max(std::max(maxLeftSum, maxRightSum),maxRightBorderSum + maxLeftBorderSum);
}


double mss3(int arr[], int length){
    int maxSum;
    clock_t startTime = clock();
    maxSum = mss3Helper(arr, 0, length-1);
    clock_t endTime = clock();
    std::cout << "Algorithms 3: " << maxSum << '\n';
    return (endTime - startTime) / (double) CLOCKS_PER_SEC * 1000000;
    
}

double mss4(int arr[], int length){
    int maxSum = 0, thisSum = 0;
    
    clock_t startTime = clock();
    for (int j = 0; j < length; ++j){
        thisSum += arr[j];
        
        if (thisSum > maxSum)
            maxSum = thisSum;
        else if (thisSum < 0)
            thisSum = 0;
    }
    clock_t endTime = clock();
    
    std::cout << "Algorithms 4: " << maxSum << '\n';
    return (endTime - startTime) / (double) CLOCKS_PER_SEC * 1000000;
}

int main() {
    int arr[2500];
    int i = 0;
    double mss1Result;
    double mss2Result;
    double mss3Result;
    double mss4Result;
    
    std::cout << "What is the array?" << '\n';
    while (std::cin >> arr[i])
        i++;
    mss1Result = mss1(arr,i);
    mss2Result = mss2(arr,i);
    mss3Result = mss3(arr,i);
    mss4Result = mss4(arr,i);
    
    std::cout << '\n';
    std::cout << "Final timing results:" << '\n';
    std::cout << "Algorithms 1 O(n^3):   " << std::fixed << std::setprecision(0) << mss1Result << "ms" << '\n';
    std::cout << "Algorithms 2 O(n^2):   " << std::fixed << std::setprecision(0) << mss2Result << "ms" << '\n';
    std::cout << "Algorithms 3 O(n lgn): " << std::fixed << std::setprecision(0) << mss3Result << "ms" << '\n';
    std::cout << "Algorithms 4 O(n)    : " << std::fixed << std::setprecision(0) << mss4Result << "ms" << '\n';
    
    
    
    
    return 0;
}
