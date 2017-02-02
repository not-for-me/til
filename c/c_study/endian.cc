#include <iostream>
using namespace std;

int main() {
  int cnt = 0;
  int inputNums[10000] = {}; 
  cin >> cnt;
  if(! (0 < cnt && cnt < 10001)) {
    return 0;
  }

  for(int i = 0; i < cnt; i++) {
    cin >> inputNums[i];
  }

  for(int i = 0; i < cnt; i++) {
    cout << inputNums[i] << "\n";
  }

  cout << endl;

}
