#ifndef _HashTable_
#define _HashTable_

#include <iostream>
#include <vector>
#include <string>
#include <cstring>
using namespace std;

template <typename T, typename P>
class List
{
public:
    T data;
    P key;
    List *next;
    List *prev;
};
template <typename T, typename P>
void insertAtFront(List<T, P> **head, T newData, P ind);
template <typename T, typename P>
void instertAfterGivenelement(List<T, P> *prevNode, T newData, P ind);
template <typename T, typename P>
void instertBeforeGivenelement(List<T, P> *nextNode, T newData, P ind);
template <typename T, typename P>
void instertAtEnd(List<T, P> **head, T newData, P ind);
template <typename T, typename P>
void DeleteGivenNode(List<T, P> **head, List<T, P> *toDelete);
template <typename T, typename P>
void printList(List<T, P> *head, ostream &s);
template <typename T, typename P>
void printList(List<T, P> *head);

template <typename T, typename P>
class Hash
{
    int size;               // meret
    double max_load_factor; // telitetseg
    int totalElements;
    vector<List<T, P> *> map; // lista
    void rehashIfNeeded();
    int hashFunction(int);
    int hashFunction(double);
    int hashFunction(string);
    int hashFunction(char *);
public:
    Hash();
    Hash(int); // Constructor
    void insertItem(P key, T val);
    void deleteItem(P);
    List<T, P> *find(P key);
    ostream &displayHash(ostream &s) const;
    void displayHash();
    ~Hash();
};

template <typename T, typename P>
ostream &operator<<(ostream &s, const Hash<T, P> &h);
template <typename T, typename P>
istream &operator>>(istream &s, Hash<T, P> &h);

#endif