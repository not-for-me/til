#include <stdio.h>

int exponential(int exponent) {
  if ( exponent == 0 ) {
    return 1;
  }

  return 2 * exponential(exponent-1);
}

int main() {
  printf("%d\n", exponential(5));
  return 0;
}
