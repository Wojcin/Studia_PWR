#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

extern void multiply(int* A, int* B, int* result, int lenA, int lenB);


int readToArray(int* array, char input[]);
const int base=10;
const int digitNum= 4000;

int main()
{


    int c[1] = {0};
    int d[1] = {0};
    int a[digitNum];
    int b[digitNum];
    int *res;

   char liczba1[4000]="2648577648764857764857463547648574764764857764876485776485746354764857476485776485746354635637648577648574635476485747648577648574635463563764857463557676485746354857648574635746357648574635635764857463557676485746354857648574635746357648574635635574635764857764857463547648574764857764857463546356376485746355767648574635485764857463574635764857463563547648576485776485746354764857476485776485746354635637648574635576764857463548576485746357463576485746356357476485776485746354635637648574635576764857463548576485746357463576485746356358577648574635463563764857764857463547648574764857764857463546356376485746355767648574635485764857463574635764857463563576485746355767648574635485764857463574635764857463563557463576485776485746354764857476485776485746354635637648574635576764857463548576485746357463576485746356354764857648577648574635476485747648577648574635463563764857463557676485746354857648574635746357648574635635747648577648574635463563764857463557676485746354857648574635746357648574635635";
   char liczba2[4000]="9879876523919879876523918987652391865987659876523918998765239189876523918876523918239198765239188239876523918918898765298798765239189876523918659876598765239189987652391898765239188765239182391987652391882398765239189183918659987987652391898765239186598765987652391899876523918987652391887652391823919876523918823987652391891887659879876523918987652391865987659876523918998765239189876523918876523918239198765239188239876523918918987652391899877648577648764857764857463547648574764857764857463546356376485776485746354764857476485776485746354635637648574635576764857463548576485746357463576485746356357648574635576764857463548576485746357463576485746356355746357648577648574635476485747648577648574635463563764857463557676485746354857648574635746357648574635635476485764857764857463547648574764857764857463546356376485746355767648574635485764857463574635764857463563574764857764857463546356376485746355767648574635485764857463574635764857463563565239189876523918876523918239198765239188239876523918918";


  //scanf("%s",liczba1);
  //scanf("%s",liczba2);
   clock_t start=clock();
   int a_size=readToArray(a, liczba1);
   int b_size=readToArray(b, liczba2);

    res = calloc(a_size+b_size, sizeof(int));

   int i;
for(i=0; i<a_size+b_size;i++)
{
     res[i]=0;
}


    multiply(a, b, res, a_size, b_size);

for(i=a_size+b_size-1; i>=0;i--)
{
     printf("%d", res[i]);
}
     printf("\n");
//printf("Czas wykonywania: %lu ms\n",((1000*(clock()-start))/CLOCKS_PER_SEC)); 
    return 0;
}



int readToArray(int* array, char input[])
{
    int input_size=0;
    do{
        input_size++;
    }while(input[input_size]!=0);
    

    for(int i=0;i<input_size;i++)
    {
            array[input_size-i-1]=input[i]-'0';
    }
    return input_size;
}