#include<iostream>
#include<string>
#include"subfunction.cpp"
#include"mainusefun.cpp"
using namespace std;
int main()
{
	cout << "请输入表达式：";
	char notionset[20] = {'0','1','2','3','4','5','6','7','8','9','+','-','*','/','%','^','=','(',')','.'};
	
	while(true)
	{
		string expression = "";
		char ch;
		while((ch=getchar())!= '\n')
		expression+=ch;
		if(othernotion(expression,notionset,20))
		{cout << "表达式输入不合法，请重新输入:";
		continue;}
		
		else
			{//else1
				if( makeSense(expression) )//makesense函数判断表达式是否合理
				{ 
					untobi(&expression);
					cout << calculate(expression) <<endl;//合理则用calculate函数计算表达式 
			
					cout << "继续？y or n" << endl;
				
					char answer = getchar();
					//cin >> answer;
					char end = getchar();//吃掉回车 
					
					if(answer == 'y')
					{cout << "请输入表达式：";
					continue;}
					else
					{
					cout << "不继续？那没事了..." << endl;
					break;}
					}
					else{cout << "表达式输入不合法，请重新输入：";
					continue;} 
			}//else1
		
	}//while
	return 0;
}//main

