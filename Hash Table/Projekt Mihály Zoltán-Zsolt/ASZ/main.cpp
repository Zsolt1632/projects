#include "HashMap.h"
#include "HashMap.cpp"

int main()
{
    int age;
    string name;
    Hash<int, string> mp;
    freopen("input.txt", "r", stdin);
    for (int i = 0; i < 10; i++)
    {
        cin >> age >> ws;
        getline(cin, name);
        mp.insertItem(name, age);
    }
    cout << mp << endl;
    List<int, string> *it = mp.find("Danilo Trajkovic");
    cout << "Kereses eredmneye: " ;
    if (it)
        cout << it->key << " " << it->data << endl;
    else
        cout << "none existent.\n";
}