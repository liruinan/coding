#include<iostream>
#include<stdio.h>
#include<string>
using namespace std;
int main()
{
    string s = "0*0,1*3,2*5,7*9";
    size_t pos = s.find(",");
    size_t pos_temp = 0;
    string temp;
    string sub = s + ",";
    string tid_list;
    string num_list;
    string tid;
    string num;
    while(pos != string::npos)
    {
       temp = sub.substr(0, pos);

       //开始解析子串3*4
       pos_temp = temp.find("*");
       tid = temp.substr(0, pos_temp);
       num = temp.substr(pos_temp + 1);
       tid_list = tid_list + tid + ",";
       num_list = num_list +  num + ",";
       //开始下一对解析
       sub = sub.substr(pos +1);
       pos = sub.find(",");

    }
    tid_list.erase(tid_list.end() - 1);
    num_list.erase(num_list.end() - 1);
    cout << tid_list;
    cout <<endl;
    cout << num_list;
    
    string k;
    k.find("*");
    cout <<k.substr(0, k.find("*"));

}

