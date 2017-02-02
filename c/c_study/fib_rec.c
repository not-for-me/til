#include <stdio.h>

long long fib(long long n);

int main() {
  long long i;

  for(i = 0; i <=50; i++)
    printf("%lld", fib(i));
}

long long fib(long long n) {
  if(n <= 2) return 1;
  if(n > 2) return fib(n-1) + fib(n-2);
  return 1ll;
}
