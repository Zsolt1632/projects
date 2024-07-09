#include "HashTable.h"
#include "HashTable.cpp"


int main()
{
    Hash<int, string> mp;
    freopen("input.txt", "r", stdin);
    for(int i = 0; i < 10; i++){
        int age;
        string data;
        cin >> age >> ws;
        getline(cin, data);
        mp.insertItem(data, age);
        //cin >> mp;
    }
    //cout << mp << endl;
    mp.displayHash();
    List<int, string> *it =  mp.find("Nagy Bela");
    cout << "Kereses eredmneye: ";
    if (it)
        cout << it->data << endl;
    else
        cout << "none existent.\n";
}