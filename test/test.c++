#include<iostream>
#include<map>
#include<unordered_map>
#include<string.h>
using namespace std;
int main(){
    multimap<int, int> m;
    m.insert(std::make_pair(4,7));
    m.insert(std::make_pair(4,6));
    m.insert(std::make_pair(2,5));
    m.clear();
    multimap<int,int>::iterator it = m.begin();

    for(; it != m.end(); ++it)
    {
        cout << it->second;
    }
    
    #pragma pack(4) //内存四个字节对齐
    union
    {
        /**
         * int      int     int      int     int     int     int 
         * int          int64        int          -           -
         *
         * float   float   float    float   float   float   float 
         *   -       -       -        -         double      float 
         *
         *  示例：共5个属性，索引及偏移量如下(索引 - 偏移量)：
         *      0  - 0 
         *      1  - 1 
         *      2  - 3 
         *      3  - 4 
         *      4  - 6
         */
        int _data[7];        //一共能存5个属性
        float _fdata[7];   //该数组也可以不用，所以其实不用联合体也可以
    };
    /**
     * 每个属性赋值时
     *      通过索引拼接得到类型
     *      通过sizeof计算类型字节数
     *      通过偏移量定位赋值地址
     */
    _data[0] = 12;                     //给第一个属性赋值
    long long k = 34;
    memcpy(_data + 1, &k, sizeof(k)); //给第二个属性赋值
    _data[3] = 56;                     //给第三个属性赋值
    double d = 7.8;
    memcpy(_data + 4, &d, sizeof(d)); //给第四个属性赋值
    /**
     * 给第五个属性赋值时有两种方式:
     *      一、假如使用float数组(_fdata):
     *          _fdata[6] = val;
     *      二、假如不使用float数组：
     *          memcpy(_data + 6, &val, sizeof(val));
     */
    float val = 9.1;
    _fdata[6] = val;
    //输出各个属性
    cout << _data[0] << endl;
    cout << *((long long *)(_data + 1)) << endl;
    cout << _data[3] << endl;
    cout << *((double *)(_data + 4)) << endl;
    cout << _fdata[6];


}
