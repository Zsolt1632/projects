#include "HashTable.h"
#include "HashTable.cpp"
#include <vector>
#include <cstdlib>
#include <ctime>

int binary_search(vector<int> arr, int s, int e, int target)
{

    while (s <= e)
    {
        int m = (s + e) / 2;
        if (arr[m] == target)
            return m;
        else if (arr[m] < target)
            s = m + 1;
        else
            e = m - 1;
    }
    return e;
}

string intToRoman(int &num)
{
    cin >> num;
    if (num < 1 or num > 3999)
    {
        cout << "Error: Invalid input!\n";
        cerr << "try again with a new number(between 1 and 3999):";
        return intToRoman(num);
    }
    vector<int> vec = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    Hash<string, int> mp;
    mp.insertItem(1, "I");
    mp.insertItem(4, "IV");
    mp.insertItem(5, "V");
    mp.insertItem(9, "IX");
    mp.insertItem(10, "X");
    mp.insertItem(40, "XL");
    mp.insertItem(50, "L");
    mp.insertItem(90, "XC");
    mp.insertItem(100, "C");
    mp.insertItem(400, "CD");
    mp.insertItem(500, "D");
    mp.insertItem(900, "CM");
    mp.insertItem(1000, "M");
    int temp = num;
    string s = "";
    List<string, int> *it;
    while (temp != 0)
    {
        it = mp.find(temp);
        if (it != NULL)
        {
            s += it->data;
            break;
        }
        int i = binary_search(vec, 0, 12, temp);
        it = mp.find(vec[i]);
        s += it->data;
        temp -= it->key;
    }
    return s;
}

int main(){
    //-freopen("be1.txt", "rt", stdin);
    //freopen("ki1.txt", "wt", stdout);
    int num;
    cerr << "insert a number: ";
    cout << intToRoman(num);
}