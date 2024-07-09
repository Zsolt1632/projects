#include "HashTable.h"
#include <typeinfo>

template <typename T, typename P>
void insertAtFront(List<T, P> **head, T newData, P ind)
{
    List<T, P> *newNode = new List<T, P>();
    // adatok megadasa az uj elemnek
    newNode->data = newData;
    newNode->key = ind;

    newNode->next = *head;
    newNode->prev = NULL;
    if (*head != NULL)
    {
        (*head)->prev = newNode;
    }
    *head = newNode;
}

template <typename T, typename P>
void instertAfterGivenelement(List<T, P> *prevNode, T newData, P ind)
{
    if (prevNode == NULL)
    {
        cout << "A megadott elem nem lehet egy NULL pointer" << endl;
        return;
    }
    List<T, P> *newNode = new List<T, P>();
    // adatok megadasa az uj elemnek
    newNode->key = ind;
    newNode->data = newData;
    // bekotes a listaba
    newNode->next = prevNode->next;
    prevNode->next = newNode;
    newNode->prev = prevNode;
    if (newNode->next != NULL)
    {
        newNode->next->prev = newNode;
    }
}

template <typename T, typename P>
void instertBeforeGivenelement(List<T, P> *nextNode, T newData, P ind)
{
    if (nextNode == NULL)
    {
        cout << "A megadott elem nem lehet egy NULL pointer" << endl;
        return;
    }
    List<T, P> *newNode = new List<T, P>();
    // adatok megadasa az uj elemnek
    newNode->key = ind;
    newNode->data = newData;
    // bekotes a listaba
    newNode->prev = nextNode->prev;
    nextNode->prev = newNode;
    newNode->next = nextNode;

    if (newNode->prev != NULL)
    {
        newNode->prev->next = newNode;
    }
}

template <typename T, typename P>
void instertAtEnd(List<T, P> **head, T newData, P ind)
{
    List<T, P> *newNode = new List<T, P>();
    List<T, P> *last = *head;

    newNode->data = newData;
    newNode->key = ind;

    newNode->next = NULL;

    if (head == NULL)
    {
        newNode->prev = NULL;
        *head = newNode;
        return;
    }

    while (last->next != NULL)
    {
        last = last->next;
    }

    last->next = newNode;
    newNode->prev = last;
}

template <typename T, typename P>
void DeleteGivenNode(List<T, P> **head, List<T, P> *toDelete)
{
    if (*head == NULL)
    {
        cout << "A lista ures.";
        return;
    }
    if (toDelete == NULL)
    {
        cout << "A torlendo elem egy NULL pointer.";
        return;
    }

    if (*head == toDelete)
    {
        *head = toDelete->next;
    }
    if (toDelete->next != NULL)
    {
        toDelete->next->prev = toDelete->prev;
    }
    if (toDelete->prev != NULL)
    {
        toDelete->prev->next = toDelete->next;
    }
    free(toDelete);
}

template <typename T, typename P>
void printList(List<T, P> *head, ostream &s)
{
    while (head != NULL)
    {
        s << head->key << " " << head->data << ";\t";
        head = head->next;
    }
}

template <typename T, typename P>
void printList(List<T, P> *head)
{
    while (head != NULL)
    {
        cout << head->key << " " << head->data << ";\t";
        head = head->next;
    }
}

template <typename T, typename P>
Hash<T, P>::Hash()
{
    this->size = 3;
    this->max_load_factor = 0.75;
    this->totalElements = 0;
    this->map.resize(size);
}

template <typename T, typename P>
Hash<T, P>::Hash(int size)
{
    this->size = size;
    this->max_load_factor = 0.75;
    this->totalElements = 0;
    this->map.resize(size);
}

template <typename T, typename P>
Hash<T, P>::~Hash()
{
    for (int i = 0; i < this->size; i++)
    {
        delete map[i];
    }
}

int convert(string x)
{
    int s = 0;
    for (unsigned i = 0; i < x.size(); i++)
    {
        s += x[i];
    }
    return s;
}

int convert(char *x)
{
    int s = 0;
    for (unsigned i = 0; i < strlen(x); i++)
    {
        s += x[i];
    }
    return s;
}

template <typename T, typename P>
int Hash<T, P>::hashFunction(int x)
{
    return (x % size);
}

template <typename T, typename P>
int Hash<T, P>::hashFunction(double x)
{
    return (int(x) % size);
}

template <typename T, typename P>
int Hash<T, P>::hashFunction(string x)
{
    int s = convert(x);
    return (s % size);
}

template <typename T, typename P>
int Hash<T, P>::hashFunction(char *x)
{
    int s = convert(x);
    return (s % size);
}

template <typename T, typename P>
void Hash<T, P>::insertItem(P key, T val)
{
    int index;
    index = hashFunction(key);
    if (map[index] != NULL)
    {
        instertAtEnd(&map[index], val, key);
        return;
    }
    insertAtFront(&map[index], val, key);
    totalElements++;
    rehashIfNeeded();
}

template <typename T, typename P>
void Hash<T, P>::rehashIfNeeded()
{
    if ((double)totalElements <= 3 * (double)size / 4)
    {
        return;
    }
    size *= 2;
    vector<List<T, P> *> newMap(size);
    for (int i = 0; i < size / 2; i++)
    {
        for (List<T, P>* j = map[i]; j != NULL; j = j->next)
        {
            int index = hashFunction(j->key);
            if (newMap[index] == NULL)
            {
                insertAtFront(&newMap[index], j->data, j->key);
            }
            else
            {
                instertAtEnd(&newMap[index], j->data, j->key);
            }
        }
    }
    map = newMap;
}

template <typename T, typename P>
List<T, P> *Hash<T, P>::find(P key)
{
    int index = hashFunction(key);
    for (List<T, P> *i = map[index]; i != NULL; i = i->next)
    {
        if (i->key == key)
        {
            return i;
        }
    }
    return NULL;
}

template <typename T, typename P>
void Hash<T, P>::deleteItem(P key)
{
    auto it = find(key);
    int index = hashFunction(key);
    if (it != NULL)
    {
        DeleteGivenNode(&map[index], it);
        totalElements--;
    }
}

template <typename T, typename P>
ostream &Hash<T, P>::displayHash(ostream &s) const
{
    for (int i = 0; i < size; i++)
    {
        s << i + 1 << " --> ";
        printList(map[i], s);
        s << endl;
    }
    return s;
}

template <typename T, typename P>
void Hash<T, P>::displayHash()
{
    for (int i = 0; i < size; i++)
    {
        cout << i + 1 << " --> ";
        printList(map[i]);
        cout << endl;
    }
}

template <typename T, typename P>
istream &operator>>(istream &s, Hash<T, P> &h)
{
    P key;
    T value;
    s >> value >> ws;
    getline(s, key);
    h.insertItem(key, value);
    return s;
}

template <typename T, typename P>
ostream &operator<<(ostream &s, const Hash<T, P> &h)
{
    return h.displayHash(s);
}