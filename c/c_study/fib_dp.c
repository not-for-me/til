#include <stdio.h>
long long fibs[100];

long long fib(long long n);

int main() {
  long long i;

  for(i = 0; i <=50; i++)
    printf("%lld", fib(i));
}

long long fib(long long n) {
  if(fibs[n]) return fibs[n];
  if(n <= 2) {
    fibs[n] = 1;
    return fibs[n];
  }
  if(n > 2) {
    fibs[n] = fib(n-1) + fib(n-2);
    return fibs[n];
  }
  return 1ll;
}
