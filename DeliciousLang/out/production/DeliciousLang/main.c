#include "stdlib.h"
#include "stdio.h"
#include "math.h"
#include "time.h"

double _eps = 1e-7;
double readint() {
   double res;
   int tmp;
   scanf("%d", &tmp);
   res = tmp;
   return res;
}

double readdouble() {
   double res;
   double tmp;
   scanf("%lf", &tmp);
   res = tmp;
   return res;
}

void print(double a) {
   if (fabs(a - (int)(a + _eps)) < _eps)
       printf("%d", (int)a);
else
   printf("%lf", a);
}

double acc_pow(double a, double n) {
   if (fabs(n - (int)(n + _eps)) < _eps && n <= 1000) {
       double res = 1;
       for (int i = 0; i < n; ++i)
           res *= a;
       return res;
   } else
       return pow(a, n);
}

char * pif(double a, double b, double c) {
return (acc_pow(a, 2)+acc_pow(b, 2)==acc_pow(c, 2) ? "YES" : "NO");
}

int main() {
;
double a= 0;
double b= 0;
double c= 0;
{
	double t_a = readint();
	double t_b = readint();
	double t_c = readint();
	a = t_a;
	b = t_b;
	c = t_c;
};
puts(pif(a, b, c));
}
