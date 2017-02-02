#include <stdio.h>

int _twoExponential(int exponent, int acc) {
  if ( exponent == 0 ) {
    return acc;
  }

  acc *= 2;
  return _twoExponential(--exponent, acc);
}

int exponential(int num) {
  return _twoExponential(num, 1);
}

int main() {
  printf("%d\n", exponential(5));
  return 0;
}
